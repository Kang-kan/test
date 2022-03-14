package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家仙兽蕴养日志
 */
@ApiModel(value = "玩家仙兽蕴养日志")
public class PlayerTotemLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id")
    private int heroConfigId;

    /**
     * 操作，0-激活，1-蕴养
     */
    @ApiModelProperty(value = "操作，0-激活，1-蕴养")
    private int opValue;

    /**
     * 仙兽id
     */
    @ApiModelProperty(value = "仙兽id")
    private int totemId;

    /**
     * 仙兽等级
     */
    @ApiModelProperty(value = "仙兽等级")
    private int totemLevel;

    /**
     * 仙兽子等级
     */
    @ApiModelProperty(value = "仙兽子等级")
    private int totemChildLevel;

    /**
     * 仙兽经验
     */
    @ApiModelProperty(value = "仙兽经验")
    private int exp;

    /**
     * 消耗
     */
    @ApiModelProperty(value = "消耗")
    private String cost;

    public int getHeroConfigId() {
        return heroConfigId;
    }

    public void setHeroConfigId(int heroConfigId) {
        this.heroConfigId = heroConfigId;
    }

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    public int getTotemId() {
        return totemId;
    }

    public void setTotemId(int totemId) {
        this.totemId = totemId;
    }

    public int getTotemLevel() {
        return totemLevel;
    }

    public void setTotemLevel(int totemLevel) {
        this.totemLevel = totemLevel;
    }

    public int getTotemChildLevel() {
        return totemChildLevel;
    }

    public void setTotemChildLevel(int totemChildLevel) {
        this.totemChildLevel = totemChildLevel;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
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
