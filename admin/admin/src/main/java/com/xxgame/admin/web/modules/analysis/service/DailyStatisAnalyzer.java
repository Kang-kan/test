package com.xxgame.admin.web.modules.analysis.service;

import java.util.Date;

/**
 * 日常数据统计
 */
public interface DailyStatisAnalyzer {

    /**
     * 充值统计
     * @param date
     */
    void analyzeDailyChargeStatis(Date date);

    /**
     * 注册统计
     * @param date
     */
    void analyzeDailyStatisRegist(Date date);

    /**
     * 每日汇总
     * @param date
     */
    void analyzeDailySummary(Date date);
}
