package com.johnny.domain.dto;

import java.util.List;

/**
 * 导出DTO
 * @author xiaobao
 *
 * 2015年10月23日
 */
public class ExportDto {
	
	/**
	 * 数据集合
	 */
	private List<?> list;
	
	/**
	 * 头部
	 */
	private String[] xhead;
	
	/**
	 * 头部宽度
	 */
	private Integer[] xheadwidth;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 标题
	 */
	private String [] title;
	
	/**
	 * 开始循环数据行数
	 */
	private int beginRow;
	
	/**
	 * 单元格
	 */
	private String [] cells;
	
	/**
	 * 需要导出字段名称集合
	 */
	private List<String> columnList;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String[] getXhead() {
		return xhead;
	}

	public void setXhead(String[] xhead) {
		this.xhead = xhead;
	}

	public Integer[] getXheadwidth() {
		return xheadwidth;
	}

	public void setXheadwidth(Integer[] xheadwidth) {
		this.xheadwidth = xheadwidth;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public int getBeginRow() {
		return beginRow;
	}

	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}

	public String[] getCells() {
		return cells;
	}

	public void setCells(String[] cells) {
		this.cells = cells;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

}
