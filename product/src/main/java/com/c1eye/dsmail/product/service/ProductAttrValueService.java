package com.c1eye.dsmail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.dsmail.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spuÊôÐÔÖµ
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:48
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValueEntity> collect);

    List<ProductAttrValueEntity> baseAttrlistforspu(Long spuId);

    void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities);
}

