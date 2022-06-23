package com.excel;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.aspectj.util.FileUtil;

import java.io.*;



public class ExcelAppend {
    public static void main(String[] args) throws Exception{
        String filePath="C:/Users/admin/Desktop/盘点结果.xlsx";
        String newFilePath="C:/Users/admin/Desktop/盘点结果测试.xlsx";
        FileUtil.copyFile(new File(filePath),new File(newFilePath));
        Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(newFilePath)));
        Sheet sheet = workbook.getSheetAt(0);
        int rownum=1;
        int cellnum=1;
        String result="分的高士大夫的鬼斧神工梵蒂冈法国的诗歌风格感动和规范的规范和公司分";
        Row row = sheet.createRow(rownum);
        Cell cell = row.createCell(cellnum);
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)16);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());// 设置背景色

        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
//        cell.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
        CellStyle rowStyle = row.getRowStyle();
        cell.setCellValue(result);
        FileOutputStream out = new FileOutputStream(new File(newFilePath));
        workbook.write(out);
        out.close();
    }
}
