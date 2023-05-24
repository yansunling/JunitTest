package com.dy.autoTest;


import com.alibaba.fastjson.JSONObject;
import com.dy.test.pojo.TMS_job_cust_base;
import com.dy.test.pojo.TMS_job_order;
import com.javaBase.thread.JobThreadExecutor;
import com.rabbitmq.client.*;
import com.yd.utils.mq.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CustomerTest implements ApplicationContextAware{
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;

	}
	@Test
	public  void test() throws Exception {


		TMS_job_cust_base bean=new TMS_job_cust_base();
		Class domainClass = bean.getClass();


		Connection connection = MqConnectionFactory.getInsatance().getFactory().newConnection();
		//创建通道
		Channel channel = connection.createChannel();


		//获取一个临时队列
		String queueName = domainClass.getSimpleName().toUpperCase();
		//队列与交换机绑定（参数为：队列名称；交换机名称；routingKey忽略）
		channel.queueDeclare(queueName,true, false, false, null);


		//这里重写了DefaultConsumer的handleDelivery方法，因为发送的时候对消息进行了getByte()，在这里要重新组装成String
		Consumer consumer = new DefaultConsumer(channel) {
			Logger logger= LoggerFactory.getLogger(DefaultConsumer.class);
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body,"UTF-8");
				JSONObject jsonObject = JSONObject.parseObject(message);
				String customer_id = jsonObject.getString("customer_id");
				JobThreadExecutor.contextHolder.set("E:/"+customer_id+".log");
				logger.info(message);
				logger.info("fileName:"+"E:/"+customer_id+".log");
			}
		};
		//声明队列中被消费掉的消息（参数为：队列名称；消息是否自动确认;consumer主体）
		channel.basicConsume(queueName,true,consumer);
		int i=1;
		while(true){
			Thread.sleep(1000L);
			System.out.println("----wait---"+i+"s");
			i++;
		}

		
	}







	public static void main(String[] args) {
		TMS_job_order message=new TMS_job_order();
		System.out.println(message.getClass().getSimpleName());
	}
}
