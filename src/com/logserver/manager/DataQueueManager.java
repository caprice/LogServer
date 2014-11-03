package com.logserver.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.logserver.thread.InsertDBThread;
import com.logserver.utils.LogManagerUtil;
import com.logserver.utils.MailUtils;

public class DataQueueManager {
	private  static DataQueueManager dataManager = null;
   //创建一个无限制的队列
	private final static BlockingQueue<String> dataQueue = new LinkedBlockingQueue<String>();
	public static DataQueueManager getInstance(){
		if(dataManager == null){
			synchronized (DataQueueManager.class) {
				dataManager = new DataQueueManager();
			}
		}
		return dataManager;
	}
	/**
	 * 
	 * <p>Title: 国安足球经理(移动)</p>
	 * <p>Description:向队列中添加数据 </p>
	 * @param msg
	 * @author guangshuai.wang
	 */
	public void addData(String msg){
		if(msg != null){
			try {
				if(dataQueue != null){
					if(dataQueue.size() < Integer.MAX_VALUE ){
						dataQueue.put(msg);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				LogManagerUtil.LogErr("放入队列时中断：" + e.getMessage());
				MailUtils.send("日志服务器消息加入队列错误", e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
			}
		}
	}
	/**
	 * 
	 * <p>Title: 国安足球经理(移动)</p>
	 * <p>Description:从队列中取数据 </p>
	 * @return
	 * @author guangshuai.wang
	 */
	public String getData(){
		if(dataQueue != null){
			try {
				return dataQueue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				LogManagerUtil.LogErr("从队列中取数据时错误：" + e.getMessage());
			}
		}
		return null;
	}
	
	public void insertToDB(){
		//开启若干线程，向数据库写入数据
		for(int i = 0;i< 1;i ++){
			InsertDBThread dbThread = new InsertDBThread();
			dbThread.setName("日志入库线程-" + i);
			dbThread.start();
		}
	}
}
