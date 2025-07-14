package com.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExecelReadTest {
    public static void main(String[] args) throws Exception{

        File file=new File("C:\\Users\\yansunling\\Desktop\\contract_day_report.xlsx");
        File file1=new File("C:\\Users\\yansunling\\Desktop\\22.xlsx");
        XSSFWorkbook workbook =(XSSFWorkbook ) WorkbookFactory.create(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        int rowNum=1;
        int columnNum=2;
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(columnNum);
        if(cell==null){
            cell = row.createCell(columnNum);
        }

        XSSFCellStyle style = workbook.createCellStyle(); // 直接返回 XSSFCellStyle
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor( new XSSFColor(new java.awt.Color(255, 192, 203)));
        // 将样式应用到单元格
      //  cell.setCellStyle(style);

        XSSFFont font = workbook.createFont();
        font.setColor(new XSSFColor(new java.awt.Color(139, 0, 0)));
//        font.setFontHeightInPoints((short) 12); // 设置字体大小
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);     // 水平居中



        cell.setCellValue(1.5);


        int insertRowIndex = 13;
       /* for(int j=0;j<10;j++) {
            // 记录原始合并单元格
            List<CellRangeAddress> originalRegions = new ArrayList<>();
            for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                originalRegions.add(sheet.getMergedRegion(i).copy());
            }


//        // 1. 将插入位置及以下的所有行下移一行
            int lastRowNum = sheet.getLastRowNum();



        }*/

       // insertLastRow(sheet,4);







        FileOutputStream out = new FileOutputStream(file1);
        workbook.write(out);
        out.close();
        workbook.close();
    }

    private static void copyRowStyle(Row sourceRow, Row targetRow) {
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell sourceCell = sourceRow.getCell(i);
            if (sourceCell != null) {
                Cell targetCell = targetRow.createCell(i);
                targetCell.setCellStyle(sourceCell.getCellStyle());
            }
        }
    }

    private static void insertLastRow(Sheet sheet,int size){
        for(int j=0;j<size;j++){
            int lastRowNum = sheet.getLastRowNum();
            // 2. 创建新行（第14行，索引13）
            Row newRow = sheet.createRow(lastRowNum+1);
            // 3. （可选）复制上一行的样式到新行
            Row previousRow = sheet.getRow(lastRowNum);
            if (previousRow != null) {
                copyRowStyle(previousRow, newRow);
            }
            sheet.addMergedRegion(new CellRangeAddress(
                    lastRowNum + 1, lastRowNum + 1, 9,10
            ));
        }

    }






}
