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
        String filePath="C:\\Users\\admin\\Desktop\\1.xlsx";

        List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, "一期", 50);
        String[] titleStr=new String[37];
        String[] firstStr = list.get(0);
        System.arraycopy(firstStr,0,titleStr,0,titleStr.length);

        list.remove(0);
        List<MarketExeclData> allData=new ArrayList<>();
        List<Map<String, Object>> excelData=new ArrayList<>();

        for(String[] str:list) {
           String street_name=str[0];
            for(int i=1;i<titleStr.length;i++){
                MarketExeclData data=new MarketExeclData();
                String area_name=titleStr[i];
                String market_num=str[i];
                //号为空跳过
                if(StringUtils.isBlank(market_num)||StringUtils.equals(market_num,"null")){
                    continue;
                }

                data.setArea_name(area_name);
                data.setStreet_name(street_name);
                data.setMarket_num(market_num);
                allData.add(data);
                Map<String, Object> stringObjectMap = BeanUtil.beanToMap(data);
                excelData.add(stringObjectMap);

            }
        }

        XSSFWorkbook wb = new XSSFWorkbook();
        WorkbookUtil.createWorkBook(wb,"一期", MarketExeclData.getExcelTitle(), null,excelData);

        OutputStream out = new FileOutputStream("C:\\Users\\admin\\Desktop\\2.xlsx");

        wb.write(out);
        out.close();



    }
}
