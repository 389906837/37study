package com.cloud.cang.wap.ac.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.DiscountRecord;

/** 活动优惠记录表(AC_DISCOUNT_RECORD) **/
public interface DiscountRecordDao extends GenericDao<DiscountRecord, String> {
    /**
     * 获取订单优惠信息
     * @param params 查询参数
     */
    DiscountRecord selectDiscountRecordByMap(Map<String, Object> params);

}