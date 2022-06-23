package com.dy.test.doc;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocxTest {

    public static void main(String[] args) throws IOException, TemplateException {

        try {

            // docx的路径和文件名
            String docxTemplate = "E:\\test_template.docx";
            // 填充完数据的临时xml
            String xmlTemp = "E:\\test.xml";
            // 目标文件名
            String toFilePath = "E:\\test.docx";

            Writer w = new FileWriter(new File(xmlTemp));
            // 1.需要动态传入的数据
            Map<String, Object> p = new HashMap<String, Object>();



            Map<String,String> map1 = new HashMap<String,String>();
            map1.put("param", "123456");

            map1.put("msg", "123123");
            Map<String,String> map2 = new HashMap<String,String>();
            map2.put("param", "456789");
            map2.put("msg", "李四");

            List<Map<String,String>> newlist = new ArrayList<>();
            newlist.add(map1);
            newlist.add(map2);

            p.put("userList", newlist);


            // 3.把填充完成的xml写入到docx中
            XmlToDocx xtd = new XmlToDocx();
            xtd.outDocx(new File(xmlTemp), docxTemplate, toFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
