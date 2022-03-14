package com.xxgame.admin.web.modules.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 服务器资源-相当于子菜单
 * @author gil
 *
 */
@Entity(name = "system_resource")
@Table(indexes = @Index(name = "menuId_index", columnList = "menuId"))
public class SystemResource {
   
	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "INT NOT NULL COMMENT 'id'")
    private int id;
    
    /**
     * 所属菜单id
     */
    @Column(columnDefinition = "INT NOT NULL COMMENT '所属菜单id'")
    private int menuId;
    
    /**
     * 资源名
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '资源名'")
    private String name;
    
	/**
	 * 对应具体的模块名
	 */
	@Column(columnDefinition = "VARCHAR(128) NOT NULL COMMENT '对应具体的模块名'")
	private String moduleName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}