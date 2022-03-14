package com.xxgame.logsrv.module.collectlog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.exception.WaittingOtherLogException;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.processor.CollectLogProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 日志
 */
@Service
public class CollectLogService {

    @Autowired
    private ApplicationContext applicationContext;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 战斗力提供者
     */
    private final ConcurrentMap<LogType, CollectLogProcessor> PROCESSORS = new ConcurrentHashMap<LogType, CollectLogProcessor>();

    @PostConstruct
    private void init() {
        Map<String, CollectLogProcessor> processorMap = applicationContext.getBeansOfType(CollectLogProcessor.class);
        for (CollectLogProcessor processor : processorMap.values()) {
            CollectLogProcessor existsProcessor = PROCESSORS.put(processor.logType(), processor);
            if (existsProcessor != null) {
                logger.error("存在重复的日志处理器: {}", processor.logType());
            }
        }
    }

    /**
     * 保存日志
     * @param msgs
     */
    public boolean save(List<MessageExt> msgs) {
        for (MessageExt messageExt : msgs) {
            try {
                JSONObject jsonObject = JSON.parseObject(messageExt.getBody(), JSONObject.class);
                String logTypeStr = jsonObject.getString("logType");
                // 日志服还没这个类型
                if (StringUtils.isBlank(logTypeStr)) {
                    return false;
                }
                LogType logType = LogType.getByName(logTypeStr);
                if (logType == null) {
                    return false;
                }
                CollectLogProcessor processor = PROCESSORS.get(logType);
                if (processor == null) {
                    return false;
                }

                processor.save(jsonObject);
            } catch (WaittingOtherLogException e) {
                logger.error(e.getMessage() + msgs);
                return false;
            } catch (Exception e) {
                String msg = "保存日志失败：" + msgs;
                logger.error(msg, e);

                // 主键冲突
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    continue;
                }
                return false;
            }
        }

        return true;
    }
}
