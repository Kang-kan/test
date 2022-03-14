package com.xxgame.admin.web.interceptor;

/**
 * 拦截顺序定义
 * @author gil
 *
 */
public interface InterceptorOrder {
	
	/**
	 * 异常处理
	 */
	int EXCEPTION = 2;
	
	/**
	 * 乐观锁并发异常重试
	 */
	int OPTIMISTIC_LOCK = 5;
	
	/**
	 * 用户认证
	 */
	int TOKEN = 10;
	
	/**
	 * 准备请求日志
	 */
	int PRE_ACCESS_LOG = 14;
	
	/**
	 * 请求日志
	 */
	int ACCESS_LOG = 15;
	
	/**
	 * 本地缓存
	 */
	int LOCAL_CACHE = 40;

}