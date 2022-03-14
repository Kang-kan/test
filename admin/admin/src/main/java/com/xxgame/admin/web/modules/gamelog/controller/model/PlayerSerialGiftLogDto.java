package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家礼包码日志
 */
@ApiModel(value = "玩家礼包码日志")
public class PlayerSerialGiftLogDto extends BasePlayerLogDto {

    /**
     * 礼包码
     */
    @ApiModelProperty(value = "礼包码")
    private String cdKey;

    /**
     * 奖励内容
     */
    @ApiModelProperty(value = "奖励内容")
    private String bonus;

    public String getCdKey() {
        return cdKey;
    }

    public void setCdKey(String cdKey) {
        this.cdKey = cdKey;
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
