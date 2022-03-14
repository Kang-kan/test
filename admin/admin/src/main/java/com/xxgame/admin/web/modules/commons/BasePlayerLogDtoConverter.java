package com.xxgame.admin.web.modules.commons;

import com.xxgame.admin.web.modules.gamelog.controller.model.BasePlayerLogDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 基础的玩家dto转换器
 */
@Service
public class BasePlayerLogDtoConverter {

    /**
     * 转换成BasePlayerLogDto
     * @param rowMap
     * @return
     */
    public BasePlayerLogDto toBasePlayerLogDto(Map<String, Object> rowMap) {
        BasePlayerLogDto dto = new BasePlayerLogDto();
        this.toBasePlayerLogDto(dto, rowMap);

        return dto;
    }

    /**
     * 转换成BasePlayerLogDto
     * @param dto
     * @param rowMap
     */
    public void toBasePlayerLogDto(BasePlayerLogDto dto, Map<String, Object> rowMap) {
        dto.setId((Long) rowMap.get("id") + "");
        dto.setAccount((String) rowMap.get("account"));
        dto.setPlayerId((Long) rowMap.get("player_id") + "");
        dto.setPlayerName((String) rowMap.get("player_name"));
        dto.setLevel((Integer) rowMap.get("level"));
        dto.setChannelId((String) rowMap.get("channel_id"));
        dto.setPlatName((String) rowMap.get("plat_name"));
        dto.setTime((Long) rowMap.get("time"));

        Integer serverId = (Integer) rowMap.get("server_id");
        if (serverId == null) {
            serverId = this.parseServerId(dto.getAccount());
        }

        dto.setServerId(serverId);
    }

    /**
     * 解析服务器id
     * @param account
     * @return
     */
    private int parseServerId(String account) {
        if (StringUtils.isBlank(account)) {
            return 0;
        }
        String[] args = account.split("\\.");
        if (args.length != 3) {
            return 0;
        }

        return Integer.parseInt(args[2]);
    }

}
