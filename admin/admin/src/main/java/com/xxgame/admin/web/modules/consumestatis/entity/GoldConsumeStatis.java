package com.xxgame.admin.web.modules.consumestatis.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 元宝消耗统计
 * @author gil
 *
 */
@Entity(name = "gold_consume_statis")
@Table(indexes = @Index(name = "summary_idx", columnList = "daily,serverId"))
public class GoldConsumeStatis {

	/**
	 * id = daily + serverId
	 */
    @Id
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT 'id'")
    private String id;

	/**
	 * 服务器id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '服务器id'")
	private int serverId;

	/**
	 * 日期
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '日期'")
	private int daily;

	/**
	 * 总人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '总人数'")
	private int total;

	/**
	 * 行为
	 */
	@Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '行为'")
	private String functionType;

	/**
	 * 花费人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '花费人数'")
	private int functionCount;

	/**
	 * 花费元宝，消费为负值，显示要转为正数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '花费元宝'")
	private int gold;

	/**
	 * 生成时间
	 */
	@Column(columnDefinition = "DATETIME COMMENT '生成时间'")
	private Date createTime = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getDaily() {
		return daily;
	}

	public void setDaily(int daily) {
		this.daily = daily;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public int getFunctionCount() {
		return functionCount;
	}

	public void setFunctionCount(int functionCount) {
		this.functionCount = functionCount;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
