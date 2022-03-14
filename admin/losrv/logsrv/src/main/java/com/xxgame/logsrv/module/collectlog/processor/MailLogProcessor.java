package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.MailLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repair.service.MailLongRepairService;
import com.xxgame.logsrv.module.collectlog.repository.MailLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 邮件日志处理
 */
@Service
public class MailLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private MailLogRepository mailLogRepository;
    @Autowired
    private MailLongRepairService mailLongRepairService;

    @Override
    public LogType logType() {
        return LogType.LOG_MAIL;
    }

    @Override
    public void save(JSONObject jsonObject) {
        MailLog mailLog = new MailLog();
        this.setBasePlayerInfo(mailLog, jsonObject);

        mailLog.setMailId(jsonObject.getLongValue("mailId"));
        mailLog.setCreateTime(jsonObject.getLongValue("createTime"));
        mailLog.setMailType(jsonObject.getByteValue("mailType"));
        mailLog.setTitle(jsonObject.getString("title"));
        mailLog.setMessage(jsonObject.getString("message"));
        mailLog.setBonus(jsonObject.getString("bonus"));
        mailLog.setReadStatus(jsonObject.getByteValue("readStatus"));
        mailLog.setIsGet(jsonObject.getByteValue("isGet"));
        mailLog.setConfigId(jsonObject.getIntValue("configId"));
        mailLog.setOpValue(jsonObject.getByteValue("opValue"));

        if (StringUtils.isBlank(mailLog.getTitle())) {
            this.repair(mailLog);
        }

        mailLogRepository.save(mailLog);
    }

    /**
     * 修复数据
     * @param mailLog
     */
    private void repair(MailLog mailLog) {
        JSONObject config = mailLongRepairService.getConfig(mailLog.getConfigId());
        if (config == null) {
            return;
        }

        mailLog.setTitle(config.getString("title"));
        mailLog.setMessage(config.getString("text"));
    }

}
