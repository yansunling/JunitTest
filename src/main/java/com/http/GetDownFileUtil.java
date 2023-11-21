package com.http;

import com.yd.utils.common.StringUtils;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class GetDownFileUtil {

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
