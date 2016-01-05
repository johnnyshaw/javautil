package com.johnny.pdf;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * 
 * 反射工具类
 *
 * 2015年9月12日
 */
public class ReflectUtils {
	private static Logger logger = Logger.getLogger(ReflectUtils.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object invokeGetMethod( Class claszz, Object o, String name) {
		Object ret = null;
		try {
			String[] names = name.split("/.");
			for (int i = 0; i < names.length; i++) {
				if(names.length>i){
					if(ret != null){
						 o = ret;
					}
					Method method2 = null;
					if(names[i].length()>7 && "(Other)".equals(names[i].substring(0, 7))){
						method2 = claszz.getMethod(names[i].substring(7));
					}else{
						method2 = claszz.getMethod("get" + firstCharUpperCase(names[i]));
					}					
					ret = method2.invoke(o);
					if(ret != null){
						claszz = ret.getClass();
					}else{
						return null;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}
	
	/**
	 * 首字母大写
	 * @param s
	 * @return
	 */
	private static String firstCharUpperCase(String s) {
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1, s.length()));
		return sb.toString();
	}

}