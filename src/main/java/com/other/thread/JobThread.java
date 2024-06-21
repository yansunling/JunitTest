package com.other.thread;

import org.slf4j.Logger;

public class JobThread extends Thread{

    protected Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());


    private String name;

    public JobThread(String name) {
        this.name=name;
    }


    @Override
    public void run() {
        logger.info("------"+name+"---start-----------");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String fileName = JobThreadExecutor.contextHolder.get();
        logger.info("------"+name+"-----"+fileName);
        logger.info("------"+name+"---end-----------");
    }
}
