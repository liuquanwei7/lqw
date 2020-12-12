package com.lqw.core.security;

import org.springframework.context.ApplicationEvent;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class AccountInvalidEvent extends ApplicationEvent {
	private static final long serialVersionUID = 2682886235172858463L;

	private long userId;

	private String reason;

	public AccountInvalidEvent(Object source) {
		super(source);
	}

	public AccountInvalidEvent(Object source, String reason) {
		super(source);
	}

	public static AccountInvalidEvent valueOf(long userId) {
		AccountInvalidEvent result = new AccountInvalidEvent(Long.valueOf(userId));
		result.userId = userId;
		return result;
	}

	public static AccountInvalidEvent valueOf(long userId, String reason) {
		AccountInvalidEvent result = new AccountInvalidEvent(Long.valueOf(userId));
		result.userId = userId;
		result.reason = reason;
		return result;
	}

	public long getUserId() {
		return this.userId;
	}

	public String getReason() {
		return this.reason;
	}
}
