package com.logserver.thread;

import java.util.concurrent.Callable;

import com.logserver.manager.TableManager;
import com.logserver.utils.LogManagerUtil;

public class InsertDBCallable implements Callable<Integer>{

	private String data;
	private TableManager tableManager;
	public InsertDBCallable(String data,TableManager tableManager){
		this.data = data;
		this.tableManager = tableManager;
	}
	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		if (data != null) {
			try{
			String[] msgs = data.split("#");
	
				tableManager.insertLog(msgs[1], Integer
						.parseInt(msgs[0]));
			}catch (Exception e) {
				LogManagerUtil.LogErr("写入数据库线程异常：" + e.getMessage() + "," + e.getCause());
				
				//e.printStackTrace();
			}
			
		}
		return 0;
	}

}
