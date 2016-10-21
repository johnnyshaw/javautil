/**
 * Copyright (c) 2016, 杭州惟安信息科技有限公司  All Rights Reserved.
 */

package com.johnny.collection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * map相关操作工具类
 * date:     2016年10月21日 下午2:43:21
 * @author   xiaobao
 */
public class MapUtil {
	
	/**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    
    /**
     * 对象字段转get请求,url拼接，如：localhost:8080/test?key=value&.....
     * @param baseReq
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String jsonToUrlParam(Object baseReq) throws Exception{
		Map<String,String> map = convertBean(baseReq);
    	String queryString = "";  
        for (String key : map.keySet()) {  
            String value = map.get(key);
            if(StringUtils.isNotEmpty(value))
            	queryString += key + "=" + value + "&";  
        }
        return queryString;
    }

}

