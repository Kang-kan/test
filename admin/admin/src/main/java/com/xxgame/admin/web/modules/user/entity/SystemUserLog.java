package com.xxgame.admin.web.modules.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 管理后台登录日志
 * @author gil
 *
 */
@Entity
@Table(name = "system_user_log")
public class SystemUserLog {
	
	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "INT NOT NULL COMMENT 'id' AUTO_INCREMENT")
    private int id;
    
    /**
     * 管理后台用户id
     */
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '管理后台用户id'")
    private long userId;
    
    /**
     * 登录ip
     */
    @Column(columnDefinition = "VARCHAR(128) DEFAULT '' COMMENT 'id'")
    private String loginIp;

    /**
     * 登录时间
     */
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '登录时间'")
    private long loginTime;

    /**
     * 登录事件
     */
    @Column(columnDefinition = "VARCHAR(512) DEFAULT '' COMMENT '登录事件'")
    private String events;
   
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
