package com.javaBuild.tmsp;

import com.excel.CJExcelUtil;
import com.yd.utils.common.ExcelReader;
import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReadExcelCreateSql {
    public static void main(String[] args) throws Exception{


        String filePath="C:\\Users\\yansunling\\Desktop\\1.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<ExcelData> importDataList = CJExcelUtil.initImportExcelDatas(titleMap, listResult, ExcelData.class);
        List<String> outLineList=new ArrayList<>();
        for(int i=0;i<importDataList.size();i++){
            ExcelData item = importDataList.get(i);
            String sql="INSERT ignore INTO `crm`.`crm_base_customer_attach`(`serial_no`, `customer_id`, `order_warn`, `order_warn_date`, `status`, `remark`, `op_user_id`, `op_user_name`, `update_time`, `creator`, `creator_name`, `create_time`, `delete_flag`, `remind_type`) " +
                    "VALUES ('"+item.getColumnData(1)+"', '"+item.getColumnData(1)+"', '"+item.getColumnData(2)+"', '2999-12-31', '1', NULL, 'T1113', '颜孙令', now(), 'T1113', '颜孙令', now(), '0', 'PC');";
            System.out.println(sql);
            outLineList.add(sql);
        }
        File outfile=new File("C:\\Users\\yansunling\\Desktop\\insert.sql");
        FileUtils.writeLines(outfile,"utf-8",outLineList);

    }

    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("column1","第1列");
        titleMap.put("column2","第2列");
        titleMap.put("column3","第3列");
        titleMap.put("column4","第4列");
        titleMap.put("column5","第5列");
        titleMap.put("column6","第6列");
        titleMap.put("column7","第7列");
    }
    @Data
    public static class ExcelData{
        private String column1;
        private String column2;
        private String column3;
        private String column4;
        private String column5;
        private String column6;
        private String column7;


        public String getColumnData(int i){
            try {
                // 获取Person类的Class对象
                Class<?> clazz = this.getClass();
                // 根据属性名查找对应的Field对象
                Field field = clazz.getDeclaredField("column"+i);
                // 设置访问权限，因为private属性默认不能直接访问
                field.setAccessible(true);
                // 获取并返回该Field的值
                Object invoke = field.get(this);
                if(invoke!=null){
                    return invoke+"";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }


    }

}
