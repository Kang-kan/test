package com.xxgame.logsrv.module.mq;

import org.apache.rocketmq.client.exception.MQClientException;

/**
 * 日志消费队
 * @author gil
 */
public interface ConsumerService {

    /**
     * 启动
     * @throws MQClientException
     */
    void start() throws MQClientException;

    /**
     * 关闭
     */
    void stop();
}
