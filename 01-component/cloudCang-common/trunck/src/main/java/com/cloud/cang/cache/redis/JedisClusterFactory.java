package com.cloud.cang.cache.redis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
/**
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
public class JedisClusterFactory implements FactoryBean<JedisCluster>,
		InitializingBean {

	private String connStr;

	private JedisCluster jedisCluster;
	private Integer timeout=5000;
	private Integer maxRedirections=6;
	private GenericObjectPoolConfig genericObjectPoolConfig;

	@Override
	public JedisCluster getObject() throws Exception {
		return jedisCluster;
	}

	@Override
	public Class<? extends JedisCluster> getObjectType() {
		return (this.jedisCluster != null ? this.jedisCluster.getClass()
				: JedisCluster.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {

			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			if (this.getConnStr().indexOf(",") == -1) {
				haps.add(new HostAndPort(
						this.connStr.split(":")[0], new Integer(
								this.connStr.split(":")[1])));
			} else {
				for (String connect : this.connStr.split(",")) {
					haps.add(new HostAndPort(connect.split(":")[0],
							new Integer(connect.split(":")[1])));
				}
			}
			return haps;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		jedisCluster = new JedisCluster(haps, timeout, maxRedirections,
				genericObjectPoolConfig);

	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}


	public void setGenericObjectPoolConfig(
			GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}

	public String getConnStr() {
		return connStr;
	}

	public void setConnStr(String connStr) {
		this.connStr = connStr;
	}

	public static void main(String[] args) throws Exception {
		/**
		 * <bean name="genericObjectPoolConfig" class="org.apache.commons.pool2.impl.GenericObjectPoolConfig" >
			<property name="maxWaitMillis" value="-1" />
			<property name="maxTotal" value="1000" />
			<property name="minIdle" value="8" />
			<property name="maxIdle" value="100" />
	</bean>
	
		 */
		
		GenericObjectPoolConfig g=new GenericObjectPoolConfig();
		g.setMaxWaitMillis(-1);
		
		JedisClusterFactory f=new JedisClusterFactory();
		f.setConnStr("10.0.101.50:7000");
		f.genericObjectPoolConfig=g;
		try {
			f.afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RedisCachedImpl i=new RedisCachedImpl();
		i.setRedisCli(f.jedisCluster);
		
		//System.out.println(i.incrByVal("prj_0", 50l));
		System.out.println(i.decrByVal("prj_0", 53l));
		
		//f.jedisCluster.set(key, value)
		//f.jedisCluster.set("a".getBytes(), "a".getBytes())
	}
}