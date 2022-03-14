package com.xxgame.admin.web.modules.user.controller.model;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 管理后台用户Dto
 * @author gil
 *
 */
public class SystemUserDto {

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
     * 电话号码
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 状态.1:启用,0:禁用
     */
    private int status;
    
    /**
     * 所属角色名
     */
    private Set<String> roleNames;

	/**
	 * 是否系统内置用户
	 */
	private boolean systemUser;
    
    /**
     * 登录IP
     */
    private String loginIp;
    
    /**
     * 登录时间
     */
    private Date loginTime;

	/**
	 * 创建时间
	 */
	private long createTime;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(Set<String> roleNames) {
		this.roleNames = roleNames;
	}

	public boolean isSystemUser() {
		return systemUser;
	}

	public void setSystemUser(boolean systemUser) {
		this.systemUser = systemUser;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
    
}
