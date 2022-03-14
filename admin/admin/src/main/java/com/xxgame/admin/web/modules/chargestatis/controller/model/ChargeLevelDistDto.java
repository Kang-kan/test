package com.xxgame.admin.web.modules.chargestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 付费等级分布
 */
@ApiModel(value = "付费等级分布")
public class ChargeLevelDistDto {

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private int level;

    /**
     * 充值人数
     */
    @ApiModelProperty(value = "充值人数")
    private int playerCount;

    /**
     * 充值总额
     */
    @ApiModelProperty(value = "充值总额")
    private long totalAmount;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
