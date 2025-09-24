package com.str;

import com.yd.utils.common.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class StringMain {
    public static void main(String[] args) {
        //打印当前日期
        System.out.println(new Timestamp(1752746520007L));

        System.out.println(bankProcess(" 徐荷花"));
        String customerName="徐荷花-服装W";
        String[] split = customerName.split("-");
        System.out.println(split[0]);

    }

    private static String bankProcess(String str){
        if(StringUtils.isNotBlank(str)){
            str=str.trim();
            str.replaceAll("\\s", "");
        }
        return str;
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
