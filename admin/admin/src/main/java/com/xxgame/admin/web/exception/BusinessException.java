package com.xxgame.admin.web.exception;

/**
 * 业务异常
 * @author gil
 *
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3030926305032880941L;

	/**
	 * 错误码
	 */
	private int code;
	
	/**
	 * 构造方法
	 * @param code
	 */
    public BusinessException(int code) {
        this.code = code;
    }

	/**
	 * 构造方法
	 * @param code
	 * @param message
	 */
	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
    
}
