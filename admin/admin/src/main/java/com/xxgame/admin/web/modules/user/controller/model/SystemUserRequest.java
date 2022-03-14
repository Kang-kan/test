package com.xxgame.admin.web.modules.user.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 管理后台用户请求对象
 * 
 * @author gil
 *
 */
@ApiModel(value = "管理后台用户请求对象")
public class SystemUserRequest {

	/**
	 * id，更新时传
	 */
	@ApiModelProperty(value = "id，更新时传")
	private String id;

	/**
	 * 账号，更新时可以不传
	 */
	@ApiModelProperty(value = "账号，更新时可以不传")
	private String userName;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;

	/**
	 * 电话号码
	 */
	@ApiModelProperty(value = "电话号码")
	private String phone;

	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;

	/**
	 * 状态.1:启用,0:禁用
	 */
	@ApiModelProperty(value = "状态.1:启用,0:禁用")
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
