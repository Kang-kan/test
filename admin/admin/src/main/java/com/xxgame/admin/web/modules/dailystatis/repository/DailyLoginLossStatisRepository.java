package com.xxgame.admin.web.modules.dailystatis.repository;

import com.xxgame.admin.web.modules.dailystatis.entity.DailyLoginLossStatis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 登录留存统计
 * @author gil
 *
 */
public interface DailyLoginLossStatisRepository extends JpaRepository<DailyLoginLossStatis, String> {

    /**
     * 查找已有的统计数据
     * @param dayils
     * @param serverId
     * @return
     */
    @Query("SELECT t FROM daily_login_loss_statis t WHERE t.daily IN(?1) AND t.serverId = ?2")
    List<DailyLoginLossStatis> findLossStatis(List<Integer> dayils, int serverId);

    /**
     * 登录留存统计
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_login_loss_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId IN(?3) AND t.channelId IN(?4)")
    Page<DailyLoginLossStatis> findLossStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, Pageable pageable);

}
