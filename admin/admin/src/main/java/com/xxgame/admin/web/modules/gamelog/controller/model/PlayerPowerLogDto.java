package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家战斗力日志
 */
@ApiModel(value = "玩家战斗力日志")
public class PlayerPowerLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id")
    private int configId;

    /**
     * 1-初始化，2-登出
     * {@link} HeroPowerAction
     */
    @ApiModelProperty(value = "1-初始化，2-登出")
    private int action;

    /**
     * 玩家总战斗力
     */
    @ApiModelProperty(value = "玩家总战斗力")
    private long power;

    /**
     * 英雄战斗力
     */
    @ApiModelProperty(value = "英雄战斗力")
    private String heroPower;

    /**
     * 英雄总属性
     */
    @ApiModelProperty(value = "英雄总属性")
    private String propertys;

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    public String getHeroPower() {
        return heroPower;
    }

    public void setHeroPower(String heroPower) {
        this.heroPower = heroPower;
    }

    public String getPropertys() {
        return propertys;
    }

    public void setPropertys(String propertys) {
        this.propertys = propertys;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
