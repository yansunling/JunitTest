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


        String filePath="C:\\Users\\yansunling\\Desktop\\2.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<ExcelData> importDataList = CJExcelUtil.initImportExcelDatas(titleMap, listResult, ExcelData.class);
        List<String> emptyList=new ArrayList<>();
        for(int i=0;i<importDataList.size();i++){
            ExcelData item = importDataList.get(i);


            String str=" ERROR_SUBTYPE_"+item.getError_subtype().toUpperCase()+"(\""+item.getError_subtype()+"\",\""+item.getError_subtype_name()+"\",\"temp\"),";
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

            if(StringUtils.equals(item.getPic1(),"×")&&StringUtils.equals(item.getPic2(),"×")&&StringUtils.equals(item.getPic3(),"×")
                    &&StringUtils.equals(item.getPic4(),"×")&&StringUtils.equals(item.getPic5(),"×")){
                emptyList.add("TMSP_ERROR_SUBTYPE.ERROR_SUBTYPE_"+item.getError_subtype().toUpperCase());
            }


        }

            System.out.println(StringUtils.join(",",emptyList.toArray()));




    }

    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("error_subtype_name","市场名称");
        titleMap.put("error_subtype","客户编码");
        titleMap.put("order_id","运单号");
        titleMap.put("car_no","车皮号");
        titleMap.put("order_weight","开单重量");
        titleMap.put("actual_weight","实际重量");
        titleMap.put("weight_diff","重量差值");
        titleMap.put("order_cube","开单体积");
        titleMap.put("actual_volume","实际体积");
        titleMap.put("cube_diff","体积差值");
        titleMap.put("pic1","标识/标签照片");
        titleMap.put("pic2","测量照片");
        titleMap.put("pic3","清单照片（系统截图）");
        titleMap.put("pic4","货物照片");
        titleMap.put("pic5","运输轨迹照片（系统截图）");
    }
    @Data
    public static class ExcelData{
        private String error_subtype;
        private String error_subtype_name;
        private String order_id;
        private String car_no;
        private String actual_weight;
        private String actual_volume;
        private String order_weight;
        private String order_cube;
        private String cube_diff;
        private String weight_diff;
        private String pic1;
        private String pic2;
        private String pic3;
        private String pic4;
        private String pic5;

    }

    private static Map<String,String> getValue(){
        Map<String,String> data=new HashMap<>();
        data.put("查出违禁品，拒收品","e0101");
        data.put("收运违禁品","e0102");
        data.put("收运危险品","e0103");
        data.put("受理超限品","e0104");
        data.put("收货未清点","e0105");
        data.put("计量差错","e0106");
        data.put("开单差错","e0201");
        data.put("标识差错","e0202");
        data.put("系统未操作，延迟操作，错误操作","e0301");
        data.put("未签运输合同","e0302");
        data.put("合同不规范","e0303");
        data.put("封签差错","e0304");
        data.put("货票不符","e0305");
        data.put("延迟入库","e0306");
        data.put("分装","e0307");
        data.put("扣押货物","e0308");
        data.put("点件不清","e0309");
        data.put("违规装卸码货","e0401");
        data.put("堵车门","e0402");
        data.put("车皮未清理","e0403");
        data.put("未做防护","e0404");
        data.put("交接不清","e0501");
        data.put("货损签收差错","e0502");
        data.put("误交付","e0503");
        data.put("未及时安排送货","e0504");
        data.put("虚假签收","e0505");
        data.put("反签收","e0506");
        data.put("交接不规范、延误","e0601");
        data.put("回单不符","e0602");
        data.put("回单丢失","e0603");
        data.put("时效延误","e0701");
        data.put("报价问题","e0801");
        data.put("计量误差","e0802");
        data.put("运费未及时转入公司账户","e0803");
        data.put("费用多收","e0804");
        data.put("偷货","e0901");
        data.put("事故上报","e1001");
        data.put("事故瞒报","e1002");
        data.put("事故处理不及时","e1003");
        data.put("异常上报","e1004");
        data.put("理赔坏账未及时上报","e1005");
        data.put("差错上报","e1006");
        data.put("时效备案","e1101");
        data.put("价格备案","e1102");
        data.put("异常货物处理","e1201");
        data.put("隐瞒捏造事实经过","e1202");
        data.put("违规操作","e1203");
        data.put("未按前端环节要求操作","e1301");
        data.put("系统，app等操作失误","e1302");
        data.put("未通知提货","e1303");
        data.put("二次送货","e1304");
        data.put("工作失误","e1305");
        data.put("工作配合","e1401");
        data.put("服务态度（对外）","e1402");
        return data;
    }


}
