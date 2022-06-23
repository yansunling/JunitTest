package com.dy.test.job;

import com.yd.utils.common.DateUtils;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.sql.Timestamp;
import java.util.Date;

public class JobTime {

    public static void main(String[] args) {

        String cron="0 0 0 0/4 * ?";
        String dateStr="2020-07-07 08:04:00";
        String format="yyyy-MM-dd HH:mm:ss";
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        Date date = DateUtils.parseDate(dateStr, format);
        Date nextTriggerTime = cronSequenceGenerator.next(date);
        System.out.println(DateUtils.format(nextTriggerTime,format));



    }

}
