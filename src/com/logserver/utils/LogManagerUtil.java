package com.logserver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogManagerUtil {
	/**接收的数据入日志**/
	private static Logger datalog = LoggerFactory.getLogger("datalog");
	private static Logger serverinfo = LoggerFactory.getLogger("serverinfo");
	private static Logger logErr = LoggerFactory.getLogger("servererr");
	
	public static void DataLog(String str){
		datalog.info(str);
	}
	/**日志服务器的信息说明**/
	public static void ServerInfo(String str){
		serverinfo.info(str);
	}
	
	public static void LogErr(String str){
		logErr.error( str);
	}
	
}
