package com.cloud.cang.security.service;


import com.cloud.cang.security.vo.PurviewVO;

import java.util.List;

/**
 * 安全框架要，
 * @author zhouhong
 *
 */
public interface SecPurviewService {

	/**
	 * 查询所有的权限信息，包括权限对应的路径
	 * @return
	 */
	List<PurviewVO> queryAll();
	
	/**
	 * 查询一个用户所分配的权限
	 * @param userId
	 * @return
	 */
	List<PurviewVO> queryByUserId(String userId);
	
}
