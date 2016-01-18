package com.xiche.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.xiche.web" })
public class RootConfig {
	
//	@Bean
//	public RemoteService remoteService() {
//		return new RemoteServiceImpl();
//	}
}
