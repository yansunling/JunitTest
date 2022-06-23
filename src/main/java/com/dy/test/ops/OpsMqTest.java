package com.dy.test.ops;


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
public class OpsMqTest implements ApplicationContextAware{
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	@Autowired
	private OPS_meta_from_ops_result_producer producer;
	@Autowired
	private MqOpsProducer opsProducer;

	@Test
	public  void test() throws Exception {

		com.dy.test.pojo.OPS_ADD_OR_UPDATE_APP app=new com.dy.test.pojo.OPS_ADD_OR_UPDATE_APP();
		app.setApp_code("test");
		opsProducer.send(app);

		Thread.sleep(100000L);

	}	


}
