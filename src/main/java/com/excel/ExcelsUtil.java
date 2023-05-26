package com.excel;


import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.io.FileUtil;
import com.dy.components.annotations.CJ_column;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.yd.common.runtime.CIPRuntimeConfigure;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.*;

import javax.imageio.ImageIO;


/**
 * 重写平台导入功能
 * @author Administrator
 * 2019-03-19
 */
@Slf4j
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


    @SneakyThrows
    public static File downFile(String serialNo,String FSM_URL){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(FSM_URL+"/fsm/api/fsm_api/download.do?file_app_id=crm&file_serial_no="+serialNo);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
        httppost.setConfig(requestConfig);
        CloseableHttpResponse response = httpclient.execute(httppost);
        File file=null;
        try {
            System.out.println(response.getStatusLine());
            Header firstHeader = response.getFirstHeader("Content-Disposition");
            String fileName = firstHeader.getValue().replace("attachment;filename=","");
            fileName= URLDecoder.decode(fileName, "utf-8");
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                InputStream is = resEntity.getContent();
                file = new File("E:/"+fileName);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int len = -1;
                while((len = is.read(buffer) )!= -1){
                    fos.write(buffer, 0, len);
                }
                fos.close();
                is.close();
            }
            EntityUtils.consume(resEntity);
        } finally {
            response.close();
        }
        return file;
    }

    public static void delFile(String path){
        File file = new File(path);
        boolean exists = file.exists();
        if(exists){
            file.delete();
        }
    }

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


    public static boolean isImage(File file) {
        try {
            // 尝试读取图片文件并检查是否为支持的图片格式
            BufferedImage image = ImageIO.read(file);
            if(image != null){
                return true;
            }
        } catch (IOException e) {
            log.info("isImage:"+file.getAbsolutePath(),e);
        }
        return false;
    }


    @SneakyThrows
    public static <T> List<T> excelToObj(List<String[]> list, Class<T> mainClazz) {
        List<T> data = new ArrayList<>();
        if(list.size()<1){
            return data;
        }
        Field titleField = mainClazz.getDeclaredField("title");
        // 设置操作权限，允许访问private属性
        titleField.setAccessible(true);
        // 创建Person类的对象
        T obj = mainClazz.newInstance();
        // 获取person对象的name属性值
        List<String> columns = (List<String>) titleField.get(obj);
        try {
            Field field;
            for (int i = 1; i < list.size(); i++) {
                String[] record = list.get(i);
                //碰到空行跳出
                if(record==null){
                    break;
                }
                int j = 0;
                T po = mainClazz.newInstance();
                for (String column : columns) {
                    if(j>=record.length){
                        break;
                    }
                    String str = String.valueOf(record[j]);
                    if (StringUtils.isNotBlank(str)) {
                        str = str.trim();
                    }
                    //值为空值时，字符串默认为空串
                    if(StringUtils.equalsIgnoreCase("null",str)){
                        str="";
                    }
                    field = po.getClass().getDeclaredField(column);
                    field.setAccessible(true);
                    Class clazz = field.getType();
                    if (clazz.isEnum()) {//判断是不是枚举
                        Method method = clazz.getMethod("values");
                        Object[] enums = (Object[]) method.invoke(null);
                        //设置枚举值
                        Method enumMethod = clazz.getMethod("nameToEnum",String.class);
                        Object invoke = enumMethod.invoke(enums[0], str);
                        field.set(po, invoke);
                        //设置属性值
                        Method codeType = clazz.getMethod("nameToCode",String.class);
                        String value = codeType.invoke(enums[0],str) + "";
                        Field valueField = po.getClass().getDeclaredField(column.replace("_name", ""));
                        valueField.setAccessible(true);
                        valueField.set(po,value);
                    }else {
                        field.set(po, str);
                    }

                    j++;
                }
                data.add(po);
            }
            return data;


        } catch (Exception e) {
            log.info("convertExcelData error",e);
            throw new RuntimeException("导入数据有误");
        }
    }


















    public static void main(String[] args) throws Exception {
//        getDataFromExcel("C:\\Users\\yansunling\\Desktop\\2022上半年交旅劳保清单(1).xlsx");

        getDataFromExcel("C:\\Users\\yansunling\\Desktop\\crmx_market_customer.xlsx");



    }
}