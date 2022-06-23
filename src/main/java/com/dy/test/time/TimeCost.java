package com.dy.test.time;

import com.yd.utils.common.DateUtils;

import java.util.Date;

public class TimeCost {
    public static void main(String[] args) {
        /*long startTime = System.currentTimeMillis();

        for(int i=0;i<50;i++){
            System.out.println();
        }

        long endTime=System.currentTimeMillis();

        System.out.println(endTime-startTime);*/


        Date date=new Date(1594849260001L);

        System.out.println(DateUtils.format(date,"yyyy-MM-dd HH:mm:ss"));


        date=new Date(1594849672930L);

        System.out.println(DateUtils.format(date,"yyyy-MM-dd HH:mm:ss"));

    }
}
