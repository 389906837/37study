package com.cloud.cang.dispatcher.services.manager;

import java.util.List;

import com.cloud.cang.dispatcher.services.ServiceInstance;

/**
 * Provides a set of available instances for a service so that a strategy can pick one of them
 */
public interface InstanceProvider
{
    List<ServiceInstance> getInstances() throws Exception;
}
