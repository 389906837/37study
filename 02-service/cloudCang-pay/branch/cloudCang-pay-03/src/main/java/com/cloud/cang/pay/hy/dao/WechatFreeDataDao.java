package com.cloud.cang.pay.hy.dao;

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

/**
 * 微信免密开通数据(HY_WECHAT_FREE_DATA)
 **/
public interface WechatFreeDataDao extends GenericDao<WechatFreeData, String> {

    /**
     * 获取微信免密数据
     *
     * @param map smemberId 会员信息 smerchantCode 商户编号
     * @return
     */
    WechatFreeData selectByMemberId(Map<String, Object> map);

    /**
     * 查询免密签约数据
     *
     * @param map contractCode 系统签约唯一标识 smerchantCode 商户编号
     * @return
     */
    WechatFreeData selectByContractCode(Map<String, Object> map);

    /**
     * 查询免密签约数据
     *
     * @param map outReuestNo 系统签约唯一标识 smerchantCode 商户编号
     * @return
     */
    WechatFreeData selectByOutReuestNo(Map<String, Object> map);

    /**
     * 获取微信免密数据
     *
     * @param map 会员信息
     * @return
     */
    WechatFreeData selectByMemberIdAndWithholdType(Map<String, Object> map);

    /**
     * 获取微信免密数据
     *
     * @param map
     * @return
     */
    WechatFreeData selectByOpenIdAndMchId(Map<String, Object> map);
}