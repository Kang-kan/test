package com.xxgame.admin.web.modules.customerservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.commons.service.QueryGameService;
import com.xxgame.admin.web.modules.customerservice.entity.BlockChat;
import com.xxgame.admin.web.modules.customerservice.entity.BlockPlayer;
import com.xxgame.admin.web.modules.customerservice.repository.BlockChatRepository;
import com.xxgame.admin.web.modules.customerservice.repository.BlockPlayerRepository;
import com.xxgame.admin.web.util.PlayerPartitionIdHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 角色服务类
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private BlockPlayerRepository blockPlayerRepository;
    @Autowired
    private BlockChatRepository blockChatRepository;
    @Autowired
    private GmClient gmClient;
    @Autowired
    private QueryGameService queryGameService;

    /**
     * { playerId : BlockPlayer }
     */
    private LoadingCache<Long, Optional<BlockPlayer>> blockPlaerCache;

    /**
     * { playerId : BlockChat }
     */
    private LoadingCache<Long, Optional<BlockChat>> blockChatCache;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    private void init() {
        blockPlaerCache = CacheBuilder.newBuilder()
                .maximumSize(2048)
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .build(new CacheLoader<Long, Optional<BlockPlayer>>() {
                    @Override
                    public Optional<BlockPlayer> load(Long key) throws Exception {
                        return blockPlayerRepository.findById(key);
                    }
                });

        blockChatCache = CacheBuilder.newBuilder()
                .maximumSize(2048)
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .build(new CacheLoader<Long, Optional<BlockChat>>() {
                    @Override
                    public Optional<BlockChat> load(Long key) throws Exception {
                        return blockChatRepository.findById(key);
                    }
                });
    }

    @Override
    public Page<BlockPlayer> findBlockPlayers(int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return blockPlayerRepository.findBlockPlayers(pageRequest);
    }

    @Override
    public Page<BlockPlayer> findBlockPlayers(int serverId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return blockPlayerRepository.findBlockPlayers(serverId, pageRequest);
    }

    @Override
    public Result<Boolean> blockPlayer(int serverId, long playerId, long unBlockTime, String reason) {
        JSONObject playerInfo = this.queryPlayerInfo(serverId, playerId);
        if (playerInfo == null) {
            return Result.error(ResultCode.PARAM_ERROR, "找不到该用户。");
        }

        String result = gmClient.blockPlayer(serverId, playerId + "", unBlockTime, reason);
        if (StringUtils.isBlank(result)) {
            return Result.error(ResultCode.FAIL, "请求超时或返回为空。");
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Integer resultCode = jsonObject.getInteger("resultCode") ;
        boolean success = resultCode != null && resultCode == 0;
        if (!success) {
            return Result.error(ResultCode.FAIL, "请求超时。");
        }

        BlockPlayer blockPlayer = new BlockPlayer();
        blockPlayer.setPlayerId(playerId);
        blockPlayer.setServerId(serverId);
        blockPlayer.setAccount(playerInfo.getString("account"));
        blockPlayer.setChannelId(playerInfo.getString("channelId"));
        blockPlayer.setPlayerName(playerInfo.getString("playerName"));
        blockPlayer.setUnBlockTime(unBlockTime);
        blockPlayer.setReason(reason);
        blockPlayer.setUpdateTime(System.currentTimeMillis());

        blockPlayerRepository.save(blockPlayer);
        // 删除缓存
        blockPlaerCache.invalidate(playerId);

        return Result.success(true);
    }

    @Override
    public Result<Boolean> unBlockPlayer(long playerId,int serverId) {
        String result = gmClient.blockPlayer(serverId, playerId + "", 0, null);
        if (StringUtils.isBlank(result)) {
            return Result.error(ResultCode.FAIL, "请求超时或返回为空。");
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Integer resultCode = jsonObject.getInteger("resultCode") ;
        boolean success = resultCode != null && resultCode == 0;
        if (!success) {
            return Result.error(ResultCode.FAIL, "请求超时。");
        }

        // 从数据库中删除
        try {
            blockPlayerRepository.deleteById(playerId);
        } catch (EmptyResultDataAccessException e) {
            // 不用处理
        }
        // 删除缓存
        blockPlaerCache.invalidate(playerId);

        return Result.success(true);
    }

    @Override
    public BlockPlayer findBlockPlayer(long playerId) {
        try {
            Optional<BlockPlayer> optional = this.blockPlaerCache.get(playerId);
            return optional.isPresent() ? optional.get() : null;
        } catch (Exception e) {
            logger.error("加载BlockPlayer数据异常 playerId:" + playerId, e);
        }
        return null;
    }

    @Override
    public Page<BlockChat> findBlockChats(int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return blockChatRepository.findBlockChats(pageRequest);
    }

    @Override
    public Page<BlockChat> findBlockChats(int serverId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return blockChatRepository.findBlockChats(serverId, pageRequest);
    }

    @Override
    public Result<Boolean> blockChat(int serverId, long playerId, long unBlockTime, String reason) {
        JSONObject playerInfo = this.queryPlayerInfo(serverId, playerId);
        if (playerInfo == null) {
            return Result.error(ResultCode.PARAM_ERROR, "找不到该用户。");
        }

        String result = gmClient.blockChat(serverId, playerId + "", unBlockTime, reason);
        if (StringUtils.isBlank(result)) {
            return Result.error(ResultCode.FAIL, "请求超时或返回为空。");
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Integer resultCode = jsonObject.getInteger("resultCode") ;
        boolean success = resultCode != null && resultCode == 0;
        if (!success) {
            return Result.error(ResultCode.FAIL, "请求超时。");
        }

        BlockChat blockChat = new BlockChat();
        blockChat.setPlayerId(playerId);
        blockChat.setServerId(serverId);
        blockChat.setAccount(playerInfo.getString("account"));
        blockChat.setChannelId(playerInfo.getString("channelId"));
        blockChat.setPlayerName(playerInfo.getString("playerName"));
        blockChat.setUnBlockTime(unBlockTime);
        blockChat.setReason(reason);
        blockChat.setUpdateTime(System.currentTimeMillis());

        blockChatRepository.save(blockChat);
        // 删除缓存
        blockChatCache.invalidate(playerId);

        return Result.success(true);
    }

    @Override
    public Result<Boolean> unBlockChat(long playerId, int serverId) {
        String result = gmClient.blockChat(serverId, playerId + "", 0, null);
        if (StringUtils.isBlank(result)) {
            return Result.error(ResultCode.FAIL, "请求超时或返回为空。");
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Integer resultCode = jsonObject.getInteger("resultCode") ;
        boolean success = resultCode != null && resultCode == 0;
        if (!success) {
            return Result.error(ResultCode.FAIL, "请求超时。");
        }

        // 从数据库中删除
        try {
            blockChatRepository.deleteById(playerId);
        } catch (EmptyResultDataAccessException e) {
            // 不用处理
        }
        // 删除缓存
        blockChatCache.invalidate(playerId);

        return Result.success(true);
    }

    @Override
    public BlockChat findBlockChat(long playerId) {
        try {
            Optional<BlockChat> optional = blockChatCache.get(playerId);
            return optional.isPresent() ? optional.get() : null;
        } catch (ExecutionException e) {
            logger.error("加载BlockChat数据异常 playerId:" + playerId, e);
        }
        return null;
    }

    /**
     * 向游戏服查询用户信息
     * @param serverId
     * @param playerId
     * @return
     */
    private JSONObject queryPlayerInfo(int serverId, long playerId) {
        JSONArray playerInfos = queryGameService.queryPlayerInfos(serverId, playerId + "", null, null);
        if (playerInfos == null || playerInfos.size() == 0) {
            return null;
        }

        return playerInfos.getJSONObject(0);
    }

}
