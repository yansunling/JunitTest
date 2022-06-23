package com.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yd.utils.http.MyX509TrustManager;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class HttpUtils {

    private static Logger logger= LoggerFactory.getLogger(HttpUtils.class);
    private static int CONNECT_TIMEOUT_VALUE = 20000;//请求(连接)超时:20秒
    private static int READ_TIMEOUT_VALUE = 30000;//响应超时:30秒,一定要设置，否则可能导致当前线程一直处于等待状态

    public static String getUrl(String url){
        String result = null;
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);
            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(READ_TIMEOUT_VALUE)
                    .setConnectTimeout(CONNECT_TIMEOUT_VALUE)
                    .build();
            request.setConfig(requestConfig);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl,
                                         String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        TrustManager[] tm = { new MyX509TrustManager() };
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url
                    .openConnection();
            urlConnection.setSSLSocketFactory(ssf);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT_VALUE);
            urlConnection.setReadTimeout(READ_TIMEOUT_VALUE);

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)){
                urlConnection.connect();
            }

            if (null != outputStr) {
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream,
                    "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String str = null;
            if ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            reader.close();
            inputStream.close();
            inputStream = null;

            urlConnection.disconnect();

            jsonObject = JSON.parseObject(buffer.toString());

        } catch (NoSuchAlgorithmException e) {
            logger.error("[HttpUtils.httpRequest]:httpRequest occured an NoSuchAlgorithmException,NoSuchAlgorithmException:"
                    + e);
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            logger.error("[HttpUtils.httpRequest]:httpRequest occured an NoSuchProviderException,NoSuchProviderException:"
                    + e);
            e.printStackTrace();
        } catch (KeyManagementException e) {
            logger.error("[HttpUtils.httpRequest]:httpRequest occured an KeyManagementException,KeyManagementException:"
                    + e);
            e.printStackTrace();
        } catch (MalformedURLException e) {
            logger.error("[HttpUtils.httpRequest]:httpRequest occured an MalformedURLException,MalformedURLException:"
                    + e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("[HttpUtils.httpRequest]:httpRequest occured an IOException,IOException:"
                    + e);
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String httpRequestStr(String requestUrl, String requestMethod, String outputStr) {
        logger.info("[HttpUtils.httpRequest]:start httpRequest.");
        StringBuffer buffer = new StringBuffer();
        TrustManager[] tm = { new MyX509TrustManager() };
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(ssf);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT_VALUE);
            urlConnection.setReadTimeout(READ_TIMEOUT_VALUE);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod(requestMethod);


            urlConnection.connect();

            if (null != outputStr) {
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String str = null;

            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            reader.close();
            inputStream.close();
            inputStream = null;

            urlConnection.disconnect();

        } catch (NoSuchAlgorithmException e) {
            logger.error(
                    "[HttpUtils.httpRequest]:httpRequest occured an NoSuchAlgorithmException,NoSuchAlgorithmException:"
                            + e);
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            logger.error(
                    "[HttpUtils.httpRequest]:httpRequest occured an NoSuchProviderException,NoSuchProviderException:"
                            + e);
            e.printStackTrace();
        } catch (KeyManagementException e) {
            logger.error(
                    "[HttpUtils.httpRequest]:httpRequest occured an KeyManagementException,KeyManagementException:"
                            + e);
            e.printStackTrace();
        } catch (MalformedURLException e) {
            logger.error(
                    "[HttpUtils.httpRequest]:httpRequest occured an MalformedURLException,MalformedURLException:" + e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("[HttpUtils.httpRequest]:httpRequest occured an IOException,IOException:" + e);
            e.printStackTrace();
        }
        logger.info("[HttpUtils.httpRequest]:end httpRequest.");
        return buffer.toString();
    }
}
