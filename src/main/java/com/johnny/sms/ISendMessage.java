package com.johnny.sms;

import java.rmi.RemoteException;

import org.springframework.stereotype.Component;


/**
 * 
 * 远程调用
 * @author licx
 *
 * 2015年8月28日
 */
@Component
public interface ISendMessage {
	
	public int send(String content, String mobile,String invoker) throws RemoteException;
	
	public SmsObj channel_query(int channel);
	
}
