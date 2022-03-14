package com.xxgame.admin.web.modules.analysis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.commons.logdao.LoginLogDao;
import com.xxgame.admin.web.modules.commons.logdao.LogoutLogDao;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.repository.GameServerRepository;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyChannelOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.entity.OnlineStatisDist;
import com.xxgame.admin.web.modules.onlinestatis.repository.DailyChannelOnlineStatisRepository;
import com.xxgame.admin.web.modules.onlinestatis.repository.DailyOnlineStatisRepository;
import com.xxgame.admin.web.modules.onlinestatis.repository.OnlineStatisDistRepository;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 在线时长分布
 */
@Service
public class OnlineStatisAnalyzerImpl implements OnlineStatisAnalyzer {

    @Autowired
    private GameServerRepository gameServerRepository;
    @Autowired
    private LogoutLogDao logoutLogDao;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private OnlineStatisDistRepository onlineStatisDistRepository;
    @Autowired
    private DailyOnlineStatisRepository dailyOnlineStatisRepository;
    @Autowired
    private DailyChannelOnlineStatisRepository dailyChannelOnlineStatisRepository;

    /**
     * 一秒钟的毫秒数
     */
    static final int ONE_SECOND_MILLISECOND = 1000;

    /**
     * 一分钟的毫秒数
     */
    static final int ONE_MINUTE_MILLISECOND = 60 * ONE_SECOND_MILLISECOND;

    /**
     * 一小时的毫秒数
     */
    static final int ONE_HOUR_MILLISECOND = 60 * ONE_MINUTE_MILLISECOND;

    /**
     * 在线时长分布
     */
    @Override
    public void analyzeOnlineDistStatis(Date date) {
        Date today0Am = DateUtils.dayAm0(date);
        long lowTime = DateUtils.lastDayAm0(today0Am).getTime();
        String daily = DateUtils.date2String(DateUtils.lastDayAm0(date), DateUtils.PATTERN_YYYYMMDD);

        List<GameServer> gameServers =  gameServerRepository.findOpenServers(date.getTime());
        for (GameServer gameServer : gameServers) {
            int serverId = gameServer.getId();

            Map<Long, Long> playerOnlineMap = new HashMap<>();
            List<Map<String, Object>> rowMaps = logoutLogDao.queryOnlineTimeDist(today0Am, serverId);
            for (Map<String, Object> row : rowMaps) {
                long playerId = (long) row.get("player_id");
                long loginTime = (long) row.get("login_time");
                // 跨天清0
                loginTime = Math.max(lowTime, loginTime);
                long logoutTime = (long) row.get("time");

                long online = logoutTime - loginTime;
                // 累计时间
                long existTime = playerOnlineMap.getOrDefault(playerId, 0L);
                existTime += online;

                playerOnlineMap.put(playerId, existTime);
            }
            // 区间分布统计
            Map<String, Integer> statisMap = new HashMap<String, Integer>();
            for (long onlineTime : playerOnlineMap.values()) {
                String statisType = this.statisType(onlineTime);
                int count = statisMap.getOrDefault(statisType, 0);
                statisMap.put(statisType, count + 1);
            }

            OnlineStatisDist onlineStatisDist = new OnlineStatisDist();
            onlineStatisDist.setId(daily + serverId);
            onlineStatisDist.setServerId(serverId);
            onlineStatisDist.setDaily(Integer.parseInt(daily));
            onlineStatisDist.setTotal(playerOnlineMap.size());
            onlineStatisDist.setMinute5(statisMap.getOrDefault("minute5", 0));
            onlineStatisDist.setMinute15(statisMap.getOrDefault("minute15", 0));
            onlineStatisDist.setMinute30(statisMap.getOrDefault("minute30", 0));
            onlineStatisDist.setMinute60(statisMap.getOrDefault("minute60", 0));
            onlineStatisDist.setHour1(statisMap.getOrDefault("hour1", 0));
            onlineStatisDist.setHour3(statisMap.getOrDefault("hour3", 0));

            onlineStatisDistRepository.save(onlineStatisDist);
        }
    }

    @Override
    public void analyzeOnlineStatis(Date date) {
        // 每日在线统计
        dailyOnlineStatis(date);
        // 渠道每日在线统计
        dailyChannelOnlineStatis(date);
    }

    /**
     * 每日在线统计
     * @param date
     */
    private void dailyOnlineStatis(Date date) {
        Date endHour = DateUtils.changeDateTime(date,0, 0);
        Date startHour = new Date(endHour.getTime() - DateUtils.ONE_HOUR_MILLISECOND);
        String daily = DateUtils.date2String(startHour, DateUtils.PATTERN_YYYYMMDD);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startHour);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // 批量保存
        List<DailyOnlineStatis> entitys = new LinkedList<>();

        List<Map<String, Object>> rowMaps = loginLogDao.analyzeHourStatisLogin(startHour.getTime(), endHour.getTime());
        for (Map<String, Object> row : rowMaps) {
            int serverId = ((Number) row.get("server_id")).intValue();
            int count = ((Number) row.get("count")).intValue();

            String id = daily + serverId;
            Optional<DailyOnlineStatis> optional = dailyOnlineStatisRepository.findById(id);
            DailyOnlineStatis statis = null;
            if (!optional.isPresent()) {
                statis = new DailyOnlineStatis();
                statis.setId(id);
                statis.setServerId(serverId);
                statis.setDaily(Integer.parseInt(daily));

                List<Integer> counts = new ArrayList<>();
                IntStream.range(0, 24).forEach(i -> counts.add(0));
                statis.setStatisList(counts);
            } else {
                statis = optional.get();
                List<Integer> counts = JSON.parseObject(statis.getStatis(), new TypeReference<List<Integer>>(){});
                statis.setStatisList(counts);
            }
            // 设置在线人数
            statis.getStatisList().set(hour, count);
            statis.setStatis(JSON.toJSONString(statis.getStatisList()));

            entitys.add(statis);
        }

        dailyOnlineStatisRepository.saveAll(entitys);
    }

    /**
     * 渠道每日在线统计
     * @param date
     */
    private void dailyChannelOnlineStatis(Date date) {
        Date endHour = DateUtils.changeDateTime(date,0, 0);
        Date startHour = new Date(endHour.getTime() - DateUtils.ONE_HOUR_MILLISECOND);
        String daily = DateUtils.date2String(startHour, DateUtils.PATTERN_YYYYMMDD);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startHour);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // 批量保存
        List<DailyChannelOnlineStatis> entitys = new LinkedList<>();

        List<Map<String, Object>> rowMaps = loginLogDao.analyzeChannelHourStatisLogin(startHour.getTime(), endHour.getTime());
        for (Map<String, Object> row : rowMaps) {
            int serverId = ((Number) row.get("server_id")).intValue();
            int count = ((Number) row.get("count")).intValue();
            String channelId = (String) row.get("channel_id");

            String id = daily + serverId + channelId;
            Optional<DailyChannelOnlineStatis> optional = dailyChannelOnlineStatisRepository.findById(id);
            DailyChannelOnlineStatis statis = null;
            if (!optional.isPresent()) {
                statis = new DailyChannelOnlineStatis();
                statis.setId(id);
                statis.setServerId(serverId);
                statis.setChannelId(channelId);
                statis.setDaily(Integer.parseInt(daily));

                List<Integer> counts = new ArrayList<>();
                IntStream.range(0, 24).forEach(i -> counts.add(0));
                statis.setStatisList(counts);
            } else {
                statis = optional.get();
                List<Integer> counts = JSON.parseObject(statis.getStatis(), new TypeReference<List<Integer>>(){});
                statis.setStatisList(counts);
            }
            // 设置在线人数
            statis.getStatisList().set(hour, count);
            statis.setStatis(JSON.toJSONString(statis.getStatisList()));

            entitys.add(statis);
        }

        dailyChannelOnlineStatisRepository.saveAll(entitys);
    }

    /**
     * 统计指标
     * @param onlineTime
     * @return
     */
    private String statisType(long onlineTime) {
        if (onlineTime > ONE_HOUR_MILLISECOND * 3) {
            return "hour3";
        } else if (onlineTime > ONE_HOUR_MILLISECOND) {
            return "hour1";
        } else if (onlineTime > ONE_MINUTE_MILLISECOND * 30) {
            return "minute60";
        } else if (onlineTime > ONE_MINUTE_MILLISECOND * 15) {
            return "minute30";
        } else if (onlineTime > ONE_MINUTE_MILLISECOND * 5) {
            return "minute15";
        }

        return "minute5";
    }

}
