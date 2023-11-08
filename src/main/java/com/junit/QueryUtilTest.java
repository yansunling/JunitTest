package com.junit;

import com.alibaba.fastjson.JSON;
import com.junit.po.QUERY_base_registerPO;
import com.junit.po.Query_base_columnsVO;
import com.yd.common.data.CIPReqCondition;
import com.yd.common.runtime.CIPRuntimeOperator;
import com.yd.query.util.QueryUtil;
import com.yd.query.util.QueryVueUtil;
import com.yd.query.vo.QueryBean;
import com.yd.query.vo.QueryLogAddBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class QueryUtilTest implements ApplicationContextAware{
	
	
	
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	
	
	@Test
	public  void test() throws Exception {
		
		/*QueryBean queryBean=new QueryBean("query_base_where_list1", "V1.0.0");
		queryBean.setPage(1);
		queryBean.setRows(15);
		List<CIPReqCondition> seachCondition = new ArrayList<CIPReqCondition>();
		CIPReqCondition condition=new CIPReqCondition();
		condition.setFieldName("query_id");
		condition.setOperator(0);
		condition.setLowValue("query_base_columns_list");


		QUERY_base_columnsPO po=new QUERY_base_columnsPO();

		po.setQuery_id("query_base_where_list1");*/


		//分页查询
		/*QueryBean queryBean=new QueryBean("query_base_where_list", "V1.0.0");
		queryBean.setPage(1);
		queryBean.setRows(10);
		queryBean.setTimeOut(90000);
		CIPRuntimeOperator user=new CIPRuntimeOperator();
		user.setSubject_id("33");
		user.setSubject_org("11");
		queryBean.setUser(user);
		List<CIPReqCondition> seachCondition = new ArrayList<CIPReqCondition>();

		CIPReqCondition condition=new CIPReqCondition();
		condition.setFieldName("customer_name");
		condition.setOperator(8);
		condition.setLowValue("李%");
		seachCondition.add(condition);





		CIPResponseQueryMsg sendSearchPage = QueryUtil.sendSearchPage(queryBean, seachCondition);
		List rows = (List)sendSearchPage.rows;
		System.out.println(rows.size());
		System.out.println(sendSearchPage.rows.toString());
		System.out.println(sendSearchPage.total);*/



//		QueryBean queryBean=new QueryBean("mdms_monit_logger", "V1.0.0");


		QueryBean queryBean=new QueryBean("query_base_register_list", "V1.0.0");
		Query_base_columnsVO columnVO=new Query_base_columnsVO();
//		columnVO.setMonit_object_1("2532906");
		QueryUtil queryUtil=new QueryUtil();
		queryUtil.setHOST_ADDRESS("http://localhost:8082");
		queryBean.setPage(1);
		queryBean.setRows(15);
		List<QUERY_base_registerPO> list = QueryUtil.sendSearch(queryBean, columnVO,QUERY_base_registerPO.class);
		System.out.println(list.size());
		System.out.println(JSON.toJSONString(list));







//		CIPResponseQueryMsg cipResponseQueryMsg = QueryUtil.sendSearchPage(queryBean, seachCondition);
//		System.out.println(cipResponseQueryMsg.total);

//		List<QUERY_base_columnsPO> query_base_columnsPOS = QueryUtil.sendSearch(queryBean, po, QUERY_base_columnsPO.class);
//		System.out.println(query_base_columnsPOS.size());

	}	
	

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		List<CIPReqCondition> seachCondition = new ArrayList<CIPReqCondition>();
		CIPReqCondition condition=new CIPReqCondition();
		condition.setFieldName("query_id");
		condition.setOperator(0);
		condition.setLowValue("query_base_columns_list");
		seachCondition.add(condition);
		CIPRuntimeOperator user=new CIPRuntimeOperator();
		user.setSubject_id("33");
		user.setSubject_org("33333df");

		QueryLogAddBean addBean=new QueryLogAddBean("query_base_columns_list", "V1.0.0",user,seachCondition);
		QueryUtil util=new QueryUtil();
		util.setHOST_ADDRESS("https://testkp.zjdywl.com");
		String logId = util.insertLog(addBean);
		System.out.println(logId);
		
		
		//不分页查询
		/*QueryBean queryBean=new QueryBean("query_base_columns_list", "V1.0.0");
		Query_base_columnsVO seachCondition=new Query_base_columnsVO();
		seachCondition.setQuery_id("cost_operate_settle_income_month_jump");
		seachCondition.setCreate_time_f(new Timestamp(System.currentTimeMillis()));
		List<QUERY_base_columnsPO> list = QueryUtil.sendSearch(queryBean, seachCondition,QUERY_base_columnsPO.class);
		System.out.println(list.size());*/
		
//		QueryBean queryBean=new QueryBean("query_base_columns_list", "V1.0.0");
//		EsReturnData data=new EsReturnData();
//		data.setOrder_close_type("wait");
//		data.setOrder_ids("01085517,00804216");
//		data.setTicket_time_bg("2019-02-01 12:00:00");
//		queryBean.setPage(1);
//		queryBean.setRows(15);
//		List<EsReturnData> list = QueryUtil.sendSearch(queryBean, data,EsReturnData.class);
//		System.out.println(list.size());
//		for(EsReturnData po:list) {
//			System.out.println(po);
//		}
		
		//分页查询
		/*QueryBean queryBean=new QueryBean("cost_trans_doc_status", "V1.0.0");
		queryBean.setPage(1);
		queryBean.setRows(15);
		List<CIPReqCondition> seachCondition = new ArrayList<CIPReqCondition>();
		
		CIPReqCondition condition=new CIPReqCondition();
		condition.setFieldName("trans_doc_id");
		condition.setOperator(0);
		condition.setLowValue("19071900034");
		seachCondition.add(condition);

		QueryUtil queryUtil=new QueryUtil();
		queryUtil.setHOST_ADDRESS("https://uatkp.zjdywl.com");

		CIPResponseQueryMsg sendSearchPage = queryUtil.sendSearchPage(queryBean, seachCondition);
		List rows = (List)sendSearchPage.rows;
		System.out.println(rows.size());
		System.out.println(sendSearchPage.rows.toString());
		System.out.println(sendSearchPage.total);*/
		
		
//		String url="http://bszhdev.zjdywl.com/query/out/query_api/doGenericSearch.do?actionId=query_doGenericSearch&query_id=query_smp_api_app_danger_log&ssid=null&actionId=query_doGenericSearch&query_id=query_smp_api_app_danger_log&ssid=null&search_condition=[{\"fieldName\":\"check_time\",\"operator\":6,\"lowValue\":\"2019-02-16 00:00:00\",\"highValue\":\"2019-02-18 23:59:59\"}]&page=1&rows=10";
		
		/*String url="http://localhost/query-api/out/query_api/seachPost.do?actionId=query_seachPost";
		
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("query_id", "query_smp_api_app_danger_log");
		params.put("page", "1");
		params.put("rows", "10");
		CIPReqCondition cip=new CIPReqCondition();
		cip.setOperator(6);
		cip.setLowValue("2019-02-16 00:00:00");
		cip.setHighValue("2019-02-18 23:59:59");
		cip.setFieldName("check_time");
		
		List<CIPReqCondition> list=new ArrayList<CIPReqCondition>();
		list.add(cip);
		params.put("search_condtition", list);
		
		HttpUtils.postJSON(url, params);*/
		
		
		/*List<String> values=new ArrayList<String>();
		values.add("2");
		values.add("5");
		values.add("755");
		
		String join = StringUtils.join(values.toArray(),"','");
		
		System.out.println(join);*/
		
		
		
		
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
