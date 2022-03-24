package com.c1eye.dsmail.auth.controller;

import com.c1eye.common.utils.HttpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author c1eye
 * time 2022/3/22 13:59
 */
@Controller
public class OAuth2Controller {

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code) throws Exception {
        // 登陆成功就跳转首页
        Map<String, String> map = new HashMap<>();
        HttpUtils.doPost("", "/oauth2/access_token", "post", null, null, map);
        return "";
    }
}
