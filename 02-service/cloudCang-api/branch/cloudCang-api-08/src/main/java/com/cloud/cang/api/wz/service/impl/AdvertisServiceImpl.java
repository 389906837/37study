package com.cloud.cang.api.wz.service.impl;

import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.wz.dao.AdvertisDao;
import com.cloud.cang.api.wz.service.AdvertisService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.AdListModel;
import com.cloud.cang.model.AdModel;
import com.cloud.cang.model.AdvertisModel;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.wz.Advertis;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdvertisServiceImpl extends GenericServiceImpl<Advertis, String> implements
		AdvertisService {

	@Autowired
	AdvertisDao advertisDao;

	
	@Override
	public GenericDao<Advertis, String> getDao() {
		return advertisDao;
	}


	/**
	 * 根据商户ID查询该商户下运营位one的广告
	 * @param deviceInfo
	 * @return
	 */
	public ResponseVo<AdListModel> selectAdOneByMerchantId(DeviceInfo deviceInfo) {
		String merchantId = deviceInfo.getSmerchantId();
		if (StringUtils.isNotBlank(merchantId)) {
//				String id = GrpParaUtil.getValue(); // 从数据字典获取配置的运营区域，然后获取区域ID
			String regionId = "wzidffef2b011e7be44000c2937a231";
			Map<String, String> map = new HashMap<>();
			map.put("sregionId", regionId);
			map.put("smerchantId", merchantId);
			List<AdModel> advertisList = advertisDao.selectAdByMerchantId(map);
			if (CollectionUtils.isNotEmpty(advertisList)) {
				AdListModel adListModel = new AdListModel();
				adListModel.setAdModelList(advertisList);
				return ResponseVo.getSuccessResponse(adListModel);
			}
		}
		return ReturnCodeEnum.ERROR_NO_AD.getResponseVo("没有查询到该设备的广告");
	}

	/**
	 * 根据商户ID查询该商户下运营位two的广告
	 * @param deviceInfo
	 * @return
	 */
	public ResponseVo<AdListModel> selectAdTwoByMerchantId(DeviceInfo deviceInfo) {
		String merchantId = deviceInfo.getSmerchantId();
		if (StringUtils.isNotBlank(merchantId)) {
			String regionId = "wzidffef2b011e7be44000c2937a231";
			Map<String, String> map = new HashMap<>();
			map.put("sregionId", regionId);
			map.put("smerchantId", merchantId);
			List<AdModel> advertisList = advertisDao.selectAdByMerchantId(map);
			if (CollectionUtils.isNotEmpty(advertisList)) {
				AdListModel adListModel = new AdListModel();
				adListModel.setAdModelList(advertisList);
				return ResponseVo.getSuccessResponse(adListModel);
			}
		}
		return ReturnCodeEnum.ERROR_NO_AD.getResponseVo("没有查询到该设备的广告");
	}

	/**
	 * 根据商户ID查询该商户下运营位three的广告
	 * @param deviceInfo
	 * @return
	 */
	public ResponseVo<AdListModel> selectAdThreeByMerchantId(DeviceInfo deviceInfo) {
		String merchantId = deviceInfo.getSmerchantId();
		if (StringUtils.isNotBlank(merchantId)) {
			String regionId = "wzidffef2b011e7be44000c2937a231";
			Map<String, String> map = new HashMap<>();
			map.put("sregionId", regionId);
			map.put("smerchantId", merchantId);
			List<AdModel> advertisList = advertisDao.selectAdByMerchantId(map);
			if (CollectionUtils.isNotEmpty(advertisList)) {
				AdListModel adListModel = new AdListModel();
				adListModel.setAdModelList(advertisList);
				return ResponseVo.getSuccessResponse(adListModel);
			}
		}
		return ReturnCodeEnum.ERROR_NO_AD.getResponseVo("没有查询到该设备的广告");
	}
	/**
	 * 查询设备广告信息
	 * @param scode 设备编号
	 * @param sregionCode 广告运营位
	 * @return
	 */
	@Override
	public List<AdvertisModel> selectByDeviceCodeAndRegionCode(String scode, String sregionCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("scode", scode);
		paramMap.put("sregionCode", sregionCode);
		return advertisDao.selectByDeviceCodeAndRegionCode(paramMap);
	}
}