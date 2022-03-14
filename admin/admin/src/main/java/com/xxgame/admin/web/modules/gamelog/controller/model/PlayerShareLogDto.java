package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家分享日志
 */
@ApiModel(value = "玩家分享日志")
public class PlayerShareLogDto extends BasePlayerLogDto {

    /**
     * 操作，0-分享，1-领取
     */
    @ApiModelProperty(value = "操作，0-分享，1-领取")
    private int opValue;

    /**
     * 分享次数
     */
    @ApiModelProperty(value = "分享次数")
    private int shareCount;

    /**
     * 分享奖励次数
     */
    @ApiModelProperty(value = "分享奖励次数")
    private int shareRewardCount;

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getShareRewardCount() {
        return shareRewardCount;
    }

    public void setShareRewardCount(int shareRewardCount) {
        this.shareRewardCount = shareRewardCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
