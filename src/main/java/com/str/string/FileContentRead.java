package com.str.string;

import com.google.common.collect.Lists;
import com.yd.utils.common.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileContentRead {
    public static void main(String[] args) throws Exception{
        File file = new File("C:\\Users\\yansunling\\Desktop\\新建文本文档.txt");
        List<String> contentList = FileUtils.readLines(file, "utf-8");

//        contentList= Lists.newArrayList("本科：计算机类（计算机科学与技术、计算机及应用、计算机应用工程、计算机及软件、软件工程、软件工程技术、人工智能工程技术），信息管理与信息系统");
        Set<String> set=new LinkedHashSet<>();
        contentList.forEach(item->{
            boolean addFlag=true;
            item=item.trim().replaceAll("本科：","");
            item=item.trim().replaceAll("本科:","");
            item=item.trim().replaceAll("研究生：","");
            item=item.trim().replaceAll(" ","");
            item=item.trim().replaceAll("；","，");

            if(item.indexOf("，")>=0||item.indexOf("（")>0){
                String[] split = item.split("，");
                List<String> temp=new ArrayList<>();
                for(String str:split){
                    if(str.indexOf("、")>0&&str.indexOf("（")>0){
                        List<String> strings = splitString(str);
                        set.addAll(strings);

                    }else if(str.indexOf("、")<0){
                        set.add(str);
                    }else{
                        temp.add(str);
                    }
                }
                item=StringUtils.join("、",temp.toArray());
                addFlag=false;
            }
            if(item.indexOf("、")>=0){
                String[] split = item.split("、");
                for(String str:split){
                    set.add(str);
                }
                addFlag=false;
            }
            if(addFlag){
                set.add(item);
            }
        });
        set.forEach(item->{
            System.out.println(item);
        });
    }
    public static List<String> splitString(String input) {
        List<String> result = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int bracketDepth = 0; // 括号层级计数器（中文全角括号）

        for (char c : input.toCharArray()) {
            // 更新括号层级
            if (c == '（') { // 左括号，层级+1
                bracketDepth++;
            } else if (c == '）') { // 右括号，层级-1
                bracketDepth--;
            }

            // 处理顿号：仅在括号外时拆分
            if (c == '、' && bracketDepth == 0) {
                result.add(buffer.toString());
                buffer.setLength(0); // 清空缓冲区
            } else {
                buffer.append(c);
            }
        }

        // 添加最后一个分段
        if (buffer.length() > 0) {
            result.add(buffer.toString());
        }

        return result;
    }

}
