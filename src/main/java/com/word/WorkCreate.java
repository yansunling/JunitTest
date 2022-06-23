package com.word;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.dy.components.annotations.CJ_column;
import com.dy.test.autoTest.ParamBean;
import com.dy.test.doc.GeneralTemplateTool;
import com.word.asset.*;
import com.word.interfaces.MyNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkCreate {


    public static void main(String[] args) throws Exception{

        closeWps();

       String filePath="C:/Users/admin/Desktop/api";
        //模板路径
        String templatePath=filePath+"/template.docx";
        //doc文档生成工具
        GeneralTemplateTool gtt = new GeneralTemplateTool();
        //......对应模板扩展
        //创建替代模板里段落中如${title}值结束
        Map<String, Object> params = new HashMap<>();
        params.put("url","https://tlwl.uat.tuolong56.com/asset-api/asset/comp_asset_base_check/deleteData.do");
        //创建替代&生成模板里tab1标识的表格中的值开始
        List<Map<String,String>> tab1list = new ArrayList<>();

        Class clazz=CompAssetBaseInfoCheckTaskPO.class;

        Object requestParam = clazz.newInstance();
        List<ParamBean> paramBeans=getParamsBean(clazz);
        for (ParamBean bean:paramBeans) {
            String paramName = bean.getName();
            Map<String, String> map = new HashMap<>();
            map.put("param", paramName);
            map.put("type", bean.getType());
            map.put("msg", bean.getDescription());
            tab1list.add(map);
        }
        params.put("tab1", tab1list);

        String pretty = JSON.toJSONString(BeanUtils.beanToMap(requestParam), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        pretty=pretty.replaceAll("\n","\r");
        pretty=pretty.replaceAll("null","\"\"");
        params.put("request_json", pretty);

        Map<String,Object> responseJson=new HashMap<>();
        responseJson.put("errorCode",0);
        responseJson.put("msg","操作成功");
        pretty = JSON.toJSONString(responseJson, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        pretty=pretty.replaceAll("\n","\r");
        params.put("response_json", pretty);


        String outFile = filePath + "/asset.docx";
        gtt.templateWrite(templatePath, outFile, params);
        System.out.println("生成模板成功");
        System.out.println(outFile);

        openWps();

    }

    public static  List<ParamBean> getParamsBean(Class clazz){

        List<ParamBean> returnList=new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {

            CJ_column cjColumn = declaredField.getAnnotation(CJ_column.class);
            if(cjColumn!=null){
                ParamBean bean=new ParamBean(declaredField.getName(),cjColumn.name());
                MyNotNull annotation = declaredField.getAnnotation(MyNotNull.class);
                if(annotation!=null){
                    bean.setType("是");
                }else{
                    bean.setType("否");
                }
                returnList.add(bean);
            }
        }
        return returnList;
    }

    public static void closeWps() throws Exception{
        Runtime run =Runtime.getRuntime();
        Process p = run.exec("taskkill /f /im wps.exe");
        p.waitFor();
    }

    public static void openWps() throws Exception{
        Runtime run =Runtime.getRuntime();

        Process p = run.exec("cmd /C start C:/Users/admin/Desktop/资产/资产接口说明.docx");
        p.waitFor();

        p = run.exec("cmd /C start C:/Users/admin/Desktop/api/asset.docx");
        p.waitFor();

    }



}
