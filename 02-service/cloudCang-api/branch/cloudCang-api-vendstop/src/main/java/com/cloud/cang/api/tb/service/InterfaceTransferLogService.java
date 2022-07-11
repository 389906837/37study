package com.cloud.cang.api.tb.service;

import com.cloud.cang.model.tb.InterfaceTransferLog;
import com.cloud.cang.generic.GenericService;

import java.util.Date;

public interface InterfaceTransferLogService extends GenericService<InterfaceTransferLog, String> {


    /**
     * 设备第三方接口调用记录
     *
     * @param deviceId         设备ID
     * @param deviceCode       设备编号
     * @param userId           用户ID
     * @param userType         用户类型 10 普通用户，20 管理员
     * @param thirdCode        第三方编号
     * @param thirdName        第三方名称
     * @param interfaceType    接口类型 10 请求接口，20 返回接口
     * @param interfaceAction  接口动作
     * @param interfaceName    接口名称
     * @param reqParams        请求参数
     * @param currentDateTime  请求时间
     * @param respParams       返回参数
     * @param currentDateTime1 响应时间
     * @param remarks          备注
     * @param currentDateTime2 添加时间
     */
    void insertLog(String deviceId, String deviceCode, String userId, Integer userType, String thirdCode, String thirdName, Integer interfaceType, String interfaceAction, String interfaceName, String reqParams, Date currentDateTime, String respParams, Date currentDateTime1, String remarks, Date currentDateTime2);

}