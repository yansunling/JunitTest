package com.org.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 只读查找字段值为指定组织名称的表和字段。
 */
@Slf4j
public class OrgNameValueCheck {

    private static final String TARGET_VALUE = "温州瓯北转运场";
    private static final int QUERY_THREADS = 10;
    private static final String EXCLUDED_TABLES_RESOURCE = "java/table/errorTable.txt";

    private final JdbcTemplate jdbcTemplateYL;

    public OrgNameValueCheck(JdbcTemplate jdbcTemplateYL) {
        this.jdbcTemplateYL = jdbcTemplateYL;
    }

    /**
     * 可传入库名限定范围；不传则只检查 tmsp、bmsp 库。
     */
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("applicationContext.xml")) {
            JdbcTemplate jdbcTemplateYL = context.getBean("jdbcTemplateYL", JdbcTemplate.class);
            new OrgNameValueCheck(jdbcTemplateYL).check(Arrays.asList(args));
        }
    }

    public void check(List<String> schemas) {
        List<String> targetSchemas = schemas.isEmpty() ? Arrays.asList("tmsp", "bmsp") : schemas;
        long startTime = System.currentTimeMillis();
        Set<String> excludedTables = loadExcludedTables();
        Set<String> skippedTables = new TreeSet<>();
        int columnCount = 0;
        Map<String, List<String>> tableColumns = new LinkedHashMap<>();
        Map<String, List<String>> indexedColumns = new LinkedHashMap<>();
        for (String schema : new HashSet<>(targetSchemas)) {
            if (Arrays.asList("information_schema", "performance_schema", "mysql", "sys").contains(schema)) {
                continue;
            }
            long metadataStart = System.currentTimeMillis();
            // 元数据按库独立读取，在内存关联，避免逐字段反复查询 STATISTICS。
            log.info("读取表元数据：库={}", schema);
            Set<String> baseTables = new HashSet<>(jdbcTemplateYL.queryForList(
                    "SELECT TABLE_NAME FROM information_schema.TABLES "
                            + "WHERE TABLE_SCHEMA = ? AND TABLE_TYPE = 'BASE TABLE'", String.class, schema));
            // 在构建任务前排除整张表，索引查询和全表扫描都不再访问它。
            baseTables.removeIf(table -> {
                String fullTable = schema + "." + table;
                if (excludedTables.contains(fullTable)) {
                    skippedTables.add(fullTable);
                    return true;
                }
                return false;
            });
            if (baseTables.isEmpty()) {
                log.info("库无待查询基础表，跳过字段和索引元数据读取：库={}", schema);
                continue;
            }
            log.info("读取字段元数据：库={}，基础表数={}", schema, baseTables.size());
            // 按真实主键标识排除（含联合主键各列），不依赖 id、serial_no 等字段名猜测。
            List<Map<String, Object>> schemaColumns = jdbcTemplateYL.queryForList(
                    "SELECT TABLE_NAME, COLUMN_NAME FROM information_schema.COLUMNS "
                            + "WHERE TABLE_SCHEMA = ? "
                            + "AND COLUMN_KEY <> 'PRI' "
                            + "AND LOWER(COLUMN_NAME) NOT IN "
                            + "('create_user_id','update_user_id','creator','creater','op_user_id') "
                            + "AND DATA_TYPE IN ('char','varchar','tinytext','text','mediumtext','longtext','enum','set') "
                            // 排除容纳不下目标名称的短字符字段，避免仅有这些字段的表参与扫描。
                            + "AND (DATA_TYPE NOT IN ('char','varchar') OR CHARACTER_MAXIMUM_LENGTH >= ?)",
                    schema, TARGET_VALUE.codePointCount(0, TARGET_VALUE.length()));
            log.info("读取索引元数据：库={}，排除主键、创建人和更新人后的候选文本字段数={}",
                    schema, schemaColumns.size());
            List<Map<String, Object>> indexes = jdbcTemplateYL.queryForList(
                    "SELECT TABLE_NAME, COLUMN_NAME FROM information_schema.STATISTICS "
                            + "WHERE TABLE_SCHEMA = ? AND SEQ_IN_INDEX = 1 AND INDEX_TYPE = 'BTREE'", schema);
            Set<String> indexedFields = new HashSet<>();
            for (Map<String, Object> index : indexes) {
                if (index.get("COLUMN_NAME") != null) {
                    indexedFields.add(quoteIdentifier(index.get("TABLE_NAME").toString()) + "."
                            + quoteIdentifier(index.get("COLUMN_NAME").toString()));
                }
            }
            for (Map<String, Object> column : schemaColumns) {
                String table = column.get("TABLE_NAME").toString();
                if (!baseTables.contains(table)) {
                    continue;
                }
                String field = column.get("COLUMN_NAME").toString();
                String fullTable = quoteIdentifier(schema) + "." + quoteIdentifier(table);
                columnCount++;
                tableColumns.computeIfAbsent(fullTable, key -> new ArrayList<>()).add(field);
                if (indexedFields.contains(quoteIdentifier(table) + "." + quoteIdentifier(field))) {
                    indexedColumns.computeIfAbsent(fullTable, key -> new ArrayList<>()).add(field);
                }
            }
            log.info("元数据读取完成：库={}，耗时={} 毫秒", schema,
                    System.currentTimeMillis() - metadataStart);
        }
        List<Map.Entry<String, List<String>>> tables = new ArrayList<>(tableColumns.entrySet());
        Set<String> matched = new ConcurrentSkipListSet<>();
        Map<String, String> failed = new ConcurrentSkipListMap<>();
        AtomicInteger nextTable = new AtomicInteger();
        AtomicInteger completed = new AtomicInteger();
        int workerCount = Math.min(QUERY_THREADS, tables.size());
        log.info("开始校验，目标值：{}，表数：{}，字段数：{}，并发数：{}",
                TARGET_VALUE, tables.size(), columnCount, workerCount);
        log.info("排除名单表数={}，本次实际跳过表数={}", excludedTables.size(), skippedTables.size());
        if (workerCount == 0) {
            writeSummary(targetSchemas, tables.size(), columnCount, skippedTables, matched, failed, startTime);
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(workerCount);
        List<Future<?>> futures = new ArrayList<>();
        try {
            // 索引首列按值定位，其余字段共享一次表扫描。
            for (int i = 0; i < workerCount; i++) {
                futures.add(executor.submit(() -> {
                    int index;
                    while (!Thread.currentThread().isInterrupted()
                            && (index = nextTable.getAndIncrement()) < tables.size()) {
                        Map.Entry<String, List<String>> table = tables.get(index);
                        checkTable(table.getKey(), table.getValue(),
                                indexedColumns.getOrDefault(table.getKey(), java.util.Collections.emptyList()),
                                matched, failed);
                        int count = completed.incrementAndGet();
                        if (count % 50 == 0 || count == tables.size()) {
                            log.info("校验进度：{}/{} 张表，命中字段数={}，失败表数={}",
                                    count, tables.size(), matched.size(), failed.size());
                        }
                    }
                }));
            }
            for (Future<?> future : futures) {
                future.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("校验被中断，未完整完成", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("校验任务异常，未完整完成", e.getCause());
        } finally {
            executor.shutdownNow();
        }
        writeSummary(targetSchemas, tables.size(), columnCount, skippedTables, matched, failed, startTime);
        if (!failed.isEmpty()) {
            throw new IllegalStateException("存在查询失败表，校验未完整完成，请查看汇总文件");
        }
    }

    private void checkTable(String fullTable, List<String> fields, List<String> indexedFields, Set<String> matched,
                            Map<String, String> failed) {
        long startTime = System.currentTimeMillis();
        List<String> scanFields = new ArrayList<>(fields);
        scanFields.removeAll(indexedFields);
        log.debug("开始查询表：{}，索引字段数={}，扫描字段数={}",
                fullTable, indexedFields.size(), scanFields.size());
        try {
            if (!indexedFields.isEmpty()) {
                List<String> expressions = new ArrayList<>();
                List<Object> parameters = new ArrayList<>();
                for (int i = 0; i < indexedFields.size(); i++) {
                    // 不能把复合索引的非首列当作可独立定位的字段。
                    expressions.add("EXISTS (SELECT 1 FROM " + fullTable + " WHERE "
                            + quoteIdentifier(indexedFields.get(i)) + " = ?) AS " + quoteIdentifier("m" + i));
                    parameters.add(TARGET_VALUE);
                }
                Map<String, Object> result = jdbcTemplateYL.queryForMap(
                        "SELECT " + String.join(",", expressions), parameters.toArray());
                collectMatches(fullTable, indexedFields, result, matched);
            }
            if (!scanFields.isEmpty()) {
                checkUnindexedFields(fullTable, scanFields, matched);
            }
        } catch (DataAccessException e) {
            failed.put(fullTable, e.getMostSpecificCause().toString());
            log.warn("查询表失败：{}，耗时={} 毫秒，原因={}", fullTable,
                    System.currentTimeMillis() - startTime, e.getMostSpecificCause().toString());
        } finally {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= 5000) {
                log.info("慢表查询：{}，耗时={} 毫秒，索引字段数={}，扫描字段数={}",
                        fullTable, elapsed, indexedFields.size(), scanFields.size());
            }
        }
    }

    private void checkUnindexedFields(String fullTable, List<String> fields, Set<String> matched) {
        List<String> expressions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            expressions.add("MAX(CASE WHEN " + quoteIdentifier(fields.get(i))
                    + " = ? THEN 1 ELSE 0 END) AS " + quoteIdentifier("m" + i));
            parameters.add(TARGET_VALUE);
        }
        // 无可用索引时仍需扫描全表；直接聚合使每行每字段只比较一次，避免 OR 再做一遍比较。
        String sql = "SELECT " + String.join(",", expressions) + " FROM " + fullTable;
        Map<String, Object> result = jdbcTemplateYL.queryForMap(sql, parameters.toArray());
        collectMatches(fullTable, fields, result, matched);
    }

    private void collectMatches(String fullTable, List<String> fields, Map<String, Object> result,
                                Set<String> matched) {
        for (int i = 0; i < fields.size(); i++) {
            Object value = result.get("m" + i);
            if ((value instanceof Number && ((Number) value).intValue() == 1)
                    || Boolean.TRUE.equals(value)) {
                String field = fullTable + "." + quoteIdentifier(fields.get(i));
                matched.add(field);
                log.info("命中字段：{}", field);
            }
        }
    }

    private void writeSummary(List<String> schemas, int tableCount, int columnCount,
                              Set<String> skippedTables, Set<String> matched,
                              Map<String, String> failed, long startTime) {
        StringBuilder summary = new StringBuilder();
        summary.append("校验汇总\n目标值：").append(TARGET_VALUE)
                .append("\n查询库：").append(String.join(",", schemas))
                .append("\n表数：").append(tableCount).append("，字段数：").append(columnCount)
                .append("，名单跳过表数：").append(skippedTables.size())
                .append("，命中字段数：").append(matched.size()).append("，失败表数：").append(failed.size())
                .append("\n耗时：").append((System.currentTimeMillis() - startTime) / 1000).append(" 秒")
                .append("\n状态：").append(failed.isEmpty() ? "完整完成（不含排除名单中的表）" : "未完整完成，以下失败表需重新查询")
                .append("\n\n命中字段（库.表.字段）：\n");
        if (matched.isEmpty()) {
            summary.append("无\n");
        } else {
            matched.forEach(field -> summary.append(field).append('\n'));
        }
        summary.append("\n按 errorTable.txt 跳过的表：\n");
        if (skippedTables.isEmpty()) {
            summary.append("无\n");
        } else {
            skippedTables.forEach(table -> summary.append(table).append('\n'));
        }
        summary.append("\n失败表：\n");
        if (failed.isEmpty()) {
            summary.append("无\n");
        } else {
            failed.forEach((table, reason) -> summary.append(table).append("：").append(reason).append('\n'));
        }
        try {
            Path directory = Paths.get("target", "org-name-check");
            Files.createDirectories(directory);
            Path report = directory.resolve("result-" + System.currentTimeMillis() + ".txt").toAbsolutePath();
            Files.write(report, summary.toString().getBytes(StandardCharsets.UTF_8));
            log.info("\n{}\n汇总文件：{}", summary, report);
        } catch (IOException e) {
            log.info("\n{}", summary);
            throw new IllegalStateException("汇总文件写入失败", e);
        }
    }

    private Set<String> loadExcludedTables() {
        Path source = Paths.get("src", "main", "resources", EXCLUDED_TABLES_RESOURCE);
        // 本地运行优先读取源文件，避免 target/classes 中的旧名单尚未随构建更新。
        try (InputStream input = Files.exists(source) ? Files.newInputStream(source)
                : OrgNameValueCheck.class.getClassLoader().getResourceAsStream(EXCLUDED_TABLES_RESOURCE)) {
            if (input == null) {
                throw new IllegalStateException("排除名单不存在：" + EXCLUDED_TABLES_RESOURCE);
            }
            Set<String> tables = new HashSet<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String table = line.replace("\uFEFF", "").trim();
                    if (!table.isEmpty()) {
                        tables.add(table);
                    }
                }
            }
            return tables;
        } catch (IOException e) {
            throw new IllegalStateException("读取排除名单失败：" + EXCLUDED_TABLES_RESOURCE, e);
        }
    }

    private static String quoteIdentifier(String identifier) {
        return "`" + identifier.replace("`", "``") + "`";
    }
}
