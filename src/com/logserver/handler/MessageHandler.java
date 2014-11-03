package com.logserver.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.logserver.manager.DataQueueManager;
import com.logserver.utils.LogManagerUtil;
import com.logserver.utils.OrderConstance;

public class MessageHandler implements IoHandler{
	//private final static TableManager tableManager = new TableManager();
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
	}

	@Override
	public void messageReceived(IoSession session, Object obj) throws Exception {
		if(obj == null){
			return ;
		}
		String msg = (String)obj;
		
		String[] msgs = msg.split("#");
		if(msgs.length != 2){
			return;
		}
		if(!msgs[0].equals("10000")){
			LogManagerUtil.DataLog(msg);
		}
		int order = Integer.parseInt(msgs[0]);
		//心跳连接
		if(order == OrderConstance.HeartBeat){
			IoBuffer buff = IoBuffer.allocate(16).setAutoExpand(true);
			String str = OrderConstance.HeartBeat + "#\n";
			buff.put(str.getBytes("utf8"));
			buff.flip();
			session.write(buff);
			return ;
		}
		//将数据添加到队列中
		DataQueueManager.getInstance().addData(msg);
		//向日志表中插入数据
		//tableManager.insertLog(msgs[1], Integer.parseInt(msgs[0]));
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		
	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		
	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		arg0.close(true);
	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		
	}

}
