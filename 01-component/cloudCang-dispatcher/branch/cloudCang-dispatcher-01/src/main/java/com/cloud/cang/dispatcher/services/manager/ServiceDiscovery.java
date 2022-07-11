package com.cloud.cang.dispatcher.services.manager;

import java.io.Closeable;
import java.util.Collection;

import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.manager.impl.ServiceProviderFactory;

public interface ServiceDiscovery extends Closeable
{
    /**
     * 发现服务前调用
     *
     * @throws Exception errors
     */
    void start() throws Exception;

    /**
     * 注册/重新注册服务实例
     *
     * @param service service to add
     * @throws Exception errors
     */
    void     registerService(ServiceInstance service) throws Exception;

    /**
     * 更新服务实例
     *
     * @param service service to update
     * @throws Exception errors
     */
    void     updateService(ServiceInstance service) throws Exception;

    /**
     * 卸载服务实例
     *
     * @param service the service
     * @throws Exception errors
     */
    void     unregisterService(ServiceInstance service) throws Exception;

    /**
     * 查询所有服务名称
     *
     * @return list of service names
     * @throws Exception errors
     */
    Collection<String> queryForNames() throws Exception;

    /**
     * 根据服务名称查询所有实例
     *
     * @param name name of the service
     * @return list of instances (or an empty list)
     * @throws Exception errors
     */
    Collection<ServiceInstance>  queryForInstances(String name) throws Exception;

    /**
     * 查询某一个服务实例
     *
     * @param name name of the service
     * @param id ID of the instance
     * @return the instance or <code>null</code> if not found
     * @throws Exception errors
     */
    ServiceInstance queryForInstance(String name, String server) throws Exception;
    
    /**
     * @return
     */
    ServiceProviderFactory getServiceProviderFactory();

}
