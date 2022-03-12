package com.c1eye.dsmail.product.feign;

import com.c1eye.common.to.SkuReductionTo;
import com.c1eye.common.to.SpuBoundTo;
import com.c1eye.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author c1eye
 * time 2022/3/12 20:37
 */
@FeignClient("dsm-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
