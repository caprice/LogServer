package com.logserver.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.logserver.model.common.ServerConfigModel;

public class InitConfig {

	private static ServerConfigModel serverCofig = null;
	public static ServerConfigModel getInstance(){
		if(serverCofig == null){
			serverCofig = init();
		}
		return serverCofig;
	}
	private static  ServerConfigModel init(){
		InputStream in = null;
		ServerConfigModel config = new ServerConfigModel();
		try{
			in = new BufferedInputStream(new FileInputStream("config/server_config.properties"));
			Properties pro = new Properties();
			pro.load(in);
			config.setServer_max_buffer(Integer.parseInt(pro.getProperty("server_max_buffer")));
			config.setServer_port(Integer.parseInt(pro.getProperty("server_port")));
			config.setServer_readidle_time(Integer.parseInt(pro.getProperty("server_readidle_time")));
			config.setServer_timeout(Integer.parseInt(pro.getProperty("server_timeout")));
			config.setServer_writeidle_time(Integer.parseInt(pro.getProperty("server_writeidle_time")));
			config.setFrom(pro.getProperty("from"));
			config.setTo(pro.getProperty("to"));
			config.setUsername(pro.getProperty("username"));
			config.setPassword(pro.getProperty("password"));
			config.setGame(pro.getProperty("game"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
	
}
