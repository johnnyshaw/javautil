package com.johnny.util;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtil {
	
	public static <T> T copyProperties(T orig, T dest, String[] properties) {
		for (String property : properties) {
			try {
				Object value = PropertyUtils.getProperty(orig, property);
				PropertyUtils.setProperty(dest, property, value);
			} catch (Exception e) {
			}
		}
		return dest;
	}
}
