package com.xxgame.admin.web.modules.dailystatis.repository;

import com.xxgame.admin.web.modules.dailystatis.entity.DailyRegistStatis;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 注册统计
 * @author gil
 *
 */
public interface DailyRegistStatisRepository extends JpaRepository<DailyRegistStatis, String> {

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_regist_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId = ?3 AND t.channelId = ?4")
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, int serverId, String channelId, Pageable pageable);

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_regist_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId IN(?3) AND t.channelId IN(?4)")
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, Pageable pageable);

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_regist_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId = ?3")
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, int serverId, Pageable pageable);

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param channelId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_regist_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId > 0 AND t.channelId = ?3")
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, String channelId, Pageable pageable);

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_regist_statis t WHERE t.daily >= ?1 AND t.daily <= ?2")
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, Pageable pageable);

}
