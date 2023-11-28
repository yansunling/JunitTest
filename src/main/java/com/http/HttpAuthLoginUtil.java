package com.http;

import com.alibaba.fastjson.JSONObject;
import com.yd.common.data.CIPResponseMsg;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class HttpAuthLoginUtil {
    public static String getAuthCookie() throws Exception{

        String url="https://tlwl.uat.tuolong56.com/auth-api/web/portal";
        HttpPost httpPost = new HttpPost(url);
        // 添加表单参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("authorize_subject_id", "T1113"));
        params.add(new BasicNameValuePair("authorize_subject_secret", "klFmgCFruQg9xym9genTEA=="));
        params.add(new BasicNameValuePair("authorize_group_id", "query_uat"));
        params.add(new BasicNameValuePair("company_id", "tlwl"));
        params.add(new BasicNameValuePair("name_space_id", "uat"));
        params.add(new BasicNameValuePair("app_id", "query"));
        params.add(new BasicNameValuePair("param_return_url", "https://tlwl.uat.tuolong56.com/query"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        StatusLine statusLine = response.getStatusLine();
        System.out.println("Response Status: " + statusLine.getStatusCode());
        HttpEntity responseEntity = response.getEntity();
        String responseBody = EntityUtils.toString(responseEntity);
        System.out.println("Response Body: " + responseBody);

        CIPResponseMsg responseMsg = JSONObject.parseObject(responseBody, CIPResponseMsg.class);
        if(responseMsg.errorCode!=0){
            throw new RuntimeException(responseMsg.msg);
        }

        List<Cookie> cookies = cookieStore.getCookies();
        StringBuffer sb=new StringBuffer();
        for(Cookie cookie:cookies) {
            sb.append(cookie.getName()+"="+cookie.getValue()+";");
        }
        System.out.println("Cookie:"+sb.toString());
        return sb.toString();

    }
}
