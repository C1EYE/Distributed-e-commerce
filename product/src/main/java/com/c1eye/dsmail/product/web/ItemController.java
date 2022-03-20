package com.c1eye.dsmail.product.web;

import com.c1eye.dsmail.product.service.SkuInfoService;
import com.c1eye.dsmail.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ExecutionException;

/**
 * @author c1eye
 * time 2022/3/20 11:04
 */
@Controller
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException,
                                                                                 InterruptedException {
        SkuItemVo vo =  skuInfoService.item(skuId);
        model.addAttribute("item", vo);
        return "shangpinxiangqing";
    }
}
