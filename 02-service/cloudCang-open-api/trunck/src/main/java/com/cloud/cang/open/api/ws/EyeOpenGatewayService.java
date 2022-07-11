package com.cloud.cang.open.api.ws;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.open.api.common.ReturnCodeEnum;
import com.cloud.cang.open.api.common.SubCodeEnum;
import com.cloud.cang.open.api.common.utils.IPUtils;
import com.cloud.cang.open.api.common.utils.RequestUtil;
import com.cloud.cang.open.api.cr.service.RecongitionService;
import com.cloud.cang.open.api.op.service.InterfaceAcceptService;
import com.cloud.cang.open.api.op.service.TransferLogService;
import com.cloud.cang.open.sdk.util.BaseSignature;
import com.cloud.cang.open.sdk.utils.EncryptUtils;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 视觉开发服务
 */
@RestController
@RequestMapping("/")
@RegisterRestResource
public class EyeOpenGatewayService {

    private final static Logger logger = LoggerFactory.getLogger(EyeOpenGatewayService.class);

    @Autowired
    private RecongitionService recongitionService;
    @Autowired
    private ICached iCached;
    @Autowired
    private TransferLogService transferLogService;
    @Autowired
    private InterfaceAcceptService interfaceAcceptService;

    @RequestMapping(value = "gateway")
    public void gateway(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("收到接口网关请求数据，开始处理...");
        //1. 解析请求参数
        Map<String, String> params = RequestUtil.getRequestParams(request);
        /*String str = "{\"body\":{\"msg\":\"success\",\"code\":\"200\",\"subCode\":\"INVALID-SIGN\",\"error_response\":\"{\\\"msg\\\":\\\"success\\\",\\\"code\\\":\\\"200\\\",\\\"subCode\\\":\\\"INVALID-SIGN\\\",\\\"subMsg\\\":\\\"签名无效，检查请求参数，重新签名后重新发起请求!\\\"}\",\"sign\":\"EksB2Tj8GocbWpuHvGq7whWNfPsz/YA1eM/fUu8XcxPR1RI5GB8u+yJzH0x1FtZ7QtAERepNTYDMTabemHiEIwt8y7N4V/Uf6309PgCu5nTgPN5jre9c0pWUSeVVvQBApW8j/fDA4izbu+6heZ9Opp9tL0PPcZYPaTIHWdZs9VA=\",\"subMsg\":\"签名无效，检查请求参数，重新签名后重新发起请求!\"}}";
        response.getWriter().write(EncryptUtils.encryptStringUnZip(str));*/
        //生成处理业务编号
        String batchNo = CoreUtils.newCode("op_interface_accept");
        SubCodeEnum subCodeEnum = SubCodeEnum.SYSTEM_ERROR;
        try {
            String methodName = EncryptUtils.decryptStringUnZip(params.get("methodName"));
            String appId = EncryptUtils.decryptStringUnZip(params.get("appId"));
            Map<String, Object> returnMap = null;//返回参数
            if (StringUtil.isNotBlank(methodName) && methodName.equals("cloud.api.recognition")) {
                long stime = System.nanoTime();
                logger.info("商户调用图片视觉识别同步方法接口");
                returnMap = recongitionService.recongitionByImg(request, batchNo, appId, params);
                response.getWriter().write((String) returnMap.get("encryptBackBody"));
                long etime = System.nanoTime();
                logger.info("处理批次：{}, 调用识别服务时间：" + (etime - stime) / 1000 / 1000 + "毫秒", batchNo);
                return;
            } else if (StringUtil.isNotBlank(methodName) && methodName.equals("cloud.api.recognition.query")) {
                logger.info("商户调用图片视觉识别订单查询接口");
                returnMap = recongitionService.recongitionOrderQuery(request, batchNo, appId, params);
                response.getWriter().write((String) returnMap.get("encryptBackBody"));
                return;
            } else if (StringUtil.isNotBlank(methodName) && methodName.equals("cloud.api.recognition.getBalance")) {
                logger.info("商户调用图片视觉识别账户信息查询接口");
                returnMap = recongitionService.recongitionAccountQuery(request, batchNo, appId, params);
                response.getWriter().write((String) returnMap.get("encryptBackBody"));
                return;
            } else if (StringUtil.isNotBlank(methodName) && methodName.equals("cloud.api.recognition.async")) {
                logger.info("商户调用图片视觉识别异步方法接口");
                returnMap = recongitionService.recongitionAsyncByImg(request, batchNo, appId, params);
                response.getWriter().write((String) returnMap.get("encryptBackBody"));
                return;
            }

        } catch (ServiceException e) {
            logger.error("接口业务处理异常, 业务批次：{} 错误编码: {}", batchNo, e.getMessage());
            subCodeEnum = SubCodeEnum.getSubCodeEnum(e.getMessage());
            if (null == subCodeEnum) {
                subCodeEnum = SubCodeEnum.UNKNOWN_ERROR;
            }
            //返回错误信息
            realTimeBackcallError(batchNo, subCodeEnum, params, response);
        } catch (Exception e) {
            logger.error("接口处理异常：{}", e);
            subCodeEnum = SubCodeEnum.SYSTEM_ERROR;
            //返回错误信息
            realTimeBackcallError(batchNo, subCodeEnum, params, response);
        }

        AppManage app = (AppManage) iCached.get(batchNo + "_app_info");
        InterfaceInfo interfaceInfo = (InterfaceInfo) iCached.get(batchNo + "_interface_info");
        if (null != app && null != interfaceInfo) {
            //获取客户端IP
            String clientIp = IPUtils.getIpFromRequest(request);
            //新增接口调用错误日志
            transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 20, "image_recognition", subCodeEnum.getCode() + "##" + subCodeEnum.getReturnMsg());

            //更新接口业务受理信息表
            interfaceAcceptService.updateInterfaceAcceptByFailed(batchNo, app, interfaceInfo, subCodeEnum, params);
            iCached.remove(batchNo + app.getSappId() + "_interface_accept");
        }
        iCached.remove(batchNo + "_app_info");
        iCached.remove(batchNo + "_interface_info");
        String serverCode = (String) iCached.get("busy_gpu_server_batch_no_" + batchNo);
        if (StringUtil.isNotBlank(serverCode)) {
            iCached.remove("busy_gpu_server_" + serverCode);
            iCached.remove("busy_gpu_server_batch_no_" + batchNo);
        }

    }

    /**
     * 错误返回处理
     *
     * @param batchNo  处理业务编号
     * @param codeEnum 错误码
     * @param params   请求参数
     * @param response @throws Exception
     */
    private void realTimeBackcallError(String batchNo, SubCodeEnum codeEnum, Map<String, String> params, HttpServletResponse response) {
        try {
            String platformPrivateKey = "";
            AppManage app = (AppManage) iCached.get(batchNo + "_app_info");
            if (null != app) {
                platformPrivateKey = app.getSplatformKey();
            }
            if (StringUtil.isNotBlank(platformPrivateKey)) {
                Map<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.put("code", "200");
                tempMap.put("msg", "success");
                tempMap.put("subCode", codeEnum.getCode());
                tempMap.put("subMsg", codeEnum.getReturnMsg());
                sendBackResponse(tempMap, params, platformPrivateKey, response);
            } else {
                logger.error("获取商户平台私钥错误，业务编号：{}", batchNo);
            }
        } catch (Exception e) {
            logger.error("返回错误信息处理异常：{}", e);
        }
    }

    /**
     * 错误返回处理
     *
     * @param batchNo  处理业务编号
     * @param codeEnum 错误码
     * @param params   请求参数
     * @param response @throws Exception
     */
    private void realTimeBackcallError(String batchNo, ReturnCodeEnum codeEnum, Map<String, String> params, HttpServletResponse response) {
        try {
            String platformPrivateKey = "";
            AppManage app = (AppManage) iCached.get(batchNo + "_app_info");
            if (null != app) {
                platformPrivateKey = app.getSplatformKey();
            }
            if (StringUtil.isNotBlank(platformPrivateKey)) {
                Map<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.put("code", String.valueOf(codeEnum.getCode()));
                tempMap.put("msg", codeEnum.getReturnMsg());
                sendBackResponse(tempMap, params, platformPrivateKey, response);
            } else {
                logger.error("获取商户平台私钥错误，业务编号：{}", batchNo);
            }
        } catch (Exception e) {
            logger.error("返回错误信息处理异常：{}", e);
        }
    }

    /**
     * 发送返回消息
     *
     * @param tempMap            临时信息
     * @param params             请求参数
     * @param platformPrivateKey 商户平台私钥
     * @param response
     * @throws Exception
     */
    private void sendBackResponse(Map<String, Object> tempMap, Map<String, String> params, String platformPrivateKey, HttpServletResponse response) throws Exception {
        Map<String, Object> backMap = new HashMap<String, Object>();
        //1、参数签名字段
        String signContent = JSONObject.toJSONString(tempMap);
        tempMap.put("error_response", signContent);
        //2、对参数进行签名
        String sign = BaseSignature.rsaSign(signContent, platformPrivateKey, params.get("charset"), params.get("signType"));
        tempMap.put("sign", sign);
        backMap.put("body", tempMap);
        String backStr = JSONObject.toJSONString(backMap);
        //3、整体加密
        String encryptBackStr = EncryptUtils.encryptStringUnZip(backStr);
        logger.info("错误返回加密数据：{}", encryptBackStr);
        response.getWriter().write(encryptBackStr);
    }

}
