package com.logserver.model;
//由BuildTableNames.java自动生成
public class ShuXingLog extends BaseEntity {
	//增加前
	public long nowValue;
	//增加后
	public long addValue;
	//事件原因
	public String reason;
	public ShuXingLog() {}
	public ShuXingLog(long id,int teamid,String userid,String teamName,String createTime,String zoneid,String serverid,int type,long nowValue,long addValue,String reason){
		super(id,teamid,userid,teamName,createTime,zoneid,serverid,type);
		this.nowValue = nowValue;
		this.addValue = addValue;
		this.reason = reason;
	}
	public void setNowValue(long nowValue){
		this.nowValue = nowValue;
	}
	public long getNowValue(){
		return this.nowValue;
	}
	public void setAddValue(long addValue){
		this.addValue = addValue;
	}
	public long getAddValue(){
		return this.addValue;
	}
	public void setReason(String reason){
		this.reason = reason;
	}
	public String getReason(){
		return this.reason;
	}
}