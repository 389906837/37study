package com.cloud.cang.mgr.vr.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.mgr.vr.dao.CommoditySkuDao;
import com.cloud.cang.mgr.vr.dao.MerchantSkuDao;
import com.cloud.cang.mgr.vr.service.MerchantSkuService;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.vr.CommoditySku;
import com.cloud.cang.model.vr.MerchantSku;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantSkuServiceImpl extends GenericServiceImpl<MerchantSku, String> implements
		MerchantSkuService {

	@Autowired
	MerchantSkuDao merchantSkuDao;

	@Autowired
	CommoditySkuDao commoditySkuDao;

	@Autowired
	MerchantInfoDao merchantInfoDao;

	
	@Override
	public GenericDao<MerchantSku, String> getDao() {
		return merchantSkuDao;
	}


	@Override
	public ResponseVo<String> insertVrCommodityIds(String vrCommodityIds, String merchantId) {
		MerchantInfo merchantInfo = merchantInfoDao.selectByPrimaryKey(merchantId);
		if (null == merchantInfo) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户信息为空");
		}
		//删除原先数据
		merchantSkuDao.deleteByVrMerchantId(merchantInfo.getId());
		String merchanrCode = merchantInfo.getScode();        // 商户编号
		if (StringUtil.isNotBlank(vrCommodityIds)) {
			String[] vrArray = vrCommodityIds.split(","); // 分割设备ID拼接的字符串“，”
			if (null != vrArray) {
				MerchantSku merchantSku = null;
				CommoditySku commoditySku = null;
				// 插入数据
				for (String vrId : vrArray) {
					if (StringUtil.isNotBlank(vrId)) {
						commoditySku = commoditySkuDao.selectByPrimaryKey(vrId);    // 查询视觉商品信息
						if (null != commoditySku) {
							String commodityCode = commoditySku.getSvrCode();    // 视觉识别编号
							if (StringUtils.isNotBlank(merchanrCode) && StringUtils.isNotBlank(commodityCode)) {
								merchantSku = new MerchantSku();
								merchantSku.setScommodityId(vrId);
								merchantSku.setScommodityCode(commodityCode);
								merchantSku.setSmerchantId(merchantId);
								merchantSku.setSmerchantCode(merchanrCode);
								merchantSku.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
								merchantSku.setTaddTime(DateUtils.getCurrentDateTime());
								merchantSkuDao.insert(merchantSku);
							}
						}
					}
				}
			}
		}
		return ResponseVo.getSuccessResponse(merchanrCode);
	}
}