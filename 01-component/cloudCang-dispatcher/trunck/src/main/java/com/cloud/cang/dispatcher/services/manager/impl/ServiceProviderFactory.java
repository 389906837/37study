package com.cloud.cang.dispatcher.services.manager.impl;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.cloud.cang.dispatcher.services.manager.ServiceCache;
import com.cloud.cang.dispatcher.services.manager.ServiceProvider;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ServiceProviderFactory implements Closeable{

	private final Collection<ServiceCache> caches = Sets.newHashSet();

	private final Map<String, ServiceProvider> existProvidersMap = Maps.newConcurrentMap();

	private final ServiceDiscoveryImpl discovery;

	ServiceProviderFactory(ServiceDiscoveryImpl discovery) {
		this.discovery = discovery;
	}

	public ServiceProvider getInstance(String serviceName) throws Exception {
		ServiceProvider provider = existProvidersMap.get(serviceName);
		if (provider == null) {
			provider = buildServiceProvider(serviceName);
		}
		return provider;
	}

	private ServiceProvider buildServiceProvider(String serviceName)
			throws Exception {

		ServiceProvider provider = new ServiceProviderImpl(discovery, serviceName);

		provider.start();
		return provider;
	}

	void cacheOpened(ServiceCache cache) {
		caches.add(cache);
	}

	void cacheClosed(ServiceCache cache) {
		caches.remove(cache);
	}

	void providerOpened(ServiceProvider provider) {
		existProvidersMap.put(provider.getServiceName(), provider);
	}

	void providerClosed(ServiceProvider cache) {
		existProvidersMap.remove(cache.getServiceName());
	}

	@Override
	public void close() throws IOException {
		for ( ServiceCache cache : Lists.newArrayList(caches) )
        {
            CloseableUtils.closeQuietly(cache);
        }
        for ( ServiceProvider provider : Lists.newArrayList(existProvidersMap.values()) )
        {
            CloseableUtils.closeQuietly(provider);
        }
	}

}
