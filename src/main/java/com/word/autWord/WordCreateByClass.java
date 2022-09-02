package com.word.autWord;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.dy.test.autoTest.ParamBean;
import com.dy.test.doc.GeneralTemplateTool;
import com.word.controller.CompAssetApplyRecordController;
import com.word.asset.interfaces.MyNotEmpty;
import com.word.asset.interfaces.MyNotNull;
import com.yd.utils.common.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCreateByClass {


    public static void main(String[] args) throws Exception{

        String filePath="C:/Users/admin/Desktop/api";
        //模板路径
        String templatePath=filePath+"/template.docx";
        //doc文档生成工具
        GeneralTemplateTool gtt = new GeneralTemplateTool();


        Class<CompAssetApplyRecordController> clazz = CompAssetApplyRecordController.class;

        RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
        //获得开始路径
        String rootPath="https://tlwl.uat.tuolong56.com/asset-api"+annotation.value()[0];
        //获得所有方法
        Method[] methods = clazz.getMethods();

        for (Method item : methods) {
            RequestMapping declaredAnnotation = item.getDeclaredAnnotation(RequestMapping.class);
            if(declaredAnnotation!=null){
                String url = rootPath+declaredAnnotation.value()[0]+".do";
                CJ_jcjs_esbMethodInfo methodInfo = item.getAnnotation(CJ_jcjs_esbMethodInfo.class);
                String desc = methodInfo.desc();
                if(item.getParameterTypes().length>0){
                    Class<?> parameterType = item.getParameterTypes()[0];
                    System.out.println(parameterType.getName());

                    //......对应模板扩展
                    //创建替代模板里段落中如${title}值结束
                    Map<String, Object> params = new HashMap<>();
                    params.put("url",url);
                    params.put("name",desc);
                    //创建替代&生成模板里tab1标识的表格中的值开始
                    List<Map<String,String>> tab1list = new ArrayList<>();


                    Object requestParam = parameterType.newInstance();
                    List<ParamBean> paramBeans=getParamsBean(parameterType);

                    List<String> paramIsList=new ArrayList<>();
                    for (ParamBean bean:paramBeans) {
                        String paramName = bean.getName();
                        Map<String, String> map = new HashMap<>();
                        map.put("param", paramName);
                        map.put("type", bean.getType());
                        map.put("msg", bean.getDescription());
                        tab1list.add(map);
                        if(StringUtils.equals("Y",bean.getListType())){
                            paramIsList.add(paramName);
                        }
                    }
                    params.put("tab1", tab1list);

                    Map<String, Object> stringObjectMap = BeanUtils.beanToMap(requestParam);

                    paramIsList.forEach(param->{
                        stringObjectMap.put(param,new ArrayList<>());
                    });

                    String pretty = JSON.toJSONString(stringObjectMap, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
                    pretty=pretty.replaceAll("\n","\r");
                    pretty=pretty.replaceAll("null","\"\"");
                    params.put("request_json", pretty);

                    Map<String,Object> responseJson=new HashMap<>();
                    responseJson.put("errorCode",0);
                    responseJson.put("msg","操作成功");


                    pretty = JSON.toJSONString(responseJson, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
                    pretty=pretty.replaceAll("\n","\r");
                    params.put("response_json", pretty);

                    String outFile = filePath + "/"+desc+".docx";
                    gtt.templateWrite(templatePath, outFile, params);
                    System.out.println("生成模板成功");
                    System.out.println(outFile);




                }
            }
        }





//        openWps();

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
                MyNotEmpty notEmpty = declaredField.getAnnotation(MyNotEmpty.class);
                if(notEmpty!=null){
                    bean.setType("是");
                }
                Class<?> type = declaredField.getType();
                if(type.getSimpleName().indexOf("List")>=0){
                    bean.setListType("Y");
                    String description = bean.getDescription();
                    if(description.indexOf("(集合)")<=0){
                        description=description+"(集合)";
                        bean.setDescription(description);
                    }
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

//        Process p = run.exec("cmd /C start C:/Users/admin/Desktop/资产/资产接口说明.docx");
//        p.waitFor();

        Process p = run.exec("cmd /C start C:/Users/admin/Desktop/api/asset.docx");
        p.waitFor();

    }



}
