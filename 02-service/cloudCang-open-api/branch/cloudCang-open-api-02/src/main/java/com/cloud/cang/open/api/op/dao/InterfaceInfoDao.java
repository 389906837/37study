package com.cloud.cang.open.api.op.dao;


import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.InterfaceInfo;

/** 平台接口信息管理表(OP_INTERFACE_INFO) **/
public interface InterfaceInfoDao extends GenericDao<InterfaceInfo, String> {
    /**
     * 根据查询参数获取接口账户
     * @param paramMap 查询参数
     * @return
     */
    InterfaceInfo selectByMap(Map<String, Object> paramMap);

}