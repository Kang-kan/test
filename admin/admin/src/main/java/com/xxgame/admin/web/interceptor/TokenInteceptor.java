package com.xxgame.admin.web.interceptor;

import com.xxgame.admin.web.model.SessionKeys;
import com.xxgame.admin.web.modules.user.TokenService;
import com.xxgame.admin.web.modules.user.model.AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 * 
 * @author gil
 *
 */
@Component
public class TokenInteceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler) throws Exception {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		Object obj = attributes.getAttribute(SessionKeys.ACCESS_TOKEN, RequestAttributes.SCOPE_REQUEST);
		if (obj == null) {
			return true;
		}

		AccessToken accessToken = (AccessToken) obj;
		String token = tokenService.refreshToken(accessToken);
		if (token != null) {
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			servletResponse.setHeader("Access-Token", token);
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)	throws Exception {

	}

}
