package com.excel;

import com.cmd.CmdUtil;
import com.http.DownFsmFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ExcelAppendPhoto implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public  void test()throws Exception{

        CmdUtil.closeWps();
        String sql="select * from crm.crm_base_customer limit 100000" ;

//        List<Map<String, Object>> listData = jdbcTemplate.queryForList(sql);
//        System.out.println(listData.size());
//        List<String> columns = Arrays.asList("customer_id","creator");
       /* String path = ExcelAppendPhoto.class.getClassLoader().getResource("").getPath();
        String filePath=path+"excel";
        File file = new File(filePath+"/cert.xlsx");
        ExcelAppendUtil.appendFile(file,listData,columns,1);*/

        String serialNo="crm_f930f0b1-5da2-4e7f-a01a-dca4cd3ff286_1";
        String host="http://localhost";
        File templateFile = DownFsmFileUtil.downFile(host, serialNo);

//        ExcelAppendUtil.appendExcelData(templateFile,listData,columns,2);

        //生成文件地址
        String newFilePath= "C:/Users/yansunling/Desktop/append/text.xlsx";
        FileOutputStream out = new FileOutputStream(newFilePath);
        ExcelAppendUtil.queryForStream(jdbcTemplate,sql,out,new ArrayList<>(),templateFile,2);




    }





}
