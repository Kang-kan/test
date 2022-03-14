package com.xxgame.logsrv.module;

/**
 * 响应结果
 * @param <T>
 */
public class Result<T> {

    /**
     * 结果码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应内容
     */
    private T context;

    /**
     * 成功
     * @param context
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T context) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS);
        result.setContext(context);
        return result;
    }

    /**
     * 错误
     * @param code
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(int code) {
        Result<T> result = new Result<>();
        result.setCode(code);
        return result;
    }

    /**
     * 错误
     * @param code
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 是否成功
     * @return
     */
    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        this.context = context;
    }
}
