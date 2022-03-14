package com.xxgame.admin.web.modules.analysis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.commons.logdao.ChargeOrderLogDao;
import com.xxgame.admin.web.modules.commons.logdao.LoginLogDao;
import com.xxgame.admin.web.modules.commons.logdao.PlayerCreateLogDao;
import com.xxgame.admin.web.modules.dailystatis.controller.model.ActiveLossV1;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyLoginLossStatis;
import com.xxgame.admin.web.modules.dailystatis.repository.DailyLoginLossStatisRepository;
import com.xxgame.admin.web.modules.datasummary.entity.MonthlyActive;
import com.xxgame.admin.web.modules.datasummary.entity.SrvDailyCharge;
import com.xxgame.admin.web.modules.datasummary.repository.MonthlyActiveRepository;
import com.xxgame.admin.web.modules.datasummary.repository.SrvDailyChargeRepository;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 数据分析服务类
 */
@Service
public class DataSummaryAnalyzerImpl implements DataSummaryAnalyzer {

    @Autowired
    private PlayerCreateLogDao playerCreateLogDao;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private ChargeOrderLogDao chargeOrderLogDao;
    @Autowired
    private MonthlyActiveRepository monthlyActiveRepository;
    @Autowired
    private SrvDailyChargeRepository srvDailyChargeRepository;
    @Autowired
    private GameServerService gameServerService;
    @Autowired
    private DailyLoginLossStatisRepository dailyLoginLossStatisRepository;

    @Override
    public void analyzeMonthlyActive(Date date) {
        List<Map<String, Object>> createPlayers = playerCreateLogDao.analyzeMonthlyActive(date);
        List<Map<String, Object>> loginPlayers = loginLogDao.analyzeMonthlyActive(date);

        Date lastMonth = DateUtils.lastMonthAm0(date);
        String lastMonthStr = DateUtils.date2String(lastMonth, DateUtils.PATTERN_YYYYMM);

        Map<String, MonthlyActive> newRecordMap = new HashMap<>();

        // 新增用户数
        for (Map<String, Object> row : createPlayers) {
            int serverId = (int) row.get("server_id");
            String channelId = (String) row.get("channel_id");
            int count = ((Long) row.get("count")).intValue();

            // 加上上次的人数
            int monthPlayer = count + this.lastMonthPlayer(serverId, channelId);;

            MonthlyActive monthlyActive = new MonthlyActive();
            monthlyActive.setId(lastMonthStr + serverId + channelId);
            monthlyActive.setServerId(serverId);
            monthlyActive.setChannelId(channelId);
            monthlyActive.setMonth(Integer.parseInt(lastMonthStr));
            monthlyActive.setMonthPlayer(count);
            monthlyActive.setCreateTime(new Date());

            newRecordMap.put(monthlyActive.getId(), monthlyActive);
        }

        // 活跃用户数
        for (Map<String, Object> row : loginPlayers) {
            int serverId = (int) row.get("server_id");
            String channelId = (String) row.get("channel_id");
            int count = ((Long) row.get("count")).intValue();

            String id = lastMonthStr + serverId + channelId;
            MonthlyActive monthlyActive = newRecordMap.get(id);
            if (monthlyActive == null) {
                monthlyActive = new MonthlyActive();
                monthlyActive.setId(lastMonthStr + serverId + channelId);
                monthlyActive.setServerId(serverId);
                monthlyActive.setChannelId(channelId);
                monthlyActive.setMonth(Integer.parseInt(lastMonthStr));
                monthlyActive.setCreateTime(new Date());
            }
            monthlyActive.setMonthActive(count);
        }

        monthlyActiveRepository.saveAll(newRecordMap.values());
    }

    /**
     * 上个月用户数
     * @param serverId
     * @param channelId
     * @return
     */
    private int lastMonthPlayer(int serverId, String channelId) {
        List<MonthlyActive> list = monthlyActiveRepository.findByServerIdAndChannelId(serverId, channelId);
        if (list == null || list.isEmpty()) {
            return 0;
        }

        return list.stream().map(MonthlyActive::getMonth).max(Integer::compareTo).get();
    }

    @Override
    public void analyzeChannelDailyCharge(Date date) {
        String dateTime = DateUtils.date2String(DateUtils.lastDayAm0(date), DateUtils.PATTERN_YYYYMMDD);

        List<Map<String, Object>> rowMaps = chargeOrderLogDao.analyzeChannelDailyCharge(date);
        for (Map<String, Object> row : rowMaps) {
            int serverId = (int) row.get("server_id");
            String channelId = (String) row.get("channel_id");
            long rmb = ((BigDecimal) row.get("rmb")).longValue();

            SrvDailyCharge srvDailyCharge = new SrvDailyCharge();
            srvDailyCharge.setId(dateTime + serverId + channelId);
            srvDailyCharge.setServerId(serverId);
            srvDailyCharge.setChannelId(channelId);
            srvDailyCharge.setDaily(Integer.parseInt(dateTime));
            srvDailyCharge.setAmount(rmb);

            srvDailyChargeRepository.save(srvDailyCharge);
        }
    }

    @Override
    public void analyzeLoginLoss(Date date) {
        // 昨天的注册记录
        lastDayCreateRecord(date);
        recordBeforeData(date);
    }

    /**
     * 昨天的注册记录
     * @param date
     */
    private void lastDayCreateRecord(Date date) {
        String dateTime = DateUtils.date2String(DateUtils.lastDayAm0(date), DateUtils.PATTERN_YYYYMMDD);
        int daily = Integer.parseInt(dateTime);

        for (int serverId : gameServerService.getAllServerIds()) {
            List<DailyLoginLossStatis> entitys = new LinkedList<>();

            // 各渠道注册人数
            List<Map<String, Object>> rowMaps = playerCreateLogDao.analyzeDailyStatisRegist(date, serverId);
            for (Map<String, Object> row : rowMaps) {
                String channelId = (String) row.get("channel_id");
                int count = ((Number) row.get("count")).intValue();

                DailyLoginLossStatis lossStatis = this.initDailyLoginLossStatis();
                lossStatis.setId(dateTime + serverId + channelId);
                lossStatis.setServerId(serverId);
                lossStatis.setChannelId(channelId);
                lossStatis.setDaily(daily);
                lossStatis.setCreateCount(count);

                entitys.add(lossStatis);
            }
            dailyLoginLossStatisRepository.saveAll(entitys);
        }
    }

    /**
     * 初始化DailyLoginLossStatis
     * @return
     */
    private DailyLoginLossStatis initDailyLoginLossStatis() {
        DailyLoginLossStatis lossStatis = new DailyLoginLossStatis();

        List<ActiveLossV1> list = new LinkedList<>();
        for (int i = 2; i <= 15; i++) {
            ActiveLossV1 activeLoss = new ActiveLossV1();
            activeLoss.setDays(i);
            list.add(activeLoss);
        }

        int[] days = new int[]{ 30, 60, 90 };
        for (int day : days) {
            ActiveLossV1 activeLoss = new ActiveLossV1();
            activeLoss.setDays(day);
            list.add(activeLoss);
        }

        lossStatis.setActiveLoss(JSON.toJSONString(list));

        return lossStatis;
    }

    /**
     * 将登录人数设置到相应的记录上。[2-15,30,60,90]天的数据
     * @param date
     */
    private void recordBeforeData(Date date) {
        Date endDate = DateUtils.dayAm0(date);
        Date startDate = DateUtils.changeDateTime(endDate, -1, 0, 0, 0);

        List<Integer> statisDays = statisDays(startDate);
        for (int serverId : gameServerService.getAllServerIds()) {
            // 需要记录登录人数的记录（统计日期往后倒X天的）
            List<DailyLoginLossStatis> beforeDatas = dailyLoginLossStatisRepository.findLossStatis(statisDays, serverId);
            for (DailyLoginLossStatis lossStatis : beforeDatas) {
                // 注册后那些人在昨天登录人数
                Set<Long> playerIds = new HashSet<Long>();

                Date createDate = DateUtils.numberToDate(lossStatis.getDaily(), DateUtils.PATTERN_YYYYMMDD);
                List<Map<String, Object>> playerRowMaps = playerCreateLogDao.dailyCreatePlayerIds(createDate, serverId, lossStatis.getChannelId());
                for (Map<String, Object> rowMap : playerRowMaps) {
                    long playerId = ((Number) rowMap.get("player_id")).longValue();
                    playerIds.add(playerId);
                }
                if (playerIds.size() == 0) {
                    continue;
                }

                int count = loginLogDao.loginCount(serverId, lossStatis.getChannelId(), startDate.getTime(), endDate.getTime(), playerIds);
                this.setValue(lossStatis, startDate, count);
            }
            dailyLoginLossStatisRepository.saveAll(beforeDatas);
        }
    }

    /**
     * 设置登录人数
     * @param lossStatis
     * @param loginDate
     * @param loginCount
     */
    private void setValue(DailyLoginLossStatis lossStatis, Date loginDate, int loginCount) {
        Date registDate = DateUtils.numberToDate(lossStatis.getDaily(), DateUtils.PATTERN_YYYYMMDD);
        int interval = DateUtils.calc2DateTDOADays(loginDate, registDate);

        List<ActiveLossV1> list = JSON.parseObject(lossStatis.getActiveLoss(), new TypeReference<List<ActiveLossV1>>(){});
        for (ActiveLossV1 loss : list) {
            if (loss.getDays() == interval + 1) {
                loss.setLoginCount(loginCount);
                break;
            }
        }

        lossStatis.setActiveLoss(JSON.toJSONString(list));
    }

    /**
     * 需要记录登录人数的记录daily，注册后[2-15,30,60,90]天的数据
     * @param statisDate 统计时间
     * @return
     */
    private List<Integer> statisDays(Date statisDate) {
        List<Integer> days = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            Date date = DateUtils.changeDateTime(statisDate, i * -1, 0, 0, 0);
            String dateStr = DateUtils.date2String(date, DateUtils.PATTERN_YYYYMMDD);
            days.add(Integer.parseInt(dateStr));
        }
        int[] objs = new int[]{ 29, 59, 89 };
        for (int day : objs) {
            Date date = DateUtils.changeDateTime(statisDate, day * -1, 0, 0, 0);
            String dateStr = DateUtils.date2String(date, DateUtils.PATTERN_YYYYMMDD);
            days.add(Integer.parseInt(dateStr));
        }

        return days;
    }

}
