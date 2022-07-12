package com.cloud.cang.mgr.sm.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.utils.IdGenerator;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sm.dao.DeviceStockDao;
import com.cloud.cang.mgr.sm.dao.StockDetailDao;
import com.cloud.cang.mgr.sm.dao.StockOperRecordDao;
import com.cloud.cang.mgr.sm.domain.DeviceStockDomain;
import com.cloud.cang.mgr.sm.service.DeviceStockService;
import com.cloud.cang.mgr.sm.vo.DeviceStockVo;
import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.model.sm.StockOperRecord;
import com.cloud.cang.model.sp.CommodityInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeviceStockServiceImpl extends GenericServiceImpl<DeviceStock, String> implements
		DeviceStockService {

	private static final Logger log = LoggerFactory.getLogger(DeviceStockServiceImpl.class);

	@Autowired
	DeviceStockDao deviceStockDao;

	@Autowired
	StockDetailDao stockDetailDao;

	@Autowired
	StockOperRecordDao stockOperRecordDao;

	@Autowired
	CommodityInfoDao commodityInfoDao;

	
	@Override
	public GenericDao<DeviceStock, String> getDao() {
		return deviceStockDao;
	}

	/**
	 * 查询总库存
	 * @param page
	 * @param deviceStockVo
	 * @return
	 */
	@Override
	public Page<DeviceStockDomain> selectDeviceStock(Page<DeviceStockDomain> page, DeviceStockVo deviceStockVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String spName = deviceStockVo.getSpName();
		if (StringUtils.isNotBlank(spName)) {
			char[] chars = spName.toCharArray();
			deviceStockVo.setSpName(StringUtils.join(chars, '%'));
		}
		return deviceStockDao.selectDeviceStock(deviceStockVo);
	}

	/**
	 * 查询单机库存
	 * @param page
	 * @param deviceStockVo
	 * @return
	 */
	@Override
	public Page<DeviceStockDomain> selectOneDeviceStock(Page<DeviceStockDomain> page, DeviceStockVo deviceStockVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String spName = deviceStockVo.getSpName();
		if (StringUtils.isNotBlank(spName)) {
			char[] chars = spName.toCharArray();
			deviceStockVo.setSpName(StringUtils.join(chars, '%'));
		}
		return deviceStockDao.selectOneDeviceStock(deviceStockVo);
	}

	/**
	 * 修改库存单价和库存数量
	 *
	 * @param deviceStock
	 */
	@Override
	public ResponseVo<DeviceStock> updatePriceAndStock(DeviceStock deviceStock) {
		String deviceStockId = deviceStock.getId();
		DeviceStock oldDeviceStock = deviceStockDao.selectByPrimaryKey(deviceStockId);
		if (null == oldDeviceStock) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("该设备库存不存在");
		}
		Integer newStock = deviceStock.getIstock();
		BigDecimal newPrice = deviceStock.getFsalePrice();
		BigDecimal oldPrice = oldDeviceStock.getFsalePrice();
		Integer oldStock = oldDeviceStock.getIstock();

		List<StockDetail> stockDetailList = stockDetailDao.selectSingleStockDetail(deviceStockId);
		List<StockDetail> stockDetailList1 = new ArrayList<>();
		for (StockDetail s : stockDetailList) {
			if (null == s.getIsaleStatus()) {
				stockDetailList1.add(s);
			}
		}
		Integer size = stockDetailList1.size();

		if (newStock != oldStock || newPrice != oldPrice) {    // 库存变更
			DeviceStock deviceStockVo = new DeviceStock();
			deviceStockVo.setId(deviceStockId);
			deviceStockVo.setIstock(newStock);
			deviceStockVo.setFsalePrice(newPrice);
			deviceStockVo.setTlastUpdateTime(new Date());
			deviceStockDao.updateByIdSelective(deviceStockVo);
		}

		if (newStock > size) {    // 上架
			Integer addNum = newStock - size;
			while (addNum > 0) {
				CommodityInfo commodityInfo = commodityInfoDao.selectByPrimaryKey(oldDeviceStock.getScommodityId());
				Integer ilifeType = commodityInfo.getIlifeType();
				StockDetail stockDetaiVo = new StockDetail();
				stockDetaiVo.setSstockId(deviceStockId);
				stockDetaiVo.setFcostAmount(deviceStock.getFsalePrice());
				if (10 == ilifeType) {
					stockDetaiVo.setSshelfLife(commodityInfo.getIshelfLife()+"天");
				} else {
					stockDetaiVo.setSshelfLife(commodityInfo.getIshelfLife()+"个月");
				}
				stockDetaiVo.setSstockCode(oldDeviceStock.getScode());
				stockDetaiVo.setScommodityId(oldDeviceStock.getScommodityId());
				stockDetaiVo.setScommodityCode(oldDeviceStock.getScommodityCode());
				stockDetaiVo.setSidentifies(IdGenerator.randomUUID());
				stockDetaiVo.setIstatus(10);
				stockDetaiVo.setTaddTime(new Date());
				stockDetailDao.insert(stockDetaiVo);

				StockOperRecord stockOperRecordVo = new StockOperRecord();
				stockOperRecordVo.setIstatus(10);
				stockOperRecordVo.setScommodityId(oldDeviceStock.getScommodityId());
				stockOperRecordVo.setScommodityCode(oldDeviceStock.getScommodityCode());
				stockOperRecordVo.setSdeviceId(oldDeviceStock.getSdeviceId());
				stockOperRecordVo.setSdeviceCode(oldDeviceStock.getSdeviceCode());
				stockOperRecordVo.setSmerchantId(oldDeviceStock.getSmerchantId());
				stockOperRecordVo.setSmerchantCode(oldDeviceStock.getSmerchantCode());
				stockOperRecordVo.setSidentifies(stockDetaiVo.getSidentifies());
				stockOperRecordVo.setTaddTime(new Date());
				stockOperRecordDao.insert(stockOperRecordVo);
				addNum--;
			}
		} else {    // 下架
			Integer subNum = size - newStock;
			List<StockDetail> subShelf = new ArrayList<>();
			subShelf.addAll(stockDetailList1);
			while (subNum > 0) {
				stockDetailList1.remove(0);
				subNum--;
			}
			subShelf.removeAll(stockDetailList1);
			for (StockDetail s : subShelf) {
				StockDetail stockDetaiVo = new StockDetail();
				stockDetaiVo.setId(s.getId());
				stockDetaiVo.setIsaleStatus(90);
				stockDetaiVo.setIstatus(null);
				stockDetailDao.updateById(stockDetaiVo);

				StockOperRecord stockOperRecordVo = new StockOperRecord();
				stockOperRecordVo.setIstatus(20);
				stockOperRecordVo.setScommodityId(oldDeviceStock.getScommodityId());
				stockOperRecordVo.setScommodityCode(oldDeviceStock.getScommodityCode());
				stockOperRecordVo.setSdeviceId(oldDeviceStock.getSdeviceId());
				stockOperRecordVo.setSdeviceCode(oldDeviceStock.getSdeviceCode());
				stockOperRecordVo.setSmerchantId(oldDeviceStock.getSmerchantId());
				stockOperRecordVo.setSmerchantCode(oldDeviceStock.getSmerchantCode());
				stockOperRecordVo.setSidentifies(s.getSidentifies());
				stockOperRecordVo.setTaddTime(new Date());
				stockOperRecordDao.insert(stockOperRecordVo);
			}
		}
		return ResponseVo.getSuccessResponse(deviceStock);

	}
}