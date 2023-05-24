package com.dy.autoTest;

import com.alibaba.fastjson.JSONObject;
import com.yd.common.utils.HttpUtils;
import com.yd.common.utils.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.util.*;

public class SwaggerApi {
	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args) throws Exception {
		String url="http://127.0.0.1:8081/brs-api/v2/api-docs";
		Map<String, Object> swaggerParse = swaggerParse(url);
		Map<String,List<ParamBean>> paramBeansMap=(Map<String,List<ParamBean>>)swaggerParse.get("vo");
		Map<String,ApiBean> apiMap=(Map<String,ApiBean>)swaggerParse.get("api");
		
		
		Set<String> keys = paramBeansMap.keySet();
		String filePath="E:/autoTest/";
		new File(filePath).mkdirs();
		for(String key:keys) {
//			if(!StringUtils.equals(key, "BRS_base_orgVO")) {
//				continue;
//			}
			
			StringBuffer sb=new StringBuffer();
			List<ParamBean> list = paramBeansMap.get(key);
			sb.append("package autoTest;\n\n").append("import java.sql.Timestamp;\n\n");
			sb.append("public class ").append(key).append(" {\n");
			for(ParamBean bean:list) {
				sb.append("\t/**\n");
				sb.append("\t* ").append(bean.getDescription()).append("\n");
				sb.append("\t*/\n").append("\tprivate ").append(bean.getType()+" ").append(bean.getName()+";\n");
				sb.append("\t/**\n").append("\t* ").append(bean.getDescription()).append("\n\t*/\n");
				sb.append("\tpublic ").append(bean.getType()+" get").append(StringUtils.upperFirst(bean.getName())).append("() {\n");
				sb.append("\t\t").append("return ").append(bean.getName()).append(";\n\t}\n");
				sb.append("\t/**\n").append("\t* ").append(bean.getDescription()).append("\n\t*/\n");
				sb.append("\tpublic void set").append(StringUtils.upperFirst(bean.getName())).append("(");
				sb.append(bean.getType()).append(" ").append(bean.getName()).append(") {\n");
				sb.append("\t\t").append("this.").append(bean.getName()).append("=").append(bean.getName()).append(";\n\t}\n");
				
			}
			sb.append("}");
			FileUtils.createJavaFile(filePath+key+".java", sb.toString());
//			System.out.println(sb.toString());
		}
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  Map<String,Object> swaggerParse(String url) throws Exception{
		String responseData = HttpUtils.get(url);
		responseData=responseData.replaceAll("\\$", "");
		Map map = JSONObject.parseObject(responseData,Map.class);
		Map pathsMap = (Map)map.get("paths");
		Set<String> paths = pathsMap.keySet();
		Map<String,ApiBean> apiBeans=new HashMap<String,ApiBean>();
		ObjectMapper mapper = new ObjectMapper();
		for(String key:paths) {
			ApiBean bean=new ApiBean();
			bean.setApiUrl(key);
			JsonNode jsonNode = mapper.readTree(JSONObject.toJSONString(pathsMap.get(key)));
			JsonNode response = jsonNode.findValue("200");
			JsonNode parameters = jsonNode.findValue("parameters");
			JsonNode summary = jsonNode.findValue("summary");
			if (response != null) {
				JsonNode childRef = response.findValue("ref");
				if(childRef!=null) {
					String responseBean = childRef.getTextValue().replace("#/definitions/", "");
					bean.setResponseBean(responseBean);
				}
			}
			//缺少多个参数类型的解析,暂只解析一个参数
			if (parameters != null) {
				JsonNode chilParam = parameters.findValue("ref");
				if(chilParam!=null) {
					String responseBean = chilParam.getTextValue().replace("#/definitions/", "");
					bean.setParamBean(responseBean);
				}
			}
			if (summary != null) {
				bean.setApiName(summary.getTextValue());
			}
			apiBeans.put(key, bean);
		}
		Map vos = (Map)map.get("definitions");
		Set<String> keys = vos.keySet();
		Map<String,List<ParamBean>> paramBeansMap=new HashMap<String,List<ParamBean>>();
		
		List<String> baseType=new ArrayList<String>();
		baseType.add("int");
		baseType.add("byte");
		baseType.add("float");
		baseType.add("char");
		
		
		
		for(String key:keys) {
			if(StringUtils.equals("Timestamp", key)) {
				continue;
			}
			Map object = (Map)vos.get(key);
			Map properties = (Map)object.get("properties");
			Set<String> keyPros = properties.keySet();
			List<ParamBean> paramBean=new ArrayList<ParamBean>();
			for(String keyPro:keyPros) {
				Map object2 = (Map)properties.get(keyPro);
				String type="";
				String description="";
				if(object2.get("type")!=null) {
					type=String.valueOf(object2.get("type"));
				}else {
					String ref = String.valueOf(object2.get("ref"));
					ref=ref.replace("#/definitions/", "");
					type=String.valueOf(ref);
				}
				if(object2.get("description")!=null) {
					description=String.valueOf(object2.get("description"));
				}
				if(!baseType.contains(type)) {
					type=StringUtils.upperFirst(type);
				}
				
				ParamBean bean=new ParamBean(type, keyPro,description);
				paramBean.add(bean);
			}
			paramBeansMap.put(key, paramBean);
		}
		Map<String,Object> returnData=new HashMap<String,Object>();
		
		returnData.put("api", apiBeans);
		returnData.put("vo", paramBeansMap);
		return returnData;
		
	}
	
	
	
	
	
	
	
}
