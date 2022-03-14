package com.xxgame.admin.web.modules.customerservice.service;


import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.modules.customerservice.entity.BlockChat;
import com.xxgame.admin.web.modules.customerservice.entity.BlockPlayer;
import org.springframework.data.domain.Page;

/**
 * 角色服务类
 */
public interface PlayerService {

    /**
     * 分页显示封禁角色
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BlockPlayer> findBlockPlayers(int pageNo, int pageSize);

    /**
     * 分页显示封禁角色
     * @param serverId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BlockPlayer> findBlockPlayers(int serverId, int pageNo, int pageSize);

    /**
     * 封禁角色
     * @param serverId
     * @param playerId
     * @param unBlockTime
     * @param reason
     * @return
     */
    Result<Boolean> blockPlayer(int serverId, long playerId, long unBlockTime, String reason);

    /**
     * 解封角色
     * @param playerId
     * @return
     */
    Result<Boolean> unBlockPlayer(long playerId,int serverId);

    /**
     * 查找BlockPlayer
     * @param playerId
     * @return
     */
    BlockPlayer findBlockPlayer(long playerId);

    /**
     * 分页显示禁言角色
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BlockChat> findBlockChats(int pageNo, int pageSize);

    /**
     * 分页显示禁言角色
     * @param serverId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BlockChat> findBlockChats(int serverId, int pageNo, int pageSize);

    /**
     * 禁言
     * @param serverId
     * @param playerId
     * @param unBlockTime
     * @param reason
     * @return
     */
    Result<Boolean> blockChat(int serverId, long playerId, long unBlockTime, String reason);

    /**
     * 解除禁言
     * @param playerId
     * @param serverId
     * @return
     */
    Result<Boolean> unBlockChat(long playerId, int serverId);

    /**
     * 查找BlockChat
     * @param playerId
     * @return
     */
    BlockChat findBlockChat(long playerId);

}
