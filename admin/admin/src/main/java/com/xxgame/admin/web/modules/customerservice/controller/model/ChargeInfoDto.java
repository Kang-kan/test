package com.xxgame.admin.web.modules.customerservice.controller.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 玩家充值排名信息
 */
@ApiModel(value = "玩家充值排名信息")
public class ChargeInfoDto {

    /**
     * 玩家id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "玩家id")
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
     * 平台
     */
    @ApiModelProperty(value = "平台")
    private String platName;

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
     * 金币
     */
    @ApiModelProperty(value = "金币")
    private long money;

    /**
     * 仙晶
     */
    @ApiModelProperty(value = "仙晶")
    private long gold;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private long rmb;

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

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
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

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getRmb() {
        return rmb;
    }

    public void setRmb(long rmb) {
        this.rmb = rmb;
    }
}
