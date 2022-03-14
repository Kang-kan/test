package com.xxgame.admin.web.modules.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 角色拥有对应的资源权限
 * 
 * @author gil
 *
 */
@Entity(name = "system_role_right")
@Table(indexes = @Index(name = "roleId_resourceId_index", columnList = "roleId, resourceId", unique = true))
public class SystemRoleRight {

	/**
	 * id
	 */
	@Id
    @Column(columnDefinition = "INT NOT NULL COMMENT 'id' AUTO_INCREMENT")
	private int id;

	/**
	 * 角色id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '角色id'")
	private int roleId;

	/**
	 * 资源id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '资源id'")
	private int resourceId;

	/**
	 * 权限，create:增加, delete:删除, update:修改, query:查询
	 */
	@Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '权限'")
	private String auth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}