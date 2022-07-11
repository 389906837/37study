package com.cloud.cang.dispatcher.server.manager;

import java.io.Closeable;
import java.util.List;

import com.cloud.cang.dispatcher.server.Server;

public interface ServerManager extends Closeable{
	
	
    /**
     * @throws Exception errors
     */
    void start() throws Exception;
    
	/**
	 * 注册server
	 * @param server
	 * @throws Exception
	 */
    void registerServer(Server server) throws Exception;

	/**
	 * 更新server
	 * @param server
	 * @throws Exception
	 */
    void updateServer(Server server) throws Exception;

	/**
	 * 获取当前注册的server
	 * @return
	 */
    Server getRegisterServer();
	
	List<Server> getAllServers();
	
	Server getServerByName(String name);
	
}
