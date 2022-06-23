package com.dy.test.autoTest;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocTest {
    public static void main(String[] args) throws  Exception{
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("param", "123456");

        map1.put("msg", "123123");
        Map<String,String> map2 = new HashMap<String,String>();
        map2.put("param", "456789");
        map2.put("msg", "李四");


        List<Map<String,String>> newlist = new ArrayList<>();
        newlist.add(map1);
        newlist.add(map2);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("userList", newlist);

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        //指定模板路径的第二种方式,我的路径是D:/      还有其他方式
        configuration.setDirectoryForTemplateLoading(new File("E:/"));



        // 输出文档路径及名称
        File outFile = new File("E:/test.docx");
        //以utf-8的编码读取ftl文件
        Template t =  configuration.getTemplate("test.ftl","utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
        t.process(dataMap,out);
        out.close();










    }





}
