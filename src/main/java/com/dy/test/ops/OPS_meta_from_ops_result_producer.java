package com.dy.test.ops;

import com.dy.components.mqutil.producer.TaskProducerService;
import org.springframework.stereotype.Component;

@Component
//@ProducerInfo(desc="meta部署结果推送",author = "5498",queue = "META_FROM_OPS_DEPLOY_APPLY_RESULT")
public class OPS_meta_from_ops_result_producer extends TaskProducerService<OPS_info> {
}
