package com.c1eye.dsmail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.dsmail.product.entity.SkuInfoEntity;
import com.c1eye.dsmail.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * skuÐÅÏ¢
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:48
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);

    List<SkuInfoEntity> getSkuBySpuId(Long spuId);

    SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException;
}

