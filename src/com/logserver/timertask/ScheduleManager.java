package com.logserver.timertask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleManager {

	private ScheduledExecutorService service=Executors.newScheduledThreadPool(5);
	private static ScheduleManager scheduleManager = new ScheduleManager();
	
	private ScheduleManager(){}
	public static ScheduleManager getInstance(){
		return scheduleManager;
	}
	/**
	 * 
	 * <p>Title: addSchedule</p>
	 * <p>Description: 添加定时任务</p>
	 * @param runnable 		要处理任务的线程
	 * @param period		 任务定时间隔执行时间，就是每隔多长时间执行一次任务
	 * @param delay 		任务的首次延迟时间，过了这个时间之后，任务开始按period定时执行
	 * @param unit			时间的格式，毫秒，毫，分钟....
	 * @author guangshuai.wang
	 */
	public void addSchedule(Runnable runnable,long delay,long period,TimeUnit unit){
		service.scheduleAtFixedRate(runnable, delay, period, unit);
	}
}
