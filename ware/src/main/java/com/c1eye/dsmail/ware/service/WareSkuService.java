package com.c1eye.dsmail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.c1eye.common.to.SkuHasStockVO;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.dsmail.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * εεεΊε­
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 16:25:08
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVO> getSkusHasStock(List<Long> skuIds);
}

