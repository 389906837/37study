package com.cloud.cang.dispatcher.services.filters;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.google.common.collect.Maps;

public class DownInstanceManager implements InstanceFilter
{
    private final ConcurrentMap<ServiceInstance, Status> statuses = Maps.newConcurrentMap();
    private final DownInstancePolicy downInstancePolicy;
    private final AtomicLong lastPurge = new AtomicLong(System.currentTimeMillis());

    private static class Status
    {
        private final long startMs = System.currentTimeMillis();
        private final AtomicInteger errorCount = new AtomicInteger(0);
    }

    public DownInstanceManager(DownInstancePolicy downInstancePolicy)
    {
        this.downInstancePolicy = downInstancePolicy;
    }

    public void add(ServiceInstance instance)
    {
        purge();

        Status newStatus = new Status();
        Status oldStatus = statuses.putIfAbsent(instance, newStatus);
        Status useStatus = (oldStatus != null) ? oldStatus : newStatus;
        useStatus.errorCount.incrementAndGet();
    }

    @Override
    public boolean apply(ServiceInstance instance)
    {
        purge();

        Status status = statuses.get(instance);
        return (status == null) || (status.errorCount.get() < downInstancePolicy.getErrorThreshold());
    }

    private void purge()
    {
        long localLastPurge = lastPurge.get();
        long ticksSinceLastPurge = System.currentTimeMillis() - localLastPurge;
        if ( ticksSinceLastPurge < (downInstancePolicy.getTimeoutMs() / 2) )
        {
            return;
        }

        if ( !lastPurge.compareAndSet(localLastPurge, System.currentTimeMillis()) )
        {
            return;
        }

        Iterator<Entry<ServiceInstance, Status>> it = statuses.entrySet().iterator();
        while ( it.hasNext() )
        {
            Entry<ServiceInstance, Status> entry = it.next();
            long elapsedMs = System.currentTimeMillis() - entry.getValue().startMs;
            if ( elapsedMs >= downInstancePolicy.getTimeoutMs() )
            {
                it.remove();
            }
        }
    }
}
