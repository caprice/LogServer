package com.logserver.timertask.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.logserver.manager.TableManager;
import com.logserver.utils.LogManagerUtil;
import com.logserver.utils.MailUtils;

public class CreateTableTask implements Runnable {
	Logger systemLog = Logger.getLogger("systemLog");
	ExecutorService executor = Executors.newSingleThreadExecutor();

	public CreateTableTask() {
		LogManagerUtil.ServerInfo("开始创建表格");
	}

	@Override
	public void run() {
		try {
			Callable<Integer> task = new Callable<Integer>() {
				TableManager tableManager = new TableManager();

				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					tableManager.createTable();
					return 0;
				}
			};
			Future<Integer> future = executor.submit(task);
			future.get(1, TimeUnit.SECONDS);
		} catch (Exception e) {
			LogManagerUtil.LogErr("定时创建表时错误：" + e.getMessage() + ";\n" + e.getCause());
			MailUtils.send("日志服务器定时创建表错误", e.getMessage() + ";\n" + e.getCause() + "\n" + e.getStackTrace());
		}

	}
}
