package com.str.string;



import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTest {

    private static Map<String,String> map=new HashMap<>();

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Pattern MUSTACHE_PATTERN = Pattern.compile("\\{\\{([^}]+)\\}\\}");

    public static void main(String[] args){
        String sql="{\"terms\":{\"org_id\":[\"${org_ids}\"]}}";
        sql=sql.replaceAll("\\[\"\\$\\{org_ids}\"]","{{#toJson}}org_ids{{/toJson}}");
        System.out.println(sql);
    }


    @SneakyThrows
    public static String toPrettyFormat(String templateStr) {
        if (templateStr == null || templateStr.trim().isEmpty()) {
            return templateStr;
        }

        // 1. 提取所有 {{...}} 并替换为唯一临时占位符
        Map<String, String> placeholderMap = new LinkedHashMap<>();
        String tempStr = templateStr;
        Matcher matcher = MUSTACHE_PATTERN.matcher(templateStr);
        int index = 0;

        while (matcher.find()) {
            String original = matcher.group(0); // e.g., "{{order_close_type}}"
            String placeholder = "__MUSTACHE_PLACEHOLDER_" + index + "__";
            placeholderMap.put(placeholder, original);
            index++;
        }

        // 执行替换（从后往前替换，避免索引偏移问题；或全局替换）
        String workingStr = templateStr;
        for (Map.Entry<String, String> entry : placeholderMap.entrySet()) {
            // 转义特殊字符（虽然 placeholder 是我们生成的，一般不需要）
            workingStr = workingStr.replace(entry.getValue(), "\"" + entry.getKey() + "\"");
        }

        // 2. 解析为 JsonNode 并格式化
        JsonNode jsonNode = objectMapper.readTree(workingStr);
        String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

        // 3. 将临时占位符还原为原始 {{...}}
        String result = prettyJson;
        for (Map.Entry<String, String> entry : placeholderMap.entrySet()) {
            // 注意：Jackson 输出的字符串会带双引号，所以我们要替换的是带引号的 placeholder
            String quotedPlaceholder = "\"" + entry.getKey() + "\"";
            result = result.replace(quotedPlaceholder, entry.getValue());
        }

        return result;
    }



    static {
        map.put("32060001","2501080402");
        map.put("32060001","2501080405");
        map.put("33060002","250108010204");
        map.put("33078201","250108010301");
        map.put("33078201","2501080105");
        map.put("52010003","250110030301");
        map.put("52010005","250110040601");
        map.put("52010006","250108010204");
        map.put("520201","250110040502");
        map.put("520300","250110040403");
        map.put("520400","250110040401");
        map.put("520500","250110040503");
        map.put("522301","250110040501");
        map.put("522601","250110040402");
        map.put("53010001","250110010301");
        map.put("53010002","250110020601");
        map.put("53010003","250110010302");
    }



}
