package com.xxgame.admin.web.modules.chargestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 元宝消耗排行
 */
@ApiModel(value = "元宝消耗排行")
public class CostGoldRankDto {

    /**
     * 玩家id
     */
    @ApiModelProperty(value = "玩家id")
    private long playerId;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String playerName;

    /**
     * 消费次数
     */
    @ApiModelProperty(value = "消费次数")
    private int times;

    /**
     * 消费元宝
     */
    @ApiModelProperty(value = "消费元宝")
    private int amount;

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

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
