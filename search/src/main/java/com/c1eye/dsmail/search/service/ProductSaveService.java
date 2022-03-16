package com.c1eye.dsmail.search.service;

import com.c1eye.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author c1eye
 * time 2022/3/15 10:02
 */
public interface ProductSaveService {

    public Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
