package com.str.random;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RandomDataTest {
    public static void main(String[] args) throws Exception{
        CompAssetBusinessCarPO compAssetBusinessCarPO = RandomDataUtil.generateRandomObject(CompAssetBusinessCarPO.class);


        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //去掉空值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        String json = objectMapper.writeValueAsString(compAssetBusinessCarPO);
        System.out.println(json);
    }
}
