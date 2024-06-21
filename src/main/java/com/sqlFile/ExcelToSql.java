package com.sqlFile;

import com.yd.utils.common.ExcelReader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelToSql {
    public static void main(String[] args) throws Exception{
        String filePath="C:/Users/yansunling/Desktop/1.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<String> sqlList=new ArrayList<>();
        for(int i=0;i<listResult.size();i++){
            Object[] result=listResult.get(i);
            String sql="INSERT ignore INTO crm.crm_base_customer_attach(serial_no, customer_id, order_warn, order_warn_date, status, remark, op_user_id, op_user_name, update_time, creator, creator_name, create_time, delete_flag, remind_type)" +
                    " VALUES (uuid_short(), '"+result[0]+"', '"+result[1]+"', '2999-12-31', '1', NULL, 'T1113', '颜孙令', now(), 'T1113', '颜孙令', now(), '0', 'PC');";
            sqlList.add(sql);
            System.out.println(sql);
        }
        File outfile=new File("C:/Users/yansunling/Desktop/excelSql.sql");
        FileUtils.writeLines(outfile,"utf-8",sqlList);
    }
}
