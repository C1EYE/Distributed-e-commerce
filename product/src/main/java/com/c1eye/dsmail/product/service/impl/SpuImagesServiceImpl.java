package com.c1eye.dsmail.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.Query;

import com.c1eye.dsmail.product.dao.SpuImagesDao;
import com.c1eye.dsmail.product.entity.SpuImagesEntity;
import com.c1eye.dsmail.product.service.SpuImagesService;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
                                               );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long id, List<String> images) {
        if (images == null || images.size() == 0) {
            return;
        } else {
            List<SpuImagesEntity> collect = images.stream().map(image -> {
                SpuImagesEntity entity = new SpuImagesEntity();
                entity.setSpuId(id);
                entity.setImgUrl(image);
                return entity;
            }).collect(Collectors.toList());
            this.saveBatch(collect);

        }
    }

}