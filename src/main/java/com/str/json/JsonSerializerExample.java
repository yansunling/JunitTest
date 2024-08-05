package com.str.json;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.javaBuild.crmx.data.CrmxStoreCustomerAppVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class JsonSerializerExample implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }

    @Test
    public  void test() throws Exception{
        CrmxStoreCustomerAppVO appVO=new CrmxStoreCustomerAppVO();
        appVO.setOrg_id("350101");
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //去掉空值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        String json = objectMapper.writeValueAsString(appVO);
        System.out.println(json);
    }

}
