package com.xxgame.admin.web.interceptor;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xxgame.admin.web.util.HttpUtils;
import com.xxgame.admin.web.util.IpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 跨域过滤器
 * Created wang
 */
public class CORSInterceptor extends GenericFilterBean {

	/**
	 * 域名
	 */
    @Value("${social.domain}")
    private String domain;
    
	/**
	 * 前端服务器地址
	 */
    @Value("${social.web.domain}")
    private String webDomain;
    
	/**
	 * 内部ip
	 */
    @Value("${social.innerips}")
    private String innerIps;
    
	/**
	 * 允许访问的ip正则
	 */
	private Pattern[] allowIpPatterns;
    
    /**
     * 初始化
     */
	@PostConstruct
	private void init() {
		allowIpPatterns = IpUtils.ipPatterns(innerIps);
	}
	
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String origin = request.getHeader("Origin");
        
        // 是否来自允许的域名
        boolean allowDomain = this.isAllowDomain(request, origin);

        response.setHeader("Access-Control-Allow-Origin", allowDomain ? origin : webDomain);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "Access-Token");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,"
        		+ "If-Modified-Since,Cache-Control,Accept-Encoding,Accept-Language,Access-Token");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");

        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(request, response);
    }
    
    /**
     * 是否来自允许的域名
     * @param request
     * @param origin
     * @return
     */
    private boolean isAllowDomain(HttpServletRequest request, String origin) {
    	if (StringUtils.isEmpty(origin)) {
    		return false;
    	}
        String requestIp = HttpUtils.getRequestIp(request);
        boolean innerIp = IpUtils.isAllowIp(allowIpPatterns, requestIp);
        if (innerIp) {
        	return true;
        }
        
        int portIndex = origin.lastIndexOf(":");
        // http://xx, https://xx, http://xxx:81
        if (portIndex > 4) {
        	origin = origin.substring(0, portIndex);
        }
        
        return origin.endsWith(domain);
    }
    
}
