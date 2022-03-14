package com.xxgame.admin.web.modules.gameserver;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.xxgame.admin.web.modules.auth.SystemUserRoleService;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.commons.IdWorkerService;
import com.xxgame.admin.web.modules.gameserver.controller.model.GameServerRequest;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.entity.RoleGameServer;
import com.xxgame.admin.web.modules.gameserver.repository.GameServerRepository;
import com.xxgame.admin.web.modules.gameserver.repository.RoleGameServerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 服务器管理类
 */
@Service
public class GameServerServiceImpl implements GameServerService {

    @Autowired
    private GameServerRepository gameServerRepository;
    @Autowired
    private RoleGameServerRepository roleGameServerRepository;
    @Autowired
    private SystemUserRoleService systemUserRoleService;
    @Autowired
    private IdWorkerService idWorkerService;
    @Autowired
    private GmClient gmClient;

    /**
     * 服务器缓存 { serverId : GameServer }
     */
    private LoadingCache<Integer, GameServer> serverCache;

    /**
     * 角色服务器权限缓存 { roleId:serverId : serverId }
     */
    private LoadingCache<String, Integer> serverRightCache;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    private void init() {
        serverCache = CacheBuilder.newBuilder()
                .maximumSize(999)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, GameServer>() {
                    @Override
                    public GameServer load(Integer key) throws Exception {
                        Optional<GameServer> optional = gameServerRepository.findById(key);
                        return optional.isPresent() ? optional.get() : null;
                    }
                });

        serverRightCache = CacheBuilder.newBuilder()
                .maximumSize(999)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        String[] args = key.split(":");
                        int roleId = Integer.valueOf(args[0]);
                        int serverId = Integer.parseInt(args[1]);
                        return loadRoleGameServer(roleId, serverId);
                    }
                });
    }

    /**
     * 是否拥有该服务器
     * @param roleId
     * @param serverId
     * @return
     */
    private int loadRoleGameServer(int roleId, int serverId) {
        RoleGameServer roleGameServer = roleGameServerRepository.findByRoleIdAndServerId(roleId, serverId);
        return roleGameServer == null ? 0 : serverId;
    }

    @Override
    public GameServer addServer(GameServerRequest request) {
        GameServer gameServer = new GameServer();
        gameServer.setId(request.getId());
        gameServer.setName(request.getName());
        gameServer.setLocalIp(request.getLocalIp());
        gameServer.setOpenTime(request.getOpenTime());
        gameServer.setSchedOpenTime(request.getSchedOpenTime());
        gameServer.setPort(request.getPort());
        gameServer.setChargePort(request.getChargePort());
        gameServer.setGmPort(request.getGmPort());
        gameServer.setMergeTime(request.getMergeTime());
        gameServer.setMergeSrvId(request.getMergeSrvId());
        gameServer.setOperatorId(request.getOperatorId());
        gameServer.setUpdateTime(System.currentTimeMillis());
        gameServer.setCreateTime(gameServer.getUpdateTime());

        return gameServerRepository.save(gameServer);
    }

    @Override
    public GameServer updateServer(GameServer gameServer, GameServerRequest request) {
        gameServer.setId(request.getId());
        gameServer.setName(request.getName());
        gameServer.setLocalIp(request.getLocalIp());
        if(gameServer.getSchedOpenTime() != request.getSchedOpenTime()) {
            gmClient.setOpenTime(gameServer.getId(), request.getSchedOpenTime());
        }
        gameServer.setOpenTime(request.getOpenTime());
        gameServer.setSchedOpenTime(request.getSchedOpenTime());
        gameServer.setPort(request.getPort());
        gameServer.setChargePort(request.getChargePort());
        gameServer.setGmPort(request.getGmPort());
        gameServer.setMergeTime(request.getMergeTime());
        gameServer.setMergeSrvId(request.getMergeSrvId());
        gameServer.setOperatorId(request.getOperatorId());
        gameServer.setUpdateTime(System.currentTimeMillis());

        gameServer = gameServerRepository.save(gameServer);
        serverCache.invalidate(gameServer.getId());

        return gameServer;
    }

    @Override
    public RoleGameServer addRoleGameServedr(int roleId, int serverId) {
        RoleGameServer roleGameServer = new RoleGameServer();
        roleGameServer.setId(idWorkerService.nextCommonId());
        roleGameServer.setRoleId(roleId);
        roleGameServer.setServerId(serverId);

        String key = String.format("%s:%s", roleId, serverId);
        serverRightCache.invalidate(key);

        return roleGameServerRepository.save(roleGameServer);
    }

    @Override
    public GameServer getCacheGameServer(int serverId) {
        try {
            return this.serverCache.get(serverId);
        } catch (ExecutionException e) {
            logger.error("加载服务器信息异常 serverId:" + serverId, e);
        }
        return null;
    }

    @Override
    public boolean isHaveThisServer(long userId, int serverId) {
        List<SystemUserRole> userRoles = systemUserRoleService.findByUserId(userId);
        for (SystemUserRole userRole : userRoles) {
            String key = String.format("%s:%s", userRole.getRoleId(), serverId);
            try {
                int id = serverRightCache.get(key);
                if (id == serverId) {
                    return true;
                }
            } catch (ExecutionException e) {
                logger.error("检查服务器权限异常 serverId:" + serverId, e);
            }
        }

        return false;
    }

    @Override
    public List<Integer> getAllServerIds() {
        List<Integer> ids = new ArrayList<>();

        List<GameServer> servers = gameServerRepository.findAll();
        for (GameServer gs : servers) {
            ids.add(gs.getId());
        }

        return ids;
    }

    @Override
    public List<Integer> getAllOpenedServerIds() {
        List<Integer> ids = new ArrayList<>();

        List<GameServer> servers = gameServerRepository.findAll();
        for (GameServer gs : servers) {
            if (gs.getOpenTime() == 0) {
                continue;
            }
            if (gs.getOpenTime() > System.currentTimeMillis()) {
                continue;
            }
            ids.add(gs.getId());
        }

        return ids;
    }

    @Override
    public List<Integer> runningServerIds(List<Integer> serverIds) {
        Iterator<Integer> iterator = serverIds.iterator();
        while (iterator.hasNext()) {
            int serverId = iterator.next();
            GameServer gameServer = this.getCacheGameServer(serverId);
            // 未开服的
            if (!gameServer.isOpen()) {
                iterator.remove();
                continue;
            }
            // 已被合服的
            if (gameServer.getMergeSrvId() > 0 && gameServer.getId() != gameServer.getMergeSrvId()) {
                iterator.remove();
                continue;
            }
        }

        return new ArrayList<>(serverIds);
    }

}
