package com.logserver.model;
//由BuildTableNames.java自动生成
public class PlayerLog extends BaseEntity {
	//签约或使用的球员id
	public int playerid;
	//球员名
	public String name;
	//是否成功
	public int isOk;
	//事件原因
	public String reason;
	public PlayerLog() {}
	public PlayerLog(long id,int teamid,String userid,String teamName,String createTime,String zoneid,String serverid,int type,int playerid,String name,int isOk,String reason){
		super(id,teamid,userid,teamName,createTime,zoneid,serverid,type);
		this.playerid = playerid;
		this.name = name;
		this.isOk = isOk;
		this.reason = reason;
	}
	public void setPlayerid(int playerid){
		this.playerid = playerid;
	}
	public int getPlayerid(){
		return this.playerid;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setIsOk(int isOk){
		this.isOk = isOk;
	}
	public int getIsOk(){
		return this.isOk;
	}
	public void setReason(String reason){
		this.reason = reason;
	}
	public String getReason(){
		return this.reason;
	}
}