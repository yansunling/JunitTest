package com.excel;


import com.grapecity.documents.excel.ImageSaveOptions;
import com.grapecity.documents.excel.Workbook;
import com.yd.utils.common.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExecelToImg {
    public static void main(String[] args) throws Exception {

        String excelFile="C:/Users/yansunling/Desktop/驮龙员工（义乌）车辆信息登记表.xlsx";
        FileInputStream fis = new FileInputStream(new File(excelFile));
        org.apache.poi.ss.usermodel.Workbook xssfWorkbook = new XSSFWorkbook(fis);
        fis.close();

        // 获取工作表
        Sheet sheet = xssfWorkbook.getSheetAt(0);// 假设操作第一个工作表

        // 记录原始的合并单元格区域
        int numMergedRegions = sheet.getNumMergedRegions();
        CellRangeAddress[] originalMergedRegions = new CellRangeAddress[numMergedRegions];
        for (int i = 0; i < numMergedRegions; i++) {
            originalMergedRegions[i] = sheet.getMergedRegion(i);
        }

        // 插入 3 条空白行
        sheet.shiftRows(0, sheet.getLastRowNum(), 3);

        for (int i = 0; i < 3; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                Cell cell = row.createCell(j);

                // 复制原始单元格的样式
                Cell originalCell = sheet.getRow(i + 3).getCell(j);
                if (originalCell!= null) {
                    CellStyle originalStyle = originalCell.getCellStyle();
                    cell.setCellStyle(originalStyle);
                }
            }
        }

        // 恢复合并单元格
        for (CellRangeAddress region : originalMergedRegions) {
            sheet.addMergedRegion(region);
        }



        String newFile="C:/Users/yansunling/Desktop/1_copy.xlsx";
        FileOutputStream fos = new FileOutputStream(newFile);
        xssfWorkbook.write(fos);
        fos.close();
        xssfWorkbook.close();


        Workbook workbook = new Workbook();
        workbook.open(newFile);



        // 配置ImageSaveOptions
        ImageSaveOptions options = new ImageSaveOptions();

        options.setShowRowHeadings(false);
        options.setShowColumnHeadings(false);
        options.setShowDrawingObjects(false);
//        options.setBackgroundColor(Color.FromArgb(226, 231, 243));
//        options.setShowGridlines(true);
//        options.setGridlineColor(Color.FromArgb(145, 167, 214));

        // 将工作表转换为图片
        String inputImagePath = "C:/Users/yansunling/Desktop/WorksheetToImage.png";
        workbook.getWorksheets().get(0).toImage(inputImagePath, options);
        String outputImagePath =  "C:/Users/yansunling/Desktop/excel.png";
        try {
            // 读取原始图片
            BufferedImage originalImage = ImageIO.read(new File(inputImagePath));
            // 获取图片的宽度和高度
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            // 定义裁剪的高度，例如裁剪掉顶部的100像素
            int cropHeight = 30;
            // 裁剪图片
            BufferedImage croppedImage = originalImage.getSubimage(0, cropHeight, width, height - cropHeight);

            // 保存裁剪后的图片
            File outputImage = new File(outputImagePath);
            ImageIO.write(croppedImage, "png", outputImage);

            System.out.println("Image cropped successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //删除文件
        File file = new File(newFile);
        file.delete();

        File imgFile = new File(inputImagePath);
        imgFile.delete();

    }




}
