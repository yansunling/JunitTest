package com.javaBuild.titleMap;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreateTitleData {
    public static void main(String[] args) throws Exception{




        List<String> list = FileUtils.readLines(new File("C:\\Users\\yansunling\\Desktop\\CrmxMarketCustomerImportData.java"));

        Map<String,String> map=new LinkedHashMap<>();
        List<String> tempList=new ArrayList<>();

        list.forEach(item->{
            String trim = item.trim();
            if(trim.startsWith("* ")){
                String name=trim.replaceAll("\\*","").trim();
                System.out.println(name);
                tempList.add(name);
            }
            if(trim.indexOf("private")>=0){
                String name=trim.replaceAll("private String","").replaceAll("= \"\";","").replaceAll(";","").trim();
                System.out.println(name);
                tempList.add(name);
            }
            if(tempList.size()==2){
                map.put(tempList.get(1),tempList.get(0));
                tempList.clear();
            }
        });
        System.out.println(map);
        StringBuffer sb=new StringBuffer();
        map.forEach((key,value)->{
            sb.append("titleMap.put(\""+key+"\",\""+value+"\");\n");
        });
        System.out.println(sb.toString());

    }
}
