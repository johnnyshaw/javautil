package com.johnny.encry.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
/**
 * 
* @ClassName: MD5 
* @Date 2014年9月24日 上午10:08:56 
* @Modify  
 */
public class MD5
{
  public static final int LENGTH_16 = 16;
  public static final int LENGTH_32 = 32;

  public static String encode(String s)
    throws NoSuchAlgorithmException, UnsupportedEncodingException
  {
    return encode(s, 16);
  }

  public static String encode(String s, int length)
    throws NoSuchAlgorithmException, UnsupportedEncodingException
  {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    StringBuffer hexValue = new StringBuffer();
    byte[] md5Bytes = md5.digest(s.getBytes("utf-8"));
    for (int i = 0; i < md5Bytes.length; i++) {
      int val = md5Bytes[i] & 0xFF;
      if (val < 16) {
        hexValue.append("0");
      }
      hexValue.append(Integer.toHexString(val));
    }
    if (length == 32) {
      return hexValue.toString();
    }
    return hexValue.toString().substring(8, 24);
  }

  public static String[] autoMD5()
    throws NoSuchAlgorithmException, UnsupportedEncodingException
  {
    String[] str = new String[2];

    Integer ranVal = Integer.valueOf(Math.abs(new Random().nextInt()) % 100000000);
    String hexValue = encode(ranVal.toString(), 16);
    str[0] = ranVal.toString();
    str[1] = hexValue.toString();
    return str;
  }

}