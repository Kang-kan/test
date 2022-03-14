package com.xxgame.admin.web.modules.analysis.service;

import com.xxgame.admin.web.modules.commons.logdao.*;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyChargeStatis;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyRegistStatis;
import com.xxgame.admin.web.modules.dailystatis.repository.DailyChargeStatisRepository;
import com.xxgame.admin.web.modules.dailystatis.repository.DailyRegistStatisRepository;
import com.xxgame.admin.web.modules.datasummary.entity.DailySummary;
import com.xxgame.admin.web.modules.datasummary.repository.DailySummaryRepository;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyChannelOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.repository.DailyOnlineStatisRepository;
import com.xxgame.admin.web.modules.onlinestatis.service.OnlineStatisService;
import com.xxgame.admin.web.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日常数据统计
 */
@Service
public class DailyStatisAnalyzerImpl implements DailyStatisAnalyzer {

    @Autowired
    private ChargeOrderLogDao chargeOrderLogDao;
    @Autowired
    private PlayerCreateLogDao playerCreateLogDao;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private DailyChargeStatisRepository dailyChargeStatisRepository;
    @Autowired
    private DailyRegistStatisRepository dailyRegistStatisRepository;
    @Autowired
    private DailySummaryRepository dailySummaryRepository;
    @Autowired
    private DailyOnlineStatisRepository dailyOnlineStatisRepository;
    @Autowired
    private OnlineStatisService onlineStatisService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void analyzeDailyChargeStatis(Date date) {
        String dateTime = DateUtils.date2String(DateUtils.lastDayAm0(date), DateUtils.PATTERN_YYYYMMDD);

        List<Map<String, Object>> rowMaps = chargeOrderLogDao.analyzeDailyStatisCharge(date);
        for (Map<String, Object> row : rowMaps) {
            int serverId = (int) row.get("server_id");
            String channelId = (String) row.get("channel_id");
            int chargeCount = ((Number) row.get("chargeCount")).intValue();
            int chargePlayer = ((Number) row.get("chargePlayer")).intValue();
            int chargeAmout = ((Number) row.get("chargeAmout")).intValue();
            int todyAmout = ((Number) row.get("todyAmout")).intValue();

            DailyChargeStatis dailyChargeStatis = new DailyChargeStatis();
            dailyChargeStatis.setId(dateTime + serverId + channelId);
            dailyChargeStatis.setServerId(serverId);
            dailyChargeStatis.setChannelId(channelId);
            dailyChargeStatis.setDaily(Integer.parseInt(dateTime));
            dailyChargeStatis.setChargeCount(chargeCount);
            dailyChargeStatis.setChargePlayer(chargePlayer);
            dailyChargeStatis.setChargeAmout(chargeAmout);
            dailyChargeStatis.setTodyAmout(todyAmout);

            dailyChargeStatisRepository.save(dailyChargeStatis);
        }
    }

    @Override
    public void analyzeDailyStatisRegist(Date date) {
        String dateTime = DateUtils.date2String(DateUtils.lastDayAm0(date), DateUtils.PATTERN_YYYYMMDD);

        List<Map<String, Object>> rowMaps = playerCreateLogDao.analyzeDailyStatisRegist(date);
        for (Map<String, Object> row : rowMaps) {
            int serverId = (int) row.get("server_id");
            String channelId = (String) row.get("channel_id");
            int count = ((Number) row.get("count")).intValue();

            DailyRegistStatis dailyRegistStatis = new DailyRegistStatis();
            dailyRegistStatis.setId(dateTime + serverId + channelId);
            dailyRegistStatis.setServerId(serverId);
            dailyRegistStatis.setChannelId(channelId);
            dailyRegistStatis.setDaily(Integer.parseInt(dateTime));
            dailyRegistStatis.setRegistCount(count);

            dailyRegistStatisRepository.save(dailyRegistStatis);
        }
    }

    @Override
    public void analyzeDailySummary(Date date) {
        String dateTime = DateUtils.date2String(DateUtils.lastDayAm0(date), DateUtils.PATTERN_YYYYMMDD);
        Date lastDate = DateUtils.lastDayAm0(date);
        Date today = DateUtils.dayAm0(date);

        Map<String, DailySummary> entityMaps = new HashMap<>();

        // 创建角色数
        {
            List<Map<String, Object>> registMaps = playerCreateLogDao.analyzeDailyStatisRegist(date);
            for (Map<String, Object> row : registMaps) {
                int serverId = (int) row.get("server_id");
                String channelId = (String) row.get("channel_id");
                int count = ((Number) row.get("count")).intValue();

                DailySummary dailySummary = getOrCreateDailySummary(entityMaps, dateTime, serverId, channelId);
                dailySummary.setCreatePlayer(count);
            }
        }
        // 登录人数
        {
            List<Map<String, Object>> loginMaps = loginLogDao.analyzeDailyStatisLogin(date);
            for (Map<String, Object> row : loginMaps) {
                int serverId = (int) row.get("server_id");
                String channelId = (String) row.get("channel_id");
                int count = ((Number) row.get("count")).intValue();

                DailySummary dailySummary = getOrCreateDailySummary(entityMaps, dateTime, serverId, channelId);
                dailySummary.setLoginPlayer(count);
            }
        }
        // 在线
        {
            List<DailyChannelOnlineStatis> onlineMaps = onlineStatisService.findOnlineStatis(lastDate, today);
            for (DailyChannelOnlineStatis row : onlineMaps) {
                int[] statis = onlineStatisService.calOnlineStatis(row);

                DailySummary dailySummary = getOrCreateDailySummary(entityMaps, dateTime, row.getServerId(), row.getChannelId());
                dailySummary.setMaxOnline(statis[1]);
                dailySummary.setAvgOnline(statis[2]);
                dailySummary.setMinOnline(statis[0]);
            }
        }
        // 充值
        {
            List<Map<String, Object>> chargeMaps = chargeOrderLogDao.dailyChargeStatis(lastDate.getTime(), today.getTime());
            for (Map<String, Object> row : chargeMaps) {
                int serverId = (int) row.get("server_id");
                String channelId = (String) row.get("channel_id");
                int chargeCount = ((Number) row.get("chargeCount")).intValue();
                int chargePlayer = ((Number) row.get("chargePlayer")).intValue();
                int amount = ((Number) row.get("amount")).intValue();

                DailySummary dailySummary = getOrCreateDailySummary(entityMaps, dateTime, serverId, channelId);
                dailySummary.setChargeCount(chargeCount);
                dailySummary.setChargePlayer(chargePlayer);
                dailySummary.setChargeAmount(amount);
                if (dailySummary.getLoginPlayer() != 0) {
                    dailySummary.setArpu(amount / dailySummary.getLoginPlayer());
                    dailySummary.setChargeRate(chargePlayer * 1.0F / dailySummary.getLoginPlayer());
                }
                if (chargePlayer != 0) {
                    dailySummary.setArppu(amount / chargePlayer);
                }
            }
        }
        // 新增用户每日点击次数、点击人数
        {
            List<Map<String, Object>> newClickMaps = chargeOrderLogDao.dailyNewClickStatis(lastDate.getTime(), today.getTime());
            for (Map<String, Object> row : newClickMaps) {
                int serverId = (int) row.get("server_id");
                String channelId = (String) row.get("channel_id");
                int clickCount = ((Number) row.get("clickCount")).intValue();
                int clickPlayer = ((Number) row.get("clickPlayer")).intValue();

                DailySummary dailySummary = getOrCreateDailySummary(entityMaps, dateTime, serverId, channelId);
                dailySummary.setNewChargeClickCount(clickCount);
                dailySummary.setNewChargeClickPlayer(clickPlayer);
            }
        }
        // 统计新用户每日充值人次
        {
            List<Map<String, Object>> newChargeMaps = chargeOrderLogDao.dailyNewChargeStatis(lastDate.getTime(), today.getTime());
            for (Map<String, Object> row : newChargeMaps) {
                int serverId = (int) row.get("server_id");
                String channelId = (String) row.get("channel_id");
                int chargeCount = ((Number) row.get("chargeCount")).intValue();
                int chargePlayer = ((Number) row.get("chargePlayer")).intValue();
                int amount = ((Number) row.get("amount")).intValue();

                DailySummary dailySummary = getOrCreateDailySummary(entityMaps, dateTime, serverId, channelId);
                // 新增用户点击付费率
                if (dailySummary.getNewChargeClickCount() != 0) {
                    dailySummary.setNewChargeClickRate(chargeCount * 1.0F / dailySummary.getNewChargeClickCount());
                }
                dailySummary.setNewChargePlayer(chargePlayer);
                dailySummary.setNewChargeAmount(amount);
                // 新增付费率、新增用户ARPU
                if (dailySummary.getCreatePlayer() != 0) {
                    dailySummary.setNewChargeRate(chargePlayer * 1.0F / dailySummary.getCreatePlayer());
                    dailySummary.setNewArpu(amount / dailySummary.getCreatePlayer());
                }
            }
        }
        // 老用户
        {
            List<Map<String, Object>> oldChargeMaps = chargeOrderLogDao.dailyOldChargeStatis(lastDate.getTime(), today.getTime());
            for (Map<String, Object> row : oldChargeMaps) {
                int serverId = (int) row.get("server_id");
                String channelId = (String) row.get("channel_id");
                int chargePlayer = ((Number) row.get("chargePlayer")).intValue();
                int amount = ((Number) row.get("amount")).intValue();

                DailySummary dailySummary = getOrCreateDailySummary(entityMaps, dateTime, serverId, channelId);
                dailySummary.setOldChargePlayer(chargePlayer);
                dailySummary.setOldChargeAmount(amount);
                int oldLoginPlayer = dailySummary.getLoginPlayer() - dailySummary.getCreatePlayer();
                // 老用户付费率，老用户ARPU
                if (oldLoginPlayer != 0) {
                    dailySummary.setOldChargeRate(chargePlayer * 1.0F / oldLoginPlayer);
                    dailySummary.setOldArpu(amount / oldLoginPlayer);
                }
            }
        }

        dailySummaryRepository.saveAll(entityMaps.values());
    }

    /**
     *
     * @param entityMaps
     * @param dateTime
     * @param serverId
     * @param channelId
     * @return
     */
    private DailySummary getOrCreateDailySummary(Map<String, DailySummary> entityMaps, String dateTime, int serverId, String channelId) {
        if (channelId == null) {
            channelId = "";
        }

        String id = this.buildKey(dateTime, serverId, channelId);
        DailySummary dailySummary = entityMaps.get(id);
        if (dailySummary != null) {
            return dailySummary;
        } else {
            dailySummary = new DailySummary();
            dailySummary.setId(id);
            dailySummary.setDaily(Integer.parseInt(dateTime));
            dailySummary.setServerId(serverId);
            dailySummary.setChannelId(channelId);

            entityMaps.put(id, dailySummary);
        }

        return dailySummary;
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
