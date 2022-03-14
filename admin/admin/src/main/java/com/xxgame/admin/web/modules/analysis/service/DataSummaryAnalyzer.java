package com.xxgame.admin.web.modules.analysis.service;

import java.util.Date;

/**
 * 数据分析服务类
 */
public interface DataSummaryAnalyzer {

    /**
     * 分析月活跃汇总
     */
    void analyzeMonthlyActive(Date date);

    /**
     * 各服每日充值统计
     * @param date
     */
    void analyzeChannelDailyCharge(Date date);

    /**
     * 各服每日登录留存统计
     * @param date
     */
    void analyzeLoginLoss(Date date);
}
