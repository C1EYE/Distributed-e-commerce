package com.c1eye.dsmail.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.dsmail.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * ÉÌÆ·½×ÌÝ¼Û¸ñ
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 15:49:17
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

