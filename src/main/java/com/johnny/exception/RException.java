package com.johnny.exception;

import com.johnny.controller.RespCode;


/**
 * 全局异常基类
 *
 */
public class RException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RespCode respMessage;
	
    private Throwable e;
    
    public RException(RespCode respMessage) {
        this.respMessage = respMessage;
    }

    public RException(RespCode respMessage, Throwable e) {
        this.respMessage = respMessage;
        this.e = e;
    }

    public Throwable getThrowable() {
        return e;
    }

	public RespCode getRespMessage() {
		return respMessage;
	}

	public void setRespMessage(RespCode respMessage) {
		this.respMessage = respMessage;
	}
	
	 /**
     * 返回异常提示信息：
     */
    @Override
    public String getMessage() {
        return this.getRespMessage().getContent();
    }

}
