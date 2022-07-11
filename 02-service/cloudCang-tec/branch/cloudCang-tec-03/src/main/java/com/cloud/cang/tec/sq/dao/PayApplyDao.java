package com.cloud.cang.tec.sq.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sq.PayApply;

/** 付款申请(SQ_PAY_APPLY) **/
public interface PayApplyDao extends GenericDao<PayApply, String> {


    /**
     * 更新付款申请信息
     * @param pmap
     */
    void updateStatusById(Map<String, Object> pmap);
}