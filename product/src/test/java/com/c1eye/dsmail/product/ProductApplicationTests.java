package com.c1eye.dsmail.product;

import com.c1eye.dsmail.product.entity.BrandEntity;
import com.c1eye.dsmail.product.service.BrandService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductApplicationTests {

	@Autowired
	BrandService brandService;

	@Test
	void contextLoads() {
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setName("华为");
		brandEntity.setDescript("用5G，选华为");
		brandService.save(brandEntity);
	}

}
