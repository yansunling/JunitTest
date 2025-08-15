package com.str.string;

import com.alibaba.fastjson.JSON;
import com.yd.utils.common.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReadUrl {
    public static void main(String[] args) throws Exception{

        Map<String,String> costMap=new HashMap<>();
        Map<String, Integer> map1 = readUrl("tmsp-master-0401143912-d4577f89f-2wpfq_tlwl_localhost_access_log.2025-04-01.log",costMap);
        Map<String, Integer> map2 = readUrl("tmsp-master-0401143912-d4577f89f-wqzk5_tlwl_localhost_access_log.2025-04-01.log",costMap);
        Map<String, Integer> map = new HashMap<>();
        map1.forEach((key,value)->{
            Integer integer = map2.get(key);
            if(integer!=null){
                map.put(key,value>integer?value:integer);
            }
        });


        // 将 HashMap 的条目转换为列表
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        // 按值排序（从小到大）
        list.sort(Map.Entry.comparingByValue());

        list.forEach(item->{
//            if(item.getValue()<5){
                String costTime = costMap.get(item.getKey());
                if(Double.parseDouble(costTime)>1000000){
                    System.out.println(item.getKey()+"-----"+item.getValue()+"-------"+costMap.get(item.getKey()));
//                }


            }


        });




    }
    public static Map<String,Integer> readUrl(String fileName, Map<String,String> costMap)throws Exception{
        File file = new File("C:\\Users\\yansunling\\Desktop\\"+fileName);
        List<String> contentList = FileUtils.readLines(file, "utf-8");

        Map<String,Integer> map=new HashMap<>();
        contentList.forEach(line->{
            String url = getUrl(line);
            String[] parts = line.split(" ");
            // 提取处理时间（倒数第二个元素）
            String body = parts[parts.length - 3];
            if(StringUtils.isNotBlank(url)){
                String[] strs = url.split(" ");
                url=strs[1];
                String[] split = url.split("\\?");
                String tempUrl=split[0];
             //   url=tempUrl;

                if(tempUrl.indexOf(".do")>=0){
                    Integer integer = map.get(url);
                    if(integer==null){
                        map.put(url,1);
                    }else{
                        map.put(url,integer+1);
                    }
                    costMap.put(url,body);
                }

            }

        });
        return map;
    }




    private static String getUrl(String input){
        String regex = "\"([^\"]*)\"";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(input);

        // 遍历匹配结果
        if (matcher.find()) {
            // 获取匹配到的内容（双引号内的内容）
            return matcher.group(1);

        }
        return "";
    }


}
