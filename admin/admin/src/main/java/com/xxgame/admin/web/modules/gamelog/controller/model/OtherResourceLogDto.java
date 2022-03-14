package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家其它货币资源日志
 */
@ApiModel(value = "玩家其它货币资源日志")
public class OtherResourceLogDto extends BasePlayerLogDto {

    /**
     * 资源类型
     */
    @ApiModelProperty(value = "资源类型，对应item表")
    private int type;

    /**
     * 当前数量
     */
    @ApiModelProperty(value = "当前数量")
    private String value;

    /**
     * 增减数量
     */
    @ApiModelProperty(value = "增减数量")
    private String changeNum;

    /**
     * 变化类型
     */
    @ApiModelProperty(value = "变化类型")
    private String functionType;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
