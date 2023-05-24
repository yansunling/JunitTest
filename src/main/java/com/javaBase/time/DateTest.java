package com.javaBase.time;

import com.yd.utils.common.DateUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();

        for(int i=0;i<7;i++){
            if(i!=0){
                calendar.add(Calendar.DATE,-1);
            }
            Date time = calendar.getTime();
            Date startOfDay = getStartOfDay(time);
            Date endOfDay = getEndOfDay(time);
            System.out.println(DateUtils.format(startOfDay,"yyyy-MM-dd HH:mm:ss"));
            System.out.println( DateUtils.format(endOfDay,"yyyy-MM-dd HH:mm:ss"));
        }
    }

    // 获得某天最大时间 2017-10-15 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2017-10-15 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }



}
