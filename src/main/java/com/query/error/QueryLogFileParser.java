package com.query.error;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryLogFileParser {

    private static final Pattern THREAD_PATTERN = Pattern.compile("([A-Za-z0-9-]+-exec-\\d+)");
    private static final Pattern COST_TIME_PATTERN = Pattern.compile("queryId:([^;\\s]+);costTime:(\\d+)");
    private static final Pattern PARAMS_PATTERN = Pattern.compile("queryId:([^;\\s]+);params:(.+)");
    private static final Pattern REQ_DATA_PATTERN = Pattern.compile("queryId:([^,\\s]+),reqData:(\\{.*\\})");
    private static final Pattern ANSI_PATTERN = Pattern.compile("\\u001B\\[[;\\d]*m");

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\yansunling\\Desktop\\query-master-0309173907-597b96bb5f-nd5d5_tlwl_catalina.2026-05-13.out";
        List<QueryParseResult> results = parseFile(filePath);
        for (QueryParseResult result : filterPrintableResults(results)) {
            System.out.println(result);
        }
    }

    public static List<QueryParseResult> parseFile(String filePath) throws IOException {
        String content = FileUtils.readFileToString(new File(filePath), "UTF-8");
        return parseText(content);
    }

    public static List<QueryParseResult> filterPrintableResults(List<QueryParseResult> results) {
        List<QueryParseResult> filtered = new ArrayList<>();
        for (QueryParseResult result : results) {
            if (shouldPrint(result)) {
                filtered.add(result);
            }
        }
        return filtered;
    }

    public static List<QueryParseResult> parseText(String content) {
        String[] lines = content.split("\\r?\\n");
        List<QueryParseResult> orderedResults = new ArrayList<>();
        Map<String, Deque<QueryParseResult>> waitingCostTimeMap = new LinkedHashMap<>();
        Map<String, Deque<QueryParseResult>> waitingConditionMap = new LinkedHashMap<>();
        String currentThread = null;

        for (String line : lines) {
            String normalizedLine = stripAnsi(line);
            String threadName = extractThreadName(normalizedLine);
            if (threadName != null) {
                currentThread = threadName;
            }

            QueryParseResult conditionResult = parseConditionLine(normalizedLine, currentThread);
            if (conditionResult != null) {
                QueryParseResult target = null;
                if (conditionResult.getThreadName() != null) {
                    target = findLatestWaiting(waitingConditionMap, conditionResult.getThreadQueryKey());
                }
                if (target == null) {
                    target = pollWaiting(waitingCostTimeMap, conditionResult.getThreadQueryKey());
                }
                if (target == null) {
                    target = new QueryParseResult();
                    target.setQueryId(conditionResult.getQueryId());
                    target.setThreadName(conditionResult.getThreadName());
                    orderedResults.add(target);
                    offerWaiting(waitingConditionMap, target);
                }
                if (target.getParams() == null || conditionResult.isParamsBased() || !target.isParamsBased()) {
                    target.setParams(conditionResult.getParams());
                    target.setParamsBased(conditionResult.isParamsBased());
                }
                continue;
            }

            QueryParseResult costTimeResult = parseCostTimeLine(normalizedLine, currentThread);
            if (costTimeResult != null) {
                QueryParseResult target = pollWaiting(waitingConditionMap, costTimeResult.getThreadQueryKey());
                if (target == null) {
                    target = new QueryParseResult();
                    target.setQueryId(costTimeResult.getQueryId());
                    target.setThreadName(costTimeResult.getThreadName());
                    orderedResults.add(target);
                    offerWaiting(waitingCostTimeMap, target);
                }
                target.setCostTime(costTimeResult.getCostTime());
            }
        }

        return orderedResults;
    }

    private static QueryParseResult parseConditionLine(String line, String threadName) {
        Matcher paramsMatcher = PARAMS_PATTERN.matcher(line);
        if (paramsMatcher.find()) {
            QueryParseResult result = new QueryParseResult();
            result.setQueryId(paramsMatcher.group(1).trim());
            result.setThreadName(threadName);
            result.setParams(paramsMatcher.group(2).trim());
            result.setParamsBased(true);
            return result;
        }

        Matcher reqDataMatcher = REQ_DATA_PATTERN.matcher(line);
        if (!reqDataMatcher.find()) {
            return null;
        }

        String queryId = reqDataMatcher.group(1).trim();
        String reqData = reqDataMatcher.group(2).trim();

        QueryParseResult result = new QueryParseResult();
        result.setQueryId(queryId);
        result.setThreadName(threadName);
        result.setParams(extractCondition(reqData));
        result.setParamsBased(false);
        return result;
    }

    private static QueryParseResult parseCostTimeLine(String line, String threadName) {
        Matcher matcher = COST_TIME_PATTERN.matcher(line);
        if (!matcher.find()) {
            return null;
        }

        QueryParseResult result = new QueryParseResult();
        result.setQueryId(matcher.group(1).trim());
        result.setThreadName(threadName);
        result.setCostTime(Long.valueOf(matcher.group(2)));
        return result;
    }

    private static String stripAnsi(String line) {
        return ANSI_PATTERN.matcher(line).replaceAll("");
    }

    private static boolean shouldPrint(QueryParseResult result) {
        return "[]".equals(result.getParams())
                &&( result.getCostTime() == null
                || result.getCostTime() > 5000L);
    }

    private static String extractThreadName(String line) {
        Matcher matcher = THREAD_PATTERN.matcher(line);
        if (!matcher.find()) {
            return null;
        }
        return matcher.group(1);
    }

    private static String extractCondition(String reqData) {
        if ("{}".equals(reqData)) {
            return reqData;
        }
        int conditionIndex = reqData.indexOf("\"condition\"");
        if (conditionIndex < 0) {
            return reqData;
        }
        int braceStart = reqData.indexOf('{', conditionIndex);
        if (braceStart < 0) {
            return reqData;
        }
        int braceEnd = findMatchingBrace(reqData, braceStart);
        if (braceEnd < 0) {
            return reqData;
        }
        return reqData.substring(braceStart, braceEnd + 1).trim();
    }

    private static int findMatchingBrace(String text, int braceStart) {
        int level = 0;
        for (int i = braceStart; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current == '{') {
                level++;
            } else if (current == '}') {
                level--;
                if (level == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static QueryParseResult pollWaiting(Map<String, Deque<QueryParseResult>> waitingMap, String key) {
        Deque<QueryParseResult> queue = waitingMap.get(key);
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        QueryParseResult result = queue.pollFirst();
        if (queue.isEmpty()) {
            waitingMap.remove(key);
        }
        return result;
    }

    private static QueryParseResult findLatestWaiting(Map<String, Deque<QueryParseResult>> waitingMap, String key) {
        Deque<QueryParseResult> queue = waitingMap.get(key);
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        return queue.peekLast();
    }

    private static void offerWaiting(Map<String, Deque<QueryParseResult>> waitingMap, QueryParseResult result) {
        String key = result.getThreadQueryKey();
        Deque<QueryParseResult> queue = waitingMap.get(key);
        if (queue == null) {
            queue = new ArrayDeque<>();
            waitingMap.put(key, queue);
        }
        queue.offerLast(result);
    }

    public static class QueryParseResult {
        private String queryId;
        private String threadName;
        private String params;
        private Long costTime;
        private boolean paramsBased;

        public String getQueryId() {
            return queryId;
        }

        public void setQueryId(String queryId) {
            this.queryId = queryId;
        }

        public String getThreadName() {
            return threadName;
        }

        public void setThreadName(String threadName) {
            this.threadName = threadName;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public Long getCostTime() {
            return costTime;
        }

        public void setCostTime(Long costTime) {
            this.costTime = costTime;
        }

        public boolean isParamsBased() {
            return paramsBased;
        }

        public void setParamsBased(boolean paramsBased) {
            this.paramsBased = paramsBased;
        }

        public String getThreadQueryKey() {
            return (threadName == null ? "" : threadName) + "|" + queryId;
        }

        @Override
        public String toString() {
            return "{" +
                    "queryId:'" + queryId + '\'' +
                    ", params:'" + params + '\'' +
                    ", costTime:" + costTime +
                    '}';
        }
    }
}
