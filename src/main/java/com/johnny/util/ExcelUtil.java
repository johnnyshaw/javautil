package com.johnny.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.johnny.domain.dto.ExportDto;

/**
 *    
 * 类名称：ExcelUtil    
 * 类描述：通用导出工具类    
 * 创建时间：2012-08-21    
 * @version   1.0
 * @author Johnny
 *
 */
public class ExcelUtil {
	
	private static Log log = LogFactory.getLog(ExcelUtil.class);
	
	/**
	 * 通用导出方法
	 * @deprecated
	 * @param response
	 * @param request
	 * @param list
	 *            结果集
	 * @param xhead 如果标题有编号的,那就在xhead的第一个加"%"占位,没有无需加
	 *            第一行标题
	 * @param xheadwidth
	 *            第一行标题宽度
	 * @param fileName
	 *            文件名
	 * @param title
	 *            标题(如果有第二行标题如:需添加<br/>换行,如有两列则添加#) 示例:String[] conTitle = new
	 *            String[] { fileName:文件名,sheetName:工作薄名称, "<br/>",
	 *            "制表人:;"+name+";12;1","#","制表日期:;"+workDate+";24;1"};
	 *            制表人:是标题,name:其值,12是合并到多少列(以";"分割),1:行
	 * @author 
	 * @Create 2012-08-21
	 */
	public static void exportJxlExcel(HttpServletRequest request, HttpServletResponse response,
			List list, String[] xhead, Integer[] xheadwidth, String fileName, String[] title) {
		try {
			fileName = new String(fileName.getBytes(), "iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String workDate = df.format(new Date());
		response.addHeader("Content-disposition", "attachment;filename=" + fileName+workDate + ".xls");
		response.setContentType("application/vnd.ms-excel;charset=GBK");
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			workbook = Workbook.createWorkbook(os);
			WritableSheet ws = workbook.createSheet("数据列表", 0);
			WritableCellFormat wcfmat = ExcelUtil.nameStytle();
			WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 12,
					WritableFont.BOLD, false);
			WritableCellFormat wcfmatTitle = new WritableCellFormat(wfont);
			wcfmatTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 设置表格的边框指定为黑色
			wcfmatTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直方向居中
			Integer tempColumn = null;
			for (int i = 0; i < title.length; i++) {
				if (i == 0) {
					ws.addCell(new jxl.write.Label(i, i, title[i], wcfmat));
					ws.setColumnView(i, 100);
					ws.setRowView(i, 1000);
					ws.mergeCells(0, 0, xhead.length - 1, 0);
				}
				if ("<br/>".equals(title[i])) {
					String tempTile = title[i + 1];
					String[] titleArray = tempTile.split(";");
					ws.addCell(new jxl.write.Label(0, 1, titleArray[0] + titleArray[1],
									wcfmatTitle));
					ws.setRowView(i, 300);
					ws.mergeCells(0, 1, Integer.parseInt(titleArray[2]), 0);
					tempColumn = Integer.parseInt(titleArray[2]) + 1;
				} else if ("#".equals(title[i])) {
					String tempTile = title[i + 1];
					String[] titleArray = tempTile.split(";");
					ws.addCell(new jxl.write.Label(tempColumn, 1, titleArray[0] + titleArray[1],
							wcfmatTitle));
					ws.mergeCells(tempColumn, 1, Integer.parseInt(titleArray[2]), 0);
				}
			}
			WritableCellFormat wcfmtTitle = ExcelUtil.titleStytle();
			WritableCellFormat wcfmtContent = ExcelUtil.contentStytle();
			for (int i = 0; i < xhead.length; i++) {
				if ("%".equals(xhead[i])) {
					ws.addCell(new jxl.write.Label(i, 2, "编号", wcfmtTitle));
				} else {
					ws.addCell(new jxl.write.Label(i, 2, xhead[i], wcfmtTitle));
				}
				ws.setColumnView(i, xheadwidth[i]);
			}
			jxl.write.Label label = null;
			String value = "";
			for (int c = 0; c < list.size(); c++) {
				Object[] table = (Object[]) list.get(c);
				for (int i = 0, j = 0; i < xhead.length; i++) {
					if ("%".equals(xhead[i])) {
						if (i == 0) {
							value = String.valueOf(c + 1);
							label = new jxl.write.Label(i, c + 3, value, wcfmtContent);
							ws.addCell(label);
							label = null;
						}
					} else {
						value = table[j] == null ? "" : String.valueOf(table[j]);
						label = new jxl.write.Label(i, c + 3, value, wcfmtContent);
						ws.addCell(label);
						j++;
						label = null;
					}
				}
			}
			workbook.write();
			ws = null;
			list = null;
		} catch (IOException e) {
			log.error("异常信息：", e);
		} catch (Exception e) {
			log.error("异常信息：", e);
		} finally {
			try {
				if(workbook!=null)workbook.close();
				if(os!=null)os.close();
			} catch (WriteException e) {
				log.error("异常信息：", e);
			} catch (IOException e) {
				log.error("异常信息：", e);
			}
			workbook = null;
			os = null;
		}
	}
	
	/**
	 * 通用导出方法
	 * 
	 * @param response
	 * @param request
	 * @param list
	 *            结果集
	 * @param xhead 如果标题有编号的,那就在xhead的第一个加"%"占位,没有无需加
	 *            第一行标题
	 * @param xheadwidth
	 *            第一行标题宽度
	 * @param fileName
	 *            文件名
	 * @param title
	 *            标题(如果有第二行标题如:需添加<br/>换行,如有两列则添加#) 示例:String[] conTitle = new
	 *            String[] { fileName,:文件名,sheetName:工作薄名称 "<br/>",
	 *            "制表人:;"+name+";12;1","#","制表日期:;"+workDate+";24;1"};
	 *            制表人:是标题,name:其值,12是合并到多少列(以";"分割);1:行数
	 * @param beginRow
	 *            开始写数据的行数
	 * @param cells
	 *            需要合并的行与列,
	 *            数组格式:String [] cells = new String[]{"0;1;5;1"};
	 *            0:第一列;2:第二行;5:第六列;2:第二行;
	 * @author 
	 * @Create 2012-08-21
	 */
	public static void exportJxlExcel(HttpServletRequest request, HttpServletResponse response,
			List list, String[] xhead, Integer[] xheadwidth, String fileName, String[] title,int beginRow,String[] cells) {
		try {
			fileName = new String(fileName.getBytes(), "iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			log.error("异常信息：", e1);
		}
		System.out.println(request.getAttribute("merges")+"....");
		int merges=request.getAttribute("merges")!=null?Integer.valueOf(request.getAttribute("merges").toString()):1;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String workDate = df.format(new Date());
		response.addHeader("Content-disposition", "attachment;filename=" + fileName+workDate + ".xls");
		response.setContentType("application/vnd.ms-excel;charset=GBK");
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			workbook = Workbook.createWorkbook(os);
			WritableSheet ws = workbook.createSheet(title[1], 0);
			WritableCellFormat wcfmat = ExcelUtil.nameStytle();
			WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 12,
					WritableFont.NO_BOLD, false);
			WritableCellFormat wcfmatTitle = new WritableCellFormat(wfont);
			wcfmatTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 设置表格的边框指定为黑色
			wcfmatTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直方向居中
			Integer tempColumn = null;
			for (int i = 0; i < title.length; i++) {
				if (i == 0 && !"".equals(title[i])) {
					ws.addCell(new jxl.write.Label(i, i, title[i], wcfmat));
					ws.setColumnView(i, 100);
					ws.setRowView(i, 1000);
					ws.mergeCells(0, 0, xhead.length - merges, 0);
				}
				if ("<br/>".equals(title[i])) {
					i++;
					String tempTile = title[i];
					String[] titleArray = tempTile.split(";");
					ws.addCell(new jxl.write.Label(0, Integer.parseInt(titleArray[3]), titleArray[0] + titleArray[1],
									wcfmatTitle));
					tempColumn = Integer.parseInt(titleArray[2]) + 1;
				} else if ("#".equals(title[i])) {
					String tempTile = title[i + 1];
					String[] titleArray = tempTile.split(";");
					ws.addCell(new jxl.write.Label(tempColumn, Integer.parseInt(titleArray[3]), titleArray[0] + titleArray[1],
							wcfmatTitle));
					tempColumn = Integer.parseInt(titleArray[2]) + 1;
				}
			}
			WritableCellFormat wcfmtTitle = ExcelUtil.titleStytle();
			WritableCellFormat wcfmtContent = ExcelUtil.contentStytle();
			for (int i = 0; i < xhead.length; i++) {
				if ("%".equals(xhead[i])) {
					ws.addCell(new jxl.write.Label(i, beginRow-1, "编号", wcfmtTitle));
				} else {
					int count = xhead[i].indexOf(";");
					if(count > 0){
						String [] st = xhead[i].split(";");
						ws.addCell(new jxl.write.Label(Integer.parseInt(st[1]), Integer.parseInt(st[2]), st[0], wcfmtTitle));
					}else{
						ws.addCell(new jxl.write.Label(i, beginRow-1, xhead[i], wcfmtTitle));
					}
				}
				ws.setColumnView(i, xheadwidth[i]);
			}
			ExcelUtil.mergeCells(cells, ws);
			jxl.write.Label label = null;
			String value = "";
			if(null != list && list.size()>0){
				for (int c = 0; c < list.size(); c++) {
					Object[] table = (Object[]) list.get(c);
					for (int i = 0, j = 0; i < xhead.length-merges+1; i++) {
						if ("%".equals(xhead[i])) {
							if (i == 0) {
								value = String.valueOf(c + 1);
								label = new jxl.write.Label(i, c + beginRow, value, wcfmtContent);
								ws.addCell(label);
								label = null;
							}
						} else {
							value = table[j] == null ? "" : String.valueOf(table[j]);
							label = new jxl.write.Label(i, c + beginRow, value, wcfmtContent);
							ws.addCell(label);
							j++;
							label = null;
						}
					}
				}
			}
			workbook.write();
			ws = null;
			list = null;
		} catch (IOException e) {
			log.error("异常信息：", e);
		} catch (Exception e) {
			log.error("异常信息：", e);
		} finally {
			try {
				if(workbook!=null)workbook.close();
				if(os!=null)os.close();
			} catch (WriteException e) {
				log.error("异常信息：", e);
			} catch (IOException e) {
				log.error("异常信息：", e);
			}
			workbook = null;
			os = null;
		}
	}
	
	/**
	 * 通用导出方法
	 * 
	 * @param response
	 * @param request
	 * @param list
	 *            结果集
	 * @param xhead 如果标题有编号的,那就在xhead的第一个加"%"占位,没有无需加
	 *            第一行标题
	 * @param xheadwidth
	 *            第一行标题宽度
	 * @param fileName
	 *            文件名
	 * @param title
	 *            标题(如果有第二行标题如:需添加<br/>换行,如有两列则添加#) 示例:String[] conTitle = new
	 *            String[] { fileName,:文件名,sheetName:工作薄名称 "<br/>",
	 *            "制表人:;"+name+";12;1","#","制表日期:;"+workDate+";24;1"};
	 *            制表人:是标题,name:其值,12是合并到多少列(以";"分割);1:行数
	 * @param beginRow
	 *            开始写数据的行数
	 * @param cells
	 *            需要合并的行与列,
	 *            数组格式:String [] cells = new String[]{"0;1;5;1"};
	 *            0:第一列;2:第二行;5:第六列;2:第二行;
	 * @author 
	 * @Create 2012-08-21
	 */
	public static void exportJxlExcel(HttpServletRequest request, HttpServletResponse response, ExportDto exportDto) {
		try {
			exportDto.setFileName(new String(exportDto.getFileName().getBytes(), "iso8859-1"));
		} catch (UnsupportedEncodingException e1) {
			log.error("异常信息：", e1);
		}
		int merges=request.getAttribute("merges")!=null?Integer.valueOf(request.getAttribute("merges").toString()):1;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String workDate = df.format(new Date());
		response.addHeader("Content-disposition", "attachment;filename=" + exportDto.getFileName()+workDate + ".xls");
		response.setContentType("application/vnd.ms-excel;charset=GBK");
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			workbook = Workbook.createWorkbook(os);
			WritableSheet ws = workbook.createSheet(exportDto.getTitle()[1], 0);
			WritableCellFormat wcfmat = ExcelUtil.nameStytle();
			WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 12,
					WritableFont.NO_BOLD, false);
			WritableCellFormat wcfmatTitle = new WritableCellFormat(wfont);
			wcfmatTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 设置表格的边框指定为黑色
			wcfmatTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直方向居中
			Integer tempColumn = null;
			for (int i = 0; i < exportDto.getTitle().length; i++) {
				if (i == 0 && !"".equals(exportDto.getTitle()[i])) {
					ws.addCell(new jxl.write.Label(i, i, exportDto.getTitle()[i], wcfmat));
					ws.setColumnView(i, 100);
					ws.setRowView(i, 1000);
					ws.mergeCells(0, 0, exportDto.getXhead().length - merges, 0);
				}
				if ("<br/>".equals(exportDto.getTitle()[i])) {
					i++;
					String tempTile = exportDto.getTitle()[i];
					String[] titleArray = tempTile.split(";");
					ws.addCell(new jxl.write.Label(0, Integer.parseInt(titleArray[3]), titleArray[0] + titleArray[1],
									wcfmatTitle));
					tempColumn = Integer.parseInt(titleArray[2]) + 1;
				} else if ("#".equals(exportDto.getTitle()[i])) {
					String tempTile = exportDto.getTitle()[i + 1];
					String[] titleArray = tempTile.split(";");
					ws.addCell(new jxl.write.Label(tempColumn, Integer.parseInt(titleArray[3]), titleArray[0] + titleArray[1],
							wcfmatTitle));
					tempColumn = Integer.parseInt(titleArray[2]) + 1;
				}
			}
			WritableCellFormat wcfmtTitle = ExcelUtil.titleStytle();
			WritableCellFormat wcfmtContent = ExcelUtil.contentStytle();
			for (int i = 0; i < exportDto.getXhead().length; i++) {
				if ("%".equals(exportDto.getXhead()[i])) {
					ws.addCell(new jxl.write.Label(i, exportDto.getBeginRow()-1, "编号", wcfmtTitle));
				} else {
					int count = exportDto.getXhead()[i].indexOf(";");
					if(count > 0){
						String [] st = exportDto.getXhead()[i].split(";");
						ws.addCell(new jxl.write.Label(Integer.parseInt(st[1]), Integer.parseInt(st[2]), st[0], wcfmtTitle));
					}else{
						ws.addCell(new jxl.write.Label(i, exportDto.getBeginRow()-1, exportDto.getXhead()[i], wcfmtTitle));
					}
				}
				ws.setColumnView(i, exportDto.getXheadwidth()[i]);
			}
			ExcelUtil.mergeCells(exportDto.getCells(), ws);
			jxl.write.Label label = null;
			String value = "";
			if(null != exportDto.getList() && exportDto.getList().size()>0){
				for (int c = 0; c < exportDto.getList().size(); c++) {
					Class classType = exportDto.getList().get(c).getClass();
					Object[] table = new Object[exportDto.getColumnList().size()]; 
					for (int t = 0; t < exportDto.getColumnList().size(); t++) {
//						Field field = classType.getDeclaredField(str);
						table[t] = ReflectionUtils.getFieldValue(exportDto.getList().get(c), exportDto.getColumnList().get(t));
						//格式化时间为2015-10-23
//						if(table[t] != null){
//							if(table[t] instanceof Date){
//								table[t] = DateTools.format((Date)table[t], DateTools.DATE_FORMAT_10);
//							}
//						}
					}
//					Object[] table = (Object[]) exportDto.getList().get(c);
					for (int i = 0, j = 0; i < exportDto.getXhead().length-merges+1; i++) {
						if ("%".equals(exportDto.getXhead()[i])) {
							if (i == 0) {
								value = String.valueOf(c + 1);
								label = new jxl.write.Label(i, c + exportDto.getBeginRow(), value, wcfmtContent);
								ws.addCell(label);
								label = null;
							}
						} else {
							value = table[j] == null ? "" : String.valueOf(table[j]);
							label = new jxl.write.Label(i, c + exportDto.getBeginRow(), value, wcfmtContent);
							ws.addCell(label);
							j++;
							label = null;
						}
					}
				}
			}
			workbook.write();
			ws = null;
			exportDto.setList(null);
		} catch (IOException e) {
			log.error("异常信息：", e);
		} catch (Exception e) {
			log.error("异常信息：", e);
		} finally {
			try {
				if(workbook!=null)workbook.close();
				if(os!=null)os.close();
			} catch (WriteException e) {
				log.error("异常信息：", e);
			} catch (IOException e) {
				log.error("异常信息：", e);
			}
			workbook = null;
			os = null;
		}
	}
	
	/**
	 * 第一行标题样式
	 * 
	 * @return WritableCellFormat
	 * @author 
	 * @Create 2012-08-21
	 */
	public static WritableCellFormat nameStytle() {
		try {
			WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 16,
					WritableFont.BOLD, false);
			WritableCellFormat wcfmat = new WritableCellFormat(wfont);// 单元格样式
			wcfmat.setAlignment(jxl.format.Alignment.CENTRE);
			wcfmat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 设置表格的边框指定为黑色
			wcfmat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直方向居中
			// wcfmat.setBackground(Colour.GRAY_25);
			return wcfmat;
		} catch (WriteException e) {
			log.error("异常信息：", e);
		}// 水平方向居中
		return null;
	}
	
	/**
	 * 各列title样式
	 * 
	 * @return WritableCellFormat
	 * @author 
	 * @Create 2012-08-21
	 */
	public static WritableCellFormat titleStytle() {
		try {
			WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 16,
					WritableFont.BOLD, false);
			WritableCellFormat wcfmat = new WritableCellFormat(wfont);// 单元格样式
			wcfmat.setAlignment(jxl.format.Alignment.CENTRE);
			wcfmat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 设置表格的边框指定为黑色
			wcfmat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直方向居中
			wcfmat.setBackground(jxl.format.Colour.GREY_25_PERCENT);// 设置背景色
			return wcfmat;
		} catch (WriteException e) {
			log.error("异常信息：", e);
		}// 水平方向居中
		return null;
	}
	
	/**
	 * 表格内容样式
	 * 
	 * @return WritableCellFormat
	 * @author 
	 * @Create 2012-08-21
	 */
	public static WritableCellFormat contentStytle() {
		try {
			WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 11);
			WritableCellFormat wcfmatcenter = new WritableCellFormat(wf);
			wcfmatcenter.setAlignment(jxl.format.Alignment.CENTRE);
			wcfmatcenter.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			wcfmatcenter.setWrap(true);
			return wcfmatcenter;
		} catch (WriteException e) {
			log.error("异常信息：", e);
		}// 水平方向居中
		return null;
	}
	
	/**
	 * Excel合并行与列    
	 * @param cells 数组,以";"分割,如:"0;1;5;1",0:第一列,1:第二行,5:第六列,1第二行
	 * @param ws void
	 * @author 
	 * @Create 2012-08-21
	 */
	public static void mergeCells(String [] cells,WritableSheet ws){
		try {
			if(cells != null && cells.length > 0){
				for(int i=0;i<cells.length;i++){
					String [] cell = cells[i].split(";");
					ws.mergeCells(Integer.parseInt(cell[0]), Integer.parseInt(cell[1]), Integer.parseInt(cell[2]), Integer.parseInt(cell[3]));
				}
			}
		} catch (RowsExceededException e) {
			log.error("异常信息：", e);
		} catch (NumberFormatException e) {
			log.error("异常信息：", e);
		} catch (WriteException e) {
			log.error("异常信息：", e);
		}
	}
	
}
