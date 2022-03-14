package com.xxgame.admin.web.modules.repair.service;

import com.xxgame.admin.web.modules.analysis.service.DailyLevelStatisAnalyzer;
import com.xxgame.admin.web.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

/**
 * 等级流失
 */
@Service
public class LevelLossRepairtor {

    @Autowired
    private DailyLevelStatisAnalyzer dailyLevelStatisAnalyzer;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化
     */
    //@PostConstruct
    private void init() {
        IntStream.range(20200216, 20200230).forEach(daily -> {
            logger.info("开始修改等级流失数据，统计日期：{}", daily);
            dailyLevelStatisAnalyzer.analyzeLossStatis(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
        IntStream.range(20200301, 20200324).forEach(daily -> {
            logger.info("开始修改等级流失数据，统计日期：{}", daily);
            dailyLevelStatisAnalyzer.analyzeLossStatis(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
    }

}
