package com.xxgame.admin.web.modules.dailystatis.service;

import com.xxgame.admin.web.modules.commons.logdao.LoginLogDao;
import com.xxgame.admin.web.modules.commons.logdao.PlayerCreateLogDao;
import com.xxgame.admin.web.modules.dailystatis.controller.model.ActiveLoss;
import com.xxgame.admin.web.modules.dailystatis.controller.model.LoginLossStatisDto;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyChargeStatis;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyLoginLossStatis;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyRegistStatis;
import com.xxgame.admin.web.modules.dailystatis.repository.DailyChargeStatisRepository;
import com.xxgame.admin.web.modules.dailystatis.repository.DailyLoginLossStatisRepository;
import com.xxgame.admin.web.modules.dailystatis.repository.DailyRegistStatisRepository;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.repository.GameServerRepository;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 日常数据统计
 */
@Service
public class DailyStatisServiceImpl implements DailyStatisService {

    @Autowired
    private DailyChargeStatisRepository dailyChargeStatisRepository;
    @Autowired
    private DailyRegistStatisRepository dailyRegistStatisRepository;
    @Autowired
    private PlayerCreateLogDao playerCreateLogDao;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private GameServerRepository gameServerRepository;
    @Autowired
    private DailyLoginLossStatisRepository dailyLoginLossStatisRepository;

    @Override
    public Page<DailyChargeStatis> findChargeStatis(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyChargeStatisRepository.findChargeStatis(startTime, endTime, serverId, channelId, pageRequest);
    }

    @Override
    public Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyRegistStatisRepository.findRegistStatis(startTime, endTime, serverId, channelId, pageRequest);
    }

    @Override
    public Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyRegistStatisRepository.findRegistStatis(startTime, endTime, serverIds, channelIds, pageRequest);
    }

    @Override
    public Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, int serverId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyRegistStatisRepository.findRegistStatis(startTime, endTime, serverId, pageRequest);
    }

    @Override
    public Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, String channelId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyRegistStatisRepository.findRegistStatis(startTime, endTime, channelId, pageRequest);
    }

    @Override
    public Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyRegistStatisRepository.findRegistStatis(startTime, endTime, pageRequest);
    }

    @Override
    public Page<DailyLoginLossStatis> findLossStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyLoginLossStatisRepository.findLossStatis(startTime, endTime, serverIds, channelIds, pageRequest);
    }

    @Override
    public List<LoginLossStatisDto> loginRetain(long startTime, long endTime) {
        List<LoginLossStatisDto> list = new ArrayList<>();

        // 时间段开服列表
        List<GameServer> gameServers = gameServerRepository.findOpenServers(startTime, endTime);
        for (GameServer gameServer : gameServers) {
            int serverId = gameServer.getId();
            Date openDate = new Date(gameServer.getOpenTime());
            Date openNextDate = DateUtils.nextDayAm0(openDate);

            // 首天注册人数
            int registCount = playerCreateLogDao.registCount(serverId, openDate.getTime(), openNextDate.getTime());

            Date openNext30Date = DateUtils.add(30, true);
            // 30天内login人数
            List<Map<String, Object>> loginRowMaps = loginLogDao.loginCount(serverId, openDate.getTime(), openNext30Date.getTime());
            // { 渠道 : { 日期 : 人数 } }
            Map<String, TreeMap<Date, Integer>> channelDayCountMap = new HashMap<>();
            for (Map<String, Object> countRow : loginRowMaps) {
                String channelId = (String) countRow.get("channel_id");
                String loginDateStr = (String) countRow.get("loginDate");
                Date loginDate = DateUtils.string2Date(loginDateStr, DateUtils.PATTERN_YYYY_MM_DD);
                int loginCount = ((Number) countRow.get("count")).intValue();

                TreeMap<Date, Integer> dayCountMap = channelDayCountMap.get(channelId);
                if (dayCountMap == null) {
                    dayCountMap = new TreeMap<>();
                    channelDayCountMap.put(channelId, dayCountMap);
                }
                dayCountMap.put(loginDate, loginCount);
            }

            // 组装数据
            for (Map.Entry<String, TreeMap<Date, Integer>> channelEntry : channelDayCountMap.entrySet()) {
                LoginLossStatisDto dto = new LoginLossStatisDto();
                dto.setOpenTime(openDate.getTime());
                dto.setChannelId(channelEntry.getKey());
                dto.setServerId(serverId);
                dto.setFirstCreate(registCount);

                List<ActiveLoss> activeList = new ArrayList<>();
                dto.setActiveLoss(activeList);
                for (Map.Entry<Date, Integer> dayEntry : channelEntry.getValue().entrySet()) {
                    Date statisDate = DateUtils.nextDayAm0(dayEntry.getKey());
                    int days = DateUtils.calc2DateTDOADays(dayEntry.getKey(), openDate);
                    int totalCount = playerCreateLogDao.registCount(serverId, openDate.getTime(), statisDate.getTime() - 1);

                    ActiveLoss activeLoss = new ActiveLoss();
                    activeLoss.setDays(days);
                    activeLoss.setLoginCount(dayEntry.getValue());
                    activeLoss.setTotalCount(totalCount);
                    activeList.add(activeLoss);
                }

                list.add(dto);
            }
        }

        return list;
    }

}
