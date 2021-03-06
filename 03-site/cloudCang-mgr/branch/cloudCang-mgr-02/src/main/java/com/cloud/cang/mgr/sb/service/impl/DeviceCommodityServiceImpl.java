package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.Constrants;
import com.cloud.cang.mgr.om.dao.OrderRecordDao;
import com.cloud.cang.mgr.sb.dao.DeviceCommodityDao;
import com.cloud.cang.mgr.sb.dao.DeviceInfoDao;
import com.cloud.cang.mgr.sb.domain.DeviceCommodityDomain;
import com.cloud.cang.mgr.sb.service.DeviceCommodityService;
import com.cloud.cang.mgr.sb.vo.DeviceCommodityVo;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.mgr.sm.dao.DeviceStockDao;
import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.mgr.sys.util.DateUtil;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceCommodity;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.ObjectUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceCommodityServiceImpl extends GenericServiceImpl<DeviceCommodity, String> implements
		DeviceCommodityService {

	@Autowired
	DeviceCommodityDao deviceCommodityDao;

	@Autowired
	DeviceInfoDao deviceInfoDao;

	@Autowired
	MerchantInfoDao merchantInfoDao;

	@Autowired
	CommodityInfoDao commodityInfoDao;

	@Autowired
	OrderRecordDao orderRecordDao;

	@Autowired
	DeviceStockDao deviceStockDao;

	
	@Override
	public GenericDao<DeviceCommodity, String> getDao() {
		return deviceCommodityDao;
	}


	@Override
	public Page<DeviceCommodityDomain> selectPageByDomainWhere(Page<DeviceCommodityDomain> page, DeviceCommodityVo deviceCommodityVo) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String sname = deviceCommodityVo.getCommodityName();
		if (StringUtils.isNotBlank(sname)) {
			char[] chars = sname.toCharArray();
			deviceCommodityVo.setCommodityName(StringUtils.join(chars, '%'));
		}
		return (Page<DeviceCommodityDomain>) deviceCommodityDao.selectByDomainWhere(deviceCommodityVo);

	}

	@Override
	public DeviceCommodityDomain selectDomainByID(String sid) {
		DeviceCommodityDomain deviceCommodityDomain = new DeviceCommodityDomain();
		DeviceCommodity deviceCommodity = deviceCommodityDao.selectByPrimaryKey(sid);//????????????????????????
		DeviceInfo deviceInfo = null;
		MerchantInfo merchantInfo = null;
		CommodityInfo commodityInfo = null;
		if (null != deviceCommodity) {
			String sbId = deviceCommodity.getSdeviceId();//??????ID
			if (StringUtils.isNotBlank(sbId)) {
				deviceInfo = deviceInfoDao.selectByPrimaryKey(sbId);//??????????????????.
				String shId = deviceInfo.getSmerchantId();
				if (StringUtils.isNotBlank(shId)) {
					merchantInfo = merchantInfoDao.selectByPrimaryKey(shId);//??????????????????
				}
			}

			String spId = deviceCommodity.getScommodityId();//??????ID
			if (StringUtils.isNotBlank(spId)) {
				commodityInfo = commodityInfoDao.selectByPrimaryKey(spId);//??????????????????
			}

			if (null != deviceInfo && null != merchantInfo && null != commodityInfo) {//
				ObjectUtils.copyObjValue(deviceCommodity, deviceCommodityDomain);//
				//set??????
				deviceCommodityDomain.setId(deviceCommodity.getId());//???????????????ID
				deviceCommodityDomain.setSname(deviceInfo.getSname());//????????????
				deviceCommodityDomain.setMerchantCode(merchantInfo.getScode());//????????????
				deviceCommodityDomain.setMerchantName(merchantInfo.getSname());//????????????
				deviceCommodityDomain.setCommodityName(commodityInfo.getSname());//????????????
				return deviceCommodityDomain;
			}
		}
		return deviceCommodityDomain;
	}

	/**
	 * ??????????????????
	 * @param checkboxId
	 * @param status
	 * @return
	 */
	@Override
	public ResponseVo<String> updateStatusByIds(String[] checkboxId, String status) {
		String id = checkboxId[0];
		if (StringUtils.isNotBlank(id)) {
			DeviceCommodity deviceCommodity = deviceCommodityDao.selectByPrimaryKey(id);
			if (null != deviceCommodity) {
				DeviceCommodity deviceCommodity1 = new DeviceCommodity();
				deviceCommodity1.setId(id);
				if (Constrants.COMMONDITY_SHELF.equals(status)) { // ????????????
					deviceCommodity1.setIstatus(10);
				} else if (Constrants.COMMONDITY_DROPOFF.equals(status)) { // ????????????
					deviceCommodity1.setIstatus(20);
				} else {
					return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????????????????????????????");
				}
				deviceCommodityDao.updateByIdSelective(deviceCommodity1);
			}
		}
		return ResponseVo.getSuccessResponse("ok");
	}

	/**
	 * ????????????ID?????????ID????????????????????????????????????????????????????????????
	 * @param map
	 * @return
	 */
	@Override
	public Integer selectSalesNum(Map<String, String> map) {
		return deviceCommodityDao.selectSalesNum(map);
	}

	/**
	 * ??????????????????
	 *
	 * @param checkboxId ????????????ID
	 * @param merchantId ??????ID
	 * @param user ?????????ID
	 */
	@Override
	public void dropOffCommodity(String[] checkboxId, String merchantId, String user) {

		for (String id : checkboxId) {
			if (StringUtils.isNotBlank(id)) {

				DeviceCommodity deviceCommodity = deviceCommodityDao.selectByPrimaryKey(id);
				if (null != deviceCommodity && StringUtils.isNotBlank(deviceCommodity.getScommodityId()) && StringUtils.isNotBlank(deviceCommodity.getSdeviceId())) {

					//?????????????????????????????????????????????????????????????????????
//					CommodityInfo commodityInfoVo = new CommodityInfo();
//					commodityInfoVo.setId(deviceCommodity.getScommodityId());
//					commodityInfoVo.setSmerchantId(merchantId);
//					commodityInfoVo.setIdelFlag(0);
//					commodityInfoVo.setIstatus(10);
//					List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByEntityWhere(commodityInfoVo);
//					if (CollectionUtils.isNotEmpty(commodityInfoList)) {
//						throw new ServiceException("????????????????????????????????????????????????????????????");
//					}

					// ?????????????????????????????????????????????????????????????????????????????????????????????0
					DeviceStock deviceStock = new DeviceStock();
					deviceStock.setScommodityId(deviceCommodity.getScommodityId());
					deviceStock.setSmerchantId(merchantId);
					deviceStock.setSdeviceId(deviceCommodity.getSdeviceId());
					List<DeviceStock> deviceStockList = deviceStockDao.selectByEntityWhere(deviceStock);
					if (CollectionUtils.isNotEmpty(deviceStockList)) {
						for (DeviceStock stock : deviceStockList) {
							Integer num = stock.getIstock();
							if (num != null) {
								if (num.compareTo(Integer.valueOf(0)) == 1) { // ????????????0 ??????1
									throw new ServiceException("???????????????????????????????????????????????????????????????");
								}
							}
						}
					}
				}

			}
		}

		// ??????????????????
		for (String id : checkboxId) {
			if (StringUtils.isNotBlank(id)) {
				DeviceCommodity deviceCommodity = new DeviceCommodity();
				deviceCommodity.setId(id);
				deviceCommodity.setIstatus(20);    //10?????? 20??????
				deviceCommodity.setTupdateTime(DateUtils.getCurrentDateTime());
				deviceCommodity.setSupdateUser(user);
				deviceCommodityDao.updateByIdSelective(deviceCommodity);
			}
		}

	}


}