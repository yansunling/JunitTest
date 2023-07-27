package com.javaBuild.tmsp;

import com.excel.CJExcelUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import lombok.Data;

import java.io.File;
import java.util.*;

public class CreateSqlByExcel {
    public static void main(String[] args) throws Exception{


        String filePath="C:\\Users\\yansunling\\Desktop\\品质差错.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<ExcelData> importDataList = CJExcelUtil.initImportExcelDatas(titleMap, listResult, ExcelData.class);

        Map<String,List<String>> mapRef=new LinkedHashMap<>();
        for(int i=0;i<importDataList.size();i++){
            ExcelData item = importDataList.get(i);
            if(StringUtils.isBlank(item.main_code)&& StringUtils.isNotBlank(item.getChild_code())){
                ExcelData beforeData = importDataList.get(i - 1);
                item.setMain_code(beforeData.getMain_code());
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
        Integer start=1;
        for(String key:keySet){
            String mainCode = StringUtils.apppendPre(start + "", 2, '0');
            String sql="INSERT INTO mdm.mdm_ddic_ddic_codes(sys_id,domain_id, code_type, code_name,code_value,code_order,remark, create_time, update_time, op_user_id,creator) VALUES " +
                    "('tmsp','error_type', '"+mainCode+"', '"+key+"','"+mainCode+"',"+start+",'', now(), now(), 'T1113','T1113');";
            System.out.println(sql);
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
//            System.out.println("\n\n");
        }




        /*String data="采购、租赁、自建、受捐、受赠";
        String domainId="asset_source";
       String[] clazzList=data.split("、");
        StringBuffer sql=new StringBuffer();
       for(int i=0;i<clazzList.length;i++){
           String value = clazzList[i];
           String key=String.valueOf(i+1);
           sql.append("INSERT INTO cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES" +
                   " ('"+domainId+"', '"+key+"', '"+value+"', now(), now(), 'T1113');\n");
       }
        System.out.println(sql.toString());*/
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
