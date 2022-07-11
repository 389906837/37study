package com.cloud.cang.open.api.op.service;

import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.open.api.common.SubCodeEnum;

import java.util.Map;

public interface InterfaceAcceptService extends GenericService<InterfaceAccept, String> {


    /**
     * 新增业务受理记录
     *
     * @param batchNo       业务编号
     * @param params        参数
     * @param app           应用信息
     * @param interfaceInfo 接口信息
     * @param bizContent    请求参数
     * @param clientIp      客户端IP
     */
    void syncAddInterfaceAcceptRecord(String batchNo, Map<String, String> params, AppManage app, InterfaceInfo interfaceInfo, String bizContent, String clientIp);

    /**
     * 接口处理错误 更新业务受理记录
     *
     * @param batchNo       业务编号
     * @param app           应用信息
     * @param interfaceInfo
     * @param subCodeEnum   错误类型
     * @param params        请求参数
     */
    void updateInterfaceAcceptByFailed(String batchNo, AppManage app, InterfaceInfo interfaceInfo, SubCodeEnum subCodeEnum, Map<String, String> params);

    /**
     * 接口处理成功 更新业务受理记录
     *
     * @param outBatchNo          商户业务编号
     * @param requestData         请求参数
     * @param imgSize             识别图片数量
     * @param successNum          成功识别数量
     * @param batchNo             业务编号
     * @param backBody            返回参数
     * @param encryptBackBody     返回加密参数
     * @param callbackBody        回调参数
     * @param encryptCallbackBody 回调加密参数
     * @param app                 应用信息
     * @param params              请求参数
     */
    void updateInterfaceAcceptBySuccess(String outBatchNo, String requestData, Integer imgSize, Integer successNum, String batchNo, String backBody, String encryptBackBody, String callbackBody, String encryptCallbackBody, AppManage app, Map<String, String> params);

    /**
     * 判断第三方业务编号是否存在
     *
     * @param interfaceCode 接口编号
     * @param outBatchNo    第三方业务编号
     * @return
     */
    InterfaceAccept selectByOutBatchNo(String interfaceCode, String outBatchNo);

    /***
     * 获取商户业务订单信息
     * @param paramMap 查询参数
     * @return
     */
    InterfaceAccept selectByMap(Map<String, Object> paramMap);

    /**
     * 接口回调成功 更新业务受理记录
     *
     * @param batchNo
     * @param appid
     */
    void updateInterfaceAcceptByCallbackSuccess(String batchNo, String appid);

    /**
     * 接口回调失败 更新业务受理记录
     *
     * @param batchNo
     * @param appid
     */
    void updateInterfaceAcceptByCallbackFaild(String batchNo, String appid);

    /**
     * 接口处理错误 更新业务受理记录
     * @param batchNo       业务编号
     * @param app           应用信息
     * @param interfaceInfo
     * @param subCodeEnum   错误类型
     * @param params        请求参数
     */
    void updateInterfaceAcceptByFailedToWeb(String batchNo, AppManage app, InterfaceInfo interfaceInfo, SubCodeEnum subCodeEnum, Map<String, String> params);
}