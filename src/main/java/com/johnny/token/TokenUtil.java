package com.johnny.token;

import java.util.UUID;


/**
 * 
 * Token工具类
 *
 * 2015年8月26日
 */
public class TokenUtil {

	/**
	 * 通过uuid生成一个token
	 * 
	 * @return token值
	 */
	public static String generateToken() {
		return new StringBuilder(100).append(UUID.randomUUID().toString()).toString();
	}
}
