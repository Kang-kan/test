package com.xxgame.admin.web.modules.analysis.service;

import java.util.Date;

/**
 * 商城消耗统计
 */
public interface ConsumeStatisAnalyzer {

    /**
     * 商城消耗统计
     * @param date
     */
    void analyzeMarketStatis(Date date);

    /**
     * 元宝消耗统计
     * @param date
     */
    void analyzeGoldStatis(Date date);
}
