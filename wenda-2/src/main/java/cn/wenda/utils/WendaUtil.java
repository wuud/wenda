package cn.wenda.utils;

import java.security.MessageDigest;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class WendaUtil {
	
	/**
	 * 将数据转成JSON格式
	 */
	public static String getJSONString(String msg, int code) {
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toJSONString();
	}
	/**
	 * 将数据转成JSON格式
	 */
	public static String getJSONString(String msg, Map<String, Object> map) {
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			json.put(entry.getKey(), entry.getValue());
		}
		return json.toJSONString();
	}
	/**
	 * 将数据转成JSON格式
	 */
	public static String getJSONString(int code) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		return json.toJSONString();
	}

	/**
	 * MD5加密算法
	 * 
	 * @param key 要加密的字符串
	 * @return String 加密后的字符串
	 */
	public static String MD5(String key) {
		final Logger logger = LoggerFactory.getLogger(WendaUtil.class);
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = key.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			logger.error("生成MD5失败", e);
			return null;
		}
	}
}