package com.cloud.cang.dispatcher.serializer;

import com.cloud.cang.dispatcher.services.ServiceInstance;

public interface InstanceSerializer
{
    byte[]             serialize(ServiceInstance instance) throws Exception;

    ServiceInstance deserialize(byte[] bytes) throws Exception;
}
