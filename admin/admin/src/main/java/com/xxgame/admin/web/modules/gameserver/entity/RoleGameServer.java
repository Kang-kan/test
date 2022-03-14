package com.xxgame.admin.web.modules.gameserver.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 角色服务器关系
 * @author gil
 *
 */
@Entity(name = "role_game_server")
@Table(indexes = { @Index(name = "role_srv_idx", columnList = "roleId, serverId", unique = true) })
public class RoleGameServer {
	
	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
    private long id;
    
    /**
     * 角色id
     */
	@Column(columnDefinition = "INT NOT NULL COMMENT '角色id'")
    private int roleId;
    
    /**
     * 服务器id
     */
    @Column(columnDefinition = "INT NOT NULL COMMENT '服务器id'")
    private int serverId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
