package com.johnny.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * 控制层的父类.提供公用方法.
 *
 * 2015年7月14日
 */
public class CommonController {
	
	/**
	 * 从绑定错误中获得响应对象
	 * @param errorResult 绑定错误
	 * @return 错误响应对象
	 */
	protected MessageResp buildResp(BindingResult errorResult) {
		List<ObjectError>objectErrors = errorResult.getAllErrors();
		Map<String,String> errMap = new LinkedHashMap<String,String>();
		for (ObjectError objectError : objectErrors) {
			String errKey = "";
			if(objectError instanceof FieldError) {
				errKey = ((FieldError)objectError).getField();
			} else {
				errKey = objectError.getObjectName();
			}
			if(errMap.get(errKey) != null) {
				errMap.put(errKey, errMap.get(errKey) + "##" + objectError.getDefaultMessage());
			} else {
				errMap.put(errKey, objectError.getDefaultMessage());
			}
		}
		return buildResp(errMap, CommonRespConst.ARGS_ERROR);
	}

	protected static MessageResp success(Object data) {
		return buildResp(data, CommonRespConst.SUCCESS);
	}
	
	protected static MessageResp fail(Object data) {
		return buildResp(data, CommonRespConst.FAIL);
	}
	
	protected static MessageResp buildResp(Object data, RespCode respEnum) {
		MessageResp resp = new MessageResp();
		resp.setMessage(respEnum);
		resp.setData(data);
		return resp;
	}
	
	protected static MessageResp success() {
		return buildResp(null, CommonRespConst.SUCCESS);
	}
	
	protected static MessageResp fail() {
		return buildResp(null, CommonRespConst.FAIL);
	}
	
	protected static MessageResp buildResp(RespCode respEnum) {
		return buildResp(null, respEnum);
	}
}
