package com.dy.test.autoTest;

import com.alibaba.fastjson.JSONObject;
import com.http.MyQueryUtil;
import com.http.SslUtils;
import com.yd.query.util.QueryUtil;
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
public class QueryBatchTest implements ApplicationContextAware{
	
	
	
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	
	
	@Test
	public  void test() throws Exception {


		SslUtils.ignoreSsl();

		QueryBean queryBean=new QueryBean("query_base_register_list", "V1.0.0");
		Query_base_columnsVO columnVO=new Query_base_columnsVO();
		columnVO.setQuery_id("query_base_where_list");

		queryBean.setCondition(columnVO);
		List<QueryBean> queryBeans=new ArrayList<>();
		queryBeans.add(queryBean);

		QueryBean queryBean1=new QueryBean("query_base_columns_list", "V1.0.0");
		queryBeans.add(queryBean1);
		queryBean1.setCondition(columnVO);

		queryBeans.add(queryBean1);


		Map<String, Object> map = QueryUtil.sendBatchSearch(queryBeans, new HashMap<>());
		System.out.println(JSONObject.toJSONString(map));






	}	

	
	public class EsTrack {
		private String order_id;

		
		
		public EsTrack(String order_id) {
			super();
			this.order_id = order_id;
		}

		public String getOrder_id() {
			return order_id;
		}

		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}

		@Override
		public String toString() {
			return "EsTrack [order_id=" + order_id + "]";
		}
		
		
		
	};
	
	
	

}
