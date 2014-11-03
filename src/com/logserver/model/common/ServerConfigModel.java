package com.logserver.model.common;

public class ServerConfigModel {

	private int server_port;
	private int server_timeout=25;
	private int server_readidle_time=20;
	private int server_writeidle_time=50;
	private int server_max_buffer=1024;
	//游戏名
	private String game;
	/*****邮件内容*****/
	//邮件发送者
	private String from ;
	//邮件接收者
	private String to;
	//登陆邮件的用户名密码
	private String username;
	private String password;
	
	
	
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getServer_port() {
		return server_port;
	}
	public void setServer_port(int serverPort) {
		server_port = serverPort;
	}
	public int getServer_timeout() {
		return server_timeout;
	}
	public void setServer_timeout(int serverTimeout) {
		server_timeout = serverTimeout;
	}
	public int getServer_readidle_time() {
		return server_readidle_time;
	}
	public void setServer_readidle_time(int serverReadidleTime) {
		server_readidle_time = serverReadidleTime;
	}
	public int getServer_writeidle_time() {
		return server_writeidle_time;
	}
	public void setServer_writeidle_time(int serverWriteidleTime) {
		server_writeidle_time = serverWriteidleTime;
	}
	public int getServer_max_buffer() {
		return server_max_buffer;
	}
	public void setServer_max_buffer(int serverMaxBuffer) {
		server_max_buffer = serverMaxBuffer;
	}
	
	
}
