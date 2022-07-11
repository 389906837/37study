package com.cloud.cang.dispatcher.services.manager.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

import com.cloud.cang.dispatcher.services.ServiceConfig;
import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.loadbalance.ProviderStrategy;
import com.cloud.cang.dispatcher.services.manager.ServiceCache;
import com.cloud.cang.dispatcher.services.manager.ServiceCacheListener;
import com.cloud.cang.dispatcher.utils.JsonSerializerUtils;
import com.cloud.cang.dispatcher.services.loadbalance.StrategyRule;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.listen.ListenerContainer;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ServiceCacheImpl implements ServiceCache, PathChildrenCacheListener
{
	
	public final static Logger LOGGER = LoggerFactory.getLogger(ServiceCacheImpl.class);
	
    private final ListenerContainer<ServiceCacheListener>           listenerContainer = new ListenerContainer<ServiceCacheListener>();
    private final ServiceDiscoveryImpl                           discovery;
    private final AtomicReference<State>                            state = new AtomicReference<State>(State.LATENT);
    private final PathChildrenCache                                 cache;
    private NodeCache                                         nodeCache;
    private final String nodePath;
    private final ConcurrentMap<String, ServiceInstance>         instances = Maps.newConcurrentMap();
    private final String name;
    private Map<String, StrategyRule> strategyRule = new HashMap<String, StrategyRule>();

    private enum State
    {
        LATENT,
        STARTED,
        STOPPED
    }

    ServiceCacheImpl(ServiceDiscoveryImpl discovery, String name)
    {
        Preconditions.checkNotNull(discovery, "discovery cannot be null");
        Preconditions.checkNotNull(name, "name cannot be null");

        this.discovery = discovery;
        this.name = name;
        nodePath = discovery.pathForName(name);
        cache = new PathChildrenCache(discovery.getClient(), discovery.pathForName(name), true);
        cache.getListenable().addListener(this);
        nodeInit();
    }
    
    private void nodeInit() {
        nodeCache = new NodeCache(discovery.getClient(), nodePath);
        try {
            nodeCache.start(true);
        } catch (Exception e) {
           LOGGER.error("Service Cache Error:{}", e);
        }
        if (nodeCache.getCurrentData() != null) {
            try {
                parserRoute(new String(nodeCache.getCurrentData().getData()));
            } catch (IOException e) {
                LOGGER.error("Service parserRoute Error:{}", e);
            }
        }
        NodeCacheListener listener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                if (nodeCache.getCurrentData() != null) {
                    parserRoute(new String(nodeCache.getCurrentData().getData()));
                }
            }
        };
        nodeCache.getListenable().addListener(listener);
    }

    @Override
    public List<ServiceInstance> getInstances()
    {
        return Lists.newArrayList(instances.values());
    }

    @Override
    public void start() throws Exception
    {
        Preconditions.checkState(state.compareAndSet(State.LATENT, State.STARTED), "Cannot be started more than once");

        cache.start(true);
        for ( ChildData childData : cache.getCurrentData() )
        {
            addInstance(childData, true);
        }
        discovery.cacheOpened(this);
    }

    @Override
    public void close() throws IOException
    {
        Preconditions.checkState(state.compareAndSet(State.STARTED, State.STOPPED), "Already closed or has not been started");

        listenerContainer.forEach
            (
                new Function<ServiceCacheListener, Void>()
                {
                    @Override
                    public Void apply(ServiceCacheListener listener)
                    {
                        discovery.getClient().getConnectionStateListenable().removeListener(listener);
                        return null;
                    }
                }
            );
        listenerContainer.clear();
        CloseableUtils.closeQuietly(cache);
        CloseableUtils.closeQuietly(nodeCache);
        
        discovery.cacheClosed(this);
    }

    @Override
    public void addListener(ServiceCacheListener listener)
    {
        listenerContainer.addListener(listener);
        discovery.getClient().getConnectionStateListenable().addListener(listener);
    }

    @Override
    public void addListener(ServiceCacheListener listener, Executor executor)
    {
        listenerContainer.addListener(listener, executor);
        discovery.getClient().getConnectionStateListenable().addListener(listener, executor);
    }

    @Override
    public void removeListener(ServiceCacheListener listener)
    {
        listenerContainer.removeListener(listener);
        discovery.getClient().getConnectionStateListenable().removeListener(listener);
    }

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception
    {
        boolean         notifyListeners = false;
        switch ( event.getType() )
        {
            case CHILD_ADDED:
            case CHILD_UPDATED:
            {
                addInstance(event.getData(), false);
                notifyListeners = true;
                break;
            }

            case CHILD_REMOVED:
            {
                instances.remove(instanceIdFromData(event.getData()));
                notifyListeners = true;
                break;
            }
        }

        if ( notifyListeners )
        {
            listenerContainer.forEach
            (
                new Function<ServiceCacheListener, Void>()
                {
                    @Override
                    public Void apply(ServiceCacheListener listener)
                    {
                        listener.cacheChanged();
                        return null;
                    }
                }
            );
        }
    }

    private String instanceIdFromData(ChildData childData)
    {
        return ZKPaths.getNodeFromPath(childData.getPath());
    }

    private void addInstance(ChildData childData, boolean onlyIfAbsent) throws Exception
    {
        String                  instanceId = instanceIdFromData(childData);
        ServiceInstance     serviceInstance = discovery.getSerializer().deserialize(childData.getData());
        if ( onlyIfAbsent )
        {
            instances.putIfAbsent(instanceId, serviceInstance);
        }
        else
        {
            instances.put(instanceId, serviceInstance);
        }
        cache.clearDataBytes(childData.getPath(), childData.getStat().getVersion());
    }

	@Override
	public ProviderStrategy getStrategy(String serviceName) {
        if (strategyRule.containsKey(serviceName)) {
            return strategyRule.get(serviceName).getStrategy();
        } else {
            StrategyRule rule = new StrategyRule();
            strategyRule.put(serviceName, rule);
            return rule.getStrategy();
        }
    
	}
	
	private void parserRoute(String configJosn) throws IOException {
		//Json : {"strategyRule":{"strategyName":"hash"}}
		//       {"strategyRule":{"strategyName":"ip","remoteIp":"192.168.2.181","remotePort":"7070"}}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("解析到Zookeeper调用配置：{}", configJosn);
			}
	        Map<String, StrategyRule> localStrategyRule = new HashMap<String, StrategyRule>();
	        if (StringUtils.isNotBlank(configJosn) && configJosn.length() >10){
	            try {
	            	ServiceConfig serviceConfig = JsonSerializerUtils.deserialize(configJosn, ServiceConfig.class);
	                StrategyRule route = serviceConfig.getStrategyRule();
	                route.setServiceName(name);
	                if (route != null) {
	                	 localStrategyRule.put(name, route);
	                }
	                
	            } catch (Exception e) {
	               LOGGER.error("Service parserRoute ERROR:{}",e);
	            }
	        }
	        strategyRule = localStrategyRule;
	    }
}
