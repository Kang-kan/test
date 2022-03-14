package com.xxgame.admin.web.configs;

import com.xxgame.admin.web.interceptor.SlowLogInteceptor;
import com.xxgame.admin.web.interceptor.TokenInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器定义配置
 * 
 * @author gil
 *
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	@Autowired
	private TokenInteceptor tokenInteceptor;
	@Autowired
	private SlowLogInteceptor slowLogInteceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInteceptor);
		registry.addInterceptor(slowLogInteceptor);
	}

}
