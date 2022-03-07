package com.c1eye.dsmail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.dsmail.ware.entity.WareOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 16:25:08
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

