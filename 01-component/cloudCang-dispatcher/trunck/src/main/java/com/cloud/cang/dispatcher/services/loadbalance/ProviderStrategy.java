package com.cloud.cang.dispatcher.services.loadbalance;

import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.manager.InstanceProvider;

public interface ProviderStrategy
{
    ServiceInstance getInstance(InstanceProvider instanceProvider, String localIp, String method, Object[] args) throws Exception;
}
