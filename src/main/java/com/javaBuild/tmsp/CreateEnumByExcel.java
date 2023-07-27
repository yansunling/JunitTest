package com.javaBuild.tmsp;

import com.alibaba.fastjson.JSON;
import com.excel.CJExcelUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import lombok.Data;
import org.apache.poi.util.StringUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class CreateEnumByExcel {
    public static void main(String[] args) throws Exception{


        String filePath="C:\\Users\\yansunling\\Desktop\\1.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<ExcelData> importDataList = CJExcelUtil.initImportExcelDatas(titleMap, listResult, ExcelData.class);
        for(int i=0;i<importDataList.size();i++){
            ExcelData item = importDataList.get(i);
            String str=" ERROR_SUBTYPE_"+item.getError_subtype()+"(\""+item.getError_subtype()+"\",\""+item.getError_subtype_name()+"\",\"temp\"),";
            List<String> list=new ArrayList<>();
            if(StringUtils.equals(item.getOrder_id(),"√")){
                list.add("order_id");
            }
            if(StringUtils.equals(item.getCar_no(),"√")){
                list.add("car_no");
            }
            if(StringUtils.equals(item.getActual_weight(),"√")){
                list.add("actual_weight");
            }
            if(StringUtils.equals(item.getActual_volume(),"√")){
                list.add("actual_volume");
            }
            str=str.replace("temp",StringUtils.join(",",list.toArray()));
            System.out.println(str);
        }


    }

    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("error_subtype","客户编码");
        titleMap.put("error_subtype_name","市场名称");
        titleMap.put("order_id","运单号");
        titleMap.put("car_no","车皮号");
        titleMap.put("actual_weight","实际重量");
        titleMap.put("actual_volume","实际体积");
    }
    @Data
    public static class ExcelData{
        private String error_subtype;
        private String error_subtype_name;
        private String order_id;
        private String car_no;
        private String actual_weight;
        private String actual_volume;
    }

}
