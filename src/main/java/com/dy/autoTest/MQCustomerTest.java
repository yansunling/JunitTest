package com.dy.autoTest;


import com.dy.service.DruidCustomer;
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
public class MQCustomerTest implements ApplicationContextAware{
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	@Autowired
	private DruidCustomer customer;
	
	private String EXCHANGE="QUERY_FANOUT_DATASOURCE";
	@Test
	public  void test() throws Exception {

         /* Connection connection = MqConnectionFactory.getInsatance().getFactory().newConnection();
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
                  System.out.println("class1 customer"+"----"+message);
              }
          };
          //声明队列中被消费掉的消息（参数为：队列名称；消息是否自动确认;consumer主体）
          channel.basicConsume(queueName,true,consumer);*/
          Thread.sleep(100);
	}	


}
