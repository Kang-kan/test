package com.xxgame.admin.web.modules.analysis.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.xxgame.admin.web.modules.commons.logdao.LoginLogDao;
import com.xxgame.admin.web.modules.commons.logdao.LogoutLogDao;
import com.xxgame.admin.web.modules.commons.logdao.PlayerCreateLogDao;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.levelstatis.entity.DailyLevelLossStatis;
import com.xxgame.admin.web.modules.levelstatis.repository.DailyLevelLossStatisRepository;
import com.xxgame.admin.web.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 日常等级统计
 */
@Service
public class DailyLevelStatisAnalyzerImpl implements DailyLevelStatisAnalyzer {

    @Autowired
    private PlayerCreateLogDao playerCreateLogDao;
    @Autowired
    private LogoutLogDao logoutLogDao;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private DailyLevelLossStatisRepository dailyLevelLossStatisRepository;
    @Autowired
    private GameServerService gameServerService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void analyzeLossStatis(Date date) {
        // 次日先找出昨天的新用户等级分布，生成新记录
        newRecord(date);
        recordLost24Count(date);
        recordLost72Count(date);
    }

    /**
     * 次日先找出昨天的新用户的人，统计每个等级有多少人，生成新记录。
     * @param date
     */
    private void newRecord(Date date) {
        Date last0Am = DateUtils.lastDayAm0(date);
        Date today0Am = DateUtils.dayAm0(date);
        String daily = DateUtils.date2String(last0Am, DateUtils.PATTERN_YYYYMMDD);

        List<Integer> serverIds = gameServerService.getAllOpenedServerIds();
        for (int serverId : serverIds) {
            // 总注册人数
            int registCount = playerCreateLogDao.registCount(serverId, last0Am.getTime(), today0Am.getTime());

            List<DailyLevelLossStatis> entitys = new LinkedList<>();
            Map<Integer, Integer> firstDayLevelStatis = this.firstDayLevelStatis(serverId, last0Am.getTime(), today0Am.getTime());
            for (Map.Entry<Integer, Integer> entry : firstDayLevelStatis.entrySet()) {
                int level = entry.getKey();
                int levelCount = entry.getValue();

                DailyLevelLossStatis entity = new DailyLevelLossStatis();
                entity.setId(daily + serverId + level);
                entity.setServerId(serverId);
                entity.setDaily(Integer.parseInt(daily));
                entity.setCount(registCount);
                entity.setLevel(level);
                entity.setLevelCount(levelCount);

                entitys.add(entity);
            }

            dailyLevelLossStatisRepository.saveAll(entitys);
        }
    }

    /**
     * 首天等级分布
     * @param serverId
     * @param startTime
     * @param endTime
     * @return { level : count }
     */
    private Map<Integer, Integer> firstDayLevelStatis(int serverId, long startTime, long endTime) {
        Map<Integer, Integer> resultMap = new TreeMap<>();

        List<Map<String, Object>> rowMaps = logoutLogDao.firstDayLevelStatis(serverId, startTime, endTime);
        for (Map<String, Object> rowMap : rowMaps) {
            int level = ((Number) rowMap.get("level")).intValue();
            int count = resultMap.getOrDefault(level, 0);
            resultMap.put(level, count + 1);
        }

        return resultMap;
    }

    /**
     * 记录流失人数
     * @param date
     */
    // date = 3.13
    private void recordLost24Count(Date date) {
        Date today0Am = DateUtils.dayAm0(date); // today = 3.13
        Date yesterday = DateUtils.lastDayAm0(date); // yesterday = 3.12
        Date bfYesterDay = DateUtils.lastDayAm0(yesterday);
        String byDaily = DateUtils.date2String(bfYesterDay, DateUtils.PATTERN_YYYYMMDD);

        // 1. 先找出只是昨天登录的人，统计每个等级有多少人，记录前天的流失人数
        List<Integer> serverIds = gameServerService.getAllOpenedServerIds();
        for (int serverId : serverIds) {
            // { level : [playerId] }
            Multimap<Integer, Long> firstDayPlayerStatis = this.firstDayPlayerStatis(serverId, bfYesterDay.getTime(), yesterday.getTime());

            List<DailyLevelLossStatis> list = dailyLevelLossStatisRepository.findDailyLoss(Integer.parseInt(byDaily), serverId);
            for (DailyLevelLossStatis lossStatis : list) {
                int loginCount = 0;
                Collection<Long> playerIds = firstDayPlayerStatis.get(lossStatis.getLevel());
                if (!playerIds.isEmpty()) {
                    loginCount = loginLogDao.loginCount(serverId, yesterday.getTime(), today0Am.getTime(), playerIds);
                }
                int lossCount = lossStatis.getLevelCount() - loginCount;
                lossStatis.setLoss24(lossCount);
            }
            dailyLevelLossStatisRepository.saveAll(list);
        }
    }

    /**
     * 首天等级玩家id
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    private Multimap<Integer, Long> firstDayPlayerStatis(int serverId, long startTime, long endTime) {
        Multimap<Integer, Long> resultMap = HashMultimap.create();

        List<Map<String, Object>> rowMaps = logoutLogDao.firstDayLevelStatis(serverId, startTime, endTime);
        for (Map<String, Object> rowMap : rowMaps) {
            int level = ((Number) rowMap.get("level")).intValue();
            long playerId = ((Number) rowMap.get("player_id")).longValue();
            resultMap.put(level, playerId);
        }

        return resultMap;
    }

    /**
     * 记录流失人数
     * @param date
     */
    // date = 2.19
    private void recordLost72Count(Date date) {
        Date today0Am = DateUtils.dayAm0(date); // today = 2.19
        Date loss72EndDday = DateUtils.changeDateTime(date, -3, 0, 0, 0); // yesterday = 2.16
        Date loss72StartDday = DateUtils.lastDayAm0(loss72EndDday);
        String byDaily = DateUtils.date2String(loss72StartDday, DateUtils.PATTERN_YYYYMMDD);

        // 1. 先找出只是昨天登录的人，统计每个等级有多少人，记录前天的流失人数
        List<Integer> serverIds = gameServerService.getAllOpenedServerIds();
        for (int serverId : serverIds) {
            // { level : [playerId] }
            Multimap<Integer, Long> firstDayPlayerStatis = this.firstDayPlayerStatis(serverId, loss72StartDday.getTime(), loss72EndDday.getTime());

            List<DailyLevelLossStatis> list = dailyLevelLossStatisRepository.findDailyLoss(Integer.parseInt(byDaily), serverId);
            for (DailyLevelLossStatis lossStatis : list) {
                int loginCount = 0;
                Collection<Long> playerIds = firstDayPlayerStatis.get(lossStatis.getLevel());
                if (!playerIds.isEmpty()) {
                    loginCount = loginLogDao.loginCount(serverId, loss72EndDday.getTime(), today0Am.getTime(), playerIds);
                }
                int lossCount = lossStatis.getLevelCount() - loginCount;
                lossStatis.setLoss72(lossCount);
            }
            dailyLevelLossStatisRepository.saveAll(list);
        }
    }

}
