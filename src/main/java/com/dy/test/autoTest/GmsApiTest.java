package com.dy.test.autoTest;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GmsApiTest {

    private String test;
    public static void main(String[] args) throws Exception {

        String url="http://localhost/gms-api/authorize/user/searchOpenId";
        Map<String,Object> params=new HashMap<>();
        List<String> user=new ArrayList<>();
        user.add("2625");
        params.put("users",user);
        params.put("appId","mpp");
        String result = HttpUtils.postJSON(url, new HashMap<>(), JSONObject.toJSONString(params));
        System.out.println(result);
    }
}
