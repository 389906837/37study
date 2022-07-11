package com.cloud.cang.act.ac.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.UseRange;

/** 活动应用范围明细表(AC_USE_RANGE) **/
public interface UseRangeDao extends GenericDao<UseRange, String> {


    UseRange selectByAcId(String acId);
}