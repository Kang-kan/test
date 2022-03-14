package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家离线挂机日志
 */
@ApiModel(value = "玩家离线挂机日志")
public class PlayerOfflineRewardLogDto extends BasePlayerLogDto {

    /**
     * 离线时长，单位秒
     */
    @ApiModelProperty(value = "离线时长，单位秒")
    private long gap;

    /**
     * 奖励内容
     */
    @ApiModelProperty(value = "奖励内容")
    private String bonus;

    public long getGap() {
        return gap;
    }

    public void setGap(long gap) {
        this.gap = gap;
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
