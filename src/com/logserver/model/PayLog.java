package com.logserver.model;
//由BuildTableNames.java自动生成
public class PayLog extends BaseEntity {
	//充值RMB
	public int ingot;
	//vip等级
	public int vipLv;
	//事件原因
	public String reason;
	public PayLog() {}
	public PayLog(long id,int teamid,String userid,String teamName,String createTime,String zoneid,String serverid,int type,int ingot,int vipLv,String reason){
		super(id,teamid,userid,teamName,createTime,zoneid,serverid,type);
		this.ingot = ingot;
		this.vipLv = vipLv;
		this.reason = reason;
	}
	public void setIngot(int ingot){
		this.ingot = ingot;
	}
	public int getIngot(){
		return this.ingot;
	}
	public void setVipLv(int vipLv){
		this.vipLv = vipLv;
	}
	public int getVipLv(){
		return this.vipLv;
	}
	public void setReason(String reason){
		this.reason = reason;
	}
	public String getReason(){
		return this.reason;
	}
}