package com.dy.mqdy;

import com.yd.utils.mq.ConsumerService;
import com.yd.utils.mq.MqHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqComsumer extends ConsumerService<MQ_DY_INFO> {

    @Autowired
    private MqHandle mqHandle;
    @Override
    public MqHandler getMqHandler() {
        return mqHandle;
    }
}
