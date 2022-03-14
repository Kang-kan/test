package com.xxgame.admin.web.modules.shiro;

import com.xxgame.admin.web.modules.commons.cache.CacheKeys;
import com.xxgame.admin.web.modules.commons.cache.LocalCacheService;
import com.xxgame.admin.web.modules.user.SystemUserService;
import com.xxgame.admin.web.modules.user.entity.SystemUser;
import com.xxgame.admin.web.modules.user.model.AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;

public class StatelessRealm extends AuthorizingRealm {

    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private ShiroRightService shiroRightService;
    @Autowired
    private LocalCacheService localCacheService;

    /**
     * domain
     */
    @Value("${social.web.domain}")
    private String domain;

    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }

    /**
     * 授权，每次做权限检查时都会请求
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        long userId = (long) principalCollection.getPrimaryPrincipal();

        Set<String> rightSet = localCacheService.getValue(CacheKeys.USER_ACCESS_RIGHTS + userId);
        if (rightSet == null) {
            SystemUser systemUser = systemUserService.getSystemUser(userId);
            if (systemUser == null || systemUser.getStatus() == 0) {
                rightSet = new HashSet<>();
            } else {
                rightSet = shiroRightService.getRights(systemUser.getId());
            }

            localCacheService.setValue(CacheKeys.USER_ACCESS_RIGHTS + userId, rightSet);
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(rightSet);

        return authorizationInfo;
    }

    /**
     * 认证，login认证时请求
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AccessToken accessToken = (AccessToken) authenticationToken.getCredentials();
        if (accessToken == null) {
            throw new UnauthenticatedException("AccessToken为空。");
        }
        if (System.currentTimeMillis() > accessToken.getExpired()) {
            throw new UnauthenticatedException("AccessToken已过期" + accessToken);
        }
        if (!StringUtils.equals(accessToken.getDomain(), this.domain)) {
            throw new UnauthenticatedException("AccessToken非法的授权域" + accessToken);
        }

        return new SimpleAuthenticationInfo(accessToken.getId(), accessToken, getName());
    }
}
