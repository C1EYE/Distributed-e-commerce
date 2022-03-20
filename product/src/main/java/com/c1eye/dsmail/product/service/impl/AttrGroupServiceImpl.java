package com.c1eye.dsmail.product.service.impl;

import com.c1eye.dsmail.product.entity.AttrEntity;
import com.c1eye.dsmail.product.service.AttrService;
import com.c1eye.dsmail.product.vo.AttrGroupWithAttrsVo;
import com.c1eye.dsmail.product.vo.SkuItemVo;
import com.c1eye.dsmail.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.Query;

import com.c1eye.dsmail.product.dao.AttrGroupDao;
import com.c1eye.dsmail.product.entity.AttrGroupEntity;
import com.c1eye.dsmail.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
                                               );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(obj -> obj.eq("attr_group_id", key).or().like("attr_group_name", key));
        }

        if (categoryId == 0) {
            return new PageUtils(this.page(new Query<AttrGroupEntity>().getPage(params), wrapper));
        } else {
            wrapper.eq("catelog_id", categoryId);
            return new PageUtils(this.page(new Query<AttrGroupEntity>().getPage(params), wrapper));
        }


    }

    /**
     * 根据分类ID查出所有分组以及这些组里的属性
     *
     * @param catelogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(
            Long catelogId) {

        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id",
                catelogId));

        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group -> {
            AttrGroupWithAttrsVo vo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group, vo);
            List<AttrEntity> relationAttr = attrService.getRelationAttr(vo.getAttrGroupId());
            vo.setAttrs(relationAttr);
            return vo;
        }).collect(Collectors.toList());
        return collect;

    }

    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {
        // 查出当前spu对应的所有属性分组信息以及当前分组下的所有属性对应的值
        List<SpuItemAttrGroupVo> vos =  baseMapper.getAttrGroupWithAttrsBySpuId(spuId, catalogId);
        return vos;
    }

}