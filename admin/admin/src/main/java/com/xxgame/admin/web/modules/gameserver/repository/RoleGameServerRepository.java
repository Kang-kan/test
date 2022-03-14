package com.xxgame.admin.web.modules.gameserver.repository;

import com.xxgame.admin.web.modules.gameserver.entity.RoleGameServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 角色游戏服
 * @author gil
 *
 */
public interface RoleGameServerRepository extends JpaRepository<RoleGameServer, Long> {

    /**
     * 根据角色id找出所有服务器列表
     * @param roleId
     * @return
     */
    List<RoleGameServer> findByRoleId(int roleId);

    /**
     * 根据角色id找出所有服务器列表
     * @param roleIds
     * @return
     */
    List<RoleGameServer> findByRoleIdIn(List<Integer> roleIds);

    /**
     * 根据角色id找出所有服务器列表
     * @param roleId
     * @param pageable
     * @return
     */
    Page<RoleGameServer> findByRoleId(int roleId, Pageable pageable);

    /**
     * 根据角色id找出所有服务器列表
     * @param roleIds
     * @param pageable
     * @return
     */
    Page<RoleGameServer> findByRoleIdIn(List<Integer> roleIds, Pageable pageable);

    /**
     * 根据角色id和服务器id查找
     * @param roleId
     * @param serverId
     * @return
     */
    RoleGameServer findByRoleIdAndServerId(int roleId, int serverId);
}
