/**
 * 
 */
package net.uchoice.travelgift.wechart.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.uchoice.travelgift.wechart.util.HttpClientUtil;

/**
 * @author ruiliang.mrl
 *
 */
public class JsTicket {

	private static final Logger log = LoggerFactory.getLogger(JsTicket.class);

	private static JsTicket ticket = new JsTicket();

	private long lastUpdateTime = 0;

	private String value;

	public String ticket() {
		if (lastUpdateTime == 0 || (System.currentTimeMillis() - lastUpdateTime) >= 7000000) {
			try {
				String response = HttpClientUtil.httpGet(String.format(Api.JS_TICKET, AccessToken.get().token()));
				JSONObject obj = JSON.parseObject(response);
				value = obj.getString("ticket");
				lastUpdateTime = System.currentTimeMillis();
			} catch (Exception e) {
				log.error("JsTicket get error", e);
			}
		}
		return value;
	}

	public static JsTicket get() {
		return ticket;
	}
}
