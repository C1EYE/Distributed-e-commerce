package com.c1eye.dsmail.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.c1eye.dsmail.product.entity.BrandEntity;
import com.c1eye.dsmail.product.entity.CategoryEntity;
import com.c1eye.dsmail.product.service.BrandService;
import com.c1eye.dsmail.product.service.CategoryService;
import com.c1eye.dsmail.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.c1eye.dsmail.product.entity.CategoryBrandRelationEntity;
import com.c1eye.dsmail.product.service.CategoryBrandRelationService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.R;


/**
 * Æ·ÅÆ·ÖÀà¹ØÁª
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 14:55:43
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;
    /**
     * 获取品牌对应的分类
     * @param brandId
     * @return
     */
    @GetMapping("/catelog/list")
    public R cateloglist(@RequestParam("brandId") Long brandId) {
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_id", brandId);
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(wrapper);
        return R.ok().put("data", data);
    }

    @RequestMapping("/brands/list")
    public R relationBrandList(@RequestParam(value = "catId",required = true) Long catId){
        List<BrandEntity> vos = categoryBrandRelationService.getBrandsByCatId(catId);
        List<BrandVo> collect = vos.stream().map(i -> {
            BrandVo vo = new BrandVo();
            vo.setBrandId(i.getBrandId());
            vo.setBrandName(i.getName());
            return vo;
        }).collect(Collectors.toList());
        return R.ok().put("data",collect);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brand = brandService.getById(brandId);
        CategoryEntity catelog = categoryService.getById(catelogId);
        categoryBrandRelation.setBrandName(brand.getName());
        categoryBrandRelation.setCatelogName(catelog.getName());
        categoryBrandRelationService.save(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
