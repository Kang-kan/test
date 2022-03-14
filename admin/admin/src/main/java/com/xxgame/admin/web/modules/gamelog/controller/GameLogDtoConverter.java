package com.xxgame.admin.web.modules.gamelog.controller;

import com.xxgame.admin.web.model.SearchResult;
import com.xxgame.admin.web.modules.commons.BasePlayerLogDtoConverter;
import com.xxgame.admin.web.modules.gamelog.controller.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class GameLogDtoConverter {

    @Autowired
    private BasePlayerLogDtoConverter basePlayerLogDtoConverter;

    /**
     * 转换成PlayerLoginLogDto
     * @param rowMap
     * @return
     */
    public PlayerLoginLogDto toPlayerLoginLogDto(Map<String, Object> rowMap) {
        PlayerLoginLogDto dto = new PlayerLoginLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setLastIP((String) rowMap.get("lastip"));
        dto.setPower(((Number) rowMap.get("power")).longValue());

        return dto;
    }

    /**
     * 转换成PlayerLoginLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerLoginLogDto> toPlayerLoginLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerLoginLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerLoginLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerLogoutLogDto
     * @param rowMap
     * @return
     */
    public PlayerLogoutLogDto toPlayerLogoutLogDto(Map<String, Object> rowMap) {
        PlayerLogoutLogDto dto = new PlayerLogoutLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setLastIP((String) rowMap.get("lastip"));
        dto.setLoginTime((Long) rowMap.get("login_time"));
        dto.setPower(((Number) rowMap.get("power")).longValue());

        return dto;
    }

    /**
     * 转换成PlayerLogoutLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerLogoutLogDto> toPlayerLogoutLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerLogoutLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerLogoutLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerResourceDto
     * @param rowMap
     * @return
     */
    public PlayerResourceLogDto toPlayerResourceDto(Map<String, Object> rowMap) {
        PlayerResourceLogDto dto = new PlayerResourceLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setValue((Long) rowMap.get("value") + "");
        dto.setChangeNum((Long) rowMap.get("change_num") + "");
        dto.setFunctionType((String) rowMap.get("function_type"));
        dto.setExt((String) rowMap.get("ext"));

        return dto;
    }



    /**
     * 转换成PlayerResourceDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerResourceLogDto> toPlayerResourceDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerResourceLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerResourceDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerItemLogDto
     * @param rowMap
     * @return
     */
    public PlayerItemLogDto toPlayerItemLogDto(Map<String, Object> rowMap) {
        PlayerItemLogDto dto = new PlayerItemLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setBonusType((String) rowMap.get("bonus_type"));
        dto.setItemId((Integer) rowMap.get("item_id"));
        dto.setItemName((String) rowMap.get("item_name"));
        dto.setCount((Long) rowMap.get("count"));
        dto.setCurrentCount((Long) rowMap.get("current_count"));
        dto.setFunctionType((String) rowMap.get("function_type"));

        return dto;
    }

    /**
     * 转换成PlayerItemLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerItemLogDto> toPlayerItemLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerItemLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerItemLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerMarketLogDto
     * @param rowMap
     * @return
     */
    public PlayerMarketLogDto toPlayerMarketLogDto(Map<String, Object> rowMap) {
        PlayerMarketLogDto dto = new PlayerMarketLogDto();
        dto.setId((Long) rowMap.get("id") + "");
        dto.setAccount((String) rowMap.get("account"));
        dto.setPlayerId((Long) rowMap.get("player_id") + "");
        dto.setPlayerName((String) rowMap.get("player_name"));
        dto.setLevel((Integer) rowMap.get("level"));
        dto.setServerId((Integer) rowMap.get("server_id"));
        dto.setChannelId((String) rowMap.get("channel_id"));
        dto.setItemId((Integer) rowMap.get("item_id"));
        dto.setItemName((String) rowMap.get("item_name"));
        dto.setPrice((Integer) rowMap.get("price"));
        dto.setCount((Integer) rowMap.get("count"));
        dto.setCurrency((Integer) rowMap.get("currency"));
        dto.setAmount((Integer) rowMap.get("amount"));
        dto.setTime((Long) rowMap.get("time"));

        return dto;
    }

    /**
     * 转换成PlayerMarketLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerMarketLogDto> toPlayerMarketLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerMarketLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerMarketLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成ChargeOrderDto
     * @param rowMap
     * @return
     */
    public ChargeOrderDto toChargeOrderDto(Map<String, Object> rowMap) {
        ChargeOrderDto dto = new ChargeOrderDto();
        dto.setId((Long) rowMap.get("id") + "");
        dto.setAccount((String) rowMap.get("account"));
        dto.setPlayerId((Long) rowMap.get("player_id") + "");
        dto.setPlayerName((String) rowMap.get("player_name"));
        dto.setServerId((Integer) rowMap.get("server_id"));
        dto.setChannelId((String) rowMap.get("channel_id"));
        dto.setPlatName((String) rowMap.get("plat_name"));
        dto.setOrderId((Long) rowMap.get("order_id") + "");
        dto.setRmb((Integer) rowMap.get("rmb"));
        dto.setGold((Integer) rowMap.get("gold"));
        dto.setGoodsId((Integer) rowMap.get("goods_id"));
        dto.setFirst((Integer) rowMap.get("first_rmb") > 0);
        dto.setTransactionId((String) rowMap.get("transaction_id"));
        dto.setArrivalTime((Long) rowMap.get("arrival_time"));
        dto.setAdviceTime((Long) rowMap.get("advice_time"));
        dto.setGameResultCode((String) rowMap.get("game_result_code"));
        dto.setTime((Long) rowMap.get("time"));

        return dto;
    }

    /**
     * 转换成ChargeOrderDto列表
     * @param rowMaps
     * @return
     */
    public List<ChargeOrderDto> toChargeOrderDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<ChargeOrderDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toChargeOrderDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成ChargeBillDto
     * @param rowMap
     * @return
     */
    public ChargeBillDto toChargeBillDto(Map<String, Object> rowMap) {
        ChargeBillDto dto = new ChargeBillDto();
        dto.setAccount((String) rowMap.get("account"));
        dto.setPlayerId((Long) rowMap.get("player_id") + "");
        dto.setPlayerName((String) rowMap.get("player_name"));
        dto.setServerId((Integer) rowMap.get("server_id"));
        dto.setChannelId((String) rowMap.get("channel_id"));
        dto.setOrderId((Long) rowMap.get("order_id") + "");
        dto.setRmb((Integer) rowMap.get("rmb"));
        dto.setTransactionId((String) rowMap.get("transaction_id"));
        dto.setArrivalTime((Long) rowMap.get("arrival_time"));
        dto.setTime((Long) rowMap.get("time"));

        return dto;
    }

    /**
     * 转换成ChargeBillDto列表
     * @param rowMaps
     * @return
     */
    public List<ChargeBillDto> toChargeBillDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<ChargeBillDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toChargeBillDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerFashionLogDto
     * @param rowMap
     * @return
     */
    public PlayerFashionLogDto toPlayerFashionLogDto(Map<String, Object> rowMap) {
        PlayerFashionLogDto dto = new PlayerFashionLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setFashionId((Integer) rowMap.get("fashion_id"));
        dto.setFashionLevel((Integer) rowMap.get("fashion_level"));
        dto.setOverTime((Long) rowMap.get("over_time"));
        dto.setWeared((Integer) rowMap.get("weared"));

        return dto;
    }

    /**
     * 转换成PlayerFashionLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerFashionLogDto> toPlayerFashionLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerFashionLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerFashionLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerMailLogDto
     * @param rowMap
     * @return
     */
    public PlayerMailLogDto toPlayerMailLogDto(Map<String, Object> rowMap) {
        PlayerMailLogDto dto = new PlayerMailLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setMailId((Long) rowMap.get("mail_id") + "");
        dto.setCreateTime((Long) rowMap.get("create_time"));
        dto.setTitle((String) rowMap.get("title"));
        dto.setMessage((String) rowMap.get("message"));
        dto.setBonus((String) rowMap.get("bonus"));
        dto.setReadStatus((Integer) rowMap.get("read_status"));
        dto.setIsGet((Integer) rowMap.get("is_get"));
        dto.setConfigId((Integer) rowMap.get("config_id"));

        return dto;
    }

    /**
     * 转换成PlayerMailLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerMailLogDto> toPlayerMailLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerMailLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerMailLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerShareLogDto
     * @param rowMap
     * @return
     */
    public PlayerShareLogDto toPlayerShareLogDto(Map<String, Object> rowMap) {
        PlayerShareLogDto dto = new PlayerShareLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setShareCount((Integer) rowMap.get("share_count"));
        dto.setShareRewardCount((Integer) rowMap.get("share_reward_count"));

        return dto;
    }

    /**
     * 转换成PlayerShareLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerShareLogDto> toPlayerShareLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerShareLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerShareLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerSealStoneLogDto
     * @param rowMap
     * @return
     */
    public PlayerSealStoneLogDto toPlayerSealStoneLogDto(Map<String, Object> rowMap) {
        PlayerSealStoneLogDto dto = new PlayerSealStoneLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setEquipIndex((Integer) rowMap.get("equip_index"));
        dto.setEquipId((Integer) rowMap.get("equip_id"));

        return dto;
    }

    /**
     * 转换成PlayerSealStoneLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerSealStoneLogDto> toPlayerSealStoneLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerSealStoneLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerSealStoneLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerOfflineRewardLogDto
     * @param rowMap
     * @return
     */
    public PlayerOfflineRewardLogDto toPlayerOfflineRewardLogDto(Map<String, Object> rowMap) {
        PlayerOfflineRewardLogDto dto = new PlayerOfflineRewardLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setGap((Long) rowMap.get("gap"));
        dto.setBonus((String) rowMap.get("bonus"));

        return dto;
    }

    /**
     * 转换成PlayerSealStoneLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerOfflineRewardLogDto> toPlayerOfflineRewardLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerOfflineRewardLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerOfflineRewardLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerSerialGiftLogDto
     * @param rowMap
     * @return
     */
    public PlayerSerialGiftLogDto toPlayerSerialGiftLogDto(Map<String, Object> rowMap) {
        PlayerSerialGiftLogDto dto = new PlayerSerialGiftLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setCdKey((String) rowMap.get("cd_key"));
        dto.setBonus((String) rowMap.get("bonus"));

        return dto;
    }

    /**
     * 转换成PlayerSerialGiftLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerSerialGiftLogDto> toPlayerSerialGiftLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerSerialGiftLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerSerialGiftLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerCopyLogDto
     * @param rowMap
     * @return
     */
    public PlayerCopyLogDto toPlayerCopyLogDto(Map<String, Object> rowMap) {
        PlayerCopyLogDto dto = new PlayerCopyLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setCopyType((Integer) rowMap.get("copy_type"));
        dto.setCopyId((Integer) rowMap.get("copy_id"));
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setBonus((String) rowMap.get("bonus"));
        dto.setExt((String) rowMap.get("ext"));

        return dto;
    }

    /**
     * 转换成PlayerCopyLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerCopyLogDto> toPlayerCopyLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerCopyLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerCopyLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerRebirthLogDto
     * @param rowMap
     * @return
     */
    public PlayerRebirthLogDto toPlayerRebirthLogDto(Map<String, Object> rowMap) {
        PlayerRebirthLogDto dto = new PlayerRebirthLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setBeforeLevel((Integer) rowMap.get("before_level"));
        dto.setCost((String) rowMap.get("cost"));

        return dto;
    }

    /**
     * 转换成PlayerRebirthLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerRebirthLogDto> toPlayerRebirthLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerRebirthLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerRebirthLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成FamilyLogDto
     * @param rowMap
     * @return
     */
    public FamilyLogDto toFamilyLogDto(Map<String, Object> rowMap) {
        FamilyLogDto dto = new FamilyLogDto();
        dto.setId((Long) rowMap.get("id") + "");
        dto.setFamilyId((Long) rowMap.get("family_id") + "");
        dto.setName((String) rowMap.get("name"));
        dto.setLeaderName((String) rowMap.get("leader_name"));
        dto.setExp((Integer) rowMap.get("exp"));
        dto.setLevel((Integer) rowMap.get("level"));
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setOpPlayerId((Long) rowMap.get("op_player_id") + "");
        dto.setOpPlayerName((String) rowMap.get("op_player_name") + "");
        dto.setOpPos((Integer) rowMap.get("op_pos"));
        dto.setTargetPlayerId((Long) rowMap.get("target_player_id") + "");
        dto.setTargetPlayerName((String) rowMap.get("target_player_name"));
        dto.setTargetPos((Integer) rowMap.get("target_pos"));
        dto.setTime((Long) rowMap.get("time"));

        return dto;
    }

    /**
     * 转换成FamilyLogDto列表
     * @param rowMaps
     * @return
     */
    public List<FamilyLogDto> toFamilyLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<FamilyLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toFamilyLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成OtherResourceLogDto
     * @param rowMap
     * @return
     */
    public OtherResourceLogDto toOtherResourceLogDto(Map<String, Object> rowMap) {
        OtherResourceLogDto dto = new OtherResourceLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setType((Integer) rowMap.get("type"));
        dto.setValue((Long) rowMap.get("value") + "");
        dto.setChangeNum((Long) rowMap.get("change_num") + "");
        dto.setFunctionType((String) rowMap.get("function_type"));

        return dto;
    }

    /**
     * 转换成OtherResourceLogDto列表
     * @param rowMaps
     * @return
     */
    public List<OtherResourceLogDto> toOtherResourceLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<OtherResourceLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toOtherResourceLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerAchieveLoDto
     * @param rowMap
     * @return
     */
    public PlayerAchieveLoDto toPlayerAchieveLoDto(Map<String, Object> rowMap) {
        PlayerAchieveLoDto dto = new PlayerAchieveLoDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setAchieveId((Integer) rowMap.get("achieve_id"));
        dto.setBonus((String) rowMap.get("bonus"));

        return dto;
    }

    /**
     * 转换成PlayerAchieveLoDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerAchieveLoDto> toPlayerAchieveLoDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerAchieveLoDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerAchieveLoDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerTitleLogDto
     * @param rowMap
     * @return
     */
    public PlayerTitleLogDto toPlayerTitleLogDto(Map<String, Object> rowMap) {
        PlayerTitleLogDto dto = new PlayerTitleLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setTitleId((Integer) rowMap.get("title_id"));
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setExpireTime((Long) rowMap.get("expire_time"));

        return dto;
    }

    /**
     * 转换成PlayerTitleLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerTitleLogDto> toPlayerTitleLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerTitleLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerTitleLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerChopLogDto
     * @param rowMap
     * @return
     */
    public PlayerChopLogDto toPlayerChopLogDto(Map<String, Object> rowMap) {
        PlayerChopLogDto dto = new PlayerChopLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setChopId((Integer) rowMap.get("chop_id"));
        dto.setChildId((Integer) rowMap.get("child_id"));

        return dto;
    }

    /**
     * 转换成PlayerChopLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerChopLogDto> toPlayerChopLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerChopLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerChopLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerPokedexLogDto
     * @param rowMap
     * @return
     */
    public PlayerPokedexLogDto toPlayerPokedexLogDto(Map<String, Object> rowMap) {
        PlayerPokedexLogDto dto = new PlayerPokedexLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setPokedexId((Integer) rowMap.get("pokedex_id"));
        dto.setPokedexLevel((Integer) rowMap.get("pokedex_level"));
        dto.setCost((String) rowMap.get("cost"));

        return dto;
    }

    /**
     * 转换成PlayerPokedexLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerPokedexLogDto> toPlayerPokedexLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerPokedexLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerPokedexLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerPracticeLogDto
     * @param rowMap
     * @return
     */
    public PlayerPracticeLogDto toPlayerPracticeLogDto(Map<String, Object> rowMap) {
        PlayerPracticeLogDto dto = new PlayerPracticeLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setConfigId((Integer) rowMap.get("config_id"));
        dto.setChildConfigId((Integer) rowMap.get("child_config_id"));
        dto.setChildType((Integer) rowMap.get("child_type"));
        dto.setCost((String) rowMap.get("cost"));
        dto.setLevelId((Integer) rowMap.get("level_id"));

        return dto;
    }

    /**
     * 转换成PlayerPracticeLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerPracticeLogDto> toPlayerPracticeLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerPracticeLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerPracticeLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerSkillLogDto
     * @param rowMap
     * @return
     */
    public PlayerSkillLogDto toPlayerSkillLogDto(Map<String, Object> rowMap) {
        PlayerSkillLogDto dto = new PlayerSkillLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setSkillIndex((Integer) rowMap.get("skill_index"));
        dto.setSkillLevel((Integer) rowMap.get("skill_level"));
        dto.setCost((String) rowMap.get("cost"));

        return dto;
    }

    /**
     * 转换成PlayerSkillLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerSkillLogDto> toPlayerSkillLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerSkillLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerSkillLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerSkillBookLogDto
     * @param rowMap
     * @return
     */
    public PlayerSkillBookLogDto toPlayerSkillBookLogDto(Map<String, Object> rowMap) {
        PlayerSkillBookLogDto dto = new PlayerSkillBookLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setSkillIndex((Integer) rowMap.get("skill_index"));
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setSkillId((Integer) rowMap.get("skill_id"));
        dto.setSkillLevel((Integer) rowMap.get("skill_level"));
        dto.setCost((String) rowMap.get("cost"));
        dto.setReward((String) rowMap.get("reward"));

        return dto;
    }

    /**
     * 转换成PlayerSkillLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerSkillBookLogDto> toPlayerSkillBookLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerSkillBookLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerSkillBookLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerMeridianLogDto
     * @param rowMap
     * @return
     */
    public PlayerMeridianLogDto toPlayerMeridianLogDto(Map<String, Object> rowMap) {
        PlayerMeridianLogDto dto = new PlayerMeridianLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setMeridianLevel((Integer) rowMap.get("meridian_level"));
        dto.setMeridianStep((Integer) rowMap.get("meridian_step"));
        dto.setCost((String) rowMap.get("cost"));

        return dto;
    }

    /**
     * 转换成PlayerMeridianLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerMeridianLogDto> toPlayerMeridianLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerMeridianLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerMeridianLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerEnhanceLogDto
     * @param rowMap
     * @return
     */
    public PlayerEnhanceLogDto toPlayerEnhanceLogDto(Map<String, Object> rowMap) {
        PlayerEnhanceLogDto dto = new PlayerEnhanceLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setType((Integer) rowMap.get("type"));
        dto.setEnIndex((Integer) rowMap.get("en_index"));
        dto.setEnLevel((Integer) rowMap.get("en_level"));
        dto.setCost((String) rowMap.get("cost"));

        return dto;
    }

    /**
     * 转换成PlayerEnhanceLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerEnhanceLogDto> toPlayerEnhanceLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerEnhanceLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerEnhanceLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerWeaponSoulLogDto
     * @param rowMap
     * @return
     */
    public PlayerWeaponSoulLogDto toPlayerWeaponSoulLogDto(Map<String, Object> rowMap) {
        PlayerWeaponSoulLogDto dto = new PlayerWeaponSoulLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setWeaponIndex((Integer) rowMap.get("weapon_index"));
        dto.setEquipId((Integer) rowMap.get("equip_id"));
        dto.setCost((String) rowMap.get("cost"));
        dto.setElfExp((Integer) rowMap.get("elf_exp"));
        dto.setDeleteEquipIds((String) rowMap.get("delete_equip_ids"));

        return dto;
    }

    /**
     * 转换成PlayerWeaponSoulLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerWeaponSoulLogDto> toPlayerWeaponSoulLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerWeaponSoulLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerWeaponSoulLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerTotemLogDto
     * @param rowMap
     * @return
     */
    public PlayerTotemLogDto toPlayerTotemLogDto(Map<String, Object> rowMap) {
        PlayerTotemLogDto dto = new PlayerTotemLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setTotemId((Integer) rowMap.get("totem_id"));
        dto.setTotemLevel((Integer) rowMap.get("totem_level"));
        dto.setTotemChildLevel((Integer) rowMap.get("totem_child_level"));
        dto.setExp((Integer) rowMap.get("exp"));
        dto.setCost((String) rowMap.get("cost"));

        return dto;
    }

    /**
     * 转换成PlayerTotemLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerTotemLogDto> toPlayerTotemLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerTotemLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerTotemLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerTotemExpandLogDto
     * @param rowMap
     * @return
     */
    public PlayerTotemExpandLogDto toPlayerTotemExpandLogDto(Map<String, Object> rowMap) {
        PlayerTotemExpandLogDto dto = new PlayerTotemExpandLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setHeroConfigId((Integer) rowMap.get("hero_config_id"));
        dto.setExpandId((Integer) rowMap.get("expand_id"));
        dto.setExpandLevel((Integer) rowMap.get("expand_level"));
        dto.setSkillIndex((Integer) rowMap.get("skill_index"));
        dto.setExpandBase((Integer) rowMap.get("expand_base"));
        dto.setCost((String) rowMap.get("cost"));

        return dto;
    }

    /**
     * 转换成PlayerTotemExpandLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerTotemExpandLogDto> toPlayerTotemExpandLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerTotemExpandLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerTotemExpandLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成BossDeadLogDto
     * @param rowMap
     * @return
     */
    public BossDeadLogDto toBossDeadLogDto(Map<String, Object> rowMap) {
        BossDeadLogDto dto = new BossDeadLogDto();

        dto.setId((Long) rowMap.get("id"));
        dto.setPlayType((Integer) rowMap.get("play_type"));
        dto.setBattleId((Long) rowMap.get("battle_id"));
        dto.setBattleServer((Integer) rowMap.get("battle_server"));
        dto.setBossId((Integer) rowMap.get("boss_id"));
        dto.setBossName((String) rowMap.get("boss_name"));
        dto.setOwnerType((Integer) rowMap.get("owner_type"));
        dto.setOwnerId((Long) rowMap.get("owner_id"));
        dto.setOwnerName((String) rowMap.get("owner_name"));
        dto.setRewardType((Integer) rowMap.get("reward_type"));
        dto.setReward((String) rowMap.get("reward"));
        dto.setTime((Long) rowMap.get("time"));

        return dto;
    }

    /**
     * 转换成BossDeadLogDto列表
     * @param rowMaps
     * @return
     */
    public List<BossDeadLogDto> toBossDeadLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<BossDeadLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toBossDeadLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerHyzLogDto
     * @param rowMap
     * @return
     */
    public PlayerHyzLogDto toPlayerHyzLogDto(Map<String, Object> rowMap) {
        PlayerHyzLogDto dto = new PlayerHyzLogDto();

        basePlayerLogDtoConverter.toBasePlayerLogDto(dto, rowMap);
        dto.setOpValue((Integer) rowMap.get("op_value"));
        dto.setKillCount((Integer) rowMap.get("kill_count"));
        dto.setReward((String) rowMap.get("reward"));

        return dto;
    }

    /**
     * 转换成PlayerHyzLogDto列表
     * @param rowMaps
     * @return
     */
    public List<PlayerHyzLogDto> toPlayerHyzLogDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<PlayerHyzLogDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> rowMap : rowMaps) {
            dtos.add(this.toPlayerHyzLogDto(rowMap));
        }

        return dtos;
    }

    /**
     * 转换成PlayerPowerLogDto列表
     * @param searchResult
     * @return
     */
    public List<PlayerPowerLogDto> toPowerLogDtos(SearchResult searchResult) {
        List<PlayerPowerLogDto> dtos = new LinkedList<>();
        if (searchResult == null || searchResult.getRows() == null) {
            return dtos;
        }

        for (Map<String, Object> row : searchResult.getRows()) {
            PlayerPowerLogDto dto = new PlayerPowerLogDto();
            dto.setId(((Number) row.get("id")).longValue() + "");
            dto.setServerId((int) row.get("serverId"));
            dto.setAccount((String) row.get("account"));
            dto.setChannelId((String) row.get("channelId"));
            dto.setPlayerId(((Number) row.get("playerId")).longValue() + "");
            dto.setPlayerName((String) row.get("playerName"));
            dto.setLevel((int) row.get("level"));
            dto.setConfigId((int) row.get("configId"));
            dto.setAction((int) row.get("action"));
            dto.setPower(((Number) row.get("power")).longValue());
            dto.setHeroPower((String) row.get("heroPower"));
            dto.setPropertys((String) row.get("propertys"));
            dto.setTime((long) row.get("time"));

            dtos.add(dto);
        }

        return dtos;
    }

}
