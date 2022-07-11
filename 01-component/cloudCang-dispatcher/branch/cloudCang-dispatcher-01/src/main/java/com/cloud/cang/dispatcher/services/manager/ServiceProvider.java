package com.cloud.cang.dispatcher.services.manager;

import java.io.Closeable;
import java.util.Collection;

import com.cloud.cang.dispatcher.services.ServiceInstance;

public interface ServiceProvider extends Closeable
{

	void start() throws Exception;

    
    /**
     * 根据方法名参数，获取策略，返回的一个服务实例
     * @param args
     * @return
     * @throws Exception
     */
    ServiceInstance getInstance(Object[] args) throws Exception;

    /**
     * 返回此提供此服务的所有提供者
     * @return
     * @throws Exception
     */
    Collection<ServiceInstance> getAllInstances() throws Exception;

    /**
     * 备注此服务实例不可用
     * @param instance
     */
    void noteError(ServiceInstance instance);
    
    /**
     * 服务名称
     * @return
     */
    String getServiceName();
    
}
