package com.c1eye.dsmail.auth.web;

import com.alibaba.fastjson.TypeReference;
import com.c1eye.common.constant.AuthServerConstant;
import com.c1eye.common.exception.BizCodeEnum;
import com.c1eye.common.utils.R;
import com.c1eye.dsmail.auth.feign.MemberFeignService;
import com.c1eye.dsmail.auth.feign.ThirdPartFeignService;
import com.c1eye.dsmail.auth.vo.UserLoginVo;
import com.c1eye.dsmail.auth.vo.UserRegisterVo;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author c1eye
 * time 2022/3/20 19:54
 */
@Controller
public class LoginController {

    @Autowired
    private ThirdPartFeignService thirdPartFeignService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MemberFeignService memberFeignService;

    @GetMapping("/sms/sendcode")
    @ResponseBody
    public R sendCode(@RequestParam("/phone")String phone){
        // 接口防刷
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if(StringUtils.isNotEmpty(redisCode)){
            long lastTime = Long.parseLong(redisCode.split("_")[1]);
            if(System.currentTimeMillis() - lastTime < 600000){
                // 60秒内不能再发
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(),
                        BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        String code = UUID.randomUUID().toString().substring(0, 5);
        String codeplus = code+ "_" + System.currentTimeMillis();
        // redis缓存验证码，防止重复发送
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, codeplus);
        thirdPartFeignService.sendCode(phone,code);
        return R.ok();
    }

    @PostMapping("/register")
    public String regist(@Valid UserRegisterVo vo, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            Map<String, String> collect =
                    result.getFieldErrors().stream().collect(Collectors.toMap((key) -> key.getField(),
                            value -> value.getDefaultMessage()));
            redirectAttributes.addFlashAttribute("errors", collect);
            //FIXME 填全称
            return "redirect:/reg.html";
        }
        // 调用远程服务注册
        String code = vo.getCode();
        String s = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if(StringUtils.isNotEmpty(s)){
            if(code.equals(s.split("_")[0])){
                // 真正注册
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                R r = memberFeignService.regist(vo);
                if(r.getCode() == 0){
                    return "redirect:/login.html";
                }else{
                    HashMap<String, String> errors = new HashMap<>();
                    errors.put("msg", r.getData(new TypeReference<String>() {}));
                    // 校验出错
                    return "redirect:http://auth.gulimail.com/reg.html";
                }
            }else {
                HashMap<String, String> errors = new HashMap<>();
                redirectAttributes.addFlashAttribute("errors", errors);
                // 校验出错
                return "redirect:http://auth.gulimail.com/reg.html";
            }
        }else {
            HashMap<String, String> errors = new HashMap<>();
            redirectAttributes.addFlashAttribute("errors", errors);
            // 校验出错
            return "redirect:http://auth.gulimail.com/reg.html";
        }
    }

    @PostMapping("/login")
    public String login(UserLoginVo vo){
        // 远程登录

        return "redirect:http://gulimail.com";
    }
}
