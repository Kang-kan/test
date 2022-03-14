package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家称号日志
 */
@ApiModel(value = "玩家称号日志")
public class PlayerTitleLogDto extends BasePlayerLogDto {

    /**
     * 称号id
     */
    @ApiModelProperty(value = "称号id")
    private int titleId;

    /**
     * 操作，0-获得，1-移除，2-装备，3-卸下
     */
    @ApiModelProperty(value = "操作，0-获得，1-移除，2-装备，3-卸下")
    private int opValue;

    /**
     * 过期时间，单位秒。0-永不过期
     */
    @ApiModelProperty(value = "过期时间，单位秒。0-永不过期")
    private long expireTime;

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
