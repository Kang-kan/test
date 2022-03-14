package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.admin.entity.GameServer;
import com.xxgame.logsrv.module.admin.repository.GameServerRepository;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 服务器信息日志处理
 */
@Service
public class GameServerProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private GameServerRepository gameServerRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_SRV_INFO;
    }

    @Override
    public void save(JSONObject jsonObject) {
        GameServer gameServer = null;
        int id = jsonObject.getIntValue("id");
        Optional<GameServer> optional = gameServerRepository.findById(id);
        if (!optional.isPresent()) {
            // 新增
            gameServer = new GameServer();
            gameServer.setId(id);
            gameServer.setCreateTime(System.currentTimeMillis());
        } else {
            gameServer = optional.get();
        }
        gameServer.setName(jsonObject.getString("name"));
        gameServer.setOperatorId(jsonObject.getIntValue("operatorId"));
        gameServer.setOpenTime(jsonObject.getLongValue("openTime"));
        gameServer.setSchedOpenTime(jsonObject.getLongValue("schedOpenTime"));
        gameServer.setMergeTime(jsonObject.getLongValue("mergerTime"));
        gameServer.setLocalIp(jsonObject.getString("ip"));
        gameServer.setPort(jsonObject.getIntValue("port"));
        gameServer.setGmPort(jsonObject.getIntValue("gmPort"));
        gameServer.setChargePort(jsonObject.getIntValue("chargePort"));
        gameServer.setUpdateTime(jsonObject.getLongValue("time"));
        gameServerRepository.save(gameServer);
    }
}
