package com.xxgame.admin.web.modules.datasummary.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 小时汇总
 */
@ApiModel(value = "小时汇总")
public class HourSummaryDto {

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
     * 年月日时
     */
    @ApiModelProperty(value = "年月日时")
    private long dateTime;

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
     * 登录人数
     */
    @ApiModelProperty(value = "登录人数")
    private int loginCount;

    /**
     * 最高在线
     */
    @ApiModelProperty(value = "最高在线")
    private int maxOnlineCount;

    /**
     * 最低在线
     */
    @ApiModelProperty(value = "最低在线")
    private int minOnlineCount;

    /**
     * 平均在线
     */
    @ApiModelProperty(value = "平均在线")
    private int avgCount;

    /**
     * 充值人次
     */
    @ApiModelProperty(value = "充值人次")
    private int chargeCount;

    /**
     * 充值人数
     */
    @ApiModelProperty(value = "充值人数")
    private int chargePlayer;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private int amount;

    /**
     * 充值元宝
     */
    @ApiModelProperty(value = "充值元宝")
    private int chargeGold;

    /**
     * 消耗元宝
     */
    @ApiModelProperty(value = "消耗元宝")
    private int consumeGold;

    /**
     * 首充人数
     */
    @ApiModelProperty(value = "首充人数")
    private int firstCharge;

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

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getMaxOnlineCount() {
        return maxOnlineCount;
    }

    public void setMaxOnlineCount(int maxOnlineCount) {
        this.maxOnlineCount = maxOnlineCount;
    }

    public int getMinOnlineCount() {
        return minOnlineCount;
    }

    public void setMinOnlineCount(int minOnlineCount) {
        this.minOnlineCount = minOnlineCount;
    }

    public int getAvgCount() {
        return avgCount;
    }

    public void setAvgCount(int avgCount) {
        this.avgCount = avgCount;
    }

    public int getChargeCount() {
        return chargeCount;
    }

    public void setChargeCount(int chargeCount) {
        this.chargeCount = chargeCount;
    }

    public int getChargePlayer() {
        return chargePlayer;
    }

    public void setChargePlayer(int chargePlayer) {
        this.chargePlayer = chargePlayer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getChargeGold() {
        return chargeGold;
    }

    public void setChargeGold(int chargeGold) {
        this.chargeGold = chargeGold;
    }

    public int getConsumeGold() {
        return consumeGold;
    }

    public void setConsumeGold(int consumeGold) {
        this.consumeGold = consumeGold;
    }

    public int getFirstCharge() {
        return firstCharge;
    }

    public void setFirstCharge(int firstCharge) {
        this.firstCharge = firstCharge;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
