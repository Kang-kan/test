package com.xxgame.admin.web.modules.customerservice.controller.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 排行榜
 */
@ApiModel(value = "排行榜")
public class RankInfoDto {

    /**
     * 玩家id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "玩家id")
    private long id;

    /**
     * 玩家名
     */
    @ApiModelProperty(value = "玩家名")
    private String name;

    /**
     * vip等级
     */
    @ApiModelProperty(value = "vip等级")
    private int vip;

    /**
     * 转生等级
     */
    @ApiModelProperty(value = "转生等级")
    private int rLevel;

    /**
     * 玩家等级
     */
    @ApiModelProperty(value = "玩家等级")
    private int level;

    /**
     * 排行值
     */
    @ApiModelProperty(value = "排行值")
    private long value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getrLevel() {
        return rLevel;
    }

    public void setrLevel(int rLevel) {
        this.rLevel = rLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
