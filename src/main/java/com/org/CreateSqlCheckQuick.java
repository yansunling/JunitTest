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
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateSqlCheckQuick implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;
    }

    @Qualifier("jdbcTemplateYL")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ========== 预加载的元数据 ==========
    private List<String> orgList;
    private List<String> orgNameList;
    // 预加载的表-列映射: schema.table -> columnList
    private Map<String, List<String>> tableColumnMap;
    // orgData 的 oldOrgId -> newOrgId 映射
    private Map<String, String> orgIdMapping;
    // 所有 oldOrgId 合并后的 IN 条件
    private String allOldOrgIdsInClause;

    @Test
    public void test() throws Exception {
        long startTime = System.currentTimeMillis();

        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
        jdbcTemplate.setQueryTimeout(600);
        List<String> schemaList = Arrays.asList("hcm");

        // ========== 【优化1】预加载所有元数据 ==========
        log.info("开始预加载元数据...");
        long preloadStart = System.currentTimeMillis();
        preloadMetadata(schemaList, orgDataList);
        log.info("元数据预加载完成，耗时: {}ms", System.currentTimeMillis() - preloadStart);

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        // 【优化2】使用线程安全的集合
        Set<String> newSqlList = Collections.synchronizedSet(new LinkedHashSet<>());

        try {
            for (String schema : schemaList) {
                String sql = "select table_name from information_schema.`TABLES` where " +
                        "table_schema='" + schema + "' and table_type!='VIEW' and table_name not like 'foc%' and table_name not like 'cip%'" +
                        " and table_name not in('tmsp_hand_schedule_car','tmsp_send_order_cust_edit_log','tmsp_order_route_org_detail_bak','tmsp_order_route_org_detail','tmsp_net_org','tmsp','tmsp_msg_send_sms','bmsp_report_outorder_order_cust','bmsp_report_inorder_putfee_order_cust','bmsp_report_inorder_putfee_customer_cust','tmsp_alter_order_report','bmsp_report_inorder_customer_cust','tmsp_depart_ontime_rate_report_item','tmsp_msg_result_sms','bmsp_report_inorder_order_cust' )  ";
                List<String> tableFiles = jdbcTemplate.queryForList(sql, String.class);

                String filePath = getClass().getClassLoader().getResource("").getPath();
                List<String> errorTable = FileUtils.readLines(new File(filePath + "java/table/errorTable.txt"), "utf-8");
                Set<String> errorTableSet = new HashSet<>(errorTable); // 使用 Set 加速查找

                CountDownLatch countDownLatch = new CountDownLatch(tableFiles.size());
                log.info("开始处理 {} 个表...", tableFiles.size());

                for (String table : tableFiles) {
                    executorService.submit(() -> {
                        try {
                            String newTable = schema + "." + table;
                            if (errorTableSet.contains(newTable)) {
                                return;
                            }

                            // 【优化3】使用预加载的列信息
                            List<String> columnList = tableColumnMap.get(newTable);
                            if (CollectionUtil.isEmpty(columnList)) {
                                return;
                            }

                            // 【优化4】批量查询所有 orgId，一次搞定
                            List<String> matchedSqls = batchCheckAndBuildSql(newTable, columnList, orgDataList);
                            if (CollectionUtil.isNotEmpty(matchedSqls)) {
                                newSqlList.addAll(matchedSqls);
                            }
                        } catch (Exception e) {
                            log.error("处理表出错: {}", e.getMessage());
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }
                countDownLatch.await();
            }
        } finally {
            executorService.shutdown();
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        }

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\notSwitch" + schemaList.get(0) + ".sql");
        FileUtils.writeLines(allFile, "utf-8", newSqlList);

        log.info("全部完成，总耗时: {}ms，生成SQL数: {}", System.currentTimeMillis() - startTime, newSqlList.size());
    }

    /**
     * 【优化核心】预加载所有元数据
     */
    @SneakyThrows
    private void preloadMetadata(List<String> schemaList, List<OrgData> orgDataList) {
        // 1. 查询组织列表（只查一次）
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
        orgList = new ArrayList<>();
        orgNameList = new ArrayList<>();
        maps.forEach(item -> {
            orgList.add(item.get("org_id") + "");
            orgNameList.add(item.get("org_name") + "");
        });
        log.info("加载组织数量: {}", orgList.size());

        // 2. 构建 orgId 映射和合并的 IN 条件
        orgIdMapping = new HashMap<>();
        Set<String> allOldOrgIds = new LinkedHashSet<>();
        for (OrgData orgData : orgDataList) {
            // 解析 oldOrgId（可能是逗号分隔的多个值）
            String oldOrgIdStr = orgData.getOldOrgId();
            if (StringUtils.isNotBlank(oldOrgIdStr)) {
                String[] oldIds = oldOrgIdStr.split(",");
                for (String oldId : oldIds) {
                    String trimmedId = oldId.trim();
                    if (StringUtils.isNotBlank(trimmedId)) {
                        orgIdMapping.put(trimmedId, orgData.getNewOrgId());
                        allOldOrgIds.add(trimmedId);
                    }
                }
            }
        }
        allOldOrgIdsInClause = String.join(",", allOldOrgIds);
        log.info("待检查的 OrgId 数量: {}", allOldOrgIds.size());

        // 3. 一次性预加载所有表的列信息
        tableColumnMap = new HashMap<>();
        String schemaInClause = schemaList.stream()
                .map(s -> "'" + s + "'")
                .collect(Collectors.joining(","));

        String allColumnsSql = "SELECT CONCAT(table_schema, '.', table_name) as full_table, column_name " +
                "FROM information_schema.COLUMNS " +
                "WHERE table_schema IN (" + schemaInClause + ") " +
                "AND column_name NOT IN ('serial_no','create_user_id','is_bill','update_user_id','creator','op_user_id','remark','salesman_id','price_remark','product_type','is_valid','is_red','unclear_remark') " +
                "AND data_type NOT IN ('decimal','datetime','date','int')";

        List<Map<String, Object>> allColumns = jdbcTemplate.queryForList(allColumnsSql);
        int columnCount = 0;
        for (Map<String, Object> row : allColumns) {
            String fullTable = row.get("full_table").toString();
            String column = row.get("column_name").toString();
            tableColumnMap.computeIfAbsent(fullTable, k -> new ArrayList<>()).add(column);
            columnCount++;
        }
        log.info("加载列数量: {}", columnCount);
    }

    // 单次 IN 条件的最大值数量（避免 IN 条件过长）
    private static final int MAX_IN_CLAUSE_SIZE = 50;
    // 单列查询超时（毫秒）
    private static final int COLUMN_QUERY_TIMEOUT_MS = 5000;

    /**
     * 【优化4】批量检查并生成SQL
     * 策略：逐个 orgId 使用 EXISTS 检查是否存在（可利用索引，快速返回）
     */
    private List<String> batchCheckAndBuildSql(String newTable, List<String> columnList, List<OrgData> orgDataList) {
        List<String> resultSqls = new ArrayList<>();

        // 使用 CompletableFuture 并行检查每个列
        List<CompletableFuture<List<String>>> futures = new ArrayList<>();

        for (String column : columnList) {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> {
                return checkColumnForOrgIds(newTable, column);
            });
            futures.add(future);
        }

        // 等待所有列检查完成（不设超时，等所有线程结束）
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            for (CompletableFuture<List<String>> future : futures) {
                List<String> sqls = future.get();
                if (CollectionUtil.isNotEmpty(sqls)) {
                    resultSqls.addAll(sqls);
                }
            }
        } catch (Exception e) {
            log.debug("表 {} 查询异常: {}", newTable, e.getMessage());
        }

        return resultSqls;
    }

    /**
     * 检查单个列中是否存在目标 orgId
     * 使用 EXISTS + LIMIT 1，找到即返回，不需要扫描全表
     */
    private List<String> checkColumnForOrgIds(String newTable, String column) {
        List<String> resultSqls = new ArrayList<>();

        // 将 orgId 分批处理，避免 IN 条件过长
        List<String> orgIdList = new ArrayList<>(orgIdMapping.keySet());
        List<List<String>> batches = partitionList(orgIdList, MAX_IN_CLAUSE_SIZE);

        Set<String> foundOrgIds = new HashSet<>();

        for (List<String> batch : batches) {
            try {
                // 方案1：使用 UNION ALL + LIMIT 1（每个值独立查询，可利用索引）
                String unionSql = batch.stream()
                        .map(orgId -> "(SELECT " + orgId + " as org_id FROM " + newTable +
                                " WHERE `" + column + "` = " + orgId + " LIMIT 1)")
                        .collect(Collectors.joining(" UNION ALL "));

                List<Map<String, Object>> results = jdbcTemplate.queryForList(unionSql);

                for (Map<String, Object> row : results) {
                    Object orgIdObj = row.get("org_id");
                    if (orgIdObj != null) {
                        foundOrgIds.add(orgIdObj.toString());
                    }
                }
            } catch (Exception e) {
                log.info("test",e);
                // UNION ALL 失败，降级为逐个检查
                log.debug("UNION ALL 查询失败，降级处理: {}.{}", newTable, column);
                foundOrgIds.addAll(fallbackExistsCheck(newTable, column, batch));
            }
        }

        // 生成 UPDATE 语句
        for (String orgId : foundOrgIds) {
            String newOrgId = orgIdMapping.get(orgId);
            if (StringUtils.isNotBlank(newOrgId)) {
                resultSqls.add("update  " + newTable + " set " + column + "=" + newOrgId +
                        " where " + column + " in(" + orgId + ");");
            }
        }

        return resultSqls;
    }

    /**
     * 降级方案：逐个使用 EXISTS 检查
     */
    private Set<String> fallbackExistsCheck(String newTable, String column, List<String> orgIds) {
        Set<String> foundOrgIds = new HashSet<>();

        for (String orgId : orgIds) {
            try {
                // EXISTS 查询，找到一条即返回，不扫描全表
                String existsSql = "SELECT 1 FROM " + newTable +
                        " WHERE `" + column + "` = " + orgId + " LIMIT 1";
                List<Map<String, Object>> result = jdbcTemplate.queryForList(existsSql);

                if (CollectionUtil.isNotEmpty(result)) {
                    foundOrgIds.add(orgId);
                }
            } catch (Exception e) {
                // 忽略单个查询失败
            }
        }

        return foundOrgIds;
    }

    /**
     * 将列表分批
     */
    private <T> List<List<T>> partitionList(List<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            batches.add(list.subList(i, Math.min(i + batchSize, list.size())));
        }
        return batches;
    }
}
