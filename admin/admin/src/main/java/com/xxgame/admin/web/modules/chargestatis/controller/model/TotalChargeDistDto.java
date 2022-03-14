package com.xxgame.admin.web.modules.chargestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 累计充值分布
 */
@ApiModel(value = "累计充值分布")
public class TotalChargeDistDto {

    /**
     * 累计金额
     */
    @ApiModelProperty(value = "累计金额")
    private int amount;

    /**
     * 充值人数
     */
    @ApiModelProperty(value = "充值人数")
    private int playerCount;

    /**
     * 总充值金额
     */
    @ApiModelProperty(value = "总充值金额")
    private long totalAmount;

    /**
     * 总充值人数
     */
    @ApiModelProperty(value = "总充值人数")
    private int totalPlayer;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalPlayer() {
        return totalPlayer;
    }

    public void setTotalPlayer(int totalPlayer) {
        this.totalPlayer = totalPlayer;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
