package com.xxgame.admin.web.modules.gamelog.controller.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * boss击杀日志
 */
@ApiModel(value = "boss击杀日志")
public class BossDeadLogDto {

    /**
     * id
     */
    @ApiModelProperty(value = "日志id，不用显示")
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    /**
     * 玩法，1-活动boss，2-奇遇boss，3-召唤boss，4-印记塔，5-仙盟试炼，6-天域争霸，7-帝君神殿，8-帝君，9-帝君塔，10-秘境boss，
     * 		11-boss之家， 12-仙戒boss，13-主线boss，14-关卡协助，  15-结婚场景，16-密境boss，17-守卫剑阁，18-世界boss，19-个人boss，20-万妖塔，
     * 		21-翎羽boss， 22-仙缘副本，23-历练之地，24-仙魔战场，25-九重天，26-仙界魔神，27-荒古禁地
     */
    @ApiModelProperty(value = "1-活动boss，2-奇遇boss，3-召唤boss，4-印记塔，5-仙盟试炼，6-天域争霸，7-帝君神殿，8-帝君，9-帝君塔，10-秘境boss，" +
            "11-boss之家， 12-仙戒boss，13-主线boss，14-关卡协助，  15-结婚场景，16-密境boss，17-守卫剑阁，18-世界boss，19-个人boss，20-万妖塔，" +
            "21-翎羽boss， 22-仙缘副本，23-历练之地，24-仙魔战场，25-九重天，26-仙界魔神，27-荒古禁地")
    private int playType;

    /**
     * 战斗id
     */
    @ApiModelProperty(value = "战斗id")
    @JsonSerialize(using = ToStringSerializer.class)
    private long battleId;

    /**
     * 战斗服务器id
     */
    @ApiModelProperty(value = "战斗服务器id")
    private int battleServer;

    /**
     * boss配置id
     */
    @ApiModelProperty(value = "boss配置id")
    private int bossId;

    /**
     * boss名
     */
    @ApiModelProperty(value = "boss名")
    private String bossName;

    /**
     * 归属类型，0-个人，1-公会
     */
    @ApiModelProperty(value = "归属类型，0-个人，1-公会")
    private int ownerType;

    /**
     * 归属者id
     */
    @ApiModelProperty(value = "归属者id")
    private long ownerId;

    /**
     * 归属者名
     */
    @ApiModelProperty(value = "归属者名")
    private String ownerName;

    /**
     * 奖励类型，0-参与，1-归属，2-排名，3-失败，4-破盾
     */
    @ApiModelProperty(value = "奖励类型，0-参与，1-归属，2-排名，3-失败，4-破盾")
    private int rewardType;

    /**
     * 奖励内容
     */
    @ApiModelProperty(value = "奖励内容")
    private String reward;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private long time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public long getBattleId() {
        return battleId;
    }

    public void setBattleId(long battleId) {
        this.battleId = battleId;
    }

    public int getBattleServer() {
        return battleServer;
    }

    public void setBattleServer(int battleServer) {
        this.battleServer = battleServer;
    }

    public int getBossId() {
        return bossId;
    }

    public void setBossId(int bossId) {
        this.bossId = bossId;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
