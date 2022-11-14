package com.word;

import com.yd.utils.common.StringUtils;

import java.lang.reflect.Method;

public class CreateSqlByEnum {
    public static void main(String[] args) throws Exception{

       String[] clazzList={"ASSET_TASK_TYPE"};
       for(String clazzName:clazzList){
           String sql = buildSql(clazzName);
           System.out.println(sql);
       }
    }

    public static  String buildSql(String clazzName) throws Exception{
        Class clazz=Class.forName("com.word.constansts."+clazzName);
        Method method = clazz.getMethod("values");
        Object[] enums = (Object[]) method.invoke(null);
        StringBuffer sql=new StringBuffer();
        for(Object object:enums){
            Method codeType = clazz.getMethod("codeType");
            String key=codeType.invoke(object)+"";
            if(StringUtils.isBlank(key)){
                continue;
            }

            Method codeName = clazz.getMethod("codeName");
            String value=codeName.invoke(object)+"";
            sql.append("INSERT ignore INTO cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES" +
                    " ('"+clazzName.toLowerCase()+"', '"+key+"', '"+value+"', now(), now(), 'T1113');\n");

        }
        return sql.toString();
    }







}
