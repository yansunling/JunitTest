package com.excel;


import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.io.FileUtil;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.*;


/**
 * 重写平台导入功能
 * @author Administrator
 * 2019-03-19
 */
public class ExcelsUtil {
    public static void getDataFromExcel(String filePath) throws IOException {
        //String filePath = "E:\\123.xlsx";
        //判断是否为excel类型文件
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            System.out.println("文件不是excel类型");
        }
        FileInputStream fis = null;
        Workbook wookbook = null;
        Sheet sheet = null;
        try {
            //获取一个绝对地址的流
            fis = new FileInputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //2003版本的excel，用.xls结尾
            wookbook = new HSSFWorkbook(fis);//得到工作簿
        } catch (Exception ex) {
            //ex.printStackTrace();
            try {
                //2007版本的excel，用.xlsx结尾
                fis = new FileInputStream(filePath);
                wookbook = new XSSFWorkbook(fis);//得到工作簿
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Map<String, PictureData>  maplist=null;
        sheet = wookbook.getSheetAt(0);
        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        System.out.println(totalRowNum);
        //获得所有数据
        for(int i = 0 ; i <= totalRowNum ; i++){
            System.out.println(i);
            //获得第i行对象
            Row row = sheet.getRow(i);
            if(row!=null){
                Cell cell = row.getCell(0);
                if(cell!=null){
                    System.out.println(cell.toString());
                }
            }
        }
        // 判断用07还是03的方法获取图片
        if (filePath.endsWith(".xls")) {
            maplist = getPictures1((HSSFSheet) sheet);
        } else if(filePath.endsWith(".xlsx")){
            maplist = getPictures2((XSSFSheet) sheet);
        }
        try {
            printImg(maplist);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (Map.Entry<String, PictureData> entry : maplist.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }
    /**
     * 获取图片和位置 (xls)
     * @param sheet
     * @return
     * @throws IOException
     */
    public static Map<String, PictureData> getPictures1 (HSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        for (HSSFShape shape : list) {
            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                PictureData pdata = picture.getPictureData();
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1(); // 行号-列号
                System.out.println(key);
                map.put(key, pdata);
            }
        }
        return map;
    }

    /**
     * 获取图片和位置 (xlsx)
     * @param sheet
     * @return
     * @throws IOException
     */
    public static Map<String, PictureData> getPictures2 (XSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    try {
                        if(shape instanceof XSSFShapeGroup){
                            XSSFShapeGroup group = (XSSFShapeGroup) shape;
                            CTGroupShape ctGroupShape = group.getCTGroupShape();
                            CTPicture[] picArray = ctGroupShape.getPicArray();

                            System.out.println("Whole group is anchored upper left:");
                            int groupRow = ((XSSFClientAnchor)shape.getAnchor()).getRow1();
                            long groupRowDy = ((XSSFClientAnchor)shape.getAnchor()).getDy1();
                            System.out.print("Row: " + groupRow);
                            System.out.println(" + " + groupRowDy + " EMU");

                            int groupCol = ((XSSFClientAnchor)shape.getAnchor()).getCol1();
                            long groupColDx = ((XSSFClientAnchor)shape.getAnchor()).getDx1();
                            System.out.print("Col: " + groupCol);
                            System.out.println(" + " + groupColDx + " EMU");

                            for(CTPicture ctPicture:picArray){
                                System.out.println(ctPicture);
                                String blipId = ctPicture.getBlipFill().getBlip().getEmbed();
                                XSSFPictureData pic = (XSSFPictureData)group.getDrawing().getRelationById(blipId);
                                // 获取图片索引
                                String picName = pic.toString();
                                // 获取图片格式
                                String ext = pic.suggestFileExtension();
                                byte[] data = pic.getData();
                                //图片保存路径
                                FileOutputStream out = new FileOutputStream("I:\\img\\" + System.currentTimeMillis() + "." + ext);
                                out.write(data);
                                out.close();


//                                ctPicture.save(new File("I:\\img\\"+System.currentTimeMillis()+".jpg"));



                            }












                        }else{
                            XSSFPicture picture = (XSSFPicture) shape;
                            XSSFClientAnchor anchor = picture.getPreferredSize();
                            CTMarker marker = anchor.getFrom();
                            String key = marker.getRow() + "-" + marker.getCol();
                            System.out.println(key);
                            map.put(key, picture.getPictureData());
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return map;
    }

        //图片写出
    public static void printImg(Map<String, PictureData> sheetList) throws IOException {

        //for (Map<String, PictureData> map : sheetList) {
        Object key[] = sheetList.keySet().toArray();
        for (int i = 0; i < sheetList.size(); i++) {
            // 获取图片流
            PictureData pic = sheetList.get(key[i]);
            // 获取图片索引
            String picName = key[i].toString();
            // 获取图片格式
            String ext = pic.suggestFileExtension();

            byte[] data = pic.getData();

            //图片保存路径
            FileOutputStream out = new FileOutputStream("I:\\img\\" + picName + "." + ext);
            out.write(data);
            out.close();
        }
        // }

    }

    public static void main(String[] args) throws Exception {
//        getDataFromExcel("C:\\Users\\admin\\Desktop\\2022上半年交旅劳保清单(1).xlsx");

        getDataFromExcel("C:\\Users\\admin\\Desktop\\crmx_market_customer.xlsx");



    }
}