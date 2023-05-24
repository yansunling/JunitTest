package com.dy.mqdy;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MqDyProduerTest implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
    @Autowired
    private MqDyProducer mqDyProducer;

    @Test
    public void test() throws Exception{

        MQ_DY_INFO info=new MQ_DY_INFO();
        info.setBeanName("111");
        mqDyProducer.send(info);
        Thread.sleep(1000L);

    }




}
