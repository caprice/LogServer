package com.logserver.utils;

public class StringUtils {

	/**
	 * 
	 * <p>Title: isNumber</p>
	 * <p>Description:判断字符串是否为整数 </p>
	 * @param str
	 * @return
	 * @author guangshuai.wang
	 */
	public static boolean isNumber(String str){
		for(int i = 0; i < str.length(); ++i){
			if(!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	/**
	 * 
	 * <p>Title: getClassType</p>
	 * <p>Description: 判断并返回基本类型</p>
	 * @param str
	 * @return
	 * @author guangshuai.wang
	 */
	public static Class<?> getClassType(String str){
		if(StringUtils.isNumber(str)){
			return int.class;
		} else {
			return String.class;
		}
	}
	/**
	 * 
	 * <p>Title: firstToUpper</p>
	 * <p>Description:把字符串转化为首字母大写的字符串 </p>
	 * @param str
	 * @return
	 * @author guangshuai.wang
	 */
	public static String firstToUpper(String str){
		return str.replaceFirst(str.substring(0, 1), str.substring(0,1).toUpperCase());
	}
}
