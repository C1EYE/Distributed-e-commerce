package com.c1eye.dsmail.product;

import com.c1eye.dsmail.product.service.BrandService;
import com.c1eye.dsmail.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class ProductApplicationTests {

	@Autowired
	BrandService brandService;

//	@Test
//	void contextLoads() {
//
//	}

	@Autowired
	CategoryService categoryService;
	@Test
	public void testFindPath() {
		Long[] path = categoryService.findCatelogPath(225L);
		log.info("完整路径:{}", Arrays.asList(path));
	}
}
