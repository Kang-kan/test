package com.xxgame.admin.web.modules.user.controller.model;

import com.xxgame.admin.web.modules.auth.controller.model.SystemMenuDto;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Set;

/**
 * 访问权限dto
 */
public class AccessRightDto {

    /**
     * 所属角色
     */
    private Set<String> roles;

    /**
     * 菜单id
     */
    private List<SystemMenuDto> systemMenuDto;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public List<SystemMenuDto> getSystemMenuDto() {
        return systemMenuDto;
    }

    public void setSystemMenuDto(List<SystemMenuDto> systemMenuDto) {
        this.systemMenuDto = systemMenuDto;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
