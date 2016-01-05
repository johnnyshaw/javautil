package com.johnny.sms;

import java.io.Serializable;

/**
 * 
 * 短信对象
 * 
 * 
 * 2015年8月28日
 */
public class SmsObj implements Serializable {

	private static final long serialVersionUID = 5966803972328384860L;
	/**
	 * 渠道
	 */
	private String channel;
	/**
	 * 渠道名称
	 */
	private String channelName;
	/**
	 * 支付方式： 后付费，预付费
	 */
	private String chargeType;
	/**
	 * 已发送数量
	 */
	private String sendNum;
	/**
	 * 剩余数量
	 */
	private String leftNum;
	/**
	 * 总点数 当支付方式为预付费是返回总充值点数
	 */
	private String totalNum;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getSendNum() {
		return sendNum;
	}

	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getLeftNum() {
		return leftNum;
	}

	public void setLeftNum(String leftNum) {
		this.leftNum = leftNum;
	}

}
