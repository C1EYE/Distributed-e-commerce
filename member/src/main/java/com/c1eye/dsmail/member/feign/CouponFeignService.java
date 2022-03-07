package com.c1eye.dsmail.member.feign;

import com.c1eye.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author c1eye
 * time 2022/3/7 18:43
 */
@FeignClient("dsm-coupon")
public interface CouponFeignService {
    @RequestMapping("coupon/coupon/member/list")
    R membercoupons();
}
