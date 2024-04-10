package com.str;

import cn.hutool.core.lang.ClassScanner;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.factory.CIPHandler;
import com.yd.common.cache.Cacheable;
import com.yd.common.exttype.Money;
import com.yd.utils.common.DateUtils;
import com.yd.utils.common.StringUtils;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

public class JsonToSql {
    public static void main(String[] args) throws Exception{
        String json="{\"apply_amount\":{\"mode\":\"Y\",\"val\":43.7},\"insurer\":\"\",\"oa_flag\":\"IS_NOT_0\",\"oa_status\":\"OA_STATUS_ALL_INIT\",\"org_id\":\"2501020301\",\"parking_date\":1691241422000,\"parking_type\":\"PARKING_TYPE_1\",\"parking_way\":\"PARKING_WAY_0\",\"remark\":\"\",\"serial_no\":\"CLLQ202311170246\",\"vehicle_law_id\":\"浙A3M121\"}";

        JSONObject jsonObject = JSON.parseObject(json);

        Set<String> keySet = jsonObject.keySet();
        List<String> columns=new ArrayList<>();
        List<Object> values=new ArrayList<>();

        Set<Class<?>> classSet = ClassScanner.scanPackageBySuper("com.str.enums",Cacheable.class);
        Map<String,String> enumsMap=new HashMap<>();
        classSet.forEach(clazz->{
            if(clazz.isEnum()){
                try {
                    Method method = clazz.getMethod("values");
                    Cacheable[] enums = (Cacheable[]) method.invoke(null);
                    for(Cacheable object:enums){
                        enumsMap.put(object.toString(),object.codeType());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        keySet.forEach(key->{
            columns.add(key);
            Object object = jsonObject.get(key);
            if(object instanceof JSONObject){
                try {
                    Money money = JSONObject.parseObject(JSON.toJSONString(object), Money.class);
                    values.add(money.yuan2Fen());
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }



            String value = enumsMap.get(object);
            if(value!=null){
                values.add(value);
                return;
            }
            if(key.indexOf("_time")>0||key.indexOf("_date")>0){
                try {
                    Timestamp timestamp = jsonObject.getTimestamp(key);
                    values.add(DateUtils.format(timestamp,"yyyy-MM-dd HH:mm:ss"));
                } catch (Exception e) {
                    System.out.println(key);
                    values.add(jsonObject.get(key));
                }
            }else{
                values.add(jsonObject.get(key));
            }
        });
        String table="tmsp.tmsp_own_vehicle_parking";

        String sql="insert into "+table+"("+ StringUtils.join(",",columns.toArray()) +")value"
                +"('"+StringUtils.join("','",values.toArray())+"');";
        System.out.println(sql);


    }
}
