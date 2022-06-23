package com.dy.test.ops;

import com.dy.components.mqutil.consumer.TaskConsumerService;
import com.dy.components.mqutil.consumer.TaskMqHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
//@ConsumerInfo(desc = "meta应用申请消费者",author = "5498",queue = "OPS_ADD_OR_UPDATE_APP")
public class OPS_meta_app_apply_consumer extends TaskConsumerService<OPS_ADD_OR_UPDATE_APP> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OPS_meta_app_apply_handler handler;
    @Override
    public TaskMqHandler getMqHandler() {

//        logger.info("收到meta应用申请，message={}",message);
//        OPS_ADD_OR_UPDATE_APP queue = JSON.parseObject(message.getBody(), OPS_ADD_OR_UPDATE_APP.class);
//        handler.handle(queue) ;
//        logger.info("消息处理成功，message={}",queue);
        return handler;
    }


}
