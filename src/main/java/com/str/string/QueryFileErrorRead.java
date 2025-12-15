package com.str.string;

import com.alibaba.fastjson.JSON;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryFileErrorRead {
    public static void main(String[] args) throws Exception{
        String filePath1 = "C:\\Users\\yansunling\\Desktop\\1.txt";

        Map<String, List<String>> queryParam = getQueryParam(filePath1);

        Map<String, List<String>> commonMap=new HashMap<>();
       /* Map<String, Integer> stringIntegerMap = FileReadUrl.readUrl("1.log", new HashMap<>());
        List<String> list=new ArrayList<>();
        stringIntegerMap.forEach((url,value)->{
            Pattern pattern = Pattern.compile("/([^/]+)\\.do*");
            Matcher matcher = pattern.matcher(url);
            String result3 = "";
            if (matcher.find()) {
                result3 = matcher.group(1);
                list.add(result3);
            }
        });
        queryParam.forEach((key,value)->{
            if(!list.contains(key)){
                commonMap.put(key,value);
//                System.out.println("not return queryId:"+key+",value:"+ JSON.toJSONString(value));
            }
        });*/




        Map<String, String> emptyMap=new HashMap<>();

        queryParam.forEach((queryId,params)->{
            params.forEach(item->{
                if(StringUtils.equals("[]",item)){
                    String value = emptyMap.get(queryId);
                    if(StringUtils.isBlank(value)){
                        System.out.println(queryId+":"+item);
                    }
                    emptyMap.put(queryId,item);
                }
            });


            //System.out.println(queryId+":"+StringUtils.join(",",params.toArray()));
        });




    }



    public static Map<String,List<String>> getQueryParam(String filePath) throws Exception{



        List<String> tableFiles = FileUtils.readLines(new File(filePath), "utf-8");


        List<String> queryList=new ArrayList<>();
        for(int i=0;i<tableFiles.size();i++){
            String item=tableFiles.get(i);
            if(item.indexOf("queryId:")>=0){
                boolean addFlag=true;
                for(int j=0;j<5;j++){
                    if(i+j<tableFiles.size()){
                        String itemNext=tableFiles.get(i+j);
                        if(itemNext.indexOf("countSql")>0){
                            addFlag=false;
                            break;
                        }
                    }
                }
                if(addFlag){
                    queryList.add(item);
                }

            }
        }

        tableFiles.forEach(item->{

        });
        Map<String,List<String>> map=new HashMap<>();

        queryList.forEach(str->{
            List<String> temp=new ArrayList<>();
            String queryId="";
            int queryIdIndex = str.indexOf("queryId:");
            if (queryIdIndex != -1) {
                int commaIndex = str.indexOf(";", queryIdIndex);
                if (commaIndex != -1) {
                    queryId = str.substring(queryIdIndex + "queryId:".length(), commaIndex).trim();
                }else{
                    commaIndex = str.indexOf(",", queryIdIndex);
                    if (commaIndex != -1) {
                        queryId = str.substring(queryIdIndex + "queryId:".length(), commaIndex).trim();
                    }
                }
            }

            // 提取params的值
            String params = null;
            int paramsIndex = str.indexOf("params:");
            if (paramsIndex != -1) {
                params = str.substring(paramsIndex + "params:".length()).trim();
                temp.add(params);
            }
            List<String> stringList = map.get(queryId);
            if(CollectionUtil.isEmpty(stringList)){
                stringList=temp;
            }else{
                stringList.addAll(temp);
            }
            map.put(queryId,stringList);
        });


        return map;

    }



}
