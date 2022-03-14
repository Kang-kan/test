package com.xxgame.admin.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回给客户端的结果对象。业务错误返回Result，系统错误返回http status。
 * 
 * @author gil
 * 
 */
@ApiModel(value = "返回给客户端的结果对象")
public class Result<T> {

	/**
	 * 状态码
	 */
	@ApiModelProperty(value = "状态码，0-代表成功")
	private int code;

	/**
	 * 返回的内容对象
	 */
	@ApiModelProperty(value = "返回的内容对象")
	private T content;
	
	/**
	 * 提示信息。如果有
	 */
	@ApiModelProperty(value = "提示信息。如果有")
	private String message;

	/**
	 * 返回成功结果
	 * 
	 * @param content 内容对象
	 * @return
	 */
	public static <T> Result<T> success(T content) {
		Result<T> result = new Result<T>();
		result.setCode(0);
		result.setContent(content);
		return result;
	}

	/**
	 * 返回失败结果
	 * 
	 * @param code 错误码
	 * @param message 错误信息
	 * @return
	 */
	public static <T> Result<T> error(int code, String message) {
		Result<T> result = new Result<T>();
		result.setCode(code);
		result.setMessage(message);
		return result;
	}

	/**
	 * 返回自定义结果
	 * 
	 * @param code 错误码
	 * @param content 内容对象
	 * @return
	 */
	public static <T> Result<T> valueOf(int code, T content) {
		Result<T> result = new Result<T>();
		result.setCode(code);
		result.setContent(content);
		return result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
