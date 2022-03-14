package com.xxgame.admin.web.modules.chargestatis.controller;

import com.xxgame.admin.web.modules.chargestatis.controller.model.*;
import com.xxgame.admin.web.modules.chargestatis.entity.ChargeLossStatis;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 充值统计Dto转换器
 */
@Service
public class ChargeStatisDtoConverter {

    /**
     * 转换成ChargeMonthDto
     * @param totalStatis
     * @param channelId
     * @param loginCount
     * @return
     */
    public ChargeMonthDto toChargeMonthDto(Map<String, Object> totalStatis, String channelId, int loginCount) {
        Number totalAmount = (Number) totalStatis.getOrDefault("totalAmount", 0);
        Number chargePlayer = (Number) totalStatis.getOrDefault("totalPlayer", 0);
        Number chargeTimes = (Number) totalStatis.getOrDefault("totalCount", 0);

        ChargeMonthDto dto = new ChargeMonthDto();
        dto.setChannelId(channelId);
        dto.setTotalAmount(totalAmount == null ? 0 : totalAmount.longValue());
        dto.setChargePlayer(chargePlayer == null ? 0 : chargePlayer.intValue());
        dto.setChargeTimes(chargeTimes == null ? 0 : chargeTimes.intValue());
        if (loginCount > 0) {
            dto.setArpu((int) (dto.getTotalAmount() / loginCount));
        }

        return dto;
    }

    /**
     * 转换成SingleChargeDistDto列表
     * @param rowMaps
     * @return
     */
    public List<SingleChargeDistDto> toSingleChargeDistDtosV1(List<Map<String, Object>> rowMaps) {
        List<SingleChargeDistDto> dtos = new LinkedList<>();
        for (Map<String, Object> row : rowMaps) {
            int count = ((Number) row.get("count")).intValue();
            int amount = ((Number) row.get("rmb")).intValue();
            String channelId = (String) row.get("channel_id");

            SingleChargeDistDto dto = new SingleChargeDistDto();
            dto.setChannelId(channelId);
            dto.setCount(count);
            dto.setAmount(amount);

            dtos.add(dto);
        }
        final int totalCount = dtos.stream().mapToInt(SingleChargeDistDto::getCount).sum();
        final long totalAmount = dtos.stream().mapToLong(dto -> dto.getAmount() * dto.getCount()).sum();
        dtos.forEach(dto -> {
            dto.setTotalCount(totalCount);
            dto.setTotalAmount(totalAmount);
        });

        return dtos;
    }

    /**
     * 全渠道SingleChargeDistDto列表
     * @param rowMaps
     * @return
     */
    public List<SingleChargeDistDto> toALLChannelSingleChargeDistDtosV1(List<Map<String, Object>> rowMaps) {
        Map<String, List<SingleChargeDistDto>> dtoMap = new HashMap<>();
        for (Map<String, Object> row : rowMaps) {
            int count = ((Number) row.get("count")).intValue();
            int amount = ((Number) row.get("rmb")).intValue();
            String channelId = (String) row.get("channel_id");

            SingleChargeDistDto dto = new SingleChargeDistDto();
            dto.setChannelId(channelId);
            dto.setCount(count);
            dto.setAmount(amount);

            List<SingleChargeDistDto> dtos = dtoMap.get(channelId);
            if (dtos == null) {
                dtos = new LinkedList<>();
                dtoMap.put(channelId, dtos);
            }
            dtos.add(dto);
        }

        List<SingleChargeDistDto> distDtos = new LinkedList<>();
        for (Map.Entry<String, List<SingleChargeDistDto>> entry : dtoMap.entrySet()) {
            List<SingleChargeDistDto> dtos = entry.getValue();
            final int totalCount = dtos.stream().mapToInt(SingleChargeDistDto::getCount).sum();
            final long totalAmount = dtos.stream().mapToLong(dto -> dto.getAmount() * dto.getCount()).sum();
            dtos.forEach(dto -> {
                dto.setTotalCount(totalCount);
                dto.setTotalAmount(totalAmount);
            });
            distDtos.addAll(dtos);
        }

        return distDtos;
    }

    /**
     * 转换成TotalChargeDistDto列表
     * @param rowMaps
     * @return
     */
    public List<TotalChargeDistDto> toTotalChargeDistDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null) {
            return new ArrayList<>(0);
        }

        long totalAmount = 0;
        int totalPlayer = rowMaps.size();

        TreeMap<Integer, Integer> amountMap = new TreeMap<>();
        for (Map<String, Object> row : rowMaps) {
            int playerAmount = ((Number) row.get("playerAmount")).intValue();

            int playerCount = amountMap.getOrDefault(playerAmount, 0);
            amountMap.put(playerAmount, playerCount + 1);

            totalAmount += playerAmount;
        }

        List<TotalChargeDistDto> dtos = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : amountMap.entrySet()) {
            TotalChargeDistDto dto = new TotalChargeDistDto();
            dto.setAmount(entry.getKey());
            dto.setPlayerCount(entry.getValue());
            dto.setTotalAmount(totalAmount);
            dto.setTotalPlayer(totalPlayer);
            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * 转换成FirstChargeLevelDistDto列表
     * @param rowMaps
     * @return
     */
    public List<FirstChargeLevelDistDto> toFirstChargeLevelDistDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null) {
            return new ArrayList<>(0);
        }

        List<FirstChargeLevelDistDto> dtos = new ArrayList<>();
        for (Map<String, Object> row : rowMaps) {
            int level = ((Number) row.get("level")).intValue();
            int count = ((Number) row.get("count")).intValue();

            FirstChargeLevelDistDto dto = new FirstChargeLevelDistDto();
            dto.setLevel(level);
            dto.setPlayerCount(count);

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * 转换成ChargeLevelDistDto列表
     * @param rowMaps
     * @return
     */
    public List<ChargeLevelDistDto> toChargeLevelDistDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null) {
            return new ArrayList<>(0);
        }

        long totalAmount = 0;
        List<ChargeLevelDistDto> dtos = new ArrayList<>();
        for (Map<String, Object> row : rowMaps) {
            int level = ((Number) row.get("level")).intValue();
            int count = ((Number) row.get("count")).intValue();
            int amount = ((Number) row.get("amount")).intValue();

            totalAmount += amount;

            ChargeLevelDistDto dto = new ChargeLevelDistDto();
            dto.setLevel(level);
            dto.setPlayerCount(count);

            dtos.add(dto);
        }
        for (ChargeLevelDistDto dto : dtos) {
            dto.setTotalAmount(totalAmount);
        }

        return dtos;
    }

    /**
     * 转换成ChargeLossDto
     * @param channelId
     * @param totalStatis
     * @param lossCount
     * @return
     */
    public ChargeLossDto toChargeLossDto(String channelId, Map<String, Object> totalStatis, int lossCount) {
        int totalPlayer = ((Number) totalStatis.get("totalPlayer")).intValue();

        ChargeLossDto dto = new ChargeLossDto();
        dto.setChannelId(channelId);
        dto.setChargePlayer(totalPlayer);
        dto.setLossPlayer(lossCount);

        return dto;
    }

    /**
     * 转换成ChargeLossDto列表
     * @param lossStatiss
     * @return
     */
    public List<ChargeLossDto> toChargeLossDtos(List<ChargeLossStatis> lossStatiss) {
        if (lossStatiss == null) {
            return new ArrayList<>(0);
        }

        List<ChargeLossDto> list = new LinkedList<>();
        for (ChargeLossStatis lossStatis : lossStatiss) {
            list.add(this.toChargeLossDto(lossStatis));
        }

        return list;
    }

    /**
     * 转换成ChargeLossDto
     * @param lossStatis
     * @return
     */
    public ChargeLossDto toChargeLossDto(ChargeLossStatis lossStatis) {
        ChargeLossDto dto = new ChargeLossDto();
        dto.setServerId(lossStatis.getServerId());
        dto.setChannelId(lossStatis.getChannelId());
        dto.setChargePlayer(lossStatis.getChargeCount());
        dto.setLossPlayer(lossStatis.getLostCount());

        return dto;
    }

    /**
     * 转换成CostGoldRankDto列表
     * @param rankMaps
     * @return
     */
    public List<CostGoldRankDto> toCostGoldRankDtos(List<Map<String, Object>> rankMaps) {
        if (rankMaps == null) {
            return new ArrayList<>();
        }

        List<CostGoldRankDto> dtos = new ArrayList<>();
        for (Map<String, Object> row : rankMaps) {
            int amount = ((Number) row.get("amount")).intValue();

            CostGoldRankDto dto = new CostGoldRankDto();
            dto.setPlayerId((long) row.get("player_id"));
            dto.setPlayerName((String) row.get("player_name"));
            dto.setTimes(((Long) row.get("times")).intValue());
            dto.setAmount(Math.abs(amount));

            dtos.add(dto);
        }

        return dtos;
    }

}
