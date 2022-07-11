package com.cloud.cang.tec.hy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.core.common.utils.IdGenerator;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.tec.sh.service.MerchantInfoService;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.hy.dao.FreeDataDao;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.tec.hy.service.FreeDataService;

@Service
public class FreeDataServiceImpl extends GenericServiceImpl<FreeData, String> implements
		FreeDataService {

	@Autowired
	FreeDataDao freeDataDao;
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Override
	public GenericDao<FreeData, String> getDao() {
		return freeDataDao;
	}


	/**
	 * 新增或修改 支付宝免密数据
	 * @param freeData 付宝免密数据
	 * @return
	 */
	@Override
	public FreeData saveOrUpdate(FreeData freeData) {
		if (StringUtil.isBlank(freeData.getId())) {//保存
			freeData.setSpaySerialNumber(IdGenerator.randomUUID());
			MerchantInfo merchantInfo = merchantInfoService.selectByCode(freeData.getSmerchantCode());
			freeData.setSmerchantId(merchantInfo.getId());
			freeData.setTaddTime(new Date());
			freeData.setTupdateTime(new Date());
			freeDataDao.insert(freeData);
		} else {//修改
			freeData.setTupdateTime(new Date());
			freeDataDao.updateByIdSelective(freeData);
		}
		return freeData;
	}


	/**
	 * 获取支付宝免密数据
	 * @param externalAgreementNo 商户签约唯一标识
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public FreeData selectByExternalAgreementNo(String externalAgreementNo, String merchantCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("externalAgreementNo", externalAgreementNo);
		map.put("merchantCode", merchantCode);
		return freeDataDao.selectByExternalAgreementNo(map);
	}
	/**
	 * 获取支付宝免密数据
	 * @param memberId 会员Id
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public FreeData selectByMemberId(String memberId, String merchantCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("merchantCode", merchantCode);
		return freeDataDao.selectByMemberId(map);
	}
}