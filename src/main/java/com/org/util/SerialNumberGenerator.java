package com.org.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class SerialNumberGenerator {
    // 日期格式：年月日时分秒（14位）
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    // 序列号计数器（范围 0000-9999）
    private static final AtomicInteger sequence = new AtomicInteger(0);

    // 上次生成时间戳（用于跨秒重置序列号）
    private static String lastTimestamp = "";

    /**
     * 生成18位递增流水号
     * 格式：14位时间戳 + 4位序列号
     */
    public static synchronized String generate() {
        String currentTimestamp = LocalDateTime.now().format(DATE_FORMAT);

        // 时间戳变化时重置序列号
        if (!currentTimestamp.equals(lastTimestamp)) {
            sequence.set(0);
            lastTimestamp = currentTimestamp;
        }

        // 生成4位序列号（不足补零）
        int seq = sequence.getAndIncrement();
        if (seq > 9999) {
            throw new IllegalStateException("序列号超出范围（单秒超过10000个）");
        }
        String sequencePart = String.format("%04d", seq);

        return currentTimestamp + sequencePart; // 14+4=18位
    }
}
