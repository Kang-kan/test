package com.xxgame.admin.web.modules.user.controller.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 简单的用户Dto
 * @author gil
 *
 */
public class SimpleSystemUserDto {

	/**
	 * id
	 */
    private String id;
	
    /**
     * 账号
     */
    private String username;
    
    /**
     * 姓名
     */
    private String name;

    /**
     * 状态.1:启用,0:禁用
     */
    private int status;
    
	/**
	 * 是否系统内置用户
	 */
	private boolean systemUser;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isSystemUser() {
		return systemUser;
	}

	public void setSystemUser(boolean systemUser) {
		this.systemUser = systemUser;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
    
}
