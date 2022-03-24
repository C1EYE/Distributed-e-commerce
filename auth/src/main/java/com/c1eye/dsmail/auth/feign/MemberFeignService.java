package com.c1eye.dsmail.auth.feign;

import com.c1eye.common.utils.R;
import com.c1eye.dsmail.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author c1eye
 * time 2022/3/21 21:31
 */
@FeignClient("dsm-member")
public interface MemberFeignService {

    @PostMapping("/member/member/regist")
    public R regist(@RequestBody UserRegisterVo vo);
}
