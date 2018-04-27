/**
 * 
 */
package net.uchoice.travelgift.wechart.api;

/**
 * @author ruiliang.mrl
 *
 */
public class Api {

	public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	public static final String JS_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

}
