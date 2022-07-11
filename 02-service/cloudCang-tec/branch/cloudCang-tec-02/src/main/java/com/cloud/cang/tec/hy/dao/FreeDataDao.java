package com.cloud.cang.tec.hy.dao;


import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.FreeData;

/** 支付宝免密开通数据(HY_FREE_DATA) **/
public interface FreeDataDao extends GenericDao<FreeData, String> {

    /**
     * 获取支付宝免密数据
     * @param map 查询参数
     * @return
     */
    FreeData selectByExternalAgreementNo(Map<String, Object> map);
    /**
     * 获取支付宝免密数据
     * @param map 查询参数
     * @return
     */
    FreeData selectByMemberId(Map<String, Object> map);
}