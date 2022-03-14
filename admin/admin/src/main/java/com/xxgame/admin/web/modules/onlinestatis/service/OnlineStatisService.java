package com.xxgame.admin.web.modules.onlinestatis.service;

import com.xxgame.admin.web.modules.onlinestatis.entity.DailyChannelOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.entity.OnlineStatisDist;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * 在线统计
 */
public interface OnlineStatisService {

    /**
     * 在线时长分布
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<OnlineStatisDist> findOnlineDist(int startTime, int endTime, int pageNo, int pageSize);

    /**
     * 在线时长分布
     * @param startTime
     * @param endTime
     * @param serverId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<OnlineStatisDist> findOnlineDist(int startTime, int endTime, int serverId, int pageNo, int pageSize);

    /**
     * 分页获取每日每小时在线
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyOnlineStatis> findOnlineStatis(int startTime, int endTime, List<Integer> serverIds, int pageNo, int pageSize);

    /**
     * 分页查找每日渠道在线统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyChannelOnlineStatis> findOnlineStatis(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize);

    /**
     * 查找每日渠道在线统计
     * @param startTime
     * @param endTime
     * @return
     */
    List<DailyChannelOnlineStatis> findOnlineStatis(Date startTime, Date endTime);

    /**
     * 分布查找每日渠道在线人数
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyChannelOnlineStatis> findChannelOnlineStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize);

    /**
     * 每日最高在线
     * @param daily
     * @param serverId
     * @return
     */
    int getMaxOnline(int daily, int serverId);

    /**
     * 统计在线值
     * @param onlineStatis
     * @return [ min, max, avg ]
     */
    int[] calOnlineStatis(DailyChannelOnlineStatis onlineStatis);

}
