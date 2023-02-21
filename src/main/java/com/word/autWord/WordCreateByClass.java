package com.word.autWord;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.dy.test.autoTest.ParamBean;
import com.dy.test.doc.GeneralTemplateTool;
import com.word.asset.interfaces.MyNotEmpty;
import com.word.asset.interfaces.MyNotNull;
import com.word.controller.TMSP_claims_hand_docController;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCreateByClass {


    public static void main(String[] args) throws Exception{

        closeWps();

        String filePath="C:/Users/admin/Desktop/api";
        //模板路径
        String templatePath=filePath+"/template.docx";
        //doc文档生成工具
        GeneralTemplateTool gtt = new GeneralTemplateTool();


        Class<TMSP_claims_hand_docController> clazz = TMSP_claims_hand_docController.class;

        RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
        //获得开始路径
        String rootPath="https://tlwl.uat.tuolong56.com/tmsp"+annotation.value()[0];
        //获得所有方法
        Method[] methods = clazz.getMethods();

        List<String> fileList=new ArrayList<>();

        for (Method item : methods) {
            RequestMapping declaredAnnotation = item.getDeclaredAnnotation(RequestMapping.class);
            if(declaredAnnotation!=null){
                String url = rootPath+declaredAnnotation.value()[0]+".do";
                //获得描述
                String desc = declaredAnnotation.name();
                CJ_jcjs_esbMethodInfo methodInfo = item.getAnnotation(CJ_jcjs_esbMethodInfo.class);
                if(methodInfo!=null){
                    desc = methodInfo.desc();
                }
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
                    Object requestParam = new HashMap<>();

                    try {
                        requestParam = parameterType.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    List<ParamBean> paramBeans=getParamsBean(parameterType);
                    Map<String, Object> stringObjectMap = new HashMap<>();
                    //集合对象
                    List<ParamBean> listChildren=getTabList(tab1list,paramBeans,stringObjectMap);
                    params.put("tab1", tab1list);
                    //是否存在对象数组
                    if(CollectionUtil.isNotEmpty(listChildren)){
                        //获得目标文件
                        templatePath = CopyWordParagraph.createListDocument(listChildren);
                        for(int i=0;i<listChildren.size();i++){
                            ParamBean paramBean = listChildren.get(i);
                            List<ParamBean> childParamsBean = getParamsBean(Class.forName(paramBean.getClazz()));
                            //获得子对象属性
                            List<Map<String,String>> tab2list = new ArrayList<>();
                            Map<String, Object> childValueObj=new HashMap<>();
                            getTabList(tab2list,childParamsBean,childValueObj);
                            params.put("tab"+(i+2), tab2list);
                            if(StringUtils.isNotBlank(paramBean.getListType())){
                                List list=new ArrayList();
                                list.add(childValueObj);
                                stringObjectMap.put(paramBean.getName(),list);
                            }else{
                                stringObjectMap.put(paramBean.getName(),childValueObj);
                            }
                        }
                    }
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
                    fileList.add(outFile);
                    System.out.println("生成模板成功");
                    System.out.println(outFile);




                }
            }
        }





        openWps(fileList);

    }
    @SneakyThrows
    public static List<ParamBean> getTabList( List<Map<String,String>> tab1list,List<ParamBean> paramBeans,Map<String, Object> stringObjectMap){
        List<ParamBean> listChildren=new ArrayList<>();
        for (ParamBean bean:paramBeans) {
            String paramName = bean.getName();
            Map<String, String> map = new HashMap<>();
            map.put("param", paramName);
            map.put("type", bean.getType());
            map.put("msg", bean.getDescription());
            tab1list.add(map);
            if(StringUtils.equals("Y",bean.getListType())){
                if(StringUtils.isNotBlank(bean.getClazz())){
                    listChildren.add(bean);
                }
            }else if(StringUtils.isNotBlank(bean.getClazz())){
                listChildren.add(bean);
            }else{
                stringObjectMap.put(paramName,bean.getDescription());//设置对象属性
            }

        }
        return listChildren;
    }



    @SneakyThrows
    public static  List<ParamBean> getParamsBean(Class clazz){

        List<ParamBean> returnList=new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
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
                if(declaredField.getType() == java.util.List.class){
                    // 如果是List类型，得到其Generic的类型
                    Type genericType = declaredField.getGenericType();
                    if(genericType == null) continue;
                    // 如果是泛型参数的类型
                    if(genericType instanceof ParameterizedType){
                        ParameterizedType pt = (ParameterizedType) genericType;
                        //得到泛型里的class类型对象
                        Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                        bean.setClazz(genericClazz.getName());
                    }
                    bean.setListType("Y");
                    String description = bean.getDescription();
                    if(description.indexOf("(集合)")<=0){
                        description=description+"(集合)";
                        bean.setDescription(description);
                    }
                }else{
                    //不是基本类型
                    Class<?> aClass = declaredField.getType();
                    boolean primitive = aClass.isPrimitive();
                    if(!primitive){
                        String simpleName = aClass.getSimpleName();
                        if(!simpleName.equals("String")&&!simpleName.equals("Object")&&!simpleName.equals("Boolean")
                                &&!simpleName.equals("Double")&&!simpleName.equals("Integer")&&!simpleName.equals("Money")){
                            bean.setClazz(aClass.getName());
                        }
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

    public static void openWps(String fileName) throws Exception{
        Runtime run =Runtime.getRuntime();
        Process p = run.exec("cmd /C start "+fileName);
        p.waitFor();
    }


    public static void openWps(List<String> list) throws Exception{
        for(String fileName:list){
            openWps(fileName);
        }

    }



}
