package com.dy.test.thread;

import java.util.Timer;
import java.util.TimerTask;

public class DelayTest {
    public static void main(String[] args) throws Exception{
        Timer timer = new Timer();// 实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("退出");
                timer.cancel();
            }
        }, 5000);// 这里百毫秒
        System.out.println("本程序存在5秒后自动退出");


        for(int i=0;i<6;i++){
            System.out.println("-----------"+i);
            Thread.sleep(1000);
        }

    }
}
