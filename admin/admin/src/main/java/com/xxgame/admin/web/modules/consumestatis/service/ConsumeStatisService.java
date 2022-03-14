package com.xxgame.admin.web.modules.consumestatis.service;

import com.xxgame.admin.web.modules.consumestatis.entity.GoldConsumeStatis;
import com.xxgame.admin.web.modules.consumestatis.entity.MarketStatis;
import org.springframework.data.domain.Page;

/**
 * 商城消耗统计
 */
public interface ConsumeStatisService {

    /**
     * 元宝功能消耗统计
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<GoldConsumeStatis> findFunctionStatis(int startTime, int endTime, int pageNo, int pageSize);

    /**
     * 元宝功能消耗统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<GoldConsumeStatis> findFunctionStatis(int startTime, int endTime, int serverId, int pageNo, int pageSize);

    /**
     * 商城消耗统计
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<MarketStatis> findMarketStatis(int startTime, int endTime, int pageNo, int pageSize);

    /**
     * 商城消耗统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<MarketStatis> findMarketStatis(int startTime, int endTime, int serverId, int pageNo, int pageSize);
}
