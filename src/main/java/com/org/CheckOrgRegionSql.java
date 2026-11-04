package com.org;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 按机构调整模板只读检查大区，只输出修复 SQL，不执行更新。 */
public class CheckOrgRegionSql {
    private static final Logger log = LoggerFactory.getLogger(CheckOrgRegionSql.class);
    private static final String REGION_ID = "3501010205";
    private static final List<String> ORG_IDS = Arrays.asList(
            "3501010205", "350101020501", "350101020502", "350101020503",
            "35010102050301", "35010102050302", "350101020504", "350101020505",
            "350101020506", "350101020507", "350101020508", "350101020509");
    private static final Pattern UPDATE = Pattern.compile(
            "^update\\s+([\\w]+\\.[\\w]+)\\s+set\\s+(.+?)\\s+where\\s+(.+?);$",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern ASSIGNMENT = Pattern.compile("`?(\\w+)`?\\s*=\\s*'(<[^>]+>)'");
    private static final Pattern ORG_CONDITION = Pattern.compile(
            "^`?(\\w+)`?\\s*(?:=\\s*'<新机构ID>'|in\\s*\\(\\s*'<老机构ID(?:单个)?>'\\s*\\))\\s*$",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern REPLACE = Pattern.compile(
            "^replace\\s+into\\s+(\\w+\\.\\w+)\\(([^)]+)\\)\\s+select\\s+(.+?)\\s+from\\s+",
            Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) throws Exception {
        // 仅实例化目标 Bean 及其依赖，避免启动无关业务组件。
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        try {
            new XmlBeanDefinitionReader(factory).loadBeanDefinitions("classpath:applicationContext.xml");
            JdbcTemplate jdbcTemplate = factory.getBean("jdbcTemplateYL", JdbcTemplate.class);
            generate(args, jdbcTemplate);
        } finally {
            factory.destroySingletons();
        }
    }

    private static void generate(String[] args, JdbcTemplate jdbcTemplate) throws Exception {
        Path output = args.length == 0
                ? Paths.get(System.getProperty("user.home"), "Desktop", "switchOrg",
                        "region_check_" + System.currentTimeMillis()) : Paths.get(args[0]);
        Files.createDirectories(output);
        List<String> failures = new ArrayList<>();
        List<String> skipped = new ArrayList<>();
        List<String> lines;
        // 优先读取源码模板，避免 IDE 未复制资源时使用旧版本。
        Path source = Paths.get("src/main/resources/java/table/tmsp_org_adjust_template_new.sql");
        if (Files.exists(source)) {
            lines = Files.readAllLines(source, StandardCharsets.UTF_8);
        } else {
            try (InputStream input = resource("java/table/tmsp_org_adjust_template_new.sql")) {
                java.io.BufferedReader reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(input, StandardCharsets.UTF_8));
                lines = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
        }
        Map<String, Rule> rules = readRules(lines, failures, skipped);
        if (rules.isEmpty()) {
            throw new IllegalStateException("模板中没有可检查的大区规则");
        }
        List<String> updates = new ArrayList<>();
        List<String> details = new ArrayList<>();
        details.add("表\t机构字段\t机构ID\t大区字段\t当前大区\t异常条数");
        updates.add("-- 仅生成修复语句，未执行更新；目标大区：" + REGION_ID);
        int checked = 0;
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            connection.setReadOnly(true);
            log.info("使用 jdbcTemplateYL，数据库={}，用户={}",
                    connection.getMetaData().getURL(), connection.getMetaData().getUserName());
            log.info("检查大区，机构数={}，规则数={}，输出目录={}", ORG_IDS.size(), rules.size(), output);
            for (Rule rule : rules.values()) {
                try {
                    check(connection, rule, updates, details);
                    checked++;
                } catch (Exception e) {
                    failures.add(rule.table + "." + rule.regionColumn + "：" + e.getMessage());
                    log.error("大区检查失败，表={}，机构字段={}，大区字段={}",
                            rule.table, rule.orgColumn, rule.regionColumn, e);
                }
            }
        } catch (Exception e) {
            failures.add("数据库检查中断：" + e.getMessage());
            log.error("数据库检查中断，输出目录={}", output, e);
            throw e;
        } finally {
            Files.write(output.resolve("update_region.sql"), updates, StandardCharsets.UTF_8);
            Files.write(output.resolve("region_mismatches.tsv"), details, StandardCharsets.UTF_8);
            Files.write(output.resolve("failed_or_skipped.txt"), failures, StandardCharsets.UTF_8);
            Files.write(output.resolve("skipped_rules.txt"), skipped, StandardCharsets.UTF_8);
        }
        log.info("大区检查结束，完成规则={}，异常分组={}，失败项={}，跳过项={}，输出目录={}",
                checked, details.size() - 1, failures.size(), skipped.size(), output);
        if (!skipped.isEmpty()) {
            log.warn("部分模板缺少机构关联字段，未检查这些项目，请查看 {}", output.resolve("skipped_rules.txt"));
        }
        if (!failures.isEmpty()) {
            throw new IllegalStateException("检查失败项数=" + failures.size() + "，首项=" + failures.get(0)
                    + "，详情请查看 " + output.resolve("failed_or_skipped.txt"));
        }
    }

    private static Map<String, Rule> readRules(List<String> lines, List<String> failures, List<String> skipped) {
        Map<String, Set<String>> tableOrgColumns = new LinkedHashMap<>();
        List<Rule> candidates = new ArrayList<>();
        for (String raw : lines) {
            String line = raw.trim();
            Matcher update = UPDATE.matcher(line);
            if (update.matches()) {
                String table = update.group(1);
                Set<String> orgColumns = tableOrgColumns.computeIfAbsent(table, key -> new LinkedHashSet<>());
                Matcher condition = ORG_CONDITION.matcher(update.group(3));
                String orgColumn = condition.matches() ? condition.group(1) : null;
                if (orgColumn != null) {
                    orgColumns.add(orgColumn);
                }
                Matcher assignment = ASSIGNMENT.matcher(update.group(2));
                while (assignment.find()) {
                    if ("<新机构ID>".equals(assignment.group(2))) {
                        orgColumns.add(assignment.group(1));
                    } else if ("<新机构大区ID>".equals(assignment.group(2))) {
                        candidates.add(new Rule(table, orgColumn, assignment.group(1)));
                    }
                }
                continue;
            }
            Matcher replace = REPLACE.matcher(line);
            if (replace.find() && line.contains("<新机构大区ID>")) {
                String[] columns = replace.group(2).split(",");
                String[] values = replace.group(3).split(",");
                if (columns.length != values.length) {
                    failures.add("无法解析大区模板：" + line);
                    continue;
                }
                String orgColumn = null;
                String regionColumn = null;
                for (int i = 0; i < columns.length; i++) {
                    if ("'<新机构ID>'".equals(values[i].trim()) && "org_id".equals(columns[i].trim())) {
                        orgColumn = columns[i].trim();
                    }
                    if ("'<新机构大区ID>'".equals(values[i].trim())) {
                        regionColumn = columns[i].trim();
                    }
                }
                if (regionColumn != null) {
                    candidates.add(new Rule(replace.group(1), orgColumn, regionColumn));
                }
            } else if (!line.startsWith("--") && line.contains("<新机构大区ID>")) {
                failures.add("未支持的大区模板：" + line);
            }
        }
        Map<String, Rule> rules = new LinkedHashMap<>();
        for (Rule rule : candidates) {
            if (rule.orgColumn == null) {
                Set<String> columns = tableOrgColumns.get(rule.table);
                // 大区单独更新时，只使用模板明确给出的 org_id 或唯一机构字段。
                if (columns != null && columns.contains("org_id")) {
                    rule.orgColumn = "org_id";
                } else if (columns != null && columns.size() == 1) {
                    rule.orgColumn = columns.iterator().next();
                }
            }
            if (rule.orgColumn == null) {
                skipped.add("缺少明确的机构关联字段，跳过：" + rule.table + "." + rule.regionColumn);
            } else {
                rules.put(rule.table + "." + rule.orgColumn + "." + rule.regionColumn, rule);
            }
        }
        return rules;
    }

    private static void check(Connection connection, Rule rule, List<String> updates,
                              List<String> details) throws Exception {
        String[] tableParts = rule.table.split("\\.");
        Set<String> columns = new LinkedHashSet<>();
        // 生成 SQL 前核对真实表结构，模板字段不能代替数据库事实。
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=? AND TABLE_NAME=?")) {
            statement.setQueryTimeout(60);
            statement.setString(1, tableParts[0]);
            statement.setString(2, tableParts[1]);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    columns.add(result.getString(1));
                }
            }
        }
        if (!columns.contains(rule.orgColumn) || !columns.contains(rule.regionColumn)) {
            throw new IllegalStateException("表不存在、无权读取或模板字段不存在");
        }
        String table = quoteIdentifier(tableParts[0]) + "." + quoteIdentifier(tableParts[1]);
        String org = quoteIdentifier(rule.orgColumn);
        String region = quoteIdentifier(rule.regionColumn);
        String orgValues = "'" + String.join("','", ORG_IDS) + "'";
        String where = org + " IN (" + orgValues + ") AND NOT (" + region + " <=> '" + REGION_ID + "')";
        String query = "SELECT " + org + ", " + region + ", COUNT(*) FROM " + table
                + " WHERE " + where + " GROUP BY " + org + ", " + region;
        boolean mismatch = false;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setQueryTimeout(60);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    mismatch = true;
                    String current = result.getString(2);
                    details.add(rule.table + "\t" + rule.orgColumn + "\t" + result.getString(1)
                            + "\t" + rule.regionColumn + "\t" + (current == null ? "NULL" : current)
                            + "\t" + result.getLong(3));
                }
            }
        }
        if (mismatch) {
            updates.add("UPDATE " + table + " SET " + region + " = '" + REGION_ID + "' WHERE " + where + ";");
            log.info("发现大区异常，表={}，机构字段={}，大区字段={}", rule.table, rule.orgColumn, rule.regionColumn);
        }
    }

    private static String quoteIdentifier(String value) {
        if (!value.matches("\\w+")) {
            throw new IllegalArgumentException("非法标识符：" + value);
        }
        return "`" + value + "`";
    }

    private static InputStream resource(String name) {
        InputStream input = CheckOrgRegionSql.class.getClassLoader().getResourceAsStream(name);
        if (input == null) {
            throw new IllegalStateException("找不到资源：" + name);
        }
        return input;
    }

    private static class Rule {
        private final String table;
        private String orgColumn;
        private final String regionColumn;

        private Rule(String table, String orgColumn, String regionColumn) {
            this.table = table;
            this.orgColumn = orgColumn;
            this.regionColumn = regionColumn;
        }
    }
}
