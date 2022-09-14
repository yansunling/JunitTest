package com.pdf;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlToPdf {

    // 模板名称
    private static String HTML = "contract.html";
    // 生成的pdf存放路径
    private static  String DEST = "I:\\contract\\";
    // 生成的pdf名称
    private static String fileName = "合同D20210511SDNQB1.pdf";

    public static void main(String[] args) throws Exception {
        Map<String,Object> data = new HashMap();
        data.put("合同编号","D20210511SDNQB");
        data.put("甲方姓名","小哈");
        data.put("签字日期","2021.05.13");
        String content = JavaToPdfHtmlFreeMarker.freeMarkerRender(data,HTML);
        JavaToPdfHtmlFreeMarker.createPdf1(content,DEST+fileName);
    }

}
