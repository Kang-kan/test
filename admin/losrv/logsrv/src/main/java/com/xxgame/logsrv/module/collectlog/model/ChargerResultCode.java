package com.xxgame.logsrv.module.collectlog.model;

/**
 * 充值返回码定义
 *
 */
public enum ChargerResultCode {
	SUCCESS("0000", "订单发货成功"), 
	NO_SUCH_ORDER("0001", "订单不存在"), 
	PRICE_NOT_MATCH("0002", "商品金额不符"), 
	SIGN_NOT_MATCH("0003", "签名不匹配"),
	FINISHED("0004", "订单已发货"),
	;
	/**
	 * 0000成功,其他失败
	 */
	private String code;
	/**
	 * 成功或失败消息
	 */
	private String message;
	
	private ChargerResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return "{\"code\":\"" + code + "\",\"message\":\"" + message + "\"}";
	}
}
