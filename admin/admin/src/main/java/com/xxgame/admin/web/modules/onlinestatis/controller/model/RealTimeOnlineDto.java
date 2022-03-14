package com.xxgame.admin.web.modules.onlinestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实时在线
 */
@ApiModel(value = "实时在线")
public class RealTimeOnlineDto {

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private long dateTime;

    @ApiModelProperty(value = "在线人数")
    private int count;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
