package com.dy.log;

import com.javaBase.thread.JobThreadExecutor;
import org.slf4j.Logger;

public class TestLogFile {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(TestLogFile.class);

    public static void main(String[] args) throws Exception{
        JobThreadExecutor.contextHolder.set("E:/log.log");
        logger.info("1");
        logger.info("2");
        logger.info("3");
//        for(int i=0;i<3;i++){
//            JobThreadExecutor.contextHolder.set("E:/log"+i+".log");
//            JobThread job=new JobThread("thread——"+i);
//            job.start();
//        }
        Thread.sleep(1000);


    }




}
