package com.lqw.core.security;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public abstract class LqwUserDetails implements UserDetails, CredentialsContainer {
	private static final long serialVersionUID = 5864269238005852275L;

	private String uuid;

	private boolean kickOut;

	private Date loginTime;

	private String reason;

	private String platform;

	private String sessionId;

	public LqwUserDetails(boolean kickOut, String platform) {
		this.kickOut = kickOut;
		this.platform = platform;
	}

	public boolean isKickOut() {
		return this.kickOut;
	}

	public void setKickOut(boolean kickOut) {
		this.kickOut = kickOut;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
