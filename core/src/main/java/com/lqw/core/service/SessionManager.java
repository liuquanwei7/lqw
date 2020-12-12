package com.lqw.core.service;

import com.google.common.collect.Lists;
import com.lqw.core.security.XinUserDetails;
import com.lqw.core.utils.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.security.web.session.SessionInformationExpiredEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class SessionManager implements ApplicationListener<ApplicationEvent> {
	private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

	private SessionRegistry sessionRegistry;

	public boolean isOnline(long pingtai_id, String zhanghao) {
		Collection<? extends GrantedAuthority> aaalists = new ArrayList<>();
		XinUserDetails principal = new XinUserDetails(pingtai_id, zhanghao, "", true, true, true, true, aaalists);
		List<SessionInformation> allSessions = this.sessionRegistry.getAllSessions(principal, false);
		return Checker.notEmpty(allSessions);
	}

	public int onlineSize() {
		int result = 0;
		List<Object> allPrincipals = this.sessionRegistry.getAllPrincipals();
		for (Object principal : allPrincipals) {
			List<SessionInformation> allSessions = this.sessionRegistry.getAllSessions(principal, false);
			if (Checker.notEmpty(allSessions)) {
				result++;
			}
		}
		return result;
	}

	public List<Long> getOnlineList() {
		List<Long> pingtaiIds = Lists.newArrayList();
		List<Object> allPrincipals = this.sessionRegistry.getAllPrincipals();
		for (Object principal : allPrincipals) {
			List<SessionInformation> allSessions = this.sessionRegistry.getAllSessions(principal, false);
			if (Checker.notEmpty(allSessions)) {
				SessionInformation information = allSessions.get(0);
				if (Checker.notNull(information) && Checker.notNull(information.getPrincipal())) {
					XinUserDetails details = (XinUserDetails)information.getPrincipal();
					pingtaiIds.add(Long.valueOf(details.getId()));
				}
			}
		}
		return pingtaiIds;
	}

	public SessionRegistry getSessionRegistry() {
		return this.sessionRegistry;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof SessionInformationExpiredEvent) {
			logger.error("失效SessionId: "+ ((SessionInformationExpiredEvent)event).getSessionInformation().getSessionId());
		}
		if (event instanceof HttpSessionDestroyedEvent) {
			logger.error("销毁SessionId: "+ ((HttpSessionDestroyedEvent)event).getSession().getId());
		}
	}
}
