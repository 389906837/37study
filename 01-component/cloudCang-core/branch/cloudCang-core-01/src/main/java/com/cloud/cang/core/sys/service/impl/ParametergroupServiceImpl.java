package com.cloud.cang.core.sys.service.impl;


import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ZKPConst;
import com.cloud.cang.core.sys.dao.ParameterGroupDetailDao;
import com.cloud.cang.core.sys.dao.ParametergroupDao;
import com.cloud.cang.core.sys.domain.DataDicDomain;
import com.cloud.cang.core.sys.domain.SysParamDomain;
import com.cloud.cang.core.sys.service.CacheDataDicService;
import com.cloud.cang.core.sys.service.ParametergroupService;

import com.cloud.cang.core.sys.vo.ParametergroupVo;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.model.sys.Parametergroup;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

public class ParametergroupServiceImpl extends AbsConfigurationHandler implements ParametergroupService,CacheDataDicService {

	private static Logger log = LoggerFactory.getLogger(ParameterGroupDetailServiceImpl.class);

	
	@Autowired
    ParametergroupDao parametergroupDao;

	@Autowired
    ParameterGroupDetailDao parameterGroupDetailDao;

	public static int PAGE_SIZE=500;
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	private Map<String, DataDicDomain> caches = null;

	@Override
	public boolean configurationHandler(String keyName, String value) {
		if (ZKPConst.CACHE_DATA_DICT.equalsIgnoreCase(keyName)) {
			loadDataDict();
			return true;
		}
		return false;
	}

	/**
	 * 加载字典数据
	 */
	@Override
	public @PostConstruct void loadDataDict() {
		ExecutorManager.getInstance().execute(new Runnable() {
			Page<SysParamDomain> page = null;
			int pageStart=0;
			@Override
			public void run() {
				try{
					lock.writeLock().lock();
					caches = new HashMap<String, DataDicDomain>();
					do{
						pageStart++;
						PageHelper.startPage(pageStart, PAGE_SIZE); 
						page=parametergroupDao.selectParaGroupFetchDetails();
						addCache(page);//添加到caches
					}while(pageStart<page.getPages());
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
				finally{ 
					lock.writeLock().unlock();
				}
			}
		});
	}
	
	private void addCache(Page<SysParamDomain> page) {
		if (page != null && !page.isEmpty()) {
			for (SysParamDomain dicDomain : page) {
				DataDicDomain _v = caches.get(dicDomain.getGroupNo());
				if (_v == null) {
					_v = new DataDicDomain();
					caches.put(dicDomain.getGroupNo(), _v);
					Parametergroup pgroup = new Parametergroup();
					pgroup.setId(dicDomain.getGid());
					pgroup.setBisModify(dicDomain.getIsModify());
					pgroup.setSgroupName(dicDomain.getGroupName());
					pgroup.setSgroupNo(dicDomain.getGroupNo());
					pgroup.setSremark(dicDomain.getGremark());
					_v.setGroup(pgroup);
				}
				ParameterGroupDetail details = new ParameterGroupDetail();
				details.setId(dicDomain.getDid());
				details.setIsort(dicDomain.getSort());
				details.setSgroupid(_v.getGroup().getId().toString());
				details.setSname(dicDomain.getSname());
				details.setSremark(dicDomain.getDremark());
				details.setSvalue(dicDomain.getSvalue());
				_v.addDataDicDetail(details);
			}
		}
		
	}

	
	@Override
	public ParameterGroupDetail selectParamGroupDetailInfo(String groupCode,
			String sname) {
		if (caches == null || StringUtils.isEmpty(groupCode) || StringUtils.isEmpty(sname)) {
			return null;
		}
		try {
			lock.readLock().lock();
			DataDicDomain _vs = caches.get(groupCode);
			if (_vs == null) {
				return null;
			}
			for (ParameterGroupDetail _d : _vs.getDataDicDetails()) {
				if (sname.equalsIgnoreCase(_d.getSname())) {
					ParameterGroupDetail _dest = new ParameterGroupDetail();
					BeanUtils.copyProperties(_dest, _d);
					return _dest;
				}
			}
			return null;
		} catch (Exception e) {
			log.error("", e);
			return null;
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public void deleteParameterGroup(String[] idArray) {
		try {
			parametergroupDao.batchDeleteByIds(idArray);
			parameterGroupDetailDao.batchDeleteByGroupIds(idArray);
		} catch (Exception e) { 
			log.error("", e);
			throw new ServiceException(e);
		}
		
	}

	@Override
	public Parametergroup selectByGroupNo(String groupNo) {
        try {
            return parametergroupDao.selectByGroupNo(groupNo);
         } catch (Exception e) {
             log.error("", e);
             throw new ServiceException(e);
         }
	}

	@Override
	public DataDicDomain selectCacheDataDictInfo(String groupCode) {
		DataDicDomain dest = new DataDicDomain();
		if (caches == null || StringUtils.isEmpty(groupCode)) {
			return dest;
		}
		try {
			lock.readLock().lock();
			DataDicDomain dataDictDomain = caches.get(groupCode);
			if (dataDictDomain == null) {
				return dest;
			}
			BeanUtils.copyProperties(dest, dataDictDomain);
			return dest;
		} catch (Exception e) {
			log.error("", e);
			return dest;
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public ParameterGroupDetail selectCacheParamGroupDetailInfo(
			String groupCode, String sname) {
		if (caches == null || StringUtils.isEmpty(groupCode) || StringUtils.isEmpty(sname)) {
			return null;
		}
		try {
			lock.readLock().lock();
			DataDicDomain dataDictDomain = caches.get(groupCode);
			if (dataDictDomain == null) {
				return null;
			}
			for (ParameterGroupDetail parameterGroupDetail : dataDictDomain.getDataDicDetails()) {
				if (sname.equalsIgnoreCase(parameterGroupDetail.getSname())) {
					ParameterGroupDetail detail = new ParameterGroupDetail();
					BeanUtils.copyProperties(detail, parameterGroupDetail);
					return detail;
				}
			}
			return null;
		} catch (Exception e) {
			log.error("", e);
			return null;
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public void updateByPrimaryKey(Parametergroup parametergroup) {
		parametergroupDao.updateByPrimaryKey(parametergroup);
		
	}

	@Override
	public void insert(Parametergroup parametergroup) {
		 parametergroupDao.insert(parametergroup);
	}

	@Override
	public Page<Parametergroup> selectByVoWhere(Page<Parametergroup> page,
												ParametergroupVo parametergroup) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return parametergroupDao.selectByVoWhere(parametergroup);
	}

	/**
	 * 查询数据字典
	 * @return
	 */
	@Override
	public List<Parametergroup> queryAllData() {
		return parametergroupDao.queryAllData();
	}

}