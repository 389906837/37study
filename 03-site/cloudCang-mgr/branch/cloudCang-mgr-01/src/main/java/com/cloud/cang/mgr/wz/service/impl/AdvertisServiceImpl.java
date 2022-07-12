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
				// 1.文件上传
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

			// 如果id为空就就添加
			if (StringUtils.isBlank(advertis.getId())) {
				List<Advertis> advertisList = advertisDao.selectByRrgionId(region.getId());
				if (advertisList.size()==region.getIcount()) {
					throw new ServiceException("请按照运营区域管理广告数量添加!");
				}
				if (photoFile == null){
					throw new ServiceException("请选择图片上传");
				}
				advertis.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());//商户编号
				advertis.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
				advertis.setTaddTime(DateUtils.getCurrentDateTime());
				advertis.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				advertis.setTupdateTime(DateUtils.getCurrentDateTime());
				advertis.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				advertis.setIdelFlag(0);
				advertis.setSregionId(region.getId());
				this.insert(advertis);
				//操作日志
				String logText = MessageFormat.format("新增广告区域，标题{0}", advertis.getStitle());
				LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
			} else {
				// 修改
				advertis.setTupdateTime(DateUtils.getCurrentDateTime());
				advertis.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				advertis.setIdelFlag(0);
				this.updateBySelective(advertis);
				//操作日志
				String logText = MessageFormat.format("修改广告区域，标题{0}", advertis.getStitle());
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
			throw new ServiceException("没有找到运营区域");
		}
		if (imgFile == null) {
			throw new ServiceException("没有找到上传文件");
		}
		//文件原名
		String filName = imgFile.getOriginalFilename();
		String[] fileNameSplit = filName.split("\\.");
		String exp = fileNameSplit[fileNameSplit.length - 1];
		//图片类型限制
		if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
			throw new ServiceException("文件类型错误，上传失败");
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
			throw new ServiceException("没有找到文件，上传失败");
		}
		return url;
	}

	@Override
	public List<Advertis> selectListByVo(AdvertisVo paramAdvertis) {
		return advertisDao.selectListByVo(paramAdvertis);
	}
	/**
	 * 更新缓存
	 * @param region 运营位
	 * @param smerchantCode 商户编号
	 */
	@Override
	public void updateByCached(Region region, String smerchantCode) throws Exception {
		//key为前缀+栏目CODE
		String catcheKey = RedisConst.WZ_REGIO_DETAIL_ + region.getScode() + "_" + smerchantCode;
		logger.info("从KEY广告位运营=========="+catcheKey);
		//找出已发布和未删除的广告位
		AdvertisVo paramAdvertis = new AdvertisVo();
		paramAdvertis.setSregionId(region.getId());
		paramAdvertis.setSmerchantCode(smerchantCode);
		paramAdvertis.setIdelFlag(0);
		paramAdvertis.setIstatus(1);
		paramAdvertis.setTstartDate(DateUtils.add(DateUtils.getToday(), Calendar.DATE,-1));
		Page<AdvertisVo> page = new Page<AdvertisVo>();
		page.setPageNum(1);
		page.setPageSize(region.getIcount());//取出要缓存的数量
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Advertis> advertisList= this.selectListByVo(paramAdvertis);

		//更新到redis缓存
		iCached.hset(RedisConst.WZ_REGIO_ADVERTIS + smerchantCode, catcheKey, JSON.toJSONString(advertisList));
		logger.debug("----更新广告Redis缓存主KEY:"+RedisConst.WZ_REGIO_ADVERTIS + smerchantCode +"---"+catcheKey+"---"+JSON.toJSONString(advertisList));
	}

	/**
	 * 根据ID进行逻辑删除(删除页面数据同时删除表数据)
	 * @param checkboxId
	 */
	@Override
	public void delete(String[] checkboxId) {
		for (String id : checkboxId) {
			Advertis advertis = advertisDao.selectByPrimaryKey(id);
			if (advertis != null) {
				if (advertis.getIstatus().equals(1)){
					throw new ServiceException("投放中的广告请勿删除！");
				}
				advertis.setIdelFlag(1);
				advertisDao.updateByPrimaryKey(advertis);
			}
			regionDao.deleteRegionById(id);
			// 操作日志
			String logText = MessageFormat.format("删除广告区域,标题{0}", advertis.getStitle());
			LogUtil.addOPLog(LogUtil.AC_DELETE,logText, null);
		}
	}
}