package com.xxgame.admin.web.modules.analysis.service;

import java.util.Date;

/**
 * 日常等级统计
 */
public interface DailyLevelStatisAnalyzer {

    /**
     * 分析等级流失
     * @param date
     */
    void analyzeLossStatis(Date date);
}
