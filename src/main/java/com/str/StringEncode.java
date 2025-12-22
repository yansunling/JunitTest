package com.str;


import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class StringEncode implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;

    }

    @Qualifier("jdbcTemplateYL")
    @Autowired
    private JdbcTemplate jdbcTemplateYL;



    @Qualifier("dataSource")
    @Autowired
    private YDDriverManagerDataSource ydDriverManagerDataSource;

    @Test
    public void test() throws Exception {
        String sql="select serial_no,cancel_remark from tmsp.tmsp_send_app_doc where cancel_remark!='' and cancel_remark  REGEXP '[^0-9a-zA-Z.]'";
        List<Map<String, Object>> maps = jdbcTemplateYL.queryForList(sql);

        List<String> sqlList=new ArrayList<>();
        maps.forEach(item->{
            try {
                String cancel_remark=item.get("cancel_remark")+"";
                String serial_no=item.get("serial_no")+"";

                cancel_remark= new String(cancel_remark.getBytes("iso-8859-1"),"utf-8");

                System.out.println(cancel_remark);
                String updateSql="update tmsp.tmsp_send_app_doc set cancel_remark='"+cancel_remark+"' where serial_no='"+serial_no+"';";
                sqlList.add(updateSql);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });
        FileUtils.writeLines(new File("C:/Users/yansunling/Desktop/cancel.sql"),sqlList);



    }





}
