package com.excel;

import cn.hutool.core.bean.BeanUtil;

import com.yd.utils.common.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExecelReadTest {
    public static void main(String[] args) throws Exception{

        File file=new File("C:\\Users\\yansunling\\Desktop\\新建 XLSX 工作表.xlsx");
        File file1=new File("C:\\Users\\yansunling\\Desktop\\22.xlsx");
        Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.getRow(16);
//        for(int i=0;i<10;i++){
            Cell cell = row.getCell(8);
            if(cell==null){
                cell = row.createCell(8);
            }
            cell.setCellValue(1.5);
//        }

        // 记录原始合并单元格
        List<CellRangeAddress> originalRegions = new ArrayList<>();
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            originalRegions.add(sheet.getMergedRegion(i).copy());
        }




        int insertRowIndex = 13;
//        // 1. 将插入位置及以下的所有行下移一行
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum >= insertRowIndex) {
            sheet.shiftRows(insertRowIndex, lastRowNum, 1, true, false);
        }

        // 2. 创建新行（第14行，索引13）
        Row newRow = sheet.createRow(insertRowIndex);

        // 3. （可选）复制上一行的样式到新行
        Row previousRow = sheet.getRow(insertRowIndex - 1);
        if (previousRow != null) {
            copyRowStyle(previousRow, newRow);
        }

        // 4. 在新行的指定单元格写入数据（示例：在A14写入"New Row"）
        Cell newCell = newRow.createCell(4);  // A列
        newCell.setCellValue("New Row");




        // 清除所有合并单元格
        for (int i = sheet.getNumMergedRegions() - 1; i >= 0; i--) {
            sheet.removeMergedRegion(i);
        }

        // 重新添加并调整合并单元格
        for (CellRangeAddress region : originalRegions) {
            int firstRow = region.getFirstRow();
            int lastRow = region.getLastRow();
            int firstCol = region.getFirstColumn();
            int lastCol = region.getLastColumn();

            if (firstRow > insertRowIndex) {
                // 区域完全在插入行下方，整体下移
                sheet.addMergedRegion(new CellRangeAddress(
                        firstRow + 1, lastRow + 1, firstCol, lastCol
                ));
            } else if (lastRow < insertRowIndex) {
                // 区域在插入行上方，保持不变
                sheet.addMergedRegion(region);
            } else {
                // 区域包含插入行
                if (firstRow == insertRowIndex) {
                    // 合并从插入行开始，下移
                    sheet.addMergedRegion(new CellRangeAddress(
                            firstRow + 1, lastRow + 1, firstCol, lastCol
                    ));
                } else if (lastRow == insertRowIndex) {
                    // 合并到插入行结束，分割
                    sheet.addMergedRegion(new CellRangeAddress(
                            firstRow, insertRowIndex - 1, firstCol, lastCol
                    ));
                } else {
                    // 合并跨越插入行，分割为两部分
                    sheet.addMergedRegion(new CellRangeAddress(
                            firstRow, insertRowIndex - 1, firstCol, lastCol
                    ));
                    sheet.addMergedRegion(new CellRangeAddress(
                            insertRowIndex + 1, lastRow + 1, firstCol, lastCol
                    ));
                }
            }
        }







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


}
