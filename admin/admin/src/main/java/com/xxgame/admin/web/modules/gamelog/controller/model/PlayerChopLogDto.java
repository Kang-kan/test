package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家神器日志
 */
@ApiModel(value = "玩家神器日志")
public class PlayerChopLogDto extends BasePlayerLogDto {

    /**
     * 神器id
     */
    @ApiModelProperty(value = "神器id")
    private int chopId;

    /**
     * 碎片id
     */
    @ApiModelProperty(value = "碎片id")
    private int childId;

    public int getChopId() {
        return chopId;
    }

    public void setChopId(int chopId) {
        this.chopId = chopId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
