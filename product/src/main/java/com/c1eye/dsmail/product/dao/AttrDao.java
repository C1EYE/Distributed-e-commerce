package com.c1eye.dsmail.product.dao;

import com.c1eye.dsmail.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ÉÌÆ·ÊôÐÔ
 * 
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:49
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> selectSearchAttrIds(@Param("ids") List<Long> ids);
}
