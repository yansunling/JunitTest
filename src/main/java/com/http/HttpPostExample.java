package com.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpPostExample {
    public static void main(String[] args) throws Exception{

        String url="https://tlwl.uat.tuolong56.com/asset-api/asset/comp_asset_base_info/deleteData.do";
        String cookieAll="JSESSIONID=B4136998EF5F6D7217A262607FF1C038; _jfinal_captcha=75b3a47b7d6d4db09d60cec86ed6e3ce; oripassword=13445AC455E8EDB1996BFB2CA0C78570; auth_sessionid=e87c7ddc64ec487998380dabe45ce3d3; auth_sso_sessionid=e87c7ddc64ec487998380dabe45ce3d3; cip_sso_token=6f560839-f09a-4738-9b2f-65ed207cd32e-1701134529977; orgid=25020203; net_org_id=250108010401";
        HttpPost httpPost = new HttpPost(url);
        Map<String,Object> params=new HashMap<>();
        params.put("serial_no","20231127003153");
        String json = JSONObject.toJSONString(params);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("cookie", cookieAll);
        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpResponse response = httpClient.execute(httpPost);
        StatusLine statusLine = response.getStatusLine();
        System.out.println("Response Status: " + statusLine.getStatusCode());
        HttpEntity responseEntity = response.getEntity();
        String responseBody = EntityUtils.toString(responseEntity);
        System.out.println("Response Body: " + responseBody);




    }
}
