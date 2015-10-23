package com.johnny.util;

public class AreaUtil {
	
	public static String getDepartmentcode(String vcdepartmentcode,String numdepartmenttype){
		String NewDepartmentcode="";
		if(numdepartmenttype.equals("1")){//总队
			NewDepartmentcode=vcdepartmentcode.substring(0, 2);
		}else if(numdepartmenttype.equals("2")){//支队
			NewDepartmentcode=vcdepartmentcode.substring(0, 4);
		}else if(numdepartmenttype.equals("3")){//大队
			NewDepartmentcode=vcdepartmentcode.substring(0, 6);
		}else if(numdepartmenttype.equals("4")){//派出所
			NewDepartmentcode=vcdepartmentcode;
		}else{
			NewDepartmentcode="";
		}
		//全部就返回空格
		return NewDepartmentcode;
	}
	
	public static int getDepartmentAreaLen(String numdepartmenttype){
		int len = 6;
		numdepartmenttype = numdepartmenttype==null?"":numdepartmenttype;
		if(numdepartmenttype.equals("")||numdepartmenttype.equals("0")){//选择全部地区时，显示的地区都是省
			len = 2;
		}else if(numdepartmenttype.equals("1")){//选择具体省时，显示的地区都是市
			len = 4;
		}else{//选择市、大队显示的地区都是大队
			len = 6;
		}
		return len;
	}
}
