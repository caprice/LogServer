package com.logserver.main;

import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;

import com.logserver.handler.ServerConfig;
import com.logserver.manager.DataQueueManager;
import com.logserver.utils.MailUtils;

public class LogServerRun {

	public static void main(String[] args) {
		//BuildTableNames build = new BuildTableNames();
		//build.build();
		try{
		ServerConfig config = new ServerConfig();      
		config.start();
		System.out.println("日志服务器启动成功\n版本号：1.1.8");
		DataQueueManager.getInstance().insertToDB();
		MailUtils.send("日志服务器启动成功", DateUtil.formatDate(new Date(), "yyyy-mm-dd hh:ss:mm") +"本次米润世纪日志服务器启动成功");
		}catch (Exception e) {
			// TODO: handle exception
			MailUtils.send("服务器启动错误", DateUtil.formatDate(new Date(),"yyyy-mm-dd hh:ss:mm") + "\n" + e.getMessage() + "\n" + e.getCause());
		}
	}
}
