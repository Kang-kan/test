package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家心法技能日志
 */
@ApiModel(value = "玩家心法技能日志")
public class PlayerSkillBookLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id")
    private int heroConfigId;

    /**
     * 技能格下标
     */
    @ApiModelProperty(value = "技能格下标")
    private int skillIndex;

    /**
     * 操作，0-学习，1-升级，2-分解，3-合成
     */
    @ApiModelProperty(value = "操作，0-学习，1-升级，2-分解，3-合成")
    private int opValue;

    /**
     * 技能id
     */
    @ApiModelProperty(value = "技能id")
    private int skillId;

    /**
     * 技能等级
     */
    @ApiModelProperty(value = "技能等级")
    private int skillLevel;

    /**
     * 消耗
     */
    @ApiModelProperty(value = "消耗")
    private String cost;

    /**
     * 奖励
     */
    @ApiModelProperty(value = "奖励")
    private String reward;

    public int getHeroConfigId() {
        return heroConfigId;
    }

    public void setHeroConfigId(int heroConfigId) {
        this.heroConfigId = heroConfigId;
    }

    public int getSkillIndex() {
        return skillIndex;
    }

    public void setSkillIndex(int skillIndex) {
        this.skillIndex = skillIndex;
    }

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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
