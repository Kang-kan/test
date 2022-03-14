package com.xxgame.admin.web.modules.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 管理后台菜单栏
 * @author gil
 *
 */
@Entity
@Table(name = "system_menu")
public class SystemMenu {
	
	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "INT NOT NULL COMMENT 'id' AUTO_INCREMENT")
    private int id;
    
    /**
     * 菜单名
     */
    @Column(columnDefinition = "VARCHAR(32) DEFAULT '' COMMENT '菜单名'")
    private String name;
    
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}