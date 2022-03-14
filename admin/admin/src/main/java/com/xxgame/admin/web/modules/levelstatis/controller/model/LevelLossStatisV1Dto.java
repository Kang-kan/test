package com.xxgame.admin.web.modules.levelstatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 等级流失V1
 */
@ApiModel(value = "等级流失V1")
public class LevelLossStatisV1Dto {

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private int daily;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private int level;

    /**
     * 玩家总人数
     */
    @ApiModelProperty(value = "玩家总人数")
    private int count;

    /**
     * 当前等级玩家人数
     */
    @ApiModelProperty(value = "当前等级玩家人数")
    private int levelCount;

    /**
     * 24小时流失人数
     */
    @ApiModelProperty(value = "24小时流失人数")
    private int loss24;

    /**
     * 3天流失人数
     */
    @ApiModelProperty(value = "3天流失人数")
    private int loss72;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getDaily() {
        return daily;
    }

    public void setDaily(int daily) {
        this.daily = daily;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(int levelCount) {
        this.levelCount = levelCount;
    }

    public int getLoss24() {
        return loss24;
    }

    public void setLoss24(int loss24) {
        this.loss24 = loss24;
    }

    public int getLoss72() {
        return loss72;
    }

    public void setLoss72(int loss72) {
        this.loss72 = loss72;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
