package com.http;


import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.Resource;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import static com.http.MyQueryUtil.generateHttpRequestFactory;

public class GetDownFileUtil {



    @SneakyThrows
    public static File downFileNew(String picPath,String url) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = generateHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(200000);
        httpRequestFactory.setReadTimeout(200000);
        RestTemplate restTemplate=new RestTemplate(httpRequestFactory);
        url="https://oss.tuolong56.com/"+url;
        // 发送GET请求获取图片地址
        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Resource.class);
        // 获取图片资源
        Resource resource = responseEntity.getBody();
        File file = new File(picPath);
        if (resource != null) {
            try (InputStream inputStream = resource.getInputStream()) {
                // 创建输出流
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    // 将输入流的内容写入文件
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    @SneakyThrows
    public static File downFile(String picPath,String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        url="https://oss.tuolong56.com/"+url;
        HttpGet httppost = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
        httppost.setConfig(requestConfig);
        CloseableHttpResponse response = httpclient.execute(httppost);
        File file =new File(picPath);
        try {
            String[] split = url.split("/");
            String fileName = split[split.length-1];
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                InputStream is = resEntity.getContent();

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
        } finally {
            response.close();
        }
        return file;
    }



}
