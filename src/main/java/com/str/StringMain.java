package com.str;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yd.common.busi.builder.base.BusiNo;
import com.yd.utils.common.BeanConvertUtils;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class StringMain {
    public static void main(String[] args) {

        String sql="insert into mpp2.mpp2_prise_ver_cust_item(prise_cust_ver_id,customer_id,customer_name,big_area,small_area,depart_org,prise_version,last_city,goods_type,product_type,inventory_org_id,effective_time,expiry_time,disable_reason,is_tax,expire_status,status,history_prise_id,apply_price_id,remark,op_user_id,update_time,creator,create_time,last_use_time) VALUES('f8b5ce78954748dda5814c134fe2db26','1034173','王奕翔','35010102','3501010202','350101010502','20220717','530100','c','G','','2022-07-17 08:46:25.0','2025-11-27 23:59:59.0','延长有效期','0','2','1','null','null','220717093532341200','T2956','2025-05-28 16:20:53.0','T0095','2022-07-17 09:35:28.0','2025-07-14 21:37:07.0');";

        Timestamp startTime=Timestamp.valueOf("2025-04-01 00:00:00");
        System.out.println(startTime);

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
