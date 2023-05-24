package com.javaBase.time;



import com.yd.utils.common.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {
    public static void main(String[] args) {
        Long time=1569814828000L;
        Date date=new Date(time);
        System.out.println(date);
        String format = DateUtils.format(date, "yyyy-MM-dd HH:mm:ss");



        System.out.println(format);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        System.out.println(dateString);


//        System.out.println(format);
    }
}
