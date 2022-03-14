package com.xxgame.admin.web.modules.consumestatis.repository;

import com.xxgame.admin.web.modules.consumestatis.entity.MarketStatis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 商城消耗统计
 * @author gil
 *
 */
public interface ConsumeStatisRepository extends JpaRepository<MarketStatis, String> {

    /**
     * 商城消耗统计
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM market_statis t WHERE t.daily >= ?1 AND t.daily <= ?2")
    Page<MarketStatis> findMarketStatis(int startTime, int endTime, Pageable pageable);

    /**
     * 商城消耗统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM market_statis t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId = ?3")
    Page<MarketStatis> findMarketStatis(int startTime, int endTime, int serverId, Pageable pageable);

}
