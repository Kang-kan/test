package com.xxgame.admin.web.modules.datasummary.repository;

import com.xxgame.admin.web.modules.dailystatis.entity.DailyRegistStatis;
import com.xxgame.admin.web.modules.datasummary.entity.SrvDailyCharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 区服充值汇总
 * @author gil
 *
 */
public interface SrvDailyChargeRepository extends JpaRepository<SrvDailyCharge, String> {

    /**
     * 查找区服每日充值
     * @param daily
     * @param serverId
     * @return
     */
    @Query("SELECT t FROM srv_daily_charge t WHERE t.daily = ?1 AND t.serverId = ?2")
    List<SrvDailyCharge> findDailyCharge(int daily, int serverId);

    /**
     * 查找区服每日充值
     * @param startTime
     * @param endTime
     * @param serverId
     * @return
     */
    @Query("SELECT t FROM srv_daily_charge t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId = ?3")
    List<SrvDailyCharge> findDailyCharge(int startTime, int endTime, int serverId);

    /**
     * 分页查找区服每日充值
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param pageable
     * @return
     */
    @Query("SELECT SUM(amount) AS amount, daily, serverId FROM srv_daily_charge t " +
            "WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId IN(?3) " +
            "GROUP BY daily, serverId")
    Page<Object[]> findDailyCharge(int startTime, int endTime, List<Integer> serverIds, Pageable pageable);

    /**
     * 查找各服时间段充值
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @return
     */
    @Query("SELECT t FROM srv_daily_charge t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId = ?3 AND t.channelId = ?4")
    List<SrvDailyCharge> findDailyCharge(int startTime, int endTime, int serverId, String channelId);

}
