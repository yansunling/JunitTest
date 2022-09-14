package com.pdf;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;

import static com.lowagie.text.pdf.PdfName.DEST;

public class PfdSign {
    private static String  DEST = "I:\\contract\\";
    public static void main(String[] args) throws Exception {
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



        Image image = Image.getInstance("I:/contract/2.png");
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
