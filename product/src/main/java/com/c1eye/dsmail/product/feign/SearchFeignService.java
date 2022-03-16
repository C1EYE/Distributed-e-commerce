package com.c1eye.dsmail.product.feign;

import com.c1eye.common.to.es.SkuEsModel;
import com.c1eye.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author c1eye
 * time 2022/3/15 10:51
 */
@FeignClient("dsm-search")
public interface SearchFeignService {
    @PostMapping("/search/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}

