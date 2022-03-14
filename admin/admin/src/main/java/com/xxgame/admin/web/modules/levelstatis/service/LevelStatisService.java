package com.xxgame.admin.web.modules.levelstatis.service;

import com.alibaba.fastjson.JSONArray;

import java.util.Date;

/**
 * 等级统计
 */
public interface LevelStatisService {

    /**
     * 查询玩家等级数量分布
     * @param serverId
     * @return
     */
    String queryLevelCountStatis(int serverId);

    /**
     * 玩家等级流失数量分布
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    String queryLevelLossStatis(int serverId, Date startTime, Date endTime);
}
