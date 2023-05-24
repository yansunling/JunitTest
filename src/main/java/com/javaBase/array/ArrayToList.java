package com.javaBase.array;

import com.redis.RedisUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayToList {
    public static void main(String[] args) {
        String[] strs={"2","3","3"};
        List<String> collect = Arrays.stream(strs).collect(Collectors.toList());
        collect.add("33");
//        System.out.println(JSONObject.toJSONString(collect));


        String[] strings = collect.toArray(new String[0]);
//        System.out.println(JSONObject.toJSONString(strings));

        String key="dd333";
//        RedisUtil.putWithStringKey("dd333",10,300);

//        Long dd = RedisUtil.delByStr("dd");


        Long aLong = RedisUtil.decrBy(key);
        System.out.println(aLong);


    }
}
