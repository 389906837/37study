package com.cloud.cang.dispatcher.services.manager;

import java.io.Closeable;
import java.util.List;

import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.loadbalance.ProviderStrategy;
import org.apache.curator.framework.listen.Listenable;

public interface ServiceCache extends Closeable,
		Listenable<ServiceCacheListener>, InstanceProvider {
	
	/**
	 * 返回所有的服务实例。这里的实例不保证一定是最新的，有1-2秒的窗口延时
	 * 
	 * @return the list
	 */
    List<ServiceInstance> getInstances();
	
	 /**
     * 根据方法名取得方法对应的策略
     * @param methodName
     * @return
     */
     ProviderStrategy getStrategy(String serviceName);

	/**
	 * 使用前调用start
	 * 
	 * @throws Exception
	 *             errors
	 */
    void start() throws Exception;
}
