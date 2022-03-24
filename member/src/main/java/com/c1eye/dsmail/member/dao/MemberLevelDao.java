package com.c1eye.dsmail.member.dao;

import com.c1eye.dsmail.member.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * »áÔ±µÈ¼¶
 * 
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 15:58:46
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {

    MemberLevelEntity getDefaultLevel();

}
