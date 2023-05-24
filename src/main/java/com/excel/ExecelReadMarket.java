package com.excel;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.excel.data.CRMX_base_market_messageData;
import com.yd.query.util.QueryVueUtil;
import com.yd.query.vo.QueryBean;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExecelReadMarket {
    public static void main(String[] args) throws Exception{
        String filePath="C:\\Users\\admin\\Desktop\\crmx_customer_import_market.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<CRMX_base_market_messageData> importDataList = CJExcelUtil.initImportExcelDatas(CRMX_base_market_messageData.titleMap, listResult, CRMX_base_market_messageData.class);

        QueryBean queryBean=new QueryBean("crm_base_market_message_list", "V1.0.0");
        QueryVueUtil vueUtil=new QueryVueUtil();
        vueUtil.setHOST_ADDRESS("https://tlwl.uat.tuolong56.com");

        List<Map<String,Object>> errorMarket=new ArrayList<>();
        List<Map<String,Object>> notFindMarket=new ArrayList<>();
        List<Map<String,Object>> correctMarket=new ArrayList<>();
        //测试只取10条
        List<List<CRMX_base_market_messageData>> partition = Lists.partition(importDataList, 10);
        List<CRMX_base_market_messageData> temp = partition.get(0);
        int index=1;
        for(CRMX_base_market_messageData messageData :importDataList) {
            index++;
            //没有号跳过
            if(StringUtils.isBlank(messageData.getNum())){
                continue;
            }

            //清除街查询条件
            CRMX_base_market_messageData paramData = new CRMX_base_market_messageData();
            BeanUtil.copyProperties(messageData, paramData);
            paramData.setColumn_4("");
            List<CRMX_base_market_messageData> rows = vueUtil.sendSearch(queryBean, paramData, CRMX_base_market_messageData.class);
            if (CollectionUtil.isNotEmpty(rows)) {
                if (rows.size() == 1) {
                    CRMX_base_market_messageData queryResult = rows.get(0);
                    //街信息相同
                    if (StringUtils.equals(queryResult.getColumn_4(), messageData.getColumn_4())) {
                        Map<String, Object> mapMessage = BeanUtil.beanToMap(messageData);
                        correctMarket.add(mapMessage);
                    } else {
                        System.out.println("第"+index+"客户编码:"+messageData.getCustomer_id()+"错误街:" + messageData.getColumn_4() + ";正确街:" + queryResult.getColumn_4());
                        messageData.setColumn_4(queryResult.getColumn_4());
                        Map<String, Object> mapMessage = BeanUtil.beanToMap(messageData);
                        errorMarket.add(mapMessage);
                    }
                } else {
                    Map<String, Object> mapMessage = BeanUtil.beanToMap(messageData);
                    notFindMarket.add(mapMessage);
                }
            } else {
                Map<String, Object> mapMessage = BeanUtil.beanToMap(messageData);
                correctMarket.add(mapMessage);
            }
        }





        XSSFWorkbook wb = new XSSFWorkbook();
        WorkbookUtil.createWorkBook(wb,"错误市场", CRMX_base_market_messageData.titleMap, null,errorMarket);
        WorkbookUtil.createWorkBook(wb,"未找到市场", CRMX_base_market_messageData.titleMap, null,notFindMarket);
        WorkbookUtil.createWorkBook(wb,"正确市场", CRMX_base_market_messageData.titleMap, null,correctMarket);

        OutputStream out = new FileOutputStream("C:\\Users\\admin\\Desktop\\market.xlsx");

        wb.write(out);
        out.close();




        excelReader.closeFile();//关闭文件(一定要确保文件的关闭，否则出错，在文件删除前)




    }
}
