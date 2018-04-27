/**
 * 
 */
package net.uchoice.travelgift.wechart.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.uchoice.travelgift.wechart.config.GlobalConfig;
import net.uchoice.travelgift.wechart.util.HttpClientUtil;

/**
 * @author ruiliang.mrl
 *
 */
public class AccessToken {

	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);

	private static AccessToken token = new AccessToken();

	private long lastUpdateTime = 0;

	private String value;

	public String token() {
		if (lastUpdateTime == 0 || (System.currentTimeMillis() - lastUpdateTime) >= 7000000) {
			try {
				String response = HttpClientUtil
						.httpGet(String.format(Api.ACCESS_TOKEN, GlobalConfig.getAppId(), GlobalConfig.getAppScret()));
				JSONObject obj = JSON.parseObject(response);
				value = obj.getString("access_token");
				lastUpdateTime = System.currentTimeMillis();
			} catch (Exception e) {
				log.error("AccessToken get error", e);
			}
		}
		return value;
	}
	
	public static AccessToken get() {
		return token;
	}
}
