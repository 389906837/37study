package com.cloud.cang.mgr.wz.service.impl;

import java.text.MessageFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.wz.dao.RegionDao;
import com.cloud.cang.mgr.wz.domain.AnnouncementDomain;
import com.cloud.cang.mgr.wz.service.RegionService;
import com.cloud.cang.mgr.wz.vo.AnnouncementVo;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.mgr.wz.dao.AnnouncementDao;
import com.cloud.cang.model.wz.Announcement;
import com.cloud.cang.mgr.wz.service.AnnouncementService;

@Service
public class AnnouncementServiceImpl extends GenericServiceImpl<Announcement, String> implements
				AnnouncementService {

	@Autowired
	AnnouncementDao announcementDao;
	@Autowired
	RegionService regionService;
	@Autowired
	RegionDao regionDao;
	@Autowired
	private ICached iCached;
	private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

	@Override
	public GenericDao<Announcement, String> getDao() {
		return announcementDao;
	}

	@Override
	public Page<AnnouncementDomain> selectAnnounceMent(Page<AnnouncementDomain> page, AnnouncementVo announcementVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return announcementDao.selectAnnounceMent(announcementVo);
	}

	@Override
	public Page<AnnouncementDomain> selectSregionId(Page<AnnouncementDomain> page, Map<String, Object> map) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return announcementDao.selectSregionId(map);
	}

	@Override
	public void save(Announcement announcement) {
		Region region = regionService.selectByPrimaryKey(announcement.getSregionId());
//		List<Announcement> announcementList = announcementDao.selectByRrgionId(region.getId());
//		if (announcementList.size()>=region.getIcount()) {
//			throw new ServiceException("??????,??????????????????!");
//		}
		// ??????id??????????????????
		if (StringUtils.isBlank(announcement.getId())) {
			announcement.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());//????????????
			announcement.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
			announcement.setTaddTime(DateUtils.getCurrentDateTime());
			announcement.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			announcement.setIdelFlag(0);
			announcement.setIstatus(10);
			announcement.setSregionId(region.getId());
			this.insert(announcement);
			//????????????
			String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.add.announcement.area",null)+"???"+MessageSourceUtils.getMessageByKey("main.title.msg",null)+"{0}", announcement.getStitle());
			LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
		} else {
			ifRegionId(announcement.getId());
			// ??????
			announcement.setTupdateTime(DateUtils.getCurrentDateTime());
			announcement.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			announcement.setIdelFlag(0);
			this.updateBySelective(announcement);
			//????????????
			String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.modify.announcement.area",null)+"???"+MessageSourceUtils.getMessageByKey("main.title.msg",null)+"{0}", announcement.getStitle());
			LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
		}
	}

	public String ifRegionId(String regionId) {
		Region region = null;
		Announcement announcement = null;
		if (StringUtils.isNotBlank(regionId)) {
			announcement = announcementDao.selectByPrimaryKey(regionId);
			region = regionService.selectByPrimaryKey(announcement.getSregionId());
		}
		if (null == region) {
			throw new ServiceException(MessageSourceUtils.getMessageByKey("wzcon.empty.ad.area",null));
		}
		return regionId;
	}

	@Override
	public List<Announcement> selectListByVo(AnnouncementVo paramAdvertis) {
		return announcementDao.selectListByVo(paramAdvertis);
	}

	/**
	 * ??????ID??????(?????????=20&&?????????????????????,?????????&&???????????????)
	 * @param checkboxId
	 */
	@Override
	public void delete(String[] checkboxId) {
		for (String id : checkboxId) {
			Announcement announcement = announcementDao.selectByPrimaryKey(id);
			if (announcement != null) {
				if (announcement.getIstatus().equals(20) && DateUtils.getCurrentDateTime().compareTo(announcement.getTvalidDate())<0){
					throw new ServiceException(MessageSourceUtils.getMessageByKey("wzcon.delete.announcement.area.error",null)+"???"+announcement.getStitle()+"??????"+ MessageSourceUtils.getMessageByKey("main.delete.error",null)+"???");
				}
				announcementDao.deleteByPrimaryKey(id);
				announcementDao.updateByPrimaryKey(announcement);
			}
			regionDao.deleteRegionById(id);
			// ????????????
			String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.delete.announcement.area",null)+","+MessageSourceUtils.getMessageByKey("main.title.msg",null)+"{0}", announcement.getStitle());
			LogUtil.addOPLog(LogUtil.AC_DELETE,logText, null);
		}
	}

	@Override
	public List<Announcement> selectByRrgionId(String rid) {
		return announcementDao.selectByRrgionId(rid);
	}

	/**
	 * ????????????
	 * @param region ?????????
	 * @param smerchantCode ????????????
	 */
	@Override
	public void updateByCached(Region region, String smerchantCode) throws Exception {
		//key?????????+??????CODE
		String catcheKey = RedisConst.WZ_REGIO_DETAIL_+region.getScode()+"_"+smerchantCode;

		//???????????????????????????????????????
		AnnouncementVo paramAdvertis = new AnnouncementVo();
		paramAdvertis.setSregionId(region.getId());
		paramAdvertis.setIdelFlag(0);
		paramAdvertis.setIstatus(20);
		paramAdvertis.setSmerchantCode(smerchantCode);
		paramAdvertis.setTpublishDate(DateUtils.add(DateUtils.getToday(), Calendar.DATE,-1));
		Page<Announcement> page = new Page<Announcement>();
		page.setPageNum(1);
		page.setPageSize(region.getIcount());//????????????????????????
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Announcement> advertisList= this.selectListByVo(paramAdvertis);

		//?????????redis??????
		iCached.hset(RedisConst.WZ_REGIO_ANNOUNCEMENT + smerchantCode, catcheKey, JSON.toJSONString(advertisList));
		logger.debug("----??????????????????Redis?????????KEY:"+RedisConst.WZ_REGIO_ANNOUNCEMENT + smerchantCode + "---"+catcheKey+"---"+JSON.toJSONString(advertisList));
	}
}