package com.logserver.model;
//由BuildTableNames.java自动生成
public class BaseEntity {
	//ID
	public long id;
	//球队id
	public int teamid;
	//用户id
	public String userid;
	//球队名
	public String teamName;
	//创建时间
	public String createTime;
	//大区ID
	public String zoneid;
	//服务器id
	public String serverid;
	//日志类型
	public int type;
	//存储表的名字
	public String tableName;
	public BaseEntity() {}
	public BaseEntity(long id, int teamid, String userid, String teamName, String createTime, String zoneid, String serverid, int type ) {
		this.id=id;
		this.teamid=teamid;
		this.userid=userid;
		this.teamName=teamName;
		this.createTime=createTime;
		this.zoneid=zoneid;
		this.serverid=serverid;
		this.type=type;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return this.id;
	}
	public void setTeamid(int teamid) {
		this.teamid = teamid;
	}
	public int getTeamid() {
		return this.teamid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserid() {
		return this.userid;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamName() {
		return this.teamName;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	public void setZoneid(String zoneid) {
		this.zoneid = zoneid;
	}
	public String getZoneid() {
		return this.zoneid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getServerid() {
		return this.serverid;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return this.type;
	}
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public String getTableName(){
		return tableName;
	}
}