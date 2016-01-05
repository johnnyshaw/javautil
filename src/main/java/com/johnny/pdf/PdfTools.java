package com.johnny.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
/**
 * pdf工具类
 *
 * 2015年9月12日
 */
public class PdfTools {

	private Logger logger = Logger.getLogger(PdfTools.class);
	private Document document;

	private BaseFont bfChinese;
	private Font font;

	public PdfTools(String path) {
		document = new Document();
		File file = new File(path);
		if(!file.getParentFile().exists()) {//判断父路劲是否存在
			file.getParentFile().mkdir();
		}
		try {
			PdfWriter.getInstance(document, new FileOutputStream(path,true));// 建立一个PdfWriter对象
			document.open();
			bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 设置中文字体
			font = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
		} catch (Exception de) {
			logger.error(de);
		} 
	}
	
	public void addTable(PdfPTable table)throws DocumentException {
		document.add(table);
	}
	
	public static PdfTools instance(String path) {
		return new PdfTools(path);
	}

	public void exportPdf() {
		document.close();
	}
	
	public void addHtmlList(List<Element> list) throws DocumentException {
		for(Element e:list){
			document.add(e);
		}
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	
}
