package com.org.newSwitch;


import com.org.data.OrgData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.DateUtils;
import com.yd.utils.common.StringUtils;
import lombok.SneakyThrows;
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
import java.util.*;
import java.util.concurrent.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateSqlCheck implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;

    }

    @Qualifier("jdbcTemplateYL")
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void test() throws Exception {
        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
        jdbcTemplate.setQueryTimeout(600);

        List<String> schemaList = Arrays.asList("auth");

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        Set<String> newSqlList=new LinkedHashSet<>();
        for(String schema:schemaList){
            String sql = "select table_name from information_schema.`TABLES` where " +
                    "table_schema='" + schema + "' and table_type!='VIEW' and table_name not like 'foc%' and table_name not like 'cip%'" +
                    " and table_name not in('tmsp_send_order_cust_edit_log','tmsp_order_route_org_detail_bak','tmsp_order_route_org_detail','tmsp_net_org','tmsp','tmsp_msg_send_sms','bmsp_report_outorder_order_cust','bmsp_report_inorder_putfee_order_cust','bmsp_report_inorder_putfee_customer_cust','tmsp_alter_order_report','bmsp_report_inorder_customer_cust','tmsp_depart_ontime_rate_report_item','tmsp_msg_result_sms','bmsp_report_inorder_order_cust' )  "
                    ;
            List<String> tableFiles = jdbcTemplate.queryForList(sql, String.class);
            String filePath = getClass().getClassLoader().getResource("").getPath();
            List<String> errorTable = FileUtils.readLines(new File(filePath + "java/table/errorTable.txt"), "utf-8");

            for(String table:tableFiles){
                String newTable = schema + "." + table;
                if(errorTable.contains(newTable)){
                    continue;
                }

                Map<String, String> sqlList = buildBaseSql(newTable);
                if(CollectionUtil.isNotEmpty(sqlList)){
                    CountDownLatch countDownLatch = new CountDownLatch(orgDataList.size());
                    for (OrgData orgData : orgDataList) {
                        executorService.submit(new FutureTask<String>(new Callable<String>() {
                            @Override
                            public String call() throws Exception {
                                try {
                                    sqlList.forEach((column,item)->{
                                        String sql="select 1 as value from "+newTable+" where "+column+" in( "+orgData.getOldOrgId()+" ) limit 1";
                                        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
                                        if(CollectionUtil.isNotEmpty(result)){
                                          newSqlList.add("select * from "+newTable+" where "+column+" in("+orgData.getOldOrgId()+");");
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }finally {
                                    countDownLatch.countDown();
                                }
                                return "";
                            }
                        }));
                    }
                    countDownLatch.await();

                }
            }
            File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\notSwitch"+ schemaList.get(0) +".sql");
            FileUtils.writeLines(allFile, "utf-8", newSqlList);





        }










    }

    @SneakyThrows
    public Map<String,String> buildBaseSql(String newTable) {
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
        List<String> orgList = new ArrayList<>();
        List<String> orgNameList = new ArrayList<>();
        maps.forEach(item -> {
            orgList.add(item.get("org_id") + "");
            orgNameList.add(item.get("org_name") + "");
        });
        Map<String,String> sqlList = new HashMap();
        String[] tables = newTable.split("\\.");
        String table = tables[1];
        try {
            String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + table + "' and table_schema='"+tables[0]+"' " +
                    "and column_name not in('serial_no','create_user_id','is_bill','update_user_id','remark','salesman_id','price_remark','product_type','is_valid','is_red','unclear_remark') and data_type not in('decimal','datetime','date','int') ";
            List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);
            if (CollectionUtil.isNotEmpty(columnList)) {

                ExecutorService executorService = Executors.newFixedThreadPool(50);
                CountDownLatch countDownLatch = new CountDownLatch(columnList.size());

                columnList.forEach(tempColumn -> {

                    executorService.submit(new FutureTask<String>(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            try {

                                String column=tempColumn;

                                String dataSql = "select `" + column + "` from " + newTable + " where  length(`" + column + "`)>=4   limit 1";
                                List<String> valueList = jdbcTemplate.queryForList(dataSql, String.class);
                                if (CollectionUtil.isEmpty(valueList)) {
                                    return "";
                                }
                                String newValue = valueList.get(0);
                                if (StringUtils.isBlank(newValue)) {
                                    return "";
                                }
                                boolean concat = false;
                                if (newValue.indexOf(",") > 0) {
                                    String[] split = newValue.split(",");
                                    newValue = split[0];
                                    concat = true;
                                }
                                if(newValue.indexOf(">")>0){
                                    String[] split = newValue.split(">");
                                    newValue = split[0];
                                    concat = true;
                                }
                                if (SwitchUtil.containsChinese(column)) {
                                    column = "`" + column + "`";
                                }
                                if (orgList.contains(newValue)||(newValue.startsWith("25")&&newValue.indexOf(".")<0)||(newValue.startsWith("35")&&newValue.indexOf(".")<0)) {
                                    sqlList.put(column,SwitchUtil.replaceSql(column, newTable, "ID"));

                                } else if (orgNameList.contains(newValue)) {
                                    sqlList.put(column,SwitchUtil.replaceSql(column, newTable, "名称"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }finally {
                                countDownLatch.countDown();
                            }
                            return "";
                        }
                    }));
                });
                countDownLatch.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlList;
    }


}
