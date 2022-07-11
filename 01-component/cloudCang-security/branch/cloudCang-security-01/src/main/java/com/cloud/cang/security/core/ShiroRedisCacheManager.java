package com.cloud.cang.security.core;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * 
 * 
 * 类功能描述:  从定义shiro的session管理器
 * @author  zhouhong
 * @version V1.0, 2014-5-7
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {

    private ShiroRedisCache<String, Object> shiroRedisCache;
	@Override
	protected Cache createCache(String cacheName) throws CacheException {
		return shiroRedisCache;
	}
	public ShiroRedisCache<String, Object> getShiroRedisCache() {
		return shiroRedisCache;
	}
	public void setShiroRedisCache(ShiroRedisCache<String, Object> shiroRedisCache) {
		this.shiroRedisCache = shiroRedisCache;
	}
}