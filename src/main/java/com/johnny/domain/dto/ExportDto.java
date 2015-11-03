package com.johnny.domain.dto;

import java.util.List;
import java.util.Map;

/**
 * 导出DTO
 * @author xiaobao
 *
 * 2015年10月23日
 */
public class ExportDto {
	
public ExportDto(){
		
	}
	
	/**
	 * 构造方法
	 * @param fileName 文件名称
	 * @param dataList 数据集合
	 * @param columnList 需要导出的字段名称
	 * @param beginRow 开始行数
	 * @param specialColMap 需要特殊处理的字段名称
	 * @param titleList 标题集合
	 * @param headsList 表格头部集合
	 */
	public ExportDto(String fileName,List<?> dataList,List<String> columnList,int beginRow,
			Map<String,Map<ExportDataType,Object>> specialColMap,List<ExportTitle> titleList,List<ExportHeads> headsList){
		this.fileName = fileName;
		this.dataList = dataList;
		this.columnList = columnList;
		this.beginRow = beginRow;
		this.titleList = titleList;
		this.headsList = headsList;
	}
	
	/**
	 * 构造方法
	 * @param fileName 文件名称
	 * @param dataList 数据集合
	 * @param columnList 需要导出的字段名称
	 * @param beginRow 开始行数
	 * @param specialColMap 需要特殊处理的字段名称
	 */
	public ExportDto(String fileName,List<?> dataList,List<String> columnList,int beginRow,Map<String,Map<ExportDataType,Object>> specialColMap){
		this.fileName = fileName;
		this.dataList = dataList;
		this.columnList = columnList;
		this.beginRow = beginRow;
	}
	
	/**
	 * 构造方法
	 * @param fileName 文件名称
	 * @param dataList 数据集合
	 * @param columnList 需要导出的字段名称
	 * @param beginRow 开始行数
	 * @param specialColMap 需要特殊处理的字段名称
	 */
	
	/**
	 * 
	 * @param fileName 文件名称
	 * @param dataList 数据集合
	 * @param columnList 需要导出的字段名称
	 * @param beginRow 开始行数
	 * @param hasNo 是否需要编号列
	 * @param specialColMap 需要特殊处理的字段名称
	 */
	public ExportDto(String fileName,List<?> dataList,List<String> columnList,int beginRow,boolean hasNo,Map<String,Map<ExportDataType,Object>> specialColMap){
		this.fileName = fileName;
		this.dataList = dataList;
		this.columnList = columnList;
		this.beginRow = beginRow;
		this.hasNo = hasNo;
		this.specialColMap = specialColMap;
	}
	
	/**
	 * 
	 * @param fileName 文件名称
	 * @param sheetName 工作薄名称
	 * @param dataList 数据集合
	 * @param columnList 需要导出的字段名称
	 */
	public ExportDto(String fileName, String sheetName,List<?> dataList,List<String> columnList){
		this.fileName = fileName;
		this.sheetName = sheetName;
		this.dataList = dataList;
		this.columnList = columnList;
	}
	
	/**
	 * 
	 * @param fileName 文件名称
	 * @param sheetName 工作薄名称
	 * @param dataList 数据集合
	 * @param titleList 列标题
	 * @param headsList 头部集合
	 * @param columnList 列集合
	 */
	public ExportDto(String fileName, String sheetName,List<?> dataList,List<ExportTitle> titleList,List<ExportHeads> headsList,List<String> columnList){
		this.fileName = fileName;
		this.sheetName = sheetName;
		this.dataList = dataList;
		this.titleList = titleList;
		this.headsList = headsList;
		this.columnList = columnList;
	}
	
	/**
	 * 是否需要编号
	 */
	private boolean hasNo;
	
	/**
	 * 
	 * 表格标题
	 * @author xiaobao
	 *
	 * 2015年10月29日
	 */
	public class ExportTitle{
		
		/**
		 * 无参构造方法
		 */
		public ExportTitle(){
			
		}
		
		/**
		 * 构造方法初始化字段
		 * @param titleName  表格标题名称
		 * @param titleRowNum 表格标题列号
		 * @param titleColNum 表格标题行号
		 */
		public ExportTitle(String titleName,int titleColNum,int titleRowNum){
			this.titleName = titleName;
			this.titleColNum = titleColNum;
			this.titleRowNum = titleRowNum;
		}
		
		/**
		 * 构造方法初始化字段
		 * @param titleName  表格标题名称
		 * @param titleRowNum 表格标题列号
		 * @param titleColNum 表格标题行号
		 * @param mergeColNum 标题需要合并到的列号
		 * @param mergeRowNum 标题需要合并到的行号
		 */
		public ExportTitle(String titleName,int titleColNum,int titleRowNum,int mergeColNum,int mergeRowNum){
			this.titleName = titleName;
			this.titleColNum = titleColNum;
			this.titleRowNum = titleRowNum;
			this.mergeColNum = mergeColNum;
			this.mergeRowNum = mergeRowNum;
		}
		
		/**
		 * 构造方法初始化字段
		 * @param titleName  表格标题名称
		 * @param titleRowNum 表格标题列号
		 * @param titleColNum 表格标题行号
		 * @param mergeColNum 标题需要合并到的列号
		 * @param mergeRowNum 标题需要合并到的行号
		 * @param rowHeight 标题行高
		 */
		public ExportTitle(String titleName,int titleColNum,int titleRowNum,int mergeColNum,int mergeRowNum,int rowHeight){
			this.titleName = titleName;
			this.titleColNum = titleColNum;
			this.titleRowNum = titleRowNum;
			this.mergeColNum = mergeColNum;
			this.mergeRowNum = mergeRowNum;
			this.rowHeight = rowHeight;
		}
		
		/**
		 * 构造方法初始化字段
		 * @param titleName  表格标题名称
		 * @param titleRowNum 表格标题列号
		 * @param titleColNum 表格标题行号
		 * @param mergeColNum 标题需要合并到的列号
		 * @param mergeRowNum 标题需要合并到的行号
		 * @param rowHeight 标题行高
		 * @param rowHeight 标题列宽
		 */
		public ExportTitle(String titleName,int titleColNum,int titleRowNum,int mergeColNum,int mergeRowNum,int rowHeight,int colWidth){
			this.titleName = titleName;
			this.titleColNum = titleColNum;
			this.titleRowNum = titleRowNum;
			this.mergeColNum = mergeColNum;
			this.mergeRowNum = mergeRowNum;
			this.rowHeight = rowHeight;
			this.colWidth = colWidth;
		}
		
		/**
		 * 表格标题名称
		 */
		private String titleName;
		
		/**
		 * 表格标题列号
		 */
		private int titleColNum;
		
		/**
		 * 表格标题行号
		 */
		private int titleRowNum;
		
		/**
		 * 标题需要合并到的列号
		 */
		private int mergeColNum;
		
		/**
		 * 标题需要合并到的行号
		 */
		private int mergeRowNum;
		
		/**
		 * 标题行高
		 */
		private int rowHeight;
		
		/**
		 * 标题列宽
		 */
		private int colWidth;
		
		public String getTitleName() {
			return titleName;
		}
		public void setTitleName(String titleName) {
			this.titleName = titleName;
		}
		public int getTitleRowNum() {
			return titleRowNum;
		}
		public void setTitleRowNum(int titleRowNum) {
			this.titleRowNum = titleRowNum;
		}
		public int getTitleColNum() {
			return titleColNum;
		}
		public void setTitleColNum(int titleColNum) {
			this.titleColNum = titleColNum;
		}

		public int getMergeColNum() {
			return mergeColNum;
		}

		public void setMergeColNum(int mergeColNum) {
			this.mergeColNum = mergeColNum;
		}

		public int getMergeRowNum() {
			return mergeRowNum;
		}

		public void setMergeRowNum(int mergeRowNum) {
			this.mergeRowNum = mergeRowNum;
		}

		public int getRowHeight() {
			return rowHeight;
		}

		public void setRowHeight(int rowHeight) {
			this.rowHeight = rowHeight;
		}

		public int getColWidth() {
			return colWidth;
		}

		public void setColWidth(int colWidth) {
			this.colWidth = colWidth;
		}
		
	}
	
	/**
	 * 
	 * 表头标题的每个列标题
	 * @author xiaobao
	 *
	 * 2015年10月29日
	 */
	public class ExportHeads{
		
		public ExportHeads(){
			
		}
		
		/**
		 * 初始化 
		 * @param headName 表头标题的每个列名称
		 * @param headWidth 表头标题的每个列宽度
		 */
		public ExportHeads(String headName,int headWidth){
			this.headName = headName;
			this.headWidth = headWidth;
		}
		
		/**
		 * 初始化 
		 * @param headName 表头标题的每个列名称
		 * @param headWidth 表头标题的每个列宽度
		 * @param hasHeadMerge 需要合并行和列
		 * @param headColNum 需要合并的列
		 * @param headRowNum 需要合并的行
		 */
		public ExportHeads(String headName,int headWidth,boolean hasHeadMerge,int headColNum, int headRowNum){
			this.headName = headName;
			this.headWidth = headWidth;
			this.hasHeadMerge = hasHeadMerge;
			this.headColNum = headColNum;
			this.headRowNum = headRowNum;
		}
		
		/**
		 * 初始化 
		 * @param headName 表头标题的每个列名称
		 * @param headWidth 表头标题的每个列宽度
		 * @param headRowHeight 表头标题的每个列宽度
		 * @param hasHeadMerge 需要合并行和列
		 * @param headColNum 需要合并的列
		 * @param headRowNum 需要合并的行
		 */
		public ExportHeads(String headName,int headWidth,int headRowHeight,boolean hasHeadMerge,int headColNum, int headRowNum){
			this.headName = headName;
			this.headWidth = headWidth;
			this.headRowHeight = headRowHeight;
			this.hasHeadMerge = hasHeadMerge;
			this.headColNum = headColNum;
			this.headRowNum = headRowNum;
		}
		
		/**
		 * 初始化头部
		 * @param headName
		 * @param headWidth
		 * @return
		 */
		public ExportHeads init(String headName,int headWidth){
			return new ExportHeads(headName, headWidth, 1000, hasHeadMerge, 0, 0);
		}
		
		public ExportHeads init(String headName,int headWidth,boolean hasHeadMerge,int headColNum, int headRowNum){
			return new ExportHeads(headName, headWidth, 1000, hasHeadMerge, 0, 0);
		}
		
		/**
		 * 表头标题的每个列名称
		 */
		private String headName;
		
		/**
		 * 表头标题的每个列宽度
		 */
		private int headWidth;
		
		/**
		 * 表头标题的高度
		 */
		private int headRowHeight;
		
		/**
		 * 表头标题列需要跨列
		 */
		private int headColNum;
		
		/**
		 * 表头标题列需要跨行
		 */
		private int headRowNum;
		
		/**
		 * 需要跨行或列
		 */
		private boolean hasHeadMerge;
		
		/**
		 * 表头标题的每个列名称
		 */
		public String getHeadName() {
			return headName;
		}
		
		/**
		 * 表头标题的每个列名称
		 */
		public void setHeadName(String headName) {
			this.headName = headName;
		}
		
		/**
		 * 表头标题的每个列宽度
		 */
		public int getHeadWidth() {
			return headWidth;
		}
		
		/**
		 * 表头标题的每个列宽度
		 */
		public void setHeadWidth(int headWidth) {
			this.headWidth = headWidth;
		}

		/**
		 * 导出标题列需要跨列
		 */
		public int getHeadColNum() {
			return headColNum;
		}

		/**
		 * 导出标题列需要跨列
		 */
		public void setHeadColNum(int headColNum) {
			this.headColNum = headColNum;
		}

		/**
		 * 导出标题列需要跨行
		 */
		public int getHeadRowNum() {
			return headRowNum;
		}

		/**
		 * 导出标题列需要跨行
		 */
		public void setHeadRowNum(int headRowNum) {
			this.headRowNum = headRowNum;
		}

		public boolean getHasHeadMerge() {
			return hasHeadMerge;
		}

		public void setHasHeadMerge(boolean hasHeadMerge) {
			this.hasHeadMerge = hasHeadMerge;
		}

		public int getHeadRowHeight() {
			return headRowHeight;
		}

		public void setHeadRowHeight(int headRowHeight) {
			this.headRowHeight = headRowHeight;
		}
		
	}
	
	/**
	 * 导出数据类型-时间,BigDecimal
	 */
	public enum ExportDataType {
		DATE,BIGDECIMAL
	};
	
	public enum exportTitleType{
		BR
	}
	
	/**
	 * 数据集合
	 */
	private List<?> dataList;
	
	/**
	 * 数据头部标题集合
	 */
	private List<ExportHeads> headsList;
	
	/**
	 * 数据表格标题
	 */
	private List<ExportTitle> titleList;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 工作薄名称
	 */
	private String sheetName;
	
	/**
	 * 开始循环数据行数
	 */
	private int beginRow;
	
	/**
	 * 字段名称集合
	 */
	private List<String> columnList;
	
	/**
	 * 需要特殊处理的字段
	 * key:字段名称,value:类型
	 */
	private Map<String,Map<ExportDataType,Object>> specialColMap;

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	public List<ExportHeads> getHeadsList() {
		return headsList;
	}

	public void setHeadsList(List<ExportHeads> headsList) {
		this.headsList = headsList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getBeginRow() {
		return beginRow;
	}

	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public Map<String, Map<ExportDataType, Object>> getSpecialColMap() {
		return specialColMap;
	}

	public void setSpecialColMap(Map<String, Map<ExportDataType, Object>> specialColMap) {
		this.specialColMap = specialColMap;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public boolean getHasNo() {
		return hasNo;
	}

	public void setHasNo(boolean hasNo) {
		this.hasNo = hasNo;
	}

	public List<ExportTitle> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<ExportTitle> titleList) {
		this.titleList = titleList;
	}
	
}
