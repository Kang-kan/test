package com.xxgame.logsrv.exception;

/**
 * 等待同步其它日志
 * @author gil
 *
 */
public class WaittingOtherLogException extends RuntimeException {

	private static final long serialVersionUID = 7760274579947997105L;

    public WaittingOtherLogException(String message) {
        super(message);
    }
    
}
