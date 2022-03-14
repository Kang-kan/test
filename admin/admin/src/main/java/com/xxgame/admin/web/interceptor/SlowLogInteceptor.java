package com.xxgame.admin.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xxgame.admin.web.constant.LoggerConstant;

/**
 * 处理请求过慢日志拦截器
 * 
 * @author gil
 *
 */
@Component
public class SlowLogInteceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(LoggerConstant.SLOW);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		long startTime = (Long) request.getAttribute("startTime");  
        long executeTime = System.currentTimeMillis() - startTime;  

        if (executeTime > 1000) {
        	logger.info("url:{} time:{}", request.getRequestURI(), executeTime); 
        }
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)	throws Exception {

	}

}
