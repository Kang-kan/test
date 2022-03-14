package com.xxgame.admin.web.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * servlet 配置
 * 
 * @author gil
 *
 */
@Configuration
public class ServletMapper {
	
	/**
	 * 禁止访问的ip列表
	 */
	@Value("${druid.deny}")
	private String druidDeny;
	
	/**
	 * 用户名
	 */
	@Value("${druid.loginUsername}")
	private String loginUsername;
	
	/**
	 * 密码
	 */
	@Value("${druid.loginPassword}")
	private String loginPassword;

	/**
	 * druid监控 servlet
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new com.alibaba.druid.support.http.StatViewServlet(), "/druid/*");

		// 添加初始化参数：initParams
		// 白名单：
		servletRegistrationBean.addInitParameter("allow", "192.168.*.*");
		// IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not
		// permitted to view this page.
		servletRegistrationBean.addInitParameter("deny", druidDeny);
		// 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername", loginUsername);
		servletRegistrationBean.addInitParameter("loginPassword", loginPassword);

		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", "false");

		return servletRegistrationBean;
	}

	/**
	 * 过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

		return filterRegistrationBean;
	}

}
