package com.lqw.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class AccountInvalidEventListener implements ApplicationListener<ApplicationEvent> {
	private static final Logger logger = LoggerFactory.getLogger(AccountInvalidEventListener.class);

	@Autowired(required = false)
	private IAccountService accountService;

	private SessionRegistry sessionRegistry;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AccountInvalidEvent) {
			long userId = ((AccountInvalidEvent)event).getUserId();
			XinUserDetails user = this.accountService.loadUserDetails(userId);
			String username = user.getUsername();
			logger.error("用户信息更改，用户[{},{}]失效", Long.valueOf(userId), username);
							Collection<? extends GrantedAuthority> aaalists = new ArrayList<>();
			XinUserDetails principal = new XinUserDetails(userId, username, "", true, true, true, true, aaalists);
			List<SessionInformation> sessionList = this.sessionRegistry.getAllSessions(principal, false);
			if (sessionList != null) {
				for (SessionInformation sessionInformation : sessionList) {
					if (sessionInformation.getPrincipal() instanceof LqwUserDetails) {
						LqwUserDetails wud = (LqwUserDetails)sessionInformation.getPrincipal();
						wud.setReason(((AccountInvalidEvent)event).getReason());
					}
					sessionInformation.expireNow();
				}
			}
		}
	}

	public IAccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	public SessionRegistry getSessionRegistry() {
		return this.sessionRegistry;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
}
