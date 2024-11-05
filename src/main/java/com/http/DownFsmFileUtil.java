package com.http;

import com.yd.utils.common.StringUtils;
import lombok.SneakyThrows;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

public class DownFsmFileUtil {

    public static void main(String[] args) {
        String serialNo="crm_227ab454-a861-4292-a043-d05d7e5b8871_1";

        String[] split = serialNo.split(",");
        String host="https://kp.tuolong56.com";

        for(String str:split){
            downFile(host,str,"");
        }


    }


    @SneakyThrows
    public static File downFile(String host,String serialNo,String fileName) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(host+"/fsm/api/fsm_api/download.do?file_app_id=crm&file_serial_no="+serialNo);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
        httppost.setConfig(requestConfig);
        CloseableHttpResponse response = httpclient.execute(httppost);
        File file=null;
        try {

            File dir=new File("C:/Users/yansunling/Desktop/fsm/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            System.out.println(response.getStatusLine());
            Header firstHeader = response.getFirstHeader("Content-Disposition");
            if(StringUtils.isNotBlank(fileName)){
                fileName=fileName+".png";
            }else{
                fileName = firstHeader.getValue().replace("attachment;filename=","");
                fileName=URLDecoder.decode(fileName, "utf-8");
            }



            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                InputStream is = resEntity.getContent();
                file = new File(dir.getPath()+"/"+fileName);
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
