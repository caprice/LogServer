package com.logserver.model;
//由BuildTableNames.java自动生成
public class LoginLog extends BaseEntity {
	//注册ip地址
	public String ip;
	public LoginLog() {}
	public LoginLog(long id,int teamid,String userid,String teamName,String createTime,String zoneid,String serverid,int type,String ip){
		super(id,teamid,userid,teamName,createTime,zoneid,serverid,type);
		this.ip = ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	public String getIp(){
		return this.ip;
	}
}