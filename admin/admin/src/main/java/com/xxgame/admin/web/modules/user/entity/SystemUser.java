package com.xxgame.admin.web.modules.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 管理后台用户
 * @author gil
 *
 */
@Entity(name = "system_user")
@Table(indexes = @Index(name = "userName_index", columnList = "userName", unique = true))
public class SystemUser {

	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
    private long id;
	
    /**
     * 账号
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '账号'")
    private String userName;
    
    /**
     * 盐
     */
    @Column(columnDefinition = "VARBINARY(512) NOT NULL COMMENT '盐'")
    private byte[] salt;
    
    /**
     * 密码
     */
    @Column(columnDefinition = "VARBINARY(512) DEFAULT '' COMMENT '密码'")
    private byte[] password;
    
    /**
     * 姓名
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '姓名'")
    private String name;
    
    /**
     * 电话号码
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '电话号码'")
    private String phone;
    
    /**
     * 邮箱
     */
    @Column(columnDefinition = "VARCHAR(32) DEFAULT '' COMMENT '邮箱'")
    private String email;
    
    /**
     * 状态.1:启用,0:禁用
     */
    @Column(columnDefinition = "INT NOT NULL COMMENT '状态'")
    private int status = 1;
    
    /**
     * 系统内置用户
     */
    @Column(columnDefinition = "TINYINT	NOT NULL DEFAULT '0' COMMENT '系统内置用户'")
    private boolean systemUser;
    
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
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
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

	public boolean isSystemUser() {
		return systemUser;
	}

	public void setSystemUser(boolean systemUser) {
		this.systemUser = systemUser;
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
