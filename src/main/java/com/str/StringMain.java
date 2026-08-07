package com.str;

import com.yd.common.exttype.Money;
import com.yd.utils.common.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class StringMain {
    public static void main(String[] args) {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT should_indoc.customer_id, base_customer.cust_salesman_id,sum(should_indoc.should_amt-should_indoc.real_amt) as owing_amount FROM crm_credit_should_indoc should_indoc");
        sql.append(" LEFT JOIN crm_base_customer base_customer ON base_customer.customer_id = should_indoc.customer_id ");
        sql.append(" WHERE should_indoc.customer_id IS NOT NULL AND should_indoc.customer_id != '' GROUP BY should_indoc.customer_id;");
        System.out.println(sql.toString());
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
