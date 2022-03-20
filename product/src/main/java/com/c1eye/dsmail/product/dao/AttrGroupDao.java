package com.c1eye.dsmail.product.dao;

import com.c1eye.dsmail.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.c1eye.dsmail.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ÊôÐÔ·Ö×é
 * 
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:49
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
