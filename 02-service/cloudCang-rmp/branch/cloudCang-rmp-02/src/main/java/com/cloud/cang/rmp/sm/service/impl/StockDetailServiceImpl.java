package com.cloud.cang.rmp.sm.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.rmp.sm.dao.StockDetailDao;
import com.cloud.cang.rmp.sm.service.StockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class StockDetailServiceImpl extends GenericServiceImpl<StockDetail, String> implements
		StockDetailService {

	@Autowired
	StockDetailDao stockDetailDao;

	
	@Override
	public GenericDao<StockDetail, String> getDao() {
		return stockDetailDao;
	}

	/**
	 * 获取商品明细列表
	 * @param sstockId 设备商品库存ID
	 * @return
	 */
	@Override
	public List<StockDetail> selectByMapForUpdate(String sstockId) {
		return stockDetailDao.selectByMapForUpdate(sstockId);
	}
	/**
	 * 获取商品库存明细
	 * @param sid 库存唯一标识
	 * @return
	 */
	@Override
	public StockDetail selectBySid(String sid) {
		return stockDetailDao.selectBySid(sid);
	}
	/***
	 * 获取库存商品异常商品数据
	 * @param sstockId 设备商品库存ID
	 * @return
	 */
	@Override
	public Map<String, BigDecimal> selectByAbnormalDetails(String sstockId) {
		return stockDetailDao.selectByAbnormalDetails(sstockId);
	}

	/**
	 * 获取设备商品 异常库存明细
	 * @param sstockId 设备商品库存ID
	 * @param unitStr 商品最小销售单位
	 * @return
	 */
	@Override
	public String getAbnormalMap(String sstockId, String unitStr) {
		StringBuffer sb = new StringBuffer();
		//查询异常商品明细数量  更新到库存主表
		Map<String, BigDecimal> abnormalMap = this.selectByAbnormalDetails(sstockId);
		if (null != abnormalMap.get("SALE") && abnormalMap.get("SALE").longValue() > 0) {
			sb.append("已售商品："+abnormalMap.get("SALE")+unitStr+";");
		}
		if (null != abnormalMap.get("EXPIRED") && abnormalMap.get("EXPIRED").longValue() > 0) {
			sb.append("已过期商品："+abnormalMap.get("EXPIRED")+unitStr+";");
		}
		if (null != abnormalMap.get("SALE_EXPIRED") && abnormalMap.get("SALE_EXPIRED").longValue() > 0) {
			sb.append("已过期且已售商品："+abnormalMap.get("SALE_EXPIRED")+unitStr+";");
		}
		if (null != abnormalMap.get("ABNORMAL") && abnormalMap.get("ABNORMAL").longValue() > 0) {
			sb.append("异常商品："+abnormalMap.get("ABNORMAL")+unitStr+";");
		}
		return sb.toString();
	}
}