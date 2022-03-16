package com.c1eye.dsmail.search;

import com.alibaba.fastjson.JSON;
import com.c1eye.dsmail.search.config.ElasticConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchApplicationTests {

	@Autowired
	private RestHighLevelClient client;


	@Test
	public void contextLoads() {


	}

	@Test
	public void indexData() throws IOException {
		IndexRequest indexRequest = new IndexRequest("users");
		indexRequest.id("1");
		User user = new User();
		String s = JSON.toJSONString(user);
		indexRequest.source(s, XContentType.JSON);
		IndexResponse index = client.index(indexRequest, ElasticConfig.COMMON_OPTIONS);
		System.out.println(index);
	}

	@Data
	class User{
		private String name;
		private String password;
	}
}
