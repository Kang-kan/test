package com.xxgame.admin.web.modules.consumestatis.controller;

import com.xxgame.admin.web.modules.consumestatis.controller.model.GoldConsumeStatisDto;
import com.xxgame.admin.web.modules.consumestatis.controller.model.MarketConsumeStatisDto;
import com.xxgame.admin.web.modules.consumestatis.entity.GoldConsumeStatis;
import com.xxgame.admin.web.modules.consumestatis.entity.MarketStatis;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class ConsumeStatisDtoConverter {

    /**
     * 转换MarketConsumeStatisDto
     * @param marketStatiss
     * @return
     */
    public List<MarketConsumeStatisDto> toMarketConsumeStatisDtos(List<MarketStatis> marketStatiss) {
        List<MarketConsumeStatisDto> list = new ArrayList<>(marketStatiss.size());
        for (MarketStatis marketStatis : marketStatiss) {
            list.add(this.toMarketConsumeStatisDto(marketStatis));
        }

        return list;
    }

    /**
     * 转换MarketConsumeStatisDto
     * @param marketStatis
     * @return
     */
    public MarketConsumeStatisDto toMarketConsumeStatisDto(MarketStatis marketStatis) {
        MarketConsumeStatisDto dto = new MarketConsumeStatisDto();
        dto.setDateTime(marketStatis.getDaily());
        dto.setServerId(marketStatis.getServerId());
        dto.setTotalPlayerCount(marketStatis.getTotalPlayerCount());
        dto.setTotalTimes(marketStatis.getTotalTimes());
        dto.setTotalAmount(marketStatis.getTotalAmount());
        dto.setItemId(marketStatis.getItemId());
        dto.setItemCount(marketStatis.getItemCount());
        dto.setItemName(marketStatis.getItemName());
        dto.setPlayerCount(marketStatis.getPlayerCount());
        dto.setTimes(marketStatis.getTimes());
        dto.setCurrency(marketStatis.getCurrency());
        dto.setAmount(marketStatis.getAmount());

        return dto;
    }

    /**
     * 转换MarketConsumeStatisDto
     * @param goldConsumeStatiss
     * @return
     */
    public List<GoldConsumeStatisDto> toGoldConsumeStatisDto(List<GoldConsumeStatis> goldConsumeStatiss) {
        List<GoldConsumeStatisDto> list = new ArrayList<>(goldConsumeStatiss.size());
        for (GoldConsumeStatis goldConsumeStatis : goldConsumeStatiss) {
            list.add(this.toGoldConsumeStatisDto(goldConsumeStatis));
        }

        return list;
    }

    /**
     * 转换GoldConsumeStatisDto
     * @param goldConsumeStatis
     * @return
     */
    public GoldConsumeStatisDto toGoldConsumeStatisDto(GoldConsumeStatis goldConsumeStatis) {
        GoldConsumeStatisDto dto = new GoldConsumeStatisDto();
        dto.setDateTime(goldConsumeStatis.getDaily());
        dto.setServerId(goldConsumeStatis.getServerId());
        dto.setTotal(goldConsumeStatis.getTotal());
        dto.setFunctionType(goldConsumeStatis.getFunctionType());
        dto.setFunctionCount(goldConsumeStatis.getFunctionCount());
        dto.setGold(goldConsumeStatis.getGold());

        return dto;
    }

}
