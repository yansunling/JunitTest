package com.dy.test.autoTest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class RestTemplateTest {

	public static void main(String[] args) throws IOException {
//		String url="http://localhost/query/actions/restful/doGenericSearch/crm_application_customer_list.do?actionId=query_doGenericSearch&version=V1.0.0";
		String url="http://localhost/query/actions/restful/doGenericExport/crm_application_customer_list.do?actionId=query_doGenericExport&version=V1.0.0&download_type=2";
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		List<String> cookies = new ArrayList<>();
		cookies.add("cip_sso_token=fd4174a4-dc1f-47a4-b638-96be6605f55e-1562729011451");
		headers.put("Cookie",cookies);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("page", "1");
		map.add("rows", "15");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		//下载文件
		ResponseEntity<Resource> response = restTemplate.postForEntity(url, request, Resource.class);
		InputStream is = response.getBody().getInputStream();
		FileOutputStream fos = new FileOutputStream("E:/crm_application_customer_list.xlsx");
		byte[] b = new byte[1024];
		int length;
		while((length= is.read(b))>0){
			fos.write(b,0,length);
		}
		is.close();
		fos.close();
//		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//		CIPResponseQueryMsg object = JSONObject.parseObject(response.getBody(), CIPResponseQueryMsg.class);
		System.out.println(response.getBody());
	}
}
