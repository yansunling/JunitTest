package com.dy.test.autoTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpUtils {
    private static final String DEFAULT_ENCODE = "utf-8";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_URL = "application/x-www-form-urlencoded";
    private static int CONNECT_TIMEOUT_VALUE = 20000;
    private static int SOCKET_TIMEOUT_VALUE = 60000;

    private HttpUtils() {
    }
    
    /**
     * post方式提交，contentType为application/json
     * @param url
     * @param headers
     * @param sendData  提交参数，需要为json字符串
     * @return
     * @throws Exception
     */
    public static String postJSON(String url, Map<String, String> headers, String sendData) throws Exception {
    	return send(url, sendData, CONTENT_TYPE_JSON, DEFAULT_ENCODE, headers);
    }

    /**
     * post方式提交，contentType为application/x-www-form-urlencoded
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws Exception
     */
    public static String postURL(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        return postURL(url, headers, params, DEFAULT_ENCODE);
    }

    /**
     * get方式提交
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        return get(url, headers, params, DEFAULT_ENCODE);
    }

    private static String postURL(String url, Map<String, String> headers, Map<String, String> params, String chaset) throws Exception {
        StringBuilder content = new StringBuilder();
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            Iterator<Entry<String, String>> iterator = entrySet.iterator();

            while(iterator.hasNext()) {
                Entry<String, String> entry = (Entry<String, String>)iterator.next();
                content.append((String)entry.getKey()).append("=").append(URLEncoder.encode((String)entry.getValue(), chaset)).append("&");
            }

            String temp = content.toString();
            temp.substring(0, temp.lastIndexOf("&"));
            content = new StringBuilder(temp);
        }

        return send(url, content.toString(), CONTENT_TYPE_URL, chaset, headers);
    }

    private static String get(String url, Map<String, String> headers, Map<String, String> params, String chaset) throws Exception {
        StringBuilder encodedUrl = new StringBuilder(url);
        if(!url.endsWith("?")){
        	encodedUrl.append("?");
        }
        if(params!= null && !params.isEmpty()){
        	for(Entry<String, String> entry: params.entrySet()){
        		encodedUrl.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), chaset)).append("&");
        	}
        	encodedUrl.deleteCharAt(encodedUrl.length() - 1);
        }

        return send(encodedUrl.toString(), "", CONTENT_TYPE_URL, chaset, headers);
    }

    private static String send(String address, String body, String contentType, String charset, Map<String, String> headers) {
        return send(address, body, contentType, charset, headers, CONNECT_TIMEOUT_VALUE, SOCKET_TIMEOUT_VALUE);
    }

    private static String send(String address, String body, String contentType, String charset, Map<String, String> headers, int connTime, int readTime) {
        StringBuilder sb = new StringBuilder();
        BufferedWriter bw = null;
        BufferedReader br = null;

        try {
            URL url = new URL(address);
            HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setInstanceFollowRedirects(false);
            httpUrlConnection.setRequestProperty("Content-type", contentType + ";charset=" + charset);
            httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.10) Gecko/20100914 Firefox/3.6.10 ");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            for(Entry<String, String> entry: headers.entrySet()){
            	httpUrlConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            httpUrlConnection.setConnectTimeout(connTime);
            httpUrlConnection.setReadTimeout(readTime);
            if (body != null && !"".equals(body)) {
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestMethod("POST");
                bw = new BufferedWriter(new OutputStreamWriter(httpUrlConnection.getOutputStream(), charset));
                bw.write(body);
                bw.close();
            }

            br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), charset));
            String out = null;

            while((out = br.readLine()) != null) {
                sb.append(out);
            }
        } catch (Exception var19) {
            throw new RuntimeException(var19);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }

                if (bw != null) {
                    bw.close();
                }
            } catch (IOException var18) {
                var18.printStackTrace();
            }

        }

        return sb.toString();
    }
}