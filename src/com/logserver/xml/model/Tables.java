package com.logserver.xml.model;

import java.util.List;

public class Tables {

	public String tableName;
	public List<TableConfigInfo> list;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<TableConfigInfo> getList() {
		return list;
	}
	public void setList(List<TableConfigInfo> list) {
		this.list = list;
	}
	
	
}
