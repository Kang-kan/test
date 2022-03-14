package com.xxgame.admin.web.modules.customerservice.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 聊天
 * @author gil
 *
 */
@Entity(name = "chatBlockWord")
public class ChatBlockWord {

	/**
	 * id
	 */
    @Id
	@Column(columnDefinition = "INT NOT NULL COMMENT 'id'")
    private int id;

    /**
     * 内容
     */
    @Column(columnDefinition = "TEXT COMMENT '内容'")
    private String blockWords;

	/**
	 * 更新时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '更新时间'")
	private long updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBlockWords() {
		return blockWords;
	}

	public void setBlockWords(String blockWords) {
		this.blockWords = blockWords;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}