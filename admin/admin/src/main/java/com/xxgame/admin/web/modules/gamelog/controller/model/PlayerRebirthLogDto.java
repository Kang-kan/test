package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家时装日志
 */
@ApiModel(value = "玩家时装日志")
public class PlayerRebirthLogDto extends BasePlayerLogDto {

    /**
     * 转生前等级
     */
    @ApiModelProperty(value = "转生前等级")
    private int beforeLevel;

    /**
     * 消耗
     */
    @ApiModelProperty(value = "消耗")
    private String cost;

    public int getBeforeLevel() {
        return beforeLevel;
    }

    public void setBeforeLevel(int beforeLevel) {
        this.beforeLevel = beforeLevel;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
