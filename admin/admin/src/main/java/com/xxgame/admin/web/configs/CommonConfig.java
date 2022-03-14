package com.xxgame.admin.web.configs;

import java.sql.SQLException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import com.xxgame.admin.web.interceptor.CORSInterceptor;
import com.xxgame.admin.web.modules.commons.cache.LocalCacheService;
import com.xxgame.admin.web.modules.commons.cache.LocalCacheServiceImpl;
import com.xxgame.admin.web.util.HttpUtils;
import org.apache.http.client.HttpClient;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 公共配置
 * 
 * @author gil
 *
 */
@Configuration
public class CommonConfig {

	/**
	 * 服务器512位密钥
	 */
    @Value("${social.serverKey}")
    private String serverKey;
	/**
	 * 服务器IV
	 */
	@Value("${social.serverIv}")
	private String serverIv;
    
	@Autowired
	private DataSourceConfig dataSourceConfig;
	@Autowired
	private LogDbConfig logDbConfig;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * server key
	 * @return
	 */
	@Bean(name = "serverKey")
	public String serverKey() {
		return serverKey;
	}

	/**
	 * server iv
	 * @return
	 */
	@Bean(name = "serverIv")
	public String serverIv() {
		return serverIv;
	}

	/**
	 * server key
	 * @return
	 */
	@Bean(name = "serverKeyBytes")
	public byte[] serverKeyBytes() {
		return Base64.decodeBase64(serverKey);
	}

	/**
	 * server iv
	 * @return
	 */
	@Bean(name = "serverIvBytes")
	public byte[] serverIvBytes() {
		return Base64.decodeBase64(serverIv);
	}
	
	/**
	 * 跨域过滤器
	 * 
	 * @return
	 */
	@Bean
	public CORSInterceptor corsInterceptor() {
		return new CORSInterceptor();
	}

	/**
	 * 文件上传临时路径
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		String folder = System.getProperty("java.io.tmpdir");

		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(folder);

		return factory.createMultipartConfig();
	}

	/**
	 * RestTemplate，http工具类
	 * @return
	 */
	@Bean(name = "restTemplate")
	public RestTemplate restTemplate() {
		HttpClient httpClient = HttpUtils.getHttpClient();
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(requestFactory);

		return restTemplate;
	}

	/**
	 * AsyncRestTemplate，异步http工具类
	 * @return
	 */
	@Bean(name = "asyncRestTemplate")
	public AsyncRestTemplate asyncRestTemplate() {
		return new AsyncRestTemplate();
	}

	/**
	 * 主数据源
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(dataSourceConfig.getUrl());
		datasource.setUsername(dataSourceConfig.getUsername());
		datasource.setPassword(dataSourceConfig.getPassword());
		datasource.setDriverClassName(dataSourceConfig.getDriver());
		datasource.setInitialSize(dataSourceConfig.getInitialSize());
		datasource.setMinIdle(dataSourceConfig.getMinIdle());
		datasource.setMaxActive(dataSourceConfig.getMaxActive());
		datasource.setMaxWait(dataSourceConfig.getMaxWait());
		datasource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
		datasource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
		datasource.setValidationQuery(dataSourceConfig.getValidationQuery());
		datasource.setTestWhileIdle(dataSourceConfig.isTestWhileIdle());
		datasource.setTestOnBorrow(dataSourceConfig.isTestOnBorrow());
		datasource.setTestOnReturn(dataSourceConfig.isTestOnReturn());
		datasource.setPoolPreparedStatements(dataSourceConfig.isPoolPreparedStatements());
		datasource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
		try {
			datasource.setFilters(dataSourceConfig.getFilters());
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}

		datasource.setConnectionProperties(dataSourceConfig.getConnectionProperties());
		datasource.setTimeBetweenLogStatsMillis(15 * 60 * 1000L);

		return datasource;
	}

	/**
	 * spring JdbcTemplate
	 * @return
	 */
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate() {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(logDbConfig.getUrl());
		datasource.setUsername(logDbConfig.getUsername());
		datasource.setPassword(logDbConfig.getPassword());

		datasource.setDriverClassName(dataSourceConfig.getDriver());
		datasource.setInitialSize(dataSourceConfig.getInitialSize());
		datasource.setMinIdle(dataSourceConfig.getMinIdle());
		datasource.setMaxActive(dataSourceConfig.getMaxActive());
		datasource.setMaxWait(dataSourceConfig.getMaxWait());
		datasource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
		datasource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
		datasource.setValidationQuery(dataSourceConfig.getValidationQuery());
		datasource.setTestWhileIdle(dataSourceConfig.isTestWhileIdle());
		datasource.setTestOnBorrow(dataSourceConfig.isTestOnBorrow());
		datasource.setTestOnReturn(dataSourceConfig.isTestOnReturn());
		datasource.setPoolPreparedStatements(dataSourceConfig.isPoolPreparedStatements());
		datasource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
		try {
			datasource.setFilters(dataSourceConfig.getFilters());
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}

		datasource.setConnectionProperties(dataSourceConfig.getConnectionProperties());
		datasource.setTimeBetweenLogStatsMillis(15 * 60 * 1000L);

		return new JdbcTemplate(datasource);
	}

	/**
	 * ThreadPoolTaskScheduler
	 * @return
	 */
	@Bean(name = "threadPoolTaskScheduler")
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(16);
		taskScheduler.initialize();

		return taskScheduler;
	}

	/**
	 * ScheduledThreadPoolExecutor
	 * @param threadPoolTaskScheduler
	 * @return
	 */
	@Bean(name = "scheduledThreadPoolExecutor")
	public ScheduledThreadPoolExecutor scheduledThreadPoolExecutor(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
		return threadPoolTaskScheduler.getScheduledThreadPoolExecutor();
	}

	/**
	 * LocalCacheService
	 * @return
	 */
	@Bean(name = "localCacheService")
	public LocalCacheService localCacheService() {
		return new LocalCacheServiceImpl();
	}

}
