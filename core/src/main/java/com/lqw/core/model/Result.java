package com.lqw.core.model;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class Result<T> {
	public static final Result<Void> res_ok = valueOfOk(null);

	private int code;

	private String msg;

	private T data;

	public static <T> Result<T> valueOf(int code, String msg, T data) {
		Result<T> result = new com.lqw.core.model.Result<T>();
		result.code = code;
		result.msg = msg;
		result.data = data;
		return result;
	}

	public static <T> Result<T> valueOfFail(int code, String msg) {
		return valueOf(code, msg, null);
	}

	public static <T> Result<T> valueOfOk(T data) {
		return valueOf(0, "", data);
	}

	public int getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

	public T getData() {
		return this.data;
	}
}
