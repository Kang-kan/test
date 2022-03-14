package com.xxgame.admin.web.modules.auth.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 管理后台角色
 * 
 * @author gil
 *
 */
@ApiModel(value = "管理后台角色")
public class SystemRoleDto {

	/**
	 * id
	 */
	@ApiModelProperty(value = "角色id")
	private int id;

	/**
	 * 角色名
	 */
	@ApiModelProperty(value = "角色名")
	private String roleName;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 系统内置角色
	 */
	@ApiModelProperty(value = "系统内置角色")
	private boolean systemRole;

	/**
	 * 操作员id
	 */
	@ApiModelProperty(value = "操作员")
	private String operator;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private long updateTime;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private long createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isSystemRole() {
		return systemRole;
	}

	public void setSystemRole(boolean systemRole) {
		this.systemRole = systemRole;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
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

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !this.getClass().isAssignableFrom(obj.getClass())) {
			return false;
		}
		SystemRoleDto objDto = (SystemRoleDto) obj;

		return new EqualsBuilder().append(id, objDto.getId()).append(roleName, objDto.getRoleName()).isEquals();
	}

}