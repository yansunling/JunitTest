package com.word.createWord;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.junit.po.ParamBean;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.springframework.beans.BeanUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public class CopyWordParagraph {

    public static String  createListDocument(List<ParamBean> beanList)throws Exception{
//        String filePath="C:/Users/admin/Desktop/api";

        String path = WordCreateByClass.class.getClassLoader().getResource("").getPath();
        String filePath=path+"api";

        //模板路径
        String templatePath=filePath+"/template.docx";

        InputStream inputStream = new FileInputStream(templatePath);
        XWPFDocument document = new XWPFDocument(inputStream);
        XWPFTable table = document.getTableArray(0);

        int startParagraph=8;
        int startTable=2;
        for(int i=0;i<beanList.size();i++){
            ParamBean paramBean = beanList.get(i);
            copyTable(document,table,startParagraph,startTable,paramBean.getName()+"参数");
            startTable+=1;
            startParagraph+=2;
        }
        //保存文件
        String fileName = filePath+"/template_list.docx";
        OutputStream outputStream = new FileOutputStream(fileName);
        document.write(outputStream);

        outputStream.close();

        return fileName;

    }





    public static void main(String[] args) throws Exception{


        WordCreateByClass.closeWps();

        String filePath="C:/Users/admin/Desktop/api";
        //模板路径
        String templatePath=filePath+"/template.docx";

        InputStream inputStream = new FileInputStream(templatePath);
        XWPFDocument document = new XWPFDocument(inputStream);
        XWPFTable table = document.getTableArray(0);

        int startParagraph=8;
        int startTable=2;
        for(int i=0;i<3;i++){
            copyTable(document,table,startParagraph,startTable,"detail_list"+(i+1)+"参数");
            startTable+=1;
            startParagraph+=2;
        }
        //保存文件
        String fileName = filePath+"模板.docx";
        OutputStream outputStream = new FileOutputStream(fileName);
        document.write(outputStream);

        outputStream.close();


        WordCreateByClass.openWps(fileName);
    }

    public static void copyTable(XWPFDocument document,XWPFTable table,int paraIndex,int tableNum,String title){
        CTTbl ctTbl = CTTbl.Factory.newInstance(); // 创建新的 CTTbl ， table
        ctTbl.set(document.getTables().get(0).getCTTbl()); // 复制原来的CTTbl
        IBody iBody = document.getTables().get(0).getBody();
        BeanUtils.copyProperties(table.getBody(), iBody);
        XWPFTable newTable = new XWPFTable(ctTbl, iBody); // 新增一个table，使用复制好的Cttbl
        XWPFTableRow row = newTable.getRow(0);
        List<XWPFTableCell> tableCells = row.getTableCells();
        XWPFTableCell xwpfTableCell = tableCells.get(0);
        List<XWPFParagraph> paragraphs = xwpfTableCell.getParagraphs();
        List<XWPFRun> runs = paragraphs.get(0).getRuns();
        runs.get(0).setText("${tab"+tableNum+"}",0);

        List<XWPFParagraph> paragraphList = document.getParagraphs();
        XWPFParagraph paragraph = paragraphList.get(paraIndex);
        XmlCursor cursor = paragraph.getCTP().newCursor();
        XWPFParagraph xwpfParagraph = document.insertNewParagraph(cursor);
        XWPFRun run = xwpfParagraph.createRun();
        run.setText(title);
        run.setBold(true);



        paragraphList = document.getParagraphs();
        paragraph = paragraphList.get(paraIndex+1);
        cursor = paragraph.getCTP().newCursor();

        // 在指定游标位置插入表格
        document.insertNewTbl(cursor);
        document.setTable(tableNum-1,newTable);


        paragraphList=document.getParagraphs();
        paragraph = paragraphList.get(paraIndex+1);
        cursor = paragraph.getCTP().newCursor();
        xwpfParagraph = document.insertNewParagraph(cursor);
        xwpfParagraph.createRun().setText("");

        /*List<XWPFParagraph> paragraphsNow = document.getParagraphs();

        XWPFParagraph xwpfParagraph = paragraphsNow.get(paragraphsNow.size() - 7);

        cursor = xwpfParagraph.getCTP().newCursor();

        document.insertNewParagraph(cursor);


        System.out.println(document.getTables().size());*/

//        List<XWPFParagraph> paragraphs1 = document.getParagraphs();
//        XWPFParagraph xwpfParagraph = paragraphs1.get(position);
//        xwpfParagraph.createRun().setText(title);

    }








    /**
     * 复制单元格和样式
     *
     * @param targetRow 要复制的行
     * @param sourceRow 被复制的行
     */
    public static void createCellsAndCopyStyles(XWPFTableRow targetRow, XWPFTableRow sourceRow) {
        targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        List<XWPFTableCell> tableCells = sourceRow.getTableCells();
        if (CollectionUtils.isEmpty(tableCells)) {
            return;
        }
        for (XWPFTableCell sourceCell : tableCells) {
            XWPFTableCell newCell = targetRow.addNewTableCell();
            newCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
            List sourceParagraphs = sourceCell.getParagraphs();
            if (CollUtil.isEmpty(sourceParagraphs)) {
                continue;
            }
            XWPFParagraph sourceParagraph = (XWPFParagraph) sourceParagraphs.get(0);
            List targetParagraphs = newCell.getParagraphs();
            XWPFParagraph targetParagraph = CollUtil.isEmpty(targetParagraphs) ? newCell.addParagraph() : (XWPFParagraph) targetParagraphs.get(0);
            targetParagraph.getCTP().setPPr(sourceParagraph.getCTP().getPPr());
            XWPFRun targetRun = targetParagraph.getRuns().isEmpty()
                    ? targetParagraph.createRun() : targetParagraph.getRuns().get(0);
            List<XWPFRun> sourceRunList=sourceParagraph.getRuns();
            if (CollUtil.isNotEmpty(sourceRunList)) {
                XWPFRun sourceRun=sourceRunList.get(0);
                //字体名称
                targetRun.setFontFamily(sourceRun.getFontFamily());
                //字体大小
                targetRun.setFontSize(sourceRun.getFontSize());
                //字体颜色
                targetRun.setColor(sourceRun.getColor());
                //字体加粗
                targetRun.setBold(sourceRun.isBold());
                //字体倾斜
                targetRun.setItalic(sourceRun.isItalic());
            }
        }
    }



}
