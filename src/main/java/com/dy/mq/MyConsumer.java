package com.dy.mq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.yd.utils.common.StringUtils;

import java.io.IOException;
import java.util.Map;

public class MyConsumer extends DefaultConsumer {

    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("-----------consume message----------");
        System.out.println("consumerTag: " + consumerTag);
        System.out.println("envelope: " + envelope);
        System.out.println("properties: " + properties);
        System.out.println("body: " + new String(body));
        String data=new String(body);

        Map map = JSONObject.parseObject(data, Map.class);
        String test = map.get("test")+"";
        String clusterId = properties.getClusterId();
        System.out.println(clusterId);
        if(StringUtils.equals(test,"1")){
            MQConstant.isERROR(clusterId);
        }else{
            MQConstant.isSuccess(clusterId);
        }

        getChannel().basicNack(envelope.getDeliveryTag(),false,false);

    }
}
