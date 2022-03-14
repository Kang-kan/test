package com.xxgame.admin.web.modules.gameserver;

import com.xxgame.admin.web.modules.gameserver.controller.model.GameServerRequest;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.entity.RoleGameServer;

import java.util.List;

/**
 * GameServerService
 */
public interface GameServerService {

    /**
     * 增加服务器
     * @param request
     * @return
     */
    GameServer addServer(GameServerRequest request);

    /**
     * 修改服务器信息
     * @param gameServer
     * @param request
     * @return
     */
    GameServer updateServer(GameServer gameServer, GameServerRequest request);

    /**
     * 添加角色服务器
     * @param roleId
     * @param serverId
     * @return
     */
    RoleGameServer addRoleGameServedr(int roleId, int serverId);

    /**
     * 获取缓存GameServer
     * @param serverId
     * @return
     */
    GameServer getCacheGameServer(int serverId);

    /**
     * 是否拥有该服务器权限
     * @param userId
     * @param serverId
     * @return
     */
    boolean isHaveThisServer(long userId, int serverId);

    /**
     * 获取所有服务器id列表
     * @return
     */
    List<Integer> getAllServerIds();

    /**
     * 获取所有已开服的服务器id列表
     * @return
     */
    List<Integer> getAllOpenedServerIds();

    /**
     * 去除未开服的、被合服的id
     * @param serverIds
     * @return
     */
    List<Integer> runningServerIds(List<Integer> serverIds);
}
