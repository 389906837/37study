package com.cloud.cang.zookeeper;

import com.cloud.cang.zookeeper.confclient.ContextUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 初始化zookeeper客户端
 * @author zhouhong
 *
 */
public class ZookeeperFactoryBean implements FactoryBean<CuratorFramework>,
		InitializingBean, DisposableBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private CuratorFramework zkClient;

	private String projectName;

	// 设置ZK链接串
	public void setZkConnectionString(String zkConnectionString) {
		this.zkConnectionString = zkConnectionString;
	}

	private String zkConnectionString;

	@Override
	public CuratorFramework getObject() {
		return zkClient;
	}

	@Override
	public Class<?> getObjectType() {
		return CuratorFramework.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		zkClient.close();
	}

	// 创建ZK链接
	@Override
	public void afterPropertiesSet() {
		// 1000 是重试间隔时间基数，3 是重试次数
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		zkClient = createWithOptions(zkConnectionString, retryPolicy, 2000,
				10000);
		zkClient.start();
		logger.info("Zookeeper 客户端链接成功");
	}

	/**
	 * 通过自定义参数创建
	 */
	public CuratorFramework createWithOptions(String connectionString,
			RetryPolicy retryPolicy, int connectionTimeoutMs,
			int sessionTimeoutMs) {
		return CuratorFrameworkFactory.builder()
				.connectString(connectionString).retryPolicy(retryPolicy)
				.connectionTimeoutMs(connectionTimeoutMs)
				.sessionTimeoutMs(sessionTimeoutMs).build();
	}


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
		ContextUtils.PROJECT_NAME=projectName;
	}
}