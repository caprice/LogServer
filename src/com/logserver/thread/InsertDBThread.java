package com.logserver.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.logserver.manager.DataQueueManager;
import com.logserver.manager.TableManager;
import com.logserver.utils.LogManagerUtil;

public class InsertDBThread extends Thread {
	Logger log = Logger.getLogger(this.getClass());
	private TableManager tableManager = null;
	ExecutorService executor = Executors.newSingleThreadExecutor();

	public void run() {
		tableManager = new TableManager();
		while (true) {
			try {
				//设置写入数据库的方法，定时，因为线程不抛异常
				String msg = DataQueueManager.getInstance()
				.getData();
			  Future<Integer> future = executor.submit(new InsertDBCallable(msg, tableManager));
				future.get(1, TimeUnit.SECONDS);
			} catch (Exception e) {
				LogManagerUtil.LogErr("写入数据库线程异常：" + e.getMessage() + "," + e.getCause());
				
				//e.printStackTrace();
			}
		}
	}
}
