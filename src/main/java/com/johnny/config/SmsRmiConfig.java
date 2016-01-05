package com.johnny.config;

import org.springframework.stereotype.Component;


/**
 * 
 * 短信发送配置文件
 * 以后可迁移至zookeeper中
 * 
 * 2015年8月20日
 */
@Component
public class SmsRmiConfig {
//	@ZkConfigAnnotation(path = "/xdgc-user/sms/smsUrl", defaultValue = "192.168.7.7")
	private String smsUrl;
//	@ZkConfigAnnotation(path = "/xdgc-user/sms/smsPort", defaultValue = "11071")
	private String smsPort;

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getSmsPort() {
		return smsPort;
	}

	public void setSmsPort(String smsPort) {
		this.smsPort = smsPort;
	}

}
