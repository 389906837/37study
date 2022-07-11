package com.cloud.cang.bzc.ac.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.DiscountDetail;

/** 活动优惠信息明细表(AC_DISCOUNT_DETAIL) **/
public interface DiscountDetailDao extends GenericDao<DiscountDetail, String> {


	/** codegen **/


    DiscountDetail selectBySacCode(String sconfCode);
}