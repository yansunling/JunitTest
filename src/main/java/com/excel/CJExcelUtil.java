package com.excel;

import com.yd.common.runtime.CIPRuntime;
import com.yd.common.runtime.CIPRuntimeOperator;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: wyy
 * @Date: 2021/3/9 上午11:56
 */
public class CJExcelUtil {



    public static <T>List<T> initImportExcelDatas(Map<String, String> titles, List<Object[]> list, Class<T> class1) {

        List<T> data = new ArrayList<>();
        try {
            CIPRuntimeOperator user = new CIPRuntimeOperator();
            user.setSubject_id("T1113");
            Set<String> columns = titles.keySet();
            Field field;
            for (int i = 0; i < list.size(); i++) {
                Object[] record = list.get(i);
                //碰到空行跳出
                if(record==null){
                    break;
                }
                int j = 0;
                T po = class1.newInstance();
                for (String column : columns) {
                    if(j>=record.length){
                        break;
                    }

                    String str = String.valueOf(record[j]);
                    if (StringUtils.isNotBlank(str)) {
                        str = str.trim();
                    }
                    //值为空值时，字符串默认为空串
                    if(StringUtils.equalsIgnoreCase("null",str)){
                        str="";
                    }
                    String typeName;
                    field = po.getClass().getDeclaredField(column);
                    field.setAccessible(true);
                    typeName = field.getType().getName();
                    //创建时间和修改时间设置为当前时间
                    if (StringUtils.equals(column, "create_time") || StringUtils.equals(column, "update_time")) {
                        field.set(po, new Timestamp(System.currentTimeMillis()));
                        j++;
                        continue;
                    }
                    //创建人和创建用户为当前登录的人
                    if (StringUtils.equals(column, "creator") || StringUtils.equals(column, "op_user_id")) {
                        field.set(po, user.getSubject_id());
                        j++;
                        continue;
                    }
                    if (typeName.startsWith("int") || typeName.contains("Integer")) {
                        if (StringUtils.isNotBlank(str)) {
                            field.set(po, Integer.parseInt( str));
                        }
                    } else if (typeName.contains("Timestamp")) {
                        if (StringUtils.isNotBlank(str)) {
                            str = str.split("\\.")[0];
                            field.set(po, Timestamp.valueOf(str));

                        }
                    } else if (typeName.contains("Double") || typeName.contains("double")) {
                        if (StringUtils.isNotBlank(str)) {
                            field.set(po, Double.parseDouble( str));
                        }
                    } else if (typeName.contains("long") || typeName.contains("Long")) {
                        if (StringUtils.isNotBlank(str)) {
                            field.set(po, Long.valueOf( str));
                        }
                    } else {
                        field.set(po, str);
                    }
                    j++;
                }


                data.add(po);
            }
            return data;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public static void delFile(String path){
        File file = new File(path);
        boolean exists = file.exists();
        if(exists){
            file.delete();
        }
    }


}
