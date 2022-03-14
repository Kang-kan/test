package com.xxgame.admin.web.exception;

/**
 * 服务器异常
 * @author gil
 *
 */
public class ServerException extends RuntimeException {

	private static final long serialVersionUID = -3858342237912775368L;

	public ServerException(String message) {
        super(message);
    }
    
}
