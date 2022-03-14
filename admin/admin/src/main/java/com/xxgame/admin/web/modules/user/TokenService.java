package com.xxgame.admin.web.modules.user;

import com.xxgame.admin.web.modules.user.model.AccessToken;

/**
 * TokenService。token 由以下格式生成：sha256(payload+salt).aes256(payload)
 * @author gil
 */
public interface TokenService {

    /**
     * 生成token
     * @param userId
     * @return
     */
    String create(long userId);

    /**
     * 检验token是否合法
     * @param token
     * @return
     */
    boolean verify(String token);

    /**
     * 解密token
     * @param token
     * @return
     */
    AccessToken decode(String token);

    /**
     * 是否需要刷新token
     * @param accessToken
     * @return
     */
    String refreshToken(AccessToken accessToken);

}
