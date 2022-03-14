package com.xxgame.admin.web.modules.operator.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "奖励邮件玩家信息")
public class MailPlayerInfoDto {

    @ApiModelProperty(value = "玩家id")
    private long playerId;

    @ApiModelProperty(value = "玩家名")
    private String playerName;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
