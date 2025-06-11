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
public class CreateSwitchOrgLimitSql implements ApplicationContextAware {
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
        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
//        SwitchUtil.deleteFolder(new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\org\\"));
        jdbcTemplateYL.setQueryTimeout(500);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);
        //排除基础表
        Set<String> sqlTotalList = new LinkedHashSet<>();
        Map<String, List<String>> schemaMap = new HashMap<>();
        Map<String, List<String>> schemaMapAll = new HashMap<>();

        List<String> notSchema=new ArrayList<>();
        for (OrgData orgData : orgDataList) {
            List<String> newSqlList = new ArrayList<>();
            List<String> sqlBaseList = buildBaseSql(schemaMap,orgData);
            String fileName = "新组织:"+orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]旧组织:" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]";
            String newFileName = fileName.replaceAll("'", "");
            sqlBaseList.forEach(item->{
                String newItem = SwitchUtil.replaceName(item, orgData);
                if(StringUtils.isNotBlank(newItem)){
                    newSqlList.add(newItem);
                    Set<String> keySet = schemaMap.keySet();
                    boolean addFlag=true;
                    for(String key:keySet){
                        if(newItem.indexOf(" "+key+".")>0){
                            List<String> list = schemaMapAll.get(key);
                            if(list==null){
                                list=new ArrayList<>();
                            }

                            String title="\n\n-- "+newFileName+"  \n\n";
                            if(!list.contains(title)){
                                list.add(title);
                            }
                            list.add(newItem);
                            addFlag=false;
                            schemaMapAll.put(key,list);
                            break;
                        }
                    }
                    if(addFlag){
                        notSchema.add(newItem);
                    }
                }
            });




            File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\org\\" + newFileName.replaceAll(":","") + ".sql");
            sqlTotalList.add("\n\n\n");
            sqlTotalList.addAll(newSqlList);
            FileUtils.writeLines(allFile, "utf-8", newSqlList);
            sqlTotalList.addAll(newSqlList);
        }


        schemaMapAll.forEach((key,list)->{
            try {
                if(CollectionUtil.isNotEmpty(list)){

                    Set<String> set=new LinkedHashSet<>();
                    set.addAll(list);
                    File schemaFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\schema\\"+key+".sql");
                    FileUtils.writeLines(schemaFile,"utf-8",set);

                    sqlTotalList.addAll(set);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\org\\allSql.sql");
        FileUtils.writeLines(allFile,"utf-8",sqlTotalList);


//        File notSchemaFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\schema\\notSchema.sql");
//        FileUtils.writeLines(notSchemaFile,"utf-8",notSchema);
    }

    @SneakyThrows
    public List<String> buildBaseSql(Map<String, List<String>> schemaMap,OrgData orgData) {
        String schemaSql = "select table_schema from information_schema.`TABLES` " +
                "where  table_schema not in('dctx','report','marketing','bml','dctx','wac','acs','als','costx','information_schema'," +
                "'query','dct','ouyang','performance_schema','portal','biq','das','gms','hcmp','click','dts','fsm','costx','mdm','mms','pay','task','tms','log','vip','kjob','crmx','jeewx-boot') " +
                "  group by table_schema";
        List<String> schemaList = jdbcTemplateYL.queryForList(schemaSql, String.class);
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplateYL.queryForList(orgSql);
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
                if(StringUtils.isBlank(table)){
                    continue;
                }
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

            String sql = "select table_name from information_schema.`TABLES` where table_schema='" + schema + "' and table_type!='VIEW' and table_name not like 's2024%'  ";
            List<String> tableList = jdbcTemplateYL.queryForList(sql, String.class);
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
                                    "and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id')  and character_maximum_length>12 and data_type not in('decimal','datetime','date','bigint','int') ";
                            List<String> columnList = jdbcTemplateYL.queryForList(columnsSql, String.class);
                            if (CollectionUtil.isNotEmpty(columnList)) {
                                List<String> sqlList = new ArrayList<>();
                                columnList.forEach(column -> {
                                    String dataSql = "select `" + column + "` from " + newTable + " where  `" + column + "` in("+orgData.getOldOrgId()+","+orgData.getOldOrgName()+")   limit 1";
                                    List<String> valueList = jdbcTemplateYL.queryForList(dataSql, String.class);
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

                                    if (orgList.contains(newValue)||(newValue.startsWith("3501")&&newValue.indexOf(".")<0||(newValue.startsWith("2501")&&newValue.indexOf(".")<0))) {
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
