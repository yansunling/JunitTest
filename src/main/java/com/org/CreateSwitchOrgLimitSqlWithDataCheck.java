package com.org;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CreateSwitchOrgLimitSqlWithDataCheck extends CreateSwitchOrgLimitSql {

    private static final int QUERY_THREADS = 20;

    private static final Pattern TABLE_PATTERN = Pattern.compile(
            "^\\s*(?:update\\s+|replace\\s+into\\s+)"
                    + "`?([\\w$]+)`?\\s*\\.\\s*`?([\\w$]+)`?(?=\\s|\\()",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern SOURCE_PATTERN = Pattern.compile(
            "\\bfrom\\s+(`?[\\w$]+`?\\s*\\.\\s*`?[\\w$]+`?)"
                    + "(?:\\s+(?:as\\s+)?([\\w$]+))?\\s+where\\b",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern WHERE_PATTERN = Pattern.compile(
            "'(?:''|\\\\.|[^'\\\\])*'|`(?:``|[^`])*`|(\\bwhere\\b)",
            Pattern.CASE_INSENSITIVE);

    @Autowired
    @Qualifier("jdbcTemplateYL")
    private JdbcTemplate dataCheckJdbcTemplate;

    @Test
    @Override
    public void test() throws Exception {
        File sourceDir = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\org");
        File[] sqlFiles = sourceDir.listFiles(file -> file.isFile()
                && file.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".sql"));
        if (sqlFiles == null || sqlFiles.length == 0) {
            throw new IllegalArgumentException("源目录不存在或没有 SQL 文件: " + sourceDir);
        }
        Arrays.sort(sqlFiles, Comparator.comparing(File::getName));
        File outputDir = new File(sourceDir.getParentFile(), "org_filtered_" + System.currentTimeMillis());
        Files.createDirectory(outputDir.toPath());
        dataCheckJdbcTemplate.setQueryTimeout(6000);
        // 缓存查询任务，重复条件共用同一个 Future；缓存仅由主线程访问。
        Map<String, Future<Boolean>> queryTasks = new HashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(QUERY_THREADS);
        int totalKept = 0;
        int totalSkipped = 0;
        log.info("使用 {} 个线程并发校验机构数据，输出目录: {}", QUERY_THREADS, outputDir.getAbsolutePath());
        try {
            for (File sqlFile : sqlFiles) {
                // 先提交本文件所有查询，再按原顺序收集，保证实际并发且不打乱 SQL。
                List<String> statements = FileUtils.readLines(sqlFile, "utf-8");
                List<Future<Boolean>> checks = new ArrayList<>();
                for (String statement : statements) {
                    String querySql = buildDataQuery(statement);
                    checks.add(querySql == null ? null : queryTasks.computeIfAbsent(querySql,
                            query -> executorService.submit(() -> hasMatchingData(query))));
                }

                List<String> result = new ArrayList<>();
                int keptCount = 0;
                int skippedCount = 0;
                for (int i = 0; i < statements.size(); i++) {
                    String statement = statements.get(i);
                    Future<Boolean> check = checks.get(i);
                    if (check == null) {
                        result.add(statement);
                    } else if (check.get()) {
                        result.add(statement);
                        keptCount++;
                    } else {
                        skippedCount++;
                        log.info("文件 {} 第 {} 行的机构条件无匹配数据，移除 SQL: {}",
                                sqlFile.getName(), i + 1, statement);
                    }
                }
                FileUtils.writeLines(new File(outputDir, sqlFile.getName()), "utf-8", result);
                totalKept += keptCount;
                totalSkipped += skippedCount;
                log.info("文件 {} 过滤完成，保留 {} 条，移除 {} 条", sqlFile.getName(), keptCount, skippedCount);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        } finally {
            // 异常时取消未执行任务，避免退出测试后继续查询数据库。
            executorService.shutdownNow();
        }
        log.info("SQL 过滤完成，共 {} 个文件，保留 {} 条，移除 {} 条，输出目录: {}",
                sqlFiles.length, totalKept, totalSkipped, outputDir.getAbsolutePath());
    }

    private String buildDataQuery(String sql) {
        if (!sql.trim().matches("(?is)^(update|replace|insert|delete)\\b.*")) {
            return null;
        }
        Matcher whereMatcher = WHERE_PATTERN.matcher(sql);
        int whereEnd = -1;
        while (whereMatcher.find()) {
            if (whereMatcher.group(1) != null) {
                whereEnd = whereMatcher.end();
                break;
            }
        }
        if (whereEnd < 0) {
            throw new IllegalArgumentException("组织调整 SQL 缺少 WHERE 条件: " + sql);
        }

        String source;
        Matcher tableMatcher = TABLE_PATTERN.matcher(sql);
        if (sql.trim().toLowerCase(java.util.Locale.ROOT).startsWith("update") && tableMatcher.find()) {
            source = "`" + tableMatcher.group(1) + "`.`" + tableMatcher.group(2) + "`";
        } else {
            // INSERT/REPLACE SELECT 以来源表和别名校验，DELETE 复用其 FROM。
            Matcher sourceMatcher = SOURCE_PATTERN.matcher(sql);
            if (!sourceMatcher.find()) {
                throw new IllegalArgumentException("无法识别组织调整 SQL 的来源表: " + sql);
            }
            source = sourceMatcher.group(1);
            if (sourceMatcher.group(2) != null) {
                source += " AS " + sourceMatcher.group(2);
            }
        }
        String condition = sql.substring(whereEnd).trim();
        if (condition.endsWith(";")) {
            condition = condition.substring(0, condition.length() - 1).trim();
        }
        return "SELECT 1 FROM " + source + " WHERE " + condition + " LIMIT 1";
    }

    private boolean hasMatchingData(String querySql) {
        try {
            return !dataCheckJdbcTemplate.queryForList(querySql, Integer.class).isEmpty();
        } catch (RuntimeException e) {
            // 查询失败不能当作无机构数据，否则会静默漏生成 SQL。
            log.error("检查机构匹配数据失败，中止本批 SQL 生成，校验查询: {}", querySql, e);
            throw e;
        }
    }
}
