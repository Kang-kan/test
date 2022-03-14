package com.xxgame.admin.web.modules.chargestatis.service;

import com.xxgame.admin.web.modules.chargestatis.entity.ChargeLossStatis;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * 充值统计
 */
public interface ChargeStatisService {

    /**
     * 查询流失数量
     * @param serverId
     * @param channelId
     * @param startDate
     * @param endDate
     * @return
     */
    int queryLossCount(int serverId, String channelId, Date startDate, Date endDate);

    /**
     * 分页查找付费登录流失
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ChargeLossStatis> findLossStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize);
}
