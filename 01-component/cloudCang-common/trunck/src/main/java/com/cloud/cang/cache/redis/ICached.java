package com.cloud.cang.cache.redis;

import java.util.Map;
import java.util.Set;


public interface ICached {
	
	/**
	 * 设置值，不设置过期时间
	 * @return
	 */
	void put(String key,Object value)throws Exception;
	
	/**
	 * 设置值，不设置过期时间
	 * @return
	 */
	void put(String key,Object value, final int expireSec)throws Exception;
	
	/**
	 * 设置redis超时时间
	 * @return
	 */
	void expireSec(String key,final int expireSec)throws Exception;
	
	/**
	 * 删除 缓存
	 * @param key
	 * @return
	 * @throws Exception
	 */
	void remove(String key)throws Exception;
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Object get(String key)throws Exception;
	
	/**
	 * 根据 正则表达式key 获取 列表
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Set hgetKeys(String key)throws Exception;
	
	
	
	/**
	 * 更新 缓存
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	void hset(String key,String hkey,Object value)throws Exception;
	

	/**
	 * 获取缓存
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Object hget(String key,String hkey)throws Exception;
	
	
	/**
	 * 删除 缓存
	 * @param key
	 * @param hkey
	 * @return
	 * @throws Exception
	 */
	Long hremove(String key,String ... hkey)throws Exception;
	/**
	 * 获取 map中的所有值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	<T> Set<T> hvalues(String key)throws Exception;
	
	/**
	 * 根据一个key取得对应的map值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> hget(String key) throws Exception;
	
	/**
	 * 调用一个map对象值
	 * @param key
	 * @param vs
	 * @throws Exception
	 */
	void hset(String key,Map<String,Object> vs) throws Exception;
	
	
	
	/**
	 * 自增值，并返回增加后的值
	 * @param key
	 * @param expireSec 数据有效期
	 * @return
	 * @throws Exception
	 */
	Long incrAndGetForExpire(String key,int expireSec) throws Exception ;
	
	/**
	 * 自增值，并返回增加后的值（没有设置失效，数据一直存放于redis）
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Long incrAndGet(String key) throws Exception;
	
	/**
	 * 自减值，并返回减后的值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Long decrAndGet(String key) throws Exception;
	
	/**
	 * 将元素插入redis列表的左端，并返回列表的大小
	 * @param key
	 * @param value
	 * @return
	 */
	long lpush(String key,Object value) throws Exception;
	
	/**
	 * 从redis列表的左端移除元素，并返回列表的大小
	 * @param key
	 * @return
	 */
    Object lpop(String key) throws Exception;
	
	/**
	 * 判断用户是否存在
	 * @param key
	 * @return
	 * @throws Exception
	 */
    boolean exists(String key) throws Exception;
	/**
	 * 取得自增序列的变量值
	 * @param key
	 * @return
	 * @throws Exception
	 */
    Long getIncrOrDecrValue(String key)throws Exception;

	/**
	 * 取得list的大小
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Long llen(String key) throws Exception;

	/**
	 * 原子增加值
	 * @param key
	 * @param val
	 * @return
	 * @throws Exception
	 */
	Long incrByVal(String key, Long val) throws Exception;

	/**
	 * 原子减少值
	 * @param key
	 * @param val
	 * @return
	 * @throws Exception
	 */
	Long decrByVal(String key, Long val) throws Exception;
	
}