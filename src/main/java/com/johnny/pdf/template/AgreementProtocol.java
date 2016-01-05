package com.johnny.pdf.template;

//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Date;
//import java.util.Map;

import com.johnny.pdf.BasePdfProtocol;


/**
 * 
 * pdf生成类
 *
 * 2015年9月12日
 */
public class AgreementProtocol extends BasePdfProtocol{
	
	private Object pdfTemplateDto;
	
	private String path;
	
	private String tempPath;

	public AgreementProtocol(String path,String tempPath){
		super(path);
		this.tempPath = tempPath;
	}
	
//	@Override
//	protected Map<String, Object> fillProtoclData() {
//		Map<String, Object> date = super.fillProtoclData();
//		date.put("no", StringTools.isNull(pdfTemplateDto.getNo()));
//		date.put("realName", StringTools.isNull(pdfTemplateDto.getRealName()));
//		date.put("idNumber",StringTools.isNull(pdfTemplateDto.getIdNumber()));
//		date.put("phone",StringTools.isNull(pdfTemplateDto.getPhone()));
//		date.put("cardNo",StringTools.isNull(pdfTemplateDto.getBankcardNo()));
//		date.put("bankName",StringTools.isNull(pdfTemplateDto.getBankName()));
//		date.put("payMoney",pdfTemplateDto.getPayMoney());
//		date.put("startTime",DateTools.date2String(new Date(), "yyyy年MM月"));
//		date.put("endTime",DateTools.date2String(pdfTemplateDto.getSignDate(), "yyyy年MM月"));
//		return date;
//	}
//	
//	@Override
//	protected void readTemplate() throws IOException {
//		super.setTemplateReader(new TemplateReader(new InputStreamReader(
//				new FileInputStream(tempPath),"utf-8")));
//	}
	
	public Object getPdfTemplateDto() {
		return pdfTemplateDto;
	}

	public void setPdfTemplateDto(Object pdfTemplateDto) {
		this.pdfTemplateDto = pdfTemplateDto;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}	
	
}
