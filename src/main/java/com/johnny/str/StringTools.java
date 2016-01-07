package com.johnny.str;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.UUID;

import com.johnny.numtool.Numbers;

/**
 * 
* @ClassName: StringTools
* @Description: TODO 字符串处理工具类
* @Date 2014年9月24日 上午10:12:02 
* @Modify  
 */
public class StringTools {
	private StringTools() {
	}

	/**
	 * getLength 返回字符串的长度
	 * 
	 * @param src
	 *            输入字符串
	 * @return int 字符串长度
	 * 
	 */

	public static int getLength(String src) {
		return ((null == src) || ("".equals(src))) ? 0 : src.getBytes().length;
	}

	/**
	 * getLength 返回非空字符串
	 * 
	 * @param o
	 *            输入对象
	 * @return string
	 * 
	 */
	public static String nvl(Object o) {
		return (null == o) ? "" : o.toString().trim();
	}

	/**
	 * 格式化数据
	 * 
	 * @param value
	 *            value
	 * @param params
	 *            params
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String getMsg(String value, Object[] params) {
		try {
			// 格式化数据
			return MessageFormat.format(value, params);
		} catch (Exception ex) {
			return value;
		}
	}

	/**
	 * <一句话功能简述> <功能详细描述>
	 * 
	 * @param s
	 *            s
	 * @param def
	 *            def
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String fenToYuan(String s, float def) {
		float fen = StringTools.toFloat(s, 0.0f);
		String sFen = (int) Math.floor(fen) + "";
		int len = sFen.length();

		String yuan;
		if (len == 1) {
			yuan = "0.0" + sFen;
		} else if (len == Numbers.TWO) {
			yuan = "0." + sFen;
		} else {
			yuan = sFen.substring(0, len - Numbers.TWO) + '.'
					+ sFen.substring(len - Numbers.TWO);
		}

		return (fen < 0) ? ('-' + yuan) : yuan;
	}

	/**
	 * 元转分
	 * 
	 * @param s
	 *            s
	 * @param def
	 *            def
	 * 
	 * @return float [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static int yuanToFen(String s, int def) {
		int fen;
		try {
			BigDecimal sumStr = new BigDecimal(s);
			fen = sumStr.movePointRight(Numbers.TWO).intValue();
		} catch (Exception e) {
			fen = (int) (def * Numbers.HUNDRED);
		}

		return fen;
	}

	/**
	 * String to float
	 * 
	 * @param s
	 *            s
	 * @param def
	 *            def
	 * 
	 * @return float [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static float toFloat(String s, float def) {
		float f = def;

		try {
			f = Float.parseFloat(s);
		} catch (Exception e) {
			f = def;
		}
		return f;
	}

	/**
	 * String to int <功能详细描述>
	 * 
	 * @param s
	 *            s
	 * @param def
	 *            def
	 * 
	 * @return int [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static int toInt(String s, int def) {
		int value = def;

		try {
			value = Integer.parseInt(s);
		} catch (Exception e) {
			value = def;
		}

		return value;
	}
	/**
	 * 
	* @Title: toDouble 
	* @Description: TODO 转为double类型
	* @Param @param s
	* @Param @param def
	* @Param @return
	* @Return double
	* @Throws
	 */
	public static double toDouble(String s, Double def) {
		Double value = def;
		try {
			value = Double.valueOf(s);
		} catch (Exception e) {
			value = def;
		}
		return value;
	}
	/**
	 * String to Long <功能详细描述>
	 * 
	 * @param s
	 *            s
	 * @param def
	 *            def
	 * 
	 * @return int [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Long toLong(String s, long def) {
		long value = def;

		try {
			value = Long.parseLong(s);
		} catch (Exception e) {
			value = def;
		}

		return value;
	}

	/**
	 * 叶面显示时对特殊字符进行转换
	 * 
	 * @param text
	 *            text
	 * @return string
	 */
	public static String toView(String text) {
		text = replace(text, "&amp;", "&");
		text = replace(text, "&lt;", "<");
		text = replace(text, "&gt;", ">");
		text = replace(text, "&quot;", "\"");
		text = replace(text, "&apos;", "'");
		return text;
	}

	/**
	 * 特殊字符转义，文本转为html
	 * 
	 * @param text
	 *            text
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String text2Html(String text) {
		text = replace(text, "&", "&amp;");
		text = replace(text, "<", "&lt;");
		text = replace(text, ">", "&gt;");
		text = replace(text, "\"", "&quot;");
		text = replace(text, "'", "&apos;");
		return text;
	}

	/**
	 * 为wml（wap1.0）的特殊字符进行转义
	 * 
	 * @param text
	 *            text
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String escForWml(String text) {
		text = replace(text, "$", "$$");
		return text;
	}

	/**
	 * 查找替换字符串中的子串。
	 * 
	 * @param text
	 *            待处理字符串。
	 * @param find
	 *            待替换的子串。
	 * @param replace
	 *            替换成的子串。
	 * @return 返回text替换后的结果。
	 */
	public static String replace(String text, String find, String replace) {
		if (text == null || find == null || replace == null) {
			return text;
		}
		int findLen = find.length();
		int textLen = text.length();
		if (textLen == 0 || findLen == 0) {
			return text;
		}
		StringBuffer sb = new StringBuffer(Numbers.TWOHANDREDFIFTYSIX);
		int begin = 0; // 下次检索开始的位置
		int i = text.indexOf(find); // 找到的子串位置
		while (i != -1) {
			sb.append(text.substring(begin, i));
			sb.append(replace);
			begin = i + findLen;
			i = text.indexOf(find, begin);
		}
		if (begin < textLen) {
			sb.append(text.substring(begin));
		}
		return sb.toString();
	}

	/**
	 * 去掉两位的打折数字的末位0
	 * 
	 * @param discount
	 *            discount
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String convertDiscount(String discount) {
		if (discount == null || "".equals(discount.trim())) {
			return "";
		} else if (discount.endsWith("0")) {
			return discount.substring(0, discount.length() - 1);
		} else {
			return discount;
		}
	}

	/**
	 * <判断字符串是否为null对象或是空白字符> <功能详细描述>
	 * 
	 * @param str
	 *            str
	 * 
	 * @return boolean [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (0 == str.trim().length());
	}

	/**
	 * <判断字符串是否bu为null对象或是空白字符> <功能详细描述>
	 * 
	 * @param str
	 *            str
	 * 
	 * @return boolean [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 将字符串通过 分隔符转换为集合
	 * 
	 * @param collection
	 *            需要存放的集合
	 * @param str
	 *            字符串
	 * @param splitStr
	 *            分隔符
	 * 
	 * @return Collection [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Collection<String> toCollection(
			Collection<String> collection, String str, String splitStr) {
		if (null != str && !"".equals(str) && null != splitStr
				&& !"".equals(splitStr)) {
			String[] strArray = str.trim().split(splitStr);
			for (String tmpStr : strArray) {
				collection.add(tmpStr.trim());
			}
		}

		return collection;
	}

	/**
	 * <判断字符串是否是数字> <功能详细描述>
	 * 
	 * @param str
	 *            str
	 * 
	 * @return boolean [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */

	public static boolean isDigtial(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches("\\d+");
	}

	/**
	 * <替换反斜线"\"为斜线"/"> <功能详细描述>
	 * 
	 * @param str
	 *            str
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */

	public static String replaceBacklash(String str) {
		if (isEmpty(str)) {
			return "";
		} else {
			return str.replace("\\", "/");
		}
	}

	/**
	 * <取子串> <功能详细描述>
	 * 
	 * @param str
	 *            str
	 * @param begin
	 *            begin
	 * @param end
	 *            end
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String subString(String str, int begin, int end) {
		if (str == null) {
			return str;
		}

		int b = Math.max(begin, 0);
		int e = Math.min(end, str.length());
		return str.substring(b, e);
	}

	/**
	 * <判断字符串是否与其后面某个参数相等> <功能详细描述>
	 * 
	 * @param base
	 *            base
	 * @param matched
	 *            matched
	 * 
	 * @return boolean [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */

	// public static boolean matchs(String base, String... matched)
	// {
	// for (int i = 0; i < matched.length; i++)
	// {
	// if (matched[i].equals(base))
	// {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * <判断字符串是否与数组中的某个参数相等> <功能详细描述>
	 * 
	 * @param base
	 *            base
	 * @param matches
	 *            matches
	 * 
	 * @return boolean [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */

	public static boolean matches(String base, String[] matches) {
		if (null == matches || 0 == matches.length) {
			return false;
		} else {
			for (int i = 0; i < matches.length; i++) {
				if (matches[i].equals(base)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 删除末尾的空格，并将全角空格替换为&nbsp <功能详细描述>
	 * 
	 * @param str
	 *            str
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String replaceFullSpaceToNbsp(String str) {
		// 如果str全部由空格(包括全角空格)组成
		if (isEmpty(str) || isEmpty(str.replace("　", "").trim())) {
			return "";
		}

		str = str.trim();
		// 删除末尾的全角空格,\\u3000是全角空格的ASC码
		str = str.replaceAll("[\u3000]+$", "");

		return str.replace("\u3000", "&nbsp;&nbsp;");
	}

	/**
	 * <一句话功能简述> 获取用户自定义锚文描述 <功能详细描述>
	 * 
	 * @param rule
	 *            rule
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String getUserDefinedName(String rule) {
		if (StringTools.isEmpty(rule) || rule.indexOf("/") < 0) {
			return "";
		}
		String tempString = rule.split("/")[1];
		return StringTools.text2Html(tempString.substring(0,
				tempString.length() - 1));
	}

	/**
	 * 若null对象或是空白字符，返回默认值，否则其trim结果
	 * 
	 * @param str
	 *            str
	 * @param def
	 *            def
	 * @return [参数说明]
	 */
	public static String getTrim(String str, String def) {
		if (str == null || "null".equals(str)) {
			return def;
		}

		String t = str.trim();
		return (t.length() == 0) ? def : t;
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 *            str
	 * @param len
	 *            len
	 * @param des
	 *            des
	 * @return [参数说明]
	 */
	public static String getStrBylength(String str, int len, String des) {
		if (null == str) {
			return "";
		}

		int strByteLen = str.getBytes().length;
		int strLen = str.length();
		if (len >= strByteLen) {
			return str;
		}
		if (strByteLen > strLen) {
			StringBuffer sb = new StringBuffer();
			char[] charSet = str.toCharArray();
			for (int i = 0, j = 0; i < strLen && j < len; i++) {
				if (String.valueOf(charSet[i]).matches("[^x00-xff]")) {
					sb.append(charSet[i]);
					j += Numbers.TWO;
				} else {
					sb.append(charSet[i]);
					j += 1;
				}
			}
			return sb.toString() + des;
		} else {
			return str.substring(0, len) + des;
		}
	}

	/**
	 * 转换折扣:例：70->7 <功能详细描述>
	 * 
	 * @param orgDiscount
	 *            orgDiscount
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String changeDiscount(int orgDiscount) {
		String discount = "";
		if (orgDiscount / Numbers.TEN == 0) {
			discount = "0." + String.valueOf(orgDiscount % Numbers.TEN);
		} else if (orgDiscount % Numbers.TEN == 0) {
			discount = String.valueOf(orgDiscount / Numbers.TEN);
		} else {
			discount = String.valueOf(orgDiscount / Numbers.TEN) + "."
					+ String.valueOf(orgDiscount % Numbers.TEN);
		}

		return discount;
	}

	/**
	 * <一句话功能简述> <功能详细描述>
	 * 
	 * @param nodeString
	 *            nodeString
	 * @param xml
	 *            xml
	 * 
	 * @return String [返回类型说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String subConetnXml(String nodeString, String xml) {
		int start = xml.indexOf(">", xml.indexOf("<" + nodeString));
		int end = xml.indexOf("</" + nodeString + ">");

		return xml.substring(start + 1, end);
	}
	/**
	 * 
	* @Title: 金额字符串添加逗号，900,000,000不超过10位
	* @Description: TODO
	* @Param @param instr
	* @Param @return
	* @Return String
	* @Throws
	 */
	public static String subStrByTod(String instr){
		String moneyStri = instr;
		if(moneyStri.length()<4){
			return moneyStri;
		}else if(moneyStri.length()<7){
			moneyStri = moneyStri.substring(0,moneyStri.length()-3)+","+moneyStri.substring(moneyStri.length()-3,moneyStri.length());
		}else if(moneyStri.length()<10){
			moneyStri = moneyStri.substring(0,moneyStri.length()-6)+","+moneyStri.substring(moneyStri.length()-6,moneyStri.length()-3)+","+moneyStri.substring(moneyStri.length()-3,moneyStri.length());;
		}
		return moneyStri;
	}
	/**
	 * 如果str为null，返回“”,否则返回str
	 * 
	 * @param str
	 * @return
	 */
	public static String isNull(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	public static String isNull(Object o) {
		if (o == null) {
			return "";
		}
		String str="";
		if(o instanceof String){
			str=(String)o;
		}else{
			str=o.toString();
		}
		return str;
	}
	/**
	 * 字符串解码
	 * 
	 * @param sStr
	 * @param sEnc
	 * @return String
	 */
	public static String UrlDecoder(String sStr){
		String sReturnCode = sStr;
		try {
			sReturnCode = URLDecoder.decode(sStr, "utf-8");
		} catch (Exception e) {
		}
		return sReturnCode;
	}
	
	/**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     * @param value
     * @return Sting
     */
    public static String formatFloatNumber(double value) {
        if(value != 0.00){
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
            return df.format(value);
        }else{
            return "0.00";
        }

    }
    /**
     * 
    * @Title: subDoublePoint 
    * @Description: TODO 将double值点(####.###)后的值四舍五入去除，
    * 例如:3432423.654,return 3432423;13152.0 return 13152
    * @Param @param value
    * @Param @return
    * @Return String
    * @Throws
     */
    public static String subDoublePoint(Double value) {
    	if (value == null) {
			return null;
		}
        if(value != 0){
            java.text.DecimalFormat df = new java.text.DecimalFormat("##########");
            return df.format(value);
        }else{
            return "0";
        }

    }
    public static Object isNullThenZero(Object o){
		if(o == null){
			return 0;
		}else{
			return o;
		}
	}
    
    /**
     * 获取UUID
    * @Title: getUuid 
    * @Description: TODO
    * @Param @return
    * @Return String
    * @Throws
     */
    public static String getUuid(){
    	 String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");     
         return uuid;  
    }
    
    /**
     * 将手机号转换为 xxx****xxxx格式
     * @param mobile
     * @return
     */
    public static String signMobile(String mobile){
    	if(isEmpty(mobile)){
    		return "";
    	}else{
    		return mobile.substring(0,mobile.length()-(mobile.substring(3)).length())+"****"+mobile.substring(7);
    	}
    }
    
}
