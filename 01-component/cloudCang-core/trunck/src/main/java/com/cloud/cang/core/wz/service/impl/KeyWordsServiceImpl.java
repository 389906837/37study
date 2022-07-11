package com.cloud.cang.core.wz.service.impl;



import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.wz.dao.KeyWordsDao;
import com.cloud.cang.core.wz.service.KeyWordsService;
import com.cloud.cang.model.wz.KeyWords;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 页面关键字
 * @author zhouhong
 * @version 2.0
 *
 */
public class KeyWordsServiceImpl extends AbsConfigurationHandler implements
		KeyWordsService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(KeyWordsServiceImpl.class);

	@Autowired
	KeyWordsDao keyWordsDao;
	
	private Map<String, KeyWords> caches = null;

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	private final String ZK_CACHE_KEY = "cache_page_keys";

	@Override
	public boolean configurationHandler(String keyName, String value) {
        	    if (keyName.equalsIgnoreCase(ZK_CACHE_KEY)) {
        		try {
        		        loadDataDict();
        			return true;
        		} catch (Exception e) {
        			LOGGER.error("call reload keywords cache error", e);
        		}
        	}
        	return false;
	}

	@Override
	public KeyWords getKeyWords(String url) {
		if (caches == null || StringUtils.isEmpty(url)) {
			return null;
		}

		try {
			lock.readLock().lock();
			KeyWords source = caches.get(url);
			if (source == null) {
				return null;
			}

			KeyWords dest = new KeyWords();
			BeanUtils.copyProperties(dest, source);
			return dest;
		} catch (Exception e) {
			LOGGER.error("", e);
			return null;
		} finally {
			lock.readLock().unlock();
		}
	}
	
	
	@PostConstruct
	public void loadDataDict() {
		ExecutorManager.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				List<KeyWords> result = null;
				try {
					caches = new HashMap<String, KeyWords>();
					lock.writeLock().lock();
					//不要直接调用dao，因为分库后，	@PostConstruct 不会走aop，所以切换库有问题
					result = SpringContext.getBean(KeyWordsService.class).selectByEntityWhere(new KeyWords());
					initCacheInfo(result);
				} finally {
					lock.writeLock().unlock();
				}

			}
		});

	}

	private void initCacheInfo(List<KeyWords> keyWords) {
		if (keyWords != null && !keyWords.isEmpty()) {
			for (KeyWords keyword : keyWords) {
				caches.put(keyword.getSpageUrl(), keyword);
			}
		}
	}

	@Override
	public List<KeyWords> selectByEntityWhere(KeyWords t) {
		return this.keyWordsDao.selectByEntityWhere(t);
	}
	
	


}