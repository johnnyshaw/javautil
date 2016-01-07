package com.johnny.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.johnny.numtool.Numbers;
import com.johnny.str.StringTools;


/**
 * 
* @ClassName: GeneralUtils 
* @Description: 通用帮助类 
* @Date 2014年9月24日 上午10:11:22 
* @Modify  
 */
public final class GeneralUtils {
	// 主键生成后两位拼接值起点
	private static Integer seed = Numbers.TEN;

	/**
	 * id生成器（根据时间） <功能详细描述>
	 * 
	 * @return 字符串
	 * @see [类、类#方法、类#成员]
	 */
	public static String idGenernator() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(now);
	}

	/**
	 * id生成器（根据时间） <功能详细描述>
	 * 
	 * @return 字符串
	 * @see [类、类#方法、类#成员]
	 */
	public static String dateGenernator() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(now);
	}

	/**
	 * 手机号码校验 <中国电信发布中国3G号码段:中国联通185,186;中国移动188,187;中国电信189,180共6个号段。
	 * 3G业务专属的180
	 * -189号段已基本分配给各运营商使用,其中180、189分配给中国电信,187、188归中国移动使用,185、186属于新联通。
	 * 中国移动拥有号码段
	 * ：139、138、137、136、135、134、159、158、157（3G）、152、151、150、188（3G）、187（
	 * 3G）;14个号段 中国联通拥有号码段：130、131、132、155、156（3G）、186（3G）、185（3G）;6个号段
	 * 中国电信拥有号码段：133、153、189（3G）、180（3G）;4个号码段 移动:
	 * 2G号段(GSM网络)有139,138,137,136,135,134(0-8),159,158,152,151,150
	 * 3G号段(TD-SCDMA网络)有157,188,187 147是移动TD上网卡专用号段. 联通:
	 * 2G号段(GSM网络)有130,131,132,155,156 3G号段(WCDMA网络)有186,185 电信:
	 * 2G号段(CDMA网络)有133,153 3G号段(CDMA网络)有189,180>
	 * 
	 * @param mobiles
	 *            输入的手机号码
	 * @return true则手机号码正确，false则手机号码不正确
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isMobileNO(String mobiles) {
		if (mobiles == null) {
			return false;
		}
		return Pattern.matches(
				"^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(14[0,7-8]))\\d{8}$",
				mobiles);
	}

	/**
	 * 校验固话和手机号码
	 * 
	 * @param number
	 *            联系方式
	 * @return true表示合法false表示非法
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isFixedPhoneNo(String number) {
		if (number == null) {
			return false;
		}
		String pattern = "(^[0-9]{3,4}\\-[0-9]{3,8}$)|(^[0-9]{3,4}[0-9]{3,8}$)|(^0{0,1}1[3-9][0-9]{9}$)";
		return Pattern.matches(pattern, number);
	}


	/**
	 * 校验一个字符串是不是整数
	 * 
	 * @param str
	 *            需要校验的字符串
	 * @return true输入的字符串全是整数false表示不全是整数或全不是
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		return Pattern.matches("(0|[1-9]\\d*)", str);
	}

	/**
	 * 判断一个字符串是不是一个数字
	 * 
	 * @param number
	 *            需要校验的字符串
	 * @return true输入的字符串全是数字false表示不全是数字或全不是
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isNumber(String number) {
		if (number == null) {
			return false;
		}
		return Pattern
				.matches("(0|[1-9]\\d*)$|^(0|[1-9]\\d*)\\.(\\d+)", number);
	}

	/**
	 * 邮箱合法性校验
	 * 
	 * @param email
	 *            所要校验的邮箱
	 * @return true表示邮箱合法false表示邮箱不合法
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isEmail(String email) {
		if (email == null) {
			return false;
		}
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		return Pattern.matches(str, email);
	}

	/**
	 * 中国身份证号合法性校验 <中国的身份证为15位或18位>
	 * 
	 * @param idCard
	 *            所要校验的身份证号
	 * @return true表示合法性身份证false表示非法性身份证
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isIdCard(String idCard) {
		if (idCard == null) {
			return false;
		}
		return Pattern.matches("^(\\d{18,18}|\\d{15,15}|\\d{17,17}(x|X))$", idCard);
	}

	/**
	 * IP合法性校验
	 * 
	 * @param ipAddr
	 *            所要校验的IP地址
	 * @return true表示合法性IP,false表示非法性IP
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isIP(String ipAddr) {
		if (ipAddr == null) {
			return false;
		}
		return Pattern
				.matches(
						"((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)",
						ipAddr);
	}

	/**
	 * 中文合法性校验
	 * 
	 * @param str
	 *            所要校验的字符
	 * @return true表示是中文,false表示不是中文
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isChinese(String str) {
		if (str == null) {
			return false;
		}
		// str=new String(str.getBytes());//用GBK编码
		// return Pattern.matches("[\u4e00-\u9fa5]+",str);
		// Unicode编码中的汉字范围
		return Pattern.matches("^[\u2E80-\u9FFF]+$", str);
	}

	/**
	 * 中国邮政编码合法性校验
	 * 
	 * @param postalCode
	 *            所要校验的邮政编码
	 * @return true表示合法的邮政编码,false表示非法的邮政编码
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isPostalCode(String postalCode) {
		if (postalCode == null) {
			return false;
		}
		return Pattern.matches("[1-9]\\d{5}(?!\\d)", postalCode);
	}

	/**
	 * 大陆固定电话号码合法性校验
	 * 
	 * @param phone
	 *            所要校验的固话号码
	 * @return true表示是国内电话号码false表示是不国内电话号码
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isMainlandPhone(String phone) {
		if (phone == null) {
			return false;
		}
		return Pattern.matches("\\d{3}-\\d{8}|\\d{4}-\\d{7}", phone);
	}

	/**
	 * 网址URL合法性校验
	 * 
	 * @param address
	 *            网址
	 * @return true表示合法性网址false表示不合理网址
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isInterAddr(String address) {
		if (address == null) {
			return false;
		}
		return Pattern.matches("[a-zA-z]+://[^\\s]*", address);
	}

	/**
	 * 
	 * 唯一键生成
	 * 
	 * @return 所生成的主键
	 * @see [类、类#方法、类#成员]
	 */
	public static String getIdGenerator() {
		StringBuffer id = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat("MMddhhmmssSSS");
		String timeStr = sf.format(new Date());
		id.append(timeStr);
		synchronized (seed.toString()) {
			id.append(seed++);
			if (seed == (Numbers.HUNDRED - Numbers.ONE)) {
				seed = Numbers.TEN;
			}
		}
		return id.toString();
	}

	/**
	 * 
	 * 唯一键生成
	 * 
	 * @return 所生成的主键
	 * @see [类、类#方法、类#成员]
	 */
	public static String idGenerator() {
		StringBuffer id = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String timeStr = sf.format(new Date());
		id.append(timeStr);
		synchronized (seed.toString()) {
			id.append(seed++);
			if (seed == (Numbers.HUNDRED - Numbers.ONE)) {
				seed = Numbers.TEN;
			}
		}
		return id.toString();
	}


	/**
	 * yyyy-MM-dd格式日期校验
	 * 
	 * @param date
	 *            输入日期值
	 * @return true表示合法性日期false表示非法性日期
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isyyyyMMdd(String date) {
		if (date == null) {
			return false;
		}
		return Pattern.matches(
				"^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$", date);
	}
	
	/**
	 * 行业系数
	* @Title: getIndustryCoefficient 
	* @Description: TODO
	* @Param @return
	* @Return Map<String,String>
	* @Throws
	 */
	public static Double getIndustryCoefficient(String key){
		Map<String, Double> map=new HashMap<String, Double>();
		map.put("1", 0.8);//服装内衣
		map.put("2", 0.8);//鞋包配饰
		map.put("3", 1d);//运动户外
		map.put("4", 1d);//珠宝手表
		map.put("5", 1d);//手机数码
		map.put("6", 1d);//家电办公
		map.put("7", 0.8);//护肤彩妆
		map.put("8", 1d);//母婴用品
		map.put("9", 1d);//家纺居家
		map.put("10", 1d);//家具建材
		map.put("11", 1d);//美食特产
		map.put("12", 1d);//日用百货
		map.put("13", 1d);//汽车摩托
		map.put("14", 1d);//文化娱乐
		map.put("15", 0.6);//本地生活
		map.put("16", 0.6);//虚拟服务
		return map.get(key);
	}
	/**
	 * 客单价中值
	* @Title: getUnitCost 
	* @Description: TODO
	* @Param @return
	* @Return Map<String,Integer>
	* @Throws
	 */
	public static Double getUnitCost(String key){
		Map<String, Double> hmap=new HashMap<String, Double>();
		hmap.put("1", 150d);//服装内衣
		hmap.put("2", 150d);//鞋包配饰
		hmap.put("3", 200d);//运动户外
		hmap.put("4", 250d);//珠宝手表
		hmap.put("5", 1000d);//手机数码
		hmap.put("6", 1000d);//家电办公
		hmap.put("7", 150d);//护肤彩妆
		hmap.put("8", 100d);//母婴用品
		hmap.put("9", 500d);//家纺居家
		hmap.put("10", 500d);//家具建材
		hmap.put("11", 100d);//美食特产
		hmap.put("12", 100d);//日用百货
		hmap.put("13", 250d);//汽车摩托
		hmap.put("14", 50d);//文化娱乐
		hmap.put("15", 200d);//本地生活
		hmap.put("16", 200d);//虚拟服务
		return hmap.get(key);
	}
	
	public static String getResultType(String resultType){
		String mess="";
		if ("PC_DAY".equals(resultType)) {
			mess="DAY";
		}else if ("PC_MONTH".equals(resultType)) {
			mess="MONTH";
		}else if ("WIRELESS_MONTH".equals(resultType)) {
			mess="PHONE_MONTH";
		}else if ("WIRELESS_DAY".equals(resultType)) {
			mess="PHONE_DAY";
		}
		return mess;
	}
    
	//将数字类型四舍五入取整
	public static BigDecimal getRoundInt(double d) {
		// 四舍五入取整
		return new BigDecimal(d).setScale(0, BigDecimal.ROUND_HALF_UP);	
	}
	
	//将对象转为字符串
	public static String isNullThenSth(Object o){
		if(o == null || "".equals(o)){
			return "";
		}else{
			return o+"";
		}
	}
	
	/**
	 * 格式化小数
	 * @param o 要转换的数字
	 * @param numberCount 小数位数
	 * @return
	 */
	public static String formatNumber(Object o, int numberCount){
		if (StringTools.isEmpty(StringTools.nvl(o))) {
			return "-";
		}
		double objD = Double.parseDouble(o.toString());
		if (objD < 0d) {
			return "-";
		}
		String mat = "0.0";
		for(int i = 1; i<numberCount; i++){
			mat+="0";
		}
		DecimalFormat df = new DecimalFormat(mat);
		return "￥" + df.format(objD);
	}
	
}
