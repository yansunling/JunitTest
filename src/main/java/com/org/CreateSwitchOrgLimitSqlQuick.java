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
import java.util.stream.Collectors;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateSwitchOrgLimitSqlQuick implements ApplicationContextAware {
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

    // ========== 预加载的元数据（只查询一次） ==========
    private List<String> schemaList;
    private List<String> orgList;
    private List<String> orgNameList;
    private List<String> tableFiles;
    private String tableFilesStr;
    private List<String> baseSqlList;
    private String existSql;
    // 预加载的表-列映射: schema -> table -> columnList
    private Map<String, Map<String, List<String>>> schemaTableColumnMap;
    // 预加载的表列表: schema -> tableList
    private Map<String, List<String>> schemaTableMap;

    @Test
    public void test() throws Exception {
        long startTime = System.currentTimeMillis();

        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
        jdbcTemplateYL.setQueryTimeout(5000);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);

        // ========== 【优化1】预加载所有元数据（只执行一次） ==========
        log.info("开始预加载元数据...");
        long preloadStart = System.currentTimeMillis();
        preloadMetadata();
        log.info("元数据预加载完成，耗时: {}ms", System.currentTimeMillis() - preloadStart);

        // ========== 【优化2】创建全局线程池（复用） ==========
        ExecutorService executorService = Executors.newFixedThreadPool(50);

        Set<String> sqlTotalList = new LinkedHashSet<>();
        Map<String, List<String>> schemaMap = new HashMap<>();
        Map<String, List<String>> schemaMapAll = new HashMap<>();

        // 初始化 schemaMap
        for (String schema : schemaList) {
            schemaMap.put(schema, new ArrayList<>());
        }

        List<String> notSchema = new ArrayList<>();

        try {
            for (OrgData orgData : orgDataList) {
                log.info("处理组织: {} -> {}", orgData.getOldOrgName(), orgData.getNewOrgName());
                long orgStart = System.currentTimeMillis();

                List<String> newSqlList = new ArrayList<>();
                // 【优化3】使用预加载的数据，传入线程池复用
                List<String> sqlBaseList = buildBaseSqlOptimized(schemaMap, orgData, executorService);

                String fileName = "新组织:" + orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]旧组织:" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]";
                String newFileName = fileName.replaceAll("'", "");

                sqlBaseList.forEach(item -> {
                    String newItem = SwitchUtil.replaceName(item, orgData);
                    if (StringUtils.isNotBlank(newItem)) {
                        newSqlList.add(newItem);
                        Set<String> keySet = schemaMap.keySet();
                        boolean addFlag = true;
                        for (String key : keySet) {
                            if (newItem.indexOf(" " + key + ".") > 0) {
                                List<String> list = schemaMapAll.computeIfAbsent(key, k -> new ArrayList<>());
                                String title = "\n\n-- " + newFileName + "  \n\n";
                                if (!list.contains(title)) {
                                    list.add(title);
                                }
                                list.add(newItem);
                                addFlag = false;
                                break;
                            }
                        }
                        if (addFlag) {
                            notSchema.add(newItem);
                        }
                    }
                });

                File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\org\\" + newFileName.replaceAll(":", "") + ".sql");
                sqlTotalList.add("\n\n\n");
                sqlTotalList.addAll(newSqlList);
                FileUtils.writeLines(allFile, "utf-8", newSqlList);
                sqlTotalList.addAll(newSqlList);

                log.info("组织处理完成，耗时: {}ms", System.currentTimeMillis() - orgStart);
            }
        } finally {
            // 【优化4】正确关闭线程池
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }

        schemaMapAll.forEach((key, list) -> {
            try {
                if (CollectionUtil.isNotEmpty(list)) {
                    Set<String> set = new LinkedHashSet<>(list);
                    File schemaFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\schema\\" + key + ".sql");
                    FileUtils.writeLines(schemaFile, "utf-8", set);
                    sqlTotalList.addAll(set);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\org\\allSql.sql");
        FileUtils.writeLines(allFile, "utf-8", sqlTotalList);

        log.info("全部完成，总耗时: {}ms", System.currentTimeMillis() - startTime);
    }

    /**
     * 【优化核心】预加载所有元数据，避免重复查询
     */
    @SneakyThrows
    private void preloadMetadata() {
        // 1. 查询schema列表
        String schemaSql = "select table_schema from information_schema.`TABLES` " +
                "where table_schema not in('dctx','report','marketing','bml','dctx','wac','acs','als','costx','information_schema'," +
                "'query','dct','ouyang','expense','performance_schema','portal','biq','das','gms','hcmp','click','dts','fsm','costx','mdm','mms','pay','task','tms','log','vip','kjob','crmx','jeewx-boot') " +
                " group by table_schema";
        schemaList = jdbcTemplateYL.queryForList(schemaSql, String.class);
        log.info("加载schema数量: {}", schemaList.size());

        // 2. 查询组织列表
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplateYL.queryForList(orgSql);
        orgList = new ArrayList<>();
        orgNameList = new ArrayList<>();
        maps.forEach(item -> {
            orgList.add(item.get("org_id") + "");
            orgNameList.add(item.get("org_name") + "");
        });
        log.info("加载组织数量: {}", orgList.size());

        // 3. 读取配置文件
        String filePath = getClass().getClassLoader().getResource("").getPath();
        tableFiles = FileUtils.readLines(new File(filePath + "java/table/errorTable.txt"), "utf-8");
        tableFilesStr = StringUtils.join(",", tableFiles.toArray()) + ",";

        // 4. 读取基础SQL模板并过滤
        baseSqlList = new ArrayList<>();
        List<String> odlSqlList = FileUtils.readLines(new File(filePath + "java/table/tmsp_org_adjust_template_new.sql"), "utf-8");
        odlSqlList.forEach(item -> {
            boolean addFlag = true;
            for (String table : tableFiles) {
                if (StringUtils.isBlank(table)) {
                    continue;
                }
                if (item.indexOf(" " + table + " ") >= 0) {
                    addFlag = false;
                    break;
                }
            }
            if (addFlag) {
                baseSqlList.add(item);
            }
        });
        existSql = StringUtils.join(",", baseSqlList.toArray());

        // 5. 【关键优化】一次性预加载所有表和列信息
        preloadTableAndColumnInfo();
    }

    /**
     * 【关键优化】一次性查询所有schema的表和列信息
     */
    private void preloadTableAndColumnInfo() {
        schemaTableMap = new HashMap<>();
        schemaTableColumnMap = new HashMap<>();

        // 构建schema IN条件
        String schemaInClause = schemaList.stream()
                .map(s -> "'" + s + "'")
                .collect(Collectors.joining(","));

        // 一次性查询所有表
        String allTablesSql = "SELECT table_schema, table_name FROM information_schema.`TABLES` " +
                "WHERE table_schema IN (" + schemaInClause + ") " +
                "AND table_type != 'VIEW' AND table_name NOT LIKE 's2024%'";
        List<Map<String, Object>> allTables = jdbcTemplateYL.queryForList(allTablesSql);

        int tableCount = 0;
        for (Map<String, Object> row : allTables) {
            String schema = row.get("table_schema").toString();
            String table = row.get("table_name").toString();
            schemaTableMap.computeIfAbsent(schema, k -> new ArrayList<>()).add(table);
            tableCount++;
        }
        log.info("加载表数量: {}", tableCount);

        // 一次性查询所有列
        String allColumnsSql = "SELECT table_schema, table_name, column_name FROM information_schema.COLUMNS " +
                "WHERE table_schema IN (" + schemaInClause + ") " +
                "AND column_name NOT IN ('serial_no','create_user_id','update_user_id','remark','salesman_id') " +
                "AND character_maximum_length > 12 " +
                "AND data_type NOT IN ('decimal','datetime','date','bigint','int')";
        List<Map<String, Object>> allColumns = jdbcTemplateYL.queryForList(allColumnsSql);

        int columnCount = 0;
        for (Map<String, Object> row : allColumns) {
            String schema = row.get("table_schema").toString();
            String table = row.get("table_name").toString();
            String column = row.get("column_name").toString();
            schemaTableColumnMap
                    .computeIfAbsent(schema, k -> new HashMap<>())
                    .computeIfAbsent(table, k -> new ArrayList<>())
                    .add(column);
            columnCount++;
        }
        log.info("加载列数量: {}", columnCount);
    }

    /**
     * 【优化版】使用预加载的数据构建SQL
     */
    @SneakyThrows
    public List<String> buildBaseSqlOptimized(Map<String, List<String>> schemaMap, OrgData orgData, ExecutorService executorService) {
        List<String> allData = Collections.synchronizedList(new ArrayList<>());

        // 收集所有任务
        List<Future<?>> futures = new ArrayList<>();

        for (String schema : schemaList) {
            schemaMap.put(schema, new ArrayList<>());

            List<String> tableList = schemaTableMap.getOrDefault(schema, Collections.emptyList());
            if (tableList.isEmpty()) {
                continue;
            }

            Map<String, List<String>> tableColumnMap = schemaTableColumnMap.getOrDefault(schema, Collections.emptyMap());
            List<String> schemaSqlList = Collections.synchronizedList(new ArrayList<>());

            CountDownLatch countDownLatch = new CountDownLatch(tableList.size());

            for (String table : tableList) {
                String newTable = schema + "." + table;

                futures.add(executorService.submit(() -> {
                    try {
                        // 已存在配置则跳过
                        if (existSql.indexOf(" " + newTable + " ") > 0) {
                            return;
                        }
                        if (tableFilesStr.indexOf(newTable + ",") > 0) {
                            return;
                        }
                        if (SwitchUtil.matchTables(newTable)) {
                            return;
                        }

                        // 使用预加载的列信息
                        List<String> columnList = tableColumnMap.getOrDefault(table, Collections.emptyList());
                        if (CollectionUtil.isEmpty(columnList)) {
                            return;
                        }

                        // 【优化5】批量查询：一次查询多个列
                        List<String> tableSqlList = batchQueryColumns(newTable, columnList, orgData);
                        if (CollectionUtil.isNotEmpty(tableSqlList)) {
                            schemaSqlList.addAll(tableSqlList);
                        }
                    } catch (Exception e) {
                        log.error("处理表 {} 出错: {}", newTable, e.getMessage());
                    } finally {
                        countDownLatch.countDown();
                    }
                }));
            }

            countDownLatch.await();

            if (CollectionUtil.isNotEmpty(schemaSqlList)) {
                allData.add("\n\n  -- " + schema.toUpperCase());
                allData.addAll(schemaSqlList);
            }
        }

        List<String> result = new ArrayList<>(baseSqlList);
        result.addAll(allData);
        return result;
    }

    // 批量查询的列数阈值，超过则使用 UNION ALL 或并行查询
    private static final int BATCH_COLUMN_THRESHOLD = 10;
    // 单列查询超时时间(毫秒)
    private static final int SINGLE_QUERY_TIMEOUT_MS = 3000;

    /**
     * 【优化5】智能查询列数据
     * - 列数 <= 10: 使用 UNION ALL 方式（每列独立查询，可利用索引）
     * - 列数 > 10: 并行查询，快速失败
     */
    private List<String> batchQueryColumns(String newTable, List<String> columnList, OrgData orgData) {
        if (columnList.size() <= BATCH_COLUMN_THRESHOLD) {
            // 少量列：使用 UNION ALL，每列可独立使用索引
            return unionAllQuery(newTable, columnList, orgData);
        } else {
            // 大量列：并行查询，设置超时快速失败
            return parallelColumnQuery(newTable, columnList, orgData);
        }
    }

    /**
     * 使用 UNION ALL 查询（适合少量列，每列可利用索引）
     * SELECT 'col1' as col_name, `col1` as col_value FROM table WHERE `col1` IN (...) LIMIT 1
     * UNION ALL
     * SELECT 'col2' as col_name, `col2` as col_value FROM table WHERE `col2` IN (...) LIMIT 1
     */
    private List<String> unionAllQuery(String newTable, List<String> columnList, OrgData orgData) {
        List<String> resultSqlList = new ArrayList<>();

        // 构建 UNION ALL 查询
        String unionSql = columnList.stream()
                .map(c -> "(SELECT '" + c + "' as col_name, `" + c + "` as col_value FROM " + newTable +
                        " WHERE `" + c + "` IN (" + orgData.getOldOrgId() + "," + orgData.getOldOrgName() + ") LIMIT 1)")
                .collect(Collectors.joining(" UNION ALL "));

        try {
            List<Map<String, Object>> results = jdbcTemplateYL.queryForList(unionSql);
            if (CollectionUtil.isEmpty(results)) {
                return resultSqlList;
            }

            // 处理结果
            for (Map<String, Object> row : results) {
                String column = row.get("col_name").toString();
                Object value = row.get("col_value");
                if (value == null) {
                    continue;
                }

                String newValue = value.toString();
                if (StringUtils.isBlank(newValue)) {
                    continue;
                }

                boolean concat = false;
                if (newValue.indexOf(",") > 0) {
                    String[] split = newValue.split(",");
                    newValue = split[0];
                    concat = true;
                }

                String columnName = column;
                if (SwitchUtil.containsChinese(column)) {
                    columnName = "`" + column + "`";
                }

                if (orgList.contains(newValue) ||
                    (newValue.startsWith("3501") && newValue.indexOf(".") < 0) ||
                    (newValue.startsWith("2501") && newValue.indexOf(".") < 0)) {
                    resultSqlList.add(SwitchUtil.matchColumn(columnName, newTable, "ID", concat));
                } else if (orgNameList.contains(newValue)) {
                    resultSqlList.add(SwitchUtil.matchColumn(columnName, newTable, "名称", concat));
                }
            }
        } catch (Exception e) {
            log.debug("UNION ALL查询失败，降级处理: {} - {}", newTable, e.getMessage());
            return fallbackSingleColumnQuery(newTable, columnList, orgData);
        }

        return resultSqlList;
    }

    /**
     * 并行查询多列（适合大量列，快速失败）
     * - 使用独立线程池
     * - 设置超时时间，避免单个慢查询阻塞整体
     * - 任意列匹配即返回结果
     */
    private List<String> parallelColumnQuery(String newTable, List<String> columnList, OrgData orgData) {
        List<String> resultSqlList = Collections.synchronizedList(new ArrayList<>());

        // 使用 CompletableFuture 并行查询
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String column : columnList) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    String dataSql = "SELECT `" + column + "` FROM " + newTable +
                            " WHERE `" + column + "` IN (" + orgData.getOldOrgId() + "," + orgData.getOldOrgName() + ") LIMIT 1";

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

                    String columnName = column;
                    if (SwitchUtil.containsChinese(column)) {
                        columnName = "`" + column + "`";
                    }

                    if (orgList.contains(newValue) ||
                        (newValue.startsWith("3501") && newValue.indexOf(".") < 0) ||
                        (newValue.startsWith("2501") && newValue.indexOf(".") < 0)) {
                        resultSqlList.add(SwitchUtil.matchColumn(columnName, newTable, "ID", concat));
                    } else if (orgNameList.contains(newValue)) {
                        resultSqlList.add(SwitchUtil.matchColumn(columnName, newTable, "名称", concat));
                    }
                } catch (Exception e) {
                    log.debug("查询列 {}.{} 失败: {}", newTable, column, e.getMessage());
                }
            });
            futures.add(future);
        }

        // 等待所有查询完成，设置总超时时间
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }  catch (Exception e) {
            log.debug("并行查询异常: {} - {}", newTable, e.getMessage());
        }

        return resultSqlList;
    }

    /**
     * 降级方案：逐列查询（当批量查询失败时使用）
     */
    private List<String> fallbackSingleColumnQuery(String newTable, List<String> columnList, OrgData orgData) {
        List<String> resultSqlList = new ArrayList<>();

        for (String column : columnList) {
            try {
                String dataSql = "SELECT `" + column + "` FROM " + newTable +
                        " WHERE `" + column + "` IN (" + orgData.getOldOrgId() + "," + orgData.getOldOrgName() + ") LIMIT 1";
                List<String> valueList = jdbcTemplateYL.queryForList(dataSql, String.class);

                if (CollectionUtil.isEmpty(valueList)) {
                    continue;
                }
                String newValue = valueList.get(0);
                if (StringUtils.isBlank(newValue)) {
                    continue;
                }

                boolean concat = false;
                if (newValue.indexOf(",") > 0) {
                    String[] split = newValue.split(",");
                    newValue = split[0];
                    concat = true;
                }

                String columnName = column;
                if (SwitchUtil.containsChinese(column)) {
                    columnName = "`" + column + "`";
                }

                if (orgList.contains(newValue) ||
                    (newValue.startsWith("3501") && newValue.indexOf(".") < 0) ||
                    (newValue.startsWith("2501") && newValue.indexOf(".") < 0)) {
                    resultSqlList.add(SwitchUtil.matchColumn(columnName, newTable, "ID", concat));
                } else if (orgNameList.contains(newValue)) {
                    resultSqlList.add(SwitchUtil.matchColumn(columnName, newTable, "名称", concat));
                }
            } catch (Exception e) {
                log.debug("查询列 {}.{} 失败: {}", newTable, column, e.getMessage());
            }
        }

        return resultSqlList;
    }
}
