package com.cloud.cang.message.xx.service.impl;

import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.message.xx.dao.SupplierInfoDao;
import com.cloud.cang.message.xx.service.SupplierInfoService;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 供应商服务
 * 
 * @author zhouhong
 * @version 1.0
 */
@Service
public class SupplierInfoServiceImpl extends AbsConfigurationHandler implements
        SupplierInfoService {
    
        private static Logger LOGGER = LoggerFactory
		.getLogger(SupplierInfoServiceImpl.class);
        
        private String CACHESUPPLIERINFO = "cache_supplier_info";
        
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        
        /**
         * 供应商缓存
         */
        private ConcurrentHashMap<String,SupplierInfo> supplierInfoCache = new ConcurrentHashMap<String,SupplierInfo>();

	@Autowired
    SupplierInfoDao supplierInfoDao;
	
	@PostConstruct
	public void loadSupplierInfo() {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					lock.writeLock().lock();
					supplierInfoCache.clear();
					List<SupplierInfo>  list = supplierInfoDao.selectByEntityWhere(new SupplierInfo());
					for (SupplierInfo supplierInfo:list) {
					    supplierInfoCache.put(supplierInfo.getScode(), supplierInfo);
					}
					LOGGER.info("load supplier info ok !!");	
				} finally {
					lock.writeLock().unlock();
				}

			}
		});
	 
	  
	}

	@Override
	public boolean configurationHandler(String keyName, String value) {
	    if (keyName.equalsIgnoreCase(CACHESUPPLIERINFO)) {
    		 try {
    		    	loadSupplierInfo();
    			return true;
    		 } catch (Exception e) {
    			LOGGER.error("call reload holiday error", e);
    		 }
	      }
	    return false;
	}

	@Override
	public SupplierInfo getSupplierInfoByCode(String code) {
		try {
			if (StringUtils.isBlank(code)) {
				return null;
			}
			lock.readLock().lock();
			if (supplierInfoCache == null) {
				return null;
			}

			return supplierInfoCache.get(code);
				
			
		} finally {
			lock.readLock().unlock();
		}
	}

	
	
	
	

}