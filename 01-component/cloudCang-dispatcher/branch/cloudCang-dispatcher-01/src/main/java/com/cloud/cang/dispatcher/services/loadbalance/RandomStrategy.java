package com.cloud.cang.dispatcher.services.loadbalance;

import java.util.List;
import java.util.Random;

import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.manager.InstanceProvider;

/**
 * 随机策略
 */
public class RandomStrategy implements ProviderStrategy
{
    private final Random            random = new Random();

    @Override
    public ServiceInstance getInstance(InstanceProvider instanceProvider, String localIp, String method, Object[] args) throws Exception
    {
        List<ServiceInstance>    instances = instanceProvider.getInstances();
        if ( instances.size() == 0 )
        {
            return null;
        }
        int                         thisIndex = random.nextInt(instances.size());
        return instances.get(thisIndex);
    }
}
