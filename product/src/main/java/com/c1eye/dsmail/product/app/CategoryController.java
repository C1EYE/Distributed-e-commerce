package com.c1eye.dsmail.product.app;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.c1eye.dsmail.product.entity.CategoryEntity;
import com.c1eye.dsmail.product.service.CategoryService;
import com.c1eye.common.utils.R;


/**
 * ÉÌÆ·Èý¼¶·ÖÀà
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 14:55:43
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类以及子分类，以树形结构组装起来
     */
    @RequestMapping("/list/tree")
    public R list(@RequestParam Map<String, Object> params) {
        List<CategoryEntity> entities = categoryService.listWithTree();
        List<CategoryEntity> data = entities.stream()
                                            .filter(e -> e.getParentCid() == 0)
                                            .map(menu -> {
                                                menu.setChildren(getChildrens(menu, entities));
                                                return menu;
                                            })
                                            .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 :
                                                    menu.getSort())))
                                            .collect(Collectors.toList());
        return R.ok().put("data", data);
    }

    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> data = all.stream()
                                       .filter(categoryEntity -> categoryEntity.getParentCid().equals(root.getCatId()))
                                       .map(categoryEntity -> {
                                           categoryEntity.setChildren(getChildrens(categoryEntity, all));
                                           return categoryEntity;
                                       })
                                       .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 :
                                               menu.getSort())))
                                       .collect(Collectors.toList());
        return data;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateCascade(category);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] category) {
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds) {
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
