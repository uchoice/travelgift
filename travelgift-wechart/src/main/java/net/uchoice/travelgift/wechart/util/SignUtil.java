package net.uchoice.travelgift.wechart.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;

import net.uchoice.travelgift.wechart.api.JsTicket;

public class SignUtil {

	/**
	 * 传入三个参数以及微信的token（静态自己设定）验证，
	 * 
	 * @param signature
	 *            签名用来核实最后的结果是否一致
	 * @param timestamp
	 *            时间标记
	 * @param nonce
	 *            随机数字标记
	 * @return 一个布尔值确定最后加密得到的是否与signature一致
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkSignature(String token, String signature, String timestamp, String nonce)
			throws NoSuchAlgorithmException {
		// 将传入参数变成一个String数组然后进行字典排序
		String[] arr = new String[] { token, timestamp, nonce };
		Arrays.sort(arr);
		// 创建一个对象储存排序后三个String的结合体
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}

		// 启动sha1加密法的工具
		MessageDigest md = null;
		String tmpStr = null;
		md = MessageDigest.getInstance("SHA-1");
		// md.digest()方法必须作用于字节数组
		byte[] digest = md.digest(content.toString().getBytes());
		// 将字节数组弄成字符串
		tmpStr = byteToStr(digest);
		content = null;

		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;

	}

	public static String signature(String noncestr, String timestamp, String url) throws NoSuchAlgorithmException {
		String string = "jsapi_ticket=" + JsTicket.get().ticket() + "&noncestr=" + noncestr + "×tamp=" + timestamp
				+ "&url=" + url;

		// 启动sha1加密法的工具
		MessageDigest md = null;
		String tmpStr = null;
		md = MessageDigest.getInstance("SHA-1");
		// md.digest()方法必须作用于字节数组
		byte[] digest = md.digest(string.getBytes());
		// 将字节数组弄成字符串
		tmpStr = byteToHex(digest);
		return tmpStr;

	}

	/**
	 * 将字节加工然后转化成字符串
	 * 
	 * @param digest
	 * @return
	 */
	private static String byteToStr(byte[] digest) {
		String strDigest = "";
		for (int i = 0; i < digest.length; i++) {
			// 将取得字符的二进制码转化为16进制码的的码数字符串
			strDigest += byteToHexStr(digest[i]);
		}
		return strDigest;
	}

	/**
	 * 把每个字节加工成一个16位的字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToHexStr(byte b) {
		// 转位数参照表
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		// 位操作把2进制转化为16进制
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(b >>> 4) & 0X0F];// XXXX&1111那么得到的还是XXXX
		tempArr[1] = Digit[b & 0X0F];// XXXX&1111那么得到的还是XXXX

		// 得到进制码的字符串
		String s = new String(tempArr);
		return s;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
