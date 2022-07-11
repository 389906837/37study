package com.cloud.cang.tec.wz.service.impl;

import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.tec.wz.vo.AdvertisVo;
import com.cloud.cang.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.wz.dao.RegionDao;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.tec.wz.service.RegionService;

@Service
public class RegionServiceImpl extends GenericServiceImpl<Region, String> implements
		RegionService {

	@Autowired
	RegionDao regionDao;

	@Override
	public GenericDao<Region, String> getDao() {
		return regionDao;
	}
}