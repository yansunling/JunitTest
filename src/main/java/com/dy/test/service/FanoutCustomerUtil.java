package com.dy.test.service;

import com.rabbitmq.client.*;
import com.yd.utils.mq.MqConnectionFactory;

import java.io.IOException;

public abstract class FanoutCustomerUtil {

	private String EXCHANGE="QUERY_FANOUT_DATASOURCE";
	public FanoutCustomerUtil() {
		try {
			  Connection connection = MqConnectionFactory.getInsatance().getFactory().newConnection();
	          //创建通道
	          Channel channel = connection.createChannel();
	          //交换机声明（参数为：交换机名称；交换机类型）
	          channel.exchangeDeclare(EXCHANGE,BuiltinExchangeType.FANOUT);
	          //获取一个临时队列
	          String queueName = channel.queueDeclare().getQueue();
	          //队列与交换机绑定（参数为：队列名称；交换机名称；routingKey忽略）
	          channel.queueBind(queueName,EXCHANGE,"");
	          //这里重写了DefaultConsumer的handleDelivery方法，因为发送的时候对消息进行了getByte()，在这里要重新组装成String
	          Consumer consumer = new DefaultConsumer(channel) {
	              @Override
	              public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	                  super.handleDelivery(consumerTag, envelope, properties, body);
	                  String message = new String(body,"UTF-8");
	                  proccess(message);
	              }
	          };
	          //声明队列中被消费掉的消息（参数为：队列名称；消息是否自动确认;consumer主体）
	          channel.basicConsume(queueName,true,consumer);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public abstract void proccess(String message);
}
