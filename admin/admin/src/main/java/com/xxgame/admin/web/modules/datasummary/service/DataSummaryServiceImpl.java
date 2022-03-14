package com.xxgame.admin.web.modules.datasummary.service;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.ServerType;
import com.xxgame.admin.web.modules.datasummary.controller.DataSummaryDtoConverter;
import com.xxgame.admin.web.modules.datasummary.controller.model.*;
import com.xxgame.admin.web.modules.datasummary.entity.DailySummary;
import com.xxgame.admin.web.modules.datasummary.entity.MonthlyActive;
import com.xxgame.admin.web.modules.datasummary.entity.SrvDailyCharge;
import com.xxgame.admin.web.modules.datasummary.repository.DailySummaryRepository;
import com.xxgame.admin.web.modules.datasummary.repository.MonthlyActiveRepository;
import com.xxgame.admin.web.modules.datasummary.repository.SrvDailyChargeRepository;
import com.xxgame.admin.web.modules.commons.logdao.*;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.repository.GameServerRepository;
import com.xxgame.admin.web.modules.onlinestatis.service.OnlineStatisService;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * DataSummaryService
 */
@Service
public class DataSummaryServiceImpl implements DataSummaryService {

    @Autowired
    private MonthlyActiveRepository monthlyActiveRepository;
    @Autowired
    private SrvDailyChargeRepository srvDailyChargeRepository;
    @Autowired
    private GameServerRepository gameServerRepository;
    @Autowired
    private GameServerService gameServerService;
    @Autowired
    private OnlineStatisService onlineStatisService;
    @Autowired
    private PlayerCreateLogDao playerCreateLogDao;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private ChargeOrderLogDao chargeOrderLogDao;
    @Autowired
    private GoldLogDao goldLogDao;
    @Autowired
    private DailySummaryRepository dailySummaryRepository;
    @Autowired
    private DataSummaryDtoConverter dataSummaryDtoConverter;

    @Override
    public Page<MonthlyActive> findMonthlyActive(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return monthlyActiveRepository.findMonthlyActive(startTime, endTime, serverId, channelId, pageRequest);
    }

    @Override
    public Page<MonthlyActive> findMonthlyActive(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return monthlyActiveRepository.findMonthlyActive(startTime, endTime, serverIds, channelIds, pageRequest);
    }

    @Override
    public PageDto<SrvListChargeDto> findServerCharge(int startTime, int pageNo, int pageSize) {
        // 获取服务器列表
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<GameServer> srvPage = gameServerRepository.findOpenServers(System.currentTimeMillis(), pageRequest);

        List<SrvListChargeDto> listChargeDtos = new ArrayList<>(srvPage.getContent().size());
        // 组装各服数据
        for (GameServer gameServer : srvPage.getContent()) {
            int serverId = gameServer.getId();
            List<SrvDailyCharge> dailyCharges = srvDailyChargeRepository.findDailyCharge(startTime, startTime + 10, serverId);

            Map<Integer, SrvChargeDto> sumByDate = new LinkedHashMap<>();
            for (SrvDailyCharge srvDailyCharge : dailyCharges) {
                SrvChargeDto dto = sumByDate.get(srvDailyCharge.getDaily());
                // 加上原来的
                if (dto != null) {
                    dto.setPrice(dto.getPrice() + srvDailyCharge.getAmount());
                } else {
                    dto = dataSummaryDtoConverter.toSrvChargeDto(srvDailyCharge);
                    sumByDate.put(dto.getDateTime(), dto);
                }
            }

            SrvListChargeDto dto = new SrvListChargeDto();
            dto.setServerId(serverId);
            dto.setSrvChargeDtos(new ArrayList<>(sumByDate.values()));

            listChargeDtos.add(dto);
        }

        PageDto<SrvListChargeDto> pageDto = new PageDto<>();
        pageDto.setPageNo(pageNo);
        pageDto.setPageSize(pageSize);
        pageDto.setTotalPage(srvPage.getTotalPages());
        pageDto.setTotalCount(srvPage.getTotalElements());
        pageDto.setContents(listChargeDtos);

        return pageDto;
    }

    @Override
    public PageDto<SrvListChargeDto> findChannelServerCharge(int startTime, String channelId, int pageNo, int pageSize) {
        // 获取服务器列表
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<GameServer> srvPage = gameServerRepository.findOpenServers(System.currentTimeMillis(), pageRequest);

        List<SrvListChargeDto> listChargeDtos = new ArrayList<>(srvPage.getContent().size());
        // 组装各服数据
        for (GameServer gameServer : srvPage.getContent()) {
            int serverId = gameServer.getId();
            List<SrvDailyCharge> dailyCharges = srvDailyChargeRepository.findDailyCharge(startTime, startTime + 10, serverId, channelId);

            List<SrvChargeDto> srvChargeDtos = new ArrayList<>(dailyCharges.size());
            for (SrvDailyCharge srvDailyCharge : dailyCharges) {
                SrvChargeDto dto = dataSummaryDtoConverter.toSrvChargeDto(srvDailyCharge);
                dto.setChannelId(channelId);
                srvChargeDtos.add(dto);
            }

            SrvListChargeDto dto = new SrvListChargeDto();
            dto.setServerId(serverId);
            dto.setSrvChargeDtos(srvChargeDtos);

            listChargeDtos.add(dto);
        }

        PageDto<SrvListChargeDto> pageDto = new PageDto<>();
        pageDto.setPageNo(pageNo);
        pageDto.setPageSize(pageSize);
        pageDto.setTotalPage(srvPage.getTotalPages());
        pageDto.setTotalCount(srvPage.getTotalElements());
        pageDto.setContents(listChargeDtos);

        return pageDto;
    }

    @Override
    public List<SrvChargeDto> findChannelServerCharge(int startTime, int endTime, int serverId, String channelId) {
        List<SrvDailyCharge> dailyCharges = srvDailyChargeRepository.findDailyCharge(startTime, endTime, serverId, channelId);

        List<SrvChargeDto> list = new ArrayList<>(dailyCharges.size());
        for (SrvDailyCharge srvDailyCharge : dailyCharges) {
            SrvChargeDto dto = dataSummaryDtoConverter.toSrvChargeDto(srvDailyCharge);
            list.add(dto);
        }

        return list;
    }

    @Override
    public PageDto<SingleSrvSimpleDto> singleSrvSimple(int startTime, int endTime, int pageNo, int pageSize) {
        // 获取服务器列表
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<GameServer> srvPage = gameServerRepository.findOpenServersByType(System.currentTimeMillis(), ServerType.OFFICIAL.getValue(), pageRequest);

        List<SingleSrvSimpleDto> listChargeDtos = new ArrayList<>(srvPage.getContent().size());
        // 组装各服数据
        for (GameServer gameServer : srvPage.getContent()) {
            int serverId = gameServer.getId();
            long openTime = gameServer.getOpenTime();
            String openDaily = DateUtils.date2String(new Date(openTime), DateUtils.PATTERN_YYYYMMDD);

            for (int daily = startTime; daily <= endTime; daily++) {
                if (daily < Integer.parseInt(openDaily)) {
                    continue;
                }

                List<SrvDailyCharge> dailyCharges = srvDailyChargeRepository.findDailyCharge(daily, serverId);
                int dailyCharge = 0;
                for (SrvDailyCharge srvDailyCharge : dailyCharges) {
                    dailyCharge += srvDailyCharge.getAmount();
                }

                Date startDate = DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD);
                Date endDate = new Date(openTime);
                int openDay = DateUtils.calc2DateTDOADays(startDate, endDate);

                SingleSrvSimpleDto dto = new SingleSrvSimpleDto();
                dto.setDaily(daily);
                dto.setServerId(serverId);
                dto.setAmount(dailyCharge);
                dto.setOpenDay(openDay + 1);

                int maxOnline = onlineStatisService.getMaxOnline(daily, serverId);
                dto.setMaxOnline(maxOnline);

                listChargeDtos.add(dto);
            }
        }

        PageDto<SingleSrvSimpleDto> result = new PageDto();
        result.setContents(listChargeDtos);
        result.setPageSize(pageNo);
        result.setPageNo(pageSize);
        result.setTotalPage(srvPage.getTotalPages());
        result.setTotalCount(srvPage.getTotalElements());

        return result;
    }

    @Override
    public PageDto<SingleSrvSimpleDto> singleSrvSimple(int startTime, int endTime, List<Integer> serverIds, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Object[]> pages = srvDailyChargeRepository.findDailyCharge(startTime, endTime, serverIds, pageRequest);

        List<SingleSrvSimpleDto> list = new LinkedList<>();
        for (Object[] objs : pages.getContent()) {
            SingleSrvSimpleDto dto = new SingleSrvSimpleDto();
            dto.setAmount(((Number) objs[0]).intValue());
            dto.setDaily(((Number) objs[1]).intValue());
            dto.setServerId(((Number) objs[2]).intValue());

            int maxOnline = onlineStatisService.getMaxOnline(dto.getDaily(), dto.getServerId());
            dto.setMaxOnline(maxOnline);

            GameServer gameServer = gameServerService.getCacheGameServer(dto.getServerId());
            Date startDate = DateUtils.numberToDate(dto.getDaily(), DateUtils.PATTERN_YYYYMMDD);
            Date endDate = new Date(gameServer.getOpenTime());
            int openDay = DateUtils.calc2DateTDOADays(startDate, endDate);
            dto.setOpenDay(openDay + 1);

            list.add(dto);
        }

        PageDto<SingleSrvSimpleDto> result = new PageDto();
        result.setContents(list);
        result.setPageSize(pageNo);
        result.setPageNo(pageSize);
        result.setTotalPage(pages.getTotalPages());
        result.setTotalCount(pages.getTotalElements());

        return result;
    }

    @Override
    public TodaySummaryDto todaySummary(String channelId, int serverId) {
        long now = System.currentTimeMillis();
        Date dayAm0 = DateUtils.dayAm0(new Date(now));

        // 过去一小时内的
        int onlineCount = loginLogDao.loginCount(serverId, channelId, now - DateUtils.ONE_HOUR_MILLISECOND, now);
        // 当前注册人数
        int registCount = playerCreateLogDao.registCount(serverId, channelId, dayAm0.getTime(), now);
        int todayLoginCount = loginLogDao.loginCount(serverId, channelId, dayAm0.getTime(), now);
        int pastLoginCount = loginLogDao.loginCount(serverId, channelId, 0, dayAm0.getTime());
        int pastRegistCount = playerCreateLogDao.registCount(serverId, channelId, 0, dayAm0.getTime());
        int firstCharge = chargeOrderLogDao.firstChargeCount(serverId, channelId, dayAm0.getTime(), now);

        TodaySummaryDto dto = new TodaySummaryDto();
        dto.setChannelId(channelId);
        dto.setServerId(serverId);
        dto.setOnlineCount(onlineCount);
        dto.setCreateAccount(registCount);
        dto.setCreatePlayer(registCount);
        dto.setPastCreatePlayer(pastRegistCount);

        {
            // 当日充值
            Map<String, Object> todayChargeStatis = chargeOrderLogDao.getChargeStatis(serverId, channelId, dayAm0.getTime(), now);
            Number chargeCount = ((Number) todayChargeStatis.get("chargeCount"));
            Number chargePlayer = ((Number) todayChargeStatis.get("chargePlayer"));
            Number amount = ((Number) todayChargeStatis.get("amount"));

            dto.setTodayChargeCount(chargeCount == null ? 0 : chargeCount.intValue());
            dto.setTodayChargePlayer(chargePlayer == null ? 0 : chargePlayer.intValue());
            dto.setTodayChargeAmount(amount == null ? 0 : amount.intValue());
            dto.setTodayFirstCharge(firstCharge);
            if (todayLoginCount != 0) {
                dto.setTodayArpu(dto.getTodayChargeAmount() / todayLoginCount);
            }
        }
        {
            // 历史充值
            Map<String, Object> todayChargeStatis = chargeOrderLogDao.getChargeStatis(serverId, channelId, 0, dayAm0.getTime());
            Number chargeCount = ((Number) todayChargeStatis.get("chargeCount"));
            Number chargePlayer = ((Number) todayChargeStatis.get("chargePlayer"));
            Number amount = ((Number) todayChargeStatis.get("amount"));

            dto.setPastChargeCount(chargeCount == null ? 0 : chargeCount.intValue());
            dto.setPastChargePlayer(chargePlayer == null ? 0 : chargePlayer.intValue());
            dto.setPastChargeAmount(amount == null ? 0 : amount.intValue());
            if (pastLoginCount != 0) {
                dto.setPastArpu(dto.getPastChargeAmount() / pastLoginCount);
            }
        }

        return dto;
    }

    @Override
    public List<HourSummaryDto> hourSummarys(String channelId, int serverId, int dateTime) {
        Date dayAm0 = DateUtils.numberToDate(dateTime, DateUtils.PATTERN_YYYYMMDD);
        Date nextDayAm0 = DateUtils.nextDayAm0(dayAm0);
        long now = System.currentTimeMillis();

        List<HourSummaryDto> list = new ArrayList<>();
        for (long startTime = dayAm0.getTime(); startTime <= nextDayAm0.getTime(); startTime += DateUtils.ONE_HOUR_MILLISECOND) {
            if (startTime >= now) {
                break;
            }
            HourSummaryDto dto = this.buildHourSummaryDto(channelId, serverId, startTime, startTime + DateUtils.ONE_HOUR_MILLISECOND);
            list.add(dto);
        }

        return list;
    }

    /**
     * 构建HourSummaryDto
     * @param channelId
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    private HourSummaryDto buildHourSummaryDto(String channelId, int serverId, long startTime, long endTime) {
        String dateHour = DateUtils.date2String(new Date(startTime), DateUtils.PATTERN_YYYYMMDDHH);
        // 当前注册人数
        int registCount = playerCreateLogDao.registCount(serverId, channelId, startTime, endTime);
        int loginCount = loginLogDao.loginCount(serverId, channelId, startTime, endTime);

        // 充值状态
        Map<String, Object> chargeStatis = chargeOrderLogDao.getChargeStatis(serverId, channelId, startTime, endTime);
        int firstCharge = chargeOrderLogDao.firstChargeCount(serverId, channelId, startTime, endTime);
        int consumeAmount = goldLogDao.consumeAmount(serverId, channelId, startTime, endTime);

        HourSummaryDto dto = new HourSummaryDto();
        dto.setServerId(serverId);
        dto.setChannelId(channelId);
        dto.setDateTime(Integer.parseInt(dateHour));
        dto.setCreateAccount(registCount);
        dto.setCreatePlayer(registCount);
        dto.setLoginCount(loginCount);

        Number chargeCount = ((Number) chargeStatis.get("chargeCount"));
        Number chargePlayer = ((Number) chargeStatis.get("chargePlayer"));
        Number amount = ((Number) chargeStatis.get("amount"));
        Number chargeGold = ((Number) chargeStatis.get("chargeGold"));
        dto.setChargeCount(chargeCount == null ? 0 : chargeCount.intValue());
        dto.setChargePlayer(chargePlayer == null ? 0 : chargePlayer.intValue());
        dto.setAmount(amount == null ? 0 : amount.intValue());
        dto.setChargeGold(chargeGold == null ? 0 : chargeGold.intValue());

        dto.setFirstCharge(firstCharge);
        dto.setConsumeGold(consumeAmount);

        return dto;
    }

    @Override
    public Page<DailySummary> findDailySummary(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return dailySummaryRepository.findDailySummary(startTime, endTime, serverId, channelId, pageRequest);
    }

    @Override
    public Page<DailySummary> findDailySummary(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return dailySummaryRepository.findDailySummary(startTime, endTime, serverIds, channelIds, pageRequest);
    }

}
