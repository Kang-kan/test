package com.xxgame.admin.web.interceptor;

import com.xxgame.admin.web.exception.ServerRightException;
import com.xxgame.admin.web.model.SessionKeys;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 服务器权限拦截器
 */
@Aspect
@Component
public class ServerRightInteceptor {

    @Autowired
    private GameServerService gameServerService;

    /**
     * 在执行织入点前进行权限验证
     *
     * @param joinPoint
     * @param serverRight
     */
    @Before("@annotation(serverRight)")
    public void auth(JoinPoint joinPoint, ServerRight serverRight) {
        Integer serverId = null;
        if (serverRight.argsIndex() >= 0) {
            serverId = (Integer) joinPoint.getArgs()[serverRight.argsIndex()];
        }

        if (serverId == null || serverId <= 0) {
            return;
        }

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        Long userId = (Long) attributes.getAttribute(SessionKeys.SYSTEM_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (userId == null || userId <= 0) {
            throw new ServerRightException();
        }

        boolean right = gameServerService.isHaveThisServer(userId, serverId);
        if (!right) {
            throw new ServerRightException();
        }
    }

}
