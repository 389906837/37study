package com.cloud.cang.act.ac.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.FirstRewardIntegral;

/** 规则数据限制记录表
当券或积分有设计是  一次或每天几次 规则时，在该表中记录对应的规则流水，以便于统计(AC_FIRST_REWARD_INTEGRAL) **/
public interface FirstRewardIntegralDao extends GenericDao<FirstRewardIntegral, String> {


	/** codegen **/
}