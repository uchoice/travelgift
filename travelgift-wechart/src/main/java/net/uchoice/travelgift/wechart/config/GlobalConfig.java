package net.uchoice.travelgift.wechart.config;

public class GlobalConfig {

	private static GlobalConfig config = new GlobalConfig();
	
	private String appId;
	
	private String token;

	public static String getAppId() {
		return config.appId;
	}

	public void setAppId(String appId) {
		config.appId = appId;
	}

	public static String getToken() {
		return config.token;
	}

	public void setToken(String token) {
		config.token = token;
	}
}
