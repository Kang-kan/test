package com.xxgame.admin.web.modules.auth.controller.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 登陆返回菜单列表
 * 
 * @author gil
 *
 */
public class SystemMenuDto {

	/**
	 * id，不会变
	 */
	private int id;

	/**
	 * name
	 */
	private String name;
	
	/**
	 * 下属权限列表
	 */
	private List<SystemResourceDto> resource = new ArrayList<SystemResourceDto>();

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

	public List<SystemResourceDto> getResource() {
		return resource;
	}

	public void setResource(List<SystemResourceDto> resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}