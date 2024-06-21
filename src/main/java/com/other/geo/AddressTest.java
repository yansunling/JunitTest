package com.other.geo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddressTest {
    public static void main(String[] args) throws Exception{
        String address = "北京市朝阳区";
        String url = "https://restapi.amap.com/v3/geocode/geo?address=" + URLEncoder.encode(address, "UTF-8") + "&output=JSON&key=3ba5fc965afe451b68a951e587416396";
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        String jsonResponse = response.toString();
        JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
        JSONArray geocodes = jsonObject.getJSONArray("geocodes");
        if (geocodes.size() > 0) {
            JSONObject geocode = geocodes.getJSONObject(0);
            String adcode = geocode.getString("adcode");
            System.out.println("省市区编码：" + adcode);
        }


    }
}
