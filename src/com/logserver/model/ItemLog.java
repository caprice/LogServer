package com.logserver.model;
//由BuildTableNames.java自动生成
public class ItemLog extends BaseEntity {
	//物品ID
	public int itemid;
	//物品数量
	public int number;
	//物品名
	public String name;
	//寄售价格
	public int price;
	//事件原因
	public String reason;
	public ItemLog() {}
	public ItemLog(long id,int teamid,String userid,String teamName,String createTime,String zoneid,String serverid,int type,int itemid,int number,String name,int price,String reason){
		super(id,teamid,userid,teamName,createTime,zoneid,serverid,type);
		this.itemid = itemid;
		this.number = number;
		this.name = name;
		this.price = price;
		this.reason = reason;
	}
	public void setItemid(int itemid){
		this.itemid = itemid;
	}
	public int getItemid(){
		return this.itemid;
	}
	public void setNumber(int number){
		this.number = number;
	}
	public int getNumber(){
		return this.number;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setPrice(int price){
		this.price = price;
	}
	public int getPrice(){
		return this.price;
	}
	public void setReason(String reason){
		this.reason = reason;
	}
	public String getReason(){
		return this.reason;
	}
}