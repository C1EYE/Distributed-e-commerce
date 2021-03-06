package com.c1eye.dsmail.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.c1eye.common.to.SkuHasStockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.c1eye.dsmail.ware.entity.WareSkuEntity;
import com.c1eye.dsmail.ware.service.WareSkuService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.R;



/**
 * 商品库存
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 16:25:08
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    // 查询sku是否有库存
    @PostMapping("/hasstock")
    public R getSkusHasStock(@RequestBody List<Long> skuIds){
        List<SkuHasStockVO> res  = wareSkuService.getSkusHasStock(skuIds);
        return R.ok().setData(res);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
