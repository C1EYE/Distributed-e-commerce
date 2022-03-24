package com.c1eye.dsmail.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.dsmail.member.entity.MemberEntity;
import com.c1eye.dsmail.member.vo.MemberRegisterVo;
import com.c1eye.dsmail.member.vo.MemberUserLoginVo;

import java.util.Map;

/**
 * »áÔ±
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 15:58:46
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegisterVo vo);

    void checkPhoneUnique(String email);

    void checkUserName(String userName);

    MemberEntity login(MemberUserLoginVo vo);
}

