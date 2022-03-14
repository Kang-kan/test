package com.xxgame.admin.web.modules.chargestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 付费登录流失
 */
@ApiModel(value = "付费登录流失")
public class ChargeLossDto {

    /**
     * serverId
     */
    @ApiModelProperty(value = "serverId")
    private int serverId;

    /**
     * 渠道
     */
    @ApiModelProperty(value = "渠道")
    private String channelId;

    /**
     * 充值用户数
     */
    @ApiModelProperty(value = "充值用户数")
    private int chargePlayer;

    /**
     * 流失用户数
     */
    @ApiModelProperty(value = "流失用户数")
    private int lossPlayer;

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

    public int getChargePlayer() {
        return chargePlayer;
    }

    public void setChargePlayer(int chargePlayer) {
        this.chargePlayer = chargePlayer;
    }

    public int getLossPlayer() {
        return lossPlayer;
    }

    public void setLossPlayer(int lossPlayer) {
        this.lossPlayer = lossPlayer;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
