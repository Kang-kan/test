package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 日志搜索请求对象
 */
@ApiModel(value = "日志搜索请求对象")
public class LogSearchRequest {

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    protected String account;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private String channelId;

    /**
     * 玩家id
     */
    @ApiModelProperty(value = "玩家id")
    private String playerId;

    /**
     * 玩家名
     */
    @ApiModelProperty(value = "玩家名")
    private String playerName;

    /**
     * pageNo
     */
    @ApiModelProperty(value = "pageNo")
    private int pageNo;

    /**
     * pageSize
     */
    @ApiModelProperty(value = "pageSize")
    private int pageSize;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
