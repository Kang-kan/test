package com.xxgame.logsrv.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 主数据源配置类
 *
 * @author gil
 *
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class PrimaryDataSourceConfig {

	/**
	 * 
	 */
	private String url;
	
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
