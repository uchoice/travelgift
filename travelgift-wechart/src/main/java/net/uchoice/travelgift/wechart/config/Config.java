package net.uchoice.travelgift.wechart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Value("${wechart.appId}")
	private String appId;

	@Value("${wechart.token}")
	private String token;

	@Bean
	public GlobalConfig globalConfig() {
		GlobalConfig config = new GlobalConfig();
		config.setAppId(appId);
		config.setToken(token);
		return config;
	}
}
