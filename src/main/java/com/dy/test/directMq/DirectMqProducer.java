package com.dy.test.directMq;

import com.yd.utils.mq.ExchangeConfig;
import com.yd.utils.mq.ExchangeEnum;
import com.yd.utils.mq.ProducerService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DirectMqProducer extends ProducerService<TASK_JOB_INFO> {

    public void sendMessage(TASK_JOB_INFO info){
        try {
            send(info,info.getSystemId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DirectMqProducer() {
        ExchangeConfig exchange = new ExchangeConfig();
        exchange.setExchangeEnum(ExchangeEnum.DIRECT);
        setExchangeConfig(exchange);
    }
}
