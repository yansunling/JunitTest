package com.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownQueryFile {
    public static void main(String[] args) throws Exception{

        CloseableHttpClient httpclient = HttpClients.createDefault();

        String cookie=HttpAuthLoginUtil.getAuthCookie();

        String host="https://tlwl.uat.tuolong56.com";
        HttpPost httppost = new HttpPost(host+"/query/actions/query_new_search/exportData/V1.0.0/query_new_register_list.do?loadColumnType=all");

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
        httppost.setConfig(requestConfig);
        httppost.setHeader("cookie",cookie);
        httppost.setHeader("Content-Type","application/x-www-form-urlencoded");
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> param=new HashMap<>();
        param.put("value","query_hcm_jx_emp_list_exp");
        param.put("param_id","query_id");
        list.add(param);
        // 添加表单参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("values", JSON.toJSONString(list)));
        httppost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = httpclient.execute(httppost);
        try {
            System.out.println(response.getStatusLine());
            Header firstHeader = response.getFirstHeader("Content-Disposition");
            String fileName = firstHeader.getValue().replace("attachment;fileName=","");
            fileName= new String(fileName.getBytes("iso-8859-1"),"utf-8");
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                InputStream is = resEntity.getContent();
                File file = new File("C:/Users/yansunling/Desktop/"+fileName);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int len = -1;
                while((len = is.read(buffer) )!= -1){
                    fos.write(buffer, 0, len);
                }
                fos.close();
                is.close();
            }
            EntityUtils.consume(resEntity);
        }finally {
            response.close();
        }

    }
}
