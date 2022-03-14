package com.xxgame.admin.web.modules.levelstatis.service;

import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 等级统计
 */
@Service
public class LevelStatisServiceImpl implements LevelStatisService {

    @Autowired
    private GmClient gmClient;

    @Override
    public String queryLevelCountStatis(int serverId) {
        return gmClient.querySqlForGame(serverId, "PLAYER_LEVEL_COUNT_STATIS", 0, 0, 0, serverId);
    }

    @Override
    public String queryLevelLossStatis(int serverId, Date startTime, Date endTime) {
        Date lastDay = DateUtils.lastDayAm0(new Date());

        Object[] args = new Object[]{ lastDay.getTime(), startTime.getTime(), endTime.getTime(), serverId };
        return gmClient.querySqlForGame(serverId, "PLAYER_LEVEL_LOSS_COUNT_STATIS", 0, 0, 0, args);
    }

}
