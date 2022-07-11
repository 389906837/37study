package com.cloud.cang.open.api.mq.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_ImgResult;
import com.cloud.cang.open.api.common.APIConstant;
import com.cloud.cang.open.api.common.SubCodeEnum;
import com.cloud.cang.open.api.op.service.InterfaceAcceptService;
import com.cloud.cang.open.api.op.service.InterfaceAccountService;
import com.cloud.cang.open.api.op.service.TransferLogService;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.model.request.ImgRecognition;
import com.cloud.cang.open.sdk.model.response.GoodDetail;
import com.cloud.cang.open.sdk.model.response.ImgResultDetail;
import com.cloud.cang.open.sdk.util.BaseSignature;
import com.cloud.cang.open.sdk.utils.EncryptUtils;
import com.cloud.cang.open.sdk.utils.SortUtils;
import com.cloud.cang.openapi.CommonParam;
import com.cloud.cang.openapi.RecongitionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: 37cang-云平台
 * @description: 消息处理类：处理图像识别结果消息
 * @author: qzg
 * @create: 2019-11-15 11:23
 **/
@Component
public class ImgResultCallBackMessageHandler implements RmqMessageHandler<Mq_ImgResult> {
    private static final Logger logger = LoggerFactory.getLogger(ImgResultCallBackMessageHandler.class);
    @Autowired
    private InterfaceAccountService interfaceAccountService;
    @Autowired
    private InterfaceAcceptService interfaceAcceptService;
    @Autowired
    private TransferLogService transferLogService;
    @Autowired
    private ICached iCached;
    @Autowired
    RmqProducer rmqProducer;


    @Override
    public HandlerEnum handler(RmqMessage<Mq_ImgResult> rmqMessage) {
        Mq_ImgResult imgResult = rmqMessage.getMessage();
        try{
            RecongitionVo<ImgRecognition> recongitionVo = this.getRecongitionVo(imgResult);
            // 业务处理
            this.processBiz(imgResult,recongitionVo);
            // 回调第三方，返回识别结果
            this.postNotifyUrl(imgResult,recongitionVo,rmqMessage);

            return HandlerEnum.ACK;
        }catch (Exception e){
            e.printStackTrace();
            DingTalkUtils.sendText("第三方open-api处理识别结果失败, 消息ID:"+rmqMessage.getId());
        }
        // 业务完成之后删除缓存
        try {
            iCached.remove(com.cloud.cang.openapi.APIConstant.RedisKey.IMG_RECOGNITIONVO_CALLBACK + imgResult.getBatchNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HandlerEnum.ACK_BIZ_FAIL;
    }


    @Override
    public boolean matchHandler(String queueName) {
        if(queueName.contains(QueueEnum.IMG_RESULT_CALLBACK.getName())) {
            return true;
        }
        return false;
    }

    private void postNotifyUrl(Mq_ImgResult imgResult,
                               RecongitionVo<ImgRecognition> recongitionVo,
                               RmqMessage<Mq_ImgResult> rmqMessage){
        CommonParam commonParam = recongitionVo.getCommonParam();
        try {
            String callbackBody = this.buildCallbackBody(imgResult, recongitionVo);
            String encryptCallbackBody = EncryptUtils.encryptStringUnZip(callbackBody);//返回加密参数
            Map param = MapUtil.builder().put("encryptCallbackBody",encryptCallbackBody).build();
            String str = HttpUtil.post(commonParam.getNotifyUrl(),param);
            logger.info(str);
        } catch (Exception e) {
            String msg = StrFormatter.format("识别图片, 回调第三方接口失败, 回调地址:{}, 消息ID:{}",
                    commonParam.getNotifyUrl(),
                    rmqMessage.getId());
            DingTalkUtils.sendText(msg);
            logger.error(msg);
            e.printStackTrace();
        }
    }

    /**
     * 更新接口账户——费调用次数、调用次数、余额次数
     * @param imgResult
     * @param recongitionVo
     * @throws Exception
     */
    private void updateInterfaceAccount(Mq_ImgResult imgResult,
                                        RecongitionVo<ImgRecognition> recongitionVo)throws Exception{
        ExecutorManager.getInstance().execute(() -> {
            AppManage appManage = recongitionVo.getAppManage();
            InterfaceInfo interfaceInfo = recongitionVo.getInterfaceInfo();
            if (interfaceInfo.getIpayWay().intValue() == BizTypeDefinitionEnum.InterfaceTollWay.TOLL.getCode()) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("userId", appManage.getSuserId());
                paramMap.put("interfaceCode", interfaceInfo.getScode());
                InterfaceAccount account = interfaceAccountService.selectByMap(paramMap);

                //更新接口账户信息
                int imgCount = imgResult.getMsg().size();
                InterfaceAccount accountUpdate = new InterfaceAccount();
                accountUpdate.setId(account.getId());
                accountUpdate.setIdeductionNum((account.getIdeductionNum() != null ?
                        account.getIdeductionNum() + imgCount : imgCount));
                accountUpdate.setItransferNum((account.getItransferNum() != null ?
                        account.getItransferNum() + imgCount : imgCount));
                if(account.getIisUnlimitedNumber() == 1){
                    accountUpdate.setFbalance(account.getFbalance() - imgCount);
                }
                accountUpdate.setUpdateTime(new Date());
                interfaceAccountService.updateBySelective(accountUpdate);
            }
        });
    }

    /**
     * 0, 若收费，则更新接口账户——费调用次数、调用次数、余额次数
     * 1, 接口处理成功 更新业务受理记录
     * 2, 新增接口调用日志记录
     * @param imgResult
     * @param recongitionVo
     */
    private void processBiz(Mq_ImgResult imgResult,
                           RecongitionVo<ImgRecognition> recongitionVo)throws Exception{
        try {
            CommonParam commonParam = recongitionVo.getCommonParam();
            AppManage appManage = recongitionVo.getAppManage();
            InterfaceInfo interfaceInfo = recongitionVo.getInterfaceInfo();
            ImgRecognition imgRecognition = recongitionVo.getBizContent();

            //====更新接口账户——费调用次数、调用次数、余额次数
            updateInterfaceAccount(imgResult,recongitionVo);

            //====接口处理成功 更新业务受理记录
            String backBody = this.buildBackBody(imgResult,recongitionVo);
            String callbackBody = this.buildCallbackBody(imgResult,recongitionVo);
            interfaceAcceptService.updateInterfaceAcceptBySuccess(
                    imgRecognition.getOutBatchNo(),
                    JSONUtil.toJsonStr(imgRecognition),
                    imgResult.getMsg().size(),
                    imgResult.getMsg().size(),
                    imgResult.getBatchNo(),
                    backBody,
                    EncryptUtils.encryptStringUnZip(backBody),
                    callbackBody,
                    EncryptUtils.encryptStringUnZip(callbackBody),
                    appManage,
                    commonParam);

            //====新增接口调用日志记录
            transferLogService.addTransferLog(
                    recongitionVo.getClientIp(),
                    imgResult.getBatchNo(),
                    appManage,
                    interfaceInfo,
                    APIConstant.BizLog.info,
                    APIConstant.BizOperType.IMAGE_RECOGNITION,
                    StrFormatter.format("图片视觉识别接口调用成功，业务编号：{}, 成功识别图片张数：{}",
                            imgResult.getBatchNo(),
                            imgResult.getMsg().size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从缓存中获取当前批次号的RecongitionVo<ImgRecognition></>
     * @param imgResult
     * @return
     */
    private RecongitionVo<ImgRecognition> getRecongitionVo(Mq_ImgResult imgResult) {
        try {
            Object object = iCached.get(com.cloud.cang.openapi.APIConstant.RedisKey.IMG_RECOGNITIONVO_CALLBACK + imgResult.getBatchNo());
            if (object != null) {
                RecongitionVo recongitionVo =
                        JSONUtil.toBean(object.toString(), RecongitionVo.class);

                ImgRecognition imgRecognition =
                        JSONUtil.toBean((cn.hutool.json.JSONObject) recongitionVo.getBizContent(),
                                ImgRecognition.class);

                recongitionVo.setBizContent(imgRecognition);
                return recongitionVo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  构建backBody
     * @param recongitionVo
     * @return
     */
    private String buildBackBody(Mq_ImgResult imgResult,RecongitionVo<ImgRecognition> recongitionVo) throws Exception {
        ImgRecognition imgRecognition = recongitionVo.getBizContent();
        CommonParam commonParam = recongitionVo.getCommonParam();

        List<ImgResultDetail> detailList = buildImgResultDetailList(imgResult);
        Map tempMap = MapUtil.builder()
                .put("code", "200")
                .put("msg", "success")
                .put("subCode", SubCodeEnum.SUCCESS.getCode())
                .put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg())
                .put("batchNo", recongitionVo.getBatchNo())
                .put("outBatchNo", imgRecognition.getOutBatchNo())
                .put("imgResultDetail", detailList)
                .put("deviceId", imgRecognition.getDeviceId())
                .map();

        if( imgResult.getStatus() != 200){
            tempMap.put("subCode", SubCodeEnum.RECOGNITION_SERVER_RESULT_ERROR.getCode());
            tempMap.put("subMsg", SubCodeEnum.RECOGNITION_SERVER_RESULT_ERROR.getReturnMsg());
        }
        // 待签名字段
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_async_response", signContentTemp);

        // 签名
        tempMap.put("sign", BaseSignature.rsaSign(signContentTemp,
                recongitionVo.getAppManage().getSplatformKey(),
                commonParam.getCharset(),
                commonParam.getSignType()));

        tempMap.put("params", JSONObject.toJSONString(MapUtil.builder()
                .put("bizContent", imgRecognition).map()));

        // Map排序
        tempMap = SortUtils.sortMapByKey(tempMap);

        String backBody = JSONObject.toJSONString(MapUtil.builder().put("body",tempMap).map());
        return backBody;
    }

    /**
     * 构建callbackBody
     * @param recongitionVo
     * @return
     */
    private String buildCallbackBody(Mq_ImgResult imgResult,
                                     RecongitionVo<ImgRecognition> recongitionVo) throws CloudCangException {
        CommonParam commonParam = recongitionVo.getCommonParam();
        AppManage appManage = recongitionVo.getAppManage();
        ImgRecognition imgRecognition = recongitionVo.getBizContent();

        List<ImgResultDetail> detailList = buildImgResultDetailList(imgResult);
        Map<String, Object> tempMap = MapUtil.<String,Object>builder()
                .put("appId", appManage.getSappId())
                .put("appSecretKey", appManage.getSappSecretKey())
                .put("code", "200")
                .put("msg", "success")
                .put("subCode", SubCodeEnum.SUCCESS.getCode())
                .put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg())
                .put("batchNo", recongitionVo.getBatchNo())
                .put("methodName", commonParam.getMethodName())
                .put("outBatchNo", imgRecognition.getOutBatchNo())
                .put("imgResultDetail",detailList)
                .put("deviceId", imgRecognition.getDeviceId())
                .map();

        if( imgResult.getStatus() != 200){
            tempMap.put("subCode", SubCodeEnum.RECOGNITION_SERVER_RESULT_ERROR.getCode());
            tempMap.put("subMsg", SubCodeEnum.RECOGNITION_SERVER_RESULT_ERROR.getReturnMsg());
        }

        //1、参数签名字段
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_async_response", signContentTemp);

        //2、对参数进行签名
        String signTemp = BaseSignature.rsaSign(signContentTemp, appManage.getSplatformKey(),
                commonParam.getCharset(), commonParam.getSignType());
        tempMap.put("sign", signTemp);

        HashMap<Object, Object> map = MapUtil.newHashMap();
        map.put("body",SortUtils.sortMapByKey(tempMap));

        String callbackBody = JSONObject.toJSONString(map);//返回参数
        return callbackBody;
    }

    /**
     * 构建List<ImgResultDetail>
     * @param imgResult
     * @return
     */
    private List<ImgResultDetail> buildImgResultDetailList(Mq_ImgResult imgResult){
        List<ImgResultDetail> resultDetail = CollUtil.newArrayList();
        imgResult.getMsg().forEach(o->{
            ImgResultDetail imgResultDetail = new ImgResultDetail();
            imgResultDetail.setCellid(o.getCameraCode());
            imgResultDetail.setStatus(o.getStatus());

            List<GoodDetail> goodDetails = CollUtil.newArrayList();
            if(CollUtil.isNotEmpty(o.getGoods())){
                o.getGoods().forEach(goods -> {
                    GoodDetail goodDetail = new GoodDetail();
                    goodDetail.setNumber(String.valueOf(goods.getNumber()));
                    goodDetail.setVrCode(goods.getSvrCode());
                    goodDetails.add(goodDetail);
                });
            }
            imgResultDetail.setGoodDetail(goodDetails);

            resultDetail.add(imgResultDetail);
        });
        return resultDetail;
    }
}

