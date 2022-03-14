package com.xxgame.admin.web.modules.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 管理后台角色
 * @author gil
 *
 */
@Entity(name = "system_role")
@Table(indexes = @Index(name = "roleName_index", columnList = "roleName"))
public class SystemRole {
	
	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "INT NOT NULL COMMENT 'id' AUTO_INCREMENT")
    private int id;
	
    /**
     * 角色名
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '角色名'")
    private String roleName;
    
    /**
     * 备注
     */
    @Column(columnDefinition = "VARCHAR(128) NOT NULL COMMENT '备注'")
    private String remark;

    /**
     * 系统内置角色
     */
    @Column(columnDefinition = "TINYINT	NOT NULL DEFAULT '0' COMMENT '系统内置角色'")
    private boolean systemRole;
    
    /**
     * 操作员id
     */
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '操作员id'")
    private long operator;
    
    /**
     * 更新时间
     */
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '更新时间'")
    private long updateTime;

    /**
     * 创建时间
     */
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '创建时间'")
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

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
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
   
}
