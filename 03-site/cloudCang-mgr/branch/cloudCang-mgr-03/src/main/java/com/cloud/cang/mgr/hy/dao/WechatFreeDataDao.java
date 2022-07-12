package com.cloud.cang.mgr.hy.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.WechatFreeData;

/** 微信免密开通数据(HY_WECHAT_FREE_DATA) **/
public interface WechatFreeDataDao extends GenericDao<WechatFreeData, String> {

    /**
     * 查询免密签约数据
     * @param map contractCode 系统签约唯一标识 smerchantCode 商户编号
     * @return
     */
    WechatFreeData selectByContractCode(Map<String, Object> map);
    /**
     * 查询免密签约数据
     * @param map openId  smerchantCode 商户编号
     * @return
     */
    WechatFreeData selectByOpenId(Map<String, Object> map);
}