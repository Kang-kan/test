package com.xxgame.admin.web.modules.onlinestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "在线时长分布")
public class OnlineTimeDistDto {

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private long dateTime;

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 总人数
     */
    @ApiModelProperty(value = "总人数")
    private int total;

    /**
     * 5分钟
     */
    @ApiModelProperty(value = "5分钟")
    private int minute5;

    /**
     * 15分钟
     */
    @ApiModelProperty(value = "15分钟")
    private int minute15;

    /**
     * 30分钟
     */
    @ApiModelProperty(value = "30分钟")
    private int minute30;

    /**
     * 60分钟
     */
    @ApiModelProperty(value = "60分钟")
    private int minute60;

    /**
     * 1小时
     */
    @ApiModelProperty(value = "1小时")
    private int hour1;

    /**
     * 3小时以上
     */
    @ApiModelProperty(value = "3小时以上")
    private int hour3;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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

    public int getMinute5() {
        return minute5;
    }

    public void setMinute5(int minute5) {
        this.minute5 = minute5;
    }

    public int getMinute15() {
        return minute15;
    }

    public void setMinute15(int minute15) {
        this.minute15 = minute15;
    }

    public int getMinute30() {
        return minute30;
    }

    public void setMinute30(int minute30) {
        this.minute30 = minute30;
    }

    public int getMinute60() {
        return minute60;
    }

    public void setMinute60(int minute60) {
        this.minute60 = minute60;
    }

    public int getHour1() {
        return hour1;
    }

    public void setHour1(int hour1) {
        this.hour1 = hour1;
    }

    public int getHour3() {
        return hour3;
    }

    public void setHour3(int hour3) {
        this.hour3 = hour3;
    }
}
