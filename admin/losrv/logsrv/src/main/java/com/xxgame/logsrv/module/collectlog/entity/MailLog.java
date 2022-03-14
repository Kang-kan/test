package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 玩家邮件日志
 * @author gil
 *
 */
@Entity(name = "mail_log")
@Table(indexes = { @Index(name = "player_idx", columnList = "playerId,time"),
		@Index(name = "srv_title_idx", columnList = "serverId")})
public class MailLog extends BasePlayerLogEntity {

	/**
	 * 邮件id
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '邮件id'")
	private long mailId;

	/**邮件创建时间 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '邮件创建时间'")
	private long createTime;

	/**邮件类型,1-附邮件*/
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '邮件类型'")
	private byte mailType;

	/**邮件标题*/
	@Column(columnDefinition = "VARCHAR(256) COMMENT '邮件标题'")
	private String title;

	/**邮件内容*/
	@Column(columnDefinition = "VARCHAR(512) COMMENT '邮件内容'")
	private String message;

	/**邮件礼包内容*/
	@Column(columnDefinition = "VARCHAR(1024) NOT NULL COMMENT '邮件礼包内容'")
	private String bonus;

	/**邮件是否以读(0表示未读 1表示已读)*/
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '是否以读'")
	private byte readStatus;

	/**邮件是否已经提取附件*/
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '否已经提取附件'")
	private byte isGet;

	/**邮件配置表id mailSetting.xml*/
	@Column(columnDefinition = "INT DEFAULT '0' COMMENT 'configId'")
	private int configId;

	/**
	 * 操作，0-领取，1-删除
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public byte getMailType() {
		return mailType;
	}

	public void setMailType(byte mailType) {
		this.mailType = mailType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public byte getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(byte readStatus) {
		this.readStatus = readStatus;
	}

	public byte getIsGet() {
		return isGet;
	}

	public void setIsGet(byte isGet) {
		this.isGet = isGet;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
