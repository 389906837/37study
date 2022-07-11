package com.cloud.cang.dispatcher.services.filters;

import java.util.concurrent.TimeUnit;

/**
 * Abstraction for values that determine when an instance is down
 */
public class DownInstancePolicy
{
    private final long timeoutMs;
    private final int errorThreshold;

    private static final long DEFAULT_TIMEOUT_MS = 30000;
    private static final int DEFAULT_THRESHOLD = 2;

    /**
     * Policy with default values
     */
    public DownInstancePolicy()
    {
        this(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS, DEFAULT_THRESHOLD);
    }

    /**
     * @param timeout window of time for down instances
     * @param unit time unit
     * @param errorThreshold number of errors within time window that denotes a down instance
     */
    public DownInstancePolicy(long timeout, TimeUnit unit, int errorThreshold)
    {
        this.timeoutMs = unit.toMillis(timeout);
        this.errorThreshold = errorThreshold;
    }

    public long getTimeoutMs()
    {
        return timeoutMs;
    }

    public int getErrorThreshold()
    {
        return errorThreshold;
    }
}
