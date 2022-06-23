package com.dy.test.thread;

import org.slf4j.Logger;

public class TimeOutThread extends Thread{

    protected Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());


    private int times;

    private boolean toStop=false;

    public TimeOutThread(int times) {
        this.times = times;
    }

    public void toStop(){
        this.toStop=true;
    }


    @Override
    public void run() {
        int i=0;
        System.out.println("------timeout start----");
        while(!toStop){
            try {
                if(i>=times){
                    break;
                }
                Thread.sleep(1000L);
                i++;
                System.out.println("sleep time "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("------timeout end----");


    }
}
