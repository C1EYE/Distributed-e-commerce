package com.c1eye.dsmail.thirdparty.controller;

import com.c1eye.common.utils.R;
import com.c1eye.dsmail.thirdparty.componenet.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author c1eye
 * time 2022/3/21 08:24
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsComponent smsComponent;
    @GetMapping("/sendcode")
    public R sendCode(@RequestParam("phone")String phone,@RequestParam("code") String code){
        smsComponent.sendSmsCode(phone,code);
        return R.ok();
    }
}
