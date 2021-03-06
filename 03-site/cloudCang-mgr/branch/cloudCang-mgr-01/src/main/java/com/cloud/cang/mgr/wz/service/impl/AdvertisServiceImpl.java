package com.cloud.cang.mgr.wz.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.wz.dao.AdvertisDao;
import com.cloud.cang.mgr.wz.dao.RegionDao;
import com.cloud.cang.mgr.wz.domain.AdvertisDomain;
import com.cloud.cang.mgr.wz.service.AdvertisService;
import com.cloud.cang.mgr.wz.service.RegionService;
import com.cloud.cang.mgr.wz.vo.AdvertisVo;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AdvertisServiceImpl extends GenericServiceImpl<Advertis, String> implements
		AdvertisService {
	@Autowired
	AdvertisDao advertisDao;
	@Autowired
	RegionDao regionDao;
	@Autowired
	RegionService regionService;
	@Autowired
	private ICached iCached;
	private static final Logger logger = LoggerFactory.getLogger(AdvertisServiceImpl.class);


	@Override
	public GenericDao<Advertis, String> getDao() {
		return advertisDao;
	}


	@Override
	public Page<AdvertisDomain> selectAdvertis(Page<AdvertisDomain> page, AdvertisVo advertisVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return advertisDao.selectAdvertis(advertisVo);
	}

	@Override
	public Page<AdvertisDomain> selectSregionId(Page<AdvertisDomain> page, Map<String, Object> map) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return advertisDao.selectSregionId(map);
	}

	@Override
	public List<Advertis> selectByRrgionId(String rid) {
		return advertisDao.selectByRrgionId(rid);
	}

	@Override
	public void save(MultipartFile photoFile, Advertis advertis) {
		if (StringUtils.isBlank(advertis.getId())) {
			if (photoFile != null && photoFile.getSize() > 0) {
				// 1.????????????
				String url = uploadHome(photoFile, advertis.getSregionId());
//			String url = uploadHome(photoFile, advertis.getId());
				advertis.setScontentUrl(url);
			}
		}
		else {
			if (photoFile != null && photoFile.getSize() > 0) {
				String url = uploadHome(photoFile, advertis.getId());
//			if (StringUtils.isNotBlank(advertis.getId())) {
//				Advertis oldAdvertis = advertisDao.selectByPrimaryKey(advertis.getId());
				advertis.setScontentUrl(url);
			}
		}

		Region region = regionService.selectByPrimaryKey(advertis.getSregionId());

			// ??????id??????????????????
			if (StringUtils.isBlank(advertis.getId())) {
				List<Advertis> advertisList = advertisDao.selectByRrgionId(region.getId());
				if (advertisList.size()==region.getIcount()) {
					throw new ServiceException("?????????????????????????????????????????????!");
				}
				if (photoFile == null){
					throw new ServiceException("?????????????????????");
				}
				advertis.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());//????????????
				advertis.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
				advertis.setTaddTime(DateUtils.getCurrentDateTime());
				advertis.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				advertis.setTupdateTime(DateUtils.getCurrentDateTime());
				advertis.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				advertis.setIdelFlag(0);
				advertis.setSregionId(region.getId());
				this.insert(advertis);
				//????????????
				String logText = MessageFormat.format("???????????????????????????{0}", advertis.getStitle());
				LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
			} else {
				// ??????
				advertis.setTupdateTime(DateUtils.getCurrentDateTime());
				advertis.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				advertis.setIdelFlag(0);
				this.updateBySelective(advertis);
				//????????????
				String logText = MessageFormat.format("???????????????????????????{0}", advertis.getStitle());
				LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
			}
	}

	public String uploadHome(MultipartFile imgFile, String regionId) {
		Region region = null;
		Advertis advertis = null;
		if (StringUtils.isNotBlank(regionId)) {
			region = regionService.selectByPrimaryKey(regionId);
			advertis = advertisDao.selectByPrimaryKey(regionId);
		}
		if (advertis != null){
			region = regionService.selectByPrimaryKey(advertis.getSregionId());
		}
		if (null == region) {
			throw new ServiceException("????????????????????????");
		}
		if (imgFile == null) {
			throw new ServiceException("????????????????????????");
		}
		//????????????
		String filName = imgFile.getOriginalFilename();
		String[] fileNameSplit = filName.split("\\.");
		String exp = fileNameSplit[fileNameSplit.length - 1];
		//??????????????????
		if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
			throw new ServiceException("?????????????????????????????????");
		}
		String url = "";
		try {
			String code = region.getScode();
			String serverPath = "article/" + DateUtils.dateParseShortString(new Date()) + "/" + code + "/";
			String fileFileName = imgFile.getOriginalFilename();
			fileFileName = fileFileName.substring(fileFileName.lastIndexOf("."));
			String tempName = DateUtils.getCurrentSTimeNumber() + fileFileName;
			if (FtpUtils.uploadToFtpServer(imgFile.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
				url =  "/" + serverPath + tempName;
				return url;
			}
		} catch (IOException e) {
			throw new ServiceException("?????????????????????????????????");
		}
		return url;
	}

	@Override
	public List<Advertis> selectListByVo(AdvertisVo paramAdvertis) {
		return advertisDao.selectListByVo(paramAdvertis);
	}
	/**
	 * ????????????
	 * @param region ?????????
	 * @param smerchantCode ????????????
	 */
	@Override
	public void updateByCached(Region region, String smerchantCode) throws Exception {
		//key?????????+??????CODE
		String catcheKey = RedisConst.WZ_REGIO_DETAIL_ + region.getScode() + "_" + smerchantCode;
		logger.info("???KEY???????????????=========="+catcheKey);
		//???????????????????????????????????????
		AdvertisVo paramAdvertis = new AdvertisVo();
		paramAdvertis.setSregionId(region.getId());
		paramAdvertis.setSmerchantCode(smerchantCode);
		paramAdvertis.setIdelFlag(0);
		paramAdvertis.setIstatus(1);
		paramAdvertis.setTstartDate(DateUtils.add(DateUtils.getToday(), Calendar.DATE,-1));
		Page<AdvertisVo> page = new Page<AdvertisVo>();
		page.setPageNum(1);
		page.setPageSize(region.getIcount());//????????????????????????
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Advertis> advertisList= this.selectListByVo(paramAdvertis);

		//?????????redis??????
		iCached.hset(RedisConst.WZ_REGIO_ADVERTIS + smerchantCode, catcheKey, JSON.toJSONString(advertisList));
		logger.debug("----????????????Redis?????????KEY:"+RedisConst.WZ_REGIO_ADVERTIS + smerchantCode +"---"+catcheKey+"---"+JSON.toJSONString(advertisList));
	}

	/**
	 * ??????ID??????????????????(???????????????????????????????????????)
	 * @param checkboxId
	 */
	@Override
	public void delete(String[] checkboxId) {
		for (String id : checkboxId) {
			Advertis advertis = advertisDao.selectByPrimaryKey(id);
			if (advertis != null) {
				if (advertis.getIstatus().equals(1)){
					throw new ServiceException("?????????????????????????????????");
				}
				advertis.setIdelFlag(1);
				advertisDao.updateByPrimaryKey(advertis);
			}
			regionDao.deleteRegionById(id);
			// ????????????
			String logText = MessageFormat.format("??????????????????,??????{0}", advertis.getStitle());
			LogUtil.addOPLog(LogUtil.AC_DELETE,logText, null);
		}
	}
}