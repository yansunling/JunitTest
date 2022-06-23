package com.dy.test.autoTest;

import com.alibaba.fastjson.JSONObject;

import com.dy.test.autoTest.po.MDM_crm_base_customer_unionPO;
import com.yd.query.util.QueryVueUtil;
import com.yd.query.vo.QueryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class QueryVueUtilTest implements ApplicationContextAware{
	
	
	
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	
	
	@Test
	public  void test() throws Exception {

		QueryBean queryBean=new QueryBean("mdm_crm_base_customer_union_list", "V1.0.0");
		queryBean.setPage(1);
		queryBean.setRows(15);
		MDM_crm_base_customer_unionPO unionPO=new MDM_crm_base_customer_unionPO();
		unionPO.setCustomer_name("客户");
		List<MDM_crm_base_customer_unionPO> resultData = QueryVueUtil.sendSearch(queryBean, unionPO, MDM_crm_base_customer_unionPO.class);
		System.out.println(JSONObject.toJSONString(resultData));

	}


	
	

}
