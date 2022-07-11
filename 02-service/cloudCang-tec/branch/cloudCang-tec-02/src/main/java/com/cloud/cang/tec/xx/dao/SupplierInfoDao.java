package com.cloud.cang.tec.xx.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.xx.SupplierInfo;

/** 消息供应商信息表(XX_SUPPLIER_INFO) **/
public interface SupplierInfoDao extends GenericDao<SupplierInfo, String> {


    List<SupplierInfo> selectByMerchantId(String merchantId);
}