package com.xxgame.admin.web.LabMQ;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author luguanlin
 * 2022/3/10 11:45
 */
public class RocketMqConsumer {
    private String namesrvAddr;
    private String consumerGroupName;
    private String topic;
    private DefaultMQPushConsumer consumer;

    private static RocketMqConsumer INSTANCE = null;

    public static RocketMqConsumer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RocketMqConsumer();
        }
        return INSTANCE;
    }

    public void init() {
        Properties properties = new Properties();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\WK\\项目\\admin\\admin\\src\\main\\resources\\config\\serve.properties"));
            properties.load(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.namesrvAddr = properties.getProperty("log.collect.namesrvAddr");
        this.consumerGroupName = properties.getProperty("log.collect.groupName");
        this.topic = properties.getProperty("log.collect.topic");
        try {
            buildConsumer();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void buildConsumer() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(messageExt -> {




                    System.out.println(new String(messageExt.getBody()));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.subscribe(topic, "*");
        consumer.start();
    }

}

