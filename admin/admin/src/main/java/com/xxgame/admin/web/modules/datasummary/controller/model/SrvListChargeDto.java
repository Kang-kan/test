package com.xxgame.admin.web.modules.datasummary.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 各服时间段充值Dto
 */
@ApiModel(value = "各服时间段充值Dto")
public class SrvListChargeDto {

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    @ApiModelProperty(value = "服务器充值Dto，最多显示10个")
    private List<SrvChargeDto> srvChargeDtos;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public List<SrvChargeDto> getSrvChargeDtos() {
        return srvChargeDtos;
    }

    public void setSrvChargeDtos(List<SrvChargeDto> srvChargeDtos) {
        this.srvChargeDtos = srvChargeDtos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
