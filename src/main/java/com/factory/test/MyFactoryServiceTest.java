package com.factory.test;


import com.dy.test.mq.MyConsumer;
import com.factory.Cat;
import com.factory.MyFactoryService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.word.asset.CompAssetBaseInfoChangeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MyFactoryServiceTest implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
    @Autowired
    private MyFactoryService myFactoryService;
    @Autowired
    private Cat cat;

    @Test
    public  void test() throws Exception{
        CompAssetBaseInfoChangeVO changeVO=new CompAssetBaseInfoChangeVO();
        changeVO.setAsset_company("33");
        //cat.catEat(changeVO);
        cat.eat("22");

    }


}
