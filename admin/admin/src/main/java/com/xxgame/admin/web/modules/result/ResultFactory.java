package com.xxgame.admin.web.modules.result;

import com.xxgame.admin.web.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 返回结果工厂类
 * @author gil
 *
 */
@Component
public class ResultFactory {
	
	@Autowired
	private ResultMessageService resultMessageService;
	
	/**
	 * 返回错误码和提示内容。
	 * @param code
	 * @return
	 */
	public <T> Result<T> error(int code) {
		String message = resultMessageService.getMessage(code);
		return Result.error(code, message);
	}

}
