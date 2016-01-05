package com.johnny.pdf.template;

//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Date;
//import java.util.Map;
//
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
import com.johnny.pdf.BasePdfProtocol;

/**
 * 
 * pdf生成类
 *
 * 2015年9月12日
 */
public class PdfProtocol extends BasePdfProtocol{
	
	private Object pdfTemplateDto;
	
	private String path;
	
	private String tempPath;

	public PdfProtocol(String path,String tempPath){
		super(path);
		this.tempPath = tempPath;
	}
	
//	@Override
//	protected Map<String, Object> fillProtoclData() {
//		Map<String, Object> date = super.fillProtoclData();
//		date.put("no", StringTools.isNull(pdfTemplateDto.getNo()));
//		date.put("postalAddr", StringTools.isNull(pdfTemplateDto.getPostalAddr()));
//		date.put("realName", StringTools.isNull(pdfTemplateDto.getRealName()));
//		date.put("money", UpNumber.toChinese(pdfTemplateDto.getLoanMoney()) + "整");
//		date.put("lastTime",DateTools.date2String(pdfTemplateDto.getSignDate(), "yyyy年MM月dd日"));
//		date.put("rate",StringTools.isNull(pdfTemplateDto.getRate()));
//		date.put("idNumber",StringTools.isNull(pdfTemplateDto.getIdNumber()));
//		date.put("phone",StringTools.isNull(pdfTemplateDto.getPhone()));
//		date.put("cardNo",StringTools.isNull(pdfTemplateDto.getBankcardNo()));
//		date.put("bankName",StringTools.isNull(pdfTemplateDto.getBankName()));
//		date.put("payMoney",pdfTemplateDto.getPayMoney());
//		date.put("startTime",DateTools.date2String(new Date(), "yyyy年MM月"));
//		date.put("endTime",DateTools.date2String(pdfTemplateDto.getSignDate(), "yyyy年MM月"));
//		return date;
//	}
	
//	@Override
//	protected void readTemplate() throws IOException {
//		super.setTemplateReader(new TemplateReader(new InputStreamReader(
//				new FileInputStream(tempPath),"utf-8")));
//	}
//	
//	@Override
//	protected void addPdfTable(PdfTools pdf) throws DocumentException {
//		 // 创建表格，5列的表格
//        PdfPTable table = new PdfPTable(2);
//        table.setTotalWidth(PageSize.A4.getWidth()- 100);
//        table.setLockedWidth(true);
//        // 创建头
//        PdfPCell pdfPCell = new PdfPCell();
//        pdfPCell.setColspan(2);
//        table.addCell(pdfPCell);
//        Font font = pdf.getFont();
//        // 添加内容
//        table.addCell(new Paragraph("\r\r　　　　　　　　　借款人\r\r借款人：\r\r\r\r\r\r法定代表人/负责人：（签字）\r\r（或代理人）\r\r",font));
//        table.addCell(new Paragraph("\r\r　　　　　　　　　贷款人\r\r贷款人：\r\r\r\r\r\r负责人：（签字或盖章）\r\r（或代理人）\r\r",font));
//        pdfPCell.addElement(new Paragraph("保证人\r\r保证人：\r\r法定代表人/负责人：（签字）\r\r（或代理人）\r\r", font));
//        table.addCell(pdfPCell);
//		pdf.addTable(table);
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
