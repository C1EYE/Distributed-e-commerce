package com.c1eye.dsmail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.c1eye.common.constant.ProductConstant;
import com.c1eye.dsmail.product.dao.AttrAttrgroupRelationDao;
import com.c1eye.dsmail.product.dao.AttrGroupDao;
import com.c1eye.dsmail.product.dao.CategoryDao;
import com.c1eye.dsmail.product.entity.AttrAttrgroupRelationEntity;
import com.c1eye.dsmail.product.entity.AttrGroupEntity;
import com.c1eye.dsmail.product.entity.CategoryEntity;
import com.c1eye.dsmail.product.service.CategoryService;
import com.c1eye.dsmail.product.vo.AttrGroupRelationVo;
import com.c1eye.dsmail.product.vo.AttrRespVo;
import com.c1eye.dsmail.product.vo.AttrVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.Query;

import com.c1eye.dsmail.product.dao.AttrDao;
import com.c1eye.dsmail.product.entity.AttrEntity;
import com.c1eye.dsmail.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
                                          );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttr(AttrVO attrVO) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVO, attrEntity);
        this.save(attrEntity);
        // 保存关联关系
        if (attrVO.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE.getCode()) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attrVO.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_type", "base".equalsIgnoreCase(type) ? ProductConstant.AttrEnum.ATTR_TYPE.getCode() :
                ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        if (catelogId != 0) {
            wrapper.eq("catelog_id", catelogId);
        }

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w -> w.eq("attr_id", key).or().like("attr_name", key));
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        PageUtils pages = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVoList = records.stream().map((attrEntity) -> {
            AttrRespVo respVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, respVo);
            // 设置分类和分组
            if ("base".equalsIgnoreCase(type)) {
                AttrAttrgroupRelationEntity relationEntity =
                        relationDao
                                .selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",
                                        attrEntity.getAttrId()));
                if (relationEntity != null && relationEntity.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    respVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                respVo.setCatelogName(categoryEntity.getName());
            }
            return respVo;
        }).collect(Collectors.toList());
        pages.setList(respVoList);
        return pages;

    }

    @Override
    @Cacheable(value = "attr",key = "'attrinfo:'+#root.args[0]")
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attr = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attr, attrRespVo);


        AttrAttrgroupRelationEntity relationEntity =
                relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",
                        attr.getAttrId()));

        if (relationEntity != null) {
            attrRespVo.setAttrGroupId(relationEntity.getAttrGroupId());
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
            if (attrGroupEntity == null) {
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
        }

        // 分类信息
        Long catelogId = attr.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        attrRespVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null) {
            attrRespVo.setCatelogName(categoryEntity.getName());
        }
        attrRespVo.setCatelogName(categoryEntity.getName());
        return attrRespVo;
    }

    @Override
    @Transactional
    public void updateAttr(AttrVO attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE.getCode()) {
            // 修改分组
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());
            UpdateWrapper<AttrAttrgroupRelationEntity> wrapper = new UpdateWrapper<>();
            wrapper.eq("attr_id", attr.getAttrId());

            Integer count = relationDao.selectCount(wrapper);
            if (count > 0) {
                relationDao.update(relationEntity, wrapper);
            } else {
                relationDao.insert(relationEntity);
            }
        }

    }

    /**
     * 根据分组ID查找关联的所有基本属性
     *
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities =
                relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id",
                        attrgroupId));
        List<Long> list = relationEntities.stream().map(attr -> attr.getAttrId()).collect(Collectors.toList());
        if (list == null || list.size() == 0) {
            return null;
        }
        Collection<AttrEntity> attrs = this.listByIds(list);
        return (List<AttrEntity>) attrs;

    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
//        relationDao.delete(new QueryWrapper<>().eq("attr_id", 1L).eq("attr_group_id", 1L));
        List<AttrAttrgroupRelationEntity> list = Arrays.asList(vos).stream().map(item -> {
            AttrAttrgroupRelationEntity entity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, entity);
            return entity;
        }).collect(Collectors.toList());
        relationDao.deleteBatchRelation(list);
    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1、当前分组只能关联自己所属的分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        //2、当前分组只能关联别的分组没有引用的属性
        //2.1)、当前分类下的其他分组
        List<AttrGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id",
                catelogId));
        List<Long> collect = group.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        //2.2)、这些分组关联的属性
        List<AttrAttrgroupRelationEntity> groupId =
                relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
        List<Long> attrIds = groupId.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2.3)、从当前分类的所有属性中移除这些属性；
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type",
                ProductConstant.AttrEnum.ATTR_TYPE.getCode());
        if (attrIds != null && attrIds.size() > 0) {
            wrapper.notIn("attr_id", attrIds);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);

        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;


    }

    @Override
    public List<Long> selectSearchAttrs(List<Long> ids) {
        return this.baseMapper.selectSearchAttrIds(ids);
    }

}