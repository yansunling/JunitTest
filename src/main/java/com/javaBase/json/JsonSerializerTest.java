package com.javaBase.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializerTest {
    public static void main(String[] args) throws Exception{
        User user = new User("Tom", 18);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        System.out.println(json);
    }
}
