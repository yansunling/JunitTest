package com.org.oneTable;


import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateTemplateSwitchOrgSql implements ApplicationContextAware {
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
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
        List<String> orgList = new ArrayList<>();
        List<String> orgNameList = new ArrayList<>();
        maps.forEach(item -> {
            orgList.add(item.get("org_id") + "");
            orgNameList.add(item.get("org_name") + "");
        });
        List<String> errorTable = new ArrayList<>();
        String filePath = getClass().getClassLoader().getResource("").getPath();
//        List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/fixTable.tab"), "utf-8");

        List<String> tableFiles= Arrays.asList("tmsp.tmsp_hand_schedule_car");

        String tableFilesStr = StringUtils.join(",", tableFiles.toArray()) + ",";
        //排除基础表
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        CountDownLatch countDownLatch = new CountDownLatch(tableFiles.size());
        List<String> totalSql = new ArrayList<>();
        for (String newTable : tableFiles) {
            executorService.submit(new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    try {
                        //不是固定表跳过
                        if (tableFilesStr.indexOf(newTable + ",") < 0) {
                            return "";
                        }
                        String[] tabs = newTable.split("\\.");

                        String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + tabs[1] + "' and TABLE_SCHEMA='" + tabs[0] + "' " +
                                "and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id') and data_type not in('decimal','datetime','date','int') ";
                        List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);
                        if (CollectionUtil.isNotEmpty(columnList)) {
                            List<String> sqlList = new ArrayList<>();
                            columnList.forEach(column -> {


                                String dataSql = "select `" + column + "` from " + newTable + " where  length(`" + column + "`)>=4     limit 1";
                                List<String> valueList = jdbcTemplate.queryForList(dataSql, String.class);
                                if (CollectionUtil.isEmpty(valueList)) {
                                    return;
                                }
                                String newValue = valueList.get(0);
                                if (StringUtils.isBlank(newValue)) {
                                    return;
                                }
                                if (newValue.indexOf(",") > 0) {
                                    String[] split = newValue.split(",");
                                    newValue = split[0];
                                }
                                if (SwitchUtil.containsChinese(column)) {
                                    column = "`" + column + "`";
                                }
                                if (orgList.contains(newValue)) {
                                    sqlList.add(replaceSql(column, newTable, "ID"));

                                } else if (orgNameList.contains(newValue)) {
                                    sqlList.add(replaceSql(column, newTable, "名称"));
                                }
                            });
                            totalSql.addAll(sqlList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorTable.add(newTable);
                    } finally {
                        countDownLatch.countDown();
                    }
                    return "";
                }
            }));

        }
        countDownLatch.await();
        if (CollectionUtil.isNotEmpty(totalSql)) {
            File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\template.sql");
            FileUtils.writeLines(allFile, "utf-8", totalSql);
        }

    }


    public String replaceSql(String column, String newTable, String type) {

        String[] split = newTable.split("\\.");
        String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + split[1] + "' and  table_schema='" + split[0] + "';";

        List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);
        if(column.indexOf("`")<0){
            column="`"+column+"`";
        }

        String join ="`"+StringUtils.join("`,`", columnList.toArray())+"`";
        String newJoin = join.replace(column, "'<新机构" + type + ">'");
        String sql = "replace into  " + newTable + " select " + newJoin + " from "+newTable+" where " + column + " in('<老机构" + type + ">');";




        String areaRegex = "big_area|region|大区";
        Pattern pattern = Pattern.compile(areaRegex);
        // 创建Matcher对象

        Matcher matcher = pattern.matcher(column);
        if (matcher.find()) {
            String regionJoin = join.replaceAll(column, "'<新机构大区" + type + ">'");
            sql = "replace into  " + newTable + " select " + regionJoin + " from "+newTable+" where " + column + " in('<老机构大区" + type + ">');";
        }
        String smallRegex = "small_area|district_id|小区";
        Pattern smallPattern = Pattern.compile(smallRegex);
        Matcher smallMatcher = smallPattern.matcher(column);
        if (smallMatcher.find()) {
            String regionJoin = join.replaceAll(column, "'<新机构小区" + type + ">'");
            sql = "replace into  " + newTable + " select " + regionJoin + " from "+newTable+" where " + column + " in('<老机构小区" + type + ">');";
        }
        return sql;
    }


}
