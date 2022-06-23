package com.dy.test.directMq;

import com.yd.utils.mq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
public class DirectMqCustomer extends ConsumerService<TASK_JOB_INFO> {

    private static String ROUTING_KEY =  "task5";

    @Autowired
    TaskExecInfoHandler resultHandler;

    @Override
    public MqHandler getMqHandler() {
        return resultHandler;
    }

    @Override
    public void init() throws IOException {
        ExchangeConfig exchange = new ExchangeConfig();
        exchange.setExchangeEnum(ExchangeEnum.DIRECT);
        exchange.setKey(ROUTING_KEY);
        setExchangeConfig(exchange);
        new EndPoint(this.getClass().getName());
        super.init();
    }
}
