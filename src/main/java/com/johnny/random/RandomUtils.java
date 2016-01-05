package com.johnny.random;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 随机数生成类
 * @author licx
 *
 * 2015年8月26日
 */
public class RandomUtils
{
  private static final Random random = new Random();

  public static String[] random(int length, int num)
  {
    return buildRandom(length, num);
  }

  public static String random(int length)
  {
    return buildRandom(length);
  }

  public static String randombunch(int length, int num)
  {
    StringBuffer str = new StringBuffer();
    for (int i = 0; i < num; i++) {
      str.append(random(length));
      if (i != num - 1)
        str.append("-");
    }
    return str.toString();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
private static String[] buildRandom(int length, int num)
  {
    if ((num < 1) || (length < 1)) {
      return null;
    }
    Set tempRets = new HashSet(num);

    while (tempRets.size() < num) {
      tempRets.add(buildRandom(length));
    }

    String[] rets = new String[num];
    rets = (String[])tempRets.toArray(rets);
    return rets;
  }
//自动生成几位数子的验证码
  public static String randomLengthNum(int lenght){
	  StringBuffer s=new StringBuffer();
	  for(int i=0;i<lenght;i++){
		  	int	 a=(int)(Math.random()*10) ;
		  	s.append(String.valueOf(a));
	  }
	  return s.toString();
  }
  
  public static int buildIntRandom(int length)
  {
    String maxStr = StringUtils.rightPad("1", length + 1, '0');
    long max = Long.parseLong(maxStr);
    long i = Math.abs(random.nextLong()) % max;
    String rand = String.valueOf(i);
    return Integer.parseInt(rand);
  }

  public static int buildIntRandomBy(int length)
  {
    return (int)(Math.random() * length);
  }
  public static String buildRandom(int length)
  {
    String maxStr = StringUtils.rightPad("1", length + 1, '0');
    long max = Long.parseLong(maxStr);
    long i = random.nextLong();
    i = Math.abs(i) % max;
    String value = StringUtils.leftPad(String.valueOf(i), length, '0');
    return value;
  }
}