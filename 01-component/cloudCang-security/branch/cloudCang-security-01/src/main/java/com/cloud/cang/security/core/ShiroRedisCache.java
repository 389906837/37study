package com.cloud.cang.security.core;

import com.cloud.cang.cache.redis.ICached;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

/**
 * 
 * 
 * 类功能描述:  实现shiro的缓存处理
 * @author  zhouhong
 * @version V1.0, 2014-5-7
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {
    
    private static Logger logger = LoggerFactory.getLogger(ShiroRedisCache.class);
    private String name;
    private ICached cached;
    /**
     * 获得byte[]型的key
     * 
     * @param key
     * @return
     */
    private String getKey(K key) {
        if (key instanceof String) {
            return key.toString();
        } else {
            return key.toString();
        }
    }
    
    private String getByteName() {
        return name;
        
    }
    
    @Override
    public V get(K key)
        throws CacheException {
        logger.info("Hash根据key从Redis中获取对象 key [" + key + "]");
        try {
            if (key == null) {
                return null;
            } else {
                V value = (V)cached.hget(getByteName(), getKey(key));
                return value;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
        
    }
    
    @Override
    public V put(K key, V value)
        throws CacheException {
        logger.info("Hash根据key从存储 key [" + key + "]");
        try {
            cached.hset(getByteName(), getKey(key), value);
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
    
    @Override
    public V remove(K key)
        throws CacheException {
        logger.info("Hash从redis中删除["+getByteName()+"] key [" + key + "]");
        try {
            V previous = get(key);
            cached.hremove(getByteName(), getKey(key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
    
    @Override
    public void clear()
        throws CacheException {
        logger.info("从redis中删除所有元素");
        try {
            cached.remove(getByteName());
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
    
    @Override
    public int size() {
        try {
            Long longSize = 0l;
            return longSize.intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
    
    @Override
    public Set<K> keys() {
        try {
           return null;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
    
    @Override
    public Collection<V> values() {
        try {
            return null;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public ICached getCached() {
        return cached;
    }

    public void setCached(ICached cached) {
        this.cached = cached;
    }
    
}