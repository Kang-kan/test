package com.xxgame.admin.web.modules.chargestatis.repository;

import com.xxgame.admin.web.modules.chargestatis.entity.ChargeLossStatis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 付费登录流失
 * @author gil
 *
 */
public interface ChargeLossStatisRepository extends JpaRepository<ChargeLossStatis, String> {

    /**
     * 查询段时间内的记录数
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("SELECT t FROM charge_loss_statis t WHERE t.daily >= ?1 AND t.daily < ?2")
    List<ChargeLossStatis> findByDaily(int startTime, int endTime);

    /**
     * 付费登录流失
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM charge_loss_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId IN(?3) AND t.channelId IN(?4)")
    Page<ChargeLossStatis> findChargeLoss(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, Pageable pageable);

}
