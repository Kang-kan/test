package com.xxgame.admin.web.modules.auth.controller.model;

import java.util.HashSet;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 菜单下属权限
 * @author gil
 *
 */
@ApiModel(value = "菜单权限")
public class SystemResourceDto {

	/**
	 * id，不会变
	 */
	@ApiModelProperty(value = "菜单id")
	private int id;

	/**
	 * name
	 */
	private String name;
	
	/**
	 * 权限，create:增加, delete:删除, update:修改, query:查询
	 */
	@ApiModelProperty(value = "权限列表，create:增加, delete:删除, update:修改, query:查询")
	private Set<String> auth = new HashSet<String>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getAuth() {
		return auth;
	}

	public void setAuth(Set<String> auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}