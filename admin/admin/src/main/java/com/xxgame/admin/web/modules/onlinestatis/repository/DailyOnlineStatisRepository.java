package com.xxgame.admin.web.modules.onlinestatis.repository;

import com.xxgame.admin.web.modules.onlinestatis.entity.DailyOnlineStatis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 每日在线统计
 * @author gil
 *
 */
public interface DailyOnlineStatisRepository extends JpaRepository<DailyOnlineStatis, String> {

    /**
     * 分页获取每日每小时在线
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM daily_online_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId IN(?3)")
    Page<DailyOnlineStatis> findOnlineStatis(int startTime, int endTime, List<Integer> serverIds, Pageable pageable);

}
