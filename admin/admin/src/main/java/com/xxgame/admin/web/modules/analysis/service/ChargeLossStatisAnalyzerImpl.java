package com.xxgame.admin.web.modules.analysis.service;

import com.xxgame.admin.web.modules.chargestatis.entity.ChargeLossStatis;
import com.xxgame.admin.web.modules.chargestatis.repository.ChargeLossStatisRepository;
import com.xxgame.admin.web.modules.commons.logdao.ChargeStatisDao;
import com.xxgame.admin.web.modules.commons.logdao.LoginLogDao;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 付费登录流失统计
 */
@Service
public class ChargeLossStatisAnalyzerImpl implements ChargeLossStatisAnalyzer {

    @Autowired
    private ChargeStatisDao chargeStatisDao;
    @Autowired
    private ChargeLossStatisRepository chargeLossStatisRepository;
    @Autowired
    private LoginLogDao loginLogDao;

    @Override
    public void analyzeLossStatis(Date date) {
        // 次日先找出昨天充值的人数，生成新记录
        newRecord(date);
        // 保存在昨天的记录中
        recordLostCount(date);
    }

    /**
     * 次日先找出昨天充值的人数，生成新记录
     * @param date
     */
    private void newRecord(Date date) {
        Date today0Am = DateUtils.dayAm0(date);
        Date last0Am = DateUtils.lastDayAm0(date);
        String daily = DateUtils.date2String(last0Am, DateUtils.PATTERN_YYYYMMDD);

        List<ChargeLossStatis> entitys = new LinkedList<>();
        List<Map<String, Object>> rowMaps = chargeStatisDao.chargeDailyStatis(last0Am.getTime(), today0Am.getTime());
        for (Map<String, Object> rowMap : rowMaps) {
            int serverId = ((Number) rowMap.get("server_id")).intValue();
            String channelId = (String) rowMap.get("channel_id");
            int count = ((Number) rowMap.get("count")).intValue();

            ChargeLossStatis entity = new ChargeLossStatis();
            entity.setId(daily + serverId + channelId);
            entity.setServerId(serverId);
            entity.setChannelId(channelId);
            entity.setDaily(Integer.parseInt(daily));
            entity.setChargeCount(count);

            entitys.add(entity);
        }

        chargeLossStatisRepository.saveAll(entitys);
    }

    /**
     * 次日找出前天的充值人A，统计今天有登录的人数B， 流失人数 = A - B，保存在昨天的记录中
     * @param date
     */
    // date = 3.13
    private void recordLostCount(Date date) {
        Date today0Am = DateUtils.dayAm0(date); // today0Am = 3.13
        Date yesterday = DateUtils.lastDayAm0(date); // yesterday = 3.12
        Date beforeYesterday = DateUtils.lastDayAm0(yesterday); // beforeYesterday = 3.11

        String yesterdayDailyStr = DateUtils.date2String(yesterday, DateUtils.PATTERN_YYYYMMDD);
        String beforeDailyStr = DateUtils.date2String(beforeYesterday, DateUtils.PATTERN_YYYYMMDD);
        int yesterdayDaily = Integer.parseInt(yesterdayDailyStr);
        int beforeDaily = Integer.parseInt(beforeDailyStr);

        // 前天的记录
        List<ChargeLossStatis> list = chargeLossStatisRepository.findByDaily(beforeDaily, yesterdayDaily);
        for (ChargeLossStatis lossStatis : list) {
            // 前天充值的玩家
            Set<Long> playerIds = new HashSet<Long>();
            List<Map<String, Object>> playerRowMaps =
                    chargeStatisDao.chargePlayers(lossStatis.getChannelId(), lossStatis.getServerId(), beforeYesterday.getTime(), yesterday.getTime());
            for (Map<String, Object> rowMap : playerRowMaps) {
                long playerId = ((Number) rowMap.get("player_id")).longValue();
                playerIds.add(playerId);
            }

            // 昨天登录人数
            int yesterLoginCount = 0;
            if (playerIds.size() > 0) {
                yesterLoginCount = loginLogDao.loginCount(lossStatis.getServerId(), lossStatis.getChannelId(), yesterday.getTime(), today0Am.getTime(), playerIds);
            }

            lossStatis.setLostCount(playerIds.size() - yesterLoginCount);
        }
        chargeLossStatisRepository.saveAll(list);
    }

}
