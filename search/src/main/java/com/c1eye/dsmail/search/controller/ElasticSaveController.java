package com.c1eye.dsmail.search.controller;

import com.c1eye.common.exception.BizCodeEnum;
import com.c1eye.common.to.es.SkuEsModel;
import com.c1eye.common.utils.R;
import com.c1eye.dsmail.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author c1eye
 * time 2022/3/15 10:00
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class ElasticSaveController {

    @Autowired
    private ProductSaveService productSaveService;

    // 上架商品
    @PostMapping("/save/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        boolean b = false;
        try {
            productSaveService.productStatusUp(skuEsModels);
        } catch (IOException e) {
            log.error("ES商品上架错误:{}", e);
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }
        if (b) {
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        } else {
            return R.ok();
        }
    }
}
