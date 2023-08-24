package com.mock;

import com.alibaba.fastjson.JSONObject;
import com.github.jsonzou.jmockdata.JMockData;
import com.mock.data.CRMX_relation_visitCust_visitVO;

public class BuildMock {
    public static void main(String[] args) {
        CRMX_relation_visitCust_visitVO mock = JMockData.mock(CRMX_relation_visitCust_visitVO.class);
        System.out.println(JSONObject.toJSONString(mock));
    }
}
