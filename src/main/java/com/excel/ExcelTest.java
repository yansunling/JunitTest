package com.excel;

import com.cmd.CmdUtil;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ExcelTest implements ApplicationContextAware {
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
        String sql="select * from crm.crm_base_customer_cert_img where creator='T1113' and date_format(create_time,'%Y-%m-%d %H:%i')='2022-05-27 10:27' and file_name not in('临欠通告模板.pptx','临欠通告.pdf' )\n" +
                " limit 10;";
        List<Map<String, Object>> listData = jdbcTemplate.queryForList(sql);
        List<String> columns = Arrays.asList("serial_no", "customer_id", "file_seq_no");
        String path = ExcelTest.class.getClassLoader().getResource("").getPath();
        String filePath=path+"excel";
        File file = new File(filePath+"/cert.xlsx");
        ExcelAppend.appendFile(file,listData,columns);

    }





}
