package com.module.mq;

import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpMq {

    public static void main(String[] args) throws Exception{
        String apiMessage = getApiMessage("22");
        System.out.println(apiMessage);

    }



    public static String getApiMessage(String queueName) throws IOException {

        String host="127.0.0.1";
        String port="15672";
        String username="guest";
        String password="guest";


        //发送一个GET请求
        HttpURLConnection httpConn = null;
        BufferedReader in = null;

        String urlString = "http://" + host + ":" + port + "/api/bindings";
        URL url = new URL(urlString);
        httpConn = (HttpURLConnection) url.openConnection();
        //设置用户名密码
        String auth = username + ":" + password;
        BASE64Encoder enc = new BASE64Encoder();
        String encoding = enc.encode(auth.getBytes());
        httpConn.setDoOutput(true);
        httpConn.setRequestProperty("Authorization", "Basic " + encoding);
        // 建立实际的连接
        httpConn.connect();
        //读取响应
        if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            StringBuilder content = new StringBuilder();
            String tempStr = "";
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            while ((tempStr = in.readLine()) != null) {
                content.append(tempStr);
            }
            in.close();
            httpConn.disconnect();
            return content.toString();
        } else {
            httpConn.disconnect();
            return "";
        }
    }
}
