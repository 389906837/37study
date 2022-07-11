/**        
 */    
package com.cloud.cang.zookeeper.confclient.listener;

/**
 * 实现对zookeeper节点的监听，并回调xml文件中配置方法
 */
public interface ConfigurationListener {

	/**
	 * 当指定的zookeeper这个节点有事件触发时，调用方法
	 * @param keyName
	 */
	void configurationChangedNotice(String keyName,String value);
}
