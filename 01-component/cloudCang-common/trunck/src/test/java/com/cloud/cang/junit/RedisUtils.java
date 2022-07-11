package com.cloud.cang.junit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.cloud.cang.cache.redis.RedisCachedImpl;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.cloud.cang.cache.redis.JedisClusterFactory;

/**
 * /**
 * redis cluster链接工厂
 * 
 * <bean name="genericObjectPoolConfig" class="org.apache.commons.pool2.impl.GenericObjectPoolConfig" >
			<property name="maxWaitMillis" value="-1" />
			<property name="maxTotal" value="1000" />
			<property name="minIdle" value="8" />
			<property name="maxIdle" value="100" />
	</bean>

	<bean id="jedisCluster" class="xxx.JedisClusterFactory">
		<property name="connStr" value="ip:port" />
		<property name="timeout" value="300000" />
		<property name="maxRedirections" value="6" />
		<property name="genericObjectPoolConfig" ref="genericObjectPoolConfig" />
	</bean>


 * @author zhouhong
 *
 */
public class RedisUtils {

	public static void main(String[] args) throws Exception {
		GenericObjectPoolConfig cof=new GenericObjectPoolConfig();
		cof.setMaxWaitMillis(-1);
		cof.setMaxTotal(1000);
		cof.setMinIdle(8);
		cof.setMaxIdle(100);
		JedisClusterFactory cli=new JedisClusterFactory();
		cli.setConnStr("192.168.1.31:7000");
		cli.setGenericObjectPoolConfig(cof);
		cli.setMaxRedirections(8);
		cli.setTimeout(50000);
		cli.afterPropertiesSet();
		
		RedisCachedImpl cache=new RedisCachedImpl();
		cache.redisCli=cli.getObject();
		
		//cache.put("t1", 900);
		//cache.put("t2", "liqinqin", 2000);
		
		//System.out.println(cache.get("t1"));
		//System.out.println(cache.get("t2"));
		
		//cache.put("t-obj", new A());
		//System.out.println(cache.get("t-obj"));
		
		//cache.hset("m1", "useranme", "liqinqin");
		//cache.hset("m1", "pwd", "liqinqin1");
		System.out.println(cache.hvalues("m1"));
		
		Map vl=new HashMap();
		vl.put("t1", 20);
		vl.put("t2",new A());
		cache.hset("m2", vl);
		Map a=cache.hget("m2");
		System.out.println();
	}
}

class A implements Serializable{
	private String name="liqin";
	
	private String pwd="pwd";
	private int age=3;
	@Override
	public String toString() {
		return "A [name=" + name + ", pwd=" + pwd + ", age=" + age + "]";
	}
}
