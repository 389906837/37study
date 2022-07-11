package com.cloud.cang.open.api.ws;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.open.api.common.APIConstant;
import com.cloud.cang.open.api.common.BaseService;
import com.cloud.cang.open.api.common.SubCodeEnum;
import com.cloud.cang.open.api.common.utils.IPUtils;
import com.cloud.cang.open.api.cr.service.RecongitionService;
import com.cloud.cang.openapi.RecongitionVo;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 提供给open-sdk调用
 *
 * >图片视觉识别
 * >视觉识别订单查询接口
 * >视觉识别账户信息查询
 */
@RestController
@RequestMapping("/vis")
public class EyeOpenGatewayService extends BaseService {
    private final static Logger logger = LoggerFactory.getLogger(EyeOpenGatewayService.class);
    @Autowired
    private RecongitionService recongitionService;
    @Autowired
    private ICached iCached;
    @Autowired
    private BaseService baseService;

    @RequestMapping(value = "/gateway")
    public void gateway(HttpServletRequest request, HttpServletResponse response) {
        logger.info("open-SDK接收请求时间：{}ms",System.currentTimeMillis());
        RecongitionVo recongitionVo = null;
        SubCodeEnum subCodeEnum = SubCodeEnum.SYSTEM_ERROR;
        String batchNo = CoreUtils.newCode("op_interface_accept");
        try {
            long beginTime = System.currentTimeMillis();
            // 校验请求参数
            recongitionVo = this.verifyCommonParam(batchNo, request, response);
            if (null == recongitionVo){//老版请求跳转到老版服务
                return;
            }
            // 执行业务处理
            this.processBiz(batchNo,recongitionVo,response);
            long endTime = System.currentTimeMillis();
            logger.info("open-SDK请求处理耗时:{}ms",(endTime-beginTime));
        } catch (ServiceException e) {
            if(recongitionVo!= null ){
                logger.error("接口业务处理异常, 业务批次：{} 错误编码: {}", recongitionVo.getBatchNo(), e.getMessage());
                subCodeEnum = SubCodeEnum.getSubCodeEnum(e.getMessage());
                if (null == subCodeEnum) {
                    subCodeEnum = SubCodeEnum.UNKNOWN_ERROR;
                }
                // 错误信息返回客户端
                super.realTimeBackcallError(recongitionVo, subCodeEnum, response);
                // 错误日志记录
                super.failOperation(recongitionVo,subCodeEnum);
            }else{
                logger.error("接口业务处理异常, 错误编码: {}", e.getMessage());
                subCodeEnum = SubCodeEnum.getSubCodeEnum(e.getMessage());
                if (null == subCodeEnum) {
                    subCodeEnum = SubCodeEnum.UNKNOWN_ERROR;
                }
                // 错误信息返回客户端
                super.realTimeBackcallError(batchNo, subCodeEnum, response);
                // 错误日志记录
                String clientIp = IPUtils.getIpFromRequest(request);
                super.failOperation(batchNo, clientIp, subCodeEnum);
                DingTalkUtils.sendText(subCodeEnum.getReturnMsg());
            }
        } catch (Exception e) {
            logger.error("接口处理异常：{}", e);
            // 错误信息返回客户端
            super.realTimeBackcallError(batchNo, subCodeEnum, response);
            // 错误日志记录
            String clientIp = IPUtils.getIpFromRequest(request);
            super.failOperation(batchNo, clientIp, subCodeEnum);
        } finally {
            try {
                iCached.remove("platform_key_" + batchNo);
                iCached.remove("common_param_" + batchNo);
                iCached.remove("app_manage_" + batchNo);
                iCached.remove("interface_info_" + batchNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private  void processBiz(String batchNo, RecongitionVo recongitionVo, HttpServletResponse response) throws ServiceException,Exception {
        Map<String, Object> returnMap = null;
        boolean flag = false;
        switch (recongitionVo.getCommonParam().getMethodName()){
            case APIConstant.Recognition.RECOGNITION:
                returnMap = recongitionService.recongitionByImg(recongitionVo);
                break;
            case APIConstant.Recognition.RECOGNITION_SYNCHRONIZE:
                flag = recongitionService.recongitionSynchronizeByImg(recongitionVo);
                break;
            case APIConstant.Recognition.RECOGNITION_CALLBACK:
                returnMap = recongitionService.recongitionByImg_callBack(recongitionVo);
                break;
            case APIConstant.Recognition.QUERY:
                returnMap = recongitionService.recongitionOrderQuery(recongitionVo);
                break;
            case APIConstant.Recognition.BALANCE:
                returnMap = recongitionService.recongitionAccountQuery(recongitionVo);
                break;
        }
        //同步接口
        if (recongitionVo.getCommonParam().getMethodName().equals(APIConstant.Recognition.RECOGNITION_SYNCHRONIZE)) {
            if (flag) {
                waitRecongition(batchNo,recongitionVo, response);
            } else {
                throw new ServiceException("UNKNOWN-ERROR");
            }
        } else {
            realTimeBackcallSuccess(returnMap, response);
        }
    }

    private void waitRecongition(String batchNo, RecongitionVo recongitionVo, HttpServletResponse response) {
        //新建线程等待返回
       /* ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {*/
                int time = 0;
                boolean flag = true;
                String encryptBackBody = "";
                String timeOutStr = SysParaUtil.getValue("cloud_sync_time_out");//同步接口请求超时时间
                int timeOut = 300;
                try {
                    timeOut = Integer.parseInt(timeOutStr);
                } catch (Exception e) {
                    timeOut = 300;
                }
                do {
                    try {
                        encryptBackBody = (String) iCached.get(com.cloud.cang.openapi.APIConstant.RedisKey.IMG_RECOGNITIONVO_THRID_SYNCHRONIZE_RESULT + batchNo);
                        if (StringUtil.isNotBlank(encryptBackBody) || time >= timeOut) {
                            flag = false;
                            //删除同步方法识别结果
                            iCached.remove(com.cloud.cang.openapi.APIConstant.RedisKey.IMG_RECOGNITIONVO_THRID_SYNCHRONIZE_RESULT + batchNo);
                            break;
                        }
                        time++;
                        Thread.sleep(200l);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (flag);
                try {
                    if (StringUtil.isNotBlank(encryptBackBody)) {
                        response.getWriter().write(encryptBackBody);
                    } else {
                        SubCodeEnum subCodeEnum = SubCodeEnum.RECOGNITION_SERVER_ERROR;
                        // 错误信息返回客户端
                        baseService.realTimeBackcallError(recongitionVo, subCodeEnum, response);
                        // 错误日志记录
                        baseService.failOperation(recongitionVo, subCodeEnum);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
          /*  }
        });*/
    }
}
