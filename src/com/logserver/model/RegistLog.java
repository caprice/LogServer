package com.logserver.model;
//由BuildTableNames.java自动生成
public class RegistLog extends BaseEntity {
	//注册ip地址
	public String ip;
	//是否成功:1成功，0失败
	public int isOk;
	public RegistLog() {}
	public RegistLog(long id,int teamid,String userid,String teamName,String createTime,String zoneid,String serverid,int type,String ip,int isOk){
		super(id,teamid,userid,teamName,createTime,zoneid,serverid,type);
		this.ip = ip;
		this.isOk = isOk;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	public String getIp(){
		return this.ip;
	}
	public void setIsOk(int isOk){
		this.isOk = isOk;
	}
	public int getIsOk(){
		return this.isOk;
	}
}