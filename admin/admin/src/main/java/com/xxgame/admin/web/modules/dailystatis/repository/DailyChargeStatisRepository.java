package com.xxgame.admin.web.modules.dailystatis.repository;

import com.xxgame.admin.web.modules.dailystatis.entity.DailyChargeStatis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 区服充值汇总
 * @author gil
 *
 */
public interface DailyChargeStatisRepository extends JpaRepository<DailyChargeStatis, String> {

    /**
     * 分页查找月活跃汇总
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_charge_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId = ?3 AND t.channelId = ?4")
    Page<DailyChargeStatis> findChargeStatis(int startTime, int endTime, int serverId, String channelId, Pageable pageable);

}
