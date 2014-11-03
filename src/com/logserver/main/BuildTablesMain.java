package com.logserver.main;

import com.logserver.xml.BuildTableNames;
/**
 * 
 * @author guangshuai.wang
 * <p>Description: 一键创建日志服务器，根据config/TableNames.xml生成基类，子类，mybatis配置文件和Getlog.java类</p>
 */
public class BuildTablesMain {
	public static void main(String[] args) {
		BuildTableNames build = new BuildTableNames();
		build.build();
	}
}
