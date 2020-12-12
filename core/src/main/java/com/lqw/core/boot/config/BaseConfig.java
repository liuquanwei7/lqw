package com.lqw.core.boot.config;

import akka.actor.ActorSystem;
import com.lqw.core.security.AccountInvalidEventListener;
import com.lqw.core.service.SessionManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.EventListener;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.lqw.**.repository"})
@ComponentScans({@ComponentScan(basePackages = {"com.lqw.core.boot.config", "com.lqw.core.db" })})
@EntityScan(basePackages = {"com.lqw.core.entity"})
public class BaseConfig {
	@Bean(destroyMethod = "")
	@Scope("singleton")
	public ActorSystem actorSystem() {
		Config config = ConfigFactory.parseString("akka {\nactor {\ndefault-dispatcher{\nexecutor = \"fork-join-executor\"\nfork-join-executor{\nparallelism-min = 8\nparallelism-factor = 3.0\nparallelism-max = 10\ntask-peeking-mode = \"FIFO\"\n}\n}\n}\n}\n");
		ActorSystem result = ActorSystem.create("actor-event-system", config);
		return result;
	}

	@Bean
	public DispatcherServletPath dispatcherServletPath() {
		return new LqwDispatcherServletPath();
	}

	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/jsp/");
		bean.setSuffix(".jsp");
		bean.setOrder(0);
		return (ViewResolver)bean;
	}

	@Bean
	public AccountInvalidEventListener accountInvalidEventListener() {
		return new AccountInvalidEventListener();
	}

	@Bean
	public SessionManager sessionManager() {
		return new SessionManager();
	}

	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> servletListenerRegistrationBean() {
		HttpSessionEventPublisher publisher = new HttpSessionEventPublisher();
		ServletListenerRegistrationBean<HttpSessionEventPublisher> bean = new ServletListenerRegistrationBean((EventListener)publisher);
		return bean;
	}
}
