/**        
 */
package com.cloud.cang.zookeeper.confclient.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.zookeeper.confclient.listener.ConfigurationListener;
import com.cloud.cang.zookeeper.ZookeeperConst;
import com.cloud.cang.zookeeper.confclient.ContextUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.utils.ZKPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.cang.zookeeper.confclient.secret.AESCryptUtil;

/**
 * 
 */
public class PropertiesConfiguration {

	private static String PATH_ZOOKEEPER_CONIF = ZookeeperConst.CONFIG_PATH
			+ ContextUtils.PROJECT_NAME;
	private static String PATH_ZOOKEEPER_BUS_CONIF = ZookeeperConst.CONFIG_PATH
			+ "cache_notice";
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Properties store = new Properties();
	CuratorFramework zkClient;
	private PathChildrenCache pathChildrenCache;
	private PathChildrenCache pathChildrenCache_NOTICE;
	private Map<String, String> listenerNotice;// 增加自定义监听器
	private final ConnectionStateListener connectionStateListener = new ConnectionStateListener() {
		@Override
		public void stateChanged(CuratorFramework client,
				ConnectionState newState) {
			if ((newState == ConnectionState.RECONNECTED)
					|| (newState == ConnectionState.CONNECTED)) {
				try {
					logger.info("重新注册监听");
					creatCacheAddWatcher();
					addListerForBusinessConf();
				} catch (Exception e) {
					logger.error("重新注册监听", e);
				}
			}
		}
	};

	/**
	 * 监听注入节点的配置
	 */
	private void addListerForBusinessConf() {
		if (listenerNotice == null || listenerNotice.isEmpty())
			return;
		pathChildrenCache_NOTICE = new PathChildrenCache(zkClient,
				PATH_ZOOKEEPER_BUS_CONIF, true);
		try {
			pathChildrenCache_NOTICE
					.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
					"Zookeeper不存创建Node Caceh对于" + PATH_ZOOKEEPER_BUS_CONIF, e);
		}
		PathChildrenCacheListener listener = new PathChildrenCacheListener() {

			@Override
			public void childEvent(CuratorFramework client,
					PathChildrenCacheEvent event) throws Exception {
				PathChildrenCacheEvent.Type eventType = event.getType();
				System.out.println(eventType);
				switch (eventType) {
				case CHILD_ADDED:
				case CHILD_UPDATED:
					// event.getData().getPath()
					if (listenerNotice != null) {
						String nodeName = ZKPaths.getNodeFromPath(event
								.getData().getPath());
						String serviceId = listenerNotice.get(nodeName);
						if (StringUtils.isBlank(serviceId))
							return;
						ConfigurationListener customerListener = SpringContext
								.getBean(serviceId);
						if (customerListener != null) {
							logger.info(nodeName + " 通知变更,对应服务:" + serviceId);
							customerListener.configurationChangedNotice(
									nodeName, new String(event.getData()
											.getData()));
							logger.info(nodeName + " 通知变更完成,对应服务:" + serviceId);
						}
					}
					break;
				default:
					if (event.getData() != null)
						System.out.println("PathChildrenCache changed : {path:"
								+ event.getData().getPath() + " data:"
								+ new String(event.getData().getData()) + "}");
				}

			}

		};
		pathChildrenCache_NOTICE.getListenable().addListener(listener);
	}

	/**
	 * 为配置节点增加监听事件
	 */
	private void creatCacheAddWatcher() {
		pathChildrenCache = new PathChildrenCache(zkClient,
				PATH_ZOOKEEPER_CONIF, true);
		try {
			pathChildrenCache
					.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Zookeeper不存创建Node Caceh对于" + PATH_ZOOKEEPER_CONIF, e);
		}
		PathChildrenCacheListener listener = new PathChildrenCacheListener() {

			@Override
			public void childEvent(CuratorFramework client,
					PathChildrenCacheEvent event) throws Exception {
				PathChildrenCacheEvent.Type eventType = event.getType();
				System.out.println(eventType);
				switch (eventType) {
				case CHILD_ADDED:
					saveData(event.getData(), eventType);
					break;
				case CHILD_REMOVED:
					saveData(event.getData(), eventType);
					break;
				case CHILD_UPDATED:
					saveData(event.getData(), eventType);
					break;
				default:
					if (event.getData() != null
							&& event.getData().getData() != null)
						System.out.println("PathChildrenCache changed : {path:"
								+ event.getData().getPath() + " data:"
								+ new String(event.getData().getData()) + "}");
				}

			}

		};
		pathChildrenCache.getListenable().addListener(listener);
	}

	/**
	 * 初始化设置，并增加接听
	 * 
	 * @param projCode
	 * @param profile
	 */
	public PropertiesConfiguration(CuratorFramework zkClient) {
		this.zkClient = zkClient;
		this.initConfig();
		zkClient.getConnectionStateListenable().addListener(
				connectionStateListener);
	}

	/**
	 * 初始化设置，并增加接听
	 * 
	 * @param projCode
	 * @param profile
	 */
	public PropertiesConfiguration(CuratorFramework zkClient,
			Map<String, String> listenerNotice) {
		//
		this(zkClient);
		this.listenerNotice = listenerNotice;
		addListerForBusinessConf();
	}

	/**
	 * 读取zookeeper的配置信息，并保存到磁盘
	 */
	protected void initConfig() {
		try {
			creatCacheAddWatcher();
			List<ChildData> datas = pathChildrenCache.getCurrentData();
			for (ChildData data : datas) {
				saveData(data);
			}
			FileUtils.saveData(ContextUtils.PROJECT_NAME, this.store);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取系统配置信息失败--系统将会读取本地配置文件", e);

		}
		try {
			if (store.isEmpty())
				this.store = FileUtils
						.readConfigFromLocal(ContextUtils.PROJECT_NAME);
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	public void saveData(ChildData childData) throws ServiceException {
		byte[] value = childData.getData();
		String path=ZKPaths.getNodeFromPath(childData.getPath());
		if(path.indexOf("jdbc.username")!=-1 || path.indexOf("jdbc.password")!=-1){
			store.put(path,value == null ? "" : AESCryptUtil.decryptByKey(new String(value)));
		}else{
			store.put(path,value == null ? "" : new String(value));
		}
		
	}

	/**
	 * 处理节点事件的变更
	 * 
	 * @param childData
	 * @param eventType
	 * @throws ServiceException
	 */
	public void saveData(ChildData childData, Type eventType)
			throws ServiceException {
		switch (eventType) {
		case CHILD_ADDED:
		case CHILD_UPDATED:
			saveData(childData);
			FileUtils.saveData(ContextUtils.PROJECT_NAME, this.store);
			break;
		case CHILD_REMOVED:
			store.remove(ZKPaths.getNodeFromPath(childData.getPath()));
			FileUtils.saveData(ContextUtils.PROJECT_NAME, this.store);
			break;

		default:
			break;
		}

	}

	// --------------------------------------------------------------------

	private String getProperty(String key) {
		return store.getProperty(key);
	}

	public Properties getProperties() {
		return this.store;
	}

	public boolean getBoolean(String key) {
		Boolean b = getBoolean(key, null);
		if (b != null) {
			return b.booleanValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return getBoolean(key, new Boolean(defaultValue)).booleanValue();
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		String value = getProperty(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toBoolean(value);
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Boolean object", e);
			}
		}
	}

	public byte getByte(String key) {
		Byte b = getByte(key, null);
		if (b != null) {
			return b.byteValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ " doesn't map to an existing object");
		}
	}

	public byte getByte(String key, byte defaultValue) {
		return getByte(key, new Byte(defaultValue)).byteValue();
	}

	public Byte getByte(String key, Byte defaultValue) {
		String value = getProperty(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toByte(value);
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Byte object", e);
			}
		}
	}

	public double getDouble(String key) {
		Double d = getDouble(key, null);
		if (d != null) {
			return d.doubleValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public double getDouble(String key, double defaultValue) {
		return getDouble(key, new Double(defaultValue)).doubleValue();
	}

	public Double getDouble(String key, Double defaultValue) {
		String value = getProperty(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toDouble(value);
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Double object", e);
			}
		}
	}

	public float getFloat(String key) {
		Float f = getFloat(key, null);
		if (f != null) {
			return f.floatValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public float getFloat(String key, float defaultValue) {
		return getFloat(key, new Float(defaultValue)).floatValue();
	}

	public Float getFloat(String key, Float defaultValue) {
		String value = getProperty(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toFloat(value);
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Float object", e);
			}
		}
	}

	public int getInt(String key) {
		Integer i = getInteger(key, null);
		if (i != null) {
			return i.intValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public int getInt(String key, int defaultValue) {
		Integer i = getInteger(key, null);

		if (i == null) {
			return defaultValue;
		}

		return i.intValue();
	}

	public Integer getInteger(String key, Integer defaultValue) {
		String value = getProperty(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toInteger(value);
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to an Integer object", e);
			}
		}
	}

	public long getLong(String key) {
		Long l = getLong(key, null);
		if (l != null) {
			return l.longValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public long getLong(String key, long defaultValue) {
		return getLong(key, new Long(defaultValue)).longValue();
	}

	public Long getLong(String key, Long defaultValue) {
		String value = getProperty(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toLong(value);
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Long object", e);
			}
		}
	}

	public short getShort(String key) {
		Short s = getShort(key, null);
		if (s != null) {
			return s.shortValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public short getShort(String key, short defaultValue) {
		return getShort(key, new Short(defaultValue)).shortValue();
	}

	public Short getShort(String key, Short defaultValue) {
		String value = getProperty(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toShort(value);
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Short object", e);
			}
		}
	}

	public String getString(String key, String defaultValue) {
		String value = getProperty(key);
		if (value != null) {
			return value;
		} else {
			return defaultValue;
		}
	}

	public static void main(String[] args) {

	}
}
