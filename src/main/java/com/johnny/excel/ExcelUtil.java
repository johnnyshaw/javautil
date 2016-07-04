package com.johnny.excel;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.johnny.date.DateTools;
import com.johnny.domain.dto.ExportDto;
import com.johnny.domain.dto.ExportDto.ExportDataType;
import com.johnny.domain.dto.ExportDto.ExportHeads;
import com.johnny.domain.dto.ExportDto.ExportTitle;
import com.johnny.reflection.ReflectionUtils;


/**
 * 
 * 通用导出工具类 JXL包基础上开发
 * @author xiaobao
 *
 * 2015年10月23日
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
			exportDto.setFileName(new String(exportDto.getFileName().getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			log.error("异常信息：", e1);
		}
		int merges=request.getAttribute("merges")!=null?Integer.valueOf(request.getAttribute("merges").toString()):1;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String workDate = df.format(new Date());
		response.setHeader("Content-disposition", "attachment;filename=" + exportDto.getFileName()+workDate + ".xls");
		response.setContentType("application/vnd.ms-excel;charset=GBK");
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			workbook = Workbook.createWorkbook(os);
			//如果工作薄名称为空,则默认文件名为工作薄名称
			if(exportDto.getSheetName() == null || "".equals(exportDto.getSheetName())){
				exportDto.setSheetName(new String(exportDto.getFileName().getBytes("iso-8859-1"), "GBK"));
			}
			//创建工作薄,并设置名称
			WritableSheet ws = workbook.createSheet(exportDto.getSheetName(), 0);
			WritableCellFormat wcfmat = ExcelUtil.nameStytle();
			WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 12,
					WritableFont.NO_BOLD, false);
			WritableCellFormat wcfmatTitle = new WritableCellFormat(wfont);
			wcfmatTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 设置表格的边框指定为黑色
			wcfmatTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直方向居中
			//处理整个Excel表格标题
			int titleMergeColNum = 0,titleMergeRowNum = 0;
//			boolean isTitleMergeRow = false;
			if(exportDto.getTitleList() != null){
				for (int i = 0; i < exportDto.getTitleList().size(); i++) {
					ExportTitle exportTitle = exportDto.getTitleList().get(i);
					ws.addCell(new jxl.write.Label(exportTitle.getTitleColNum(), exportTitle.getTitleRowNum(), exportTitle.getTitleName(), wcfmat));
					//如果未设置行高,默认设置高度1000
					if(exportTitle.getRowHeight()==0){
						ws.setRowView(i, 1000);
					}else{
						ws.setRowView(i, exportTitle.getRowHeight());
					}
					
					//如果未设置列宽,默认设置高度100
					if(exportTitle.getColWidth()==0){
						ws.setColumnView(i, 100);
					}else{
						ws.setColumnView(i, exportTitle.getColWidth());
					}
					
					//如果标题有需要合并的列
					if(exportTitle.getMergeColNum() != 0){
						titleMergeColNum = exportTitle.getMergeColNum();
					}else{	//否则默认为合并所有的表头
						titleMergeColNum = exportDto.getHeadsList().size() - merges;
					}
					//如果标题有需要合并的行
					if(exportTitle.getMergeRowNum() != 0){
						titleMergeRowNum = exportTitle.getMergeRowNum();
						//配合下面数据起始行数使用,如果标题有跨行就必须设置开始行数,否则难以计算,此处非不能计算,而是太麻烦
//						if(!isTitleMergeRow){
//							isTitleMergeRow = true;
//						}
					}else{	//否则默认当前行
						titleMergeRowNum = exportTitle.getTitleRowNum();
					}
					//合并标题行和列
					ws.mergeCells(exportTitle.getTitleColNum(), exportTitle.getTitleRowNum(), titleMergeColNum, titleMergeRowNum);
				}
			}
			
			
			WritableCellFormat wcfmtTitle = ExcelUtil.titleStytle();
			WritableCellFormat wcfmtContent = ExcelUtil.contentStytle();
			int beginRow = 0;
			
			//判断数据开始行数是否设置
			//特别注意,仅限标题不跨行,如果标题跨行则必须要设置数据开始行数
			if(exportDto.getBeginRow() == 0){
				throw new Exception("因标题及头部涉及到跨行,所以开始行数[beginRow]不能为空!");
			}else{
				beginRow = exportDto.getBeginRow();
			}
			//开始处理导出数据列标题集合
			for (int i = 0; i < exportDto.getHeadsList().size(); i++) {
				ExportHeads head = exportDto.getHeadsList().get(i);
				//如果导出需要编号列
				if (exportDto.getHasNo()) {
					ws.addCell(new jxl.write.Label(i, beginRow, "编号", wcfmtTitle));
				} else{
					//如果头部需要合并
					if(head.getHasHeadMerge()){
						ws.addCell(new jxl.write.Label(head.getHeadColNum(), head.getHeadRowNum(), head.getHeadName(), wcfmtTitle));
						//合并标题行和列
						ws.mergeCells(head.getHeadColNum(), head.getHeadRowNum(), head.getMergeColNum(), head.getMergeRowNum());
					}else{
						if(head.getHeadColNum() == 0 && head.getHeadRowNum() == 0){
							ws.addCell(new jxl.write.Label(i, beginRow-1, head.getHeadName(), wcfmtTitle));
						}else{
							ws.addCell(new jxl.write.Label(head.getHeadColNum(), head.getHeadRowNum(), head.getHeadName(), wcfmtTitle));
						}
					}
				}
				ws.setColumnView(i, head.getHeadWidth());
			}
			
//			ExcelUtil.mergeCells(exportDto.getCells(), ws);
			jxl.write.Label label = null;
			String value = "";
			if(null != exportDto.getDataList() && exportDto.getDataList().size()>0){
				Object[] countTotal = new Object[exportDto.getColumnList().size()];
				for (int c = 0; c < exportDto.getDataList().size(); c++) {
					Class classType = exportDto.getDataList().get(c).getClass();
					Object[] table = new Object[exportDto.getColumnList().size()]; 
					for (int t = 0; t < exportDto.getColumnList().size(); t++) {
//						Field field = classType.getDeclaredField(str);
						if(exportDto.getDataList().get(c) instanceof Map){
							table[t] = ((Map)exportDto.getDataList().get(c)).get(exportDto.getColumnList().get(t));
						}else{
							table[t] = ReflectionUtils.getFieldValue(exportDto.getDataList().get(c), exportDto.getColumnList().get(t));
						}
						
						if(table[t] != null){
							//判断是否有需要特殊处理的字段map集合
							if(exportDto.getSpecialColMap() != null && exportDto.getSpecialColMap().containsKey(exportDto.getColumnList().get(t))){
								//根据字段名称获取
								Map<ExportDataType,Object> map = exportDto.getSpecialColMap().get(exportDto.getColumnList().get(t));
								//判断该字段需要特殊处理
								if(map != null ){
									//当需要特殊处理的字段为时间类型
									if(map.containsKey(ExportDto.ExportDataType.DATE)){
										if(map.get(ExportDto.ExportDataType.DATE)!=null){
											SimpleDateFormat sdf = new SimpleDateFormat(String.valueOf(map.get(ExportDto.ExportDataType.DATE)));
											table[t] = sdf.format(table[t]);
										}
									}else if(map.containsKey(ExportDto.ExportDataType.BIGDECIMAL)){//当需要特殊处理的字段为BigDecimal
										String temp = String.valueOf(map.get(ExportDto.ExportDataType.BIGDECIMAL));
										table[t] = ((BigDecimal)table[t]).setScale(Integer.valueOf(temp));
									}
								}
							}else{
								//默认处理时间为yyyy-MM-dd
								if(table[t] instanceof Date){
									table[t] = DateTools.format((Date)table[t], DateTools.DATE_FORMAT_10);
								}else if(table[t] instanceof Double){
									DecimalFormat dataformat = new DecimalFormat("0.00");
									table[t] = Double.valueOf(dataformat.format(table[t]));
								}
							}
							if (exportDto.getHasNo()){
								if (c == 0) {
									value = String.valueOf(c + 1);
									label = new jxl.write.Label(t, c + beginRow, value, wcfmtContent);
									ws.addCell(label);
									label = null;
								}
							} else {
								//需要合计
								if(exportDto.isNeedTotal()){
									if(table[t] instanceof Integer){
										int result = Integer.valueOf(String.valueOf(table[t]));
										countTotal[t] = countTotal[t]==null?0:countTotal[t];
										countTotal[t] = Integer.parseInt(String.valueOf(countTotal[t]))+result;
									}else if(table[t] instanceof Double){
										double result = Double.valueOf(String.valueOf(table[t]));
										countTotal[t] = countTotal[t]==null?0:countTotal[t];
										countTotal[t] = Double.parseDouble(String.valueOf(countTotal[t]))+result;
									}else{
										countTotal[t] = "";
									}
								}
								value = table[t] == null ? "" : String.valueOf(table[t]);
								label = new jxl.write.Label(t, c + beginRow, value, wcfmtContent);
								ws.addCell(label);
								label = null;
								
							}
						} else {
							value = table[t] == null ? "" : String.valueOf(table[t]);
							label = new jxl.write.Label(t, c + beginRow, value, wcfmtContent);
							ws.addCell(label);
							label = null;
						}
					}
					
				}
				//需要合计
				if(exportDto.isNeedTotal()){
					countTotal[0] = "合计";
					for (int i = 0; i < countTotal.length; i++) {
						value = countTotal[i] == null ? "" : String.valueOf(countTotal[i]);
						if(countTotal[i] instanceof Double){
							DecimalFormat dataformat = new DecimalFormat("0.00");
							value = dataformat.format(countTotal[i]);
						}
						label = new jxl.write.Label(i,exportDto.getDataList().size()-1 + beginRow + 1, value, wcfmtContent);
						ws.addCell(label);
						label = null;
					}
				}
				
			}
			workbook.write();
			ws = null;
			exportDto.setDataList(null);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("异常信息：", e);
		} catch (Exception e) {
			e.printStackTrace();
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
	
	/**
	 * 计算开始行数
	 * @param beginRow 开始行数
	 * @param size 表格标题行数,或者标题行数+表头行数
	 * 该方法不使用,因为如果要计算开始行数,方法会变得更加负责,所有暂时不使用,还是自己设置数据开始行数
	 * @return
	 */
	@SuppressWarnings("unused")
	private static int calBeginRow(int beginRow,int size){
		//等于0数据开始默认为标题行数+1
		if(beginRow == 0){
			beginRow = size;
		}
		return beginRow;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		ExportDto exportDto = new ExportDto();
		list.add("1");
		list.add("22");
		exportDto.setDataList(list);
		for (int i = 0; i < exportDto.getDataList().size(); i++) {
			System.out.println(exportDto.getDataList().get(i).getClass());
		}
	}
	
}
