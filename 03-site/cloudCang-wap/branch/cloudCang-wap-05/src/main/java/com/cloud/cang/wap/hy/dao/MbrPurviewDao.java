package com.cloud.cang.wap.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.MbrPurview;

import java.util.List;


/** 权限码管理(HY_MBR_PURVIEW) **/
public interface MbrPurviewDao extends GenericDao<MbrPurview, String> {

	/**
	 * 根据站点名称查找站点权限
	 * @param siteName
	 * @return
	 */
	List<MbrPurview> selectPurviewBySiteName(String siteName);


	/**
	 * 查找用户权限
	 * @param userId
	 * @return
	 */
	List<MbrPurview> selectPurviewByUserId(String userId);
}