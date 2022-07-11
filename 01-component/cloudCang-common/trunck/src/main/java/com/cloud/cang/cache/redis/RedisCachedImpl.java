package com.cloud.cang.cache.redis;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cloud.cang.utils.SerializeUtils;
import redis.clients.jedis.JedisCluster;


/**
 * 
 * 
 * 类功能描述: 实现redis的缓存， 基础redis集群模式
 * 
 * @author zhouhong
 * @version V1.0, 2014-5-9
 */
public class RedisCachedImpl implements ICached {
	 
	
	public static final String CHARSET = "UTF-8";
	public JedisCluster redisCli;

	public void setRedisCli(JedisCluster redisCli) {
		this.redisCli = redisCli;
	}

	@Override
	public void put(String key, Object value) throws Exception {
		redisCli.set(key.getBytes(CHARSET), SerializeUtils.serialize(value) );
	}

	@Override
	public void put(String key, Object value, int expireSec) throws Exception {
		redisCli.setex(key.getBytes(CHARSET), expireSec, SerializeUtils.serialize(value));
	}

	@Override
	public void expireSec(String key, int expireSec) throws Exception {
		redisCli.expire(key, expireSec);
	}

	@Override
	public void remove(String key) throws Exception {
		redisCli.del(key);
	}

	@Override
	public Object get(String key) throws Exception {
		return SerializeUtils.deserialize(redisCli.get(key.getBytes(CHARSET)));
	}



	@Override
	public Set hgetKeys(String key) throws Exception {
		return redisCli.hkeys(key);
	}

	@Override
	public void hset(String key, String hkey, Object value) throws Exception {
		redisCli.hset(key.getBytes(CHARSET), hkey.getBytes(CHARSET), SerializeUtils.serialize(value) );
		
	}

	@Override
	public Object hget(String key, String hkey) throws Exception {
		return SerializeUtils.deserialize(redisCli.hget(key.getBytes(CHARSET), hkey.getBytes(CHARSET)));
	}

	@Override
	public Long hremove(String key, String ... hkey) throws Exception {
		return redisCli.hdel(key, hkey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Set<T>  hvalues(String key) throws Exception {
		Collection<byte[]> tmp = this.redisCli.hvals(key.getBytes(CHARSET));
		Set<T> list = new HashSet<T>();
		for (byte[] bs : tmp) {
			list.add((T)SerializeUtils.deserialize(bs));
		}
		return list;
	}

	@Override
	public Long incrAndGetForExpire(String key, int expireSec) throws Exception {
		Long value= this.redisCli.incr(key);
		this.redisCli.expire(key, expireSec);
		return value;
	}
	
	public Map<String,Object> hget(String key) throws Exception{
		Map<byte[], byte[]> map=this.redisCli.hgetAll(key.getBytes(CHARSET));
		Map<String,Object> values=new HashMap<String, Object>();
		for(Map.Entry<byte[], byte[]> entry: map.entrySet()) {
			values.put(new String(entry.getKey(), CHARSET), SerializeUtils.deserialize(entry.getValue()));
		}
		return values;
	}
	
	public void hset(String key,Map<String,Object> vs) throws Exception{
		Map<byte[], byte[]> saveValue=new HashMap<byte[], byte[]>();
		for(Map.Entry<String,Object> entry: vs.entrySet()) {
			saveValue.put(entry.getKey().getBytes(CHARSET), SerializeUtils.serialize(entry.getValue()));
		}
		this.redisCli.hmset(key.getBytes(CHARSET), saveValue);
	}

	@Override
	public Long incrAndGet(String key) throws Exception {
		return this.redisCli.incr(key);
	}

	public Long getIncrOrDecrValue(String key)throws Exception {
		String val=this.redisCli.get(key);
		if(val==null){
			return 0l;
		}else{
			return Long.parseLong(val);
		}
			
	}
	
	@Override
	public Long decrAndGet(String key) throws Exception {
		return this.redisCli.decr(key);
	}

	@Override
	public long lpush(String key, Object value) throws Exception {
		return this.redisCli.lpush(key.getBytes(CHARSET), SerializeUtils.serialize(value) );
	}

	@Override
	public Object lpop(String key) throws Exception {
		return SerializeUtils.deserialize(this.redisCli.lpop(key.getBytes(CHARSET)));
	}

	@Override
	public boolean exists(String key) throws Exception {
		return this.redisCli.exists(key.getBytes(CHARSET));
	}
	@Override
	public Long llen(String key) throws Exception {
		return this.redisCli.llen(key);
	}
	@Override
	public Long incrByVal(String key,Long val) throws Exception {
		return this.redisCli.incrBy(key, val);
	}
	@Override
	public Long decrByVal(String key,Long val) throws Exception {
		return this.redisCli.decrBy(key, val);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("liqinqin".getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
