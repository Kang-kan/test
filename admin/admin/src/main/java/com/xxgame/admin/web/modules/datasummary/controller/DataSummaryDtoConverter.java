package com.xxgame.admin.web.modules.datasummary.controller;

import com.xxgame.admin.web.modules.datasummary.controller.model.*;
import com.xxgame.admin.web.modules.datasummary.entity.DailySummary;
import com.xxgame.admin.web.modules.datasummary.entity.MonthlyActive;
import com.xxgame.admin.web.modules.datasummary.entity.SrvDailyCharge;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * DtoConverter
 */
@Service
public class DataSummaryDtoConverter {

    /**
     * 转换成MonthlyActiveDto
     * @param monthlyActive
     * @return
     */
    public MonthlyActiveDto toMonthlyActiveDto(MonthlyActive monthlyActive) {
        MonthlyActiveDto dto = new MonthlyActiveDto();
        dto.setServerId(monthlyActive.getServerId());
        dto.setChannelId(monthlyActive.getChannelId());
        dto.setDateTime(monthlyActive.getMonth());
        dto.setTotalPlayer(monthlyActive.getMonthPlayer());
        dto.setActivePlayer(monthlyActive.getMonthActive());

        return dto;
    }

    /**
     * 转换成MonthlyActiveDto列表
     * @param monthlyActives
     * @return
     */
    public List<MonthlyActiveDto> toMonthlyActiveDtos(List<MonthlyActive> monthlyActives) {
        List<MonthlyActiveDto> list = new ArrayList<>(monthlyActives.size());
        for (MonthlyActive monthlyActive : monthlyActives) {
            list.add(this.toMonthlyActiveDto(monthlyActive));
        }

        return list;
    }

    /**
     * 转换成SrvChargeDto
     * @param channelDailyCharge
     * @return
     */
    public SrvChargeDto toSrvChargeDto(SrvDailyCharge channelDailyCharge) {
        SrvChargeDto dto = new SrvChargeDto();
        dto.setServerId(channelDailyCharge.getServerId());
        dto.setChannelId(channelDailyCharge.getChannelId());
        dto.setDateTime(channelDailyCharge.getDaily());
        dto.setPrice(channelDailyCharge.getAmount());

        return dto;
    }

    /**
     * 转换成DailySummaryDto列表
     * @param dailySummarys
     * @return
     */
    public List<DailySummaryDto> toDailySummaryDtos(List<DailySummary> dailySummarys) {
        if (dailySummarys == null) {
            return new ArrayList<>(0);
        }

        List<DailySummaryDto> dtos = new ArrayList<>(dailySummarys.size());
        for (DailySummary dailySummary : dailySummarys) {
            DailySummaryDto dto = this.toDailySummaryDto(dailySummary);
            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * 转换成DailySummaryDto
     * @param dailySummary
     * @return
     */
    public DailySummaryDto toDailySummaryDto(DailySummary dailySummary) {
        DailySummaryDto dto = new DailySummaryDto();
        dto.setServerId(dailySummary.getServerId());
        dto.setChannelId(dailySummary.getChannelId());
        dto.setDaily(dailySummary.getDaily());
        dto.setCreatePlayer(dailySummary.getCreatePlayer());
        dto.setLoginPlayer(dailySummary.getLoginPlayer());
        dto.setMaxOnline(dailySummary.getMaxOnline());
        dto.setMinOnline(dailySummary.getMinOnline());
        dto.setAvgOnline(dailySummary.getAvgOnline());
        dto.setChargeCount(dailySummary.getChargeCount());
        dto.setChargePlayer(dailySummary.getChargePlayer());
        dto.setChargeAmount(dailySummary.getChargeAmount());
        dto.setArpu(dailySummary.getArpu());
        dto.setArppu(dailySummary.getArppu());
        dto.setChargeRate(dailySummary.getChargeRate());
        dto.setNewChargeClickCount(dailySummary.getNewChargeClickCount());
        dto.setNewChargeClickPlayer(dailySummary.getNewChargeClickPlayer());
        dto.setNewChargeClickRate(dailySummary.getNewChargeClickRate());
        dto.setNewChargePlayer(dailySummary.getNewChargePlayer());
        dto.setNewChargeAmount(dailySummary.getNewChargeAmount());
        dto.setNewChargeRate(dailySummary.getNewChargeRate());
        dto.setNewArpu(dailySummary.getNewArpu());
        dto.setOldChargePlayer(dailySummary.getOldChargePlayer());
        dto.setOldChargeAmount(dailySummary.getOldChargeAmount());
        dto.setOldChargeRate(dailySummary.getOldChargeRate());
        dto.setOldArpu(dailySummary.getOldArpu());

        return dto;
    }

    /**
     * 转换成CreateRoleUiCountDto列表
     * @param rowMaps
     * @return
     */
    public Collection<CreateRoleUiCountDto> toCreateRoleUiCountDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        LinkedHashMap<String, CreateRoleUiCountDto> map = new LinkedHashMap<>();
        for (Map<String, Object> rowMap : rowMaps) {
            int serverId = (Integer) rowMap.get("server_id");
            String channelId = (String) rowMap.get("channel_id");
            int type = ((Number) rowMap.get("type")).intValue();
            int count = ((Number) rowMap.get("count")).intValue();

            String key = String.format("%s:%s", serverId, channelId);
            CreateRoleUiCountDto dto = map.get(key);
            if (dto == null) {
                dto = new CreateRoleUiCountDto();
                dto.setServerId(serverId);
                dto.setChannelId(channelId);
                map.put(key, dto);
            }
            if (type == 0) {
                dto.setCount(count);
            } else if (type == 1) {
                dto.setLoadingCount(count);
            }
        }

        return map.values();
    }

    /**
     * 转换成ExpCopyDto列表
     * @param rowMaps
     * @return
     */
    public List<ExpCopyDto> toExpCopyDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<ExpCopyDto> dtos = new LinkedList<>();
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toExpCopyDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成ExpCopyDto
     * @param rowMap
     * @return
     */
    public ExpCopyDto toExpCopyDto(Map<String, Object> rowMap) {
        ExpCopyDto dto = new ExpCopyDto();
        dto.setServerId((Integer) rowMap.get("server_id"));
        dto.setChannelId((String) rowMap.get("channel_id"));
        dto.setMultiple((Integer) rowMap.get("multiple"));
        dto.setTimes(((Number) rowMap.get("times")).intValue());
        dto.setCount(((Number) rowMap.get("count")).intValue());

        return dto;
    }

}
