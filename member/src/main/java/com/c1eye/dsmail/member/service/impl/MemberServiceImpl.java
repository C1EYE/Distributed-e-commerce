package com.c1eye.dsmail.member.service.impl;

import com.c1eye.dsmail.member.dao.MemberLevelDao;
import com.c1eye.dsmail.member.entity.MemberLevelEntity;
import com.c1eye.dsmail.member.exception.PhoneExsitException;
import com.c1eye.dsmail.member.exception.UsernameExistException;
import com.c1eye.dsmail.member.vo.MemberRegisterVo;
import com.c1eye.dsmail.member.vo.MemberUserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.Query;

import com.c1eye.dsmail.member.dao.MemberDao;
import com.c1eye.dsmail.member.entity.MemberEntity;
import com.c1eye.dsmail.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
                                            );

        return new PageUtils(page);
    }

    @Override
    public void regist(MemberRegisterVo vo) {
        MemberEntity memberEntity = new MemberEntity();
        // 默认等级
        MemberLevelEntity level = memberLevelDao.getDefaultLevel();
        memberEntity.setLevelId(level.getId());

        // 检查唯一性
        checkPhoneUnique(vo.getPhone());
        checkUserName(vo.getUserName());

        memberEntity.setMobile(vo.getPhone());
        memberEntity.setUsername(vo.getUserName());

        // 密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        memberEntity.setPassword(encode);

        // 其他的信息

        this.baseMapper.insert(memberEntity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExsitException {
        Integer mobile = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (mobile > 0) {
            throw new PhoneExsitException();
        }
    }

    @Override
    public void checkUserName(String userName) throws UsernameExistException {
        Integer username = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));
        if (username > 0) {
            throw new UsernameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberUserLoginVo vo) {
        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        MemberEntity entity =
                this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("mobile", loginacct).or().eq("username",
                        loginacct));
        if (entity == null) {
            // 登录失败
            return null;
        }
        if (new BCryptPasswordEncoder().matches(password, entity.getPassword())) {
            return entity;
        }
        return null;

    }


}