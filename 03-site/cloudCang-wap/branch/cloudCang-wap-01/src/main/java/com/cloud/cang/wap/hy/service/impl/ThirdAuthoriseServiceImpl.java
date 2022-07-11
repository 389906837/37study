package com.cloud.cang.wap.hy.service.impl;

import org.apache.zookeeper.Op;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.wap.hy.dao.ThirdAuthoriseDao;
import com.cloud.cang.model.hy.ThirdAuthorise;
import com.cloud.cang.wap.hy.service.ThirdAuthoriseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThirdAuthoriseServiceImpl extends GenericServiceImpl<ThirdAuthorise, String> implements
		ThirdAuthoriseService {

	@Autowired
	ThirdAuthoriseDao thirdAuthoriseDao;

	
	@Override
	public GenericDao<ThirdAuthorise, String> getDao() {
		return thirdAuthoriseDao;
	}

	/***
	 * 获取第三方授权登录信息
	 * @param openId 第三方openId
	 * @param itype 第三方类型 10:微信 20:支付宝
	 * @return
	 */
	@Override
	public List<ThirdAuthorise> selectThirdAuthoriseByOpenId(String merchantCode,String openId, Integer itype) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("itype", itype);
		map.put("merchantCode", merchantCode);
		return thirdAuthoriseDao.selectThirdAuthoriseByOpenId(map);
	}
	/***
	 * 获取第三方授权登录信息
	 * @param memberId 会员ID
	 * @param itype 第三方类型 10:微信 20:支付宝
	 * @return
	 */
	@Override
	public ThirdAuthorise selectThirdAuthoriseByMemberId(String memberId, Integer itype) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("itype", itype);
		return thirdAuthoriseDao.selectThirdAuthoriseByMemberId(map);
	}
}