package com.str;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class StringMain {
    public static void main(String[] args) {
        //打印当前日期
        System.out.println(new Timestamp(1752746520007L));



    }

    public static Timestamp getMonthsLaterFirstDay(Timestamp timestamp, int month) {
        // 转换为 LocalDate（丢弃时间部分）
        LocalDate localDate = timestamp.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // 增加四个月并设置为 1 日
        LocalDate futureDate = localDate.plusMonths(month).withDayOfMonth(1);

        // 组合为 LocalDateTime（00:00:00）
        LocalDateTime futureDateTime = futureDate.atStartOfDay();

        // 转回 Timestamp
        return Timestamp.valueOf(futureDateTime);
    }




}
