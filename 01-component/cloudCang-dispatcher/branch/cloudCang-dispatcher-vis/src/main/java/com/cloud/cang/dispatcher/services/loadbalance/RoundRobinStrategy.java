package com.cloud.cang.dispatcher.services.loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.manager.InstanceProvider;

/**
 *  轮训策略
 */
public class RoundRobinStrategy<T> implements ProviderStrategy
{
    private final AtomicInteger         index = new AtomicInteger(0);

    @Override
    public ServiceInstance getInstance(InstanceProvider instanceProvider, String localIp, String method, Object[] args) throws Exception
    {
        List<ServiceInstance>    instances = instanceProvider.getInstances();
        if ( instances.size() == 0 )
        {
            return null;
        }
        int                         thisIndex = Math.abs(index.getAndIncrement());
        return instances.get(thisIndex % instances.size());
    }
}
