package com.cloud.cang.wap.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.wap.ac.vo.CommodityVo;

/** 商品信息表(SP_COMMODITY_INFO) **/
public interface CommodityInfoDao extends GenericDao<CommodityInfo, String> {


    List<CommodityVo> selectByCondition(Map<String, String> map);
}