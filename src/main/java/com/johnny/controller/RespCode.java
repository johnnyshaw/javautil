package com.johnny.controller;

/**
 * 返回状态信息对象
 *
 * 2015年8月17日
 */
public class RespCode {

	private String mCode;
	private String mContent;
	
	public RespCode(String code,String msg) {
		this.mCode = code;
		this.mContent = msg;
	}
	
    public String getCode() {
        return mCode;
    }

    public String getContent() {
        return mContent;
    }
	
	
}
