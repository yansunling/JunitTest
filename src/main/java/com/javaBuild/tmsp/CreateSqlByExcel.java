package com.javaBuild.tmsp;

import com.alibaba.fastjson.JSON;
import com.excel.CJExcelUtil;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import lombok.Data;

import java.io.File;
import java.util.*;

public class CreateSqlByExcel {
    public static void main(String[] args) throws Exception{


        String filePath="C:\\Users\\yansunling\\Desktop\\2.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<ExcelData> importDataList = CJExcelUtil.initImportExcelDatas(titleMap, listResult, ExcelData.class);

        Map<String,List<String>> mapRef=new LinkedHashMap<>();
        for(int i=0;i<importDataList.size();i++){
            ExcelData item = importDataList.get(i);
            if(StringUtils.isBlank(item.main_code)&& StringUtils.isNotBlank(item.getChild_code())){
                for(int j=i;j>=0;j--){
                    ExcelData beforeData = importDataList.get(j - 1);
                    if(StringUtils.isNotBlank(beforeData.getMain_code())){
                        item.setMain_code(beforeData.getMain_code());
                        break;
                    }
                }
            }
            if(StringUtils.isNotBlank(item.getMain_code())&&StringUtils.isNotBlank(item.getChild_code())){
                List<String> children = mapRef.get(item.getMain_code());
                if(children==null){
                    children=new ArrayList<>();
                }
                children.add(item.getChild_code());
                mapRef.put(item.getMain_code(),children);
            }

        }
        Set<String> keySet = mapRef.keySet();

        ArrayList<String> list = Lists.newArrayList("事故处理", "系统备案", "违规操作", "工作失误", "工作配合");

        Integer start=1;
        for(String key:keySet){
            String mainCode = "e"+StringUtils.apppendPre(start + "", 2, '0');
            String parentCode="e1";
            if(list.contains(key)){
                parentCode="e2";
            }
            String sql="INSERT INTO mdm.mdm_ddic_ddic_codes(sys_id,domain_id, code_type, code_name,code_value,code_order,remark, create_time, update_time, op_user_id,creator) VALUES " +
                    "('tmsp','error_type', '"+mainCode+"', '"+key+"','"+parentCode+"',"+start+",'', now(), now(), 'T1113','T1113');";
//            System.out.println(sql);
            Integer childStart=1;
            List<String> childSet = mapRef.get(key);

            for(String child:childSet){
               String childCode = mainCode+StringUtils.apppendPre(childStart + "", 2, '0');
               sql="INSERT INTO mdm.mdm_ddic_ddic_codes(sys_id,domain_id, code_type, code_name,code_value,code_order,remark, create_time, update_time, op_user_id,creator) VALUES " +
                       "('tmsp','error_subtype', '"+childCode+"', '"+child+"','"+mainCode+"',"+childStart+",'', now(), now(), 'T1113','T1113');";
               System.out.println(sql);
               childStart++;
           }
            start++;
        }


    }

    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("main_code","客户编码");
        titleMap.put("child_code","市场名称");


    }
    @Data
    public static class ExcelData{
        private String main_code;
        private String child_code;
    }

}
