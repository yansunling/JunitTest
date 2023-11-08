package com.junit;


import com.junit.po.ApiBean;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class BeanPropertyTest {
    public static void main(String[] args) throws Exception{

        ApiBean apiBean=new ApiBean();
        apiBean.setApiName("33");
        Field columnFiled = ReflectionUtils.findField(ApiBean.class, "apiName1");
        if(columnFiled!=null){
            Field field = ApiBean.class.getDeclaredField("apiName"); // 获取字段对象
            field.setAccessible(true); // 设置字段可访问
            field.set(apiBean, ""); // 设置字段值
        }

        System.out.println(apiBean.getApiName());





    }
}
