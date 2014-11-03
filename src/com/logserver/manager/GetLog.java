package com.logserver.manager;
import com.alibaba.fastjson.JSON;
import com.logserver.model.BaseEntity;
import com.logserver.model.RegistLog;
import com.logserver.model.ItemLog;
import com.logserver.model.LoginLog;
import com.logserver.model.ShuXingLog;
import com.logserver.model.PlayerLog;
import com.logserver.model.JibenxingweiLog;
import com.logserver.model.PayLog;
//由BuildTableNames.java自动生成
public class GetLog {
	public BaseEntity getLogEntity(String jsonData,int type){
		BaseEntity log = null;
		switch(type){
			case 1 :
			log = (RegistLog)JSON.parseObject(jsonData, RegistLog.class);
			break;
			case 2 :
			log = (ItemLog)JSON.parseObject(jsonData, ItemLog.class);
			break;
			case 3 :
			log = (LoginLog)JSON.parseObject(jsonData, LoginLog.class);
			break;
			case 4 :
			log = (ShuXingLog)JSON.parseObject(jsonData, ShuXingLog.class);
			break;
			case 5 :
			log = (PlayerLog)JSON.parseObject(jsonData, PlayerLog.class);
			break;
			case 6 :
			log = (JibenxingweiLog)JSON.parseObject(jsonData, JibenxingweiLog.class);
			break;
			case 7 :
			log = (PayLog)JSON.parseObject(jsonData, PayLog.class);
			break;
		}
		return log;
	}
}