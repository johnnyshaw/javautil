package com.johnny.sms;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Component;

import com.johnny.config.SmsRmiConfig;


/**
 * 
 * 短信发送工具类
 *
 * 2015年8月28日
 */
@Component
public class SendSMS {
	
	@Resource
	private  SmsRmiConfig smsRmiConfig;
	
	private static ISendMessage sendMessage ;
	private Logger log = Logger.getLogger(SendSMS.class);
	
	/**
	 * @Title: send
	 * @Description: 短信发送
	 * @Param @param content 发送内容
	 * @Param @param mobile 接收者手机
	 * @Param @return 发送成功标识
	 * @Return int 0成功，非0失败
	 */
	public int send(String content, String mobile ,String invoker) {
		int res = 10000;
		try {
			if(sendMessage == null){
				loadRmi();
			}
			res = sendMessage.send(content, mobile , invoker);
		} catch (Exception e) {
			res = 10000;
			log.error("SendSMS ..send..调用发送短信失败",e);
			loadRmi();
		}
		log.debug("客户端发送短信-------"+mobile+",content:"+content);
		return res;
	}
	
	/**
	 * 
	 * @Title: channel_query 
	 * @Description: 查询通道详情
	 */
	public SmsObj channel_query(int channel) {
		SmsObj dto = null;
		try {
			if(sendMessage == null){
				loadRmi();
			}
			dto = sendMessage.channel_query(channel);
		} catch (Exception e) {
			log.error("SendSMS ..send..调用发送短信失败",e);
			loadRmi();
		}
		return dto;
	}
	
	/**
	 * @Title: loadRmi 
	 * @Description: 获取短信rmi服务对象
	 */
	private void loadRmi(){
		    //动态参数,rmi地址
		    String serverAddress = smsRmiConfig.getSmsUrl();
		    //动态参数,rmi端口
		    String serverPort = smsRmiConfig.getSmsPort();
		    String cacheRmiUrl = "rmi://" + serverAddress + ":" + serverPort + "/messageServer";
		    RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
		    factory.setServiceInterface(ISendMessage.class);
		    factory.setServiceUrl(cacheRmiUrl);
		    factory.afterPropertiesSet();
		    sendMessage = (ISendMessage)factory.getObject();
		    log.warn("---sms loadRmi---短信rmi初始化-Address:"+serverAddress+",port:"+serverPort);
	}
}
