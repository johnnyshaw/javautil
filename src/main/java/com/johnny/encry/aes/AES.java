package com.johnny.encry.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
* 
* @ClassName: AES
* @Description: AES加密解密工具类
* @Date 2014年9月24日 上午10:06:54 
* @Modify  
 */
public class AES {
	private static final String ALGORITHM = "AES";

	/**
	 * BASE64解密 <功能详细描述>
	 * 
	 * @param key
	 *            key
	 * @return byte[]
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密 <功能详细描述>
	 * 
	 * @param key
	 *            key
	 * @return String
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * 加密 <功能详细描述>
	 * 
	 * @param data
	 *            data 要加密的文本
	 * @param rawKey
	 *            rawKey 必须为16位字符
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	public static String encrypt(String data, String rawKey) {
		byte[] key = rawKey.getBytes();
		// Instantiate the cipher
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encrypted = cipher.doFinal(data.getBytes());
			return encryptBASE64(encrypted);
		} catch (Exception e) {
			// App.log.info("AES", "获取加密串出错," + e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 解密 <功能详细描述>
	 * 
	 * @param encrypted
	 *            要解密的字符 encrypted
	 * @param rawKey
	 *            rawKey 必须为16位且是加密时用的密钥
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	public static String decrypt(String encrypted, String rawKey) {
		try {
			byte[] tmp = decryptBASE64(encrypted);
			byte[] key = rawKey.getBytes();

			SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			byte[] decrypted = cipher.doFinal(tmp);
			return new String(decrypted);
		} catch (Exception e) {
			// App.log.info("AES", "获取解密串出错," + e.getMessage());
			e.printStackTrace();
			return "";
		}

	}
}