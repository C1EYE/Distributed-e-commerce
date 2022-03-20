package com.c1eye.dsmail.product.dao;

import com.c1eye.dsmail.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.c1eye.dsmail.product.vo.SkuItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * skuÏúÊÛÊôÐÔ&Öµ
 * 
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:49
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(@Param("spuId") Long spuId);
}
