package com.excel;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.dy.components.annotations.CJ_column;
import com.yd.common.exttype.Money;
import lombok.Data;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class JSONToExcel {
    public static void main(String[] args) throws Exception{
        String filePath="C:\\Users\\yansunling\\Desktop\\1.json";

        String json = FileUtil.readAsString(new File(filePath));
        List<RecordData> recordData = JSON.parseArray(json, RecordData.class);
        Map<String, List<RecordData>> tempMap = recordData.stream().collect(Collectors.groupingBy(RecordData::getSend_trans_cust_id));
        List<Map<String,Object>> dataList=new ArrayList<>();
        List<String> list = Arrays.asList("P2412150227", "P2412130052");
        list.forEach(transCustId->{
            List<RecordData> tempList=tempMap.get(transCustId);
            tempList.forEach(item->{
                Map<String, Object> objectMap = BeanUtil.beanToMap(item);
                dataList.add(objectMap);
            });
        });
        String file="C:\\Users\\yansunling\\Desktop\\transCustId.xlsx";
        ExcelsUtil.createExcel(file,dataList,RecordData.getExcelTitle(),Arrays.asList("actual_delivery"));

    }
    @Data
    static class RecordData{
        @CJ_column(name = "配载单号")
        private String send_trans_cust_id;

        @CJ_column(name = "运单号")
        private String order_id;

        @CJ_column(name = "件数")
        private Integer goods_count;

        @CJ_column(name = "实送费")
        private Money actual_delivery;


        public static Map<String,String> getExcelTitle(){
            Map<String, String> titleMap = new LinkedHashMap<>();
            titleMap.put("send_trans_cust_id","配载单号");
            titleMap.put("order_id","运单号");
            titleMap.put("goods_count","件数");
            titleMap.put("actual_delivery","实送费");
            return titleMap;

        }
    }

}
