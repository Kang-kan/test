package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家登录日志
 */
@ApiModel(value = "玩家登录日志")
public class PlayerLoginLogDto extends BasePlayerLogDto {

    /**
     * 上次登录IP
     */
    @ApiModelProperty(value = "上次登录IP")
    private String lastIP;

    /**
     * 战斗力
     */
    @ApiModelProperty(value = "战斗力")
    private long power;

    public String getLastIP() {
        return lastIP;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
