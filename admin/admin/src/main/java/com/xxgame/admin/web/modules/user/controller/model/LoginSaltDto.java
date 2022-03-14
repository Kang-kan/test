package com.xxgame.admin.web.modules.user.controller.model;

/**
 * 登录加密参数
 */
public class LoginSaltDto {

    private String salt;

    private String iv;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
