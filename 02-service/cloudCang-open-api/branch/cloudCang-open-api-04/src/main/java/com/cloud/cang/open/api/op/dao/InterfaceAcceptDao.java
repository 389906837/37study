package com.cloud.cang.open.api.op.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.InterfaceAccept;

/** 平台接口业务受理信息表(OP_INTERFACE_ACCEPT) **/
public interface InterfaceAcceptDao extends GenericDao<InterfaceAccept, String> {

    /***
     * 获取接口受理信息
     * @param paramMap 查询参数
     * @return
     */
    InterfaceAccept selectByMap(Map<String, Object> paramMap);

    /***
     * 获取商户业务订单信息
     * @param paramMap 查询参数
     * @return
     */
    InterfaceAccept selectByParamMap(Map<String, Object> paramMap);
}