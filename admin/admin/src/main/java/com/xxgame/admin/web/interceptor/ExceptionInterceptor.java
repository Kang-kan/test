package com.xxgame.admin.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.xxgame.admin.web.exception.ServerRightException;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.result.ResultFactory;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.util.HttpUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理
 * @author gil
 *
 */
@ControllerAdvice
public class ExceptionInterceptor implements Ordered {
	
	/**
	 * 返回结果工厂类
	 */
	@Autowired
	protected ResultFactory resultFactory;

	@Override
	public int getOrder() {
		return InterceptorOrder.EXCEPTION;
	}
	
    private Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);

    /**
     * BusinessException
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result<Void> businessException(HttpServletRequest request, BusinessException exception) {
        StringBuilder sb = new StringBuilder();
        if (request != null) {
        	sb.append("URL:").append(request.getRequestURI()).append(" ");
            sb.append("METHOD:").append(request.getMethod()).append(" ");
            sb.append("IP:").append(HttpUtils.getRequestIp(request)).append(" ");
        }
    	logger.info("业务操作错误，请求参数：{} 错误代码：{}", sb.toString(), exception.getCode());
    	
    	return Result.error(exception.getCode(), exception.getMessage());
    }
    
    /**
     * 未处理异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Void> exception(Exception exception) {
    	logger.error("发生异常", exception);
    	return resultFactory.error(ResultCode.FAIL);
    }

    /**
     * 没管理后台未认证异常
     * @return
     */
    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public Result<Void> unauthenticatedException() {
        return Result.error(ResultCode.TOKEN_EXPIRE, "登录已过期，请重新请登录。");
    }

    /**
     * 没管理后台未授权异常
     * @return
     */
    @ExceptionHandler(value = { UnauthorizedException.class, AuthenticationException.class } )
    @ResponseBody
    public Result<Void> unauthorizedException() {
        return Result.error(ResultCode.NO_RIGHT, "无权限。");
    }

    /**
     * 未拥有该服务器权限
     * @return
     */
    @ExceptionHandler(value = ServerRightException.class)
    @ResponseBody
    public Result<Void> serverRightException() {
        return Result.error(ResultCode.DONOT_HAVE_THIS_SERVER, "无该服务器权限。");
    }

}
