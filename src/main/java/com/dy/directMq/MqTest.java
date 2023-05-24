package com.dy.directMq;


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
public class MqTest implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }

    @Autowired
    private DirectMqProducer mqProducer;


    @Test
    public void test() throws Exception{
        TASK_JOB_INFO info=new TASK_JOB_INFO();
        info.setBeanName("12345678");
        info.setSystemId("task-input");
//        mqProducer.sendMessage(info);

        info.setSystemId("task5");
        info.setBeanName("testt");

        for(int i=0;i<100;i++){
            info.setBeanName("test"+i);
            mqProducer.sendMessage(info);
        }



//        Thread.sleep(1000000L);

    }




}
