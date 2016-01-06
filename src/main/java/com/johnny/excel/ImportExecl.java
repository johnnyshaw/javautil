package com.johnny.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.johnny.date.DateTools;


/**
 * excel处理类POI基础上开发
 *
 * 2015年8月13日
 */
public  class ImportExecl {
	/** 总行数 */
	private static int totalRows = 0;
	/** 总列数 */
	private static int totalCells = 0;
	/** 错误信息 */
	private static String errorInfo;
	/** 构造方法 */
	public  ImportExecl() {

	}

/**
 * 得到总行数
* @Title: getTotalRows 
* @Param @return
* @Return int
* @Throws
 */
	public static int getTotalRows() {
		return totalRows;
	}

	/**
	 * 得到总列数
	* @Title: getTotalCells 
	* @Param @return
	* @Return int
	* @Throws
	 */
	public static int getTotalCells() {
		return totalCells;
	}

	/**
	 * 得到错误信息
	* @Title: getErrorInfo 
	* @Param @return
	* @Return String
	* @Throws
	 */
	public static String getErrorInfo() {

		return errorInfo;

	}

	/**
	 * 验证excel文件
	* @Title: validateExcel 
	* @Param @param filePath
	* @Param @return
	* @Return boolean
	* @Throws
	 */
	public static boolean validateExcel(String filePath) {
		/** 检查文件名是否为空或者是否是Excel格式的文件 */
		if (filePath == null
				|| !(WDWUtil.isExcel2003(filePath) || WDWUtil
						.isExcel2007(filePath))) {
			errorInfo = "文件名不是excel格式";
			return false;
		}
		/** 检查文件是否存在 */
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			errorInfo = "文件不存在";
			return false;
		}
		return true;
	}

	/**
	 * 根据文件名读取excel文件
	* @Title: read 
	* @Param @param filePath
	* @Param @return
	* @Return List<List<String>>
	* @Throws
	 */
	public static List<List<String>> read(String filePath,String type,int sheeAt) {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		InputStream is = null;
		try {
			/** 验证文件是否合法 */
			if (!validateExcel(filePath)) {
				System.out.println(errorInfo);
				return null;
			}
			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (WDWUtil.isExcel2007(filePath)) {
				isExcel2003 = false;
			}
			/** 调用本类提供的根据流读取的方法 */
			File file = new File(filePath);
			is = new FileInputStream(file);
			dataLst = read(is, isExcel2003,type,sheeAt);
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		/** 返回最后读取的结果 */
		return dataLst;
	}

	/**
	 * 根据流读取Excel文件
	* @Title: read 
	* @Param @param inputStream
	* @Param @param isExcel2003
	* @Param @return
	* @Return List<List<String>>
	* @Throws
	 */
	public static List<List<String>> read(InputStream inputStream, boolean isExcel2003,String type,int sheeAt) {
		List<List<String>> dataLst = null;
		try {
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			if (isExcel2003) {
				wb = new HSSFWorkbook(inputStream);
			} else {
				wb = new XSSFWorkbook(inputStream);
			}
			dataLst = read(wb,type,sheeAt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataLst;

	}

	/**
	 * 读取数据
	* @Title: read 
	* @Param @param wb
	* @Param @return
	* @Return List<List<String>>
	* @Throws
	 */

	private static List<List<String>> read(Workbook wb,String type,int sheeAt) {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		/** 得到第一个shell */
		Sheet sheet = wb.getSheetAt(sheeAt);
		/** 得到Excel的行数 */
		totalRows = sheet.getLastRowNum();
		/** 得到Excel的列数 */
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		/** 循环Excel的行 */
		for (int r = 0; r <= totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				System.out.println(r);
				continue;
			}
			List<String> rowLst = new ArrayList<String>();
			/** 循环Excel的列 */
			for (int c = 0; c < getTotalCells(); c++) {

				Cell cell = row.getCell(c);

				String cellValue = "";
				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						//System.out.println("类型：："+cell.getCellType());
						if ("1".equals(type)) {
							if (cell.getCellStyle().getDataFormat()==176) {// 处理日期格式、时间格式  
				                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");   
				                Date date = cell.getDateCellValue();  
				                cellValue = sdf.format(date);  
				               // System.out.println("176--"+cellValue);
				           } else {
				        	   cellValue = cell.getNumericCellValue() + "";
				            }	
						}else {
							 cellValue = cell.getNumericCellValue() + "";
						}
						// System.out.println("cell.getCellType())::"+cell.getCellStyle().getDataFormat()+ "  cellValue::"+cellValue);
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;
					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;

					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
					}
				}
				rowLst.add(cellValue);
			}
			/** 保存第r行的第c列 */
			dataLst.add(rowLst);
		}
		return dataLst;

	}

	/**
	 * main测试方法
	 * 
	 * @Title: main
	 * @Param @param args
	 * @Param @throws Exception
	 * @Return void
	 * @Throws
	 */
	/*public static void main(String[] args) throws Exception {

		// List<List<String>> list = poi.read("e:/123123.xls",null);
		List<List<String>> list = ImportExecl.read("H:/data/conf/zl/tmp/ycAccount_2015-10-30.xls","1",1);
		//path表示你所创建文件的路径
		String path = "";
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		} 
		// fileName表示你创建的文件名；为txt类型；
		String fileName="test.txt";
		File file = new File(f,fileName);
		if(!file.exists()){
			file.createNewFile();
		}
		File file = new File("192.168.7.7:8082/dataloan/");
		if(!file.exists()){
			file.createNewFile();
		}
		FileWriter fileWriter=new FileWriter("192.168.7.7:8082/dataloan/aaa.txt");
		if (list != null) {
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				List<String> cellList = list.get(i);
				for (int j = 0; j < cellList.size(); j++) {
					// System.out.print("    第" + (j + 1) + "列值：");
					//System.out.print("    " + cellList.get(j));
					 fileWriter.write(cellList.get(j)+"\t");
					 
				}
				fileWriter.write("\r\n");
			}
			
			fileWriter.write("完毕");
			fileWriter.close();
			System.out.println("完毕");
		}

	}*/
	
	public static String excelToTxt(String excelPath,int sheeAt,String txtPath,String separator) throws IOException{
		FileWriter fileWriter=new FileWriter(excelPath);
		List<List<String>> list = ImportExecl.read(txtPath,"1",sheeAt);
		if (list != null) {
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				List<String> cellList = list.get(i);
				for (int j = 0; j < cellList.size(); j++) {
					 fileWriter.write(cellList.get(j)+separator);
				}
				fileWriter.write("\r\n");
			}
			fileWriter.close();
			System.out.println("文件转换完毕");
		}
		return excelPath;
	}
}

/**
 * 工具类
 * 
 * @ClassName: WDWUtil
 * @Author liaocw
 * @Date 2015年1月15日 上午11:49:14
 * @Modify
 * @CopyRight 杭州阿思拓电子商务有限公司
 */
class WDWUtil {

	/**
	 * 是否是2003的excel，返回true是2003
	 * 
	 * @Title: isExcel2003
	 * @Param @param filePath
	 * @Param @return
	 * @Return boolean
	 * @Throws
	 */

	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	/**
	 * 是否是2007的excel，返回true是2007
	 * 
	 * @Title: isExcel2007
	 * @Param @param filePath
	 * @Param @return
	 * @Return boolean
	 * @Throws
	 */

	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	
}
