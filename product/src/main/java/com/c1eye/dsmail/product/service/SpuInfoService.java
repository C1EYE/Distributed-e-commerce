package com.c1eye.dsmail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.dsmail.product.entity.SpuInfoDescEntity;
import com.c1eye.dsmail.product.entity.SpuInfoEntity;
import com.c1eye.dsmail.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spuÐÅÏ¢
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:49
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity infoEntity);


    PageUtils queryPageByCondition(Map<String, Object> params);
}

