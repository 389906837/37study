package com.cloud.cang.core.sys.service.impl;


import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.sys.dao.HolidayDao;
import com.cloud.cang.core.sys.service.HolidayService;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 节日服务
 * @author zhouhong
 * @version 2.0
 *
 */
public class HolidayServiceImpl extends AbsConfigurationHandler implements
		HolidayService {

	private static Logger LOGGER = LoggerFactory
			.getLogger(HolidayServiceImpl.class);

	@Autowired
	HolidayDao holidayDao;

	private static final String HOLIDAY = "cache_holiday_conf";

	private static List<String> caches = null;

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	@Override
	public List<String> selectListString() {
		return holidayDao.selectListString();
	}

	@PostConstruct
	public void loadHoliday() {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					lock.writeLock().lock();
					caches = new ArrayList<String>();
					caches = holidayDao.selectListString();
				} finally {
					lock.writeLock().unlock();
				}

			}
		});

	}

	@Override
	public boolean isRestDay(Date date) {
		try {
			if (date == null) {
				return false;
			}
			lock.readLock().lock();
			if (caches == null) {
				return false;
			}

			if (caches != null) {
				Iterator<String> iterator = caches.iterator();
				while (iterator.hasNext()) {
					String holiday = iterator.next();
					if (DateUtils.dateParseShortString(date).equals(holiday)) {
						return true;
					}
				}
			}
		} finally {
			lock.readLock().unlock();
		}

		return false;
	}

	@Override
	public boolean configurationHandler(String keyName, String value) {
		if (keyName.equalsIgnoreCase(HOLIDAY)) {
			try {
				loadHoliday();
				return true;
			} catch (Exception e) {
				LOGGER.error("call reload holiday error", e);
			}
		}
		return false;
	}

}
