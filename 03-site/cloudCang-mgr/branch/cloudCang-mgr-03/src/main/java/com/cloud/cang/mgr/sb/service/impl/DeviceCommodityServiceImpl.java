package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.Constrants;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
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
		DeviceCommodity deviceCommodity = deviceCommodityDao.selectByPrimaryKey(sid);//获取设备商品信息
		DeviceInfo deviceInfo = null;
		MerchantInfo merchantInfo = null;
		CommodityInfo commodityInfo = null;
		if (null != deviceCommodity) {
			String sbId = deviceCommodity.getSdeviceId();//设备ID
			if (StringUtils.isNotBlank(sbId)) {
				deviceInfo = deviceInfoDao.selectByPrimaryKey(sbId);//获取设备信息.
				String shId = deviceInfo.getSmerchantId();
				if (StringUtils.isNotBlank(shId)) {
					merchantInfo = merchantInfoDao.selectByPrimaryKey(shId);//获取商户信息
				}
			}

			String spId = deviceCommodity.getScommodityId();//商品ID
			if (StringUtils.isNotBlank(spId)) {
				commodityInfo = commodityInfoDao.selectByPrimaryKey(spId);//获取商品信息
			}

			if (null != deviceInfo && null != merchantInfo && null != commodityInfo) {//
				ObjectUtils.copyObjValue(deviceCommodity, deviceCommodityDomain);//
				//set属性
				deviceCommodityDomain.setId(deviceCommodity.getId());//监控配置表ID
				deviceCommodityDomain.setSname(deviceInfo.getSname());//设备名称
				deviceCommodityDomain.setMerchantCode(merchantInfo.getScode());//商户编号
				deviceCommodityDomain.setMerchantName(merchantInfo.getSname());//商户名称
				deviceCommodityDomain.setCommodityName(commodityInfo.getSname());//商品名称
				return deviceCommodityDomain;
			}
		}
		return deviceCommodityDomain;
	}

	/**
	 * 修改商品状态
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
				if (Constrants.COMMONDITY_SHELF.equals(status)) { // 改为上架
					deviceCommodity1.setIstatus(10);
				} else if (Constrants.COMMONDITY_DROPOFF.equals(status)) { // 改为下架
					deviceCommodity1.setIstatus(20);
				} else {
					return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.device.status.update.error"));
				}
				deviceCommodityDao.updateByIdSelective(deviceCommodity1);
			}
		}
		return ResponseVo.getSuccessResponse("ok");
	}

	/**
	 * 根据设备ID和商品ID从订单表与订单明细表中查询该商品的总销量
	 * @param map
	 * @return
	 */
	@Override
	public Integer selectSalesNum(Map<String, String> map) {
		return deviceCommodityDao.selectSalesNum(map);
	}

	/**
	 * 下架设备商品
	 *
	 * @param checkboxId 设备商品ID
	 * @param merchantId 商户ID
	 * @param user 操作员ID
	 */
	@Override
	public void dropOffCommodity(String[] checkboxId, String merchantId, String user) {

		for (String id : checkboxId) {
			if (StringUtils.isNotBlank(id)) {

				DeviceCommodity deviceCommodity = deviceCommodityDao.selectByPrimaryKey(id);
				if (null != deviceCommodity && StringUtils.isNotBlank(deviceCommodity.getScommodityId()) && StringUtils.isNotBlank(deviceCommodity.getSdeviceId())) {

					//判断商品是否已经下架，没有下架不能下架设备商品
//					CommodityInfo commodityInfoVo = new CommodityInfo();
//					commodityInfoVo.setId(deviceCommodity.getScommodityId());
//					commodityInfoVo.setSmerchantId(merchantId);
//					commodityInfoVo.setIdelFlag(0);
//					commodityInfoVo.setIstatus(10);
//					List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByEntityWhere(commodityInfoVo);
//					if (CollectionUtils.isNotEmpty(commodityInfoList)) {
//						throw new ServiceException("该商品在商品列表中处于上架状态，不能下架");
//					}

					// 判断该商品在设备库存表中是否存在，如果存在判断实时数量是否大于0
					DeviceStock deviceStock = new DeviceStock();
					deviceStock.setScommodityId(deviceCommodity.getScommodityId());
					deviceStock.setSmerchantId(merchantId);
					deviceStock.setSdeviceId(deviceCommodity.getSdeviceId());
					List<DeviceStock> deviceStockList = deviceStockDao.selectByEntityWhere(deviceStock);
					if (CollectionUtils.isNotEmpty(deviceStockList)) {
						for (DeviceStock stock : deviceStockList) {
							Integer num = stock.getIstock();
							if (num != null) {
								if (num.compareTo(Integer.valueOf(0)) == 1) { // 数量大于0 返回1
									throw new ServiceException("设备中存在未售完的商品，因此不能下架该商品");
								}
							}
						}
					}
				}

			}
		}

		// 进行下架操作
		for (String id : checkboxId) {
			if (StringUtils.isNotBlank(id)) {
				DeviceCommodity deviceCommodity = new DeviceCommodity();
				deviceCommodity.setId(id);
				deviceCommodity.setIstatus(20);    //10上架 20下架
				deviceCommodity.setTupdateTime(DateUtils.getCurrentDateTime());
				deviceCommodity.setSupdateUser(user);
				deviceCommodityDao.updateByIdSelective(deviceCommodity);
			}
		}

	}


}