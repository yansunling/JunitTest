package com.dy.test.autoTest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.dy.test.pojo.TMS_job_order;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MQProducerTest implements ApplicationContextAware{
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	

	
	@Test
	public  void test() throws Exception {
		/* Connection connection = MqConnectionFactory.getInsatance().getFactory().newConnection();
         //创建通道
         Channel channel = connection.createChannel();
         //声明交换机（参数为：交换机名称; 交换机类型，广播模式）
         channel.exchangeDeclare("fanoutLogs", BuiltinExchangeType.FANOUT);
         //消息发布（参数为：交换机名称; routingKey，忽略。在广播模式中，生产者声明交换机的名称和类型即可）
         for (int i = 0; i < 2; i++){
             String msg="生产者-队列-多个消费者" + i;
             channel.basicPublish("fanoutLogs","", null,msg.getBytes());
             System.out.println(msg);
         }*/
       
//		mqUtil.sendMessage("crm");
         
	}	

	public static void main(String[] args) {
		TMS_job_order message=new TMS_job_order();
		System.out.println(message.getClass().getSimpleName());
	}
}
