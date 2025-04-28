package com.oa;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class FileUploader {
    public static void main(String[] args) throws Exception {

        //HttpPost httpPost = new HttpPost("https://oa.uat.tuolong56.com/docs/docupload/MultiDocUploadByWorkflow.jsp");
        File file=new File("C:/Users/yansunling/Desktop/企业微信截图_20240301084259.png");
        // 请求的目标 URL
        String url = "https://oa.uat.tuolong56.com/docs/docupload/MultiDocUploadByWorkflow.jsp";

        // 创建 HttpClient 实例
        HttpClient httpClient = HttpClients.createDefault();
        // 创建 HttpPost 请求
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        httpPost.setHeader("Cookie", "loginfileweaver=%2Fwui%2Ftheme%2Fecology8%2Fpage%2Flogin.jsp%3FtemplateId%3D3%26logintype%3D1%26gopage%3D; languageidweaver=7; testBanCookie=test; ecology_JSessionId=abcy3b2bTKL17d6Bci1zz; loginidweaver=1492");

        try {
            // 创建 MultipartEntityBuilder 对象
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            // 添加文件
            builder.addBinaryBody("Filedata", file, ContentType.APPLICATION_OCTET_STREAM, file.getName());

            // 构建多部分表单数据
            HttpEntity multipart = builder.build();
            // 设置 Content-Type 头，让其包含自动生成的边界
            httpPost.setEntity(multipart);
            httpPost.setHeader("Content-Type", multipart.getContentType().getValue());

            // 执行请求
            HttpResponse response = httpClient.execute(httpPost);
            // 获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                // 处理响应内容
                String responseContent = EntityUtils.toString(responseEntity);
                System.out.println("Response: " + responseContent);
            }

            // 消耗掉响应实体，释放资源
            EntityUtils.consume(responseEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
