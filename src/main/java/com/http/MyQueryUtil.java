package com.http;

import com.alibaba.fastjson.JSONObject;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.yd.common.data.CIPReqCondition;
import com.yd.common.data.CIPResponseQueryMsg;
import com.yd.common.runtime.CIPRuntimeOperator;
import com.yd.query.auto.GlobalErrorInfoEnum;
import com.yd.query.util.QueryUtil;
import com.yd.query.vo.CIPResponseMsg;
import com.yd.query.vo.QueryBean;
import com.yd.query.vo.QueryLogAddBean;
import com.yd.query.vo.QueryRequestLogBean;
import com.yd.utils.common.HttpUtils;
import com.yd.utils.common.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FilenameFilter;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyQueryUtil {
	private static Logger logger = LoggerFactory.getLogger(QueryUtil.class);
	private static String HOST_ADDRESS;
	private static String CONDITION="condition";
	private static String SEARCH_CONDITION="search_condtition";
	private static String USER="user";
	private static String USER_ID="user_id";
	private static String ORG_ID="org_id";
	private static String QUERY_ID="query_id";
	private static String VERSION="version";
	private static String PAGE="page";
	private static String ROWS="rows";
	private static String ID="id";
	private static String SYS_ID="sys_id";
	private static String SYSTEM_ID;

	@Value("#{properties['query.host']}")
	public  void setHOST_ADDRESS(String hOST_ADDRESS) {
		HOST_ADDRESS = hOST_ADDRESS;
	}
	@Value("#{properties['cip.system.id']}")
	public  void setSYSTEM_ID(String sYSTEM_ID) {
		SYSTEM_ID = sYSTEM_ID;
	}






	public static <T> List<T>  sendSearch(QueryBean queryBean, Object seachCondition, Class<T> type)throws  Exception{
		String name =type.getName();
		if(StringUtils.equals(name, "java.util.List")){
			throw new GlobalErrorInfoException(GlobalErrorInfoEnum.QUERY_QUERY__06101001);
		}
		String url=HOST_ADDRESS+"/query-api/out/query_api/searchData.do?queryId="+queryBean.getQueryId()+"&version="+queryBean.getVersion()+"&sys_id="+SYSTEM_ID;
		CIPResponseMsg msg = null;
		try {
			//将参数设置到Map里
			Map<String,Object> param=new HashMap<>();
			param.put(CONDITION, seachCondition);
			param.put(USER, queryBean.getUser());
			param.put(PAGE, queryBean.getPage());
			param.put(ROWS, queryBean.getRows());
			//发送请求到query-api
			String responseBody = sendQuery(queryBean.getTimeOut(), url, param);
			msg = JSONObject.parseObject(responseBody, CIPResponseMsg.class);
		} catch (Exception e) {
			logger.info("sendSearch error;",e);
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06201001);
		}

		if(msg==null){
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06201001);
		}

		if(msg.getErrorCode()!=0){
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06101005,msg.getMsg());
		}


		try {
			if(msg.getErrorCode()==0&& StringUtils.isNotBlank(msg.getData())){
				return (List<T>) JSONObject.parseArray(msg.getData(), type);
			}
		} catch (Exception e) {
			logger.info("parseArray error;",e);
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06101002,msg.getData());
		}


		return null;
	}

	/**
	 * 批量查询queryId
	 * @param queryBeans
	 * @param publicCondition
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> sendBatchSearch(List<QueryBean> queryBeans, Map<String,Object> publicCondition) throws Exception{
		Map<String,Object> params=new HashMap<>();
		params.put(SYS_ID,SYSTEM_ID);//设置系统变量
		params.put("querys",queryBeans);
		params.put("public_condition",publicCondition);
		String url=HOST_ADDRESS+"/query-api/out/batchSearchData.do";
		try {
			String responseBody = sendQuery(60000, url, params);
			return JSONObject.parseObject(responseBody, Map.class);
		}catch (Exception e) {
			logger.info("sendSearch error;",e);
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06201001);
		}
	}

	/**
	 * 分页查询
	 * @param queryBean 查询对象
	 * @param seachCondition 查询条件对象
	 * @return
	 */
	public static CIPResponseQueryMsg  sendSearchPage(QueryBean queryBean, List<CIPReqCondition> seachCondition)throws  Exception{
		String url=HOST_ADDRESS+"/query-api/out/query_api/seachPost.do";
		CIPResponseQueryMsg msg = null;
		try {
			//将参数设置到Map里
			Map<String,Object> param=new HashMap<String,Object>();
			param.put(QUERY_ID, queryBean.getQueryId());
			param.put(VERSION, queryBean.getVersion());
			param.put(SEARCH_CONDITION, seachCondition);
			param.put(SYS_ID, SYSTEM_ID);
			CIPRuntimeOperator user = queryBean.getUser();
			if(user!=null) {
				param.put(USER_ID, user.getSubject_id());
				param.put(ORG_ID, user.getSubject_org());
			}
			param.put(PAGE, queryBean.getPage());
			param.put(ROWS, queryBean.getRows());
			//发送请求到query-api
			String postJSON = sendQuery(queryBean.getTimeOut(), url, param);
			msg = JSONObject.parseObject(postJSON, CIPResponseQueryMsg.class);
		} catch (Exception e) {
			logger.info("sendSearch error;",e);
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06201001);
		}

		if(msg==null){
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06201001);
		}

		if(msg.errorCode!=0){
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06101005,msg.msg);
		}
		return msg;
	}



	/**
	 * 1新增查询记录
	 * @param logBean 日志对象
	 * @return
	 */
	public static CIPResponseQueryMsg  addRequestLog(QueryRequestLogBean logBean)throws  Exception{
		String url = HOST_ADDRESS + "/query-api/out/query_api/addRequestLog.do";
		CIPResponseQueryMsg msg = null;

		if(StringUtils.isBlank(logBean.getId())) {
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06101003);
		}
		if(StringUtils.isBlank(logBean.getQueryId())) {
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06101004);
		}
		try {
			// 将参数设置到Map里
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(SEARCH_CONDITION, logBean.getSeachCondition());
			param.put(QUERY_ID, logBean.getQueryId());
			param.put(VERSION, logBean.getVersion());
			param.put(ID, logBean.getId());
			CIPRuntimeOperator user = logBean.getUser();
			if (user != null) {
				param.put(USER_ID, user.getSubject_id());
				param.put(ORG_ID, user.getSubject_org());
			}

			String postJSON = HttpUtils.postJSON(url, param);
			msg = JSONObject.parseObject(postJSON, CIPResponseQueryMsg.class);
		} catch (Exception e) {
			logger.info("sendSearch error;", e);
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06201001);
		}

		if (msg == null) {
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06201001);
		}

		if (msg.errorCode != 0) {
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06101005,msg.msg);
		}
		return msg;
	}


	/**
	 * 新增日志
	 * @param logBean
	 * @return 日志流水号
	 * @throws Exception
	 */
	public static String  insertLog(QueryLogAddBean logBean)throws  Exception{
		String url = HOST_ADDRESS + "/query-api/out/query_api/insertLog.do";
		CIPResponseQueryMsg msg = null;
		if(StringUtils.isBlank(logBean.getQueryId())) {
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06101004);
		}
		try {
			// 将参数设置到Map里
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(SEARCH_CONDITION, logBean.getSeachCondition());
			param.put(QUERY_ID, logBean.getQueryId());
			param.put(VERSION, logBean.getVersion());
			CIPRuntimeOperator user = logBean.getUser();
			if (user != null) {
				param.put(USER_ID, user.getSubject_id());
				param.put(ORG_ID, user.getSubject_org());
			}
			String postJSON = HttpUtils.postJSON(url, param);
			msg = JSONObject.parseObject(postJSON, CIPResponseQueryMsg.class);
		} catch (Exception e) {
			logger.info("sendSearch error;", e);
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06201001);
		}

		if (msg == null) {
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06201001);
		}

		if (msg.errorCode != 0) {
			throw new GlobalErrorInfoException (GlobalErrorInfoEnum.QUERY_QUERY__06101005,msg.msg);
		}
		return String.valueOf(msg.data);
	}



	/**
	 * 发送请求到query
	 * @param timeout
	 * @param url
	 * @param param
	 * @return
	 */
	private static String sendQuery(int timeout,String url,Map<String, Object> param){

//		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		HttpComponentsClientHttpRequestFactory httpRequestFactory = generateHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(timeout);
		httpRequestFactory.setReadTimeout(timeout);
		RestTemplate restTemplate=new RestTemplate(httpRequestFactory);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(param, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		return response.getBody();

	}


	public static HttpComponentsClientHttpRequestFactory generateHttpRequestFactory(){
		try {
			TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
			CloseableHttpClient httpClient = httpClientBuilder.build();
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setHttpClient(httpClient);
			return factory;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}






}
