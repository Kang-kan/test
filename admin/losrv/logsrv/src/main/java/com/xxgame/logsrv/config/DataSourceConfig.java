package com.xxgame.logsrv.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据源配置类
 *
 * @author gil
 *
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfig {

	/**
	 * 连接池
	 */
	private String type;

	/**
	 * 驱动
	 */
	private String driver;
	
	/**
	 * 初始化大小
	 */
	private int initialSize;

	/**
	 * 最小
	 */
	private int minIdle;

	/**
	 * 最多
	 */
	private int maxActive;

	/**
	 * 配置获取连接等待超时的时间
	 */
	private long maxWait;

	/**
	 * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
	 */
	private long timeBetweenEvictionRunsMillis;

	/**
	 * 配置一个连接在池中最小生存的时间，单位是毫秒
	 */
	private long minEvictableIdleTimeMillis;

	/**
	 * 检查语句
	 */
	private String validationQuery;

	/**
	 * 
	 */
	private boolean testWhileIdle;

	/**
	 * 
	 */
	private boolean testOnBorrow;

	/**
	 * 
	 */
	private boolean testOnReturn;

	/**
	 * 打开PSCache，并且指定每个连接上PSCache的大小
	 */
	private boolean poolPreparedStatements;

	/**
	 * 
	 */
	private int maxPoolPreparedStatementPerConnectionSize;

	/**
	 * 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
	 */
	private String filters;

	/**
	 * 通过connectProperties属性来打开mergeSql功能；慢SQL记录
	 */
	private String connectionProperties;

	/**
	 * 合并多个DruidDataSource的监控数据
	 */
	private boolean useGlobalDataSourceStat;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public boolean isUseGlobalDataSourceStat() {
		return useGlobalDataSourceStat;
	}

	public void setUseGlobalDataSourceStat(boolean useGlobalDataSourceStat) {
		this.useGlobalDataSourceStat = useGlobalDataSourceStat;
	}
	
}
