package com.xxgame.admin.web.modules.datasummary.repository;

import com.xxgame.admin.web.modules.datasummary.entity.MonthlyActive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 月活跃数据
 * @author gil
 *
 */
public interface MonthlyActiveRepository extends JpaRepository<MonthlyActive, String> {

    /**
     * 分页查找月活跃汇总
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM monthly_active t WHERE t.month >= ?1 AND t.month <= ?2 AND t.serverId = ?3 AND t.channelId = ?4")
    Page<MonthlyActive> findMonthlyActive(int startTime, int endTime, int serverId, String channelId, Pageable pageable);

    /**
     * 分页查找月活跃汇总
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM monthly_active t WHERE t.month >= ?1 AND t.month <= ?2 AND t.serverId IN(?3) AND t.channelId IN(?4)")
    Page<MonthlyActive> findMonthlyActive(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, Pageable pageable);

    /**
     * 根据服务器id和渠道id查找
     * @param serverId
     * @param channelId
     * @return
     */
    List<MonthlyActive> findByServerIdAndChannelId(int serverId, String channelId);

}
