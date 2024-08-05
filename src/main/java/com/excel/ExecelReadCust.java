package com.excel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.excel.data.CRMX_base_market_messageData;
import com.excel.data.CrmLineSalesman;
import com.google.common.collect.Lists;
import com.yd.query.util.QueryVueUtil;
import com.yd.query.vo.QueryBean;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecelReadCust {
    public static void main(String[] args) throws Exception{
        String filePath="C:\\Users\\yansunling\\Desktop\\2.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<CrmLineSalesman> importDataList = CJExcelUtil.initImportExcelDatas(CrmLineSalesman.titleMap, listResult, CrmLineSalesman.class);

        Map<String,String> map=new HashMap<>();
        map.put("兰州","620100");
        map.put("西宁","630100");
        map.put("银川","640100");

        List<String> list=new ArrayList<>();
        importDataList.forEach(item->{
            if(StringUtils.isBlank(item.getCustomer_id())){
                return;
            }

            item.setSalesman_id("T2826");
            item.setLast_city(map.get(item.getLast_city()));
            String sql="INSERT ignore INTO crm.crm_base_customer_line_sales(serial_no, customer_id, last_city, salesman_id, creator, create_time)" +
                    " VALUES (uuid_short(), '"+item.getCustomer_id()+"', '"+item.getLast_city()+"', '"+item.getSalesman_id()+"', 'T1113', now());";
            System.out.println(sql);
            list.add(sql);

        });

        File outfile=new File("C:\\Users\\yansunling\\Desktop\\customer.sql");
        FileUtils.writeLines(outfile,"utf-8",list);


    }
}
