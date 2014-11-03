package com.logserver.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author guangshuai.wang
 * <p>Description: 一些常量信息的配置</p>
 */
public class OrderConstance {
	//心跳消息号
	public final static int HeartBeat = 10000;
	public final static Map<Integer, String> tableNamesMap = new HashMap<Integer, String>();
	public final static Map<Integer,Object> dispatchMap = new HashMap<Integer, Object>();
	static {
		//消息分发处理
		//dispatchMap.put(1, new StartLogProcess());
	}
	
	
}
