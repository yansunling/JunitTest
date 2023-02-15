package com.engine;

import cn.hutool.core.io.FileUtil;
import com.yd.utils.common.DateUtils;
import com.yd.utils.common.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.util.*;

public class FileToRequired {
    public static void main(String[] args) throws Exception{
        File file=new File("C:\\Users\\admin\\Desktop\\required.txt");
        List<String> list = FileUtil.readLines(file, "UTF-8");
        Map<String,String> map=new LinkedHashMap<>();
        list.forEach(item->{
            if(StringUtils.isNotBlank(item)){
                String[] strList = item.split("\t");
                map.put(strList[0],strList[1]);
            }
        });

        List<String> keyList=new ArrayList<>();
        List<String> valueList=new ArrayList<>();
        map.forEach((key,value)->{
            keyList.add(key);
            valueList.add(value);
            System.out.println("str.equals(\""+value+"\",data.node_name)");
        });

        map.forEach((key,value)->{

            System.out.println("orderPO.node_type='"+key+"';");


        });




        keyList.forEach(key->{
            System.out.println(key);
        });

        valueList.forEach(key->{
            System.out.println(key);
        });






            keyList.forEach(key->{
            if(key.indexOf("time")>0){
                System.out.println("data."+key+"!=nil");
            }else {
                System.out.println("str.isBlank(data."+key+")");
            }
        });

        valueList.forEach(key->{
            System.out.println("第%s行数据的"+key+"为必填项！");
        });

    }
}
