package com.xxgame.admin.web.modules.analysis.service;

import java.util.Date;

/**
 * 在线时长分布
 */
public interface OnlineStatisAnalyzer {

    /**
     * 在线时长分布
     */
    void analyzeOnlineDistStatis(Date date);

    /**
     * 每小时统计在线人数
     * @param date
     */
    void analyzeOnlineStatis(Date date);

}
