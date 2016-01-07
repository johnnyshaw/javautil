package com.johnny.log;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

/**
 * 日志类，定义日志的内容及输出格式.
 * 使用了Alibaba fastjson
 */
public class LogInfo {
	
	/**
	 * 记录用户行为日志模板
	 * <p>logger.info(LogInfo.OPTLOGTPL ,userId,”新增用户”);</p>
	 */
	public final static String OPTLOGTPL = "用户ID={},操作={}";
	
	/**
	 * 记录操作详细日志模板
	 * <p>logger.info(LogInfo.DETAILLOGTPL ,”新增用户”,”操作失败，用户名不能为空”);</p>
	 */
	public final static String DETAILLOGTPL = "操作={},详情={}";
	
	private final static String LOGTEMPLATE = "用户ID=%s,操作=%s,参数=[%s]";
	
	private LogInfo(){
		
	}
	
	/**
	 * 将对象转换为json字符串
	 * @param o 对象
	 * @return json字符串
	 */
	public static String toJson(Object o) {
		return JSON.toJSONString(o);
	}

	@Deprecated
	public static String toString(String userId,String doing,Object ... args) {
		Assert.notNull(userId, "用户ID不能为空");
		Assert.notNull(doing, "用户操作不能为空");
		StringBuilder sb = new StringBuilder();
		if(args != null) {
			int i = 0;
			for(Object o : args) {
				sb.append("参数" + (i++) + "=").append(o).append(",");
			}
		}
		return String.format(LOGTEMPLATE, userId, doing, sb.toString());
	}
	
	@Deprecated
	public static String toJson(String userId,String doing,Object ... args) {
		Assert.notNull(userId, "用户ID不能为空");
		Assert.notNull(doing, "用户操作不能为空");
		StringBuilder sb = new StringBuilder();
		if(args != null) {
			int i = 0;
			for(Object o : args) {
				sb.append("参数" + (i++) + "=").append(JSON.toJSONString(o)).append(",");
			}
		}
		return String.format(LOGTEMPLATE, userId, doing, sb.toString());
	}
	
}
