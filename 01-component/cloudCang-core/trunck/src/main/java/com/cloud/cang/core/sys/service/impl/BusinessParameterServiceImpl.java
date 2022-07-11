package com.cloud.cang.core.sys.service.impl;


import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ZKPConst;
import com.cloud.cang.core.sys.dao.BusinessParameterDao;
import com.cloud.cang.core.sys.domain.BusinessParameterDomain;
import com.cloud.cang.core.sys.service.BusinessParameterService;

import com.cloud.cang.core.sys.vo.BusinessParameterVo;
import com.cloud.cang.model.sys.BusinessParameter;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class BusinessParameterServiceImpl extends AbsConfigurationHandler implements
        BusinessParameterService {
    private static Logger LOGGER = LoggerFactory.getLogger(BusinessParameterServiceImpl.class);
    
	@Autowired
    BusinessParameterDao businessParameterDao;
	
	private Map<String, BusinessParameterDomain> caches = null;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); 
	

	/**
	 * 根据key取得值
	 * @param key
	 * @return
	 */
	public String getCacheValue(String key){
	    if (caches == null || StringUtils.isEmpty(key)) {
            return null;
        }
        try {
            lock.readLock().lock();
            BusinessParameterDomain _d = caches.get(key);
            if (_d == null) {
                return null;
            }
            //BusinessParameterDomain dest = new BusinessParameterDomain();
           // BeanUtils.copyProperties(dest, _d);
            return _d.getValue();
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        } finally {
            lock.readLock().unlock();
        }
	}
	
	public BusinessParameterDomain getCacheParamObj(String key){
        if (caches == null || StringUtils.isEmpty(key)) {
            return null;
        }
        try {
            lock.readLock().lock();
            BusinessParameterDomain _d = caches.get(key);
            if (_d == null) {
                return null;
            }
            BusinessParameterDomain dest = new BusinessParameterDomain();
           BeanUtils.copyProperties(dest, _d);
            return dest;
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }
	
	@Override
    public @PostConstruct void loadDataDict() {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                List<BusinessParameterDomain> result = null;
                try {
                    lock.writeLock().lock();
                    caches = new HashMap<String, BusinessParameterDomain>();
                    result = businessParameterDao.selectAll();
                    initCacheInfo(result);
                } finally {
                    lock.writeLock().unlock();
                }

            }
        });

    }

    private void initCacheInfo(List<BusinessParameterDomain> sysParams) {
        if (sysParams != null && !sysParams.isEmpty()) {

            for (BusinessParameterDomain dicDomain : sysParams) {
                //BusinessParameterDomain _v = caches.get(dicDomain.getName());
                caches.put(dicDomain.getName(), dicDomain);
               
            }

        }
    }

	@Override
	public boolean configurationHandler(String keyName, String value) {
		if (ZKPConst.CACHE_BS_PARAM.equalsIgnoreCase(keyName)) {
			loadDataDict();
			return true;
		}
		return false;
	}

	@Override
	public Page<BusinessParameter> queryPage(Page<BusinessParameter> page,
                                             BusinessParameterVo businessParameter) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return this.businessParameterDao.selectByVoWhere(businessParameter);
	}

	@Override
	public void batchDeleteByIds(String[] checkboxId) {
		businessParameterDao.batchDeleteByIds(checkboxId);
		
	}

	@Override
	public void insert(BusinessParameter businessParameter) {
		businessParameterDao.insert(businessParameter);
		
	}

	@Override
	public void updateByPrimaryKey(BusinessParameter businessParameter) {

		businessParameterDao.updateByIdSelective(businessParameter);
		
	}
	
	@Override
    public BusinessParameter selectByKey(String key){
	    return  businessParameterDao.selectByPrimaryKey(key);
    }

}