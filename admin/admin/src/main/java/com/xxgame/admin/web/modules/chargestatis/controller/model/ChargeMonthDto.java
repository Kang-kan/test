package com.xxgame.admin.web.modules.chargestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 月充值汇总
 */
@ApiModel(value = "月充值汇总")
public class ChargeMonthDto {

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 渠道
     */
    @ApiModelProperty(value = "渠道")
    private String channelId;

    /**
     * 充值总额
     */
    @ApiModelProperty(value = "充值总额")
    private long totalAmount;

    /**
     * 充值人数
     */
    @ApiModelProperty(value = "充值人数")
    private int chargePlayer;

    /**
     * 充值次数
     */
    @ApiModelProperty(value = "充值次数")
    private int chargeTimes;

    /**
     * ARPU值
     */
    @ApiModelProperty(value = "ARPU值")
    private int arpu;

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

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getChargePlayer() {
        return chargePlayer;
    }

    public void setChargePlayer(int chargePlayer) {
        this.chargePlayer = chargePlayer;
    }

    public int getChargeTimes() {
        return chargeTimes;
    }

    public void setChargeTimes(int chargeTimes) {
        this.chargeTimes = chargeTimes;
    }

    public int getArpu() {
        return arpu;
    }

    public void setArpu(int arpu) {
        this.arpu = arpu;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
