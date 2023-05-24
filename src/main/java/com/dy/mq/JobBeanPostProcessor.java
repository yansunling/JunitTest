package com.dy.mq;

import com.yd.utils.mq.ExchangeConfig;
import com.yd.utils.mq.ExchangeEnum;
import com.yd.utils.mq.ProducerService;

import java.io.IOException;
//@Component
public class JobBeanPostProcessor extends ProducerService<TASK_JOB_INFO> {

    private static final String ROUTING_KEY = "register";
    @Override
    public void init() throws IOException {
        ExchangeConfig exchange = new ExchangeConfig();
        exchange.setExchangeEnum(ExchangeEnum.DIRECT);
        exchange.setKey(ROUTING_KEY);
        setExchangeConfig(exchange);
        super.init();
    }
}
