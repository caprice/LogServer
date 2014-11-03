package com.logserver.timertask;

import java.util.concurrent.TimeUnit;

import com.logserver.manager.TableManager;
import com.logserver.timertask.task.CreateTableTask;
import com.logserver.utils.DateUtils;


public class TimerTaskManager {

	//服务器启动时创建数据库表
	public void createTable(){
		ScheduleManager scheduleManager = ScheduleManager.getInstance();
		//服务器启动的时候，先检查要创建的表，没有的话创建。
		TableManager tableManager = new TableManager();
		tableManager.createTable();
		//添加以后每天创建表的定时器
		long oneday =24*60*60L + 5;//注意：这里单位是：秒
		long delay = (DateUtils.getTodayZero(1) - System.currentTimeMillis()) / 1000 + 5;
		//oneday = 10;
		 //delay = 10;
		scheduleManager.addSchedule(new CreateTableTask(), delay,oneday,TimeUnit.SECONDS);
		
	}
}
