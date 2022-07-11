package com.cloud.cang.open.api.op.service;

import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.model.op.TransferLog;
import com.cloud.cang.generic.GenericService;

public interface TransferLogService extends GenericService<TransferLog, String> {


    /**
     * 新增接口调用日志记录
     * @param clientIp 请求IP
     * @param batchNo 业务编号
     * @param app 应用信息
     * @param interfaceInfo 接口信息
     * @param itype 日志类型
     * @param operType 操作类型
     * @param content 操作内容
     */
    void addTransferLog(String clientIp, String batchNo, AppManage app, InterfaceInfo interfaceInfo, int itype, String operType, String content);
}