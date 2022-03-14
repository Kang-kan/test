package com.xxgame.logsrv.module;

/**
 * 结果码
 */
public interface ResultCode {

    /**
     * 成功
     */
    int SUCCESS = 0;

    /**
     * 错误
     */
    int ERROR = -1;

    /**
     * 参数错误
     */
    int PARAMETER_ERROR = -2;

    /**
     * 签名错误
     */
    int SIGN_ERROR = -3;

    /**
     * 已支付
     */
    int PAIED = -4;
}
