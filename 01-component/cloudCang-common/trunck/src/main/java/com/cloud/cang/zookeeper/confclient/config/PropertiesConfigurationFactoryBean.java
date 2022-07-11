package com.cloud.cang.zookeeper.confclient.config;

import java.util.HashMap;
import java.util.Properties;

import com.cloud.cang.exception.ServiceException;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

/**
 * 系统参数配置工厂类，实现了spring FactoryBean接口
 * 
 * 在代码中需要读取配置信息时，调用方法为：PropertiesConfigurationFactoryBean.
 * getPropertiesConfiguration();
 * 
	<bean id="listenersNotice" class="java.util.HashMap">  
	    <constructor-arg>  
	       <map>  
	         <entry key="OperatorPurviewNotice" value="shiroChainUpdate"></entry>
	         <entry key="areaLocation" value="areaLocationServiceImpl"></entry>
	         <entry key="dataDict" value="dataDicServiceImpl"></entry>
	         <entry key="holiday" value="holidayService"></entry>
	       </map>  
	    </constructor-arg>  
	</bean>
	<bean id="propertyConfigurer"
		class="PropertiesConfigurationFactoryBean" depends-on="zkClient">
		<constructor-arg name="zkclient" ref="zkClient"/>
		<constructor-arg name="listener" ref="listenersNotice"/><!--  -->
	</bean>
 */
public class PropertiesConfigurationFactoryBean implements
		FactoryBean<Properties> {
	private CuratorFramework zkclient;
	private static PropertiesConfiguration __configuration;
	private static boolean init = false;

	public PropertiesConfigurationFactoryBean(CuratorFramework zkclient) {
		__configuration = new PropertiesConfiguration(zkclient);
		init = true;
	}

	public PropertiesConfigurationFactoryBean(CuratorFramework zkclient,
			HashMap<String, String> listener) {
		__configuration = new PropertiesConfiguration(zkclient, listener);
		init = true;
	}

	@Override
	public Properties getObject() throws Exception {
		Assert.notNull(__configuration);
		return __configuration.getProperties();
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public static PropertiesConfiguration getPropertiesConfiguration() {
		if (!init) {
			throw new ServiceException(
					"PropertiesConfigurationFactoryBean 没有初始化");
		}
		return __configuration;
	}

	public CuratorFramework getZkclient() {
		return zkclient;
	}

	public void setZkclient(CuratorFramework zkclient) {
		this.zkclient = zkclient;
	}
}
