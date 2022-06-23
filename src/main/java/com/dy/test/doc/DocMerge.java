package com.dy.test.doc;

import java.util.*;

public class DocMerge {
    public static void main(String[] args) {
        //模板文档路径
        String filePath = "E:/template_data.docx";
        String res = String.valueOf(new Date().getTime());
        //生成文档路径
        String outFile = "E:/插入值后文档" + res + ".docx";
        try {
            GeneralTemplateTool gtt = new GeneralTemplateTool();

            Map<String, Object> params = new HashMap<String, Object>();
            //创建替代模板里段落中如${title}值开始
            params.put("alias","query" );
            params.put("name","查询");
            params.put("url","https://www.baidu.com");
            params.put("data_type","array");

            //......对应模板扩展
            //创建替代模板里段落中如${title}值结束

            //创建替代&生成模板里tab1标识的表格中的值开始
            List<Map<String,String>> tab1list = new ArrayList<Map<String,String>>();
            for (int i = 1; i <= 3; i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("param", "张" + i);
                map.put("type", "String");
                map.put("msg", "测试"+i);
                map.put("must", "否");
                tab1list.add(map);
            }
            params.put("tab1", tab1list);

            List<Map<String,String>> resplist = new ArrayList<Map<String,String>>();
            for (int i = 1; i <= 3; i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("resp_param", "张" + i);
                map.put("resp_type", "String");
                map.put("resp_msg", "测试"+i);
                map.put("resp_must", "否");
                resplist.add(map);
            }
            params.put("tab2", resplist);





            gtt.templateWrite(filePath, outFile, params);
            System.out.println("生成模板成功");
            System.out.println(outFile);
        } catch (Exception e) {
            System.out.println("生成模板失败");
            e.printStackTrace();
        }
    }
}
