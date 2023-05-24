package com.excel;



import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import javax.imageio.ImageIO;


public class ExcelImgUtil {
    private static int counter = 0;

    /**
     * 获取图片和位置 (xlsx)
     *
     * @param sheet
     * @return
     * @throws IOException
     */
    public static Map<String, PictureData> getPictures(XSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol();
                    byte[] data = picture.getPictureData().getData();
                    map.put(key, picture.getPictureData());
                }
            }
        }
        return map;
    }

    public static Map<String, String> printImg(Map<String, PictureData> sheetList, String path) throws IOException {
        Map<String, String> pathMap = new HashMap<String, String>();
        Object[] key = sheetList.keySet().toArray();
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs(); // 创建目录
        }
        for (int i = 0; i < sheetList.size(); i++) {
            // 获取图片流
            PictureData pic = sheetList.get(key[i]);
            // 获取图片索引
            String picName = key[i].toString();
            // 获取图片格式
            String ext = pic.suggestFileExtension();
            String fileName = encodingFilename(picName);
            byte[] data = pic.getData();

            // 图片保存路径
            String imgPath = path + fileName + "." + ext;
            FileOutputStream out = new FileOutputStream(imgPath);


            pathMap.put(picName, imgPath);
            out.write(data);
            out.close();
        }
        return pathMap;
    }

    private static final String encodingFilename(String fileName) {
        fileName = fileName.replace("_", " ");
        fileName = System.nanoTime()+"";
        return fileName;
    }

    /**
     * 读取excel文字
     *
     * Excel 07版本以上
     *
     * @param sheet
     */
    public static List<Map<String, String>> readData(XSSFSheet sheet, Map<String, String> map,String providerId) {

        List<Map<String, String>> newList = new ArrayList<Map<String, String>>();// 单行数据
        try {

            int rowNum = sheet.getLastRowNum() + 1;
            for (int i = 1; i < rowNum; i++) {// 从第三行开始读取数据,第一行是备注，第二行是标头

                Row row = sheet.getRow(i);// 得到Excel工作表的行
                if (row != null) {
                    int col = row.getPhysicalNumberOfCells();
                    // 单行数据

                    Map<String, String> mapRes = new HashMap<String, String>();// 每格数据
                    for (int j = 0; j < col; j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null) {
                            // arrayString.add("");
                        } else if (cell.getCellType() == 0) {// 当时数字时的处理

                            mapRes.put(getMapKey(j), new Double(cell.getNumericCellValue()).toString());
                        } else {// 如果EXCEL表格中的数据类型为字符串型
                            mapRes.put(getMapKey(j), cell.getStringCellValue().trim());

                        }

                    }

                    if (i != 1) {// 不是标头列时，添加图片路径

                        String path = map.get(i + "-9");
                        mapRes.put(getMapKey(9), path);

                    }
                    mapRes.put("providerId", providerId);
                    newList.add(mapRes);

                }

            }

        } catch (Exception e) {
        }
        return newList;
    }

    public static String getMapKey(int num) {
        String res = "";
        switch (num) {
            case 0:// 分类
                res = "secondDictCode";
                break;
            case 1:// 产品名称
                res = "productName";
                break;
            case 2:// 规格型号
                res = "specification";
                break;
            case 3:// 计量单位
                res = "unit";
                break;
            case 4:// 风格
                res = "style";
                break;
            case 5:// 颜色
                res = "color";
                break;
            case 6:// 采购单价
                res = "purchasePrice";
                break;
            case 7:// 材质
                res = "material";
                break;
            case 8:// 备注
                res = "remark";
                break;

            case 9:// 产品图片
                res = "picture";
                break;

            default:
                break;
        }
        return res;
    }

    /**
     * <一句话功能简述> excel插入图片
     * <功能详细描述>
     * author: zhanggw
     * 创建时间:  2022/5/25
     * @param book poi book对象
     * @param drawingPatriarch 用于图片插入Represents a SpreadsheetML drawing
     * @param rowIndex 图片插入的单元格第几行
     * @param colIndex 图片插入的单元格第几列
     * @param localPicPath 本地图片路径
     */
    public static void insertExcelPic(XSSFWorkbook book, XSSFDrawing drawingPatriarch, int rowIndex, int colIndex, String localPicPath) throws IOException {
        // 获取图片后缀格式
        String fileSuffix = localPicPath.substring(localPicPath.lastIndexOf(".") + 1);
        fileSuffix = fileSuffix.toLowerCase();

        // 将图片写入到字节数组输出流中
        BufferedImage bufferImg;
        ByteArrayOutputStream picByteOut = new ByteArrayOutputStream();
        bufferImg = ImageIO.read(new File(localPicPath));
        ImageIO.write(bufferImg, fileSuffix, picByteOut);

        // 将图片字节数组输出流写入到excel中
        XSSFClientAnchor anchor = new XSSFClientAnchor(12, 3, 0, 0,
                (short) colIndex, rowIndex, (short) colIndex + 1, rowIndex + 1);
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
        drawingPatriarch.createPicture(anchor, book.addPicture(picByteOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        picByteOut.close();
    }



    public static void main(String[] args) throws Exception{
        String path = ExcelImgUtil.class.getClassLoader().getResource("").getPath();
        String filePath=path+"excel";
        File file = new File(filePath+"/test.xlsx");
        File exportFile=new File("C:\\Users\\admin\\Desktop\\test2.xlsx");
        //删除备份文件
        boolean exists = exportFile.exists();
        if(exists){
            exportFile.delete();
        }
        FileUtil.copyFile(file,exportFile);
        File picture=new File(filePath+"/test1.png");
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(exportFile));
        final XSSFSheet sheet = wb.getSheetAt(0);// 得到Excel工作表对象
        XSSFRow row = sheet.createRow(1);
        row.setHeight((short)(100*20));
        row.createCell(0).setCellValue("test");
        XSSFDrawing drawingPatriarch = sheet.createDrawingPatriarch(); // 插入图片
        insertExcelPic(wb,drawingPatriarch,1,1,picture.getPath());
        sheet.setColumnWidth(2,6000);
        FileOutputStream out = new FileOutputStream(exportFile);
        wb.write(out);
        out.close();
        wb.close();

        //读取excel图片
        /*Map<String, PictureData> map = ExcelImgUtil.getPictures(sheet);//获取图片和位置
        System.out.println(map.keySet());*/


    }

}


