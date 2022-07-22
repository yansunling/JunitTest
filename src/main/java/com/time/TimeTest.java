package com.time;

import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.sql.Timestamp;

public class TimeTest {
    public static void main(String[] args) throws Exception{
        System.out.println(new Timestamp(1654478555635L));
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT should_indoc.customer_id, base_customer.cust_salesman_id,sum(should_indoc.should_amt-should_indoc.real_amt) as owing_amount FROM crm_credit_should_indoc should_indoc");
        sql.append(" LEFT JOIN crm_base_customer base_customer ON base_customer.customer_id = should_indoc.customer_id ");
        sql.append(" WHERE should_indoc.customer_id IS NOT NULL AND should_indoc.customer_id != '' GROUP BY should_indoc.customer_id;");
        System.out.println(sql);

        String str="1.0";
        System.out.println(str.split("\\.")[0]);

    }
}
