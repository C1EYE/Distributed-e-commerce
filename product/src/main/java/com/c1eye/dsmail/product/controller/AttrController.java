package com.c1eye.dsmail.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.c1eye.dsmail.product.entity.ProductAttrValueEntity;
import com.c1eye.dsmail.product.service.ProductAttrValueService;
import com.c1eye.dsmail.product.vo.AttrGroupRelationVo;
import com.c1eye.dsmail.product.vo.AttrRespVo;
import com.c1eye.dsmail.product.vo.AttrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.c1eye.dsmail.product.entity.AttrEntity;
import com.c1eye.dsmail.product.service.AttrService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.R;


/**
 * ÉÌÆ·ÊôÐÔ
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 14:55:43
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;


    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(
            @RequestParam Map<String, Object> params,
            @PathVariable("catelogId") Long catelogId,
            @PathVariable("attrType") String type) {

        PageUtils page = attrService.queryBaseAttrPage(params, catelogId, type);
        return R.ok().put("page", page);
    }


    // /product/attr/base/listforspu/{spuId}
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId){

        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrlistforspu(spuId);

        return R.ok().put("data",entities);
    }

    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId")Long spuId,List<ProductAttrValueEntity> entities){
        productAttrValueService.updateSpuAttr(spuId, entities);
        return R.ok();
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrRespVo attr = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVO attr) {
        attrService.saveAttr(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVO attr) {
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
