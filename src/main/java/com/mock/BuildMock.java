package com.mock;

import com.alibaba.fastjson.JSONObject;
import com.github.jsonzou.jmockdata.JMockData;
import com.mock.data.CRMX_relation_visitCust_visitVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BuildMock {
    public static void main(String[] args) {
//        CRMX_relation_visitCust_visitVO mock = JMockData.mock(CRMX_relation_visitCust_visitVO.class);
//        System.out.println(JSONObject.toJSONString(mock));


        List<String> allPerfectCustomer = Arrays.asList("1","2","3","4","5","6");



        allPerfectCustomer.stream().forEach(item->{
            System.out.println(item);
        });


    }
}
