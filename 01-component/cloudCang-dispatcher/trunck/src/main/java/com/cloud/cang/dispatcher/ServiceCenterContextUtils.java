package com.cloud.cang.dispatcher;

import java.util.Map;

import com.google.common.collect.Maps;

public class ServiceCenterContextUtils {
	
	private final static Map<String, RegisterServiceCenter> dispatcherMap = Maps.newConcurrentMap();
	
	private static RegisterServiceCenter defaultDispatcher;
	
	public static void addDispatcher(RegisterServiceCenter dispatcher){
		dispatcherMap.put(dispatcher.getConnectString(), dispatcher);
	}
	
	public static RegisterServiceCenter getDefaultServerCenter(){
		if(defaultDispatcher == null){
			defaultDispatcher = dispatcherMap.values().iterator().next();
		}
		return defaultDispatcher;
	}
	
}
