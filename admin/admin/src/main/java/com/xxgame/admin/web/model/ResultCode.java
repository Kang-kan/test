package com.xxgame.admin.web.model;

/**
 * 结果码定义
 * @author gil
 *
 */
public interface ResultCode {

	//----------------------------------基本的结果码---------------------------------
	
	/**
	 * 成功
	 */
	int SUCCESS = 0;
	
	/**
	 * 失败
	 */
	int FAIL = -1;
	
	/**
	 * 未授权
	 */
	int NO_RIGHT = -101;

	/**
	 * token 过期
	 */
	int TOKEN_EXPIRE = -102;
	
	/**
	 * 微信登录异常
	 */
	int WX_LOGIN_FAIL = -103;
	
	/**
	 * 参数不能为空
	 */
	int PARAM_EMPTY = -104;
	
	/**
	 * 参数错误
	 */
	int PARAM_ERROR = -105;
	
	/**
	 * http请求异常
	 */
	int HTTP_REQUEST_ERROR = -106;
	
	/**
	 * 请求结果为空
	 */
	int RESULT_EMPTY = -107;
	
	/**
	 * 参数长度过短
	 */
	int PARAM_MIN_LENGTH = -108;
	
	/**
	 * 参数长度过长
	 */
	int PARAM_MAX_LENGTH = -109;

	/**
	 *  登录参数过期
	 */
	int PARAMETER_EXPIRE = -110;

	/**
	 *  未拥有该服务器权限
	 */
	int DONOT_HAVE_THIS_SERVER = -111;

	//----------------------------------用户模块的结果码 -2xx---------------------------------
	
	/**
	 * 用户名长度过短
	 */
	int USERNAME_MIN_LENGTH = -200;
	
	/**
	 * 用户名长度过长
	 */
	int USERNAME_MAX_LENGTH = -201;
	
	/**
	 * 密码长度过短
	 */
	int PASSWORD_MIN_LENGTH = -202;
	
	/**
	 * 密码长度过长
	 */
	int PASSWORD_MAX_LENGTH = -203;
	
	/**
	 * 密码必须包含数字和字母
	 */
	int PASSWORD_UNUSEFUL = -204;
	
	/**
	 * 昵称长度过短
	 */
	int NICKNAME_MIN_LENGTH = -205;
	
	/**
	 * 昵称长度过长
	 */
	int NICKNAME_MAX_LENGTH = -206;
	
	/**
	 * 用户名已存在
	 */
	int USER_NAME_EXISTS = -207;
	
	/**
	 * 用户名或密码错误
	 */
	int USER_NAME_PWD_ERROR = -208;
	
	/**
	 * 用户不存在
	 */
	int USER_NOT_EXISTS = -209;
	
	/**
	 * 帐号已停用
	 */
	int USER_DISABLE = -210;
	
	/**
	 * 新密码不能和旧密码一样
	 */
	int NEWPWD_SAMEAS_OLD = -211;
	
	/**
	 * 不能重置自己的密码
	 */
	int CANNOT_RESET_PWD = -212;
	
	//---------------------------------- 权限模块的结果码 -3xx---------------------------------

	/**
	 * 角色名已存在
	 */
	int ROLE_NAME_EXISTS = -300;
	
	/**
	 * 角色名长度不正确
	 */
	int ROLE_NAME_LENGTH = -301;
	
	/**
	 * 系统角色不能删除
	 */
	int SYSTEM_ROLE = -302;
	
	/**
	 * 角色不存在
	 */
	int ROLE_NOT_EXISTS = -303;
	
	/**
	 * 权限不正确
	 */
	int RIGHT_NOT_CORRECT = -304;
	
	/**
	 * 资源不存在
	 */
	int RESOURCE_NOT_EXISTS = -305;
	
	/**
	 * 用户已经有了该角色
	 */
	int HAVE_THIS_ROLE = -306;
	
	/**
	 * 用户没有有该角色
	 */
	int DONT_HAVE_THIS_ROLE = -307;
	
	/**
	 * 系统角色不能删除
	 */
	int SYSTEM_USER = -303;
	
}
