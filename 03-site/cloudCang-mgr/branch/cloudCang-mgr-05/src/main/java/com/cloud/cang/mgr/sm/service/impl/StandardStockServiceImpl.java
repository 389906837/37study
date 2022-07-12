package com.cloud.cang.mgr.sm.service.impl;

import java.util.Date;

import com.cloud.cang.mgr.sm.service.StandardDetailService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.model.sm.StandardDetail;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.mgr.sm.dao.StandardStockDao;
import com.cloud.cang.model.sm.StandardStock;
import com.cloud.cang.mgr.sm.service.StandardStockService;

import javax.servlet.http.HttpServletRequest;

@Service
public class StandardStockServiceImpl extends GenericServiceImpl<StandardStock, String> implements
		StandardStockService {

	@Autowired
	StandardStockDao standardStockDao;
	@Autowired
	private StandardDetailService standardDetailService;
	@Autowired
	private CommodityInfoService commodityInfoService;
	@Override
	public GenericDao<StandardStock, String> getDao() {
		return standardStockDao;
	}

	/***
	 * 更新标准库存数据
	 * @param standardStock 标准库存数据
	 * @param ilayerNum 设备层数
	 *@param request  @throws ServiceException
	 */
	@Override
	public boolean updateStandardStock(StandardStock standardStock, Integer ilayerNum, HttpServletRequest request) throws ServiceException {
		//更新标准库存状态
		if (StringUtil.isNotBlank(standardStock.getId())) {//编辑
			standardStock.setTupdateTime(new Date());
			standardStock.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			this.updateBySelective(standardStock);
		} else {//新增
			standardStock.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			standardStock.setTupdateTime(new Date());
			standardStock.setTaddTime(new Date());
			standardStock.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			this.insert(standardStock);
		}
		//删除设备原有的标准库存数据
		standardDetailService.deleteByDeviceId(standardStock.getSdeviceId());
		StandardDetail standardDetail = null;
		String commodityIds = "";
		String arr[] = null;
		String temp = "";
		Integer stockNumTemp = null;
		CommodityInfo commodityInfo = null;
		for (int i = 1;i<=ilayerNum.intValue();i++) {
			commodityIds = request.getParameter("commodityIds_"+i);
			if (StringUtil.isNotBlank(commodityIds) && commodityIds.length()>0) {//说明有商品
				arr = commodityIds.split(",");
				if (null != arr && arr.length > 0) {
					for (int j = 0; j < arr.length; j++) {
						commodityInfo = commodityInfoService.selectByPrimaryKey(arr[j]);
						if (null != commodityInfo) {
							standardDetail = new StandardDetail();
							standardDetail.setScommodityId(commodityInfo.getId());
							standardDetail.setScommodityCode(commodityInfo.getScode());
							//获取商品数据
							stockNumTemp = 0;
							temp = request.getParameter("standardStock_" + arr[j] + "_" + i);
							if (StringUtil.isNotBlank(temp)) {
								try {
									stockNumTemp = Integer.parseInt(temp);
								} catch (Exception e) {
									stockNumTemp = 0;
								}
							}
							if (stockNumTemp < 0) {
								stockNumTemp = 0;
							}
							standardDetail.setIstandardStock(stockNumTemp);//标准库存
							temp = request.getParameter("minSillValue_" + arr[j] + "_" + i);
							if (StringUtil.isNotBlank(temp)) {
								try {
									stockNumTemp = Integer.parseInt(temp);
								} catch (Exception e) {
									stockNumTemp = 0;
								}
							}
							if (stockNumTemp < 0) {
								stockNumTemp = 0;
							}
							//最低阈值
							if (stockNumTemp.intValue() >= standardDetail.getIstandardStock().intValue()) {
								if (standardDetail.getIstandardStock().intValue() > 0) {
									standardDetail.setIminSillValue(standardDetail.getIstandardStock().intValue() - 1);
								} else {
									standardDetail.setIminSillValue(0);
								}
							} else {
								standardDetail.setIminSillValue(stockNumTemp);
							}
							standardDetail.setSdeviceCode(standardStock.getSdeviceCode());
							standardDetail.setSdeviceId(standardStock.getSdeviceId());
							standardDetail.setIlayerNum(i);
							//新增标准库存明细
							standardDetailService.insert(standardDetail);
						}
					}
				}
			}
		}


		return false;
	}
}