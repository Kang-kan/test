package com.xxgame.admin.web.modules.customerservice.controller.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 聊天记录
 */
@ApiModel(value = "聊天记录")
public class ChatRecordDto {

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "id")
    private long id;

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
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "playerId")
    private long playerId;

    /**
     * 玩家名
     */
    @ApiModelProperty(value = "玩家名")
    private String playerName;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private int level;

    /**
     * vip等级
     */
    @ApiModelProperty(value = "vip等级")
    private int vipLevel;

    /**
     * 聊天频道
     */
    @ApiModelProperty(value = "聊天频道")
    private int chatChannel;

    /**
     * 聊天内容
     */
    @ApiModelProperty(value = "聊天内容")
    private String content;

    /**
     * 聊天接收方
     */
    @ApiModelProperty(value = "聊天接收方-如果有")
    private ChatReceiverDto receiver;

    /**
     * 状态：0-正常，1-禁言，2-封号
     */
    @ApiModelProperty(value = "状态：0-正常，1-禁言，2-封号")
    private int status;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private long time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getChatChannel() {
        return chatChannel;
    }

    public void setChatChannel(int chatChannel) {
        this.chatChannel = chatChannel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatReceiverDto getReceiver() {
        return receiver;
    }

    public void setReceiver(ChatReceiverDto receiver) {
        this.receiver = receiver;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
