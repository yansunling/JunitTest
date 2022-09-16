package com.pdf;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.lowagie.text.pdf.PdfName.DEST;

public class PfdSign {
    private static String  DEST = "I:\\contract\\";


    public static void main(String[] args) throws Exception{

        closeWps();
        signature();
    }


    public static void signature() throws  Exception {



        String KEYSTORE="I:/contract/test.p12";
        char[] PASSWORD = "123456".toCharArray();//keystory密码
        String SRC=DEST+"合同D20210511SDNQB1.pdf"; ;//原始pdf
        String DEST="I:/contract/demo_signed_box.pdf" ;//签名完成的pdf
        String DEST2=DEST+"签名.pdf"; ;//签名完成的pdf
        String chapterPath="I:/contract/sign.png";//签章图片
        String signername="測試";
        String reason="数据不可更改";
        String location="桃源乡";

/*
        float[] position= PdfKeywordFinder.getAddImagePositionXY(SRC,"承租人(乙方)签章：");
        //Read file using PdfReader
        PdfReader pdfReader = new PdfReader(SRC);
        System.out.println("x:"+position[1]+" y:"+position[2]);*/


        PdfKeywordFinder.sign(new FileInputStream(SRC), new FileOutputStream(DEST2),
                new FileInputStream(KEYSTORE), PASSWORD,
                reason, location, chapterPath);
    }

    public static void closeWps() throws Exception{
        Runtime run =Runtime.getRuntime();
        Process p = run.exec("taskkill /f /im wps.exe");
        int i = p.waitFor();
        Thread.sleep(500L);


    }








    public static void signPfg() throws Exception {
        // base64格式的图片
        String signData = "data:image/png;base64,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";
        // 生成电子签字
        String oldPdfUrl= DEST+"合同D20210511SDNQB1.pdf";
        String newPdfUrl= DEST+"合同D20210511SDNQB.pdf";
        //查找签名位置
        float[] position= PdfKeywordFinder.getAddImagePositionXY(oldPdfUrl,"承租人(乙方)签章：");
        //Read file using PdfReader
        PdfReader pdfReader = new PdfReader(oldPdfUrl);
        System.out.println("x:"+position[1]+" y:"+position[2]);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(newPdfUrl));
        String contractNo="33333";
//        String imgUrl = Base64ImgConvert.convertBase64ToPng(signData, contractNo);



        Image image = Image.getInstance("I:/contract/sign.png");
        //Fixed Positioning
        image.scaleAbsolute(100, 50);
        //Scale to new height and new width of image
        image.setAbsolutePosition(position[1]-20, position[2]-20);

        System.out.println("pages:"+pdfReader.getNumberOfPages());
        PdfContentByte content = pdfStamper.getUnderContent((int) position[0]);
        content.addImage(image);
        pdfStamper.close();
    }
}
