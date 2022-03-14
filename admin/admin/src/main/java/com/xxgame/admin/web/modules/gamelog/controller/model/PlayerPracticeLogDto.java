package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 伙伴圣魂守护兽日志
 */
@ApiModel(value = "伙伴圣魂守护兽日志")
public class PlayerPracticeLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id")
    private int heroConfigId;

    /**
     * 操作，0-激活，1-激活子功能，2-提升属性等级，3-增加属性经验，4-技能升级，5-装备，6-装备升阶
     */
    @ApiModelProperty(value = "操作，0-激活，1-激活子功能，2-提升属性等级，3-增加属性经验，4-技能升级，5-装备，6-装备升阶")
    private int opValue;

    /**
     * 配置id
     */
    @ApiModelProperty(value = "配置id")
    private int configId;

    /**
     * 子功能配置id
     */
    @ApiModelProperty(value = "子功能配置id")
    private int childConfigId;

    /**
     * 子功能类型，0-CostItemLevelUp、1-CostItemAddExp、2-CostItemLearnSkill、3-GridConfig
     */
    @ApiModelProperty(value = "子功能类型，0-CostItemLevelUp、1-CostItemAddExp、2-CostItemLearnSkill、3-GridConfig")
    private int childType;

    /** 消耗 */
    @ApiModelProperty(value = "消耗")
    private String cost;

    /**
     * 等级或配置id
     */
    @ApiModelProperty(value = "等级或配置id")
    private int levelId;

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

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public int getChildConfigId() {
        return childConfigId;
    }

    public void setChildConfigId(int childConfigId) {
        this.childConfigId = childConfigId;
    }

    public int getChildType() {
        return childType;
    }

    public void setChildType(int childType) {
        this.childType = childType;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
