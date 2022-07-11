/**
 * 
 */
package com.cloud.cang.zookeeper.confclient.listener;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存数据处理,所有的监听器需要继承该类
 * 该类会处理通过结果，并回写到zookeeper节点
 * @author zhouhong
 *
 */
public abstract class AbsConfigurationHandler implements ConfigurationListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private CuratorFramework zkClient;
	
	/* (non-Javadoc)
	 * @see ConfigurationListener#configurationChangedNotice(java.lang.String, java.lang.String)
	 */
	@Override
	public void configurationChangedNotice(String keyName, String value) {
		boolean suc=configurationHandler(keyName, value);
		logger.info("节点通过处理完成：{},结果{}",keyName,suc);
		
	}

	/**
	 * 子方法实现，并返回告之处理结果
	 * @param keyName
	 * @param value
	 * @return
	 */
	public abstract boolean configurationHandler(String keyName, String value);

	public CuratorFramework getZkClient() {
		return zkClient;
	}

	public void setZkClient(CuratorFramework zkClient) {
		this.zkClient = zkClient;
	}
}
