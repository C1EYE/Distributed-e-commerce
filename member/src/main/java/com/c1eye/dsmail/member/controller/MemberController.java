package com.c1eye.dsmail.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.c1eye.common.exception.BizCodeEnum;
import com.c1eye.dsmail.member.exception.PhoneExsitException;
import com.c1eye.dsmail.member.exception.UsernameExistException;
import com.c1eye.dsmail.member.feign.CouponFeignService;
import com.c1eye.dsmail.member.vo.MemberRegisterVo;
import com.c1eye.dsmail.member.vo.MemberUserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.c1eye.dsmail.member.entity.MemberEntity;
import com.c1eye.dsmail.member.service.MemberService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.R;



/**
 * »áÔ±
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 15:58:46
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;

    @RequestMapping("coupons")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        R membercoupons = couponFeignService.membercoupons();
        return R.ok().put("member", memberEntity).put("coupons", membercoupons.get("coupons"));
    }

    @PostMapping("/login")
    public R login(@RequestBody MemberUserLoginVo vo){
        MemberEntity entity = memberService.login(vo);
        return R.ok();
    }


    @PostMapping("/regist")
    public R regist(@RequestBody MemberRegisterVo vo){
        try {
            memberService.regist(vo);

        }catch (PhoneExsitException e){
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        }catch (UsernameExistException e){
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
