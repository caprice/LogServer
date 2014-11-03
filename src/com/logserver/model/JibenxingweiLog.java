package com.logserver.model;
//由BuildTableNames.java自动生成
public class JibenxingweiLog extends BaseEntity {
	//基本行为日志
	public String data;
	//事件原因
	public String reason;
	public JibenxingweiLog() {}
	public JibenxingweiLog(long id,int teamid,String userid,String teamName,String createTime,String zoneid,String serverid,int type,String data,String reason){
		super(id,teamid,userid,teamName,createTime,zoneid,serverid,type);
		this.data = data;
		this.reason = reason;
	}
	public void setData(String data){
		this.data = data;
	}
	public String getData(){
		return this.data;
	}
	public void setReason(String reason){
		this.reason = reason;
	}
	public String getReason(){
		return this.reason;
	}
}