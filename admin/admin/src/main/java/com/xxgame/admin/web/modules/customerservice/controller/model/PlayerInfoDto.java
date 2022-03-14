package com.xxgame.admin.web.modules.customerservice.controller.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家基本信息
 */
@ApiModel(value = "玩家基本信息")
public class PlayerInfoDto {

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
    protected String platName;

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
     * ip
     */
    @ApiModelProperty(value = "ip")
    private String ip;

    /**
     * 封号到期时间,-1永久
     */
    @ApiModelProperty(value = "封号到期时间,-1永久")
    private long banPlayerTime;

    /**
     * 禁言到期时间,-1永久
     */
    @ApiModelProperty(value = "禁言到期时间,-1永久")
    private long banWordTime;

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
     * 内币
     */
    @ApiModelProperty(value = "内币")
    private long fakeGold;

    /**
     * 总充值金额
     */
    @ApiModelProperty(value = "总充值金额")
    private int totalCharge;

    /**
     * 战力
     */
    @ApiModelProperty(value = "战力")
    private long power;

    /**
     * 是否在线
     */
    @ApiModelProperty(value = "是否在线")
    private boolean online;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    private long registTime;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private long lastLoginTime;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getBanPlayerTime() {
        return banPlayerTime;
    }

    public void setBanPlayerTime(long banPlayerTime) {
        this.banPlayerTime = banPlayerTime;
    }

    public long getBanWordTime() {
        return banWordTime;
    }

    public void setBanWordTime(long banWordTime) {
        this.banWordTime = banWordTime;
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

    public long getFakeGold() {
        return fakeGold;
    }

    public void setFakeGold(long fakeGold) {
        this.fakeGold = fakeGold;
    }

    public int getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(int totalCharge) {
        this.totalCharge = totalCharge;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public long getRegistTime() {
        return registTime;
    }

    public void setRegistTime(long registTime) {
        this.registTime = registTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
