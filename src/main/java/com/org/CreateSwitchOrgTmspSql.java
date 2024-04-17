package com.org;


import com.alibaba.fastjson.JSON;
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
public class CreateSwitchOrgTmspSql implements ApplicationContextAware {
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
        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\TL_TMSP.xlsx";
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
        SwitchUtil.deleteFolder(new File("C:\\Users\\yansunling\\Desktop\\tmsp\\"));
        jdbcTemplate.setQueryTimeout(500);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);
        //排除基础表
        List<String> sqlTotalList = new ArrayList<>();
        Map<String, List<String>> schemaMap = new HashMap<>();
        List<String> sqlBaseList = buildBaseSql(schemaMap);
        List<String> notSchema=new ArrayList<>();
        for (OrgData orgData : orgDataList) {
            List<String> newSqlList = new ArrayList<>();
            String fileName = orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]切为" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]";
            String newFileName = fileName.replaceAll("'", "");
            sqlBaseList.forEach(item->{
                String newItem = SwitchUtil.replaceName(item, orgData);
                if(StringUtils.isNotBlank(newItem)){
                    newSqlList.add(newItem);
                    Set<String> keySet = schemaMap.keySet();
                    boolean addFlag=true;
                    for(String key:keySet){
                        if(newItem.indexOf(" "+key+".")>0){
                            List<String> list = schemaMap.get(key);
                            String title="\n\n-- "+newFileName+"  \n\n";
                            if(!list.contains(title)){
                                list.add(title);
                            }
                            list.add(newItem);
                            addFlag=false;
                            break;
                        }
                    }
                    if(addFlag){
                        notSchema.add(newItem);
                    }
                }
            });




            File allFile = new File("C:\\Users\\yansunling\\Desktop\\tmsp\\" + newFileName + ".sql");
            sqlTotalList.add("\n\n\n");
            sqlTotalList.addAll(newSqlList);
            FileUtils.writeLines(allFile, "utf-8", newSqlList);
            sqlTotalList.addAll(newSqlList);
        }
        File allFile = new File("C:\\Users\\yansunling\\Desktop\\tmsp\\allSql.sql");
        FileUtils.writeLines(allFile,"utf-8",sqlTotalList);

        schemaMap.forEach((key,list)->{
            try {
                if(CollectionUtil.isNotEmpty(list)){
                    Set<String> set=new LinkedHashSet<>();
                    set.addAll(list);
                    File schemaFile = new File("C:\\Users\\yansunling\\Desktop\\tmsp\\"+key+".sql");
                    FileUtils.writeLines(schemaFile,"utf-8",set);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        File notSchemaFile = new File("C:\\Users\\yansunling\\Desktop\\tmsp\\notSchema.sql");
        FileUtils.writeLines(notSchemaFile,"utf-8",notSchema);
    }

    @SneakyThrows
    public List<String> buildBaseSql(Map<String, List<String>> schemaMap) {

        List<String> schemaList = Arrays.asList("tmsp","bmsp");
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
        List<String> orgList = new ArrayList<>();
        List<String> orgNameList = new ArrayList<>();
        maps.forEach(item -> {
            orgList.add(item.get("org_id") + "");
            orgNameList.add(item.get("org_name") + "");
        });
        String filePath = getClass().getClassLoader().getResource("").getPath();
        List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/errorTable.txt"), "utf-8");
        String tableFilesStr = StringUtils.join(",", tableFiles.toArray()) + ",";
        //排除基础表
        List<String> sqlList = new ArrayList<>();
        List<String> odlSqlList = FileUtils.readLines(new File(filePath + "java/table/tmsp_org_adjust_template_new.sql"), "utf-8");
        odlSqlList.forEach(item -> {
            boolean addFlag = true;
            for (String table : tableFiles) {
                if (item.indexOf(" " + table + " ") >= 0) {
                    addFlag = false;
                    break;
                }
            }
            if (addFlag) {
                sqlList.add(item);
            }
        });
        String existSql = StringUtils.join(",", sqlList.toArray());
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        List<String> allData = new ArrayList<>();
        for (String schema : schemaList) {
            //设置初始值
            schemaMap.put(schema,new ArrayList<>());

            String sql = "select table_name from information_schema.`TABLES` where table_schema='" + schema + "' and table_type!='VIEW' ";
            List<String> tableList = jdbcTemplate.queryForList(sql, String.class);
            CountDownLatch countDownLatch = new CountDownLatch(tableList.size());
            List<String> totalSql = new ArrayList<>();
            for (String table : tableList) {
                String newTable = schema + "." + table;
                executorService.submit(new FutureTask<String>(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        try {
                            //已存在配置
                            if (existSql.indexOf(" " + newTable + " ") > 0) {
                                return "";
                            }

                            if (tableFilesStr.indexOf(newTable + ",") > 0) {
                                return "";
                            }
                            if (SwitchUtil.matchTables(newTable)) {
                                return "";
                            }
                            String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + table + "' and table_schema='" + schema + "' " +
                                    "and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id') and data_type not in('decimal','datetime','date','int') ";
                            List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);
                            if (CollectionUtil.isNotEmpty(columnList)) {
                                List<String> sqlList = new ArrayList<>();
                                columnList.forEach(column -> {
                                    String dataSql = "select `" + column + "` from " + newTable + " where  ifnull(`" + column + "`,'')!=''  limit 1";
                                    List<String> valueList = jdbcTemplate.queryForList(dataSql, String.class);
                                    log.info("newTable:"+newTable+",columns:"+column+",valueList:"+JSON.toJSONString(valueList));
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

                                    if (orgList.contains(newValue)) {
                                        sqlList.add(SwitchUtil.matchColumn(column, newTable, "ID", concat));
                                    } else if (orgNameList.contains(newValue)) {
                                        sqlList.add(SwitchUtil.matchColumn(column, newTable, "名称", concat));
                                    }
                                });
                                totalSql.addAll(sqlList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            countDownLatch.countDown();
                        }
                        return "";
                    }
                }));

            }
            countDownLatch.await();
            if (CollectionUtil.isNotEmpty(totalSql)) {
                allData.add("\n\n  -- " + schema.toUpperCase());
                allData.addAll(totalSql);

            }
        }
        sqlList.addAll(allData);
        return sqlList;
    }


}
