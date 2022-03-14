package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.WeaponSoulLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.WeaponSoulLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 器灵日志处理
 */
@Service
public class WeaponSoulLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private WeaponSoulLogRepository weaponSoulLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_WEAPON_SOUL;
    }

    @Override
    public void save(JSONObject jsonObject) {
        WeaponSoulLog log = new WeaponSoulLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        log.setOpValue(jsonObject.getIntValue("opValue"));
        log.setWeaponIndex(jsonObject.getIntValue("weaponIndex"));
        log.setEquipId(jsonObject.getIntValue("equipId"));
        log.setCost(jsonObject.getString("cost"));
        log.setElfExp(jsonObject.getIntValue("elfExp"));
        log.setDeleteEquipIds(jsonObject.getString("deleteEquipIds"));

        weaponSoulLogRepository.save(log);
    }
}
