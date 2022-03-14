package com.xxgame.admin.web.modules.consumestatis.repository;

import com.xxgame.admin.web.modules.consumestatis.entity.GoldConsumeStatis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 元宝消耗统计
 * @author gil
 *
 */
public interface GoldConsumeStatisRepository extends JpaRepository<GoldConsumeStatis, String> {

    /**
     * 元宝功能消耗统计
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM gold_consume_statis t WHERE t.daily >= ?1 AND t.daily <= ?2")
    Page<GoldConsumeStatis> findFunctionStatis(int startTime, int endTime, Pageable pageable);

    /**
     * 元宝功能消耗统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM gold_consume_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId = ?3")
    Page<GoldConsumeStatis> findFunctionStatis(int startTime, int endTime, int serverId, Pageable pageable);

}
