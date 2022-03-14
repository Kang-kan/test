package com.xxgame.admin.web.modules.customerservice.controller.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 仙盟信息
 */
@ApiModel(value = "仙盟信息")
public class FamilyInfoDto {

    /**
     * 仙盟id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "仙盟id")
    private long id;

    /**
     * 仙盟名
     */
    @ApiModelProperty(value = "仙盟名")
    private String name;

    /**
     * 会长名字
     */
    @ApiModelProperty(value = "会长名字")
    private String leaderName;

    /**
     * 公告内容
     */
    @ApiModelProperty(value = "公告内容")
    private String notice;

    /**
     * 工会经验
     */
    @ApiModelProperty(value = "工会经验")
    private int exp;

    /**
     * 工会等级
     */
    @ApiModelProperty(value = "工会等级")
    private int level;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
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
}
