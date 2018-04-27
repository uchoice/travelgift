package net.uchoice.travelgift.wechart.config;

public class GlobalConfig {

	private static GlobalConfig config = new GlobalConfig();
	
	private String appId;
	
	private String token;
	
	private String appScret;

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

	public static String getAppScret() {
		return config.appScret;
	}

	public void setAppScret(String appScret) {
		config.appScret = appScret;
	}
}
