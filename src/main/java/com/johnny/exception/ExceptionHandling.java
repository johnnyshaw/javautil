package com.johnny.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnny.controller.CommonRespConst;
import com.johnny.controller.MessageResp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理基于springmvc,springboot。
 *
 * 2015年7月14日
 */
public abstract class ExceptionHandling {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandling.class);

	private static final String RESP_TEMPLATE = "{\"respCode\" : \"%s\",\"respMsg\" : \"%s\"}";
	
	protected abstract String getUserId(String token);
	
	/**
	 * 全局异常处理，记录异常详细日志.
	 * @return 返回为空
	 */
	@ExceptionHandler(value = Exception.class)
	public ModelAndView resolveException(HttpServletRequest req,
			HttpServletResponse resp, Exception ex) {
		MessageResp msgResp = new MessageResp();
		String token = req.getParameter("token");
		String userId = "";
		if(StringUtils.isNotEmpty(token)) {
			userId = getUserId(token);
		}
		String path = req.getRequestURI();
		if(logger.isErrorEnabled()) {
			logger.error("程序异常", ex);
		}
		String errorDetail = ex.getMessage();
		if(ex instanceof RException) {
			RException xdgcEx = (RException) ex;
			msgResp.setMessage(xdgcEx.getRespMessage());
			errorDetail = xdgcEx.getCause() == null ? errorDetail : xdgcEx.getCause().getMessage();
		} else {
			msgResp.setMessage(CommonRespConst.FAIL);
		}
		logger.error("程序异常，用户ID={},url={},状态码={},信息={},详情={}", userId,path,msgResp.getRespCode(),msgResp.getRespMsg(),errorDetail);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		try {
			resp.getWriter().write(String.format(RESP_TEMPLATE, msgResp.getRespCode(),msgResp.getRespMsg()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
