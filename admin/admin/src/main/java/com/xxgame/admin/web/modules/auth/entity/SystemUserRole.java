package com.xxgame.admin.web.modules.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户角色关系
 * @author gil
 *
 */
@Entity(name = "system_user_role")
@Table(indexes = {@Index(name = "user_role_id_index", columnList = "userId, roleId", unique = true),
		@Index(name = "user_id_index", columnList = "userId")})
public class SystemUserRole {
	
	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
    private long id;
    
    /**
     * 管理后台用户id
     */
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '管理后台用户id'")
    private long userId;
    
    /**
     * 角色id
     */
    @Column(columnDefinition = "INT NOT NULL COMMENT '角色id'")
    private int roleId;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
