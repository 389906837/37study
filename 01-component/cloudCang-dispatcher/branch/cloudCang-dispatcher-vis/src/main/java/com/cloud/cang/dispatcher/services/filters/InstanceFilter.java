package com.cloud.cang.dispatcher.services.filters;

import com.google.common.base.Predicate;
import com.cloud.cang.dispatcher.services.ServiceInstance;

/**
 * Typedef for an Instance predicate
 */
public interface InstanceFilter extends Predicate<ServiceInstance>
{
}
