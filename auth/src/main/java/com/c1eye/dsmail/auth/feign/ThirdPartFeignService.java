package com.c1eye.dsmail.auth.feign;

import com.c1eye.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author c1eye
 * time 2022/3/21 08:39
 */
@FeignClient("dsm-third-party")
public interface ThirdPartFeignService {

    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone")String phone, @RequestParam("code") String code);
}
