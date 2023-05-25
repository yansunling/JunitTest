package com.module.mq.directMq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MqClearTest implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }


    @Test
    public void test() throws Exception{
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setPassword("guest");
        connectionFactory.setUsername("guest");




        //2 获取Connection
        Connection connection = connectionFactory.newConnection();
        //3 通过Connection创建一个新的Channel
        Channel channel = connection.createChannel();

        long count = channel.messageCount("DirectMqCustomer");

        System.out.println("---count-----"+count);


        channel.queuePurge("DirectMqCustomer");



        channel.close();
        connection.close();

        Thread.sleep(1000000L);



    }




}
