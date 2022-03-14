package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.ChopLog;
import com.xxgame.logsrv.module.collectlog.entity.PokedexLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.PokedexLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 异闻录日志处理
 */
@Service
public class PokedexLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private PokedexLogRepository pokedexLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_POKEDEX;
    }

    @Override
    public void save(JSONObject jsonObject) {
        PokedexLog log = new PokedexLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setOpValue(jsonObject.getIntValue("opValue"));
        log.setPokedexId(jsonObject.getIntValue("pokedexId"));
        log.setPokedexLevel(jsonObject.getIntValue("pokedexLevel"));
        log.setCost(jsonObject.getString("cost"));

        pokedexLogRepository.save(log);
    }
}
