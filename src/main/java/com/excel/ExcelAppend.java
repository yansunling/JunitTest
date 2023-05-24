package com.excel;

import cn.hutool.core.io.FileUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;


public class ExcelAppend {



    public static void appendFile(File templateFile, List<Map<String, Object>> listData, List<String> columns) throws Exception{
        //生成文件地址
        String newFilePath= "C:/Users/yansunling/Desktop/"+templateFile.getName();
        //删除重名文件
        ExcelsUtil.delFile(newFilePath);
        File file = new File(newFilePath);
        //复制模板
        FileUtil.copyFile(templateFile,file);
        if(CollectionUtil.isEmpty(listData)){
            return;
        }
        int startRow=1;
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = workbook.getSheetAt(0);
        //设置字体大小
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)10);
        //正常单元格
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        for(int rowNum=0;rowNum<listData.size();rowNum++){
            Map<String, Object> resultPO = listData.get(rowNum);
            //创建行
            Row row = sheet.createRow(rowNum+startRow);
            for(int cellNum=0;cellNum<columns.size();cellNum++){
                //通过反射获得值
                String column=columns.get(cellNum);
                String value = resultPO.get(column)+"";
                if(StringUtils.equalsIgnoreCase("null",value)){
                    value="";
                }
                //图片下载
                if((StringUtils.equals(column,"file_seq_no")||StringUtils.equals(column,"photo_url"))&&StringUtils.isNotBlank(value)){
                    String[] serialList = value.split(",");
                    XSSFDrawing drawingPatriarch = sheet.createDrawingPatriarch(); // 插入图片
                    StringBuffer filePath=new StringBuffer();
                    for(int j=0;j<serialList.length;j++){
                        File picFile = ExcelsUtil.downFile(serialList[j],"https://kp.tuolong56.com");
                        boolean image = ExcelsUtil.isImage(picFile);
                        if(image){
                            row.setHeight((short)(100*20));
                            sheet.setColumnWidth(cellNum+j,6000);
                            ExcelsUtil.insertExcelPic(workbook,drawingPatriarch,rowNum+startRow,cellNum+j,picFile.getPath());
                            picFile.delete();
                        }else{
                            filePath.append(picFile.getAbsolutePath());
                        }
                    }
                    if(StringUtils.isNotBlank(filePath.toString())){
                        //创建单元格
                        Cell cell = row.createCell(cellNum);
                        cell.setCellValue(filePath.toString());
                    }

                }else{
                    //创建单元格
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(value);
                }
            }
        }
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
        workbook.close();
    }



    public static void appendText() throws Exception{
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
