package com.xxgame.admin.web.modules.commons.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 简单属性设置
 * @author gil
 *
 */
@Entity
@Table(name = "property")
public class Property {

    @Id
    @Column(columnDefinition = "VARCHAR(128) NOT NULL COMMENT 'propKey'")
    private String propKey;
    
    @Column(columnDefinition = "VARCHAR(2048) DEFAULT '' COMMENT 'propValue'")
    private String propValue;
    
	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
