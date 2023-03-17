package com.sqlFile;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.word.autWord.WordCreateByClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SqlFilePrint {

    public static void main(String[] args) {

        String path = SqlFilePrint.class.getClassLoader().getResource("").getPath();
        String filePath=path+"sql/sql.txt";

        List<String> list = FileUtil.readUtf8Lines(new File(filePath));

        StringBuffer sb=new StringBuffer();
        list.forEach(item->{
            char[] chars = item.toCharArray();
            for(int i=0;i<chars.length;i++){
                char c = chars[i];
                sb.append(c);
                if(i+1<chars.length){
                    char after = chars[i+1];

                    if(isChinse(c)&&after==' '){
                        sb.append("\n");
                    }
                }
            }
        });
        System.out.println(sb.toString());

    }
    public static boolean isChinse(char c){
        if((c >= 0x4e00)&&(c <= 0x9fbb)||c==')') {
           return true;
        }
        return  false;

    }














}
