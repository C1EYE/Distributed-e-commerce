package com.c1eye.dsmail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.dsmail.product.entity.AttrGroupEntity;
import com.c1eye.dsmail.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * ÊôÐÔ·Ö×é
 *
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:49
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long categoryId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}

