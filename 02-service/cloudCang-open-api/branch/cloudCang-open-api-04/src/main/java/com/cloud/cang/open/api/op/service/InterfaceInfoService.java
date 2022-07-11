package com.cloud.cang.open.api.op.service;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.generic.GenericService;

public interface InterfaceInfoService extends GenericService<InterfaceInfo, String> {

    /**
     * 验证接口权限
     * @param methodName 接口名称
     * @param userId 用户ID
     * @return
     * @throws ServiceException
     */
    InterfaceInfo verifyInterfaceAuth(String methodName, String userId) throws ServiceException;
}