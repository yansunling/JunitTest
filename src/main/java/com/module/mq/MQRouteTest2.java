package com.module.mq;


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
public class MQRouteTest2 implements ApplicationContextAware{
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	@Autowired
	private JobBeanPostProcessor producer;
	
	private String EXCHANGE="QUERY_FANOUT_DATASOURCE";
	@Test
	public  void test() throws Exception {
		TASK_JOB_INFO info=new TASK_JOB_INFO();
		info.setJobDesc("tests");

//		producer.send(info,"register");

		Thread.sleep(10000000L);


	}	


}
