package com.module.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class DeadConsumer extends DefaultConsumer {

    public DeadConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("-----------consume message----------");
        System.out.println("consumerTag: " + consumerTag);
        System.out.println("envelope: " + envelope);
        System.out.println("properties: " + properties);
        System.out.println("body: " + new String(body));
        System.out.println(envelope.getRoutingKey());

        String clusterId = properties.getClusterId();

        properties.getHeaders();


        String value = MQConstant.getKey("dy:task:mq:cache:"+clusterId);
        System.out.println(value);



    }
}
