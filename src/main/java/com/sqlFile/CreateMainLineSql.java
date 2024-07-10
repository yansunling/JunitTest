package com.sqlFile;


import com.yd.utils.common.CollectionUtil;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CreateMainLineSql implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public  void createLineSql() throws Exception {
        String mainLine="38";
        String mainLineName="曲靖";
        String mainLineCode="QJ";
        String crmSql="INSERT INTO crm.cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) " +
                "VALUES ('main_lines', '"+mainLine+"', '"+mainLineName+"', now(), now(), 'T1113');";

        String mdmSql="INSERT INTO mdm.mdm_ddic_ddic_codes(sys_id, domain_id, code_type, code_name, code_value, code_order, remark, op_user_id, update_time, creator, create_time)" +
                " VALUES ('crm', 'cust_name_main_lines', '"+mainLine+"', '"+mainLineName+"', '"+mainLineCode+"', "+mainLine+", '', 'T1113', now(), 'T1113', now());";

        List<String> sqlList=new ArrayList<>();
        sqlList.add(crmSql);
        sqlList.add(mdmSql);

        String sql="select area_code from mdm.mdm_ddic_geo_area where area_code_name like '%"+mainLineName+"%' " +
                "union select city_code from mdm.mdm_ddic_geo_city where city_code_name like '%"+mainLineName+"%'";
        List<String> areaList = jdbcTemplate.queryForList(sql, String.class);
        if(CollectionUtil.isNotEmpty(areaList)){
            String areaCode = areaList.get(0);
            String cityCode = areaList.get(0).substring(0,4)+"00";
            String addSql="INSERT INTO crm.crm_main_line_area_ref(serial_no, main_lines, main_lines_name, area_code, add_area_code, creator, create_time) " +
                    "VALUES (uuid_short(), '"+mainLine+"', '"+mainLineName+"', '"+cityCode+"', '"+areaCode+"', 'T1113', now());";
            sqlList.add(addSql);
        }
        System.out.println("======================\n\n");
        sqlList.forEach(item->{
            System.out.println(item+"\n\n");
        });
        System.out.println("\n\n\n======================");

    }


}
