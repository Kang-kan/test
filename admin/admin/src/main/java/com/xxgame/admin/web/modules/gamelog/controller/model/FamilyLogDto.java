package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 仙盟日志
 */
@ApiModel(value = "仙盟日志")
public class FamilyLogDto {

    /**
     * id
     */
    @ApiModelProperty(value = "日志id，不用显示")
    private String id;

    /** 工会编号 */
    @ApiModelProperty(value = "工会编号")
    private String familyId;

    /** 工会名称 */
    @ApiModelProperty(value = "工会名称")
    private String name;

    /** 会长名字 */
    @ApiModelProperty(value = "会长名字")
    private String leaderName;

    /** 工会经验 */
    @ApiModelProperty(value = "工会经验")
    private int exp;

    /** 工会等级 */
    @ApiModelProperty(value = "工会等级")
    private int level;

    /**
     * 操作类型，0-创建，1-加入，2-离开，3-任命，4-职位变化，5-转让，6-弹劾，7-踢人，8-解散，9-捐献，10-批准加入
     */
    @ApiModelProperty(value = "操作类型，0-创建，1-加入，2-离开，3-任命，4-职位变化，5-转让，6-弹劾，7-踢人，8-解散，9-捐献，10-批准加入")
    private int opValue;

    /** 操作人id */
    @ApiModelProperty(value = "操作人id")
    private String opPlayerId;

    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    private String opPlayerName;

    /** 操作人职位，1-会长，2-副会长，3-长老，4-护法，5-会员，6-非会员 */
    @ApiModelProperty(value = "操作人职位，1-会长，2-副会长，3-长老，4-护法，5-会员，6-非会员")
    private int opPos;

    /** 被操作人id */
    @ApiModelProperty(value = "被操作人id")
    private String targetPlayerId;

    /** 被操作人 */
    @ApiModelProperty(value = "被操作人")
    private String targetPlayerName;

    /** 被操作人职位 */
    @ApiModelProperty(value = "被操作人职位")
    private int targetPos;

    /** 时间 */
    @ApiModelProperty(value = "时间")
    private long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    public String getOpPlayerId() {
        return opPlayerId;
    }

    public void setOpPlayerId(String opPlayerId) {
        this.opPlayerId = opPlayerId;
    }

    public String getOpPlayerName() {
        return opPlayerName;
    }

    public void setOpPlayerName(String opPlayerName) {
        this.opPlayerName = opPlayerName;
    }

    public int getOpPos() {
        return opPos;
    }

    public void setOpPos(int opPos) {
        this.opPos = opPos;
    }

    public String getTargetPlayerId() {
        return targetPlayerId;
    }

    public void setTargetPlayerId(String targetPlayerId) {
        this.targetPlayerId = targetPlayerId;
    }

    public String getTargetPlayerName() {
        return targetPlayerName;
    }

    public void setTargetPlayerName(String targetPlayerName) {
        this.targetPlayerName = targetPlayerName;
    }

    public int getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(int targetPos) {
        this.targetPos = targetPos;
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
