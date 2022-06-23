package com.dy.test.ops;

import com.yd.utils.mq.ProducerService;
import org.springframework.stereotype.Component;

@Component
public class MqOpsProducer extends ProducerService<com.dy.test.pojo.OPS_ADD_OR_UPDATE_APP> {
}
