package com.c1eye.dsmail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.dsmail.product.entity.CategoryEntity;
import com.c1eye.dsmail.product.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

/**
 * ÉÌÆ·Èý¼¶·ÖÀà
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:49
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> asList);

    /**
     * 找到 catelogId的完整路径
     * [A/B/C]
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);

    void updateCascade(CategoryEntity category);

    List<CategoryEntity> getLevelOneCategorys();

    Map<String, List<Catelog2Vo>> getCatalogJson();

}

