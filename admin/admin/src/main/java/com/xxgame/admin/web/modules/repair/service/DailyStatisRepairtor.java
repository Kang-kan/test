package com.xxgame.admin.web.modules.repair.service;

import com.xxgame.admin.web.modules.analysis.service.DailyStatisAnalyzer;
import com.xxgame.admin.web.modules.datasummary.entity.DailySummary;
import com.xxgame.admin.web.modules.datasummary.repository.DailySummaryRepository;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyChannelOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.service.OnlineStatisService;
import com.xxgame.admin.web.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * 日常数据统计
 */
@Service
public class DailyStatisRepairtor {

    @Autowired
    private DailyStatisAnalyzer dailyStatisAnalyzer;
    @Autowired
    private OnlineStatisService onlineStatisService;
    @Autowired
    private DailySummaryRepository dailySummaryRepository;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化
     */
    //@PostConstruct
    private void init() {
        IntStream.range(20200216, 20200230).forEach(daily -> {
            logger.info("开始修改日常数据统计数据，统计日期：{}", daily);
            doRepair(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
        IntStream.range(20200301, 20200332).forEach(daily -> {
            logger.info("开始修改日常数据统计数据，统计日期：{}", daily);
            doRepair(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
        IntStream.range(20200401, 20200431).forEach(daily -> {
            logger.info("开始修改日常数据统计数据，统计日期：{}", daily);
            doRepair(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
        IntStream.range(20200501, 20200510).forEach(daily -> {
            logger.info("开始修改日常数据统计数据，统计日期：{}", daily);
            doRepair(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
    }

    /**
     *
     * @param date
     */
    private void doRepair(Date date) {
        String dateTime = DateUtils.date2String(DateUtils.lastDayAm0(date), DateUtils.PATTERN_YYYYMMDD);
        Date lastDate = DateUtils.lastDayAm0(date);
        Date today = DateUtils.dayAm0(date);

        List<DailyChannelOnlineStatis> onlineMaps = onlineStatisService.findOnlineStatis(lastDate, today);
        for (DailyChannelOnlineStatis row : onlineMaps) {
            String id = this.buildKey(dateTime, row.getServerId(), row.getChannelId());
            Optional<DailySummary> optional = dailySummaryRepository.findById(id);
            if (!optional.isPresent()) {
                continue;
            }

            int[] statis = onlineStatisService.calOnlineStatis(row);

            DailySummary dailySummary = optional.get();
            dailySummary.setMaxOnline(statis[1]);
            dailySummary.setAvgOnline(statis[2]);
            dailySummary.setMinOnline(statis[0]);

            dailySummaryRepository.save(dailySummary);
        }
    }

    /**
     * build key
     * @param dateTime
     * @param serverId
     * @param channelId
     * @return
     */
    private String buildKey(String dateTime, int serverId, String channelId) {
        return String.format("%s%s%s", dateTime, serverId, channelId);
    }

}
