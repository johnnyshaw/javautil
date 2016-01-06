package com.johnny.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 
 * @ClassName: DateTools
 * @Description: 日期工具类
 * @Modify
 */
public class DateTools {
	/** 时间格式 : yyyy/MM/dd HH:mm */
	public static final String PATTERN = "yyyy/MM/dd HH:mm";

	/** 时间格式 : yyyyMMddHHmm */
	public static final String DATE_FORMAT_12 = "yyyyMMddHHmm";

	/** 时间格式 : yyyyMMddHHmmss */
	public static final String DATE_FORMAT_14 = "yyyyMMddHHmmss";

	/** 时间格式 : yyyyMM */
	public static final String DATE_FORMAT_6 = "yyyyMM";

	/** 时间格式 : yyyy-MM */
	public static final String DATE_FORMAT_7 = "yyyy-MM";

	/** 时间格式 : yyyyMMdd */
	public static final String DATE_FORMAT2_8 = "yyyyMMdd";

	/** 时间格式 : yy.MM.dd */
	public static final String DATE_FORMAT_8 = "yy.MM.dd";

	/** 时间格式 : [yyyy-MM-dd hh:mm:ss] */
	public static final String DATE_FORMAT_21 = "[yyyy-MM-dd hh:mm:ss]";

	/** 时间格式 : [yyyy-MM-dd HH:mm:ss] */
	public static final String DATE_FORMAT_24HOUR_21 = "[yyyy-MM-dd HH:mm:ss]";

	/** 时间格式 ：yyyy-MM-dd hh:mm */
	public static final String DATE_FORMAT_16 = "yyyy-MM-dd hh:mm";

	/** 时间格式 : yy-MM-dd hh:mm */
	public static final String DATE_PATTERN_14 = "yy-MM-dd hh:mm";

	/** 时间格式 : yy-MM-dd hh:mm */
	public static final String DATE_FORMAT_24HOUR_16 = "yyyy-MM-dd HH:mm";

	/** 时间格式 : yy-MM-dd HH:mm */
	public static final String DATE_PATTERN_24HOUR_14 = "yy-MM-dd HH:mm";

	/** 时间格式 ：yyyy-MM-dd */
	public static final String DATE_FORMAT_10 = "yyyy-MM-dd";

	/** 时间格式:年 */
	public static final String DATE_FORMAT_YEAR = "yyyy";

	/** 时间格式:月 */
	public static final String DATE_FORMAT_MONTH = "MM";

	/** 时间格式:日 */
	public static final String DATE_FORMAT_DAY = "dd";

	/** 时间格式 : yyyy-MM-dd HH:mm:ss */
	public static final String DATE_FORMAT_24HOUR_19 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_POINT = "yyyy.MM.dd";
	/** 年 */
	public static final int YEAR = 0;

	/** 月 */
	public static final int MONTH = 1;

	/** 星期 */
	public static final int WEEK = 2;

	/** DAY */
	public static final int DAY = 3;

	/** HOUR */
	public static final int HOUR = 4;

	/** MINUTE */
	public static final int MINUTE = 5;

	/** SECOND */
	public static final int SECOND = 6;

	/** MILLISECOND */
	public static final int MILLISECOND = 7;

	/** MINUTEOFDAY */
	public static final int MINUTEOFDAY = 8;

	/** Automatically generated javadoc for: ONE_MINUTE */
	private static final int ONE_MINUTE = 60;

	/** Automatically generated javadoc for: ONE_THOUSAND */
	private static final int ONE_THOUSAND = 1000;

	/** Automatically generated javadoc for: ONE_HOUR */
	private static final int ONE_HOUR = 60;

	private static final HashMap<CompareDateFormate, int[]> MAPS = new HashMap<CompareDateFormate, int[]>();
	private static int weeks = 0;

	/**
	 * 私有构造器
	 */
	public DateTools() {

	}

	/**
	 * 在Locale下转换时间的格式为指定格式
	 * 
	 * @param time
	 *            time
	 * @param oldpattern
	 *            oldpattern
	 * @param dateTimeStyle
	 *            DateFormat.LONG/DateFormat.FULL
	 * @param currentLocale
	 *            currentLocale
	 * @return String
	 */
	public static String getTimeByLocale(String time, String oldpattern,
			int dateTimeStyle, Locale currentLocale) {
		String oldtime = "";

		// 参数有效性检查
		if (null == oldpattern) {
			throw new IllegalArgumentException("the old pattern is null");
		}

		// 检查传入时间和格式是否一致
		SimpleDateFormat olddatepattern = new SimpleDateFormat(oldpattern,
				currentLocale);
		Date now = null;
		try {
			now = olddatepattern.parse(time);

			// 用原来的pattern解析日期，再和原来的比较，由此来检查时间是否合法
			oldtime = olddatepattern.format(now);
			if (!oldtime.equals(time)) {
				throw new IllegalArgumentException("using Illegal time");
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException("using [" + oldpattern
					+ "] parse [" + time + "] failed");
		}

		// 格式转换
		DateFormat newdatepattern = DateFormat.getDateTimeInstance(
				dateTimeStyle, dateTimeStyle, currentLocale);
		return newdatepattern.format(now);
	}

	/**
	 * 按要求转化时间的显示格式 参数：oldpattern，旧日期格式， 如:yyyyMMddhhmmss 格式描述符的含义参见JDK
	 * simpleDateFormat类 newpattern，新日期格式
	 * 
	 * @param time
	 *            time
	 * @param oldpattern
	 *            oldpattern
	 * @param newpattern
	 *            newpattern
	 * @return [参数说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String timeTransform(String time, String oldpattern,
			String newpattern) {
		String oldtime = null;
		if (null == oldpattern || null == newpattern) {
			return time;
		}

		SimpleDateFormat olddatepattern = new SimpleDateFormat(oldpattern,
				new Locale("EN"));
		Date date = null;
		try {
			date = olddatepattern.parse(time);

			// 用原来的pattern解析日期，再和原来的比较，由此来检查时间是否合法
			oldtime = olddatepattern.format(date);
			if (!oldtime.equals(time)) {
				return time;
			}
		} catch (ParseException e) {
			return time;
		}
		SimpleDateFormat newdatepattern = new SimpleDateFormat(newpattern,
				new Locale("EN"));

		return newdatepattern.format(date);
	}

	/**
	 * 获取指定格式的当前日期 参数：pattern， 日期格式，如:yyyyMMddhhmmss 格式描述符的含义参见JDK
	 * simpleDateFormat类
	 * 
	 * @param pattern
	 *            pattern
	 * @return [参数说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String getCurrentDate(String pattern) {
		if (null == pattern) {
			throw new IllegalArgumentException("input string parameter is null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("EN"));
		Date now = new Date();
		return sdf.format(now);
	}

	/**
	 * 得到指定格式的Date型当前时间
	 */
	public static Date getCurrentTime(String formater) {
		return formtDate(getCurrentTime(), formater);
	}

	/**
	 * 得到当前时间的字符格式
	 */
	public static String getCurrentTime() {
		return format(new Date());
	}

	/**
	 * <获取java.sql.Date类型的当前日期 返回：java.sql.Date 如2005-10-19> <功能详细描述>
	 * 
	 * @return java.sql.Date [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static java.sql.Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return new java.sql.Date(cal.getTimeInMillis());
	}

	/**
	 * 获取java.sql.Timestamp类型的当前日期。 保护年月日时分秒 返回：java.sql.Timestamp 如2005-10-19
	 * 18:20:07
	 * 
	 * @return Timestamp [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Timestamp getCurrentTimestamp() {
		Timestamp t = new Timestamp(new Date().getTime());
		return t;
	}

	/**
	 * <一句话功能简述> <功能详细描述>
	 * 
	 * @return long [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static long getCurrentTimeLong() {
		return new Date().getTime();
	}

	/**
	 * 将日期长整型转换成字符串 参数：time，long，从格林威治时间：1970年1月1日0点起的毫秒数 pattern, String,
	 * 转换的目标格式 <功能详细描述>
	 * 
	 * @param time
	 *            time
	 * @param pattern
	 *            pattern
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String long2TimeStr(long time, String pattern) {
		if (null == pattern) {
			throw new IllegalArgumentException(
					"pattern parameter can not be null");
		}
		Date dt = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("EN"));
		return sdf.format(dt);
	}

	/**
	 * 将日期型转换成字符串 参数：time，Date pattern, String, 转换的目标格式 <功能详细描述>
	 * 
	 * @param time
	 *            time
	 * @param pattern
	 *            pattern
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String date2TimeStr(Date time, String pattern) {
		if (null == pattern) {
			throw new IllegalArgumentException(
					"pattern parameter can not be null");
		}
		if (null == time) {
			throw new IllegalArgumentException("time parameter can not be null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("EN"));
		return sdf.format(time);
	}

	/**
	 * 将日期增加一个增量，目前只能是，年，月，周，日，时、分、秒、毫秒 参数：date, long， 原始时间 delta，int，增量的大小
	 * unit, int, 增量的单位，YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, MILLISECOND
	 * 返回：long，从格林威治时间：1970年1月1日0点起的毫秒数
	 * 
	 * @param date
	 *            date
	 * @param delta
	 *            delta
	 * @param unit
	 *            unit
	 * @return long [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static long addDate(long date, int delta, int unit) {
		if ((unit < YEAR) || (unit > MILLISECOND)) {
			throw new IllegalArgumentException(
					"time unit must in [YEAR, MONTH, WEEK, DAY, HOUR, MINUTE, SECOND, MILLISECOND], others not support");
		}
		Date dt = new Date(date);
		Calendar calendar = getLocalCalendar(dt);

		// 增加增量
		switch (unit) {
		case YEAR:
			calendar.add(Calendar.YEAR, delta);
			break;
		case MONTH:
			calendar.add(Calendar.MONTH, delta);
			break;
		case WEEK:
			calendar.add(Calendar.DAY_OF_WEEK, delta);
			break;
		case DAY:
			calendar.add(Calendar.DAY_OF_MONTH, delta);
			break;
		case HOUR:
			calendar.add(Calendar.HOUR, delta);
			break;
		case MINUTE:
			calendar.add(Calendar.MINUTE, delta);
			break;
		case SECOND:
			calendar.add(Calendar.SECOND, delta);
			break;
		case MILLISECOND:
			calendar.add(Calendar.MILLISECOND, delta);
			/* falls through */
		default:
			break;
		}

		// 获取新的时间，并转换成长整形
		Date ndt = calendar.getTime();
		return ndt.getTime();
	}

	/**
	 * 将日期增加一个增量，目前只能是，年，月，周，日，时，分，秒，毫秒 参数：date, long， 原始时间 delta，int，增量的大小
	 * unit, int, 增量的单位，YEAR, MONTH, WEEK, DAY, HOUR, MINUTE, SECOND,
	 * MILLISECOND pattern, String, 转换的目标格式 返回：String，指定格式的日期字符串
	 * 
	 * @param date
	 *            date
	 * @param delta
	 *            delta
	 * @param unit
	 *            unit
	 * @param pattern
	 *            pattern
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String addDate(long date, int delta, int unit, String pattern) {
		if (null == pattern) {
			throw new IllegalArgumentException(
					"pattern parameter can not be null");
		}
		return long2TimeStr(addDate(date, delta, unit), pattern);
	}

	public static Date addSecond(Date d, long seconds) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);

		final long DAY_TIME = (24 * 60 * 60);

		ca.add(Calendar.DAY_OF_MONTH, (int) (seconds / DAY_TIME));
		ca.add(Calendar.SECOND, (int) (seconds % DAY_TIME));

		return ca.getTime();
	}

	public static Date addDay(Date d, double days) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);

		final long DAY_TIME = (24 * 60 * 60 * 1000);
		long milliseconds = (long) (DAY_TIME * days);

		ca.add(Calendar.DAY_OF_MONTH, (int) (milliseconds / DAY_TIME));
		ca.add(Calendar.MILLISECOND, (int) (milliseconds % DAY_TIME));

		return ca.getTime();
	}

	/**
	 * 将字符串转换成日期长整形 参数：time，String，日期字符串 pattern, String, 解析的格式 返回：long，日期长整形
	 * <功能详细描述>
	 * 
	 * @param time
	 *            time
	 * @param pattern
	 *            pattern
	 * @return long [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static long timeStr2Long(String time, String pattern) {
		return timeStr2Date(time, pattern).getTime();
	}

	/**
	 * 将字符串转换成日期形 参数：time，String，日期字符串 pattern, String, 解析的格式 返回：Date，日期形
	 * <功能详细描述>
	 * 
	 * @param time
	 *            time
	 * @param pattern
	 *            pattern
	 * @return Date [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Date timeStr2Date(String time, String pattern) {
		if (null == time) {
			throw new IllegalArgumentException("time parameter can not be null");
		}
		if (null == pattern) {
			throw new IllegalArgumentException(
					"pattern parameter can not be null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("EN"));
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			throw new IllegalArgumentException("using [" + pattern
					+ "] parse [" + time + "] failed");
		}
	}

	/**
	 * 获取日期字符串的某一部分 参数：date，有效的日期字符串 pattern，日期格式字符串
	 * part，时间部分的指示符，只能是：YEAR,MONTH,WEEK,DAY,HOUR,MINUTE,SECOND，MILLISECOND
	 * 
	 * @param date
	 *            date
	 * @param pattern
	 *            pattern
	 * @param part
	 *            part
	 * @return int [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static int getDatePart(String date, String pattern, int part) {
		if (null == date) {
			throw new IllegalArgumentException("date parameter is null");
		}
		if (null == pattern) {
			throw new IllegalArgumentException("pattern parameter is null");
		}
		if ((part < YEAR) || (part > MINUTEOFDAY)) {
			throw new IllegalArgumentException(
					"the part parameter must be in [YEAR,MONTH, DAY, HOUR, MINUTE, SECOND]");
		}
		Date dt = timeStr2Date(date, pattern);
		return getDatePart(dt, part);
	}

	/**
	 * 获取日期的某一部分 参数：date，有效的日期类型 part，时间部分的指示符，
	 * 只能是：YEAR,MONTH,WEEK,DAY,HOUR,MINUTE,SECOND，MILLISECOND
	 * 
	 * @param date
	 *            date
	 * @param part
	 *            part
	 * @return int [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static int getDatePart(Date date, int part) {
		if (null == date) {
			throw new IllegalArgumentException("date parameter is null");
		}
		if ((part < YEAR) || (part > MINUTEOFDAY)) {
			throw new IllegalArgumentException(
					"the part parameter must be in [YEAR,MONTH, DAY, HOUR, MINUTE, SECOND]");
		}
		Calendar calendar = getLocalCalendar(date);
		int result = 0;
		switch (part) {
		case YEAR:
			result = calendar.get(Calendar.YEAR);
			break;
		case MONTH:
			result = calendar.get(Calendar.MONTH);
			break;
		case WEEK:
			result = calendar.get(Calendar.DAY_OF_WEEK);
			break;
		case DAY:
			result = calendar.get(Calendar.DAY_OF_MONTH);
			break;
		case HOUR:
			result = calendar.get(Calendar.HOUR_OF_DAY);
			break;
		case MINUTE:
			result = calendar.get(Calendar.MINUTE);
			break;
		case SECOND:
			result = calendar.get(Calendar.SECOND);
			break;
		case MILLISECOND:
			result = calendar.get(Calendar.MILLISECOND);
			break;
		case MINUTEOFDAY:
			result = calendar.get(Calendar.HOUR_OF_DAY) * ONE_HOUR
					+ calendar.get(Calendar.MINUTE);
			/* falls through */
		default:
			break;
		}
		return result;
	}

	/**
	 * 获取下一个周期的开始时间 参数：date，String类型，有效的时间 pattern，String类型， 时间格式字符串
	 * part，int类型，周期类型，可以是年、月、日、周
	 * 
	 * @param galeday
	 *            galeday
	 * @param pattern
	 *            pattern
	 * @param part
	 *            part
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String getNextPeriodTime(Date galeday, String pattern,
			int part) {
		if (null == galeday) {
			throw new IllegalArgumentException("date parameter is null");
		}
		if (null == pattern) {
			throw new IllegalArgumentException("pattern parameter is null");
		}
		if ((part < YEAR) || (part > DAY)) {
			throw new IllegalArgumentException(
					"the part parameter must be in [YEAR,MONTH, WEEK, DAY]");
		}
		String result = null;
		Calendar caldeduct = getLocalCalendar(galeday);
		Calendar calnow = getLocalCalendar(new Date());
		switch (part) {
		case DAY: // 扣费周期为每天
			return recursiveGet(caldeduct, calnow, pattern,
					Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.HOUR);
		case WEEK: // 周期为每周
			return recursiveGetWeek(caldeduct, calnow, pattern,
					Calendar.DAY_OF_WEEK, Calendar.DAY_OF_MONTH, 0,
					Calendar.DAY_OF_WEEK);
		case YEAR: // 周期为每年
			return recursiveGet(caldeduct, calnow, pattern, Calendar.YEAR,
					Calendar.MONTH, Calendar.MONTH);
		case MONTH: // 周期为每月
			return recursiveGet(caldeduct, calnow, pattern, Calendar.MONTH,
					Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH);
		default:
			result = "unsupport period : " + String.valueOf(part);
			/* falls through */
			break;
		}
		return result;
	}

	private static String recursiveGetWeek(Calendar caldeduct, Calendar calnow,
			String pattern, int largepart, int part, int gap, int step) {
		int deduct = caldeduct.get(step);
		int now = calnow.get(step);
		if (step == Calendar.DAY_OF_WEEK) {
			gap = deduct - now;
		}
		if (deduct > now) {
			calnow.add(step, gap);
			return DateTools.date2TimeStr(calnow.getTime(), pattern);
		} else if (deduct < now) {
			calnow.add(step, 7 + gap);
			return DateTools.date2TimeStr(calnow.getTime(), pattern);
		} else {
			switch (step) {
			case Calendar.DAY_OF_WEEK:
				step = Calendar.HOUR;
				break;
			case Calendar.HOUR:
				step = Calendar.MINUTE;
				break;
			case Calendar.MINUTE:
				step = Calendar.SECOND;
				break;
			case Calendar.SECOND:
				step = Calendar.MILLISECOND;
				break;
			case Calendar.MILLISECOND:
				return date2TimeStr(calnow.getTime(), pattern);
			default:
				break;
			}
			return recursiveGetWeek(caldeduct, calnow, pattern, largepart,
					part, gap, step);
		}
	}

	private static String recursiveGet(Calendar caldeduct, Calendar calnow,
			String pattern, int largepart, int part, int step) {

		int deduct = caldeduct.get(step);
		int now = calnow.get(step);
		if (deduct > now) {
			calnow.set(part, caldeduct.get(part));
			if (largepart == Calendar.YEAR) {
				calnow.set(Calendar.DAY_OF_MONTH,
						caldeduct.get(Calendar.DAY_OF_MONTH));
			}
			return DateTools.date2TimeStr(calnow.getTime(), pattern);
		} else if (deduct < now) {
			calnow.add(largepart, 1);
			calnow.set(part, caldeduct.get(part));
			if (largepart == Calendar.YEAR) {
				calnow.set(Calendar.DAY_OF_MONTH,
						caldeduct.get(Calendar.DAY_OF_MONTH));
			}
			return DateTools.date2TimeStr(calnow.getTime(), pattern);
		} else {
			switch (step) {
			case Calendar.YEAR:
				step = Calendar.MONTH;
				break;
			case Calendar.MONTH:
				step = Calendar.DATE;
				break;
			case Calendar.DAY_OF_MONTH:
				step = Calendar.HOUR;
				break;
			case Calendar.HOUR:
				step = Calendar.MINUTE;
				break;
			case Calendar.MINUTE:
				step = Calendar.SECOND;
				break;
			case Calendar.SECOND:
				step = Calendar.MILLISECOND;
				break;
			case Calendar.MILLISECOND:
				return date2TimeStr(calnow.getTime(), pattern);
			default:
				break;
			}
			return recursiveGet(caldeduct, calnow, pattern, largepart, part,
					step);
		}
	}

	/**
	 * 获得东八时区的日历，并设置日历的当前日期 参数：date，Date，日期型 <功能详细描述>
	 * 
	 * @param date
	 *            date
	 * @return Calendar [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Calendar getLocalCalendar(Date date) {
		// 设置为GMT+08:00时区
		String[] ids = TimeZone.getAvailableIDs(8 * ONE_HOUR
				* ONE_MINUTE * ONE_THOUSAND);
		if (0 == ids.length) {
			throw new IllegalArgumentException(
					"get id of GMT+08:00 time zone failed");
		}
		// 创建Calendar对象，并设置为指定时间
		Calendar calendar = new GregorianCalendar(TimeZone.getDefault());

		// 设置成宽容方式
		if (!calendar.isLenient()) {
			calendar.setLenient(true);
		}
		// 设置SUNDAY为每周的第一天
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);

		// 设置日历的当前时间
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 获取当月的开始时间 <功能详细描述>
	 * 
	 * @param date
	 *            date
	 * @return Date [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Date getBeginTimeOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// calendar.add(Calendar.MONTH, -1);

		// 当月第1天日期
		Date firstDate = calendar.getTime();
		return firstDate;
	}

	/**
	 * 获取当月的结束时间 <功能详细描述>
	 * 
	 * @param date
	 *            date
	 * @return Date [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Date getEndTimeOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		// 当月最后一天日期
		Date lastDate = calendar.getTime();
		return lastDate;
	}

	/**
	 * 
	 * <一句话功能简述> <功能详细描述>
	 * 
	 * @author xKF16712
	 * @version [版本号, Dec 1, 2011]
	 * @see [相关类/方法]
	 * @since [产品/模块版本]
	 */
	public static enum CompareDateFormate {
		/** 枚举 */
		year, month, day, hour, minute, second,

		/** 枚举 */
		yyyyMMddhhmmss, yyyyMMddhhmm, yyyyMMddhh, yyyyMMdd, yyyyMM,

		/** 枚举 */
		MMddhhmmss, MMddhhmm, MMddhh, MMdd, ddhhmmss, ddhhmm, ddhh, hhmmss, hhmm, mmss
	}

	/**
	 * asd
	 */
	static {
		MAPS.put(CompareDateFormate.year, new int[] { Calendar.YEAR });
		MAPS.put(CompareDateFormate.month, new int[] { Calendar.MONTH });
		MAPS.put(CompareDateFormate.day, new int[] { Calendar.DATE });
		MAPS.put(CompareDateFormate.hour, new int[] { Calendar.HOUR_OF_DAY });
		MAPS.put(CompareDateFormate.minute, new int[] { Calendar.MINUTE });
		MAPS.put(CompareDateFormate.second, new int[] { Calendar.SECOND });

		MAPS.put(CompareDateFormate.yyyyMMddhhmmss, new int[] { Calendar.YEAR,
				Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY,
				Calendar.MINUTE, Calendar.SECOND });
		MAPS.put(CompareDateFormate.yyyyMMddhhmm, new int[] { Calendar.YEAR,
				Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY,
				Calendar.MINUTE });
		MAPS.put(CompareDateFormate.yyyyMMddhh, new int[] { Calendar.YEAR,
				Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY });
		MAPS.put(CompareDateFormate.yyyyMMdd, new int[] { Calendar.YEAR,
				Calendar.MONTH, Calendar.DATE });
		MAPS.put(CompareDateFormate.yyyyMM, new int[] { Calendar.YEAR,
				Calendar.MONTH });

		MAPS.put(CompareDateFormate.MMddhhmmss, new int[] { Calendar.MONTH,
				Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE,
				Calendar.SECOND });
		MAPS.put(CompareDateFormate.MMddhhmm, new int[] { Calendar.MONTH,
				Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE });
		MAPS.put(CompareDateFormate.MMddhh, new int[] { Calendar.MONTH,
				Calendar.DATE, Calendar.HOUR_OF_DAY });
		MAPS.put(CompareDateFormate.MMdd, new int[] { Calendar.MONTH,
				Calendar.DATE });

		MAPS.put(CompareDateFormate.ddhhmmss, new int[] { Calendar.DATE,
				Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND });
		MAPS.put(CompareDateFormate.ddhhmm, new int[] { Calendar.DATE,
				Calendar.HOUR_OF_DAY, Calendar.MINUTE });
		MAPS.put(CompareDateFormate.ddhh, new int[] { Calendar.DATE,
				Calendar.HOUR_OF_DAY });

		MAPS.put(CompareDateFormate.hhmmss, new int[] { Calendar.HOUR_OF_DAY,
				Calendar.MINUTE, Calendar.SECOND });
		MAPS.put(CompareDateFormate.hhmm, new int[] { Calendar.HOUR_OF_DAY,
				Calendar.MINUTE });
		MAPS.put(CompareDateFormate.mmss, new int[] { Calendar.MINUTE,
				Calendar.SECOND });
	}

	/**
	 * 根据CompareFields的格式（如只比较年月）比较两个日期先后，
	 * 
	 * 在比较字段内，若返回1，表示date1在date2之后，返回-1，表示date1在date2之前，0表示两者相等
	 * 
	 * @param date1
	 *            date1
	 * @param date2
	 *            date2
	 * @param cdf
	 *            cdf
	 * @return int [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static int compare(Date date1, Date date2, CompareDateFormate cdf) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);

		int[] form = MAPS.get(cdf);
		for (int field : form) {
			int t1 = c1.get(field);
			int t2 = c2.get(field);
			if (t1 > t2) {
				return 1;
			} else if (t1 < t2) {
				return -1;
			}
		}

		return 0;
	}

	/**
	 * 计算传入时间到（dept+传入时间）的剩余时间
	 * 
	 * @param minute
	 *            分钟
	 * @param second
	 *            秒
	 * @param dept
	 *            往后累加的分钟数
	 * @return String[]
	 */
	public static String[] getSpareTime(int minute, int second, int dept) {
		String[] result = new String[2];

		// 一分钟60秒用于计算剩余秒数
		int total = 60;
		int temMin = 0;
		int temSec = 0;

		// 只有处在当前时间 与 当前时间+累加的分钟 之间的，才计算剩余时间
		if (dept > minute) {
			if (second == 0) {
				temMin = dept - minute;
				temSec = second;// second==0
			} else {
				temMin = dept - minute - 1;
				temSec = total - second;
			}
		}
		result[0] = (temMin < 10) ? "0" + temMin : String
				.valueOf(temMin);
		result[1] = (temSec < 10) ? "0" + temSec : String
				.valueOf(temSec);
		return result;
	}

	/**
	 * 计算时间差 <功能详细描述>
	 * 
	 * @param startTime
	 *            startTime
	 * @param endTime
	 *            endTime
	 * @return long
	 * @see [类、类#方法、类#成员]
	 */
	public static long getTimeDifference(long startTime, long endTime) {
		if (endTime > startTime) {
			return endTime - startTime;
		} else {
			return 0;
		}
	}

	/**
	 * 格式化成系统常用日期格式：yyyy-MM-dd HH:mm:ss <功能详细描述>
	 * 
	 * @param date
	 *            date
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期 <功能详细描述>
	 * 
	 * @param date
	 *            date
	 * @param formatStr
	 *            formatStr
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String format(Date date, String formatStr) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sf = new SimpleDateFormat(formatStr);
		return sf.format(date);
	}

	/**
	 * 把字符串格式化日期
	 */
	public static Date formtDate(String dateStr, String formater) {
		formater = (null == formater) ? "yyyy-MM-dd HH:mm:ss" : formater;
		DateFormat formatter = new SimpleDateFormat(formater);
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取下个月开始时刻 <功能详细描述>
	 * 
	 * @param date
	 *            date
	 * @return Date [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Date getBeginOfNextMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		// 月份加1
		c.add(Calendar.MONTH, 1);

		return new Date(c.getTimeInMillis());
	}

	/**
	 * 月份减1
	 * 
	 * @Title: getBeginOfNextMonth
	 * @Description: TODO
	 * @Param @param date
	 * @Param @return
	 * @Return Date
	 * @Throws
	 */
	public static Date getBeginOfUpMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		// 月份减1
		c.add(Calendar.MONTH, -1);

		return new Date(c.getTimeInMillis());
	}

	/**
	 * 获取UTC时间<一句话功能简述> <功能详细描述>
	 * 
	 * @return Date [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Date getUTCTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis()
				- TimeZone.getDefault().getRawOffset());
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 获取当前时间的前一天
	 * 
	 * @param formatStr
	 *            返回的时间格式
	 * @return [参数说明]前一天时间
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String getBeforeCurrentDate(String formatStr) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date date = calendar.getTime();
		return DateTools.format(date, formatStr);
	}

	/**
	 * 把时间转换成字符串，格式为2006-01-10
	 * 
	 * @param date
	 *            时间
	 * @return 时间字符串
	 */
	public static String date2StringByDay(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}

	/**
	 * 按照指定格式把时间转换成字符串，格式的写法类似yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param date
	 *            时间
	 * @param pattern
	 *            格式
	 * @return 时间字符串
	 */
	public static String date2String(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return (new SimpleDateFormat(pattern)).format(date);
	}

	/**
	 * 
	 * @Title: getAmPm
	 * @Description: 判断是上午还是下午
	 * @Param @return
	 * @Return String
	 * @Throws
	 */
	public static String getAmPm() {
		GregorianCalendar ca = new GregorianCalendar();
		int ampm = ca.get(GregorianCalendar.AM_PM);
		if (ampm == 0) {
			return "上午";
		} else {
			return "下午";
		}
	}

	public static int getIntervalDays(Date date1, Date date2) {
		date1 = getZeroClockOfDate(date1);
		date2 = getZeroClockOfDate(date2);
		if (date1.after(date2)) {
			Date temp = date1;
			date1 = date2;
			date2 = temp;
		}

		return (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 
	 * @Title: getIntervalMonths
	 * @Description: TODO 计算过去日期与当前日期相差月数
	 * @Param @param date1 过去日期
	 * @Param @param date2 当前日期
	 * @Param @return
	 * @Return int
	 * @Throws
	 */
	public static int getIntervalMonths(Date date1, Date date2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(5) < objCalendarDate1.get(5)) {
				flag = 1;
			}
			if (objCalendarDate2.get(1) > objCalendarDate1.get(1)) {
				iMonth = (objCalendarDate2.get(1) - objCalendarDate1.get(1))
						* 12 + objCalendarDate2.get(2) - flag
						- objCalendarDate1.get(2);
			} else {
				iMonth = objCalendarDate2.get(2) - objCalendarDate1.get(2)
						- flag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/**
	 * 
	 * @Title: getIntervalYears
	 * @Description: TODO 计算过去日期与当前日期相差年数
	 * @Param @param date1 过去日期
	 * @Param @param date2 当前日期
	 * @Param @return
	 * @Return int
	 * @Throws
	 */
	public static int getIntervalYears(Date date1, Date date2) {
		int months = getIntervalMonths(date1, date2);
		int years = 0;
		if (months >= 12) {
			years = months / 12;
		}
		return years;
	}

	/**
	 * 
	 * @Title: getIntervalYearsMonths
	 * @Description: TODO 计算过去日期与当前日期相关年数和月数
	 * @Param @param date1 过去日期
	 * @Param @param date2 当前日期
	 * @Param @return
	 * @Return int[]
	 * @Throws
	 */
	public static int[] getIntervalYearsMonths(Date date1, Date date2) {
		int months = getIntervalMonths(date1, date2);
		int years = 0;
		if (months >= 12) {
			years = months / 12;
			months = months % 12;
		}
		return new int[] { months, years };
	}

	/**
	 * 
	 * @Title: isEndOfMonth
	 * @Description: TODO 判断这一天是否是月底
	 * @Param @param calendar
	 * @Param @return
	 * @Return boolean
	 * @Throws
	 */

	public static boolean isEndOfMonth(Calendar calendar) {
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
			return true;
		return false;
	}

	/**
	 * desc：获取上月第一天的时间
	 * 
	 * @return 返回上月第一天的时间 如：DateUtil.getLastMonthFistDay()
	 */
	public static Date getLastMonthFistDay() {
		Calendar now = new GregorianCalendar();
		now.add(Calendar.MONTH, -1);
		Calendar stdCal = new GregorianCalendar(now.get(Calendar.YEAR),
				now.get(Calendar.MONTH), 1);
		return stdCal.getTime();
	}

	/**
	 * 获取上月最后一天
	 * 
	 * @Title: getLastMonthDay
	 * @Description: TODO
	 * @Param @return
	 * @Return String
	 * @Throws
	 */
	public static Date getLastMonthDay() {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, -1);
		// 得到一个月最最后一天日期(31/30/29/28)
		int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay, 23,
				59, 59);
		// 按格式输出
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return cal.getTime();
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearLast() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	public static Date getYearFirstByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return getYearFirst(year);
	}

	public static Date getYearLastByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return getYearLast(year);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	/**
	 * 
	 * @Title: getZeroClockOfDate
	 * @Description: 将日期的时分秒归零
	 * @Param @param date
	 * @Param @return
	 * @Return Date
	 * @Throws
	 */
	public static Date getZeroClockOfDate(Date date) {
		return DateTools.formtDate(DateTools.format(date, DATE_FORMAT_10),
				DATE_FORMAT_10);
	}

	/**
	 * 
	 * @Title: getBeginOfWeek
	 * @Description: 本周一的日期
	 * @Param @param date
	 * @Param @return
	 * @Return Date
	 * @Throws
	 */
	public static Date getBeginOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getEndOfWeek
	 * @Description: 本周日的日期
	 * @Param @param date
	 * @Param @return
	 * @Return Date
	 * @Throws
	 */
	public static Date getEndOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.add(Calendar.WEEK_OF_YEAR, 1);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getBeforeCurrentDate
	 * @Description: TODO 获取距离当前日期多少天的日期
	 * @Param @param days 距离当前天数
	 * @Param @param formatStr 获取的日期格式
	 * @Param @return
	 * @Return String
	 * @Throws
	 */
	public static Date getBeforeCurrentDate(int days, String formatStr) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -days);
		return calendar.getTime();
	}

	/**
	 * 
	 * @Title: getBeforeDaysDate
	 * @Description: TODO
	 * @Param @param days
	 * @Param @param date
	 * @Param @param formatStr
	 * @Param @return
	 * @Return Date
	 * @Throws
	 */
	public static Date getBeforeDaysDate(int days, String date, String formatStr) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(formtDate(date, DATE_FORMAT_10));
		calendar.add(Calendar.DATE, -days);
		return calendar.getTime();
	}

	/**
	 * 
	 * @Title: getAfterDaysDate
	 * @Description: TODO 在指定日期的基本上加1天
	 * @Param @param days
	 * @Param @param date
	 * @Param @param formatStr
	 * @Param @return
	 * @Return Date
	 * @Throws
	 */
	public static Date getAfterDaysDate(int days, String date, String formatStr) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(formtDate(date, DATE_FORMAT_10));
		calendar.add(Calendar.DATE, +days);
		return calendar.getTime();
	}

	/**
	 * 
	 * @Title: convertMillisTo
	 * @Description: TODO
	 * @Param @param millis
	 * @Param @return
	 * @Return String
	 * @Throws
	 */
	public static String convertMillisTo(long millis) {
		long hourMillis = 60 * 60 * 1000;
		long minuteMillis = 60 * 1000;
		long secondMillis = 1000;
		long hourCount = 0;
		long minuteCount = 0;
		long secondCount = 0;
		hourCount = millis / hourMillis;
		minuteCount = (millis - hourCount * hourMillis) / minuteMillis;
		secondCount = (millis - hourCount * hourMillis - minuteCount
				* minuteMillis)
				/ secondMillis;

		return hourCount + "小时" + minuteCount + "分钟" + secondCount + "秒";
	}

	/**
	 * 
	 * @Title: getMonthFistDay
	 * @Description: TODO 根据距离传入日期前N个月第一天
	 * @Param @param mon 表示距离传入日期几个月
	 * @Param @param date 传入日期
	 * @Param @return
	 * @Return Date
	 * @Throws
	 */
	public static Date getMonthFistDay(int mon, Date date) {

		Calendar now = new GregorianCalendar();
		now.setTime(date);
		now.add(Calendar.MONTH, -mon);
		Calendar stdCal = new GregorianCalendar(now.get(Calendar.YEAR),
				now.get(Calendar.MONTH), 1);
		return stdCal.getTime();
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 比较时间大小
	 * 
	 * @Title: isAlive
	 * @Description: TODO
	 * @Param @param begin
	 * @Param @param end
	 * @Param @return
	 * @Return boolean
	 * @Throws
	 */
	public static boolean isAlive(String begin, String end) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date act_begin = sdf.parse(begin);
			Date act_end = sdf.parse(end);
			if (act_begin.before(act_end) || act_begin.equals(act_end)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Integer getField(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(field);
	}

	/**
	 * 比较字符串日期与当前日期相差秒数
	 * 
	 * @Title: getSecondsForDateTime
	 * @Description: TODO
	 * @Param @param date string
	 * @Param @return
	 * @Return hour number
	 * @Throws
	 */

	public static long getSecondsForDateTime(long start) {
		long end = new Date().getTime();
		return (end - start) / 1000;
	}

	/**
	 * 获得上周一日期
	 * 
	 * @Title: getPreviousWeekday
	 * @Description: TODO
	 * @Param @return
	 * @Return String
	 * @Throws
	 */
	public static Date getPreviousWeekday() {
		weeks = 0;
		weeks -= 1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_10);
		String preMonday = sdf.format(monday);
		monday = formtDate(preMonday, DATE_FORMAT_10);
		return monday;
	}

	/**
	 * 获得上周日日期
	 * 
	 * @Title: getPreviousWeekSunday
	 * @Description: TODO
	 * @Param @return
	 * @Return String
	 * @Throws
	 */
	public static Date getPreviousWeekSunday() {
		weeks = 0;
		weeks -= 1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_10);
		String preMonday = sdf.format(monday);
		monday = formtDate(preMonday, DATE_FORMAT_10);
		return monday;
	}

	/**
	 * 获得上月第一天
	 * 
	 * @Title: getPreviousMonthFirst
	 * @Description: TODO
	 * @Param @return
	 * @Return String
	 * @Throws
	 */
	public static Date getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_10);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		lastDate.add(2, -1);
		str = sdf.format(lastDate.getTime());
		Date date = formtDate(str, DATE_FORMAT_10);
		return date;
	}

	/**
	 * 获得上月最后一天
	 * 
	 * @Title: getPreviousMonthEnd
	 * @Description: TODO
	 * @Param @return
	 * @Return String
	 * @Throws
	 */
	public static Date getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_10);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(2, -1);
		lastDate.set(5, 1);
		lastDate.roll(5, -1);
		str = sdf.format(lastDate.getTime());
		Date date = formtDate(str, DATE_FORMAT_10);
		return date;
	}

	/**
	 * 本月第一天
	 * 
	 * @Title: getFirstDayOfMonth
	 * @Description: TODO
	 * @Param @return
	 * @Return String
	 * @Throws
	 */
	public static Date getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		str = sdf.format(lastDate.getTime());
		Date date = formtDate(str, DATE_FORMAT_10);
		return date;
	}

	/**
	 * 获得本月最后一天
	 * 
	 * @Title: getDefaultDay
	 * @Description: TODO
	 * @Param @return
	 * @Return Date
	 * @Throws
	 */
	public static Date getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		lastDate.add(2, 1);
		lastDate.add(5, -1);
		str = sdf.format(lastDate.getTime());
		Date date = formtDate(str, DATE_FORMAT_10);
		return date;
	}

	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(7) - 1;
		if (dayOfWeek == 1) {
			return 0;
		}
		return (1 - dayOfWeek);
	}

	/**
	 * 
	 * @Title: getMonthDates
	 * @Description: TODO 根据开始时间和结束时间获取所有月份日期(前后包含)
	 * @Param @param startDateStr
	 * @Param @param endDateStr
	 * @Param @return
	 * @Return List<Date>
	 * @Throws
	 */
	public static List<Date> getMonthDates(String startDateStr,
			String endDateStr) {
		List<Date> list = new ArrayList<Date>();
		Date startDate = DateTools.formtDate(startDateStr,
				DateTools.DATE_FORMAT_10);
		Date endDate = DateTools
				.formtDate(endDateStr, DateTools.DATE_FORMAT_10);
		int months = DateTools.getIntervalMonths(startDate, endDate);
		Date tarDate = startDate;
		for (int i = 0; i <= months; i++) {
			list.add(tarDate);
			tarDate = DateTools.getBeginOfNextMonth(tarDate);
		}
		return list;
	}

	/**
	 * 
	 * @Title: getDayDates
	 * @Description: TODO 根据开始时间和结束时间获取所有天日期(前后包含)
	 * @Param @param startDateStr
	 * @Param @param endDateStr
	 * @Param @return
	 * @Return List<Date>
	 * @Throws
	 */
	public static List<Date> getDayDates(String startDateStr, String endDateStr) {
		List<Date> list = new ArrayList<Date>();
		Date startDate = DateTools.formtDate(startDateStr,
				DateTools.DATE_FORMAT_10);
		Date endDate = DateTools
				.formtDate(endDateStr, DateTools.DATE_FORMAT_10);
		int days = DateTools.getIntervalDays(startDate, endDate);
		Date tarDate = startDate;
		for (int i = 0; i <= days; i++) {
			list.add(tarDate);
			tarDate = DateTools.getAfterDaysDate(1,
					DateTools.format(tarDate, DateTools.DATE_FORMAT_10),
					DateTools.DATE_FORMAT_10);
		}
		return list;
	}

	/**
	 * 
	* @Title: getAddMonth 
	* @Description: TODO 目标时间月底日期
	* @Param @param date 目标时间
	* @Param @param num 往后推几个月
	* eg:(2015-04-30,2) return 2015-05-31
	* @Param @return 
	* @Return Date
	* @Throws
	 */
	public static Date getAddMonth(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, num);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();

	}
	
	/**
	 * 
	* @Title: getAddMonthCurr 
	* @Description: TODO 根据目标时间和指定月份数获取相对于目标时间的指定月份时间
	* @Param @param date 目标时间
	* @Param @param add_months 指定月份
	* @Param @return 
	* eg:(2015-04-18,1);return 2015-05-18
	* @Return Date
	* @Throws
	 */
	public static Date getAddMonthCurr(Date date, int add_months) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.add(2, add_months);
		return time.getTime();
	}
	
	/**
	 * 
	* @Title: getAddMonthCurr 
	* @Description: TODO 根据目标时间和指定月份数获取相对于目标时间的指定月份时间(包含当天)
	* @Param @param date 目标时间
	* @Param @param add_months 指定月份
	* @Param @return 
	* eg:(2015-04-18,1);return 2015-05-18
	* @Return Date
	* @Throws
	 */
	public static Date getAddMonthContainNowDay(Date date, int add_months) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.add(2, add_months);
		time.set(Calendar.DATE, time.get(Calendar.DATE) - 1);
		return time.getTime();
	}
	
	/**
	 * 把日期更改为(yyyy-mm-dd 12:00:00)
	 * 例如:2015-10-15 12:00:00
	 * @param date 时间
	 * @param appendTime 需要追加的分钟
	 * @return
	 */
	public static Date getEndClockOfDate(Date date,String appendTime) {
		String dateStr = DateTools.format(date, DATE_FORMAT_10)+" "+appendTime;
		return DateTools.formtDate(dateStr,DATE_FORMAT_24HOUR_19);
	}
	
	/**
	 * 把日期更改为(yyyy-mm-dd 23:59:59)
	 * 例如:2015-10-15 23:59:59
	 * @param date 时间
	 * @return
	 */
	public static Date getEndClockOfDate(Date date) {
		String dateStr = DateTools.format(date, DATE_FORMAT_10)+" 23:59:59";
		return DateTools.formtDate(dateStr,DATE_FORMAT_24HOUR_19);
	}
	
}
