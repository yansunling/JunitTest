package com.module.mq;

import com.yd.utils.mq.MqHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TASK_register_infoHandler extends MqHandler<TASK_JOB_INFO> {

    private Logger logger = LoggerFactory.getLogger(getClass());



    @Override
    public void handler(TASK_JOB_INFO info) {
        System.out.println("------收到消息");
        System.out.println(info);
        System.out.println("------收到结束");
    }
}
