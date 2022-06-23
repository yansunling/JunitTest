package com.dy.test.ops;

import com.yd.utils.mq.ConsumerService;
import com.yd.utils.mq.MqHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpsComsumer extends ConsumerService<META_FROM_OPS_DEPLOY_APPLY_RESULT> {

    @Autowired
    private MqOpsHandle mqHandle;
    @Override
    public MqHandler getMqHandler() {
        return mqHandle;
    }
}
