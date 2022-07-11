package com.cloud.cang.dispatcher.server.manager;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

import com.cloud.cang.dispatcher.server.Server;

public interface ServerCache extends Closeable{
	
	/**
	 * 返回所有server
	 * 
	 * @return the list
	 */
    List<Server> getAllServers();

	/**
	 * 使用前调用start
	 * 
	 * @throws Exception
	 *             errors
	 */
    void start() throws Exception;
	
	
	Map<String, Server> getServerCache();
}
