package com.dy.test.autoTest;


import com.yd.utils.mq.YdProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.dy.test.pojo.TMS_job_cust_base;
import com.dy.test.pojo.TMS_job_order;
import com.dy.test.pojo.TMS_job_poten_cust;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ProducerTest implements ApplicationContextAware{
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	@Test
	public  void test() throws Exception {
		
		
		TMS_job_cust_base bean=new TMS_job_cust_base();
		YdProducer produer=new YdProducer(bean.getClass().getSimpleName().toUpperCase());

		bean.setCustomer_id("CUS2018111305142");
		bean.setCust_mobile("13745241234");
		bean.setCustomer_name("测试发货233443");
		bean.setResp_org_id("444");
		bean.setCreator("root");
		for(int i=0;i<5;i++){
			bean.setCustomer_id("log"+i);
			produer.sendMessage(bean);
		}

		
		
		
		//约车客户
		/*TMS_job_poten_cust potenCust=new TMS_job_poten_cust();
		produer=new YdProducer(potenCust.getClass().getSimpleName().toUpperCase());
		potenCust.setSend_mobile("13745241233");
		produer.sendMessage(potenCust);
		
		
		
		TMS_job_order message=new TMS_job_order();
		produer=new YdProducer(message.getClass().getSimpleName().toUpperCase());
		
		
		
		message.setTicket_id("064623232164");
		message.setSend_customer_id("CUS2018111302423");
		message.setRecv_customer_id("CUS201612215399790");
		message.setRecv_prov_code(320000);
		message.setRecv_city_code(320100);
		message.setRecv_area_code(320114);
		message.setRecv_address("近郊铁心桥龙西路310号");
		message.setTicket_org_id("2099");
		message.setBusiness_org_id("2100");
		message.setRecv_mobile("13701773682");
		message.setSend_mobile("13724213424");
		message.setBusiness_org_id("2242");
		message.setSend_org_id("474");
		message.setRecv_cust_name("测试发货");
		message.setSend_prov_code(320000);
		message.setSend_city_code(320100);
		message.setSend_area_code(320114);
		message.setSend_address("近郊铁心桥龙西路310号");
		message.setTotal_fee(1524);
		message.setRecv_addr_remark("");
		
		
		System.out.println(message);
		produer.sendMessage(message);*/
		
		
		/*
		
		Date parseDate = DateUtils.parseDate("2018-08-25", "yyyy-MM-dd");
		BMS_REPAY_BILLQUEUE message=new BMS_REPAY_BILLQUEUE();
		message.setCustomer_id("CUS201801135254946");
		message.setRepay_amount(60.0);
		message.setRepay_date(new Timestamp(parseDate.getTime()));
		message.setRepay_type("owe");
		produer.send(message);*/

		
	}	

	public static void main(String[] args) {
		TMS_job_order message=new TMS_job_order();
		System.out.println(message.getClass().getSimpleName());
	}
}
