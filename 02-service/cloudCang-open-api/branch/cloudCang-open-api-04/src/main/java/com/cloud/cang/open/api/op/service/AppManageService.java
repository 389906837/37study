package com.cloud.cang.open.api.op.service;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.generic.GenericService;

public interface AppManageService extends GenericService<AppManage, String> {


    /**
     * 获取应用信息
     * @param appId 应用ID
     * @return
     */
    AppManage selectAppManageByAppId(String appId);
    /**
     * 验证商户APPID 是否有效
     * @param appId 商户appId
     * @return 0 代表成功 -1 失败
     * @throws ServiceException
     */
    AppManage verifyAppId(String appId) throws ServiceException;
}