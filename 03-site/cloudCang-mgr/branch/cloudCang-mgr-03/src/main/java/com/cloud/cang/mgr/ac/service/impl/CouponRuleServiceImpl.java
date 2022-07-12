package com.cloud.cang.mgr.ac.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sp.service.BrandInfoService;
import com.cloud.cang.mgr.sp.service.CategoryService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.mgr.ac.dao.CouponRuleDao;
import com.cloud.cang.model.ac.CouponRule;
import com.cloud.cang.mgr.ac.service.CouponRuleService;

@Service
public class CouponRuleServiceImpl extends GenericServiceImpl<CouponRule, String> implements
		CouponRuleService {

	@Autowired
	CouponRuleDao couponRuleDao;

	@Autowired
	private BrandInfoService brandInfoService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommodityInfoService commodityInfoService;
	@Autowired
	private DeviceInfoService deviceInfoService;
	@Override
	public GenericDao<CouponRule, String> getDao() {
		return couponRuleDao;
	}

	/**
	 * 新增或修改送券规则
	 * @param couponRule 券规则实体
	 * @param iscenesType 活动场景分类 30 下单
	 * @param itype 活动分类 10 场景活动 20 促销活动
	 * @return
	 */
	@Override
	public CouponRule saveOrUpdate(CouponRule couponRule, Integer iscenesType, Integer itype) {
		//验证选择商品是否有效？
		Map<String, Object> paramMap = null;
		//获取限制数据
		if ((null != iscenesType && (iscenesType.intValue() == BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_ORDER.getCode() ||
				iscenesType.intValue() == BizTypeDefinitionEnum.SourceBizStatus.FIRST_ORDER.getCode() ||
				iscenesType.intValue() == BizTypeDefinitionEnum.SourceBizStatus.ORDER.getCode() ))
				|| itype == BizTypeDefinitionEnum.ActivityType.PROMOTIONS_ACTIVITY.getCode()) {//判断是不是场景下单、促销活动 等场景

			if (StringUtil.isNotBlank(couponRule.getSgiveLimitBrand())) {//商品品牌赠送限制
				String temp = couponRule.getSgiveLimitBrand() + ",";
				String tempArr[] = couponRule.getSgiveLimitBrand().split(",");
				List<BrandInfo> brandInfos = null;
				for (String brandId : tempArr) {
					paramMap = new HashMap<String, Object>();
					paramMap.put("smerchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
					paramMap.put("id", brandId);
					brandInfos = brandInfoService.selectByMapWhere(paramMap);
					if (null == brandInfos || brandInfos.size() <= 0) {
						temp = temp.replace(brandId + ",", "");
					}
				}
				couponRule.setSgiveLimitBrand(temp.length() > 0 ? temp.substring(0, temp.length() - 1) : "");
			}
			if (StringUtil.isNotBlank(couponRule.getSgiveLimitCategory())) {//商品分类赠送限制
				String temp = couponRule.getSgiveLimitCategory()+",";
				String tempArr[] = couponRule.getSgiveLimitCategory().split(",");
				Category category = null;
				for(String categoryId : tempArr) {
					category = categoryService.selectByPrimaryKey(categoryId);
					if (null == category) {
						temp = temp.replace(categoryId+",","");
					}
				}
				couponRule.setSgiveLimitCategory(temp.length()>0?temp.substring(0,temp.length()-1):"");
			}
			if (StringUtil.isNotBlank(couponRule.getSuseLimitCommodity())) {//商品使用限制
				String temp = couponRule.getSuseLimitCommodity() + ",";
				String tempArr[] = couponRule.getSuseLimitCommodity().split(",");
				List<CommodityInfo> commodityInfos = null;
				for (String commodityCode : tempArr) {
					paramMap = new HashMap<String, Object>();
					paramMap.put("smerchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
					paramMap.put("scode", commodityCode);
					commodityInfos = commodityInfoService.selectByMapWhere(paramMap);
					if (null == commodityInfos || commodityInfos.size() <= 0) {
						temp = temp.replace(commodityCode + ",", "");
					}
				}
				couponRule.setSuseLimitCommodity(temp.length() > 0 ? temp.substring(0, temp.length() - 1) : "");
			}
			/*if (StringUtil.isNotBlank(couponRule.getSgiveLimitDevice())) {//设备赠送限制
				String temp = couponRule.getSgiveLimitDevice()+",";
				String tempArr[] = couponRule.getSgiveLimitDevice().split(",");
				List<DeviceInfo> deviceInfos = null;
				for(String deviceId : tempArr) {
					paramMap = new HashMap<String, Object>();
					paramMap.put("smerchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
					paramMap.put("scode", deviceId);
					deviceInfos = deviceInfoService.selectByMapWhere(paramMap);
					if (null == deviceInfos || deviceInfos.size() <= 0) {
						temp = temp.replace(deviceId+",","");
					}
				}
				couponRule.setSgiveLimitDevice(temp.length()>0?temp.substring(0,temp.length()-1):"");
			}*/
		} else {
			couponRule.setIgiveMethod(20);
			couponRule.setFmaxValue(BigDecimal.ZERO);
			couponRule.setFminValue(BigDecimal.ZERO);
		}
		//获取使用限制数据
		if (StringUtil.isNotBlank(couponRule.getSuseLimitBrand())) {//商品品牌使用限制
			String temp = couponRule.getSuseLimitBrand()+",";
			String tempArr[] = couponRule.getSuseLimitBrand().split(",");
			List<BrandInfo> brandInfos = null;
			for(String brandId : tempArr) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("smerchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
				paramMap.put("id", brandId);
				brandInfos = brandInfoService.selectByMapWhere(paramMap);
				if (null == brandInfos || brandInfos.size() <= 0) {
					temp = temp.replace(brandId+",","");
				}
			}
			couponRule.setSuseLimitBrand(temp.length()>0?temp.substring(0,temp.length()-1):"");
		}
		if (StringUtil.isNotBlank(couponRule.getSuseLimitCategory())) {//商品分类使用限制
			String temp = couponRule.getSuseLimitCategory()+",";
			String tempArr[] = couponRule.getSuseLimitCategory().split(",");
			Category category = null;
			for(String categoryId : tempArr) {
				category = categoryService.selectByPrimaryKey(categoryId);
				if (null == category) {
					temp = temp.replace(categoryId+",","");
				}
			}
			couponRule.setSuseLimitCategory(temp.length()>0?temp.substring(0,temp.length()-1):"");
		}
		if (StringUtil.isNotBlank(couponRule.getSgiveLimitCommodity())) {//商品赠送限制
			String temp = couponRule.getSgiveLimitCommodity()+",";
			String tempArr[] = couponRule.getSgiveLimitCommodity().split(",");
			List<CommodityInfo> commodityInfos = null;
			for(String commodityCode : tempArr) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("smerchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
				paramMap.put("scode", commodityCode);
				commodityInfos = commodityInfoService.selectByMapWhere(paramMap);
				if (null == commodityInfos || commodityInfos.size() <= 0) {
					temp = temp.replace(commodityCode+",","");
				}
			}
			couponRule.setSgiveLimitCommodity(temp.length()>0?temp.substring(0,temp.length()-1):"");
		}
		if (StringUtil.isNotBlank(couponRule.getSuseLimitDevice())) {//设备使用限制
			String temp = couponRule.getSuseLimitDevice()+",";
			String tempArr[] = couponRule.getSuseLimitDevice().split(",");
			List<DeviceInfo> deviceInfos = null;
			for(String deviceId : tempArr) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("smerchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
				paramMap.put("scode", deviceId);
				deviceInfos = deviceInfoService.selectByMapWhere(paramMap);
				if (null == deviceInfos || deviceInfos.size() <= 0) {
					temp = temp.replace(deviceId+",","");
				}
			}
			couponRule.setSuseLimitDevice(temp.length()>0?temp.substring(0,temp.length()-1):"");
		}

		if (StringUtils.isBlank(couponRule.getId())) {// 执行新增
			couponRule.setIdelFlag(0);
			couponRule.setScode(CoreUtils.newCode("ac_coupon_rule"));
			couponRule.setTaddTime(new Date());
			couponRule.setTupdateTime(new Date());
			couponRule.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			couponRule.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			this.insert(couponRule);
		} else {// 执行修改
			couponRule.setTupdateTime(new Date());
			couponRule.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			this.updateBySelective(couponRule);
		}
		return couponRule;
	}
}