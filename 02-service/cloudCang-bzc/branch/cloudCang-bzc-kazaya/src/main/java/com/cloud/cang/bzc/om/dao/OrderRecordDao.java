package com.cloud.cang.bzc.om.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.OrderRecord;

/** 商品订单记录信息表(OM_ORDER_RECORD) **/
public interface OrderRecordDao extends GenericDao<OrderRecord, String> {

    /**
     * 根据订单编号查询订单
     */

    OrderRecord  selectByOrderCode (String sorderCode);
	/** codegen **/
}