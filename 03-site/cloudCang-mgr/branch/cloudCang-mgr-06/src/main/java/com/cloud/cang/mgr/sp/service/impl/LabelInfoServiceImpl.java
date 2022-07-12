package com.cloud.cang.mgr.sp.service.impl;

import java.util.Date;
import java.util.List;

import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.mgr.sp.domain.LabelInfoDomain;
import com.cloud.cang.mgr.sp.vo.LabelInfoVo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sp.dao.LabelInfoDao;
import com.cloud.cang.model.sp.LabelInfo;
import com.cloud.cang.mgr.sp.service.LabelInfoService;

@Service
public class LabelInfoServiceImpl extends GenericServiceImpl<LabelInfo, String> implements
		LabelInfoService {

	@Autowired
	LabelInfoDao labelInfoDao;

	@Autowired
	CommodityInfoDao commodityInfoDao;

	
	@Override
	public GenericDao<LabelInfo, String> getDao() {
		return labelInfoDao;
	}


	@Override
	public Page<LabelInfoDomain> selectPageByDomainWhere(Page<LabelInfoDomain> page, LabelInfoVo labelInfoVo) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<LabelInfoDomain>) labelInfoDao.selectByDomainWhere(labelInfoVo);
	}

	/**
	 * 删除标签字段
	 * 同时清空对应商品的标签字段
	 * @param checkboxId
	 */
	@Override
	public void batchLogicDelByIds(String[] checkboxId) {
		LabelInfo labelInfo = null;
		String operateName = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();
		Date stampTime = DateUtils.getCurrentDateTime();
		for (String id : checkboxId) {
			if (StringUtils.isNotBlank(id)) {
				labelInfo = new LabelInfo();
				labelInfo.setId(id);
				labelInfo.setIdelFlag(1);//逻辑删除，将表中是否删除改为1
				labelInfo.setSupdateUser(operateName);//修改人
				labelInfo.setTupdateTime(stampTime);//修改时间
				labelInfoDao.updateByIdSelective(labelInfo);

				//将对应的商品标签删除
				commodityInfoDao.emptyCommodityLabel(id);
			}
		}


	}

	@Override
	public List<LabelInfo> selectLabelByMerchantId(String merchantId) {
		return labelInfoDao.selectLabelByMerchantId(merchantId);
	}

	@Override
	public List<LabelInfo> selectParentLabelByMerchantId(String merchantId) {
		return labelInfoDao.selectParentLabelByMerchantId(merchantId);
	}

	@Override
	public int updateBySelectiveVo(LabelInfo labelInfo) {
		return labelInfoDao.updateByIdSelectiveVo(labelInfo);
	}
}