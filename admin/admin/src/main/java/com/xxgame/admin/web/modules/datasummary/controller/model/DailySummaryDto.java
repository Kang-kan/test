package com.xxgame.admin.web.modules.datasummary.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 每日汇总
 */
@ApiModel(value = "每日汇总")
public class DailySummaryDto {

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private String channelId;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private int daily;

    /**
     * 创建角色数
     */
    @ApiModelProperty(value = "创建角色数")
    private int createPlayer;

    /**
     * 登录人数
     */
    @ApiModelProperty(value = "登录人数")
    private int loginPlayer;

    /**
     * 最高在线
     */
    @ApiModelProperty(value = "最高在线")
    private int maxOnline;

    /**
     * 最低在线
     */
    @ApiModelProperty(value = "最低在线")
    private int minOnline;

    /**
     * 平均在线
     */
    @ApiModelProperty(value = "平均在线")
    private int avgOnline;

    /**
     * 充值人次
     */
    @ApiModelProperty(value = "充值人次")
    private int chargeCount;

    /**
     * 充值人数
     */
    @ApiModelProperty(value = "充值人数")
    private int chargePlayer;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private int chargeAmount;

    /**
     * arpu
     */
    @ApiModelProperty(value = "arpu")
    private int arpu;

    /**
     * arppu
     */
    @ApiModelProperty(value = "arppu")
    private int arppu;

    /**
     * 当日付费渗透率
     */
    @ApiModelProperty(value = "当日付费渗透率")
    private float chargeRate;

    /**
     * 新增用户充值点击次数
     */
    @ApiModelProperty(value = "新增用户充值点击次数")
    private int newChargeClickCount;

    /**
     * 新增用户充值点击人数
     */
    @ApiModelProperty(value = "新增用户充值点击人数")
    private int newChargeClickPlayer;

    /**
     * 新增用户点击付费率
     */
    @ApiModelProperty(value = "新增用户点击付费率")
    private float newChargeClickRate;

    /**
     * 新增用户付费人数
     */
    @ApiModelProperty(value = "新增用户付费人数")
    private int newChargePlayer;

    /**
     * 新增用户充值金额
     */
    @ApiModelProperty(value = "新增用户充值金额")
    private int newChargeAmount;

    /**
     * 新增付费率
     */
    @ApiModelProperty(value = "新增付费率")
    private float newChargeRate;

    /**
     * 新增用户ARPU
     */
    @ApiModelProperty(value = "新增用户ARPU")
    private float newArpu;

    /**
     * 老用户付费人数
     */
    @ApiModelProperty(value = "老用户付费人数")
    private int oldChargePlayer;

    /**
     * 老用户充值金额
     */
    @ApiModelProperty(value = "老用户充值金额")
    private int oldChargeAmount;

    /**
     * 老用户付费率
     */
    @ApiModelProperty(value = "老用户付费率")
    private float oldChargeRate;

    /**
     * 老用户ARPU
     */
    @ApiModelProperty(value = "老用户ARPU")
    private float oldArpu;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getDaily() {
        return daily;
    }

    public void setDaily(int daily) {
        this.daily = daily;
    }

    public int getCreatePlayer() {
        return createPlayer;
    }

    public void setCreatePlayer(int createPlayer) {
        this.createPlayer = createPlayer;
    }

    public int getLoginPlayer() {
        return loginPlayer;
    }

    public void setLoginPlayer(int loginPlayer) {
        this.loginPlayer = loginPlayer;
    }

    public int getMaxOnline() {
        return maxOnline;
    }

    public void setMaxOnline(int maxOnline) {
        this.maxOnline = maxOnline;
    }

    public int getMinOnline() {
        return minOnline;
    }

    public void setMinOnline(int minOnline) {
        this.minOnline = minOnline;
    }

    public int getAvgOnline() {
        return avgOnline;
    }

    public void setAvgOnline(int avgOnline) {
        this.avgOnline = avgOnline;
    }

    public int getChargeCount() {
        return chargeCount;
    }

    public void setChargeCount(int chargeCount) {
        this.chargeCount = chargeCount;
    }

    public int getChargePlayer() {
        return chargePlayer;
    }

    public void setChargePlayer(int chargePlayer) {
        this.chargePlayer = chargePlayer;
    }

    public int getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(int chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public int getArpu() {
        return arpu;
    }

    public void setArpu(int arpu) {
        this.arpu = arpu;
    }

    public int getArppu() {
        return arppu;
    }

    public void setArppu(int arppu) {
        this.arppu = arppu;
    }

    public float getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(float chargeRate) {
        this.chargeRate = chargeRate;
    }

    public int getNewChargeClickCount() {
        return newChargeClickCount;
    }

    public void setNewChargeClickCount(int newChargeClickCount) {
        this.newChargeClickCount = newChargeClickCount;
    }

    public int getNewChargeClickPlayer() {
        return newChargeClickPlayer;
    }

    public void setNewChargeClickPlayer(int newChargeClickPlayer) {
        this.newChargeClickPlayer = newChargeClickPlayer;
    }

    public float getNewChargeClickRate() {
        return newChargeClickRate;
    }

    public void setNewChargeClickRate(float newChargeClickRate) {
        this.newChargeClickRate = newChargeClickRate;
    }

    public int getNewChargePlayer() {
        return newChargePlayer;
    }

    public void setNewChargePlayer(int newChargePlayer) {
        this.newChargePlayer = newChargePlayer;
    }

    public int getNewChargeAmount() {
        return newChargeAmount;
    }

    public void setNewChargeAmount(int newChargeAmount) {
        this.newChargeAmount = newChargeAmount;
    }

    public float getNewChargeRate() {
        return newChargeRate;
    }

    public void setNewChargeRate(float newChargeRate) {
        this.newChargeRate = newChargeRate;
    }

    public float getNewArpu() {
        return newArpu;
    }

    public void setNewArpu(float newArpu) {
        this.newArpu = newArpu;
    }

    public int getOldChargePlayer() {
        return oldChargePlayer;
    }

    public void setOldChargePlayer(int oldChargePlayer) {
        this.oldChargePlayer = oldChargePlayer;
    }

    public int getOldChargeAmount() {
        return oldChargeAmount;
    }

    public void setOldChargeAmount(int oldChargeAmount) {
        this.oldChargeAmount = oldChargeAmount;
    }

    public float getOldChargeRate() {
        return oldChargeRate;
    }

    public void setOldChargeRate(float oldChargeRate) {
        this.oldChargeRate = oldChargeRate;
    }

    public float getOldArpu() {
        return oldArpu;
    }

    public void setOldArpu(float oldArpu) {
        this.oldArpu = oldArpu;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
