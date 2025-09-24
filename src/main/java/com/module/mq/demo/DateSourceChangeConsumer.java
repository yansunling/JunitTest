package com.module.mq.demo;


import com.rabbitmq.client.*;

import java.io.IOException;

public class DateSourceChangeConsumer {
    public static void main(String[] args) throws Exception{
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("tlwl-uat-rabbitmq.k8s.local");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("rabbit");
        connectionFactory.setPassword("123456");


        String QUEUE_NAME = "consumer_info1";
        String EXCHANGE_NAME = "DSB.QUEUE.EXCHANGER";

        //2 获取Connection
        Connection connection = connectionFactory.newConnection();
        /* 2.创建通道 */
        Channel channel = connection.createChannel();
        /* 3.消费者关联队列 */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /* 4.消费者绑定交换机 参数1 队列 参数2交换机 参数3 routingKey */
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "6510549174325248.hcm.hcm_emp_ent");
//        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("消费者获取生产者消息:" + msg);
            }
        };
        /* 5.消费者监听队列消息 */
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
