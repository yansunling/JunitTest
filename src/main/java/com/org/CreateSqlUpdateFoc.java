package com.org;


import com.org.data.OrgData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
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
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateSqlUpdateFoc implements ApplicationContextAware {
    private static final int DAILY_QUERY_THREADS = 20;

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
        jdbcTemplate.setQueryTimeout(5000);


        List<String> tableFiles = Arrays.asList("tmsp.foc_plugins_review_report", "tmsp.foc_plugins_review_log");

        Map<String, String> mapSql = new HashMap<>();

        mapSql.put("tmsp.foc_plugins_review_report", "report_org_id");
        mapSql.put("tmsp.foc_plugins_review_log", "review_org_id");


        Map<String, String> timeColumnMap = new HashMap<>();
        timeColumnMap.put("tmsp.foc_plugins_review_report", "report_time");
        timeColumnMap.put("tmsp.foc_plugins_review_log", "create_time");
        LocalDate queryStartDate = LocalDate.now().minusMonths(2);
        LocalDate queryEndDate = LocalDate.now().plusDays(1);
        Set<String> allTotalSet = new LinkedHashSet<>();
        ExecutorService dailyQueryExecutor = Executors.newFixedThreadPool(DAILY_QUERY_THREADS);
        try {
            for (OrgData orgData : orgDataList) {
                Set<String> rowSet = new LinkedHashSet<>();
                for (String table : tableFiles) {
                    String column = mapSql.get(table);
                    if (StringUtils.isBlank(column)) {
                        continue;
                    }
                    String timeColumn = timeColumnMap.get(table);
                    try {
                        Set<String> result = querySerialNosByDay(table, column, timeColumn,
                                orgData.getOldOrgId(), queryStartDate, queryEndDate, dailyQueryExecutor);
                        if (CollectionUtil.isNotEmpty(result)) {
                            String title = "-- " + orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]切" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]\n\n\n";
                            String updateSql = "update " + table + " set " + column + "=" + orgData.getNewOrgId() + " where serial_no in('" + StringUtils.join("','", result.toArray()) + "');";
                            rowSet.add(title);
                            rowSet.add(updateSql + "\n\n");
                        }
                    } catch (Exception e) {
                        log.error("按天查询失败，table={}, oldOrgId={}", table, orgData.getOldOrgId(), e);
                    }
                }
                allTotalSet.addAll(rowSet);
            }
        } finally {
            dailyQueryExecutor.shutdownNow();
        }


        String[] split = tableFiles.get(0).split("\\.");
        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\tmsp_foc.sql");
        FileUtils.writeLines(allFile, "utf-8", allTotalSet);


    }

    private Set<String> querySerialNosByDay(String table, String orgColumn, String timeColumn,
                                            String oldOrgIds, LocalDate startDate, LocalDate endDate,
                                            ExecutorService queryExecutor) throws Exception {
        Set<String> result = new LinkedHashSet<>();
        List<Future<List<String>>> queryFutures = new ArrayList<>();
        for (LocalDate queryDate = startDate; queryDate.isBefore(endDate); queryDate = queryDate.plusDays(1)) {
            LocalDate currentDate = queryDate;
            queryFutures.add(queryExecutor.submit(new Callable<List<String>>() {
                @Override
                public List<String> call() {
                    LocalDate nextDate = currentDate.plusDays(1);
                    String sql = "select serial_no from " + table
                            + " where " + orgColumn + " in(" + oldOrgIds + ")"
                            + " and " + timeColumn + ">=? and " + timeColumn + "<?";
                    return jdbcTemplate.queryForList(sql,
                            new Object[]{java.sql.Date.valueOf(currentDate), java.sql.Date.valueOf(nextDate)}, String.class);
                }
            }));
        }
        try {
            for (Future<List<String>> queryFuture : queryFutures) {
                List<String> dailyResult = queryFuture.get();
                if (CollectionUtil.isNotEmpty(dailyResult)) {
                    result.addAll(dailyResult);
                }
            }
        } catch (Exception e) {
            for (Future<List<String>> queryFuture : queryFutures) {
                queryFuture.cancel(true);
            }
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw e;
        }
        return result;
    }

}
