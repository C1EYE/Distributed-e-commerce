package com.c1eye.dsmail.thirdparty;

import com.c1eye.dsmail.thirdparty.componenet.SmsComponent;
import com.c1eye.dsmail.thirdparty.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ThirdPartyApplicationTests {

	@Test
	public void msgSend() {
		String host = "https://dfsns.market.alicloudapi.com";
		String path = "/data/send_sms";
		String method = "POST";
		String appcode = "30de473dd11240e3b3db1a1bc6307720";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("content", "code:1234");
		bodys.put("phone_number", "13514304211");
		bodys.put("template_id", "TPL_0000");


		try {
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			System.out.println(response.toString());
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Autowired
	SmsComponent smsComponent;
	@Test
	public void test2() {
		smsComponent.sendSmsCode("13514304211","666666");
	}
}
