package com.xxgame.admin.web.modules.repair.service;

import com.xxgame.admin.web.modules.analysis.service.OnlineStatisAnalyzer;
import com.xxgame.admin.web.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

/**
 * 在线时长分布
 */
@Service
public class OnlineStatisRepairtor {

    @Autowired
    private OnlineStatisAnalyzer onlineStatisAnalyzer;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化
     */
    //@PostConstruct
    private void init() {
        this.analyzeOnlineDistStatis();
        this.dailyOnlineStatis();
        this.dailyChannelOnlineStatis();
    }

    /**
     * 在线时长分布数据
     */
    private void analyzeOnlineDistStatis() {
        IntStream.range(20200216, 20200230).forEach(daily -> {
            logger.info("开始修改在线时长分布数据，统计日期：{}", daily);
            onlineStatisAnalyzer.analyzeOnlineDistStatis(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
        IntStream.range(20200301, 20200332).forEach(daily -> {
            logger.info("开始修改在线时长分布数据，统计日期：{}", daily);
            onlineStatisAnalyzer.analyzeOnlineDistStatis(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
        IntStream.range(20200401, 20200431).forEach(daily -> {
            logger.info("开始修改在线时长分布数据，统计日期：{}", daily);
            onlineStatisAnalyzer.analyzeOnlineDistStatis(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
        IntStream.range(20200501, 20200509).forEach(daily -> {
            logger.info("开始修改在线时长分布数据，统计日期：{}", daily);
            onlineStatisAnalyzer.analyzeOnlineDistStatis(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
    }

    /**
     * 每日在线统计
     */
    private void dailyOnlineStatis() {
//        IntStream.range(20200216, 20200230).forEach(daily -> {
//            logger.info("开始修改每日在线统计数据，统计日期：{}", daily);
//            for (int i = 0; i < 24; i++) {
//                long dailyHour = daily * 100 + i;
//                onlineStatisAnalyzer.dailyOnlineStatis(DateUtils.numberToDate(dailyHour, DateUtils.PATTERN_YYYYMMDDHH));
//            }
//        });
//        IntStream.range(20200301, 20200332).forEach(daily -> {
//            logger.info("开始修改每日在线统计数据，统计日期：{}", daily);
//            for (int i = 0; i < 24; i++) {
//                long dailyHour = daily * 100 + i;
//                onlineStatisAnalyzer.dailyOnlineStatis(DateUtils.numberToDate(dailyHour, DateUtils.PATTERN_YYYYMMDDHH));
//            }
//        });
//        IntStream.range(20200401, 20200431).forEach(daily -> {
//            logger.info("开始修改每日在线统计数据，统计日期：{}", daily);
//            for (int i = 0; i < 24; i++) {
//                long dailyHour = daily * 100 + i;
//                onlineStatisAnalyzer.dailyOnlineStatis(DateUtils.numberToDate(dailyHour, DateUtils.PATTERN_YYYYMMDDHH));
//            }
//        });
//        IntStream.range(20200501, 20200509).forEach(daily -> {
//            logger.info("开始修改每日在线统计数据，统计日期：{}", daily);
//            for (int i = 0; i < 24; i++) {
//                long dailyHour = daily * 100 + i;
//                onlineStatisAnalyzer.dailyOnlineStatis(DateUtils.numberToDate(dailyHour, DateUtils.PATTERN_YYYYMMDDHH));
//            }
//        });
    }

    /**
     * 日渠道在线统计
     */
    private void dailyChannelOnlineStatis() {
//        IntStream.range(20200216, 20200230).forEach(daily -> {
//            logger.info("开始修改每日渠道在线统计数据，统计日期：{}", daily);
//            for (int i = 0; i < 24; i++) {
//                long dailyHour = daily * 100 + i;
//                onlineStatisAnalyzer.dailyChannelOnlineStatis(DateUtils.numberToDate(dailyHour, DateUtils.PATTERN_YYYYMMDDHH));
//            }
//        });
//        IntStream.range(20200301, 20200332).forEach(daily -> {
//            logger.info("开始修改每日渠道在线统计数据，统计日期：{}", daily);
//            for (int i = 0; i < 24; i++) {
//                long dailyHour = daily * 100 + i;
//                onlineStatisAnalyzer.dailyChannelOnlineStatis(DateUtils.numberToDate(dailyHour, DateUtils.PATTERN_YYYYMMDDHH));
//            }
//        });
//        IntStream.range(20200401, 20200431).forEach(daily -> {
//            logger.info("开始修改每日渠道在线统计数据，统计日期：{}", daily);
//            for (int i = 0; i < 24; i++) {
//                long dailyHour = daily * 100 + i;
//                onlineStatisAnalyzer.dailyChannelOnlineStatis(DateUtils.numberToDate(dailyHour, DateUtils.PATTERN_YYYYMMDDHH));
//            }
//        });
//        IntStream.range(20200501, 20200509).forEach(daily -> {
//            logger.info("开始修改每日渠道在线统计数据，统计日期：{}", daily);
//            for (int i = 0; i < 24; i++) {
//                long dailyHour = daily * 100 + i;
//                onlineStatisAnalyzer.dailyChannelOnlineStatis(DateUtils.numberToDate(dailyHour, DateUtils.PATTERN_YYYYMMDDHH));
//            }
//        });
    }

}
