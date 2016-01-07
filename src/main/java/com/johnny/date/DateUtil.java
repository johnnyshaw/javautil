package com.johnny.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ʱ�䣬����ת����
 * 
 * @author WangT Create By 2008-08-18
 * 
 */
public class DateUtil {

	private static final Log log = LogFactory.getLog(DateUtil.class);

	private static final SimpleDateFormat DAY = getFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DAY_NUMBER = getFormat("yyyyMMdd");
	
	private static final SimpleDateFormat DAY_ONLY = getFormat("MM:dd");

	private static final SimpleDateFormat SECOND = getFormat("yyyy-MM-dd HH:mm:ss");

	private static final SimpleDateFormat SECOND_ONLY = getFormat("HH:mm:ss");

	private static final SimpleDateFormat MINUTE = getFormat("yyyy-MM-dd HH:mm");

	private static final SimpleDateFormat MINUTE_ONLY = getFormat("HH:mm");
	
	private static final SimpleDateFormat MINUTE_ANOTHER = getFormat("yyyyMMdd-HHmm");
	
	public static String getMinuteDbStr(Date date) {
		synchronized (MINUTE_ANOTHER) {
			return getStr(date, MINUTE_ANOTHER);
		}
	}
	
	public static Date getMinuteDbDate(String str) {
		synchronized (MINUTE_ANOTHER) {
			return getDate(str, MINUTE_ANOTHER);
		}
	}
	
	public static void main(String[] args) {
		System.err.println(getMinuteDbStr(new Date()));
	}
	
	public static String getSecondOnlyStr(Date date) {
		synchronized (SECOND_ONLY) {
			return getStr(date, SECOND_ONLY);
		}
	}
	public static String getOnlyDayStr(long date) {
		synchronized (DAY_ONLY) {
			return getStr(new Date(date), DAY_ONLY);
		}
	}
	public static String getSecondDateStr(long date) {
		return getSecondStr(new Date(date));
	}

	public static String getSecondStr(long date) {
		return getSecondOnlyStr(new Date(date));
	}

	public static String getMinuteStr(Date date) {
		synchronized (MINUTE) {
			return getStr(date, MINUTE);
		}
	}

	public static String getMinuteStr(long date) {
		return getMinuteStr(new Date(date));
	}

	public static String getMinuteOnlyStr(Date date) {
		synchronized (MINUTE_ONLY) {
			return getStr(date, MINUTE_ONLY);
		}
	}

	public static String getSecondStr(Date date) {
		synchronized (SECOND) {
			return getStr(date, SECOND);
		}
	}

	public static String getDayStr(Date date) {
		synchronized (DAY) {
			return getStr(date, DAY);
		}
	}
	
	public static int getDayNumber(Date date) {
		if(date==null){
			return 0;
		}
		synchronized (DAY_NUMBER) {
			return Integer.valueOf(getStr(date, DAY_NUMBER));
		}
	}
	
	public static Date getDayDate(Date date) {
		return getDayDate(getDayStr(date));
	}
	public static String getDayStr(long date) {
		return getDayStr(new Date(date));
	}

	public static Date getSecondDate(String dateStr) {
		synchronized (SECOND) {
			return getDate(dateStr, SECOND);
		}
	}

	public static Date getDayDate(String dateStr) {
		synchronized (DAY) {
			return getDate(dateStr, DAY);
		}
	}
	public static Date getMinuteOnlyDate(String dateStr) {
		synchronized (MINUTE_ONLY) {
			return getDate(dateStr, MINUTE_ONLY);
		}
	}
	public static Date getMinuteDate(String dateStr) {
		synchronized (MINUTE) {
			return getDate(dateStr, MINUTE);
		}
	}

	public static Date getMinuteDate(long time) {
		return getMinuteDate(getMinuteStr(time));
	}
	
	public static Date daysAddOrSub(Date date, int offset) {
		return addOrSub(date, Calendar.DAY_OF_MONTH, offset);
	}

	public static Date hoursAddOrSub(Date date, int offset) {
		return addOrSub(date, Calendar.HOUR_OF_DAY, offset);
	}

	public static Date minutesAddOrSub(Date date, int offset) {
		return addOrSub(date, Calendar.MINUTE, offset);
	}

	public static Date secondsAddOrSub(Date date, int offset) {
		return addOrSub(date, Calendar.SECOND, offset);
	}

	private static Date addOrSub(Date date, int type, int offset) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.get(type);
		cal.set(type, cal.get(type) + offset);
		return cal.getTime();
	}

	private static String getStr(Date date, SimpleDateFormat format) {
		if (date == null) {
			return "";
		}
		return format.format(date);
	}

	private static SimpleDateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}

	private static Date getDate(String dateStr, SimpleDateFormat format) {
		if ("".equals(dateStr) || dateStr == null)
			return null;
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			log.error("format yyyy-MM-dd HH:mm:ss error:", e);
		}
		return null;
	}

	// /////////////////////////////////time
	// tools///////////////////////////////
	/**
	 * �Ƿ�ΪTimeString ��ʽ��String 00:00 - 23:59
	 * 
	 * @param toCheck
	 * @return
	 */
	public static boolean isTimeString(String toCheck) {
		if(toCheck == null || "".equals(toCheck)){
			return false;
		}
		if (toCheck.trim().matches("([0-1][0-9]|2[0-3]):[0-5][0-9]|24:00"))
			return true;
		else
			return false;
	}

	/**
	 * �Ƚ�}��TimeString �Ĵ�С
	 * 
	 * @param ts1
	 * @param ts2
	 * @return
	 */
	public static int compareHHmmInString(String ts1, String ts2) {
		return ts1.compareTo(ts2);
	}

	/**
	 * ����Ƿ��ڿ�ʼ�����֮�䣬ǰ�պ���� -1�� start ��С��end 0: ����start ��end֮�� 1: ��start��end֮��
	 * 
	 * @param ts
	 * @param start
	 * @param end
	 * @return
	 */
	public static int betweenHHmmInString(String ts, String start, String end) {
		if (compareHHmmInString(start, end) >= 0)
			return -1;
		if (compareHHmmInString(ts, start) < 0)
			return 0;
		if (compareHHmmInString(end, ts) <= 0)
			return 0;
		return 1;
	}

	/**
	 * ���}��ʱ���Ƿ����, 00:00 == 24:00
	 * 
	 * @param ts1
	 * @param ts2
	 * @return
	 */
	public static boolean equalsInTimeString(String ts1, String ts2) {
		if (ts1.equals(ts2))
			return true;
		if (ts1.equals("00:00") || ts1.equals("24:00")) {
			if (ts2.equals("00:00") || ts2.equals("24:00")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ����Ƿ�Ϊͬһ��Сʱ�ͷ���. 1: cal>c; 0:cal=c; -1:cal<c
	 */
	public static int compareHHmm(Calendar cal, Calendar c) {
		if (cal.get(Calendar.HOUR_OF_DAY) > c.get(Calendar.HOUR_OF_DAY)) {
			return 1;
		} else if (cal.get(Calendar.HOUR_OF_DAY) == c.get(Calendar.HOUR_OF_DAY)) {
			if (cal.get(Calendar.MINUTE) > c.get(Calendar.MINUTE))
				return 1;
			else if (cal.get(Calendar.MINUTE) == c.get(Calendar.MINUTE))
				return 0;
			else
				return -1;
		} else
			return -1;
	}

	/**
	 * ����Ƿ��ڿ�ʼ�����֮�䣬����� -1�� start ��С��end 0: ����start ��end֮�� 1: ��start��end֮��
	 * 
	 * @param cal
	 * @param start
	 * @param end
	 * @return
	 */
	public static int betweenHHmm(Calendar cal, Calendar start, Calendar end) {
		if (compareHHmm(start, end) != -1)
			return -1;
		if (compareHHmm(cal, start) == -1)
			return 0;
		if (compareHHmm(end, cal) == -1)
			return 0;
		return 1;
	}

	/**
	 * ����Ƿ�Ϊͬ��һ��. 1: cal>c; 0:cal=c; -1:cal<c
	 */
	public static boolean compareDay(Calendar cal, Calendar c) {
		if (cal.get(Calendar.MONTH) == c.get(Calendar.MONTH)
				&& cal.get(Calendar.DAY_OF_MONTH) == c
						.get(Calendar.DAY_OF_MONTH))
			return true;
		else
			return false;
	}

	/**
	 * ��00:00��ʽ���ַ�תΪCalender
	 * 
	 * @param timeString
	 * @return Calender
	 * @throws Exception
	 */
	public static Calendar string2calendar(String timeString) throws Exception {
		if (!DateUtil.isTimeString(timeString))
			throw new Exception("Wrong argument : timeString format error "
					+ timeString);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, getHour(timeString));
		cal.set(Calendar.MINUTE, getMinute(timeString));
		return cal;
	}

	/**
	 * ��calendarתΪHH:MM��ʽ���ַ�
	 * 
	 * @param cal
	 * @return
	 */
	public static String calendar2TimeString(Calendar cal) {
		return (cal.get(Calendar.HOUR_OF_DAY) > 9 ? cal
				.get(Calendar.HOUR_OF_DAY) : "0"
				+ cal.get(Calendar.HOUR_OF_DAY))
				+ ":"
				+ (cal.get(Calendar.MINUTE) > 9 ? cal.get(Calendar.MINUTE)
						: "0" + cal.get(Calendar.MINUTE));
	}

	/**
	 * TimeString �еõ�Сʱ��Ϣ
	 * 
	 * @param timeString
	 * @return
	 */
	public static int getHour(String timeString) {
		return Integer.parseInt(timeString.substring(0, 2));
	}

	/**
	 * TimeString �еõ�������Ϣ
	 * 
	 * @param timeString
	 * @return
	 */
	public static int getMinute(String timeString) {
		return Integer.parseInt(timeString.substring(3, 5));
	}

	public static String getDateStringFromMinute(String minute) {
		return minute.substring(5, 10);
	}

	public static String getMiniteOnlyFromMinute(String minute) {
		return minute.substring(11, 16);
	}

	public static boolean isMiniteDate(String str) {
		if (str == null) {
			return false;
		}
		try {
			MINUTE_ONLY.parse(str);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	public static long getMiniteDate(Date date,String str) {
		if(str==null)
			return 0;
		return getMinuteDate(getDayStr(date)+" "+str).getTime();
	}
	/**
	 * ��Date���ַ���ֻ�õ�������Ϣ
	 * 
	 * @param dateString
	 * @return
	 */
	public static String getDateOnlyFromDate(String dateString) {
		return dateString.substring(5, 10);
	}

	// /////////////////////////////date tools////////////////////////////
	/**
	 * ��calendarתΪMM:DD��ʽ���ַ�
	 * 
	 * @param cal
	 * @return
	 */
	public static String calendar2DateString(Calendar cal) {
		return (cal.get(Calendar.MONTH) + 1 > 9 ? cal.get(Calendar.MONTH) + 1
				: "0" + (cal.get(Calendar.MONTH) + 1))
				+ ":"
				+ (cal.get(Calendar.DAY_OF_MONTH) > 9 ? cal
						.get(Calendar.DAY_OF_MONTH) : "0"
						+ cal.get(Calendar.DAY_OF_MONTH));
	}

	public static boolean isDateString(String toCheck) {
		if (toCheck == null)
			return false;
		if (toCheck.trim().matches("(0[0-9]|1[0-2]):([0-2][0-9]|3[0-1])"))
			return true;
		else
			return false;
	}
	
	
	public static long tradeTimeConverter(long weekId, long num) {
		long dd, hh, mm;
		
		long h = num / 100;
		mm = Math.abs(num) % 100;
		
		if(h >= 0) {
			dd = h / 24;
			hh = h % 24;
		} else {
			 long x = Math.abs(h) % 24;
			if(x == 0) {
				dd = Math.abs(h) / 24;
			} else {
				dd = Math.abs(h) / 24 + 1;
			}
			
			hh = dd * 24 + h;
			dd = -1 * dd;
		} 
		
		if(dd >= 0) {
			return ((weekId + dd) % 7) * 60 * 24 + hh * 60 + mm;
		} else {
			return ((Math.abs(weekId + dd) / 7 + 1) * 7 + weekId + dd) * 60 * 24 + hh * 60 + mm;
		}
	}
	
	/**
	 * 根据年龄计算生日
	 * @param age
	 * @return
	 */
	public static Date queryAge(int age){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -(age-1));
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
}