package com.word.createWord;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.javaBuild.tmsp.WordCreateTmspByClass;
import com.word.doc.GeneralTemplateTool;
import com.word.createWord.query.QueryNewColumnsData;
import com.word.createWord.query.QueryNewRegisterData;
import com.word.createWord.query.QueryNewWhereParamData;
import com.word.doc.POIMergeDocUtil;
import com.yd.query.util.QueryVueUtil;
import com.yd.query.vo.QueryBean;

import java.util.*;

public class WordCreateByQuery {



    public static void main(String[] args) throws Exception{

        String path = WordCreateTmspByClass.class.getClassLoader().getResource("").getPath();
        String filePath=path+"api";
        //模板路径
        String templatePath=filePath+"/template_query.docx";
        //doc文档生成工具
        GeneralTemplateTool gtt = new GeneralTemplateTool();

        QueryVueUtil queryUtil=new QueryVueUtil();
        queryUtil.setHOST_ADDRESS("https://tlwl.uat.tuolong56.com");

        List<String> queryList = Arrays.asList("tmsp_quality_error_info_file","tmsp_quality_error_info_list","tmsp_quality_error_log_list","tmsp_quality_error_log_report","tmsp_quality_error_subtype_select","tmsp_quality_error_type_select","tmsp_quality_order_except","tmsp_quality_order_list","tmsp_quality_penalty_reward_list","tmsp_quality_penalty_reward_report","tmsp_quality_penalty_reward_select");

        List<String> fileList=new ArrayList<>();
        for (String  queryId : queryList) {

            String url="https://tlwl.uat.tuolong56.com/query/auth/query_new_search/searchTotalData/V1.0.0/"+queryId+".do";
            //......对应模板扩展
            //创建替代模板里段落中如${title}值结束
            Map<String, Object> params = new HashMap<>();
            params.put("url",url);
            //获得查询名称
            QueryNewRegisterData registerData=new QueryNewRegisterData(queryId);
            QueryBean queryBean=new QueryBean(registerData.SEARCH_QUERY_ID, registerData.VERSION);
            List<QueryNewRegisterData> resultData = queryUtil.sendSearch(queryBean, registerData, QueryNewRegisterData.class);
            String desc=resultData.get(0).getQuery_name();
            params.put("name",desc);
            //查询条件
            QueryNewWhereParamData paramData=new QueryNewWhereParamData(queryId);
            QueryBean paramQueryBean=new QueryBean(paramData.SEARCH_QUERY_ID, paramData.VERSION);
            List<QueryNewWhereParamData> paramList = queryUtil.sendSearch(paramQueryBean, paramData, QueryNewWhereParamData.class);

            //查询字段
            QueryNewColumnsData columnsData=new QueryNewColumnsData(queryId);
            QueryBean columnBean=new QueryBean(columnsData.SEARCH_QUERY_ID, columnsData.VERSION);
            List<QueryNewColumnsData> columnsList = queryUtil.sendSearch(columnBean, paramData, QueryNewColumnsData.class);



            //创建替代&生成模板里tab1标识的表格中的值开始
            List<Map<String,String>> tab1list = new ArrayList<>();
            Map<String,String> condition=new HashMap<>();
            for (QueryNewWhereParamData bean:paramList) {
                Map<String, String> map = new HashMap<>();
                map.put("param", bean.getParam_id());
                map.put("type", "String");
                map.put("msg", bean.getWhere_name());
                tab1list.add(map);
                condition.put(bean.getParam_id(),bean.getWhere_name());
            }
            params.put("tab1", tab1list);
            Map<String,Object> requestJson=new HashMap<>();
            requestJson.put("page",1);
            requestJson.put("rows",15);
            requestJson.put("condition",condition);
            String pretty = JSON.toJSONString(requestJson, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
            pretty=pretty.replaceAll("\n","\r");
            pretty=pretty.replaceAll("null","\"\"");
            params.put("request_json", pretty);
            Map<String,Object> responseJson=new HashMap<>();
            responseJson.put("errorCode",0);
            responseJson.put("msg","操作成功");
            Map<String,String> result=new HashMap<>();
            List<Map<String,String>> responseData=new ArrayList();
            responseData.add(result);
            responseJson.put("data",responseData);
            //查询结果
            List<Map<String,String>> tab2list = new ArrayList<>();
            for (QueryNewColumnsData bean:columnsList) {
                Map<String, String> map = new HashMap<>();
                map.put("resp_param", bean.getUi_column_id());
                map.put("resp_type", "String");
                map.put("resp_msg", bean.getUi_column_name());
                tab2list.add(map);
                result.put(bean.getUi_column_id(),bean.getUi_column_name());
            }
            params.put("tab2", tab2list);
            pretty = JSON.toJSONString(responseJson, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
            pretty=pretty.replaceAll("\n","\r");
            params.put("response_json", pretty);

            String outFile =  "C:/Users/yansunling/Desktop/api/"+desc+".docx";
            gtt.templateWrite(templatePath, outFile, params);
            fileList.add(outFile);
        }
        String[] file =fileList.toArray(new String[0]);
        String  apiDoc="C:/Users/yansunling/Desktop/api/查询接口.docx";
        POIMergeDocUtil.mergeDoc(file,apiDoc);

    }



    public static void closeWps() throws Exception{
        Runtime run =Runtime.getRuntime();
        Process p = run.exec("taskkill /f /im wps.exe");
        p.waitFor();
    }

    public static void openWps() throws Exception{
        Runtime run =Runtime.getRuntime();

//        Process p = run.exec("cmd /C start C:/Users/admin/Desktop/资产/资产接口说明.docx");
//        p.waitFor();

        Process p = run.exec("cmd /C start C:/Users/admin/Desktop/api/asset.docx");
        p.waitFor();

    }



}
