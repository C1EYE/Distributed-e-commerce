package com.c1eye.dsmail.search.feign;

import com.c1eye.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author c1eye
 * time 2022/3/19 19:08
 */
@FeignClient("dsm-product")
public interface ProductFeignService {


    @GetMapping("/product/attr/info/{attrId")
    R attrInfo(long parseLong);

    @GetMapping("/infos")
    R info(@RequestParam("brandIds") List<Long> brandIds);
}
