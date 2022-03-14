package com.xxgame.admin.web.modules.scheduled;

import com.xxgame.admin.web.modules.analysis.service.*;
import com.xxgame.admin.web.modules.operator.ChatNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 所有定时任务都定义在这里。注意这只是定义，不要把业务逻辑写在这里。
 * @author gil
 */
@Service
public class ScheduledService {

    @Autowired
    private DataSummaryAnalyzer dataSummaryAnalyzer;
    @Autowired
    private DailyStatisAnalyzer dailyStatisAnalyzer;
    @Autowired
    private OnlineStatisAnalyzer onlineStatisAnalyzer;
    @Autowired
    private ConsumeStatisAnalyzer consumeStatisAnalyzer;
    @Autowired
    private ChatNoticeService chatNoticeService;
    @Autowired
    private ChargeLossStatisAnalyzer chargeLossStatisAnalyzer;
    @Autowired
    private DailyLevelStatisAnalyzer dailyLevelStatisAnalyzer;

    /**
     * 每月1号4:05:01 执行统计月活跃汇总
     */
    @Scheduled(cron = "01 5 4 1 * *")
    private void analyzeMonthlyActive() {
        dataSummaryAnalyzer.analyzeMonthlyActive(new Date());
    }

    /**
     * 每晚12:00:10执行 每日充值汇总
     */
    @Scheduled(cron = "10 0,10,30 0 * * *")
    private void analyzeDailyCharge() {
        dataSummaryAnalyzer.analyzeChannelDailyCharge(new Date());
    }

    /**
     * 每晚12:00:20执行 充值统计
     */
    @Scheduled(cron = "20 0,10,30 0 * * *")
    private void analyzeDailyChargeStatis() {
        dailyStatisAnalyzer.analyzeDailyChargeStatis(new Date());
    }

    /**
     * 每晚12:00:05执行 注册统计
     */
    @Scheduled(cron = "05 0,10,30 0 * * *")
    private void analyzeDailyStatisRegist() {
        dailyStatisAnalyzer.analyzeDailyStatisRegist(new Date());
    }

    /**
     * 每晚12:00:15执行 在线时长分布
     */
    @Scheduled(cron = "15 0,30 0 * * *")
    private void analyzeOnlineDistStatis() {
        onlineStatisAnalyzer.analyzeOnlineDistStatis(new Date());
    }

    /**
     * 每晚12:00:20执行 商城消耗统计
     */
    @Scheduled(cron = "20 0,15,30 0 * * *")
    private void analyzeMarketStatis() {
        consumeStatisAnalyzer.analyzeMarketStatis(new Date());
    }

    /**
     * 每晚12:00:20执行 元宝消耗统计
     */
    @Scheduled(cron = "25 0,15,30 0 * * *")
    private void analyzeGoldStatis() {
        consumeStatisAnalyzer.analyzeGoldStatis(new Date());
    }

    /**
     * 每晚12:01:01执行 分析等级流失
     */
    @Scheduled(cron = "1 1 0 * * *")
    private void analyzeLevelLossStatis() {
        dailyLevelStatisAnalyzer.analyzeLossStatis(new Date());
    }

    /**
     * 每晚12:05:01执行 每日统计
     */
    @Scheduled(cron = "01 5,20,35 0 * * *")
    private void analyzeDailySummary() {
        dailyStatisAnalyzer.analyzeDailySummary(new Date());
    }

    /**
     * 每晚12:05:10执行 每日统计
     */
    @Scheduled(cron = "10 5 0 * * *")
    private void analyzeLoginLoss() {
        dataSummaryAnalyzer.analyzeLoginLoss(new Date());
    }

    /**
     * 每晚12:05:20执行 每日统计
     */
    @Scheduled(cron = "20 5 0 * * *")
    private void analyzeLossStatis() {
        chargeLossStatisAnalyzer.analyzeLossStatis(new Date());
    }

    /**
     * 每小时统计在线人数
     */
    @Scheduled(cron = "05 0 0/1 * * *")
    private void analyzeOnlineStatis() {
        onlineStatisAnalyzer.analyzeOnlineStatis(new Date());
    }

}
