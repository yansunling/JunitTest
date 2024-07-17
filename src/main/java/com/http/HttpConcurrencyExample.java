package com.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.*;

public class HttpConcurrencyExample {
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(15);
        int size=5;

        CountDownLatch countDownLatch = new CountDownLatch(size);
        for(int i=0;i<size;i++){
            Thread.sleep(1000);
            executorService.submit(new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    try {

                        String url="http://localhost:10340/tmsp-api/prv/api/send/sign.do";

                        HttpPost httpPost = new HttpPost(url);
                        String json="{\n" +
                                "    \"content\": {\n" +
                                "        \"should_amt\": \"0.0\",\n" +
                                "        \"sign_longitude\": \"87.50754134402843\",\n" +
                                "        \"sign_address\": \"中国新疆维吾尔自治区乌鲁木齐市头屯河区北站东路街道北站东路228号\",\n" +
                                "        \"deviceNo\": \"\",\n" +
                                "        \"sessionId\": \"ab40cfa7-658e-463b-8d12-f9ee3d84bb74\",\n" +
                                "        \"orgId\": \"\",\n" +
                                "        \"pay_way\": \"1\",\n" +
                                "        \"sign_latitude\": \"43.88637592517298\",\n" +
                                "        \"driverId\": \"XJ0985\",\n" +
                                "        \"file_serial_no_signin\": \"portal_tms_0217dfff-c4d2-4795-8423-19d2ff8d4ec1_1\",\n" +
                                "        \"id\": \"240710154145098671\",\n" +
                                "        \"rel_amt\": \"0\",\n" +
                                "        \"order_id\": \"601494045\",\n" +
                                "        \"sign_type\": \"1\"\n" +
                                "    }\n" +
                                "}";
                        StringEntity entity = new StringEntity(json);
                        httpPost.setEntity(entity);
                        httpPost.setHeader("Content-Type", "application/json");
                        httpPost.setHeader("x-appid-header", "sendapp");
                        httpPost.setHeader("x-appversion-header", "1.0.0");
                        httpPost.setHeader("x-deviceid-header", "gmsDevice");
                        httpPost.setHeader("x-guuid-header", "139504b0-af80-4857-97da-fc95ac833002");
                        CloseableHttpClient httpClient = HttpClients.custom().build();
                        HttpResponse response = httpClient.execute(httpPost);
                        StatusLine statusLine = response.getStatusLine();
                        System.out.println("Response Status: " + statusLine.getStatusCode());
                        HttpEntity responseEntity = response.getEntity();
                        String responseBody = EntityUtils.toString(responseEntity);
                        System.out.println("Response Body: " + responseBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                    return null;
                }
            }));
        }
        countDownLatch.await();
        executorService.shutdown();





    }
}
