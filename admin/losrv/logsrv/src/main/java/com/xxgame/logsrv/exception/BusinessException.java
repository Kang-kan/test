package com.xxgame.logsrv.exception;

/**
 * 业务异常
 * @author gil
 *
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7760274579947997105L;

    public BusinessException(String message) {
        super(message);
    }
    
}
