package com.xxgame.admin.web.modules.gameserver.controller;

import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.controller.model.GameServerDto;
import com.xxgame.admin.web.modules.gameserver.controller.model.SimpleGameServerDto;
import com.xxgame.admin.web.modules.gameserver.controller.model.UserGameServerDto;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.entity.RoleGameServer;
import com.xxgame.admin.web.modules.gameserver.repository.GameServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO转换器
 */
@Service
public class GameServerDtoConverter {

    @Autowired
    private GameServerRepository gameServerRepository;
    @Autowired
    private GameServerService gameServerService;

    /**
     * 转换成GameServerDto
     * @param gameServer
     * @return
     */
    public GameServerDto toGameServerDto(GameServer gameServer) {
        GameServerDto dto = new GameServerDto();
        dto.setId(gameServer.getId());
        dto.setName(gameServer.getName());
        dto.setLocalIp(gameServer.getLocalIp());
        dto.setOpenTime(gameServer.getOpenTime());
        dto.setSchedOpenTime(gameServer.getSchedOpenTime());
        dto.setPort(gameServer.getPort());
        dto.setChargePort(gameServer.getChargePort());
        dto.setGmPort(gameServer.getGmPort());
        dto.setMergeTime(gameServer.getMergeTime());
        dto.setMergeSrvId(gameServer.getMergeSrvId());
        dto.setOperatorId(gameServer.getOperatorId());
        dto.setUpdateTime(gameServer.getUpdateTime());
        dto.setCreateTime(gameServer.getCreateTime());

        return dto;
    }

    /**
     * 转换成GameServerDto列表
     * @param gameServers
     * @return
     */
    public List<GameServerDto> toGameServerDtos(List<GameServer> gameServers) {
        if (gameServers == null || gameServers.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<GameServerDto> dtos = new ArrayList<>(gameServers.size());
        for (GameServer gameServer : gameServers) {
            dtos.add(this.toGameServerDto(gameServer));
        }

        return dtos;
    }

    /**
     * 转换成UserGameServerDto
     * @param roleGameServer
     * @return
     */
    public UserGameServerDto toUserGameServerDto(RoleGameServer roleGameServer) {
        GameServer gameServer = gameServerRepository.getOne(roleGameServer.getServerId());

        UserGameServerDto dto = new UserGameServerDto();
        dto.setId(roleGameServer.getId());
        dto.setServerId(roleGameServer.getServerId());
        dto.setName(gameServer == null ? "" : gameServer.getName());

        return dto;
    }

    /**
     * UserGameServerDto
     * @param roleGameServers
     * @return
     */
    public List<UserGameServerDto> toUserGameServerDtos(List<RoleGameServer> roleGameServers) {
        if (roleGameServers == null || roleGameServers.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<UserGameServerDto> dtos = new ArrayList<>(roleGameServers.size());
        for (RoleGameServer roleGameServer : roleGameServers) {
            dtos.add(this.toUserGameServerDto(roleGameServer));
        }

        return dtos;
    }

    /**
     * 转换成SimpleGameServerDto
     * @param gameServer
     * @return
     */
    public SimpleGameServerDto toSimpleGameServerDto(GameServer gameServer) {
        SimpleGameServerDto dto = new SimpleGameServerDto();
        dto.setId(gameServer.getId());
        dto.setName(gameServer.getName());
        dto.setOpenTime(gameServer.getOpenTime());
        dto.setMergeTime(gameServer.getMergeTime());
        dto.setMergeSrvId(gameServer.getMergeSrvId());

        return dto;
    }

    /**
     * 转换成SimpleGameServerDto列表
     * @param roleGameServers
     * @return
     */
    public List<SimpleGameServerDto> toSimpleGameServerDtos(List<RoleGameServer> roleGameServers) {
        if (roleGameServers == null || roleGameServers.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<SimpleGameServerDto> dtos = new ArrayList<>(roleGameServers.size());
        for (RoleGameServer roleGameServer : roleGameServers) {
            GameServer gameServer = gameServerService.getCacheGameServer(roleGameServer.getServerId());
            dtos.add(this.toSimpleGameServerDto(gameServer));
        }

        return dtos;
    }

}
