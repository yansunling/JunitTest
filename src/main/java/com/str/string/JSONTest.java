package com.str.string;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JSONTest {
    public static void main(String[] args) {

        List<String> strings = Lists.newArrayList("1", "2");


        List<String> temp = Lists.newArrayList("1");

        strings.removeAll(temp);
        System.out.println(JSON.toJSONString(strings));


    }
}
