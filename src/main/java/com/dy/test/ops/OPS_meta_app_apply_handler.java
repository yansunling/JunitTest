package com.dy.test.ops;

import com.dy.components.mqutil.consumer.TaskMqHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OPS_meta_app_apply_handler extends TaskMqHandler<OPS_ADD_OR_UPDATE_APP> {





    Logger logger = LoggerFactory.getLogger(this.getClass());
    public void handler(OPS_ADD_OR_UPDATE_APP message){

        logger.info("收到meta应用申请，message={}",message);






    }
}
