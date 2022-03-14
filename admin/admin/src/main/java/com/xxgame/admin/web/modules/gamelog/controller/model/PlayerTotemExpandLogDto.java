package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家仙兽灵感日志
 */
@ApiModel(value = "玩家仙兽灵感日志")
public class PlayerTotemExpandLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id")
    private int heroConfigId;

    /**
     * 灵感id
     */
    @ApiModelProperty(value = "灵感id")
    private int expandId;

    /**
     * 灵感等级
     */
    @ApiModelProperty(value = "灵感等级")
    private int expandLevel;

    /**
     * 灵感技能index
     */
    @ApiModelProperty(value = "灵感技能index")
    private int skillIndex;

    /**
     * 灵感次数
     */
    @ApiModelProperty(value = "灵感次数")
    private int expandBase;

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

    public int getExpandId() {
        return expandId;
    }

    public void setExpandId(int expandId) {
        this.expandId = expandId;
    }

    public int getExpandLevel() {
        return expandLevel;
    }

    public void setExpandLevel(int expandLevel) {
        this.expandLevel = expandLevel;
    }

    public int getSkillIndex() {
        return skillIndex;
    }

    public void setSkillIndex(int skillIndex) {
        this.skillIndex = skillIndex;
    }

    public int getExpandBase() {
        return expandBase;
    }

    public void setExpandBase(int expandBase) {
        this.expandBase = expandBase;
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
