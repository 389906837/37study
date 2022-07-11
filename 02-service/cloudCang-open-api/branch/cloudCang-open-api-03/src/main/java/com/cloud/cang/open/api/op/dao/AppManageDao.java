package com.cloud.cang.open.api.op.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.AppManage;

/** 平台应用管理信息表(OP_APP_MANAGE) **/
public interface AppManageDao extends GenericDao<AppManage, String> {
    /**
     * 获取应用信息
     * @param appId 应用ID
     * @return
     */
    AppManage selectAppManageByAppId(String appId);

}