package com.cloud.cang.dispatcher.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.cloud.cang.dispatcher.utils.DispatcherUtils;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Server {

	private String ipAddress;
	
	private int port;
	
	private String intallDir;//安装目录
	
	private String version;//版本
	
	private long lastRegisterTime;//最近注册时间
	
	private long startupTime;//启动时间
	
	private String module;//所属模块
	
	@JsonIgnore
	private String zkPath;
	
	public Server(){
		intallDir = DispatcherUtils.getIntallDir();
		version = DispatcherUtils.getCurrentVersion();//版本
		lastRegisterTime = System.currentTimeMillis();//最近注册时间
		startupTime = System.currentTimeMillis();//启动时间
		port = DispatcherUtils.getPort();
		
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIntallDir() {
		return intallDir;
	}

	public void setIntallDir(String intallDir) {
		this.intallDir = intallDir;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getLastRegisterTime() {
		return lastRegisterTime;
	}

	public void setLastRegisterTime(long lastRegisterTime) {
		this.lastRegisterTime = lastRegisterTime;
	}

	public long getStartupTime() {
		return startupTime;
	}

	public void setStartupTime(long startupTime) {
		this.startupTime = startupTime;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getZkPath() {
		return zkPath;
	}

	public void setZkPath(String zkPath) {
		this.zkPath = zkPath;
	}

	@JsonIgnore
	public String getName() {
		return this.ipAddress + ":" + this.port;
	}

}
