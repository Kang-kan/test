package com.xxgame.admin.web.configs;

import com.xxgame.admin.web.modules.shiro.StatelessAuthFilter;
import com.xxgame.admin.web.modules.shiro.StatelessDefaultSubjectFactory;
import com.xxgame.admin.web.modules.shiro.StatelessRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置
 * 
 * @author gil
 *
 */
@Configuration
public class ShiroConfiguration {

	/**
	 * statelessRealm
	 * 
	 * @return
	 */
	@Bean(name = "statelessRealm")
	public StatelessRealm statelessRealm() {
		StatelessRealm statelessRealm = new StatelessRealm();
		statelessRealm.setCachingEnabled(false);
		return statelessRealm;
	}

	/**
	 * subjectFactory
	 * @return
	 */
	@Bean(name = "subjectFactory")
	public SubjectFactory subjectFactory() {
		return new StatelessDefaultSubjectFactory();
	}

	/**
	 * sessionManager
	 * 因为我们禁用掉了会话，所以没必要再定期过期会话了。
	 * @return
	 */
	@Bean(name = "sessionManager")
	public SessionManager sessionManager() {
		DefaultSessionManager sessionManager = new DefaultSessionManager();
		sessionManager.setSessionValidationSchedulerEnabled(false);
		sessionManager.setDeleteInvalidSessions(false);
		return sessionManager;
	}

	/**
	 * spring 支持的生命周期管理
	 * 
	 * @return
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * SecurityManager
	 * 
	 * @return
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(SubjectFactory subjectFactory, SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(this.statelessRealm());
		securityManager.setSubjectFactory(subjectFactory);
		securityManager.setSessionManager(sessionManager);

		DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
		DefaultSessionStorageEvaluator sessionStorageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
		sessionStorageEvaluator.setSessionStorageEnabled(false);

		return securityManager;
	}

	/**
	 *
	 * @param securityManager
	 * @return
	 */
	public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		methodInvokingFactoryBean.setArguments(securityManager);

		return methodInvokingFactoryBean;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean(name = "authorizationAttributeSourceAdvisor")
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager);
		return aasa;
	}

	/**
	 * 自动代理所有的advisor: 由Advisor决定对哪些类的方法进行AOP代理。
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	/**
	 * StatelessAuthFilter
	 * @return
	 */
	@Bean(name = "statelessAuthFilter")
	public StatelessAuthFilter statelessAuthFilter() {
		return new StatelessAuthFilter();
	}

	/**
	 *
	 * @param securityManager
	 * @param statelessAuthFilter
	 * @return
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager, StatelessAuthFilter statelessAuthFilter) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		shiroFilterFactoryBean.getFilters().put("statelessAuth", statelessAuthFilter);

		Map<String, String> filterChain = new LinkedHashMap<String, String>();
		filterChain.put("/**", "statelessAuth");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);

		return shiroFilterFactoryBean;
	}

	/**
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean delegatingFilterProxy(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxy);

		return filterRegistrationBean;
	}

}
