package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家时装日志
 */
@ApiModel(value = "玩家时装日志")
public class PlayerFashionLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id，1-剑修 2-法修 3-灵修")
    private int heroConfigId;

    /**
     * 操作，0-激活，1-删除，2-穿戴，3-卸下
     */
    @ApiModelProperty(value = "操作，0-激活，1-删除，2-穿戴，3-卸下")
    private int opValue;

    /**
     * 时装id
     */
    @ApiModelProperty(value = "时装id")
    private int fashionId;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private int fashionLevel;

    /**
     * 到期时间 0-永久
     */
    @ApiModelProperty(value = "到期时间 0-永久")
    private long overTime;

    /**
     * 是否穿戴 1 穿 ，0未穿
     */
    @ApiModelProperty(value = "是否穿戴 1 穿 ，0未穿")
    private int weared;

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

    public int getFashionId() {
        return fashionId;
    }

    public void setFashionId(int fashionId) {
        this.fashionId = fashionId;
    }

    public int getFashionLevel() {
        return fashionLevel;
    }

    public void setFashionLevel(int fashionLevel) {
        this.fashionLevel = fashionLevel;
    }

    public long getOverTime() {
        return overTime;
    }

    public void setOverTime(long overTime) {
        this.overTime = overTime;
    }

    public int getWeared() {
        return weared;
    }

    public void setWeared(int weared) {
        this.weared = weared;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
