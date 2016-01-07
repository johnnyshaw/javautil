package com.johnny.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * service远程接口调用工具类
 *
 * 2015年8月28日
 */
@SuppressWarnings("deprecation")
public class RemoteHttpUtil {

	public final static Logger logger = LoggerFactory
			.getLogger(RemoteHttpUtil.class);

	/**
	 * 
	 * @param targetURL
	 *            目标地址
	 * @param data
	 *            请求实体
	 * @param targetC
	 *            响应实体类型
	 * @return 响应实体
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public static <T> T sendByKV(String targetURL, Object data, Class<T> targetC) {
		if (StringUtils.isEmpty(targetURL) || targetC == null) {
			return null;
		}
		DefaultHttpClient httpClient = null;
		HttpPost post = null;
		try {
			logger.info("--to-targetURL:" + targetURL);

			ObjectMapper jsonOM = new ObjectMapper();
			String reqPkg = jsonOM.writeValueAsString(data);
			logger.info("--to-reqpkg:" + reqPkg);

			// 封装请求数据
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			Map dataMap = null;
			if (data instanceof String) {
				NameValuePair param = new BasicNameValuePair("k",
						String.valueOf(data));
				paramList.add(param);
			} else if (data instanceof Map) {
				dataMap = (HashMap) data;
			} else {
				dataMap = BeanUtils.describe(data);
			}
			// 转换KV
			if (dataMap != null) {
				Iterator<String> ite = dataMap.keySet().iterator();
				while (ite.hasNext()) {
					String k = ite.next();
					Object v = dataMap.get(k);
					if (v == null) {
						continue;
					}
					NameValuePair param = new BasicNameValuePair(k,String.valueOf(v));
					paramList.add(param);
				}
			}

			if (paramList.isEmpty()) {
				NameValuePair param = new BasicNameValuePair("k", "1");
				paramList.add(param);
			}

			// 初始化HTTP连接
			httpClient = new DefaultHttpClient();
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
			HttpConnectionParams.setSoTimeout(httpParams, 120000);
			httpClient.setParams(httpParams);

			// 初始化POST方式
			post = new HttpPost(targetURL);
			// 设置消息体
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,
					"UTF-8");
			post.setEntity(entity);

			// 发送消息
			HttpResponse response = httpClient.execute(post);
			int respCode = response.getStatusLine().getStatusCode();
			if (respCode >= 100 && respCode < 300) {
				// 消息应答
				HttpEntity respBody = response.getEntity();
				String respPkg = EntityUtils.toString(respBody, "UTF-8");
				logger.info("--to-resppkg:" + respPkg);
				return (T) jsonOM.readValue(respPkg, targetC);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("访问服务异常BY-KV", e);
			return null;
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
	}

	/**
	 * 
	 * @param targetURL
	 *            目标地址
	 * @param data
	 *            请求实体
	 * @param targetC
	 *            响应实体类型
	 * @return 响应实体
	 */
	@SuppressWarnings("resource")
	public static <T> T sendByJson(String targetURL, Object data,
			Class<T> targetC) {
		if (StringUtils.isEmpty(targetURL) || data == null || targetC == null)
			return null;
		DefaultHttpClient httpClient = null;
		HttpPost post = null;
		try {
			logger.info("--to-targetURL:" + targetURL);
			
			String token=null;
			try{
				token=BeanUtils.getProperty(data, "token");
			}catch(Exception e){
				logger.info("--to-object:未检测到token参数");
			}
			
			if(StringUtils.isNotEmpty(token)){
				targetURL=targetURL+"?token="+token;
			}
			
			ObjectMapper jsonOM = new ObjectMapper();
			String reqPkg = jsonOM.writeValueAsString(data);
			logger.info("--to-reqpkg:" + reqPkg);

			// 初始化HTTP连接
			httpClient = new DefaultHttpClient();
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
			HttpConnectionParams.setSoTimeout(httpParams, 120000);
			httpClient.setParams(httpParams);

			post = new HttpPost(targetURL);
			
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-Type", "application/json;charset=UTF-8");

			// 设置消息体
			HttpEntity entity = new StringEntity(reqPkg, "UTF-8");
			post.setEntity(entity);

			// 发送消息
			HttpResponse response = httpClient.execute(post);
			int respCode = response.getStatusLine().getStatusCode();
			if (respCode >= 100 && respCode < 300) {
				// 消息应答
				HttpEntity respBody = response.getEntity();
				String respPkg = EntityUtils.toString(respBody, "UTF-8");
				logger.info("--to-resppkg:" + respPkg);
				return (T) jsonOM.readValue(respPkg, targetC);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("访问服务异常BY-JSON", e);
			return null;
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
	}

}
