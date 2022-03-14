package com.xxgame.admin.web.modules.gameserver.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 简单的游戏服配置DTO
 */
@ApiModel(value = "简单的游戏服配置DTO")
public class SimpleGameServerDto {

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int id;

    /**
     * 服务器名
     */
    @ApiModelProperty(value = "服务器名")
    private String name;

    /**
     * 开服时间
     */
    @ApiModelProperty(value = "开服时间")
    private long openTime;

    /**
     * 合服时间
     */
    @ApiModelProperty(value = "合服时间")
    private long mergeTime;

    /**
     * 合服后服务器id
     */
    @ApiModelProperty(value = "合服后服务器id")
    private int mergeSrvId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getMergeTime() {
        return mergeTime;
    }

    public void setMergeTime(long mergeTime) {
        this.mergeTime = mergeTime;
    }

    public int getMergeSrvId() {
        return mergeSrvId;
    }

    public void setMergeSrvId(int mergeSrvId) {
        this.mergeSrvId = mergeSrvId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
