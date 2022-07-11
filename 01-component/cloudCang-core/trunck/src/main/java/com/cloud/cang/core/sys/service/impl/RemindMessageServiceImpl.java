package com.cloud.cang.core.sys.service.impl;


import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.sys.dao.RemindMessageDao;
import com.cloud.cang.core.sys.domain.RemindMessageDomain;
import com.cloud.cang.core.sys.service.RemindMessageService;

import com.cloud.cang.model.sys.RemindMessage;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class RemindMessageServiceImpl extends AbsConfigurationHandler implements
        RemindMessageService {
	
	private static Logger LOGGER = LoggerFactory
			.getLogger(RemindMessageServiceImpl.class);
	
	private static final String CACHE_REMIND_MESSAGE = "cache_remind_message";

	private static List<RemindMessage> caches = null;

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	private Integer itype = null;
	
	

/*	public RemindMessageServiceImpl(Integer itype) {
		super();
		this.itype = itype;
	}*/

	@Autowired
    RemindMessageDao remindMessageDao;
	
	
	public void init() {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					lock.writeLock().lock();
					caches = new ArrayList<RemindMessage>();
					RemindMessage remindMessage = new RemindMessage();
					if (itype != null && itype != 0 ) {
						remindMessage.setItype(itype);
					}
					caches = remindMessageDao.selectByEntityWhere(remindMessage);
					LOGGER.info("==> remain message load is ok");
				} finally {
					lock.writeLock().unlock();
				}

			}
		});
		
		
	}

	@Override
	public boolean configurationHandler(String keyName, String value) {
		if (keyName.equalsIgnoreCase(CACHE_REMIND_MESSAGE)) {
			try {
				init();
				return true;
			} catch (Exception e) {
				LOGGER.error("call reload remain message error", e);
			}
		}
		return false;
	}

	public Integer getItype() {
		return itype;
	}

	public void setItype(Integer itype) {
		this.itype = itype;
	}

	@Override
	public RemindMessage getRemindMessageByCode(String code) {
		try {
			if (code == null) {
				return null;
			}
			lock.readLock().lock();
			if (caches == null) {
				return null;
			}

			if (caches != null) {
				Iterator<RemindMessage> iterator = caches.iterator();
				while (iterator.hasNext()) {
					RemindMessage remindMessage  = iterator.next();
					if (remindMessage.getScode().equals(code)) {
					RemindMessage targetRemindMessage = new RemindMessage();
					BeanUtils.copyProperties(remindMessage, targetRemindMessage);
					return targetRemindMessage;
					}
				}
			}
		} finally {
			lock.readLock().unlock();
		}
		
		return null;
		
	}

	/**
	 * 消息提醒配置列表数据
	 */
	@Override
	public Page<RemindMessage> selectPageByDomainWhere(Page<RemindMessage> page, RemindMessageDomain remindMessageDomain) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return remindMessageDao.selectPageByDomainWhere(remindMessageDomain);
	}



	/**
	 *  根据ID数据删除消息提醒配置
	 */
	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId)
		{
			RemindMessage remindMessage  = remindMessageDao.selectByPrimaryKey(id);
			if (remindMessage != null)
			{
				//物理删除
				remindMessageDao.deleteByPrimaryKey(remindMessage.getId());
				//逻辑删除
				/*
            	remindMessage.setIdelFlag(1);
            	remindMessageDao.updateByPrimaryKey(remindMessage);

                //操作日志
                String logText=MessageFormat.format("删除消息提醒配置名称{0},编号{1}",remindMessage.getSname(),remindMessage.getScode() );
                LogUtil.addOPLog(LogUtil.AC_DELETE, LogUtil.TY_OTHER, logText, null);
				*/
			}
		}
	}

	@Override
	public RemindMessage selectByPrimaryKey(String id) {
		return  remindMessageDao.selectByPrimaryKey(id);
	}

	@Override
	public void insert(RemindMessage remindMessage) {
		remindMessageDao.insert(remindMessage);
	}

	@Override
	public void updateBySelective(RemindMessage remindMessage) {
		remindMessageDao.updateByIdSelective(remindMessage);
	}

}