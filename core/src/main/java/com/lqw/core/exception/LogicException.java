package com.lqw.core.exception;

/**
 * 异常
 *
 * @author liuquanwei
 * @date 2020/12/12
 */
public class LogicException extends RuntimeException {
	
	private static final long serialVersionUID = -3651828036599228205L;

	private final int code;

	public static LogicException valueOf(int code, String message) {
		return new LogicException(code, message);
	}

	public static LogicException valueOfUnknow(String message) {
		return new LogicException(-255, message);
	}

	public LogicException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}
