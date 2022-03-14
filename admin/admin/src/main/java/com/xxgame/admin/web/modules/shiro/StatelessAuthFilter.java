package com.xxgame.admin.web.modules.shiro;

import com.xxgame.admin.web.model.SessionKeys;
import com.xxgame.admin.web.modules.user.TokenService;
import com.xxgame.admin.web.modules.user.model.AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 无状态的请求过滤器
 */
public class StatelessAuthFilter extends AccessControlFilter {

    @Autowired
    private TokenService tokenService;

    private final Set<String> allowPath = new HashSet<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    void init() {
        allowPath.add("/systemuser/salt");
        allowPath.add("/systemuser/login");
        allowPath.add("/chatManager/blockWords");
        allowPath.add("/swagger-ui.html");
        allowPath.add("/webjars/springfox-swagger-ui");
        allowPath.add("/swagger-resources");
        allowPath.add("/v2/api-docs");
        allowPath.add("/cs/leaveMessage");
        allowPath.add("/onlinestatis/repairData");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        if (this.isAllowPath(request.getServletPath())) {
            return true;
        }

        String token = request.getHeader("Access-Token");
        if (StringUtils.isBlank(token)) {
            this.writeNotAuth(servletResponse);
            return false;
        }

        try {
            AccessToken accessToken = tokenService.decode(token);
            // 委托给Realm进行登录
            getSubject(servletRequest, servletResponse).login(StatelessToken.valueOf(accessToken));

            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            attributes.setAttribute(SessionKeys.ACCESS_TOKEN, accessToken, RequestAttributes.SCOPE_REQUEST);
            attributes.setAttribute(SessionKeys.SYSTEM_USER_ID, accessToken.getId(), RequestAttributes.SCOPE_REQUEST);
        } catch (Exception e) {
            logger.error("AccessToken无效或过期。", e);
            this.writeNotAuth(servletResponse);
            return false;
        }

        return true;
    }

    /**
     * 是否是允许的path
     * @param requestPath
     * @return
     */
    private boolean isAllowPath(String requestPath) {
        for (String path : allowPath) {
            if (requestPath.startsWith(path)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 响应无权限
     * @param servletResponse
     * @throws IOException
     */
    private void writeNotAuth(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().flush();
    }

}
