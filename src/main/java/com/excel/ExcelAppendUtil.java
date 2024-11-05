package com.excel;

import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.excel.data.QUERYConstant;
import com.excel.data.QueryExportConfigBean;
import com.http.DownFsmFileUtil;
import com.yd.common.exception.CIPServiceException;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.common.runtime.CIPRuntime;
import com.yd.common.utils.RedisUtils;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

@Slf4j
@Component
public class ExcelAppendUtil {


    private  static Logger logger = org.slf4j.LoggerFactory.getLogger(ExcelAppendUtil.class);

    public static void main(String[] args) {
        String serialNo="portal_tms_85340605-d4c1-4de2-80ff-e3c6f51c5433_2";
        String host="http://localhost";
        File templateFile = DownFsmFileUtil.downFile(host, serialNo,"");






    }










    public static void appendFile(File templateFile, List<Map<String, Object>> listData, List<String> columns,int startRow) throws Exception{
        //生成文件地址
        String newFilePath= "C:/Users/yansunling/Desktop/append/"+templateFile.getName();
        //删除重名文件
        ExcelsUtil.delFile(newFilePath);
        File file = new File(newFilePath);
        //复制模板
        FileUtil.copyFile(templateFile,file);
        if(CollectionUtil.isEmpty(listData)){
            return;
        }
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

    @SneakyThrows
    public static void appendExcelData(File templateFile, List<Map<String, Object>> listData, Collection<String> keySet, int startRow){

        //生成文件地址
        String newFilePath= "C:/Users/yansunling/Desktop/append/"+templateFile.getName();
        //删除重名文件
        ExcelsUtil.delFile(newFilePath);
        File file = new File(newFilePath);
        //复制模板
        FileUtil.copyFile(templateFile,file);

        if(CollectionUtil.isEmpty(listData)){
            return;
        }
        List<String> columns=new ArrayList<>();
        columns.addAll(keySet);
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        SXSSFWorkbook writeWB = new SXSSFWorkbook(workbook,100);
        SXSSFSheet sheet = writeWB.getSheetAt(0);
        //设置字体大小
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)10);
        //正常单元格
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        FileOutputStream out = new FileOutputStream(file);

        for(int rowNum=0;rowNum<listData.size();rowNum++){
            Map<String, Object> resultPO = listData.get(rowNum);
            //创建行
            SXSSFRow row = sheet.createRow(rowNum+startRow);
            for(int cellNum=0;cellNum<columns.size();cellNum++){
                //通过反射获得值
                String column=columns.get(cellNum);
                Object objValue = resultPO.get(column);
                String value=String.valueOf(objValue);
                if(objValue==null){
                    value="";
                }
                //创建单元格
                SXSSFCell cell = row.createCell(cellNum);
                cell.setCellStyle(cellStyle);
                if(objValue instanceof BigDecimal){
                    double doubleValue = ((BigDecimal) objValue).doubleValue();
                    cell.setCellValue(doubleValue);
                }else  if(objValue instanceof Integer){
                    int intValue = ((Integer) objValue).intValue();
                    cell.setCellValue(intValue);
                }else{
                    cell.setCellValue(value);
                }
            }

        }
        writeWB.write(out);
        out.close();
        writeWB.dispose();
        workbook.close();
        System.gc();
    }

    @SneakyThrows
    public static void queryForStream(JdbcTemplate jdbcTemplate, String sql,
                                      OutputStream os,List<Object> params,File file,int startRow){
        logger.info("params:"+ JSONObject.toJSONString(params)+"download sql:"+sql);



        jdbcTemplate.execute(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement preparedStatement = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
                if(CollectionUtils.isNotEmpty(params)){
                    for(int i=0;i<params.size();i++){
                        preparedStatement.setObject(i+1,params.get(i));
                    }
                }
                return preparedStatement;
            }}, new PreparedStatementCallback<T>() {
            @Override
            public T doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
                ResultSet rs =null;
                try {
                    pstmt.setFetchSize(Integer.MIN_VALUE);
                    pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
                    rs= pstmt.executeQuery();
                    ResultSetMetaData rsm = rs.getMetaData();
                    int rowNum=0;
                    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
                    SXSSFWorkbook writeWB = new SXSSFWorkbook(workbook,100);
                    SXSSFSheet sheet = writeWB.getSheetAt(0);
                    //设置字体大小
                    Font font = workbook.createFont();
                    font.setFontName("微软雅黑");
                    font.setFontHeightInPoints((short)10);
                    //正常单元格
                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setFont(font);
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);

                    //中文转化映射本地缓存
                    Map<String,String> cacheMapRef=new HashMap<>();
                    while(rs.next()){
                        SXSSFRow row = sheet.createRow(rowNum+startRow);
                        for(int i=1; i<=rsm.getColumnCount(); i++){
                            SXSSFCell cell = row.createCell(i-1);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(rs.getString(i));
                        }
//                        if (rowNum % 100 == 0) { // 每batchSize个单元格写入一次临时文件
//                            sheet.flushRows(100);
//                        }
                        rowNum++;

                    }
                    writeWB.write(os);
                    writeWB.dispose();
                    workbook.close();
                } catch (Exception e) {
                    logger.info("download sql:"+sql,e);
                    throw new CIPServiceException(new CIPErrorCode(9991,e.getMessage()));

                }finally {
                    if(rs!=null) {
                        rs.close();
                    }
                }
                return null;
            }
        });
    }


    private static String getRefValueName(String cacheId,String value,Map<String,String> cacheMapRef,String cacheFormat){
        if(org.apache.commons.lang3.StringUtils.isBlank(value)){
            return value;
        }
        //生成缓存keyDownVueFileUtil
        String key=cacheId+"|"+value;
        String refValue = cacheMapRef.get(key);
        if(org.apache.commons.lang3.StringUtils.isBlank(refValue)){
            List<String> mapValue=new ArrayList<>();
            try {
                mapValue = RedisUtils.getMapValue(QUERYConstant.REDIS_QUERY_RESULT_PREX + cacheId, String.class, value);
            } catch (Exception e) {
                logger.info("getRefValueName error,cacheId:"+cacheId);
            }
            if(CollectionUtil.isNotEmpty(mapValue)){
                //有映射值
                refValue= mapValue.get(0);
            }else{
                //查询不到映射值
                refValue=value;
            }
            if(org.apache.commons.lang3.StringUtils.isBlank(refValue)){
                //查询不到映射值
                refValue=value;
                //匹配不到值默认为空
                if(org.apache.commons.lang3.StringUtils.equals(cacheFormat,QUERYConstant.FORMAT_TYPE_CACHE_NULL)){
                    refValue="";
                }
            }
            //本地缓存映射值
            cacheMapRef.put(key,refValue);
        }
        return refValue;
    }
















}
