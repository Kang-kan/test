package com.xxgame.admin.web.modules.onlinestatis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.onlinestatis.controller.model.DailyRealOnlineStatisDto;
import com.xxgame.admin.web.modules.onlinestatis.controller.model.OnlineTimeDistDto;
import com.xxgame.admin.web.modules.onlinestatis.controller.model.RealTimeOnlineDto;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.entity.OnlineStatisDist;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
@Service
public class OnlinestatisDtoConverter {

    /**
     * 转换成RealTimeOnlineDto列表
     * @param rowMaps
     * @return
     */
    public List<RealTimeOnlineDto> toRealTimeOnlineDtos(List<Map<String, Object>> rowMaps) {
        TreeMap<Long, Integer> channelMap = new TreeMap<>();
        for (Map<String, Object> row : rowMaps) {
            long time = (long) row.get("time");
            int count = channelMap.getOrDefault(time, 0);
            channelMap.put(time, count + 1);
        }

        List<RealTimeOnlineDto> list = new ArrayList<>(channelMap.size());
        for (Map.Entry<Long, Integer> entry : channelMap.entrySet()) {
            RealTimeOnlineDto dto = new RealTimeOnlineDto();
            dto.setDateTime(entry.getKey());
            dto.setCount(entry.getValue());

            list.add(dto);
        }

        return list;
    }

    /**
     * 转换成OnlineTimeDistDto列表
     * @param onlineStatisDists
     * @return
     */
    public List<OnlineTimeDistDto> toOnlineTimeDistDtos(List<OnlineStatisDist> onlineStatisDists) {
        List<OnlineTimeDistDto> list = new ArrayList<>(onlineStatisDists.size());
        for (OnlineStatisDist onlineStatisDist : onlineStatisDists) {
            list.add(this.toOnlineTimeDistDto(onlineStatisDist));
        }

        return list;
    }

    /**
     * 转换为OnlineTimeDistDto
     * @param onlineStatisDist
     * @return
     */
    public OnlineTimeDistDto toOnlineTimeDistDto(OnlineStatisDist onlineStatisDist) {
        OnlineTimeDistDto dto = new OnlineTimeDistDto();
        dto.setDateTime(onlineStatisDist.getDaily());
        dto.setServerId(onlineStatisDist.getServerId());
        dto.setTotal(onlineStatisDist.getTotal());
        dto.setMinute5(onlineStatisDist.getMinute5());
        dto.setMinute15(onlineStatisDist.getMinute15());
        dto.setMinute30(onlineStatisDist.getMinute30());
        dto.setMinute60(onlineStatisDist.getMinute60());
        dto.setHour1(onlineStatisDist.getHour1());
        dto.setHour3(onlineStatisDist.getHour3());

        return dto;
    }

    /**
     * 转换成DailyRealOnlineStatisDto列表
     * @param onlineStatiss
     * @return
     */
    public List<DailyRealOnlineStatisDto> toDailyRealOnlineStatisDtos(List<DailyOnlineStatis> onlineStatiss) {
        List<DailyRealOnlineStatisDto> list = new ArrayList<>(onlineStatiss.size());
        for (DailyOnlineStatis onlineStatis : onlineStatiss) {
            list.add(this.toDailyRealOnlineStatisDto(onlineStatis));
        }

        return list;
    }

    /**
     * 转换成DailyRealOnlineStatisDto
     * @param onlineStatis
     * @return
     */
    public DailyRealOnlineStatisDto toDailyRealOnlineStatisDto(DailyOnlineStatis onlineStatis) {
        DailyRealOnlineStatisDto dto = new DailyRealOnlineStatisDto();
        dto.setServerId(onlineStatis.getServerId());
        dto.setDaily(onlineStatis.getDaily());
        dto.setStatis(JSON.parseObject(onlineStatis.getStatis(), new TypeReference<List<Integer>>(){}));

        return dto;
    }

}
