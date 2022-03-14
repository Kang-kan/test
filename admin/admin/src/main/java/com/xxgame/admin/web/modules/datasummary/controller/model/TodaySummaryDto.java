package com.xxgame.admin.web.modules.datasummary.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 当日汇总
 */
@ApiModel(value = "当日汇总")
public class TodaySummaryDto {

    /**
     * serverId
     */
    @ApiModelProperty(value = "serverId")
    private int serverId;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private String channelId;

    /**
     * 当前在线
     */
    @ApiModelProperty(value = "当前在线")
    private int onlineCount;

    /**
     * 新增账号数
     */
    @ApiModelProperty(value = "新增账号数")
    private int createAccount;

    /**
     * 创建角色数
     */
    @ApiModelProperty(value = "创建角色数")
    private int createPlayer;

    /**
     * 历史创建角色数
     */
    @ApiModelProperty(value = "历史创建角色数")
    private int pastCreatePlayer;

    /**
     * 当日充值人次
     */
    @ApiModelProperty(value = "当日充值人次")
    private int todayChargeCount;

    /**
     * 当日充值人数
     */
    @ApiModelProperty(value = "当日充值人数")
    private int todayChargePlayer;

    /**
     * 当日充值金额
     */
    @ApiModelProperty(value = "当日充值金额")
    private int todayChargeAmount;

    /**
     * 当日首充人数
     */
    @ApiModelProperty(value = "当日首充人数")
    private int todayFirstCharge;

    /**
     * 当日ARPU
     */
    @ApiModelProperty(value = "当日ARPU")
    private float todayArpu;

    /**
     * 历史充值人数
     */
    @ApiModelProperty(value = "历史充值人数")
    private int pastChargePlayer;

    /**
     * 历史充值人次
     */
    @ApiModelProperty(value = "历史充值人次")
    private int pastChargeCount;

    /**
     * 历史充值人数
     */
    @ApiModelProperty(value = "历史充值人数")
    private int pastChargeAmount;

    /**
     * 历史ARPU
     */
    @ApiModelProperty(value = "历史ARPU")
    private float pastArpu;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(int createAccount) {
        this.createAccount = createAccount;
    }

    public int getCreatePlayer() {
        return createPlayer;
    }

    public void setCreatePlayer(int createPlayer) {
        this.createPlayer = createPlayer;
    }

    public int getPastCreatePlayer() {
        return pastCreatePlayer;
    }

    public void setPastCreatePlayer(int pastCreatePlayer) {
        this.pastCreatePlayer = pastCreatePlayer;
    }

    public int getTodayChargeCount() {
        return todayChargeCount;
    }

    public void setTodayChargeCount(int todayChargeCount) {
        this.todayChargeCount = todayChargeCount;
    }

    public int getTodayChargePlayer() {
        return todayChargePlayer;
    }

    public void setTodayChargePlayer(int todayChargePlayer) {
        this.todayChargePlayer = todayChargePlayer;
    }

    public int getTodayChargeAmount() {
        return todayChargeAmount;
    }

    public void setTodayChargeAmount(int todayChargeAmount) {
        this.todayChargeAmount = todayChargeAmount;
    }

    public int getTodayFirstCharge() {
        return todayFirstCharge;
    }

    public void setTodayFirstCharge(int todayFirstCharge) {
        this.todayFirstCharge = todayFirstCharge;
    }

    public float getTodayArpu() {
        return todayArpu;
    }

    public void setTodayArpu(float todayArpu) {
        this.todayArpu = todayArpu;
    }

    public int getPastChargePlayer() {
        return pastChargePlayer;
    }

    public void setPastChargePlayer(int pastChargePlayer) {
        this.pastChargePlayer = pastChargePlayer;
    }

    public int getPastChargeCount() {
        return pastChargeCount;
    }

    public void setPastChargeCount(int pastChargeCount) {
        this.pastChargeCount = pastChargeCount;
    }

    public int getPastChargeAmount() {
        return pastChargeAmount;
    }

    public void setPastChargeAmount(int pastChargeAmount) {
        this.pastChargeAmount = pastChargeAmount;
    }

    public float getPastArpu() {
        return pastArpu;
    }

    public void setPastArpu(float pastArpu) {
        this.pastArpu = pastArpu;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
