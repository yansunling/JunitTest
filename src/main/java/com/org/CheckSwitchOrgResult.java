package com.org;

import com.excel.CJExcelUtil;
import com.org.data.OrgData;
import com.yd.utils.common.ExcelReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Excel 保留逐行映射，只读查找机构切换后的旧编码、旧名称残留。 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CheckSwitchOrgResult {

    private static final String EXCEL_PATH = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
    private static final List<String> SCHEMAS = Arrays.asList(
            "tmsp", "bmsp", "crm", "mpp", "mpp2", "bds", "hcm", "pmp");
    private static final int QUERY_THREADS = 20;
    private static final Pattern ORG_FIELD = Pattern.compile(
            "org|dept|depart|site|region|district|area|power|机构|组织|部门|网点|大区|小区",
            Pattern.CASE_INSENSITIVE);

    @Autowired
    @Qualifier("jdbcTemplateYL")
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test() throws Exception {
        List<OrgData> mappings = readMappings();
        Map<String, Set<String>> oldValues = new LinkedHashMap<>();
        Set<String> newValues = new LinkedHashSet<>();
        for (OrgData mapping : mappings) {
            addMapping(oldValues, newValues, "编码", mapping.getOldOrgId(), mapping.getNewOrgId());
            addMapping(oldValues, newValues, "名称", mapping.getOldOrgName(), mapping.getNewOrgName());
        }
        if (oldValues.isEmpty()) {
            throw new IllegalArgumentException("Excel 中没有编码或名称发生变化的有效机构映射");
        }

        File outputDir = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\check_result_"
                + System.currentTimeMillis());
        Files.createDirectories(outputDir.toPath());
        jdbcTemplate.setQueryTimeout(60);
        Set<String> failures = new ConcurrentSkipListSet<>();
        Map<String, List<Column>> tables = loadColumns(failures);
        Set<String> matches = new ConcurrentSkipListSet<>();
        Set<String> queries = new ConcurrentSkipListSet<>();
        Set<String> foundValues = new ConcurrentSkipListSet<>();
        AtomicInteger completed = new AtomicInteger();
        ExecutorService executor = Executors.newFixedThreadPool(QUERY_THREADS);
        List<Future<?>> tasks = new ArrayList<>();
        log.info("开始机构切换校验，库={}，表数={}，旧值数={}，线程数={}，报告目录={}",
                SCHEMAS, tables.size(), oldValues.size(), QUERY_THREADS, outputDir);
        try {
            // 按表并发，表内逐字段查询，避免多层线程池把并发数放大。
            for (Map.Entry<String, List<Column>> entry : tables.entrySet()) {
                tasks.add(executor.submit(() -> {
                    for (Column column : entry.getValue()) {
                        if (Thread.currentThread().isInterrupted()) {
                            return;
                        }
                        checkColumn(entry.getKey(), column, oldValues, newValues,
                                matches, queries, foundValues, failures);
                    }
                    int count = completed.incrementAndGet();
                    if (count % 20 == 0 || count == tables.size()) {
                        log.info("校验进度 {}/{} 张表，命中记录数={}，失败项数={}",
                                count, tables.size(), matches.size(), failures.size());
                    }
                }));
            }
            for (Future<?> task : tasks) {
                task.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            failures.add("任务被中断，校验未完整完成");
            throw e;
        } finally {
            executor.shutdownNow();
        }

        List<String> summary = new ArrayList<>();
        summary.add("查询库：" + String.join(",", SCHEMAS));
        summary.add("表数：" + tables.size() + "，命中记录数：" + matches.size() + "，失败项数：" + failures.size());
        summary.add("范围：基础表的文本、整数、整数型 DECIMAL 字段；排除流水号、创建人、更新人和备注字段。");
        summary.add("规则：精确匹配旧编码/名称；机构相关文本字段额外检查逗号分隔列表；未变化的映射不检查。");
        summary.add("结果表示旧值是否残留；没有旧值不能证明历史数据已全部迁移到正确的新机构。");
        summary.add("旧值同时属于 Excel 中的新值时，命中列为待人工核对，不直接认定切换失败。");
        summary.add("任意字段查询失败时，未发现旧值的项也不能视作校验通过。");
        summary.add("");
        for (Map.Entry<String, Set<String>> entry : oldValues.entrySet()) {
            String status = foundValues.contains(entry.getKey())
                    ? (newValues.contains(entry.getKey()) ? "旧值命中，属于目标值，待人工核对" : "发现旧值残留，待核对")
                    : (failures.isEmpty() ? "本次范围内未发现旧值残留" : "未发现旧值，但存在查询失败，结论不完整");
            summary.add(status + "\t" + String.join("；", entry.getValue()));
        }
        FileUtils.writeLines(new File(outputDir, "summary.txt"), "utf-8", summary);
        List<String> detail = new ArrayList<>();
        detail.add("状态\t库.表\t字段\t旧值\t当前字段值\tExcel 映射");
        detail.addAll(matches);
        FileUtils.writeLines(new File(outputDir, "old_value_matches.tsv"), "utf-8", detail);
        FileUtils.writeLines(new File(outputDir, "recheck.sql"), "utf-8", queries);
        FileUtils.writeLines(new File(outputDir, "failed_queries.txt"), "utf-8", failures);
        log.info("机构切换校验结束，报告目录={}，命中记录数={}，失败项数={}", outputDir, matches.size(), failures.size());
        if (!failures.isEmpty()) {
            throw new IllegalStateException("存在查询失败项，校验未完整完成，请查看 " + outputDir);
        }
    }

    private List<OrgData> readMappings() throws Exception {
        ExcelReader reader = new ExcelReader(new File(EXCEL_PATH));
        reader.openFile();
        try {
            List<Object[]> rows = reader.getAllRow();
            if (rows == null || rows.size() < 2) {
                throw new IllegalArgumentException("Excel 没有机构映射数据: " + EXCEL_PATH);
            }
            // 不按新机构合并旧机构，避免相同编码、仅名称变化的行被误判。
            List<Object[]> dataRows = rows.subList(1, rows.size()).stream()
                    .filter(row -> row != null).collect(Collectors.toList());
            return CJExcelUtil.initImportExcelDatas(OrgData.titleMap, dataRows, OrgData.class);
        } finally {
            reader.closeFile();
        }
    }

    private void addMapping(Map<String, Set<String>> oldValues, Set<String> newValues,
                            String type, String oldValue, String newValue) {
        oldValue = oldValue == null ? "" : oldValue.trim();
        newValue = newValue == null ? "" : newValue.trim();
        if (!newValue.isEmpty()) {
            newValues.add(newValue);
        }
        if (oldValue.isEmpty() && newValue.isEmpty()) {
            return;
        }
        if (oldValue.isEmpty() || newValue.isEmpty()) {
            throw new IllegalArgumentException("Excel 机构" + type + "映射不完整: " + oldValue + " / " + newValue);
        }
        if (!oldValue.equals(newValue)) {
            oldValues.computeIfAbsent(oldValue, key -> new LinkedHashSet<>())
                    .add(type + ": " + oldValue + " 调整为 " + newValue);
        }
    }

    private Map<String, List<Column>> loadColumns(Set<String> failures) {
        Map<String, List<Column>> tables = new TreeMap<>();
        for (String schema : SCHEMAS) {
            try {
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                        "SELECT c.TABLE_NAME, c.COLUMN_NAME, c.DATA_TYPE, c.CHARACTER_MAXIMUM_LENGTH, c.NUMERIC_PRECISION "
                                + "FROM information_schema.COLUMNS c JOIN information_schema.TABLES t "
                                + "ON t.TABLE_SCHEMA=c.TABLE_SCHEMA AND t.TABLE_NAME=c.TABLE_NAME "
                                + "WHERE c.TABLE_SCHEMA=? AND t.TABLE_TYPE='BASE TABLE' "
                                + "AND (c.DATA_TYPE IN ('char','varchar','tinytext','text','mediumtext','longtext',"
                                + "'enum','set','tinyint','smallint','mediumint','int','bigint') "
                                + "OR (c.DATA_TYPE IN ('decimal','numeric') AND c.NUMERIC_SCALE=0)) "
                                + "AND LOWER(c.COLUMN_NAME) NOT IN ('serial_no','create_user_id','update_user_id',"
                                + "'creator','creater','op_user_id','remark','price_remark','unclear_remark') "
                                + "ORDER BY c.TABLE_NAME,c.ORDINAL_POSITION", schema);
                if (rows.isEmpty()) {
                    failures.add(schema + "：无可见候选字段，请确认库是否存在及查询权限");
                }
                for (Map<String, Object> row : rows) {
                    Column column = new Column();
                    column.name = row.get("COLUMN_NAME").toString();
                    column.text = Arrays.asList("char", "varchar", "tinytext", "text", "mediumtext", "longtext", "enum", "set")
                            .contains(row.get("DATA_TYPE").toString().toLowerCase(Locale.ROOT));
                    Object length = row.get(column.text ? "CHARACTER_MAXIMUM_LENGTH" : "NUMERIC_PRECISION");
                    column.maxLength = length instanceof Number ? ((Number) length).longValue() : Long.MAX_VALUE;
                    String table = quote(schema) + "." + quote(row.get("TABLE_NAME").toString());
                    tables.computeIfAbsent(table, key -> new ArrayList<>()).add(column);
                }
                log.info("库 {} 元数据加载完成，候选字段数={}", schema, rows.size());
            } catch (RuntimeException e) {
                failures.add(schema + " 元数据查询失败：" + e.getMessage());
                log.error("库 {} 元数据查询失败", schema, e);
            }
        }
        return tables;
    }

    private void checkColumn(String table, Column column, Map<String, Set<String>> oldValues,
                             Set<String> newValues, Set<String> matches, Set<String> queries,
                             Set<String> foundValues, Set<String> failures) {
        List<String> values = oldValues.keySet().stream()
                .filter(value -> value.length() <= column.maxLength
                        && (column.text || value.matches("[0-9]+")))
                .collect(Collectors.toList());
        if (values.isEmpty()) {
            return;
        }
        String field = quote(column.name);
        List<Object> parameters = new ArrayList<>(values);
        String condition = field + " IN (" + String.join(",", Collections.nCopies(values.size(), "?")) + ")";
        boolean checkList = column.text && ORG_FIELD.matcher(column.name).find();
        if (checkList) {
            // 只在机构相关字段检查列表边界，避免将编码当作其他长字符串的子串。
            condition += " OR " + field + " REGEXP ?";
            parameters.add("(^|,)(" + values.stream().map(this::regexLiteral)
                    .collect(Collectors.joining("|")) + ")(,|$)");
        }
        String sql = "SELECT DISTINCT " + field + " FROM " + table + " WHERE " + condition;
        try {
            List<String> results = jdbcTemplate.queryForList(sql, String.class, parameters.toArray());
            for (String actual : results) {
                Set<String> matched = new TreeSet<>();
                if (oldValues.containsKey(actual)) {
                    matched.add(actual);
                }
                if (checkList && actual != null) {
                    for (String part : actual.split(",")) {
                        if (oldValues.containsKey(part)) {
                            matched.add(part);
                        }
                    }
                }
                for (String value : matched) {
                    foundValues.add(value);
                    matches.add((newValues.contains(value) ? "目标值复用，待核对" : "旧值残留，待核对")
                            + "\t" + table + "\t" + field + "\t" + cell(value) + "\t" + cell(actual)
                            + "\t" + cell(String.join("；", oldValues.get(value))));
                    queries.add("SELECT * FROM " + table + " WHERE " + field
                            + (actual == null ? " IS NULL" : " = '" + actual.replace("\\", "\\\\").replace("'", "''") + "'")
                            + " LIMIT 100;");
                }
            }
        } catch (RuntimeException e) {
            failures.add(table + "." + field + "\t" + cell(e.getMessage()) + "\n" + sql
                    + "\n参数：" + cell(parameters.toString()));
            log.warn("机构校验查询失败：{}.{}，原因={}", table, field, e.getMessage());
        }
    }

    private String regexLiteral(String value) {
        StringBuilder escaped = new StringBuilder();
        for (char c : value.toCharArray()) {
            if ("\\.^$|?*+()[]{}".indexOf(c) >= 0) {
                escaped.append('\\');
            }
            escaped.append(c);
        }
        return escaped.toString();
    }

    private static String quote(String identifier) {
        return "`" + identifier.replace("`", "``") + "`";
    }

    private static String cell(String value) {
        return value == null ? "" : value.replace('\t', ' ').replace('\r', ' ').replace('\n', ' ');
    }

    private static class Column {
        private String name;
        private boolean text;
        private long maxLength;
    }
}
