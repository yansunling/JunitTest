package com.dy.mqdy;

import com.yd.utils.mq.MqHandler;
import org.springframework.stereotype.Component;

@Component
public class MqHandle extends MqHandler<MQ_DY_INFO> {


    @Override
    public void handler(MQ_DY_INFO mq_dy_info) {
        System.out.println(mq_dy_info);
    }
}
