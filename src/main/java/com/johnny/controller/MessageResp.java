package com.johnny.controller;


/**
 * 控制层返回结果对象。
 *
 * 2015年7月13日
 */
public class MessageResp {

    protected String code = "100200";

    protected String msg = "";
    
    /**
     * 返回结果的具体数据对象
     */
    protected Object data;

    public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getRespCode() {
		return code;
	}

	public void setRespCode(String respCode) {
		this.code = respCode;
	}

	public String getRespMsg() {
		return msg;
	}

	public void setRespMsg(String respMsg) {
		this.msg = respMsg;
	}

	public void setMessage(RespCode respCode) {
		code = respCode.getCode();
		msg = respCode.getContent();
    }
	
	public void setMessageWithAppend(RespCode respCode,String appendMsg) {
		code = respCode.getCode();
		msg = respCode.getContent()+","+appendMsg;
    }

}
