package com.xxgame.admin.web.modules.user;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

/**
 * 用户规则服务类
 * @author gil
 *
 */
@Service
public class UserRuleService {
	
	/**
	 * 匹配包含数字和字母，长度为6-16
	 */
	private final String regex = "(?=.*\\d)(?=.*[a-zA-Z]).{6,16}";
	
	/**
	 * 正则表达式的编译表示形式
	 */
	private final Pattern pattern = Pattern.compile(regex);
	
	/**
	 * 用户名最小长度
	 * @return
	 */
	public int minUserNameLength() {
		return 6;
	}

	/**
	 * 用户名最大长度
	 * @return
	 */
	public int maxUserNameLength() {
		return 32;
	}
	
	/**
	 * 密码最小长度
	 * @return
	 */
	public int minPasswordLength() {
		return 6;
	}

	/**
	 * 密码最大长度
	 * @return
	 */
	public int maxPasswordLength() {
		return 16;
	}
	
	/**
	 * 昵称最小长度
	 * @return
	 */
	public int minNameLength() {
		return 4;
	}

	/**
	 * 昵称最大长度
	 * @return
	 */
	public int maxNameLength() {
		return 30;
	}
	
	/**
	 * 密码是否符合条件
	 * @param password
	 * @return
	 */
	public boolean passwordUseful(String password) {
		return pattern.matcher(password).matches();
	}
	
}
