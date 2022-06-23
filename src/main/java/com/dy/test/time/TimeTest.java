package com.dy.test.time;

import com.yd.utils.common.DateUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {
    public static void main(String[] args) throws Exception{
        String format = "yyyy-MM-dd HH:mm:ss";
        Date effectTime = new SimpleDateFormat(format).parse("2020-03-21 10:27:00");
        Date expireTime = new SimpleDateFormat(format).parse("2020-03-19 21:27:00");

        Timestamp effect=new Timestamp(effectTime.getTime());
        Timestamp expire=new Timestamp(expireTime.getTime());


//        System.out.println(isEffectiveDate(nowTime, 20, 8));

        boolean availableTime = CompareUtil.availableTime(effect, null);
        System.out.println(availableTime);


    }


    public static boolean isEffectiveDate(Date now,Integer delayStart,Integer delayEnd) {
        boolean delayFlag=false;
        //获取当前时间小时
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int nowHour = cal.get(Calendar.HOUR_OF_DAY);
        System.out.println(nowHour);


        if(delayEnd<delayStart){

            //当前时间大于开始时间
            if(nowHour>=delayStart&&nowHour<delayEnd+24){
                delayFlag=true;
            }else if(nowHour<delayStart){
                //当前时间加24小时
                nowHour+=24;
                //开始小时时间小于结束小时时间，结束时间+24
                if(nowHour>=delayStart&&nowHour<delayEnd+24){
                    delayFlag=true;
                }
            }
        }else{
            //开始小时时间大于结束小时时间
            if(nowHour>=delayStart&&nowHour<delayEnd){
                delayFlag=true;
            }
        }

        String endStr=DateUtils.format(now,"yyyy-MM-dd")+" "+delayEnd+":00:00";
        Date endDate = DateUtils.parseDate(endStr, "yyyy-MM-dd HH:mm:ss");
        //结束时间大于当前时间
        if(now.compareTo(endDate)>=0){
            cal.setTime(endDate);
            cal.add(Calendar.DATE,1);
            endDate=cal.getTime();
        }

        System.out.println(DateUtils.format(endDate,"yyyy-MM-dd HH:mm:ss"));



        return delayFlag;
    }

}
