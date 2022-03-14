package com.xxgame.admin.web.modules.dailystatis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.dailystatis.controller.model.*;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyChargeStatis;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyLoginLossStatis;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyRegistStatis;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyChannelOnlineStatis;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Service
public class DailyStatisDtoConverter {

    /**
     * 转换成DailyChargeStatisDto列表
     * @param dailyChargeStatiss
     * @return
     */
    public List<DailyChargeStatisDto> toDailyChargeStatisDtos(List<DailyChargeStatis> dailyChargeStatiss) {
        List<DailyChargeStatisDto> list = new ArrayList<>(dailyChargeStatiss.size());
        for (DailyChargeStatis dailyChargeStatis : dailyChargeStatiss) {
            list.add(this.toDailyChargeStatisDto(dailyChargeStatis));
        }

        return list;
    }

    /**
     * 转换成DailyChargeStatisDto
     * @param dailyChargeStatis
     * @return
     */
    public DailyChargeStatisDto toDailyChargeStatisDto(DailyChargeStatis dailyChargeStatis) {
        DailyChargeStatisDto dto = new DailyChargeStatisDto();
        dto.setDateTime(dailyChargeStatis.getDaily());
        dto.setChannelId(dailyChargeStatis.getChannelId());
        dto.setServerId(dailyChargeStatis.getServerId());
        dto.setChargeCount(dailyChargeStatis.getChargeCount());
        dto.setChargePlayer(dailyChargeStatis.getChargePlayer());
        dto.setChargeAmout(dailyChargeStatis.getChargeAmout());
        dto.setTodyAmout(dailyChargeStatis.getTodyAmout());

        return dto;
    }

    /**
     * 转换成DailyRegistStatisDto列表
     * @param dailyRegistStatiss
     * @return
     */
    public List<DailyRegistStatisDto> toDailyRegistStatisDtos(List<DailyRegistStatis> dailyRegistStatiss) {
        List<DailyRegistStatisDto> list = new ArrayList<>();
        for (DailyRegistStatis dailyRegistStatis : dailyRegistStatiss) {
            list.add(this.toDailyRegistStatisDto(dailyRegistStatis));
        }

        return list;
    }

    /**
     * 转换成DailyRegistStatisDto
     * @param dailyRegistStatis
     * @return
     */
    public DailyRegistStatisDto toDailyRegistStatisDto(DailyRegistStatis dailyRegistStatis) {
        DailyRegistStatisDto dto = new DailyRegistStatisDto();
        dto.setDateTime(dailyRegistStatis.getDaily());
        dto.setChannelId(dailyRegistStatis.getChannelId());
        dto.setServerId(dailyRegistStatis.getServerId());
        dto.setCount(dailyRegistStatis.getRegistCount());

        return dto;
    }

    /**
     * 转换为DailyOnlineStatisDto列表
     * @param onlineStatiss
     * @return
     */
    public List<DailyOnlineStatisDto> toDailyOnlineStatisDtos(List<DailyChannelOnlineStatis> onlineStatiss) {
        if (onlineStatiss == null) {
            return new ArrayList<>(0);
        }

        List<DailyOnlineStatisDto> list = new ArrayList<>(onlineStatiss.size());
        for (DailyChannelOnlineStatis row : onlineStatiss) {
            DailyOnlineStatisDto dto = this.toDailyOnlineStatisDto(row);
            list.add(dto);
        }

        return list;
    }

    /**
     * 转换成DailyOnlineStatisDto
     * @param onlineStatis
     * @return
     */
    public DailyOnlineStatisDto toDailyOnlineStatisDto(DailyChannelOnlineStatis onlineStatis) {
        DailyOnlineStatisDto dto = new DailyOnlineStatisDto();
        dto.setServerId(onlineStatis.getServerId());
        dto.setChannelId(onlineStatis.getChannelId());
        dto.setDateTime(onlineStatis.getDaily());

        List<Integer> statis = JSON.parseObject(onlineStatis.getStatis(), new TypeReference<List<Integer>>(){});
        if (statis == null || statis.isEmpty()) {
            return dto;
        }
        int max = statis.stream().mapToInt(Integer::intValue).max().getAsInt();
        int min = statis.stream().mapToInt(Integer::intValue).min().getAsInt();
        double avg = statis.stream().mapToInt(Integer::intValue).average().getAsDouble();

        dto.setMaxCount(max);
        dto.setMinCount(min);
        dto.setAvgCount((float) avg);

        return dto;
    }

    /**
     * 转换成DailyRegistStatisDto列表
     * @param lossStatiss
     * @return
     */
    public List<LoginLossStatisV1Dto> toLoginLossStatisV1Dtos(List<DailyLoginLossStatis> lossStatiss) {
        List<LoginLossStatisV1Dto> list = new ArrayList<>();
        for (DailyLoginLossStatis lossStatis : lossStatiss) {
            list.add(this.toLoginLossStatisV1Dto(lossStatis));
        }

        return list;
    }

    /**
     * 转换成LoginLossStatisV1Dto
     * @param lossStatis
     * @return
     */
    public LoginLossStatisV1Dto toLoginLossStatisV1Dto(DailyLoginLossStatis lossStatis) {
        LoginLossStatisV1Dto dto = new LoginLossStatisV1Dto();
        dto.setDaily(lossStatis.getDaily());
        dto.setChannelId(lossStatis.getChannelId());
        dto.setServerId(lossStatis.getServerId());
        dto.setCreateCount(lossStatis.getCreateCount());

        List<ActiveLossV1> activeLoss = JSON.parseObject(lossStatis.getActiveLoss(), new TypeReference<List<ActiveLossV1>>(){});
        dto.setActiveLoss(activeLoss);

        return dto;
    }

}
