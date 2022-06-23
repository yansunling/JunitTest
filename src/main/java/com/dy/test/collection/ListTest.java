package com.dy.test.collection;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>();

        list.add("344");
        list.add("344454");

        list.add(0,"test");

        System.out.println(JSONObject.toJSONString(list));

    }
}
