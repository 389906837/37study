package com.cloud.cang.dispatcher.services.manager.impl;

import java.util.List;

import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.filters.InstanceFilter;
import com.cloud.cang.dispatcher.services.manager.InstanceProvider;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

class FilteredInstanceProvider implements InstanceProvider
{
    private final InstanceProvider instanceProvider;
    private final Predicate<ServiceInstance> predicates;

    FilteredInstanceProvider(InstanceProvider instanceProvider, List<InstanceFilter> filters)
    {
        this.instanceProvider = instanceProvider;
        predicates = Predicates.and(filters);
    }

    @Override
    public List<ServiceInstance> getInstances() throws Exception
    {
        Iterable<ServiceInstance> filtered = Iterables.filter(instanceProvider.getInstances(), predicates);
        return ImmutableList.copyOf(filtered);
    }
}
