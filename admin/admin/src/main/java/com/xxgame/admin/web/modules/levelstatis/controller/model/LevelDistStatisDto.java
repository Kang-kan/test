package com.xxgame.admin.web.modules.levelstatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 等级分布
 */
@ApiModel(value = "等级分布")
public class LevelDistStatisDto {

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private int daily;

    /**
     * 服务器名
     */
    @ApiModelProperty(value = "服务器名")
    private int serverId;

    /**
     * 服务器名
     */
    @ApiModelProperty(value = "服务器名")
    private int total;

    /**
     * 等级人数
     */
    @ApiModelProperty(value = "等级人数")
    private List<LevelCount> levelCounts;

    public int getDaily() {
        return daily;
    }

    public void setDaily(int daily) {
        this.daily = daily;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LevelCount> getLevelCounts() {
        return levelCounts;
    }

    public void setLevelCounts(List<LevelCount> levelCounts) {
        this.levelCounts = levelCounts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
