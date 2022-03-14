package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 成就日志
 */
@ApiModel(value = "成就日志")
public class PlayerAchieveLoDto extends BasePlayerLogDto {

    /**
     * 成就ID
     */
    @ApiModelProperty(value = "成就ID")
    private int achieveId;

    /**
     * 奖励
     */
    @ApiModelProperty(value = "奖励")
    private String bonus;

    public int getAchieveId() {
        return achieveId;
    }

    public void setAchieveId(int achieveId) {
        this.achieveId = achieveId;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
