package com.xxgame.admin.web.modules.dailystatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 充值统计
 */
@ApiModel(value = "充值统计")
public class DailyChargeStatisDto {

    @ApiModelProperty(value = "数值格式时间")
    private int dateTime;

    @ApiModelProperty(value = "渠道")
    private String channelId;

    @ApiModelProperty(value = "服务器id")
    private int serverId;

    @ApiModelProperty(value = "充值人次")
    private int chargeCount;

    @ApiModelProperty(value = "充值人数")
    private int chargePlayer;

    @ApiModelProperty(value = "充值金额")
    private int chargeAmout;

    @ApiModelProperty(value = "当天首充")
    private int todyAmout;

    public int getDateTime() {
        return dateTime;
    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
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

    public int getChargeAmout() {
        return chargeAmout;
    }

    public void setChargeAmout(int chargeAmout) {
        this.chargeAmout = chargeAmout;
    }

    public int getTodyAmout() {
        return todyAmout;
    }

    public void setTodyAmout(int todyAmout) {
        this.todyAmout = todyAmout;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
