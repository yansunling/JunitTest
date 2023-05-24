package com.dy.mq;


import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MqProducer {
    public static void main(String[] args) throws Exception{
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

        String exchange = "test_dlx_exchange";
        String routingKey = "dlx.save";
        channel.exchangeDeclare(exchange, "direct");


        for(int i=0;i<10;i++){
            Map<String,String> map=new HashMap<>();
            map.put("test",i+"");
            String msg = JSONObject.toJSONString(map);
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties("text/plain",
                    null,
                    null,
                    2,
                    0, null, null, null,
                    null, null, null, null,
                    null, UUID.randomUUID().toString());
            //发送消息
            channel.basicPublish(exchange, routingKey, true,  basicProperties, msg.getBytes());
            System.out.println("------------"+msg+"-----------");
        }
        channel.close();
        connection.close();
//        Thread.sleep(100000L);
    }
}
