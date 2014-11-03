package com.logserver.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

import com.logserver.model.common.ServerConfigModel;
import com.logserver.timertask.TimerTaskManager;
import com.logserver.utils.InitConfig;
import com.logserver.utils.LogManagerUtil;
import com.logserver.utils.MailUtils;

public class ServerConfig {
	/**
	 * 
	 * <p>Title: start</p>
	 * <p>Description: </p>
	 * @author guangshuai.wang
	 */
	public void start(){
		
		 NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
	        acceptor.setHandler(new MessageHandler());
	         Executor threadPool = Executors.newFixedThreadPool(1500);
	        acceptor.getFilterChain().addLast("exector", new ExecutorFilter(threadPool));
	       // acceptor.getFilterChain().addLast("logger", new LoggingFilter());
	        DatagramSessionConfig dcfg = acceptor.getSessionConfig();
	        acceptor.getFilterChain().addLast(
	        		      "codec", 
	        		      new ProtocolCodecFilter(new MyCodeFactory(Charset.forName( "UTF-8" ))));
	       
	        ServerConfigModel configMode = InitConfig.getInstance();
	        if(configMode == null){
	        	LogManagerUtil.LogErr("服务器配置为空");
	        	return ;
	        }
	        dcfg.setReadBufferSize(configMode.getServer_max_buffer());
	        dcfg.setReceiveBufferSize(configMode.getServer_max_buffer());
	        dcfg.setSendBufferSize(configMode.getServer_max_buffer());
	        dcfg.setReaderIdleTime(configMode.getServer_readidle_time());
	        dcfg.setWriterIdleTime(configMode.getServer_writeidle_time());
	        dcfg.setWriteTimeout(configMode.getServer_timeout());
	        dcfg.setReuseAddress(true);//
	        try {
				acceptor.bind(new InetSocketAddress(configMode.getServer_port()));
				TimerTaskManager timerManager = new TimerTaskManager();
				timerManager.createTable();
	        } catch (IOException e) {
				//e.printStackTrace();
	        	LogManagerUtil.LogErr("日志服务器启动时错误:" + e.getMessage() + "\n" + e.getCause());
	        	MailUtils.send("日志服务器启动时错误", e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
			}//
	}
}
