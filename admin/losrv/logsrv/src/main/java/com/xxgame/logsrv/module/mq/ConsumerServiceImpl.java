package com.xxgame.logsrv.module.mq;

import com.xxgame.logsrv.module.collectlog.service.CollectLogService;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 日志消费
 * @author gil
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Value("${xxgame.namesrvAddr}")
    private String namesrvAddr;
    @Value("${xxgame.groupName}")
    private String groupName;
    @Value("${xxgame.subscribeTopic}")
    private String subscribeTopic;
    @Value("${xxgame.accessKey}")
    private String accessKey;
    @Value("${xxgame.secretKey}")
    private String secretKey;

    @Autowired
    private CollectLogService collectLogService;

    DefaultMQPushConsumer consumer = null;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void start() throws MQClientException {
        consumer = new DefaultMQPushConsumer(this.getAclRPCHook());
        consumer.setConsumerGroup(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(subscribeTopic, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                boolean saveResult = collectLogService.save(msgs);
                return saveResult ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });

        consumer.start();
        logger.info("已启动日志MQ消费端");
    }

    /**
     * rocket mq acl
     * @return
     */
    private RPCHook getAclRPCHook() {
        return new AclClientRPCHook(new SessionCredentials(accessKey, secretKey));
    }

    @PreDestroy
    @Override
    public void stop() {
        if (consumer != null) {
            consumer.shutdown();
            logger.info("关闭日志MQ消费端");
        }
    }

}
