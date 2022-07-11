package com.cloud.cang.open.api.mq.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
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
import com.cloud.cang.model.*;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_Cloud_ImgResul;
import com.cloud.cang.mq.message.Mq_Cloud_Weight_ImgResul;
import com.cloud.cang.mq.message.Mq_ImgResult;
import com.cloud.cang.open.api.common.APIConstant;
import com.cloud.cang.open.api.common.SubCodeEnum;
import com.cloud.cang.open.api.op.service.InterfaceAcceptService;
import com.cloud.cang.open.api.op.service.InterfaceAccountService;
import com.cloud.cang.open.api.op.service.TransferLogService;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.model.request.ImgRecognitionDto;
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
public class ImgResultMessageHandler implements RmqMessageHandler<Mq_ImgResult> {
    private static final Logger logger = LoggerFactory.getLogger(ImgResultMessageHandler.class);
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
            RecongitionVo<ImgRecognitionDto> recongitionVo = this.getRecongitionVo(imgResult);
            // 业务处理
            this.processBiz(imgResult,recongitionVo);

            //todo 测试代码=====================
            if(iCached.get("test_cang_"+recongitionVo.getBatchNo()) !=null ){
                long startTime = (long)iCached.get("test_cang_"+recongitionVo.getBatchNo());
                logger.info("测试代码-识别耗时："+ (System.currentTimeMillis() - startTime));
                iCached.remove("test_cang_"+recongitionVo.getBatchNo());
                return HandlerEnum.ACK;
            }
            //todo 测试代码=====================

            // 识别结果发送MQ给api
            int deviceType = Integer.valueOf(recongitionVo.getBizContent().getDeviceType());
            if(ImgRecognitionDto.DeviceType.vision.getType() == deviceType){
                // 50 = 纯视觉
                this.sendImgResultToApi(rmqMessage, imgResult, recongitionVo);
            }else if(ImgRecognitionDto.DeviceType.vision_weigh.getType() == deviceType){
                // 60 = 视觉+称重
                this.sendImgWeightResultToApi(rmqMessage, imgResult, recongitionVo);
            }else{
                logger.error("open-api处理识别结果失败, 设备类型参数异常,deviceType:"+deviceType);
                DingTalkUtils.sendText("open-api处理识别结果失败, 设备类型参数异常,deviceType:"+deviceType);
            }

            return HandlerEnum.ACK;
        }catch (Exception e){
            e.printStackTrace();
            DingTalkUtils.sendText("open-api处理识别结果失败, 消息ID:"+rmqMessage.getId());
        }
        // 业务完成之后删除缓存
        try {
            iCached.remove(com.cloud.cang.openapi.APIConstant.RedisKey.IMG_RECOGNITIONVO + imgResult.getBatchNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HandlerEnum.ACK_BIZ_FAIL;
    }


    @Override
    public boolean matchHandler(String queueName) {
        if(queueName.contains(QueueEnum.IMG_RESULT.getName())) {
            return true;
        }
        return false;
    }

    /**
     * 更新接口账户——费调用次数、调用次数、余额次数
     * @param imgResult
     * @param recongitionVo
     * @throws Exception
     */
    private void updateInterfaceAccount(Mq_ImgResult imgResult,
                                        RecongitionVo<ImgRecognitionDto> recongitionVo)throws Exception{
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
                           RecongitionVo<ImgRecognitionDto> recongitionVo)throws Exception{
        try {
            CommonParam commonParam = recongitionVo.getCommonParam();
            AppManage appManage = recongitionVo.getAppManage();
            InterfaceInfo interfaceInfo = recongitionVo.getInterfaceInfo();
            ImgRecognitionDto imgRecognition = recongitionVo.getBizContent();

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
    private RecongitionVo<ImgRecognitionDto> getRecongitionVo(Mq_ImgResult imgResult) {
        try {
            Object object = iCached.get(com.cloud.cang.openapi.APIConstant.RedisKey.IMG_RECOGNITIONVO + imgResult.getBatchNo());
            if (object != null) {
                RecongitionVo recongitionVo =
                        JSONUtil.toBean(object.toString(), RecongitionVo.class);

                ImgRecognitionDto imgRecognition =
                        JSONUtil.toBean((cn.hutool.json.JSONObject) recongitionVo.getBizContent(),
                                ImgRecognitionDto.class);

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
    private String buildBackBody(Mq_ImgResult imgResult,RecongitionVo<ImgRecognitionDto> recongitionVo) throws CloudCangException {
        ImgRecognitionDto imgRecognition = recongitionVo.getBizContent();
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

        // 待签名字段
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_bitmain_response", signContentTemp);

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
                                     RecongitionVo<ImgRecognitionDto> recongitionVo) throws CloudCangException {
        CommonParam commonParam = recongitionVo.getCommonParam();
        AppManage appManage = recongitionVo.getAppManage();
        ImgRecognitionDto imgRecognition = recongitionVo.getBizContent();

        List<ImgResultDetail> detailList = buildImgResultDetailList(imgResult);
        Map<String, String> tempMap = MapUtil.<String,String>builder()
                .put("appId", appManage.getSappId())
                .put("appSecretKey", appManage.getSappSecretKey())
                .put("code", "200")
                .put("msg", "success")
                .put("subCode", SubCodeEnum.SUCCESS.getCode())
                .put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg())
                .put("batchNo", recongitionVo.getBatchNo())
                .put("methodName", commonParam.getMethodName())
                .put("outBatchNo", imgRecognition.getOutBatchNo())
                .put("imgResultDetail", JSONObject.toJSONString(detailList))
                .put("deviceId", imgRecognition.getDeviceId()).map();

        //1、参数签名字段
        String signContentTemp = JSONObject.toJSONString(tempMap);

        //2、对参数进行签名
        String signTemp = BaseSignature.rsaSign(signContentTemp, appManage.getSplatformKey(),
                commonParam.getCharset(), commonParam.getSignType());

        tempMap.put("sign", signTemp);

        String callbackBody = JSONObject.toJSONString(tempMap);//返回参数
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

    /**
     * 纯视觉
     * 识别结果发送给api
     * @param imgResult
     * @param recongitionVo
     */
    private void sendImgResultToApi(RmqMessage<Mq_ImgResult> rmqMessage,
                                    Mq_ImgResult imgResult,
                             RecongitionVo<ImgRecognitionDto> recongitionVo) throws Exception{
        // 构建List<ImgResultDetail>、List<Mq_Cloud_ImgResul.Mq_Cloud_Img>
        List<ImgResultDetail> imgResultDetails = CollUtil.newArrayList();
        List<Mq_Cloud_ImgResul.Mq_Cloud_Img> imageUrls = CollUtil.newArrayList();

        imgResult.getMsg().forEach( imgResultItem ->{
            // List<ImgResultDetail>
            ImgResultDetail resultDetail = new ImgResultDetail();
            resultDetail.setCellid(imgResultItem.getCameraCode());
            resultDetail.setStatus(imgResultItem.getStatus());
            List<GoodDetail> goodDetailList = CollUtil.newArrayList();
            if(CollUtil.isNotEmpty(imgResultItem.getGoods())){
                imgResultItem.getGoods().forEach((goods -> {
                    GoodDetail goodDetail = new GoodDetail();
                    goodDetail.setVrCode(goods.getSvrCode());
                    goodDetail.setNumber(String.valueOf(goods.getNumber()));
                    goodDetailList.add(goodDetail);
                }));
            }
            resultDetail.setGoodDetail(goodDetailList);
            imgResultDetails.add(resultDetail);

            // List<Mq_Cloud_ImgResul.Mq_Cloud_Img>
            Mq_Cloud_ImgResul.Mq_Cloud_Img imgItem = new Mq_Cloud_ImgResul.Mq_Cloud_Img();
            imgItem.setCameraCode(imgResultItem.getCameraCode());
            imgItem.setImageUrl(imgResultItem.getImageUrl());
            imageUrls.add(imgItem);
        });

        // 构建Mq_Cloud_ImgResul
        ImgRecognitionDto imgRecognitionDto = recongitionVo.getBizContent();
        logger.info("纯视觉识别, ImgRecognitionDto (method={}) ",imgRecognitionDto.getMethod());
        Mq_Cloud_ImgResul mqMessage = Mq_Cloud_ImgResul.builder()
                .deviceId(imgRecognitionDto.getDeviceId())
                .key(imgRecognitionDto.getKey())
                .openDoorType(Integer.valueOf(imgRecognitionDto.getUserType()))
                .type(this.methodType(imgRecognitionDto))
                .userId(imgRecognitionDto.getUserId())
                .imgResultDetail(JSONUtil.toJsonStr(imgResultDetails))
                .picUrlList(JSONUtil.toJsonStr(imageUrls))
                .uniquelyIdentifies(imgRecognitionDto.getUniquelyIdentifies())
                .success(true)
                .build();

        RmqMessage<Mq_Cloud_ImgResul> build = RmqMessage.<Mq_Cloud_ImgResul>builder()
                .queueName(QueueEnum.IMG_OPENAPI_RESULT.getName())
                .message(mqMessage)
                .flagDB(rmqMessage.isFlagDB())
                .build();

        // 发送消息
        rmqProducer.sendMessage(build);
    }


    /**
     * 视觉+称重
     * 识别结果发送给api
     * @param imgResult
     * @param recongitionVo
     */
    private void sendImgWeightResultToApi(RmqMessage<Mq_ImgResult> rmqMessage,
                                          Mq_ImgResult imgResult,
                                    RecongitionVo<ImgRecognitionDto> recongitionVo) throws Exception{

        ImgRecognitionDto imgRecognitionDto = recongitionVo.getBizContent();
        logger.info("视觉+称重识别, ImgRecognitionDto (method={}) ",imgRecognitionDto.getMethod());
        Mq_Cloud_Weight_ImgResul weight = Mq_Cloud_Weight_ImgResul.builder()
                .deviceId(imgRecognitionDto.getDeviceId())
                .userId(imgRecognitionDto.getUserId())
                .openSource(20)
                .success(true)
                .openDoorType(Integer.valueOf(imgRecognitionDto.getUserType()))
                .uniquelyIdentifies(imgRecognitionDto.getUniquelyIdentifies())
                .type(this.methodType(imgRecognitionDto))
                .build();

        List<Mq_Cloud_Weight_ImgResul.Mq_Cloud_Img> imageUrls = CollUtil.newArrayList();

        //====补货关门: 设置 com.cloud.cang.model.Goods
        if(StrUtil.equals("20",imgRecognitionDto.getUserType())
                && (StrUtil.equals("closeDoor",imgRecognitionDto.getMethod()) ||
                 StrUtil.equals("closeDoor_inventory",imgRecognitionDto.getMethod()))){
            Goods goods = new Goods();
            goods.setOpenDoorType(imgRecognitionDto.getUserType());
            goods.setTotalWeight(imgRecognitionDto.getTotalWeight());
            List<TagModel> goodsList = CollUtil.newArrayList();
            imgResult.getMsg().forEach( imgResultItem ->{
                if(CollUtil.isNotEmpty(imgResultItem.getGoods())){
                    if(CollUtil.isNotEmpty(imgResultItem.getGoods())){
                        imgResultItem.getGoods().forEach(g->{
                            TagModel tagModel = new TagModel();
                            tagModel.setSvrCode(g.getSvrCode());
                            tagModel.setNumber(g.getNumber());
                            goodsList.add(tagModel);
                        });
                    }
                }

                // List<Mq_Cloud_Weight_ImgResul.Mq_Cloud_Img>
                Mq_Cloud_Weight_ImgResul.Mq_Cloud_Img imgItem = new Mq_Cloud_Weight_ImgResul.Mq_Cloud_Img();
                imgItem.setCameraCode(imgResultItem.getCameraCode());
                imgItem.setImageUrl(imgResultItem.getImageUrl());
                imageUrls.add(imgItem);
            });
            goods.setGoodsList(goodsList);
            weight.setGoods(JSONUtil.toJsonStr(goods));
            weight.setPicUrlList(JSONUtil.toJsonStr(imageUrls));
        //====其他：设置 com.cloud.cang.model.ShopLayeredGoods
        }else{
            ShopLayeredGoods shopLayeredGoods = new ShopLayeredGoods();
            shopLayeredGoods.setOpenDoorType(imgRecognitionDto.getUserType());

            // 设备视觉分层集合
            List<LayeredGoods> layeredGoodsList = CollUtil.newArrayList();
            imgResult.getMsg().forEach( imgResultItem -> {
                LayeredGoods layeredGoods = new LayeredGoods();
                layeredGoods.setCameraIp(imgResultItem.getCameraCode());
                List<TagModel> goodsList = CollUtil.newArrayList();
                if(CollUtil.isNotEmpty(imgResultItem.getGoods())){
                    imgResultItem.getGoods().forEach(goods -> {
                        TagModel tagModel = new TagModel();
                        tagModel.setNumber(goods.getNumber());
                        tagModel.setSvrCode(goods.getSvrCode());
                        goodsList.add(tagModel);
                    });
                }
                layeredGoods.setGoodsList(goodsList);
                layeredGoodsList.add(layeredGoods);
                // List<Mq_Cloud_Weight_ImgResul.Mq_Cloud_Img>
                Mq_Cloud_Weight_ImgResul.Mq_Cloud_Img imgItem = new Mq_Cloud_Weight_ImgResul.Mq_Cloud_Img();
                imgItem.setCameraCode(imgResultItem.getCameraCode());
                imgItem.setImageUrl(imgResultItem.getImageUrl());
                imageUrls.add(imgItem);
            });
            shopLayeredGoods.setLayeredGoodsList(layeredGoodsList);

            // 设备重力分层集合
            List<LayeredWeight> layeredWeightList = CollUtil.newArrayList();
            if(CollUtil.isNotEmpty(imgRecognitionDto.getLayeredWeightList())){
                imgRecognitionDto.getLayeredWeightList().forEach(layered -> {
                    LayeredWeight layeredWeight = new LayeredWeight();
                    layeredWeight.setLayeredWeight(layered.getLayeredWeight());
                    layeredWeight.setWeightIp(layered.getWeightIp());
                    layeredWeightList.add(layeredWeight);
                });
            }
            shopLayeredGoods.setLayeredWeightList(layeredWeightList);
            weight.setShopLayeredGoods(JSONUtil.toJsonStr(shopLayeredGoods));
            weight.setPicUrlList(JSONUtil.toJsonStr(imageUrls));
        }

        RmqMessage build = RmqMessage.<Mq_Cloud_Weight_ImgResul>builder()
                .queueName(QueueEnum.IMG_OPENAPI_WEIGHT_RESULT.getName())
                .message(weight)
                .flagDB(rmqMessage.isFlagDB())
                .build();

        // 发送消息
        rmqProducer.sendMessage(build);
    }

    private int methodType(ImgRecognitionDto imgRecognitionDto){
        int type = 10;// 10 开门 20 实时 30 关门
        switch (imgRecognitionDto.getMethod()){
            // openDoor=开门
            // openDoorCheck=开门校验
            // openDoorInventory=实时盘货
            // closeDoor=关门

            //==== 视觉+称重
            // closeDoor_inventory=关门盘货
            // localGravityLayeredOpenDoorInventory=购物实时
            // localGravityLayeredReplenOpenDoorInventory=补货实时
            // local_gravity_layered_close_door=称重分层 关门盘货
            case "openDoor":
            case "openDoorCheck":
                type = 10;
                break;
            case "openDoorInventory":
            case "localGravityLayeredOpenDoorInventory":
            case "localGravityLayeredReplenOpenDoorInventory":
                type = 20;
                break;
            case "closeDoor":
            case "closeDoor_inventory":
            case "local_gravity_layered_close_door":
                type = 30;
                break;
        }
        return type;
    }
}

