package com.javaBase.thread;

public class TimeOutTest {
    public static void main(String[] args) throws Exception{
        System.out.println("---------start-------------");
        int times=10;
        TimeOutThread timeout=new TimeOutThread(times);
        timeout.start();

        for(int i=0;i<20;i++){
            System.out.println("-------"+i+"-------------");
            Thread.sleep(1000L);
        }

        timeout.toStop();

        System.out.println("---------ent-------------");


        Thread.sleep(100000L);



    }
}
