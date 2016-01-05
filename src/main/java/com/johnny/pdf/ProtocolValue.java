package com.johnny.pdf;

import java.util.HashMap;
import java.util.Map;

/**
 * pdf模板属性
 *
 * 2015年9月12日
 */
public class ProtocolValue {
	private Map<String,Object> data=new HashMap<String,Object>();
	
	private final String NULL="NULL";
	
	public ProtocolValue() {
		super();
	}

	public Object printProtocol(String var){
		Object ret="";
		String[] tVars=var.split("\\.");
		if(tVars==null){
			return ret;
		}else if(tVars.length==1){
			ret=data.get(tVars[0]);
		}else if(tVars.length==2){
			ret=data.get(tVars[0]);
			if(ret==null){
				return NULL;
			}else{
				ret=ReflectUtils.invokeGetMethod(ret.getClass(), ret, tVars[1]);
			}
		}
		return ret;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
