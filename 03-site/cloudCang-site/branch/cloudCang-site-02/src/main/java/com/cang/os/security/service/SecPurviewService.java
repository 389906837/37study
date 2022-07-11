package com.cang.os.security.service;

import java.util.List;

import com.cang.os.security.vo.PurviewVO;

/**
 * 安全框架要，
 * @author jili
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
