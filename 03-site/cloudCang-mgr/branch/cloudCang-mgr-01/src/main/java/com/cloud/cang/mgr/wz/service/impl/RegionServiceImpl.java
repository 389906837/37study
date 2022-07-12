package com.cloud.cang.mgr.wz.service.impl;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.wz.dao.AdvertisDao;
import com.cloud.cang.mgr.wz.dao.AnnouncementDao;
import com.cloud.cang.mgr.wz.dao.RegionDao;
import com.cloud.cang.mgr.wz.domain.RegionDomain;
import com.cloud.cang.mgr.wz.service.AdvertisService;
import com.cloud.cang.mgr.wz.service.RegionService;
import com.cloud.cang.mgr.wz.vo.RegionVo;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Announcement;
import com.cloud.cang.model.wz.Region;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class RegionServiceImpl extends GenericServiceImpl<Region, String> implements
		RegionService {

	@Autowired
	RegionDao regionDao;

	@Autowired
	AdvertisDao advertisDao;

	@Autowired
	AnnouncementDao announcementDao;

	@Override
	public GenericDao<Region, String> getDao() {
		return regionDao;
	}

	@Override
	public Page<Region> selectRegionAll(Page<Region> page, RegionVo regionVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return regionDao.selectRegionAll(regionVo);
	}

	/**
	 * 根据运营ID删除
	 * @param checkboxId
	 */
	@Override
	public void delete(String[] checkboxId) {
		// 查询广告表
		List<Advertis> advertisList = null;
		// 查询系统公告
		List<Announcement> announcementList = null;
		for (String id:checkboxId)
		{
			// 查询运营区域表
			Region region  = regionDao.selectByPrimaryKey(id);
			if (region != null) {
				// 判断广告类型;10:广告,20:公告
				if (region.getItype().equals(10)){
					advertisList = advertisDao.selectByRrgionId(region.getId());
					for (Advertis advertis : advertisList){
						if (advertis.getIstatus().equals(1)){
							throw new ServiceException("运营区域正在使用请勿删除！");
						}
					}
				}
				if (region.getItype().equals(20)){
					announcementList = announcementDao.selectByRrgionId(region.getId());
					for (Announcement announcement : announcementList){
						if (announcement.getIstatus().equals(20)){
							throw new ServiceException("系统公告为已发布状态且未到期请勿删除！");
						}
					}
				}
				//逻辑删除
				region.setIdelFlag(1);
				regionDao.updateByPrimaryKey(region);

				//操作日志
				String logText= MessageFormat.format("删除运营区域,名称{0},编号{1}",region.getSregionName(),region.getScode() );
				LogUtil.addOPLog(LogUtil.AC_DELETE,logText, null);

			}
		}
	}

	@Override
	public List<Region> selectGetRerionList(RegionVo regionVo) {
		return regionDao.selectGetRerionList(regionVo);
	}

	@Override
	public List<Region> selectGetRerionList1(RegionVo regionVo) {
		return regionDao.selectGetRerionList1(regionVo);
	}

}