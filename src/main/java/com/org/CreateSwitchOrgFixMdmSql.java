package com.org;


import cn.hutool.core.collection.CollUtil;
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
public class CreateSwitchOrgFixMdmSql implements ApplicationContextAware {
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
        SwitchUtil.deleteFolder(new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\mdm\\"));
        jdbcTemplate.setQueryTimeout(500);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);
        List<OrgData> newOrgDataList = new ArrayList<>();
        newOrgDataList.add(orgDataList.get(0));
        String filePath = getClass().getClassLoader().getResource("").getPath();
        List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/mdm/mdm.tab"), "utf-8");


        List<String> totalSetAll=new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for(String table:tableFiles){
                Set<String> totalSet=new LinkedHashSet<>();
                CountDownLatch countDownLatch = new CountDownLatch(orgDataList.size());
                for (OrgData orgData : orgDataList) {
                    executorService.submit(new FutureTask<String>(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            try {
                                String title = orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]切为" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]";
                                Set<String> newSqlList=new LinkedHashSet<>();
                                List<String> sqlList = buildBaseSql(table, orgData);
                                sqlList.forEach(item->{
                                    String newItem = SwitchUtil.replaceName(item, orgData);
                                    if(StringUtils.isNotBlank(newItem)){
                                        newSqlList.add(newItem);
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
                String[] tables = table.split("\\.");
                File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\mdm\\" +  tables[1] + ".sql");
                FileUtils.writeLines(allFile, "utf-8", totalSet);
                totalSetAll.addAll(totalSet);
            }
        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\mdm\\mdm.sql");
        FileUtils.writeLines(allFile, "utf-8", totalSetAll);


    }

    @SneakyThrows
    public List<String> buildBaseSql(String newTable,OrgData orgData) {

        List<String> sqlList = new ArrayList<>();
        String[] tables = newTable.split("\\.");
        String table = tables[1];
        try {
            String filePath = getClass().getClassLoader().getResource("").getPath();
            List<String> odlSqlList = FileUtils.readLines(new File(filePath + "java/table/mdm/mdm_template.sql"), "utf-8");
            odlSqlList.forEach(item -> {
                if (item.indexOf(" " + newTable + " ") >= 0) {
                    sqlList.add(item);
                }
            });
            if(CollUtil.isNotEmpty(sqlList)){
                return sqlList;
            }
            String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + table + "' " +
                    "and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id','price_remark') and data_type not in('decimal','datetime','date','int') ";
            List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);
            if (CollectionUtil.isNotEmpty(columnList)) {

                columnList.forEach(column -> {
                    String dataSql = "select `" + column + "` from " + newTable + " where  `" + column + "` in("+orgData.getOldOrgId()+","+orgData.getOldOrgName()+")   limit 1";
                    List<String> valueList = jdbcTemplate.queryForList(dataSql, String.class);
                    if (CollectionUtil.isEmpty(valueList)) {
                        return;
                    }
                    String newValue = valueList.get(0);
                    if (StringUtils.isBlank(newValue)) {
                        return;
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
                    if (!SwitchUtil.containsChinese(newValue)) {
                        sqlList.add(SwitchUtil.matchColumn(column, newTable, "ID", concat));

                    } else {
                        sqlList.add(SwitchUtil.matchColumn(column, newTable, "名称", concat));
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlList;
    }


}
