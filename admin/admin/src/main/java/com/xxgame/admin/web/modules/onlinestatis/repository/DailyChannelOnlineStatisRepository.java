package com.xxgame.admin.web.modules.onlinestatis.repository;

import com.xxgame.admin.web.modules.onlinestatis.entity.DailyChannelOnlineStatis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 每日渠道在线统计
 * @author gil
 *
 */
public interface DailyChannelOnlineStatisRepository extends JpaRepository<DailyChannelOnlineStatis, String> {

    /**
     * 分页查找每日渠道在线统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_channel_online_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId = ?3 AND t.channelId = ?4")
    Page<DailyChannelOnlineStatis> findOnlineStatis(int startTime, int endTime, int serverId, String channelId, Pageable pageable);

    /**
     * 分页查找每日渠道在线统计
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_channel_online_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId IN(?3) AND t.channelId IN(?4)")
    Page<DailyChannelOnlineStatis> findOnlineStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, Pageable pageable);

    /**
     * 查找每日渠道在线统计
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("SELECT t FROM daily_channel_online_statis t WHERE t.daily >= ?1 AND t.daily < ?2")
    List<DailyChannelOnlineStatis> findOnlineStatis(int startTime, int endTime);
}
