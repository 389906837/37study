package com.cloud.cang.dispatcher.services.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.filters.EnabledServiceFilter;
import com.cloud.cang.dispatcher.services.filters.InstanceFilter;
import com.cloud.cang.dispatcher.services.loadbalance.ProviderStrategy;
import com.cloud.cang.dispatcher.services.manager.ServiceCache;
import com.cloud.cang.dispatcher.services.filters.DownInstanceManager;
import com.cloud.cang.dispatcher.services.filters.DownInstancePolicy;
import com.cloud.cang.dispatcher.services.manager.InstanceProvider;
import com.cloud.cang.dispatcher.services.manager.ServiceProvider;
import com.google.common.collect.Lists;

public class ServiceProviderImpl implements ServiceProvider {
	private final String serviceName;
	private final ServiceCache cache;
	private final InstanceProvider instanceProvider;
	private final ServiceDiscoveryImpl discovery;
	private final DownInstanceManager downInstanceManager;
	private final EnabledServiceFilter enabledServiceFilter;

	public ServiceProviderImpl(ServiceDiscoveryImpl discovery,
			String serviceName) {
		this(discovery, serviceName, new ArrayList<InstanceFilter>());
	}

	public ServiceProviderImpl(ServiceDiscoveryImpl discovery,
			String serviceName, ProviderStrategy providerStrategy) {
		this(discovery, serviceName, 
				new ArrayList<InstanceFilter>(), new DownInstancePolicy());
	}

	public ServiceProviderImpl(ServiceDiscoveryImpl discovery,
			String serviceName, List<InstanceFilter> filters) {
		this(discovery, serviceName, filters,
				new DownInstancePolicy());
	}


	public ServiceProviderImpl(ServiceDiscoveryImpl discovery,
			String serviceName, List<InstanceFilter> filters, DownInstancePolicy downInstancePolicy) {
		
		this.serviceName = serviceName;
		this.discovery = discovery;

		enabledServiceFilter = new EnabledServiceFilter(discovery);
		downInstanceManager = new DownInstanceManager(downInstancePolicy);
		cache = new ServiceCacheImpl(discovery, serviceName);

		ArrayList<InstanceFilter> localFilters = Lists.newArrayList(filters);
		localFilters.add(downInstanceManager);
		localFilters.add(enabledServiceFilter);
		instanceProvider = new FilteredInstanceProvider(cache, localFilters);
	}

	@Override
	public void start() throws Exception {
		cache.start();
		discovery.providerOpened(this);
	}

	@Override
	public void close() throws IOException {
		discovery.providerClosed(this);
		cache.close();
	}

	/**
	 * 返回所有当前可用的服务实例
	 * 
	 * @return all known instances
	 * @throws Exception
	 *             any errors
	 */
	@Override
	public Collection<ServiceInstance> getAllInstances() throws Exception {
		return instanceProvider.getInstances();
	}

	
	/**
	 * 应用路由及负载均衡策略后返回一个实例。
	 * 
	 * @return the instance to use
	 * @throws Exception
	 *             any errors
	 */
	@Override
	public ServiceInstance getInstance(Object[] args) throws Exception{
	        String localIp=discovery.getServerManager().getRegisterServer().getIpAddress();
	       return cache.getStrategy(serviceName).getInstance(instanceProvider, localIp, serviceName, args);
	   }

	@Override
	public void noteError(ServiceInstance instance) {
		downInstanceManager.add(instance);
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}
}
