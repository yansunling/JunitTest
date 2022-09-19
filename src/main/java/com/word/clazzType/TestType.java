package com.word.clazzType;

import com.alibaba.fastjson.JSONObject;
import com.dy.components.annotations.CJ_column;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestType {

    public static void main(String[] args) {

        List<ParamEntry> paramsBean = getParamsBean(RequestTypeData.class);
        System.out.println(JSONObject.toJSONString(paramsBean));


    }

    @SneakyThrows
    public static List<ParamEntry> getParamsBean(Class clazz){

        List<ParamEntry> returnList=new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            CJ_column cjColumn = declaredField.getAnnotation(CJ_column.class);
            if(cjColumn!=null){
                ParamEntry bean=new ParamEntry(declaredField.getName(),cjColumn.name());
                Class<?> declaringClass = declaredField.getType();
                bean.setClazz(declaringClass);
                //不是基本类型
                if(!baseType(declaringClass)){
                    if(declaringClass.getSimpleName().equals("List")){
                        ParameterizedType type = (ParameterizedType) declaredField.getGenericType();
                        Type actualTypeArgument = type.getActualTypeArguments()[0];
                        //获得参数类名
                        String clazzName = actualTypeArgument.getTypeName();
                        Class<?> typeClass = Class.forName(clazzName);
                        if(!baseType(typeClass)){
                            bean.setChildren(getParamsBean(typeClass));
                        }
                    }else{
                        bean.setChildren(getParamsBean(declaringClass));
                    }
                }
                returnList.add(bean);
            }
        }
        return returnList;
    }

    private static  boolean baseType(Class clazz){
        boolean primitive = clazz.isPrimitive();
        if(!primitive){
            String simpleName = clazz.getSimpleName();
            if(!simpleName.equals("String")&&!simpleName.equals("Object")&&!simpleName.equals("Boolean")
                    &&!simpleName.equals("Double")&&!simpleName.equals("Integer")){
                return false;
            }
        }
        return true;
    }

}


