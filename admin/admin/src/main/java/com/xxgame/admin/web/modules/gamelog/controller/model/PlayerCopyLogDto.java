package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家副本日志
 */
@ApiModel(value = "玩家副本日志")
public class PlayerCopyLogDto extends BasePlayerLogDto {

    /**
     * 副本类型，0-材料副本，1-经验副本，2-仙缘副本
     */
    @ApiModelProperty(value = "副本类型，0-材料副本，1-经验副本，2-仙缘副本")
    private int copyType;

    /**
     * 副本id
     */
    @ApiModelProperty(value = "副本id")
    private int copyId;

    /**
     * 操作，0-进入，1-离开，2-领取奖励，3-扫荡，4-掉落
     */
    @ApiModelProperty(value = "操作，0-进入，1-离开，2-领取奖励，3-扫荡，4-掉落")
    private int opValue;

    /**
     * 奖励内容
     */
    @ApiModelProperty(value = "奖励内容")
    private String bonus;

    /**
     * 额外信息
     */
    @ApiModelProperty(value = "额外信息")
    private String ext;

    public int getCopyType() {
        return copyType;
    }

    public void setCopyType(int copyType) {
        this.copyType = copyType;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
