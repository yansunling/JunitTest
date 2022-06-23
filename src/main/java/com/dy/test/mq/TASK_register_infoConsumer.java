package com.dy.test.mq;

import com.yd.utils.mq.ConsumerService;
import com.yd.utils.mq.ExchangeConfig;
import com.yd.utils.mq.ExchangeEnum;
import com.yd.utils.mq.MqHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
public class TASK_register_infoConsumer extends ConsumerService<TASK_JOB_INFO> {

    @Autowired
    TASK_register_infoHandler handler;

    @Override
    public MqHandler getMqHandler() {
        return handler;
    }

    @Override
    public void init() throws IOException {
        ExchangeConfig exchange = new ExchangeConfig();
        exchange.setExchangeEnum(ExchangeEnum.DIRECT);
        exchange.setKey("register");
        setExchangeConfig(exchange);
        super.init();
    }
}
