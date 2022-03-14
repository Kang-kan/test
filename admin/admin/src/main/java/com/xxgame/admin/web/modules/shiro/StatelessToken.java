package com.xxgame.admin.web.modules.shiro;

import com.xxgame.admin.web.modules.user.model.AccessToken;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Shiro AuthenticationToken
 */
public class StatelessToken implements AuthenticationToken {

    private AccessToken accessToken;

    @Override
    public Object getPrincipal() {
        return accessToken.getId();
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     * @return
     */
    public static StatelessToken valueOf(AccessToken accessToken) {
        StatelessToken token = new StatelessToken();
        token.accessToken = accessToken;
        return token;
    }
}
