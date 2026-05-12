package com.query.error;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestTraceLogParser {

    // 日志里带 ANSI 颜色码时，先去掉再做 request-trace 正则匹配，避免匹配失败。
    private static final Pattern ANSI_PATTERN = Pattern.compile("\\u001B\\[[;\\d]*m");
    private static final Pattern START_PATTERN = Pattern.compile("\\[request-trace\\]\\s+START\\s+requestId=([^,\\s]+),\\s+uri=([^,\\s]+)");
    private static final Pattern END_PATTERN = Pattern.compile("\\[request-trace\\]\\s+END\\s+requestId=([^,\\s]+),\\s+uri=([^,\\s]+)(?:,\\s+costMs=([^,\\s]+))?");
    // 有些异常请求不会打印 END，而是直接打印 ERROR，这里把 ERROR 当作一次请求的收口日志处理。
    private static final Pattern ERROR_PATTERN = Pattern.compile("\\[request-trace\\]\\s+ERROR\\s+requestId=([^,\\s]+),\\s+uri=([^,\\s]+).*?,\\s+costMs=([^,\\s]+)");
    private static final long COST_THRESHOLD = 5000L;
    private static final String DEFAULT_FILE_PATH = "C:\\Users\\yansunling\\Desktop\\1.log";

    public static void main(String[] args) throws IOException {
        String filePath = args != null && args.length > 0 ? args[0] : DEFAULT_FILE_PATH;
        List<RequestTraceRecord> abnormalRecords = parseAbnormalRecords(filePath);
        for (RequestTraceRecord record : abnormalRecords) {
            System.out.println(record);
            System.out.println("--------------------------------------------------");
        }
        System.out.println("异常记录总数: " + abnormalRecords.size());
    }

    public static List<RequestTraceRecord> parseAbnormalRecords(String filePath) throws IOException {
        Map<String, RequestTraceRecord> recordMap = parseFile(filePath);
        List<RequestTraceRecord> abnormalRecords = new ArrayList<RequestTraceRecord>();
        for (RequestTraceRecord record : recordMap.values()) {
            if (shouldPrint(record)) {
                abnormalRecords.add(record);
            }
        }
        return abnormalRecords;
    }

    private static Map<String, RequestTraceRecord> parseFile(String filePath) throws IOException {
        Map<String, RequestTraceRecord> recordMap = new LinkedHashMap<String, RequestTraceRecord>();
        Path path = Paths.get(filePath);
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String normalizedLine = stripAnsi(line);
                if (normalizedLine.contains("[request-trace] START")) {
                    handleStartLine(recordMap, normalizedLine, line);
                    continue;
                }
                if (normalizedLine.contains("[request-trace] END")) {
                    handleEndLine(recordMap, normalizedLine, line);
                    continue;
                }
                if (normalizedLine.contains("[request-trace] ERROR")) {
                    handleErrorLine(recordMap, normalizedLine, line);
                }
            }
        } finally {
            reader.close();
        }
        return recordMap;
    }

    private static void handleStartLine(Map<String, RequestTraceRecord> recordMap, String normalizedLine, String rawLine) {
        Matcher matcher = START_PATTERN.matcher(normalizedLine);
        if (!matcher.find()) {
            return;
        }
        String requestId = matcher.group(1).trim();
        RequestTraceRecord record = getOrCreate(recordMap, requestId);
        record.setRequestId(requestId);
        record.setUri(matcher.group(2).trim());
        record.setStartRaw(rawLine);
    }

    private static void handleEndLine(Map<String, RequestTraceRecord> recordMap, String normalizedLine, String rawLine) {
        Matcher matcher = END_PATTERN.matcher(normalizedLine);
        if (!matcher.find()) {
            return;
        }
        String requestId = matcher.group(1).trim();
        RequestTraceRecord record = getOrCreate(recordMap, requestId);
        record.setRequestId(requestId);
        record.setUri(matcher.group(2).trim());
        record.setEndRaw(rawLine);
        record.setCostMs(parseCostMs(matcher.group(3)));
    }

    private static void handleErrorLine(Map<String, RequestTraceRecord> recordMap, String normalizedLine, String rawLine) {
        Matcher matcher = ERROR_PATTERN.matcher(normalizedLine);
        if (!matcher.find()) {
            return;
        }
        String requestId = matcher.group(1).trim();
        RequestTraceRecord record = getOrCreate(recordMap, requestId);
        record.setRequestId(requestId);
        record.setUri(matcher.group(2).trim());
        record.setEndRaw(rawLine);
        record.setCostMs(parseCostMs(matcher.group(3)));
    }

    private static RequestTraceRecord getOrCreate(Map<String, RequestTraceRecord> recordMap, String requestId) {
        RequestTraceRecord record = recordMap.get(requestId);
        if (record == null) {
            record = new RequestTraceRecord();
            recordMap.put(requestId, record);
        }
        return record;
    }

    private static Long parseCostMs(String costMsText) {
        if (costMsText == null || costMsText.trim().length() == 0) {
            return null;
        }
        try {
            return Long.valueOf(costMsText.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static boolean shouldPrint(RequestTraceRecord record) {
        // 只保留关注的异常请求：没有收口、收口但没拿到 costMs、或者耗时超过阈值。
        if (record.getStartRaw() != null && record.getEndRaw() == null) {
            return true;
        }
        if (record.getEndRaw() != null && record.getCostMs() == null) {
            return true;
        }
        return record.getCostMs() != null && record.getCostMs().longValue() >= COST_THRESHOLD;
    }

    private static String stripAnsi(String line) {
        return ANSI_PATTERN.matcher(line).replaceAll("");
    }

    public static class RequestTraceRecord {
        private String requestId;
        private String uri;
        private Long costMs;
        private String startRaw;
        // 这里统一承接 END 或 ERROR 原文，方便直接回看日志现场。
        private String endRaw;

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Long getCostMs() {
            return costMs;
        }

        public void setCostMs(Long costMs) {
            this.costMs = costMs;
        }

        public String getStartRaw() {
            return startRaw;
        }

        public void setStartRaw(String startRaw) {
            this.startRaw = startRaw;
        }

        public String getEndRaw() {
            return endRaw;
        }

        public void setEndRaw(String endRaw) {
            this.endRaw = endRaw;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("requestId=").append(requestId).append('\n');
            sb.append("uri=").append(uri).append('\n');
            sb.append("costMs=").append(costMs == null ? "" : costMs).append('\n');
            sb.append("start日志原文=").append(startRaw == null ? "" : startRaw).append('\n');
            sb.append("end日志原文=").append(endRaw == null ? "" : endRaw);
            return sb.toString();
        }
    }
}
