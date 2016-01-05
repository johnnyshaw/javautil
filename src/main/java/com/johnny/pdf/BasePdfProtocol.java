package com.johnny.pdf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.WritableDirectElement;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.WritableElement;

/**
 * pdf基类
 *
 * 2015年9月12日
 */
public class BasePdfProtocol {
	private static Logger logger = Logger.getLogger(BasePdfProtocol.class);
	private PdfTools pdf;
	private TemplateReader templateReader;
	private Map<String,Object> data=new HashMap<String,Object>();
	
	public BasePdfProtocol(String path){
		pdf = PdfTools.instance(path);
	}

	protected Map<String,Object> fillProtoclData(){
		SimpleDateFormat sdf_year = new  SimpleDateFormat("yyyy年MM月dd日");
		String year = sdf_year.format(new Date());
		data.put("now", year);		
		return data;
	}
	
	public void createPdf() throws Exception {
		logger.info("读取PDF模板");
		readTemplate();
		logger.info("处理PDF模板");
		doTemplate();
		logger.info("导出PDF");
		pdf.exportPdf();
	}

	protected void readTemplate() throws IOException{};
	
	protected void doTemplate() throws IOException, DocumentException{
		while (templateReader.read()!=-1){}
		templateReader.close();
		List<String> elements=templateReader.getLines();
		ProtocolValue pv=new ProtocolValue();
		pv.setData(fillProtoclData());
		StringBuffer sb=new StringBuffer();
		for(String e:elements){
			if(e!=null&&e.startsWith("${")&&e.endsWith("}")){
				e = e.substring(2, e.length() - 1);
				sb.append(pv.printProtocol(e));
			}else{
				sb.append(e);
			}
		}
		templateHtml(sb.toString());
	}
	
	protected void addPdfTable(PdfTools pdf)throws DocumentException {}
	
	protected String templateHtml(String str) throws IOException,
			DocumentException {
		logger.info("打印协议内容：" + str);
		final List<Element> pdfeleList = new ArrayList<Element>();
		ElementHandler elemH = new ElementHandler() {
			public void add(final Writable w) {
				if (w instanceof WritableElement) {
					pdfeleList.addAll(((WritableElement) w).elements());
				}
			}
		};
		InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(
				str.getBytes("UTF-8")), "UTF-8");
		XMLWorkerHelper.getInstance().parseXHtml(elemH, isr);
		List<Element> list = new ArrayList<Element>();
		for (Element ele : pdfeleList) {
			if (ele instanceof LineSeparator
					|| ele instanceof WritableDirectElement) {
				continue;
			}
			list.add(ele);
		}
		pdf.addHtmlList(list);
		return "";
	}

	public PdfTools getPdf() {
		return pdf;
	}
	public void setPdf(PdfTools pdf) {
		this.pdf = pdf;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public TemplateReader getTemplateReader() {
		return templateReader;
	}

	public void setTemplateReader(TemplateReader templateReader) {
		this.templateReader = templateReader;
	}
	
}
