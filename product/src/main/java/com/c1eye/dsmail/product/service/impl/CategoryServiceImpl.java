package com.c1eye.dsmail.product.service.impl;

import com.c1eye.dsmail.product.dao.CategoryBrandRelationDao;
import com.c1eye.dsmail.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.Query;

import com.c1eye.dsmail.product.dao.CategoryDao;
import com.c1eye.dsmail.product.entity.CategoryEntity;
import com.c1eye.dsmail.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 查出所有分类
        List<CategoryEntity> list = baseMapper.selectList(null);
        return list;

    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO 检查当前删除菜单是否被引用

        baseMapper.deleteBatchIds(asList);
    }


    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid() != 0){
            findParentPath(catelogId, paths);
        }
        return paths.toArray(new Long[paths.size()]);
    }

    /**
     * 级联更新
     * @param category
     */
    @Override
    @Transactional
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    private List<Long> findParentPath(Long cid,List<Long> path){
        CategoryEntity self = this.getById(cid);
        if(self.getParentCid() != 0){
            findParentPath(self.getParentCid(), path);
        }
        path.add(self.getCatId());
        return path;
    }


}