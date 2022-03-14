package com.xxgame.admin.web.modules.chargestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 首充等级分布
 */
@ApiModel(value = "首充等级分布")
public class FirstChargeLevelDistDto {

    /**
     * 首充等级
     */
    @ApiModelProperty(value = "首充等级")
    private int level;

    /**
     * 首充人数
     */
    @ApiModelProperty(value = "首充人数")
    private int playerCount;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
