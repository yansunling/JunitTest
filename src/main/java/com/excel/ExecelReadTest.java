package com.excel;

import cn.hutool.core.bean.BeanUtil;

import com.yd.utils.common.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExecelReadTest {
    public static void main(String[] args) throws Exception{
        String filePath="C:\\Users\\yansunling\\Desktop\\七月份货量统计表.xlsx";

        List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, "Sheet2", 2);
        list.forEach(item->{
            for(int i=0;i<item.length;i++){
                System.out.println(i+"----"+item[i]);
            }


        });



    }
}
