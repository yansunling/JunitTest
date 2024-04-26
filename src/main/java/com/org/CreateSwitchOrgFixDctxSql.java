package com.org;


import com.org.data.OrgData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.DruidComboPoolDataSource;
import com.yd.utils.datasource.YDDriverManagerDataSource;
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
public class CreateSwitchOrgFixDctxSql implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Qualifier("dataSource")
    @Autowired
    private YDDriverManagerDataSource ydDriverManagerDataSource;

    @Test
    public void test() throws Exception {
        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
        jdbcTemplate.setQueryTimeout(500);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);

        String sql = "select concat(table_schema,'.',table_name) from information_schema.`TABLES` where table_schema='dctx' and table_type!='VIEW'  and table_name not like 's2024%' ";
        List<String> tableFiles=jdbcTemplate.queryForList(sql, String.class);
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        Set<String> totalSet=new LinkedHashSet<>();
        for(String table:tableFiles){
            Map<String, String> sqlList = buildBaseSql(table);
            if(CollectionUtil.isNotEmpty(sqlList)){

                CountDownLatch countDownLatch = new CountDownLatch(orgDataList.size());
                for (OrgData orgData : orgDataList) {
                    executorService.submit(new FutureTask<String>(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            try {
                                String title = orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]切为" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]";
                                Set<String> newSqlList=new LinkedHashSet<>();


                                sqlList.forEach((column,item)->{
                                    String sql="select 1 as value from "+table+" where "+column+" regexp "+orgData.getOldOrgId().replaceAll("','","|")+" limit 1";
                                    List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

                                    sql="select 1 as value from "+table+" where "+column+" regexp "+orgData.getOldOrgName().replaceAll("','","|")+" limit 1";
                                    List<Map<String, Object>> resultName = jdbcTemplate.queryForList(sql);

                                    if(CollectionUtil.isNotEmpty(result)||CollectionUtil.isNotEmpty(resultName)){
                                        String newItem = SwitchUtil.replaceName(item, orgData);
                                        if(StringUtils.isNotBlank(newItem)){
                                            newSqlList.add(newItem);
                                        }
                                    }
                                });
                                if(CollectionUtil.isNotEmpty(newSqlList)){
                                    List<String> titleList=new ArrayList<>();
                                    titleList.add("\n\n--   "+title+"\n\n");
                                    titleList.addAll(newSqlList);
                                    totalSet.addAll(titleList);

                                }


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

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\dctx.sql");
        FileUtils.writeLines(allFile, "utf-8", totalSet);



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
        String filePath = getClass().getClassLoader().getResource("").getPath();
        List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/fixTable.tab"), "utf-8");

        try {
            String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + table + "' and table_schema='"+tables[0]+"' " +
                    "and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id','price_remark','product_type') and data_type not in('decimal','datetime','date','int') ";
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
                                if (SwitchUtil.containsChinese(column)) {
                                    column = "`" + column + "`";
                                }
                                String type="ID";
                                if (orgNameList.contains(newValue)) {
                                    type="名称";
                                }
                                if(tableFiles.contains(newTable)){
                                    sqlList.put(column,SwitchUtil.replaceSql(column, newTable, type));
                                }else{
                                    sqlList.put(column,SwitchUtil.matchColumn(column, newTable, type, concat));
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
