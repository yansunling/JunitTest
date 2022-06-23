package com.dy.test.mq.demo;


import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DemoProducer {
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

        String EXCHANGE_NAME = "my_fanout_exchange";
        String routingKey = "dlx.save";
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);


        String QUEUE_NAME = "consumer_info";
        /* 3.消费者关联队列 */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

//        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
//        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");


        /** 4.发送消息 */
        String message = "",sendType="";
        for (int i = 0; i < 10; i++)
        {
            if(i%2==0){
                sendType = "info";
                message = "我是 info 级别的消息类型：" + i;
            }else{
                sendType = "error";
                message = "我是 error 级别的消息类型：" + i;
            }
            System.out.println("[send]：" + message + "  " +sendType);
            channel.basicPublish(EXCHANGE_NAME, sendType, null, message.getBytes("utf-8"));
            try {
                Thread.sleep(5 * i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /** 5.关闭通道、连接 */
        channel.close();
        connection.close();
        /** 注意：如果消费没有绑定交换机和队列，则消息会丢失 */
//        Thread.sleep(100000L);
    }
}
