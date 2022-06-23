package com.dy.test.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yd.utils.common.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTextRead {
    public static void main(String[] args) throws Exception{
        List<String> strings = FileUtils.readLines(new File("E:/TMSP_take_orderController.java"), "UTF-8");
        List<String> list=new ArrayList<>();
        List<String> listUrl=new ArrayList<>();
        boolean startFlag=false;
        for(String str:strings){
            if(StringUtils.isNotBlank(str)){
                int i = str.indexOf("@CJ_jcjs_esbMethodInfo");
                if(i>=0){
//                    System.out.println(str);
                    int start = str.indexOf("alias =");
                    int end = str.indexOf(",autoRegister");
                    String substring = str.substring(start, end).replace("alias =","").replace("\"","");
                    list.add(substring.trim());
                    startFlag=true;
                }
                if(str.indexOf("@RequestMapping")>=0&&startFlag){
                    str=str.replace(" = \"","=\"");
                    int start = str.indexOf("value=");
                    int end = str.indexOf(",method");
                    String substring = str.substring(start, end).replace("value=","").replace("\"","");
                    listUrl.add(substring);
                }

            }

        }

        String aliasList = JSONObject.toJSONString(list);

        String temp = aliasList.replaceAll("\"", "'").replaceAll("\\[", "").replaceAll("\\]", "");
        System.out.println(temp);

        for(int i=0;i<list.size();i++){
            String alias=list.get(i);
            String url="http://online-tmsp-api:8080/tmsp-api/prv/tmsp_take_order"+listUrl.get(i)+".do";
            String sql="update gms.gms_connector_api set api_target_url='"+url+"',sys_id='tmsp-api' where api_alias='"+alias+"';";
            System.out.println(sql);
        }


    }
}
