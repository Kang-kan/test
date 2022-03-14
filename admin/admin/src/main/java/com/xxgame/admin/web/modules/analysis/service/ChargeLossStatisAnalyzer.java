package com.xxgame.admin.web.modules.analysis.service;

import java.util.Date;

/**
 * 付费登录流失统计
 */
public interface ChargeLossStatisAnalyzer {

    /**
     * 付费登录流失统计
     * @param date
     */
    void analyzeLossStatis(Date date);
}
