package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家混元珠日志
 */
@ApiModel(value = "玩家混元珠日志")
public class PlayerHyzLogDto extends BasePlayerLogDto {

    /**
     * 操作，0-激活，1-杀怪奖励，2-领取额外奖励
     */
    @ApiModelProperty(value = "操作，0-激活，1-杀怪奖励，2-领取额外奖励")
    private int opValue;

    /**
     * boss击杀数
     */
    @ApiModelProperty(value = "boss击杀数")
    private int killCount;

    /**
     * 奖励
     */
    @ApiModelProperty(value = "奖励")
    private String reward;

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
