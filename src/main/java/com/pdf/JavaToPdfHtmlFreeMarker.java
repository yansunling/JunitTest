package com.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class JavaToPdfHtmlFreeMarker {

    private final static Logger logger = LoggerFactory.getLogger(JavaToPdfHtmlFreeMarker.class);

    private static final String FONT = "C:\\Windows\\Fonts\\simhei.ttf";
    private static final String freeMarkDir = "I:\\contract\\";

    private static Configuration freemarkerCfg = null;

    static {
        freemarkerCfg =new Configuration();
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new File(freeMarkDir));
            freemarkerCfg.setEncoding(Locale.CHINA, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createPdf1(String content,String dest) throws IOException, DocumentException, com.lowagie.text.DocumentException {
        // String LOGO_PATH = "file:///C:/Users/86132/Desktop/";
        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 解析html生成pdf
        render.setDocumentFromString(content);
        //解决图片相对路径的问题
        // render.getSharedContext().setBaseURL(LOGO_PATH);

        render.layout();
        render.createPDF(new FileOutputStream(dest));
    }

    /**
     * freemarker渲染html
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTmp,"UTF-8");
            template.setEncoding("UTF-8");
//            logger.info("template info --->" + template);
            // 合并数据模型与模板
            template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
//            logger.info("template info --->" + template);
//            logger.info("out info --->" + out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }




}
