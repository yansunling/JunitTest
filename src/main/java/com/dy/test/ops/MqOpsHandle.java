package com.dy.test.ops;

import com.dy.test.mqdy.MQ_DY_INFO;
import com.yd.utils.mq.MqHandler;
import org.springframework.stereotype.Component;

@Component
public class MqOpsHandle extends MqHandler<META_FROM_OPS_DEPLOY_APPLY_RESULT> {


    @Override
    public void handler(META_FROM_OPS_DEPLOY_APPLY_RESULT mq_dy_info) {
        System.out.println("-----------------------");
        System.out.println(mq_dy_info);
        System.out.println("-----------------------");
    }
}
