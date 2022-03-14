package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 充值定单Dto
 */
@ApiModel(value = "充值定单Dto")
public class ChargeOrderDto {

    /**
     * id
     */
    @ApiModelProperty(value = "日志id，不用显示")
    private String id;

     /**
     * 账号
     */
     @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 玩家id
     */
    @ApiModelProperty(value = "玩家id")
    private String playerId;

    /**
     * 玩家名
     */
    @ApiModelProperty(value = "玩家名")
    private String playerName;

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
     * 平台
     */
    @ApiModelProperty(value = "平台")
    private String platName;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderId;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private int rmb;

    /**
     * 元宝
     */
    @ApiModelProperty(value = "元宝")
    private int gold;

    /**
     * 商品Id
     */
    @ApiModelProperty(value = "商品Id")
    private int goodsId;

    /**
     * 第一次充值
     */
    @ApiModelProperty(value = "第一次充值")
    private boolean first;

    /**
     * 平台支付定单号
     */
    @ApiModelProperty(value = "平台支付定单号")
    private String transactionId;

    /**
     * 下单时间
     */
    @ApiModelProperty(value = "下单时间")
    private long time;

    /**
     * 到账时间
     */
    @ApiModelProperty(value = "到账时间")
    private long arrivalTime;

    /**
     * 通知发货时间
     */
    @ApiModelProperty(value = "通知发货时间")
    private long adviceTime;

    /**
     * 通知游戏服发货返回的结果
     */
    @ApiModelProperty(value = "通知游戏服发货返回的结果，0000-订单发货成功，0001-订单不存在，0002-商品金额不符，0003-签名不匹配，0004-订单已发货")
    private String gameResultCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

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

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getRmb() {
        return rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(long adviceTime) {
        this.adviceTime = adviceTime;
    }

    public String getGameResultCode() {
        return gameResultCode;
    }

    public void setGameResultCode(String gameResultCode) {
        this.gameResultCode = gameResultCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
