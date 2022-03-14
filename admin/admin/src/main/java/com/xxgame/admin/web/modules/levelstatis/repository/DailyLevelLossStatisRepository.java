package com.xxgame.admin.web.modules.levelstatis.repository;

import com.xxgame.admin.web.modules.levelstatis.entity.DailyLevelLossStatis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 每日等级流失统计
 * @author gil
 *
 */
public interface DailyLevelLossStatisRepository extends JpaRepository<DailyLevelLossStatis, String> {

    /**
     * 查找每日等级流失统计
     * @param daily
     * @param serverId
     * @return
     */
    @Query("SELECT t FROM daily_level_loss_statis t WHERE t.daily = ?1 AND t.serverId = ?2")
    List<DailyLevelLossStatis> findDailyLoss(int daily, int serverId);

}
