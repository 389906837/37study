package com.cloud.cang.open.api.cr.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_Cloud_Exception_ImgResul;
import com.cloud.cang.mq.message.Mq_ImgRec;
import com.cloud.cang.open.api.common.APIConstant;
import com.cloud.cang.open.api.common.SubCodeEnum;
import com.cloud.cang.open.api.common.utils.ImageUtils;
import com.cloud.cang.open.api.cr.service.RecognitionServerService;
import com.cloud.cang.open.api.cr.service.RecongitionService;
import com.cloud.cang.open.api.op.service.InterfaceAcceptService;
import com.cloud.cang.open.api.op.service.InterfaceAccountService;
import com.cloud.cang.open.api.op.service.TransferLogService;
import com.cloud.cang.open.api.sb.service.DeviceInfoService;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.model.request.*;
import com.cloud.cang.open.sdk.util.BaseSignature;
import com.cloud.cang.open.sdk.utils.EncryptUtils;
import com.cloud.cang.open.sdk.utils.IdGenerator;
import com.cloud.cang.open.sdk.utils.SortUtils;
import com.cloud.cang.openapi.CommonParam;
import com.cloud.cang.openapi.RecongitionVo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class RecongitionServiceImpl implements RecongitionService {
    private final static Logger logger = LoggerFactory.getLogger(RecongitionServiceImpl.class);
    @Autowired
    private ICached iCached;
    @Autowired
    private InterfaceAccountService interfaceAccountService;
    @Autowired
    private InterfaceAcceptService interfaceAcceptService;
    @Autowired
    private TransferLogService transferLogService;
    @Autowired
    RmqProducer rmqProducer;
    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    private RecognitionServerService recognitionServerService;

    //ImgRecognitionDto ??? method ??????
    public final static String[]  METHOD_ARRAY= {
                        "openDoorInventory",
                        "localGravityLayeredOpenDoorInventory",
                        "localGravityLayeredReplenOpenDoorInventory"};

    /**
     * ??????????????????
     * @param recongitionVo  ????????????
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> recongitionByImg(RecongitionVo<ImgRecognitionDto> recongitionVo)
            throws ServiceException, Exception {
         CommonParam commonParam = recongitionVo.getCommonParam();

        logger.info("?????????????????????????????????????????????????????? {}???????????????APPID???{}",
                recongitionVo.getBatchNo(), commonParam.getAppId());
        // ????????????
        saveImgRecognition(recongitionVo);

        // ????????????????????????????????????
        checkInterfaceAccount(recongitionVo);

        //======???????????????RecongitionVo????????????======//
        iCached.put(com.cloud.cang.openapi.APIConstant.RedisKey.IMG_RECOGNITIONVO + recongitionVo.getBatchNo(),
                JSONUtil.toJsonStr(recongitionVo));

        // MQ???????????????vis
        try {
            this.sendMqMessageToVis(recongitionVo);
        } catch (Exception e) {
            this.sendImgErrorToApi(recongitionVo);
            e.printStackTrace();
        }
        return builRecongitionReturn(recongitionVo,commonParam);
    }



    /**
     * ??????????????????
     * @param recongitionVo  ????????????
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> recongitionByImg_callBack(RecongitionVo<ImgRecognition> recongitionVo)
            throws ServiceException, Exception {
        CommonParam commonParam = recongitionVo.getCommonParam();

        logger.info("?????????????????????????????????????????????????????? {}???????????????APPID???{}",
                recongitionVo.getBatchNo(), commonParam.getAppId());
        // ????????????
        saveImgRecognition_callback(recongitionVo);

        // ????????????????????????????????????
        checkInterfaceAccount_callback(recongitionVo);

        //======???????????????RecongitionVo????????????======//
        iCached.put(com.cloud.cang.openapi.APIConstant.RedisKey.IMG_RECOGNITIONVO_CALLBACK + recongitionVo.getBatchNo(),
                JSONUtil.toJsonStr(recongitionVo));

        // MQ???????????????vis
        this.sendMqMessageToVis_thrid(recongitionVo, QueueEnum.IMG_MODEL_CALLBACK.getName());
        /*try {
            this.sendMqMessageToVis_thrid(recongitionVo);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return builRecongitionReturn_callback(recongitionVo,commonParam);
    }

    private void sendMqMessageToVis(RecongitionVo<ImgRecognitionDto> recongitionVo){
        ImgRecognitionDto imgRecognition = recongitionVo.getBizContent();
        ExecutorManager.getInstance().execute(() -> {

            List<Mq_ImgRec.ImgRecoItem> list = CollUtil.newArrayList();
            imgRecognition.getImageDetail().forEach((o)->{
                list.add(new Mq_ImgRec.ImgRecoItem(o.getCellid(),o.getImgBase64()));
            });

            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(imgRecognition.getDeviceId());
            if(!isExistReconServer(deviceInfo.getSrecognitionModelCode())){
                DingTalkUtils.sendText("open-api????????????, " +
                        "???????????????"+deviceInfo.getSrecognitionModelCode()+", ??????????????????vis??????");
                return;
            }
            Mq_ImgRec mq_imgRec = Mq_ImgRec.builder()
                    .batchNo(recongitionVo.getBatchNo())
                    .imgRecoItems(list)
                    .deviceId(imgRecognition.getDeviceId())
                    .userId(imgRecognition.getUserId())
                    .build();

            // ?????????????????????,??????mq???????????????????????????
            boolean flagDB = !ArrayUtil.contains(METHOD_ARRAY,imgRecognition.getMethod());
            RmqMessage message = RmqMessage.<Mq_ImgRec>builder()
                    .queueName(QueueEnum.IMG_MODEL.getName() + deviceInfo.getSrecognitionModelCode())
                    .message(mq_imgRec)
                    .flagDB(flagDB)
                    .build();
            rmqProducer.sendMessage(message);
        });
    }

    private void sendMqMessageToVis_thrid(RecongitionVo<ImgRecognition> recongitionVo, String queueName){
        logger.info("?????????????????????????????????????????????????????????{}", queueName);
        ImgRecognition imgRecognition = recongitionVo.getBizContent();
        if(!isExistReconServer(imgRecognition.getModelCode())){
            DingTalkUtils.sendText("open-api????????????, " +
                    "???????????????"+imgRecognition.getModelCode()+", ??????????????????vis??????");
            return;
        }
        ExecutorManager.getInstance().execute(() -> {
            try {
                List<Mq_ImgRec.ImgRecoItem> list = CollUtil.newArrayList();
                imgRecognition.getImageDetail().forEach((o)->{
                    list.add(new Mq_ImgRec.ImgRecoItem(o.getCellid(),o.getImgBase64()));
                });

                Mq_ImgRec mq_imgRec = Mq_ImgRec.builder()
                        .batchNo(recongitionVo.getBatchNo())
                        .imgRecoItems(list)
                        .deviceId(imgRecognition.getDeviceId())
                        .build();

                RmqMessage message = RmqMessage.<Mq_ImgRec>builder()
                .queueName(queueName + imgRecognition.getModelCode())
                .message(mq_imgRec)
                .flagDB(true)
                .build();
                rmqProducer.sendMessage(message);
            } catch (Exception e) {
                logger.error("?????????????????????,???????????????{}, ??????MQ????????????:{}", queueName, e);
                DingTalkUtils.sendText("?????????????????????,???????????????"+queueName+", ??????MQ????????????");
            }
        });
    }

    private void checkInterfaceAccount(RecongitionVo<ImgRecognitionDto> recongitionVo){
        int tollNum = 1;
        InterfaceInfo interfaceInfo = recongitionVo.getInterfaceInfo();
        if(interfaceInfo.getIpayWay().intValue() ==
                BizTypeDefinitionEnum.InterfaceTollWay.TOLL.getCode()){//????????????
            // ??????????????????
            if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()
                    || interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode()) {
                tollNum = recongitionVo.getBizContent().getImageDetail().size();
            } else if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
                tollNum = 0;
            }
            verifyAccountBalance(recongitionVo.getAppManage().getSuserId(), interfaceInfo.getScode(), tollNum);
        }
    }

    private void checkInterfaceAccount_callback(RecongitionVo<ImgRecognition> recongitionVo){
        int tollNum = 1;
        InterfaceInfo interfaceInfo = recongitionVo.getInterfaceInfo();
        if(interfaceInfo.getIpayWay().intValue() ==
                BizTypeDefinitionEnum.InterfaceTollWay.TOLL.getCode()){//????????????
            // ??????????????????
            if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()
                    || interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode()) {
                tollNum = recongitionVo.getBizContent().getImageDetail().size();
            } else if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
                tollNum = 0;
            }
            verifyAccountBalance(recongitionVo.getAppManage().getSuserId(), interfaceInfo.getScode(), tollNum);
        }
    }

    private Map<String,Object> builRecongitionReturn(RecongitionVo recongitionVo,CommonParam commonParam)
            throws CloudCangException {
        ImgRecognitionDto imgRecognition = (ImgRecognitionDto) recongitionVo.getBizContent();

        Map tempMap = MapUtil.builder()
                .put("code", "200")
                .put("msg", "success")
                .put("subCode", SubCodeEnum.SUCCESS.getCode())
                .put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg())
                .put("batchNo", recongitionVo.getBatchNo())
                .put("outBatchNo", imgRecognition.getOutBatchNo())
                .put("deviceId", imgRecognition.getDeviceId())
                .map();
        // ???????????????
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_bitmain_response", signContentTemp);

        // ??????
        tempMap.put("sign", BaseSignature.rsaSign(signContentTemp,
                recongitionVo.getAppManage().getSplatformKey(),
                commonParam.getCharset(),
                commonParam.getSignType()));

        tempMap.put("params", JSONObject.toJSONString(MapUtil.builder()
                .put("bizContent", imgRecognition).map()));

        // Map??????
        tempMap = SortUtils.sortMapByKey(tempMap);

        String backBody = JSONObject.toJSONString(MapUtil.builder().put("body",tempMap).map());
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);

        return MapUtil.<String,Object>builder()
                .put("backBody",backBody)
                .put("encryptBackBody",encryptBackBody)
                .map();
    }

    private Map<String,Object> builRecongitionReturn_callback(RecongitionVo recongitionVo,CommonParam commonParam)
            throws CloudCangException {
        ImgRecognition imgRecognition = (ImgRecognition) recongitionVo.getBizContent();
        Map tempMap = MapUtil.builder()
                .put("code", "200")
                .put("msg", "success")
                .put("subCode", SubCodeEnum.SUCCESS.getCode())
                .put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg())
                .put("batchNo", recongitionVo.getBatchNo())
                .put("outBatchNo", imgRecognition.getOutBatchNo())
                .put("deviceId", imgRecognition.getDeviceId())
                .map();

        if(!isExistReconServer(imgRecognition.getModelCode())){
            tempMap.put("code",-1000);
            tempMap.put("msg", "???????????????????????????");
        }

        // ???????????????
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_async_response", signContentTemp);

        // ??????
        tempMap.put("sign", BaseSignature.rsaSign(signContentTemp,
                recongitionVo.getAppManage().getSplatformKey(),
                commonParam.getCharset(),
                commonParam.getSignType()));

        tempMap.put("params", JSONObject.toJSONString(MapUtil.builder()
                .put("bizContent", imgRecognition).map()));

        // Map??????
        tempMap = SortUtils.sortMapByKey(tempMap);

        String backBody = JSONObject.toJSONString(MapUtil.builder().put("body",tempMap).map());
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);

        return MapUtil.<String,Object>builder()
                .put("backBody",backBody)
                .put("encryptBackBody",encryptBackBody)
                .map();
    }

    private boolean isExistReconServer(String modelCode){
        RecognitionServer entity = new RecognitionServer();
        entity.setSmodelCode(modelCode);
        entity.setIdelFlag(0);
        entity.setIauditStatus(20);
        List list = recognitionServerService.selectByEntityWhere(entity);
        if(list != null && list.size() >0){
            return true;
        }
        return false;
    }

    /**
     * ??????????????????????????????
     * @param recongitionVo ????????????
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> recongitionAccountQuery(RecongitionVo<BalanceModel> recongitionVo) throws ServiceException, Exception {
        CommonParam commonParam = recongitionVo.getCommonParam();
        logger.info("?????????????????????????????????????????????????????????????????????APPID???{}??????????????????{}", commonParam.getAppId(), commonParam);

        BalanceModel balanceModel = recongitionVo.getBizContent();
        if (null == balanceModel || StringUtil.isBlank(balanceModel.getInterfaceAction())) {
            throw new ServiceException("INVALID-PARAMETER");
        }

        Map paramMap = MapUtil.<String, Object>builder()
                .put("userId", recongitionVo.getAppManage().getSuserId())
                .put("interfaceAction", balanceModel.getInterfaceAction())
                .map();
        InterfaceAccount account = interfaceAccountService.selectByParamsMap(paramMap);
        if (null == account) {
            throw new ServiceException("NO-DATA");
        }

        //??????????????????
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("code", "200");
        tempMap.put("msg", "success");
        tempMap.put("subCode", SubCodeEnum.SUCCESS.getCode());
        tempMap.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
        tempMap.put("accountNo", account.getScode());
        tempMap.put("accountType", account.getIaccountType());
        tempMap.put("transferNum", account.getItransferNum() != null ? account.getItransferNum() : 0);
        tempMap.put("deductionNum", account.getIdeductionNum() != null ? account.getIdeductionNum() : 0);
        tempMap.put("balanceNum", account.getFbalance() != null ? account.getFbalance() : 0);

        if (account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode() ||
                account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_NUM_TOLL_TYPE.getCode() ||
                account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode())
            if (null != account.getIvalidityType() && account.getIvalidityType().intValue() == 20) {
                tempMap.put("expireDate", "--");
            } else if (null != account.getTendTime() && null != account.getIvalidityType() && account.getIvalidityType().intValue() == 10) {
                tempMap.put("expireDate", DateUtils.dateToString(account.getTendTime(), "yyyyMMddHHmmss"));
            }
        //1?????????????????????
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_getBalance_response", signContentTemp);
        //2????????????????????????
        String signTemp = BaseSignature.rsaSign(signContentTemp,
                recongitionVo.getAppManage().getSplatformKey(),
                commonParam.getCharset(), commonParam.getSignType());
        tempMap.put("sign", signTemp);

        tempMap = SortUtils.sortMapByKey(tempMap);//Map??????

        Map<String, Object> tempObj = new HashMap<String, Object>();
        tempObj.put("body", tempMap);

        String backBody = JSONObject.toJSONString(tempObj);//????????????
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//??????????????????
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);

        interfaceAcceptService.updateInterfaceAcceptBySuccess("", JSONObject.toJSONString(balanceModel),
                0, 0, recongitionVo.getBatchNo(), backBody, encryptBackBody, "", "",
                recongitionVo.getAppManage(), commonParam);

        transferLogService.addTransferLog(recongitionVo,
                "image_recognition_account_query",
                "??????????????????????????????????????????????????????????????????" + recongitionVo.getBatchNo());


        iCached.remove(recongitionVo.getBatchNo() + recongitionVo.getAppManage().getSappId() + "_interface_accept");
        iCached.remove(recongitionVo.getBatchNo() + APIConstant.RedisKey.INTERFACE_INFO);
        return returnMap;
    }

    /**
     * ????????????????????????????????????
     *
     * @param recongitionVo  ????????????
     */
    @Override
    public Map<String, Object> recongitionOrderQuery(RecongitionVo<QueryModel> recongitionVo) throws ServiceException, Exception {
        CommonParam commonParam = recongitionVo.getCommonParam();
        logger.info("???????????????????????????????????????????????????????????????APPID???{}??????????????????{}", commonParam.getAppId(), commonParam);

        // ????????????
        QueryModel query = recongitionVo.getBizContent();
        if (StringUtil.isBlank(query.getBatchNo()) && StringUtil.isBlank(query.getOutBatchNo())) {
            throw new ServiceException("INVALID-PARAMETER");
        }

        // ???????????????????????????
        Map paramMap = MapUtil.<String,Object>builder()
                .put("batchNo", query.getBatchNo())
                .put("outBatchNo", query.getOutBatchNo())
                .put("queryCondition", " and (B.SACTION = 'cloud.api.recognition.async' or B.SACTION = 'cloud.api.recognition.bitmain')")
                .map();
        InterfaceAccept interfaceAccept = interfaceAcceptService.selectByMap(paramMap);
        if (null == interfaceAccept || StringUtil.isBlank(interfaceAccept.getSresponseEncryData())) {
            throw new ServiceException("NO-DATA");
        }

        String rootNode = commonParam.getMethodName().replace('.', '_') + "_response";
        String backBody = interfaceAccept.getSresponseData().replace("cloud_api_recognition_bitmain_response", rootNode)
                                                            .replace("cloud_api_recognition_async_response", rootNode);
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//??????????????????

        Map returnMap = MapUtil.<String,Object>builder()
                .put("backBody", backBody)
                .put("encryptBackBody", encryptBackBody)
                .map();

        interfaceAcceptService.updateInterfaceAcceptBySuccess("", JSONObject.toJSONString(query),
                0, 0, recongitionVo.getBatchNo(), backBody, encryptBackBody, "", "",
                recongitionVo.getAppManage(), commonParam);


        transferLogService.addTransferLog(recongitionVo,"image_recognition_order_query",
                "??????????????????????????????????????????????????????????????????" + recongitionVo.getBatchNo());
        return returnMap;
    }
    /**
     * ??????????????????
     *
     * @param recongitionVo  ????????????
     * @throws ServiceException
     * @throws Exception
     */
    @Override
    public boolean recongitionSynchronizeByImg(RecongitionVo recongitionVo) throws ServiceException, Exception {
        CommonParam commonParam = recongitionVo.getCommonParam();

        logger.info("?????????????????????????????????????????????????????? {}???????????????APPID???{}",
                recongitionVo.getBatchNo(), commonParam.getAppId());
        // ????????????
        saveImgRecognition_callback(recongitionVo);

        // ????????????????????????????????????
        checkInterfaceAccount_callback(recongitionVo);

        //======???????????????RecongitionVo????????????======//
        iCached.put(com.cloud.cang.openapi.APIConstant.RedisKey.IMG_RECOGNITIONVO_SYNCHRONIZE + recongitionVo.getBatchNo(),
                JSONUtil.toJsonStr(recongitionVo));

        // MQ???????????????vis
        this.sendMqMessageToVis_thrid(recongitionVo, QueueEnum.IMG_MODEL_SYNCHRONIZE.getName());

        return true;
    }

    /**
     * ??????????????????
     *
     * @param userId        ??????ID
     * @param interfaceCode ????????????
     * @param tollNum       ????????????
     * @return
     */
    private InterfaceAccount verifyAccountBalance(String userId, String interfaceCode, int tollNum) throws ServiceException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("interfaceCode", interfaceCode);
        InterfaceAccount account = interfaceAccountService.selectByMap(paramMap);
        if (null == account) {
            throw new ServiceException("INTERFACE-ACCOUNT-NOT-EXIST");
        }
        //?????? ????????????????????????
        account = interfaceAccountService.selectByPrimaryKeySync(account.getId());
        if (null == account.getIfreezeNum()) {
            account.setIfreezeNum(0);
        }

        if (account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.NUM_TOLL_TYPE.getCode()
                || account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()) {
            //????????????
            if(account.getIisUnlimitedNumber() == null || account.getIisUnlimitedNumber() == 1){
                if (account.getFbalance().intValue() < tollNum) {
                    throw new ServiceException("BALANCE-NOT-ENOUGH");
                }
                //????????????????????????
                account.setIfreezeNum(account.getIfreezeNum() + tollNum);
            }
        } else if (account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
            //???????????????
            if (account.getIvalidityType().intValue() == BizTypeDefinitionEnum.ValidityType.FIXED_DATE.getCode()
                    && account.getTendTime().before(new Date())) {
                throw new ServiceException("BALANCE-NOT-ENOUGH");
            }
        } else if (account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_NUM_TOLL_TYPE.getCode()
                || account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode()) {
            //???????????????
            if (account.getIvalidityType().intValue() == BizTypeDefinitionEnum.ValidityType.FIXED_DATE.getCode()
                    && account.getTendTime().before(new Date())) {
                //????????????
                if (account.getItransferNum().intValue() < tollNum) {
                    throw new ServiceException("BALANCE-NOT-ENOUGH");
                }
                //????????????????????????
                account.setIfreezeNum(account.getIfreezeNum() + tollNum);
            }
        }
        return account;
    }

    /***
     * ???????????????????????????
     * @param recongitionVo
     * @return
     * @throws ServiceException
     */
    private List<String> saveImgRecognition(RecongitionVo recongitionVo) throws ServiceException {
        long beginTime = System.currentTimeMillis();
        ImgRecognitionDto imgRecognition = (ImgRecognitionDto)recongitionVo.getBizContent();
        InterfaceInfo interfaceInfo = recongitionVo.getInterfaceInfo();
        String batchNo = recongitionVo.getBatchNo();
        List<String> imgPaths = new ArrayList<>();
        if (null == imgRecognition) {
            throw new ServiceException("INVALID-PARAMETER");
        }
        if (StringUtil.isBlank(imgRecognition.getOutBatchNo())) {
            throw new ServiceException("INVALID-PARAMETER-OUT-BATCH-NO");
        }
        InterfaceAccept accept = interfaceAcceptService.selectByOutBatchNo(interfaceInfo.getScode(), imgRecognition.getOutBatchNo());
        if (null != accept) {
            throw new ServiceException("INVALID-PARAMETER-OUT-BATCH-NO-EXIST");
        }
        List<ImageDetail> imgs = imgRecognition.getImageDetail();
        if (null == imgs || imgs.size() <= 0) {
            throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
        }
        int maxNum = StringUtil.toNumber(BizParaUtil.get("recognition_image_max_num"), 4);
        if (imgs.size() > maxNum) {
            throw new ServiceException("INVALID-PARAMETER-IMAGE-MAX");
        }
        //????????????????????????????????? ?????????????????????+??????yyyyMMdd+????????????.
        String filePathType = SysParaUtil.getValue("recognition_image_file_path_type");//?????????????????????
        String prefix = EvnUtils.getValue("upload.image.path.prefix");//?????????????????????
        String sharePrefix = EvnUtils.getValue("image.path.share.prefix");//???????????????????????????
        String fileName = "";//?????????????????????
        String tempFileName = "";//???????????????????????????
        String extName = "";//????????????????????????
        int imgType = -1;//1 base64 2 ????????????
        for (ImageDetail img : imgs) {
            if (StringUtil.isBlank(img.getCellid())) {
                throw new ServiceException("INVALID-PARAMETER-RECOGNITION-CELL-EMPTY");
            }
            if (StringUtil.isBlank(img.getImgBase64()) && StringUtil.isBlank(img.getImgUrl())) {
                throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
            }
            try {
                fileName = "";
                extName = "";
                if (StringUtil.isNotBlank(img.getImgBase64())) {
                    tempFileName = IdGenerator.randomUUID(32);
                    extName = StringUtil.isBlank(img.getImgFormat()) ? "jpg" : img.getImgFormat();
                    fileName = tempFileName + "." + extName;
                    imgType = 1;
                } else if (StringUtil.isNotBlank(img.getImgUrl())) {
                    tempFileName = img.getImgUrl().substring(img.getImgUrl().lastIndexOf("/") + 1);
                    extName = tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
                    fileName = IdGenerator.randomUUID(32) + "." + extName;
                    imgType = 2;
                }
                if (StringUtil.isBlank(fileName) || StringUtil.isBlank(extName)) {
                    logger.error("?????????????????????????????????");
                    throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
                }
                if (!extName.equalsIgnoreCase("jpg") && !extName.equalsIgnoreCase("jpeg")) {
                    logger.error("??????????????????????????????????????????????????????{}", batchNo);
                    throw new ServiceException("INVALID-PARAMETER-IMAGE-FORMAT-ERROR");
                }
            } catch (Exception e) {
                logger.error("???????????????????????????????????????????????????{}, ???????????????{}", batchNo, e);
                throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
            }
            //?????????????????????????????????
            String folder = DateUtils.getCurrentDTimeNumber();
            if (filePathType.equals("windows")) {
                String uploadFilePath = prefix + "\\" + folder + "\\" + batchNo;
                File tempfile = new File(uploadFilePath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                String uploadPath = prefix + "\\" + folder + "\\" + batchNo + "\\" + fileName;//??????????????????
                String serverPath = sharePrefix + "/" + folder + "/" + batchNo + "/" + fileName;//??????????????????
                saveImage(imgType, img.getImgBase64(), img.getImgUrl(), uploadPath, batchNo);
                imgPaths.add(serverPath);
                if (imgType == 1) {
                    img.setImgBase64(serverPath);//??????base64 ?????????????????????
                }
                img.setUploadUrl(uploadPath);
            } else {//linux ???????????????nfs??????????????????
                String uploadFilePath = prefix + "/" + folder + "/" + batchNo;
                File tempfile = new File(uploadFilePath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                String serverPath = prefix + "/" + folder + "/" + batchNo + "/" + fileName;//??????????????????
                saveImage(imgType, img.getImgBase64(), img.getImgUrl(), serverPath, batchNo);

                imgPaths.add(serverPath.replace(prefix, sharePrefix));
                if (imgType == 1) {
                    img.setImgBase64(serverPath);//??????base64 ?????????????????????
                }
                img.setUploadUrl(serverPath);
            }
        }
        imgRecognition.setImageDetail(imgs);

        recongitionVo.setBizContent(imgRecognition);
        long endTime = System.currentTimeMillis();
        logger.info("??????????????????:{}ms",(endTime-beginTime));
        return imgPaths;
    }

    /***
     * ???????????????????????????
     * @param recongitionVo
     * @return
     * @throws ServiceException
     */
    private List<String> saveImgRecognition_callback(RecongitionVo<ImgRecognition> recongitionVo) throws ServiceException {
        long beginTime = System.currentTimeMillis();
        ImgRecognition imgRecognition = (ImgRecognition)recongitionVo.getBizContent();
        InterfaceInfo interfaceInfo = recongitionVo.getInterfaceInfo();
        String batchNo = recongitionVo.getBatchNo();
        List<String> imgPaths = new ArrayList<>();
        if (null == imgRecognition) {
            throw new ServiceException("INVALID-PARAMETER");
        }
        if (StringUtil.isBlank(imgRecognition.getOutBatchNo())) {
            throw new ServiceException("INVALID-PARAMETER-OUT-BATCH-NO");
        }
        InterfaceAccept accept = interfaceAcceptService.selectByOutBatchNo(interfaceInfo.getScode(), imgRecognition.getOutBatchNo());
        if (null != accept) {
            throw new ServiceException("INVALID-PARAMETER-OUT-BATCH-NO-EXIST");
        }
        List<ImageDetail> imgs = imgRecognition.getImageDetail();
        if (null == imgs || imgs.size() <= 0) {
            throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
        }
        int maxNum = StringUtil.toNumber(BizParaUtil.get("recognition_image_max_num"), 4);
        if (imgs.size() > maxNum) {
            throw new ServiceException("INVALID-PARAMETER-IMAGE-MAX");
        }
        //????????????????????????????????? ?????????????????????+??????yyyyMMdd+????????????.
        String filePathType = SysParaUtil.getValue("recognition_image_file_path_type");//?????????????????????
        String prefix = EvnUtils.getValue("upload.image.path.prefix");//?????????????????????
        String sharePrefix = EvnUtils.getValue("image.path.share.prefix");//???????????????????????????
        String fileName = "";//?????????????????????
        String tempFileName = "";//???????????????????????????
        String extName = "";//????????????????????????
        int imgType = -1;//1 base64 2 ????????????
        for (ImageDetail img : imgs) {
            if (StringUtil.isBlank(img.getCellid())) {
                throw new ServiceException("INVALID-PARAMETER-RECOGNITION-CELL-EMPTY");
            }
            if (StringUtil.isBlank(img.getImgBase64()) && StringUtil.isBlank(img.getImgUrl())) {
                throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
            }
            try {
                fileName = "";
                extName = "";
                if (StringUtil.isNotBlank(img.getImgBase64())) {
                    tempFileName = IdGenerator.randomUUID(32);
                    extName = StringUtil.isBlank(img.getImgFormat()) ? "jpg" : img.getImgFormat();
                    fileName = tempFileName + "." + extName;
                    imgType = 1;
                } else if (StringUtil.isNotBlank(img.getImgUrl())) {
                    tempFileName = img.getImgUrl().substring(img.getImgUrl().lastIndexOf("/") + 1);
                    extName = tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
                    fileName = IdGenerator.randomUUID(32) + "." + extName;
                    imgType = 2;
                }
                if (StringUtil.isBlank(fileName) || StringUtil.isBlank(extName)) {
                    logger.error("?????????????????????????????????");
                    throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
                }
                if (!extName.equalsIgnoreCase("jpg") && !extName.equalsIgnoreCase("jpeg")) {
                    logger.error("??????????????????????????????????????????????????????{}", batchNo);
                    throw new ServiceException("INVALID-PARAMETER-IMAGE-FORMAT-ERROR");
                }
            } catch (Exception e) {
                logger.error("???????????????????????????????????????????????????{}, ???????????????{}", batchNo, e);
                throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
            }
            //?????????????????????????????????
            String folder = DateUtils.getCurrentDTimeNumber();
            if (filePathType.equals("windows")) {
                String uploadFilePath = prefix + "\\" + folder + "\\" + batchNo;
                File tempfile = new File(uploadFilePath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                String uploadPath = prefix + "\\" + folder + "\\" + batchNo + "\\" + fileName;//??????????????????
                String serverPath = sharePrefix + "/" + folder + "/" + batchNo + "/" + fileName;//??????????????????
                saveImage(imgType, img.getImgBase64(), img.getImgUrl(), uploadPath, batchNo);
                imgPaths.add(serverPath);
                if (imgType == 1) {
                    img.setImgBase64(serverPath);//??????base64 ?????????????????????
                }
                img.setUploadUrl(uploadPath);
            } else {//linux ???????????????nfs??????????????????
                String uploadFilePath = prefix + "/" + folder + "/" + batchNo;
                File tempfile = new File(uploadFilePath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                String serverPath = prefix + "/" + folder + "/" + batchNo + "/" + fileName;//??????????????????
                saveImage(imgType, img.getImgBase64(), img.getImgUrl(), serverPath, batchNo);

                imgPaths.add(serverPath.replace(prefix, sharePrefix));
                if (imgType == 1) {
                    img.setImgBase64(serverPath);//??????base64 ?????????????????????
                }
                img.setUploadUrl(serverPath);
            }
        }
        imgRecognition.setImageDetail(imgs);

        recongitionVo.setBizContent(imgRecognition);
        long endTime = System.currentTimeMillis();
        logger.info("??????????????????:{}ms",(endTime-beginTime));
        return imgPaths;
    }
    /**
     * ????????????
     *
     * @param imgType    ???????????? 1 base64
     * @param fileBase64 ??????base64??????
     * @param imgUrl     ????????????
     * @param serverPath ???????????????????????????
     * @param batchNo    ????????????
     * @throws ServiceException
     */
    private void saveImage(int imgType, String fileBase64, String imgUrl, String serverPath, String batchNo) throws ServiceException {
        if (imgType == 1) {
            boolean flag = ImageUtils.string2Image(fileBase64, serverPath);
            if (!flag) {
                logger.error("base64???????????????????????????????????????{}, ???????????????{}", batchNo, serverPath);
                throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
            }
        } else if (imgType == 2) {
            ImageUtils.downloadImage(imgUrl, serverPath);
        }
    }

    private boolean isSendMq(String methodStr){
        // ????????????????????????
        String[]  METHOD_ARRAY= {
                "openDoorInventory",
                "localGravityLayeredOpenDoorInventory",
                "localGravityLayeredReplenOpenDoorInventory"};

        return !ArrayUtil.contains(METHOD_ARRAY,methodStr);
    }

    private void sendImgErrorToApi(RecongitionVo<ImgRecognitionDto> recongitionVo){
        ImgRecognitionDto imgRecognitionDto = recongitionVo.getBizContent();
        String methodStr = imgRecognitionDto.getMethod();
        if(this.isSendMq(methodStr)){
            int method = 20;
            if(StrUtil.equals("openDoor",methodStr)
                    || StrUtil.equals("openDoorCheck",methodStr)){
                method = 10;
            }
            Mq_Cloud_Exception_ImgResul errorImg = Mq_Cloud_Exception_ImgResul.builder()
                    .deviceId(imgRecognitionDto.getDeviceId())
                    .key(imgRecognitionDto.getKey())
                    .openDoorType(Integer.valueOf(imgRecognitionDto.getUserType()))
                    .methodType(method)
                    .userId(imgRecognitionDto.getUserId())
                    .build();
            // ???????????????MQ?????????api
            RmqMessage build = RmqMessage.<Mq_Cloud_Exception_ImgResul>builder()
                    .queueName(QueueEnum.IMG_ERROR.getName())
                    .message(errorImg)
                    .build();
            // ??????????????????
            rmqProducer.sendMessage(build);
        }
    }
}