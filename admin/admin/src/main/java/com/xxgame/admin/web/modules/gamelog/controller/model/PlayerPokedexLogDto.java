package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家异闻录日志
 */
@ApiModel(value = "玩家异闻录日志")
public class PlayerPokedexLogDto extends BasePlayerLogDto {

    /**
     * 操作，0-激活，1-升级
     */
    @ApiModelProperty(value = "操作，0-激活，1-升级")
    private int opValue;

    /**
     * 异闻录id
     */
    @ApiModelProperty(value = "异闻录id")
    private int pokedexId;

    /**
     * 异闻录等级
     */
    @ApiModelProperty(value = "异闻录等级，默认0显示+1")
    private int pokedexLevel;

    /** 消耗 */
    @ApiModelProperty(value = "消耗")
    private String cost;

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    public int getPokedexId() {
        return pokedexId;
    }

    public void setPokedexId(int pokedexId) {
        this.pokedexId = pokedexId;
    }

    public int getPokedexLevel() {
        return pokedexLevel;
    }

    public void setPokedexLevel(int pokedexLevel) {
        this.pokedexLevel = pokedexLevel;
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
