package com.xxgame.admin.web.modules.levelstatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 等级流失
 */
@ApiModel(value = "等级流失")
public class LevelLossStatisDto {

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级，前端按等级排序")
    private int level;

    /**
     * 玩家人数
     */
    @ApiModelProperty(value = "玩家人数")
    private int count;

    /**
     * 流失人数
     */
    @ApiModelProperty(value = "流失人数")
    private int lossCount;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLossCount() {
        return lossCount;
    }

    public void setLossCount(int lossCount) {
        this.lossCount = lossCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
