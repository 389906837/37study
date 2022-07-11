package com.cloud.cang.wap.hy.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.hy.MbrPurview;

import java.util.List;



public interface MbrPurviewService extends GenericService<MbrPurview, String> {
 
	/**
	 * 查找用户权限
	 * @param userId
	 * @return
	 */
	List<MbrPurview> selectPurviewByUserId(String userId);
}