package com.module.mq;


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

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MqSimpleCustomer implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
    @Test
    public  void test() throws Exception{
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


        //指定死信发送的Exchange
        Map<String, Object> agruments = new HashMap<String, Object>();
        agruments.put("x-dead-letter-exchange", "task.mq.death.exchange");

        String queueName="MqSimpleQuence";

        channel.queueDeclare(queueName, false, false, false, agruments);



        //要进行死信队列的声明
//        channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
//        channel.queueDeclare("dlx.queue", true, false, false, null);
//        channel.queueBind("dlx.queue", "dlx.exchange", "deadKey1");

        channel.basicConsume(queueName, false, new MyConsumer(channel));


        Thread.sleep(1000000000L);


    }


}
