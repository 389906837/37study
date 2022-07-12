package com.cloud.cang.mgr.sp.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cloud.cang.core.common.utils.BigDecimalUtils;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.mgr.sp.domain.CommodityBatchDomain;
import com.cloud.cang.mgr.sp.vo.CommodityBatchVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
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

import com.cloud.cang.mgr.sp.dao.CommodityBatchDao;
import com.cloud.cang.model.sp.CommodityBatch;
import com.cloud.cang.mgr.sp.service.CommodityBatchService;

@Service
public class CommodityBatchServiceImpl extends GenericServiceImpl<CommodityBatch, String> implements
		CommodityBatchService {

	@Autowired
	CommodityBatchDao commodityBatchDao;

	@Autowired
	CommodityInfoDao commodityInfoDao;

	
	@Override
	public GenericDao<CommodityBatch, String> getDao() {
		return commodityBatchDao;
	}


	@Override
	public Page<CommodityBatchDomain> selectPageByDomainWhere(Page<CommodityBatchDomain> page, CommodityBatchVo commodityBatchVo) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String sname = commodityBatchVo.getCommodityName();
		if (StringUtils.isNotBlank(sname)) {
			char[] chars = sname.toCharArray();
			commodityBatchVo.setCommodityName(StringUtils.join(chars, '%'));
		}
		return (Page<CommodityBatchDomain>) commodityBatchDao.selectByDomainWhere(commodityBatchVo);
	}

	/**
	 * 根据商品ID查询商品批次信息
	 * @param sid
	 * @return
	 */
	@Override
	public List<CommodityBatch> selectByCommodityId(String sid) {
		return commodityBatchDao.selectByCommodityId(sid);
	}

	/**
	 * 批量添加商品批次信息
	 * @param commodityIds
	 * @param commodityBatch
	 */
	@Override
	public void insertCommodityIds(String commodityIds, CommodityBatch commodityBatch) {
		String[] commodityArray = commodityIds.split(",");    //	分割设备ID拼接的字符串“，”
		String commodityId = null;		// 商品ID
		String commodityCode = null;	// 商品编号
		String merchantId = null;		// 商户ID
		String merchantCode = null;		// 商户编号
		if (ArrayUtils.isNotEmpty(commodityArray)) {
			for (int i = 0; i < commodityArray.length; i++) {
				commodityId = commodityArray[i];
				if (StringUtils.isNotBlank(commodityId)) {

					//选中商品中如果已经过添加商品批次信息不做处理，循环下一个
					List<CommodityBatch> commodityBatchList = commodityBatchDao.selectByCommodityId(commodityId);
					if (CollectionUtils.isNotEmpty(commodityBatchList)) {
						continue;
					}

					// 查询选中的单个商品信息
					CommodityInfo commodityInfo = commodityInfoDao.selectByPrimaryKey(commodityId);
					if (null != commodityInfo) {
						commodityCode = commodityInfo.getScode();
						merchantId = commodityInfo.getSmerchantId();
						merchantCode = commodityInfo.getSmerchantCode();

						//计算过期日期
						Date dproduceDate = commodityBatch.getDproduceDate();   //生产日期
						long dexpiredDateTemp = dproduceDate.getTime();                             //保质期时间--毫秒
						Integer ilifeType = commodityInfo.getIlifeType(); //保质期类型10=天 20=月
						Integer ishelfLife = commodityInfo.getIshelfLife(); //保质期
						if (Integer.valueOf(10).equals(ilifeType)) { //天
							dexpiredDateTemp = dexpiredDateTemp + (long) ishelfLife * 24 * 3600 * 1000;
							Calendar c = Calendar.getInstance();
							c.setTimeInMillis(dexpiredDateTemp);
							Date dexpiredDate = c.getTime();
							commodityBatch.setDexpiredDate(dexpiredDate);
						} else if (Integer.valueOf(20).equals(ilifeType)) { //月    按30天计算
							dexpiredDateTemp = dexpiredDateTemp + (long) ishelfLife * 30 * 24 * 3600 * 1000;
							Calendar c = Calendar.getInstance();
							c.setTimeInMillis(dexpiredDateTemp);
							Date dexpiredDate = c.getTime();
							commodityBatch.setDexpiredDate(dexpiredDate);
						} else {    //添加商品没有填保质期字段
						}

					}
					String sbatchNo = CoreUtils.newCode(EntityTables.SP_COMMODITY_BATCH);//商品批次编号;
					if (StringUtils.isNotBlank(sbatchNo) && StringUtils.isNotBlank(commodityCode) && StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(merchantCode)) {//商品批次编号
						commodityBatch.setScommodityId(commodityId);		// 商品ID
						commodityBatch.setScommodityCode(commodityCode);	// 商品编号
						commodityBatch.setSmerchantId(merchantId);			// 商户ID
						commodityBatch.setSmerchantCode(merchantCode);		// 商户编号
						commodityBatch.setSbatchNo(sbatchNo);   			// 商品批次编号
						commodityBatch.setIstatus(10);      				// 10启用 20禁用
						commodityBatch.setIstockStatus(10); 				// 10待上架 20部分上架 30全部上架
						commodityBatch.setIsaleStatus(10);  				// 10销售中 20售罄
						commodityBatch.setIdelFlag(0);						// 是否删除0否1是
						commodityBatch.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
						commodityBatch.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
						commodityBatch.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
						commodityBatch.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
						commodityBatchDao.insert(commodityBatch);
					}

				}
			}
		}
	}

	/**
	 * 添加商品批次信息
	 * @param commodityBatch
	 * @param sbatchNo
	 */
	@Override
	public void insertCommodityBatch(CommodityBatch commodityBatch, String sbatchNo) {

		Integer totalNum = commodityBatch.getIcommodityNum();
		Integer lossNum = commodityBatch.getIlossGoodsNum();
		if (totalNum != null && lossNum != null && (lossNum.compareTo(totalNum) == 1)) {
			throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.add.sp.batch.exception",null));
		}

		BigDecimal ftaxPoint = commodityBatch.getFtaxPoint();
		if (null != ftaxPoint) {
			if (BigDecimalUtils.ltZero(ftaxPoint)) {
				throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.add.sp.batch.tax.error",null));
			}
			if (ftaxPoint.compareTo(new BigDecimal("100")) == 1) {
				throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.add.sp.batch.tax.exception",null));
			}
			ftaxPoint = BigDecimalUtils.multiply(ftaxPoint, new BigDecimal("0.01"));
			commodityBatch.setFtaxPoint(ftaxPoint);
		}

		//计算过期日期
		Date dproduceDate = commodityBatch.getDproduceDate();   //生产日期
		String scommodityId = commodityBatch.getScommodityId(); //商品信息对象
		long dexpiredDateTemp = dproduceDate.getTime();                             //保质期时间--毫秒
		if (null != dproduceDate && StringUtils.isNotBlank(scommodityId)) {
			CommodityInfo commodityInfo = commodityInfoDao.selectByPrimaryKey(scommodityId);
			if (null != commodityInfo) {
				Integer ilifeType = commodityInfo.getIlifeType(); //保质期类型10=天 20=月
				Integer ishelfLife = commodityInfo.getIshelfLife(); //保质期
				if (Integer.valueOf(10).equals(ilifeType) && null != ishelfLife) { //天
					dexpiredDateTemp = dexpiredDateTemp + (long) ishelfLife * 24 * 3600 * 1000;
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(dexpiredDateTemp);
					Date dexpiredDate = c.getTime();
					commodityBatch.setDexpiredDate(dexpiredDate);
				} else {
					if (Integer.valueOf(20).equals(ilifeType) && null != ishelfLife) { //月    按30天计算
						dexpiredDateTemp = dexpiredDateTemp + (long) ishelfLife * 30 * 24 * 3600 * 1000;
						Calendar c = Calendar.getInstance();
						c.setTimeInMillis(dexpiredDateTemp);
						Date dexpiredDate = c.getTime();
						commodityBatch.setDexpiredDate(dexpiredDate);
					} else {    //添加商品没有填保质期字段
					}
				}

			}
		}

		if (commodityBatch.getIlossGoodsNum() == 0 || commodityBatch.getIlossGoodsNum() < 0) {
			commodityBatch.setIlossGoodsNum(0);		// 货损数量默认值：0
		}
		commodityBatch.setIshelfNum(0);			// 已上架数量默认值：0
		commodityBatch.setSbatchNo(sbatchNo);   //商品批次编号
		commodityBatch.setIstatus(10);      // 10启用 20禁用
		commodityBatch.setIstockStatus(10); // 10待上架 20部分上架 30全部上架
		commodityBatch.setIsaleStatus(30);  // 10销售中 20售罄 30待上架
		commodityBatch.setIdelFlag(0);/* 是否删除0否1是 */
		commodityBatch.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
		commodityBatch.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
		commodityBatch.setTupdateTime(DateUtils.getCurrentDateTime());	// 修改日期
		commodityBatch.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName()); // 修改人
		commodityBatchDao.insert(commodityBatch);
	}

	/**
	 * 自定义修改方法
	 * @param commodityBatch
	 */
	@Override
	public void updateBySelectiveVo(CommodityBatch commodityBatch) {
		Integer totalNum = commodityBatch.getIcommodityNum();
		Integer lossNum = commodityBatch.getIlossGoodsNum();
		if (totalNum != null && lossNum != null && (lossNum.compareTo(totalNum) == 1)) {
			throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.add.sp.batch.exception",null));
		}
		commodityBatchDao.updateByIdSelectiveVo(commodityBatch);
	}
}