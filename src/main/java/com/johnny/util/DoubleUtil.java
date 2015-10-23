package com.johnny.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleUtil {
	private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(8, RoundingMode.HALF_UP);
	public static String formatMoney(Double d, int point) {
		if(point < 0 )
			throw new IllegalArgumentException("小数点位数不合法:" + point);
		StringBuilder sb = new StringBuilder("###,##0");
		if(point > 0) {
			sb.append(".");
		}
		for(int i = 0; i < point; i++) {
			sb.append("0");
		}
		
		DecimalFormat df = new DecimalFormat(sb.toString());
		return df.format(d);
	}
	
	public static String format(Double d, int point) {
		if(point < 0 )
			throw new IllegalArgumentException("小数点位数不合法:" + point);
		String format = "0";
		if(point > 0)
			format = format + ".";
		for(int i=0; i< point ; i++){
			format = format + "0";
		}
		DecimalFormat df = new DecimalFormat(format); 
		return df.format(d);
	}
	
	/**
	 * 连除
	 * 
	 * @return v1/v2
	 */
	public static double divide(double v1, double... v2s) {
		return divide(DEFAULT_MATH_CONTEXT, v1, v2s);
	}
	
	public static double divide(MathContext mc, double v1, double... v2s) {
		BigDecimal b1 = new BigDecimal(v1);
		for (double d : v2s) {
			BigDecimal b2 = new BigDecimal(d);
			b1 = b1.divide(b2, mc.getPrecision(), mc.getRoundingMode());
		}
		return b1.doubleValue();
	}
	
	/**
	 * 连乘
	 * 
	 * @return v1*v2
	 */
	public static double multiply(double v1, double... v2s) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		for (double d : v2s) {
			BigDecimal b2 = new BigDecimal(Double.toString(d));
			b1 = b1.multiply(b2);
		}
		return b1.doubleValue();
	}
	
	public static String formatDouble(double d) {
		DecimalFormat df = new DecimalFormat("#.###################################");
		return df.format(d);
	}
}
