package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家星宿日志
 */
@ApiModel(value = "玩家星宿日志")
public class PlayerMeridianLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id")
    private int heroConfigId;

    /**
     * 操作，0-升级，1-升阶
     */
    @ApiModelProperty(value = "操作，0-升级，1-升阶")
    private int opValue;

    /**
     * 经脉等级
     */
    @ApiModelProperty(value = "经脉等级")
    private int meridianLevel;

    /**
     * 经脉阶
     */
    @ApiModelProperty(value = "经脉阶")
    private int meridianStep;

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

    public int getMeridianLevel() {
        return meridianLevel;
    }

    public void setMeridianLevel(int meridianLevel) {
        this.meridianLevel = meridianLevel;
    }

    public int getMeridianStep() {
        return meridianStep;
    }

    public void setMeridianStep(int meridianStep) {
        this.meridianStep = meridianStep;
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
