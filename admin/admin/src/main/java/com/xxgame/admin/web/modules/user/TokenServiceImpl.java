package com.xxgame.admin.web.modules.user;

import com.alibaba.fastjson.JSON;
import com.xxgame.admin.web.modules.user.model.AccessToken;
import com.xxgame.admin.web.util.CryptUtils;
import com.xxgame.admin.web.util.ShaDigestUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * TokenService
 * @author gil
 */
@Service
public class TokenServiceImpl implements TokenService {

    /**
     * datacenterId
     */
    @Value("${social.datacenterId}")
    private int datacenterId;

    /**
     * serverId
     */
    @Value("${social.serverId}")
    private int serverId;

    /**
     * domain
     */
    @Value("${social.web.domain}")
    private String domain;

    @Autowired
    private byte[] serverKeyBytes;
    @Autowired
    private byte[] serverIvBytes;

    /**
     * 过期时间
     */
    private final int EXPIRED_TIME = 30 * 60 * 1000;

    /**
     * 刷新时间
     */
    private final int REFRESH_TIME = (int) (EXPIRED_TIME * 0.7);

    /**
     * hash 5次
     */
    private final int HASH_ITERATIONS = 5;

    @Override
    public String create(long userId) {
        AccessToken accessToken = new AccessToken();
        accessToken.setId(userId);
        accessToken.setIss(datacenterId + ":" + serverId);
        accessToken.setCreateTime(System.currentTimeMillis());
        accessToken.setExpired(accessToken.getCreateTime() + EXPIRED_TIME);
        accessToken.setDomain(domain);

        byte[] payLoadBytes = JSON.toJSONBytes(accessToken);
        byte[] aesPwdBytes = CryptUtils.aesEncrypt(payLoadBytes, serverKeyBytes, serverIvBytes);
        byte[] hashBytes = ShaDigestUtils.sha256(aesPwdBytes, serverKeyBytes, 5);

        StringBuilder sb = new StringBuilder();
        sb.append(Base64.encodeBase64String(hashBytes));
        sb.append(".");
        sb.append(Base64.encodeBase64String(aesPwdBytes));

        return sb.toString();
    }

    @Override
    public boolean verify(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        String[] strs = token.split(".");
        if (strs.length != 2) {
            return false;
        }
        String expectSha = strs[0];
        byte[] aesPwdBytes = Base64.decodeBase64(strs[1]);
        byte[] hashBytes = ShaDigestUtils.sha256(aesPwdBytes, serverKeyBytes, 5);
        String actualSha = Base64.encodeBase64String(hashBytes);

        return StringUtils.equals(expectSha, actualSha);
    }

    @Override
    public AccessToken decode(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String[] strs = token.split("\\.");
        if (strs.length != 2) {
            return null;
        }
        String expectSha = strs[0];
        byte[] aesPwdBytes = Base64.decodeBase64(strs[1]);
        byte[] hashBytes = ShaDigestUtils.sha256(aesPwdBytes, serverKeyBytes, 5);
        String actualSha = Base64.encodeBase64String(hashBytes);

        if (!StringUtils.equals(expectSha, actualSha)) {
            return null;
        }

        byte[] payloadBytes = CryptUtils.aesDecrypt(aesPwdBytes, serverKeyBytes, serverIvBytes);
        return JSON.parseObject(payloadBytes, AccessToken.class);
    }

    /**
     * 是否需要刷新token
     * @param accessToken
     * @return
     */
    @Override
    public String refreshToken(AccessToken accessToken) {
        // 还有多久过期
        long remainTime = accessToken.getExpired() - System.currentTimeMillis();
        // 快要过期才需要刷新token
        if (remainTime > REFRESH_TIME) {
            return null;
        }
        synchronized (String.valueOf(accessToken.getId()).intern()) {
            return this.create(accessToken.getId());
        }
    }

}
