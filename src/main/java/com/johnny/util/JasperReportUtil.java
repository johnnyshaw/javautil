package com.johnny.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 
 * 项目名称：operation    
 * 类名称：JasperReportUtil    
 * 类描述：JasperReport报表工具类    
 * 创建时间：Mar 5, 2012 9:57:26 AM       
 * @version   1.0
 * @author xiaobao
 *
 */
public class JasperReportUtil {
	
	/**
	 * 用jasperReport导出Pdf
	 * @param request 
	 * @param response
	 * @param reportFilePath jasper报表文件路径
	 * @param paramMap 字段
	 * @param dataList 
	 * @throws IOException
	 * @throws JRException void
	 * @author xiaobao 
	 * @Create Mar 5, 2012 10:01:39 AM
	 */
	public static String exportReportToPDF(HttpServletRequest request,HttpServletResponse response,String reportFilePath, Map<String, Object> paramMap, List dataList)
			throws IOException, JRException {
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
		String fileName = paramMap.get("fileName").toString();
		fileName = fileName+".pdf";
		String path = request.getRealPath("/")+File.separator+"report"+File.separator+"export"+File.separator+ fileName;
		JasperRunManager.runReportToPdfFile(reportFilePath, path, paramMap, dataSource);
		return fileName;
	}
	
	/**
	 * 用jasperReport导出Pdf
	 * @param request 
	 * @param response/
	 * @param reportFilePath jasper报表文件路径
	 * @param paramMap 字段
	 * @param dataList 
	 * @throws IOException
	 * @throws JRException void
	 * @author xiaobao 
	 * @Create Mar 5, 2012 10:01:39 AM
	 */
	public static void viewReportToPDF(HttpServletRequest request,HttpServletResponse response,String reportFilePath, Map<String, Object> paramMap, List dataList)
			throws IOException, JRException {
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
		byte[] bytes = JasperRunManager.runReportToPdf(reportFilePath, paramMap, dataSource);
		
		String fileName = paramMap.get("fileName").toString();
		try {
			fileName = new String(fileName.getBytes(), "iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		OutputStream out =response.getOutputStream();
		response.setContentType("application/pdf;charset=GBK");
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+".pdf");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		out.write(bytes, 0, bytes.length);
		out.flush();
		out.close();
	}
	
	/**
	 * 用jasperReport打印Pdf
	 * @param request 
	 * @param response/
	 * @param reportFilePath jasper报表文件路径
	 * @param paramMap 字段
	 * @param dataList 
	 * @throws IOException
	 * @throws JRException void
	 * @author xiaobao 
	 * @Create Mar 5, 2012 10:01:39 AM
	 */
	public static String printReportToHTML(HttpServletRequest request,HttpServletResponse response,String reportFilePath, Map<String, Object> paramMap, List dataList)
			throws IOException, JRException {
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
		String path = JasperRunManager.runReportToHtmlFile(reportFilePath, paramMap, dataSource);
		
		String fileName = paramMap.get("fileName").toString();
		try {
			fileName = new String(fileName.getBytes(), "iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return path;
	}
	
	 /**
	 * 压缩文件zip-Apache中ant所带包
	 * @功能信息 :
	 * @参数信息 :
	 * @返回值信息 :
	 * @异常信息 :
	 * */
	public static String getZip(List list, String path, String fileName) throws Exception {
		byte[] buffer = new byte[1024];
		
		String strZipName = fileName + ".zip";
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path + strZipName));
		for (int j = 0; j < list.size(); j++) {
			String name = list.get(j).toString();
			FileInputStream fis = new FileInputStream(path + name);
			out.putNextEntry(new ZipEntry(name));
			int len;
			while ((len = fis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			fis.close();
			new File(path + name).delete();
		}
		out.close();
		String zipFilePath = path + strZipName;
//		System.out.println(zipFilePath+"----");
//		System.out.println("生成Demo.zip成功");
		return zipFilePath;
	}
	
	public static void main(String[]args){
		List list = new ArrayList();
		list.add("0合同-20120306172247.pdf");
		list.add("1合同-20120306172247.pdf");
		list.add("2合同-20120306172247.pdf");
		try {
			JasperReportUtil.getZip(list, "E:\\johnny\\Project_7.0\\operation\\WebRoot\\report\\export\\", "合同");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
     * 把一个文件转化为字节
     * @param file
     * @return   byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) throws Exception
    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if(length>Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
            {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset<bytes.length&&(numRead=is.read(bytes,offset,bytes.length-offset))>=0)
            {
                offset+=numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset<bytes.length)
            {
                System.out.println("file length is error");
                return null;
            }
            is.close();
        }
        file.delete();
        return bytes;
    }
    
    public Map getMap(){
    	Map map = new HashMap();
//    	map.put("vcstandards", arg1)
    	return map;
    }
    
    /**
	 * 把Blob类型转换为String类型
	 * @param blob
	 * @return
	 */
	public static String blobToString(Blob blob) {
		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream(blob.getBinaryStream());
			byte[] bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
			return new String(bytes);
		} catch (Exception e) {
			return null;
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				return null;
			}
		}
	}
	
	/**
	 * 把Blob类型转换为byte类型数组
	 * @param blob
	 * @return
	 */
	public static byte[] blobToBytes(Blob blob) {
		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream(blob.getBinaryStream());
			byte[] bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
			return bytes;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				return null;
			}
		}
	}
    
}
