package com.cloud.cang.open.api.cr.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.eye.sdk.bean.*;
import com.cloud.cang.eye.sdk.socket.Connection;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.model.op.*;
import com.cloud.cang.open.api.common.SubCodeEnum;
import com.cloud.cang.open.api.common.utils.IPUtils;
import com.cloud.cang.open.api.common.utils.ImageUtils;
import com.cloud.cang.open.api.common.utils.TxtExport;
import com.cloud.cang.open.api.common.utils.VisualUtils;
import com.cloud.cang.open.api.cr.service.RecongitionService;
import com.cloud.cang.open.api.cr.service.ServerListService;
import com.cloud.cang.open.api.op.service.*;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.mapping.StringUtils;
import com.cloud.cang.open.sdk.model.request.BalanceModel;
import com.cloud.cang.open.sdk.model.request.ImageDetail;
import com.cloud.cang.open.sdk.model.request.ImgRecognition;
import com.cloud.cang.open.sdk.model.request.QueryModel;
import com.cloud.cang.open.sdk.model.response.GoodDetail;
import com.cloud.cang.open.sdk.model.response.ImgResultDetail;
import com.cloud.cang.open.sdk.util.BaseEncrypt;
import com.cloud.cang.open.sdk.util.BaseSignature;
import com.cloud.cang.open.sdk.util.WebUtils;
import com.cloud.cang.open.sdk.utils.EncryptUtils;
import com.cloud.cang.open.sdk.utils.IdGenerator;
import com.cloud.cang.open.sdk.utils.SortUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RecongitionServiceImpl implements RecongitionService {

    private final static Logger logger = LoggerFactory.getLogger(RecongitionServiceImpl.class);

    @Autowired
    private AppManageService appManageService;
    @Autowired
    private InterfaceInfoService interfaceInfoService;
    @Autowired
    private InterfaceAccountService interfaceAccountService;
    @Autowired
    private InterfaceAcceptService interfaceAcceptService;
    @Autowired
    private TransferLogService transferLogService;
    @Autowired
    private ServerListService serverListService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ICached iCached;

    /**
     * ?????????????????? ??????
     *
     * @param request
     * @param batchNo ??????????????????
     * @param appId   ????????????appId
     * @param params  ????????????
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> recongitionByImg(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("???????????????????????????????????????????????????????????????????????? {}???????????????APPID???{}", batchNo, appId);
        //logger.debug("????????????????????????????????????????????????,???????????????{}", params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String charset = params.get("charset");
        String signType = params.get("signType");
        //???????????????IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //????????????????????????
        ImgRecognition imgRecognition = JSONObject.parseObject(bizContent, ImgRecognition.class);
        long stime = System.nanoTime();
        List<String> imgPaths = verifyRecognitionParam(interfaceInfo.getScode(), request, batchNo, imgRecognition);//???????????? ????????????
        long etime = System.nanoTime();
        logger.info("???????????????{}, ?????????????????????" + (etime - stime) / 1000 / 1000 + "??????", batchNo);
        //??????????????????
        //??????????????????????????????
        boolean updateAccount = false;
        InterfaceAccount account = null;
        int tollNum = 1;
        if (interfaceInfo.getIpayWay().intValue() == BizTypeDefinitionEnum.InterfaceTollWay.TOLL.getCode()) {
            //??????????????????
            if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()
                    || interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode()) {
                tollNum = imgRecognition.getImageDetail().size();
            } else if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
                tollNum = 0;
            }
            account = verifyAccountBalance(app.getSuserId(), interfaceInfo.getScode(), tollNum);
            updateAccount = true;
        }
        String modelCode = params.get("modelCode");//??????????????????
        stime = System.nanoTime();
        //??????????????????
        Map<String, Object> recognitionResult = recognition(modelCode, imgPaths, batchNo, app, imgRecognition.getImageDetail(), imgRecognition.getDeviceId());
        etime = System.nanoTime();
        logger.info("???????????????{}, ???????????????" + (etime - stime) / 1000 / 1000 + "??????", batchNo);
        List<ImgResultDetail> resultDetail = (List<ImgResultDetail>) recognitionResult.get("imgResultDetail");

        //??????????????????
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("code", "200");
        tempMap.put("msg", "success");
        tempMap.put("subCode", SubCodeEnum.SUCCESS.getCode());
        tempMap.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
        tempMap.put("batchNo", batchNo);
        tempMap.put("outBatchNo", imgRecognition.getOutBatchNo());
        tempMap.put("imgResultDetail", resultDetail);
        tempMap.put("deviceId", imgRecognition.getDeviceId());
        //1?????????????????????
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_response", signContentTemp);
        //2????????????????????????
        String signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap.put("sign", signTemp);

        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("bizContent", imgRecognition);
        tempMap.put("params", JSONObject.toJSONString(temp));

        tempMap = SortUtils.sortMapByKey(tempMap);//Map??????

        Map<String, Object> tempObj = new HashMap<String, Object>();
        tempObj.put("body", tempMap);

        String backBody = JSONObject.toJSONString(tempObj);//????????????
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//??????????????????
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);

        //??????????????????
        Map<String, String> tempMap1 = new HashMap<String, String>();
        tempMap1.put("appId", app.getSappId());
        tempMap1.put("appSecretKey", app.getSappSecretKey());
        tempMap1.put("code", "200");
        tempMap1.put("msg", "success");
        tempMap1.put("subCode", SubCodeEnum.SUCCESS.getCode());
        tempMap1.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
        tempMap1.put("batchNo", batchNo);
        tempMap1.put("methodName", EncryptUtils.decryptStringUnZip(params.get("methodName")));
        tempMap1.put("outBatchNo", imgRecognition.getOutBatchNo());
        tempMap1.put("imgResultDetail", JSONObject.toJSONString(resultDetail));
        tempMap1.put("deviceId", imgRecognition.getDeviceId());

        //1?????????????????????
        signContentTemp = JSONObject.toJSONString(tempMap1);
        //2????????????????????????
        signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap1.put("sign", signTemp);

        String callbackBody = JSONObject.toJSONString(tempMap1);//????????????
        String encryptCallbackBody = EncryptUtils.encryptStringUnZip(callbackBody);//??????????????????

        returnMap.put("callbackBody", callbackBody);
        returnMap.put("encryptCallbackBody", encryptCallbackBody);


        Integer successNum = (Integer) recognitionResult.get("successNum");//????????????
        Integer deduction = 0;
        //1????????????????????????????????? > 0  ?????????????????????
        //if (successNum.intValue() > 0 && updateAccount) {
        //2????????????????????????????????? = ????????????????????????  ?????????????????????
        if (successNum.intValue() == imgRecognition.getImageDetail().size()) {
            deduction = successNum;
        }
        if (updateAccount) {
            //????????????????????????
            InterfaceAccount accountUpdate = new InterfaceAccount();
            accountUpdate.setId(account.getId());
            accountUpdate.setIdeductionNum((account.getIdeductionNum() != null ? account.getIdeductionNum() + deduction : deduction));
            accountUpdate.setItransferNum((account.getItransferNum() != null ? account.getItransferNum() + successNum : successNum));
            accountUpdate.setFbalance(account.getFbalance() - deduction);
            accountUpdate.setUpdateTime(new Date());
            interfaceAccountService.updateBySelective(accountUpdate);
        }
        //????????????????????????
        interfaceAcceptService.updateInterfaceAcceptBySuccess(imgRecognition.getOutBatchNo(), JSONObject.toJSONString(imgRecognition), imgRecognition.getImageDetail().size(), deduction, batchNo, backBody, encryptBackBody, callbackBody, encryptCallbackBody, app, params);
        transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition", "????????????????????????????????????????????????????????????" + batchNo + "??????????????????????????????" + successNum);

        iCached.remove(batchNo + app.getSappId() + "_interface_accept");
        iCached.remove(batchNo + "_app_info");
        iCached.remove(batchNo + "_interface_info");
        return returnMap;
    }

    /**
     * ?????????????????? ??????
     *
     * @param request
     * @param batchNo ??????????????????
     * @param appId   ????????????appId
     * @param params  ????????????
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    @Override
    public Map<String, Object> recongitionAsyncByImg(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("???????????????????????????????????????????????????????????????????????? {}???????????????APPID???{}", batchNo, appId);
        logger.debug("????????????????????????????????????????????????,???????????????{}", params);
        String charset = params.get("charset");
        String signType = params.get("signType");
        //???????????????IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //????????????????????????
        ImgRecognition imgRecognition = JSONObject.parseObject(bizContent, ImgRecognition.class);
        List<String> imgPaths = verifyRecognitionParam(interfaceInfo.getScode(), request, batchNo, imgRecognition);//???????????? ????????????
        //??????????????????
        //??????????????????????????????
        boolean updateAccount = false;
        InterfaceAccount account = null;
        int tollNum = 1;
        if (interfaceInfo.getIpayWay().intValue() == BizTypeDefinitionEnum.InterfaceTollWay.TOLL.getCode()) {
            //??????????????????
            if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()
                    || interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode()) {
                tollNum = imgRecognition.getImageDetail().size();
            } else if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
                tollNum = 0;
            }
            account = verifyAccountBalance(app.getSuserId(), interfaceInfo.getScode(), tollNum);
            updateAccount = true;
        }
        String modelCode = params.get("modelCode");//??????????????????
        String methodName = EncryptUtils.decryptStringUnZip(params.get("methodName"));
        return AsyncRecognition(modelCode, imgPaths, batchNo, app, imgRecognition.getImageDetail(), imgRecognition.getDeviceId(), charset, signType, imgRecognition, account, updateAccount, clientIp, interfaceInfo, methodName, params);
    }

    /**
     * ??????????????????   ??????
     *
     * @param modelCode   ????????????
     * @param imgPaths    ????????????????????????
     * @param batchNo     ????????????
     * @param app         ????????????
     * @param imageDetail ??????????????????
     * @param deviceId    ????????????
     * @return
     */
    private synchronized Map<String, Object> AsyncRecognition(String modelCode, List<String> imgPaths, String batchNo, AppManage app, List<ImageDetail> imageDetail, String deviceId, String charset, String signType, ImgRecognition imgRecognition, InterfaceAccount account, boolean updateAccount, String clientIp, InterfaceInfo interfaceInfo, String methodName, Map<String, String> params) throws ServiceException, CloudCangException {
        //??????????????????
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        //??????????????????
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("code", "200");
        tempMap.put("msg", "success");
        tempMap.put("subCode", SubCodeEnum.SUCCESS.getCode());
        tempMap.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
        //1?????????????????????
        final String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_async_response", signContentTemp);
        //2????????????????????????
        String signTemp = null;
        signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap.put("sign", signTemp);
        tempMap = SortUtils.sortMapByKey(tempMap);//Map??????
        Map<String, Object> tempObj = new HashMap<String, Object>();
        tempObj.put("body", tempMap);
        String backBody = JSONObject.toJSONString(tempObj);//????????????
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//??????????????????
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);


        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    String sip = IPUtils.getLocalAddress();
                    if (StringUtil.isBlank(sip)) {
                        throw new ServiceException("RECOGNITION-SERVER-ERROR");
                    }
                    paramMap.put("sip", sip);
                    paramMap.put("irunStatus", "30");//???????????????????????????????????????
                    UserInfo userInfo = userInfoService.selectByPrimaryKey(app.getSuserId());
                    if (StringUtil.isNotBlank(modelCode)) {
                        paramMap.put("modelCode", modelCode);
                    }
                    if (StringUtil.isNotBlank(userInfo.getSmerchantCode())) {
                        paramMap.put("merchantCode", userInfo.getSmerchantCode());
                    } else {
                        paramMap.put("appId", app.getSappId());
                    }
                    //???????????????????????????
                    List<ServerList> serverList = serverListService.selectByMap(paramMap);
                    if (null == serverList || serverList.size() <= 0) {
                        throw new ServiceException("RECOGNITION-SERVER-ERROR");
                    }
                    ServerList server = null;
                    Connection connection = null;
                    Integer tempNum = 0;
                    String tempStr = "";
                    ServerList tempServer = null;
                    Random random = new Random();
                    int rnum = 0;
                    Integer busyTimeOut = StringUtil.toNumber(GrpParaUtil.getValue(GrpParaUtil.SYSTEM_PARA_CODE, "GPU_SERVER_BUSY_TIME_OUT"), 60);
                    do {
                        if (tempNum > 0) {
                            try {
                                if (tempNum >= busyTimeOut) {
                                    throw new ServiceException("RECOGNITION-SERVER-ERROR");
                                }
                                logger.info("?????????????????????????????????????????????" + tempNum + "???");
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                logger.error("?????????????????????????????????{}", e);
                            }
                        }
                        rnum = random.nextInt(serverList.size());
                        tempServer = serverList.get(rnum);
                        try {
                            tempStr = (String) iCached.get("busy_gpu_server_" + tempServer.getScode());
                        } catch (Exception e) {
                            tempStr = tempServer.getScode();
                            logger.error("????????????????????????????????????{}", e);
                        }
                        if (StringUtil.isBlank(tempStr)) {
                            connection = VisualUtils.getGoodsRecognition().getConnsMap().get(tempServer.getScode());
                            if (null != connection) {
                                try {
                                    iCached.put("busy_gpu_server_" + tempServer.getScode(), tempServer.getScode(), busyTimeOut);//????????????????????? 1??????
                                    iCached.put("busy_gpu_server_batch_no_" + batchNo, tempServer.getScode(), busyTimeOut);
                                } catch (Exception e) {
                                    logger.error("????????????????????????????????????????????????{}", e);
                                }
                                tempNum = 0;
                                server = tempServer;
                                break;
                            }
                        }
                        tempNum++;
                    } while (null == connection);

                    //??????????????????
                    ImageCellListBean listBean = new ImageCellListBean();
                    listBean.setScode(batchNo);
                    listBean.setServerCode(server.getScode());
                    List<ImageCellBean> imgs = new ArrayList<ImageCellBean>();
                    ImageCellBean cellBean = null;
                    File fileTemp = null;
                    int imgNum = 0;
                    for (ImageDetail img : imageDetail) {
                        cellBean = new ImageCellBean();
                        cellBean.setCellid(img.getCellid());
                        cellBean.setFilePath(imgPaths.get(imgNum));
                        fileTemp = new File(img.getUploadUrl());
                        if (null == fileTemp || !fileTemp.exists() || !fileTemp.isFile()) {//????????????????????????
                            logger.error("??????????????????????????????????????????{}, ?????????????????????{}", batchNo, cellBean.getFilePath());
                            throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
                        }
                        cellBean.setDeviceId(StringUtil.isBlank(deviceId) ? "" : deviceId);
                        cellBean.setSextends("");
                        cellBean.setImageData("");
                        imgs.add(cellBean);
                        imgNum++;
                    }
                    listBean.setImgs(imgs);
                    String recognitionParam = VisualUtils.getGson().toJson(listBean);
                    String result = VisualUtils.getGoodsRecognition().filePathInventory(connection, recognitionParam);
                    try {
                        iCached.remove("busy_gpu_server_" + server.getScode());
                        iCached.remove("busy_gpu_server_batch_no_" + batchNo);
                    } catch (Exception e) {
                        logger.error("????????????????????????????????????????????????{}", e);
                    }
                    logger.info("??????????????????????????????????????????{} ???????????????{}", batchNo, result);
                    InventoryResultBean resultBean = VisualUtils.getGson().fromJson(result, new TypeToken<InventoryResultBean>() {
                    }.getType());
                    if (null == resultBean || !resultBean.getCode().equals("0")) {
                        throw new ServiceException("RECOGNITION-SERVER-RESULT-ERROR");
                    }
                    List<ImgResultDetail> resultDetail = new ArrayList<ImgResultDetail>();
                    ImgResultDetail imgResult = null;
                    GoodDetail good = null;
                    List<GoodDetail> goodDetail = null;
                    Integer success = 0;
                    Integer failed = 0;
                    if (null != resultBean.getData()) {
                        for (CellGoodsBean goodsBean : resultBean.getData()) {
                            if (goodsBean.getStatus().equals("0")) {
                                success++;
                            } else {
                                failed++;
                            }
                            imgResult = new ImgResultDetail();
                            imgResult.setCellid(goodsBean.getCellid());
                            imgResult.setStatus(goodsBean.getStatus());
                            if (null != goodsBean.getGoods() && goodsBean.getGoods().size() > 0) {
                                goodDetail = new ArrayList<GoodDetail>();
                                for (GoodsBean goods : goodsBean.getGoods()) {
                                    good = new GoodDetail();
                                    good.setNumber(goods.getNumber());
                                    good.setVrCode(goods.getSvrCode());
                                    goodDetail.add(good);
                                }
                                imgResult.setGoodDetail(goodDetail);
                            }
                            resultDetail.add(imgResult);
                        }
                    } else {
                        failed = -1;
                    }
                    if (failed == -1 || failed == resultBean.getData().size()) {
                        throw new ServiceException("RECOGNITION-SERVER-RESULT-ERROR");
                    }
                    resultMap.put("imgResultDetail", resultDetail);
                    resultMap.put("successNum", success);


                    //??????????????????
                    Map<String, String> tempMap1 = new HashMap<String, String>();
                    tempMap1.put("appId", app.getSappId());
                    tempMap1.put("appSecretKey", app.getSappSecretKey());
                    tempMap1.put("code", "200");
                    tempMap1.put("msg", "success");
                    tempMap1.put("subCode", SubCodeEnum.SUCCESS.getCode());
                    tempMap1.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
                    tempMap1.put("batchNo", batchNo);
                    tempMap1.put("methodName", methodName);
                    tempMap1.put("outBatchNo", imgRecognition.getOutBatchNo());
                    tempMap1.put("imgResultDetail", JSONObject.toJSONString(resultDetail));
                    tempMap1.put("deviceId", imgRecognition.getDeviceId());

                    //1?????????????????????
                    String signContentTemp = JSONObject.toJSONString(tempMap1);
                    //2????????????????????????
                    String signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);

                    tempMap1.put("sign", signTemp);

                    String callbackBody = JSONObject.toJSONString(tempMap1);//????????????
                    String encryptCallbackBody = EncryptUtils.encryptStringUnZip(callbackBody);//??????????????????

                    returnMap.put("callbackBody", callbackBody);
                    returnMap.put("encryptCallbackBody", encryptCallbackBody);


                    Integer successNum = success;//????????????
                    Integer deduction = 0;
                    //1????????????????????????????????? > 0  ?????????????????????
                    //if (successNum.intValue() > 0 && updateAccount) {
                    //2????????????????????????????????? = ????????????????????????  ?????????????????????
                    if (successNum.intValue() == imgRecognition.getImageDetail().size()) {
                        deduction = successNum;
                    }
                    if (updateAccount) {
                        //????????????????????????
                        InterfaceAccount accountUpdate = new InterfaceAccount();
                        accountUpdate.setId(account.getId());
                        accountUpdate.setIdeductionNum((account.getIdeductionNum() != null ? account.getIdeductionNum() + deduction : deduction));
                        accountUpdate.setItransferNum((account.getItransferNum() != null ? account.getItransferNum() + successNum : successNum));
                        accountUpdate.setFbalance(account.getFbalance() - deduction);
                        accountUpdate.setUpdateTime(new Date());
                        interfaceAccountService.updateBySelective(accountUpdate);
                    }
                    //????????????????????????
                    interfaceAcceptService.updateInterfaceAcceptBySuccess(imgRecognition.getOutBatchNo(), JSONObject.toJSONString(imgRecognition), imgRecognition.getImageDetail().size(), deduction, batchNo, backBody, encryptBackBody, callbackBody, encryptCallbackBody, app, params);
                    transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition", "????????????????????????????????????????????????????????????" + batchNo + "??????????????????????????????" + successNum);
                    iCached.remove(batchNo + app.getSappId() + "_interface_accept");
                    iCached.remove(batchNo + "_app_info");
                    iCached.remove(batchNo + "_interface_info");

                    //????????????
                    String notifyUrl = params.get("notifyUrl");
                    //????????????????????????
                    Map<String, String> map = new HashMap();
                    map.put("backData", encryptCallbackBody);
                    String status = WebUtils.doGet(notifyUrl, map);
                    //????????????
                    if (status.equals("SUCCESS")) {
                        interfaceAcceptService.updateInterfaceAcceptByCallbackSuccess(batchNo, app.getSappId());
                    } else {
                        //????????????
                        interfaceAcceptService.updateInterfaceAcceptByCallbackFaild(batchNo, app.getSappId());
                    }
                    //???????????????????????????
                } catch (Exception e) {
                    logger.error("????????????????????????????????????:{}", e);
                }
            }
        });


        return returnMap;
    }


    /**
     * ??????????????????????????????
     *
     * @param request ????????????
     * @param batchNo ????????????
     * @param appId   ??????ID
     * @param params  ????????????
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> recongitionAccountQuery(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("?????????????????????????????????????????????????????????????????????APPID???{}??????????????????{}", appId, params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String charset = params.get("charset");
        String signType = params.get("signType");
        //???????????????IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //????????????????????????
        BalanceModel balanceModel = JSONObject.parseObject(bizContent, BalanceModel.class);
        if (null == balanceModel || StringUtil.isBlank(balanceModel.getInterfaceAction())) {
            throw new ServiceException("INVALID-PARAMETER");
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", app.getSuserId());
        paramMap.put("interfaceAction", balanceModel.getInterfaceAction());
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
        String signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap.put("sign", signTemp);

        /*Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("bizContent", balanceModel);
        tempMap.put("params", JSONObject.toJSONString(temp));*/

        tempMap = SortUtils.sortMapByKey(tempMap);//Map??????

        Map<String, Object> tempObj = new HashMap<String, Object>();
        tempObj.put("body", tempMap);

        String backBody = JSONObject.toJSONString(tempObj);//????????????
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//??????????????????
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);

        interfaceAcceptService.updateInterfaceAcceptBySuccess("", JSONObject.toJSONString(balanceModel), 0, 0, batchNo, backBody, encryptBackBody, "", "", app, params);
        transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition_account_query", "??????????????????????????????????????????????????????????????????" + batchNo);

        iCached.remove(batchNo + app.getSappId() + "_interface_accept");
        iCached.remove(batchNo + "_app_info");
        iCached.remove(batchNo + "_interface_info");
        return returnMap;
    }

    /**
     * ????????????????????????????????????
     *
     * @param request ????????????
     * @param batchNo ????????????
     * @param appId   ??????ID
     * @param params  ????????????
     */
    @Override
    public Map<String, Object> recongitionOrderQuery(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("???????????????????????????????????????????????????????????????APPID???{}??????????????????{}", appId, params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String methodName = EncryptUtils.decryptStringUnZip(params.get("methodName"));
        //???????????????IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //????????????????????????
        QueryModel query = JSONObject.parseObject(bizContent, QueryModel.class);

        if (null == query || (StringUtil.isBlank(query.getBatchNo()) && StringUtil.isBlank(query.getOutBatchNo()))) {
            throw new ServiceException("INVALID-PARAMETER");
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtil.isNotBlank(query.getBatchNo())) {
            paramMap.put("batchNo", query.getBatchNo());
        }
        if (StringUtil.isNotBlank(query.getOutBatchNo())) {
            paramMap.put("outBatchNo", query.getOutBatchNo());
        }
        paramMap.put("queryCondition", " and (B.SACTION = 'cloud.api.recognition.async' or B.SACTION = 'cloud.api.recognition')");
        InterfaceAccept interfaceAccept = interfaceAcceptService.selectByMap(paramMap);
        logger.debug("?????????????????????{}", interfaceAccept);
        if (null == interfaceAccept || StringUtil.isBlank(interfaceAccept.getSresponseEncryData())) {
            throw new ServiceException("NO-DATA");
        }
        String rootNode = methodName.replace('.', '_') + "_response";
        String backBody = interfaceAccept.getSresponseData().replace("cloud_api_recognition_response", rootNode).replace("cloud_api_recognition_async_response", rootNode);//????????????
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//??????????????????
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);

        interfaceAcceptService.updateInterfaceAcceptBySuccess("", JSONObject.toJSONString(query), 0, 0, batchNo, backBody, encryptBackBody, "", "", app, params);
        transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition_order_query", "??????????????????????????????????????????????????????????????????" + batchNo);


        iCached.remove(batchNo + app.getSappId() + "_interface_accept");
        iCached.remove(batchNo + "_app_info");
        iCached.remove(batchNo + "_interface_info");
        return returnMap;
    }

    /***
     * http???????????????????????????
     * @param request ????????????
     * @param batchNo ????????????
     * @param appId APPID
     * @param params ????????????
     * @return
     */
    @Override
    public Map<String, Object> recongitionByImgToWeb(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("???????????????????????????????????????????????????????????????????????? {}???????????????APPID???{}", batchNo, appId);
        //logger.debug("????????????????????????????????????????????????,???????????????{}", params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String charset = params.get("charset");
        String signType = params.get("signType");
        String methodName = params.get("methodName");
        //???????????????IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, methodName, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //????????????????????????
        ImgRecognition imgRecognition = JSONObject.parseObject(bizContent, ImgRecognition.class);
        long stime = System.nanoTime();
        List<String> imgPaths = verifyRecognitionParam(interfaceInfo.getScode(), request, batchNo, imgRecognition);//???????????? ????????????
        long etime = System.nanoTime();
        logger.info("???????????????{}, ?????????????????????" + (etime - stime) / 1000 / 1000 + "??????", batchNo);
        //??????????????????
        //??????????????????????????????
        boolean updateAccount = false;
        InterfaceAccount account = null;
        int tollNum = 1;
        if (interfaceInfo.getIpayWay().intValue() == BizTypeDefinitionEnum.InterfaceTollWay.TOLL.getCode()) {
            //??????????????????
            if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()
                    || interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode()) {
                tollNum = imgRecognition.getImageDetail().size();
            } else if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
                tollNum = 0;
            }
            account = verifyAccountBalance(app.getSuserId(), interfaceInfo.getScode(), tollNum);
            updateAccount = true;
        }
        String modelCode = params.get("modelCode");//??????????????????
        stime = System.nanoTime();
        //??????????????????
        Map<String, Object> recognitionResult = recognition(modelCode, imgPaths, batchNo, app, imgRecognition.getImageDetail(), imgRecognition.getDeviceId());
        etime = System.nanoTime();
        logger.info("???????????????{}, ???????????????" + (etime - stime) / 1000 / 1000 + "??????", batchNo);
        List<ImgResultDetail> resultDetail = (List<ImgResultDetail>) recognitionResult.get("imgResultDetail");

        //??????????????????
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("code", "200");
        tempMap.put("msg", "success");
        tempMap.put("subCode", SubCodeEnum.SUCCESS.getCode());
        tempMap.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
        tempMap.put("batchNo", batchNo);
        tempMap.put("outBatchNo", imgRecognition.getOutBatchNo());
        tempMap.put("imgResultDetail", resultDetail);
        tempMap.put("deviceId", imgRecognition.getDeviceId());
        //1?????????????????????
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_response", signContentTemp);
        //2????????????????????????
        String signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap.put("sign", signTemp);

        /*Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("bizContent", imgRecognition);
        tempMap.put("params", JSONObject.toJSONString(temp));*/

        tempMap = SortUtils.sortMapByKey(tempMap);//Map??????

        Map<String, Object> tempObj = new HashMap<String, Object>();
        tempObj.put("body", tempMap);

        String backBody = JSONObject.toJSONString(tempObj);//????????????
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//??????????????????
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);

        //??????????????????
        Map<String, String> tempMap1 = new HashMap<String, String>();
        tempMap1.put("appId", app.getSappId());
        tempMap1.put("appSecretKey", app.getSappSecretKey());
        tempMap1.put("code", "200");
        tempMap1.put("msg", "success");
        tempMap1.put("subCode", SubCodeEnum.SUCCESS.getCode());
        tempMap1.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
        tempMap1.put("batchNo", batchNo);
        tempMap1.put("methodName", params.get("methodName"));
        tempMap1.put("outBatchNo", imgRecognition.getOutBatchNo());
        tempMap1.put("imgResultDetail", JSONObject.toJSONString(resultDetail));
        tempMap1.put("deviceId", imgRecognition.getDeviceId());

        //1?????????????????????
        signContentTemp = JSONObject.toJSONString(tempMap1);
        //2????????????????????????
        signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap1.put("sign", signTemp);

        String callbackBody = JSONObject.toJSONString(tempMap1);//????????????
        String encryptCallbackBody = EncryptUtils.encryptStringUnZip(callbackBody);//??????????????????

        returnMap.put("callbackBody", callbackBody);
        returnMap.put("encryptCallbackBody", encryptCallbackBody);


        Integer successNum = (Integer) recognitionResult.get("successNum");//????????????
        Integer deduction = 0;
        //1????????????????????????????????? > 0  ?????????????????????
        //if (successNum.intValue() > 0 && updateAccount) {
        //2????????????????????????????????? = ????????????????????????  ?????????????????????
        if (successNum.intValue() == imgRecognition.getImageDetail().size()) {
            deduction = successNum;
        }
        if (updateAccount) {
            //????????????????????????
            InterfaceAccount accountUpdate = new InterfaceAccount();
            accountUpdate.setId(account.getId());
            accountUpdate.setIdeductionNum((account.getIdeductionNum() != null ? account.getIdeductionNum() + deduction : deduction));
            accountUpdate.setItransferNum((account.getItransferNum() != null ? account.getItransferNum() + successNum : successNum));
            accountUpdate.setFbalance(account.getFbalance() - deduction);
            accountUpdate.setUpdateTime(new Date());
            interfaceAccountService.updateBySelective(accountUpdate);
        }
        //????????????????????????
        interfaceAcceptService.updateInterfaceAcceptBySuccess(imgRecognition.getOutBatchNo(), JSONObject.toJSONString(imgRecognition), imgRecognition.getImageDetail().size(), deduction, batchNo, backBody, encryptBackBody, callbackBody, encryptCallbackBody, app, params);
        transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition", "????????????????????????????????????????????????????????????" + batchNo + "??????????????????????????????" + successNum);

        iCached.remove(batchNo + app.getSappId() + "_interface_accept");
        iCached.remove(batchNo + "_app_info");
        iCached.remove(batchNo + "_interface_info");
        return returnMap;
    }

    private Map<String,Object> verifyBaseInfo(HttpServletRequest request, String methodName, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        logger.info("?????????????????????????????????????????? ???????????????{}, ??????ID???{}", batchNo, appId);
        String charset = params.get("charset");
        //????????????APPId ????????????
        AppManage app = appManageService.verifyAppId(appId);
        //????????????????????????
        iCached.put(batchNo + "_app_info", app, 2 * 60 * 60);
        //??????????????????
        InterfaceInfo interfaceInfo = interfaceInfoService.verifyInterfaceAuth(methodName, app.getSuserId());
        //??????????????????????????????
        iCached.put(batchNo + "_interface_info", interfaceInfo, 2 * 60 * 60);
        //???????????????IP
        String clientIp = IPUtils.getIpFromRequest(request);
        //?????? ???????????? ?????? ????????? ??????
        interfaceAcceptService.syncAddInterfaceAcceptRecord(batchNo, params, app, interfaceInfo, "", clientIp);
        //????????????
        //String bizContent = BaseEncrypt.decryptContent(params.get("bizContent"), params.get("encryptType"), app.getSappSecretKey(), charset);
        String bizContent = params.get("bizContent");
        //?????????????????? ????????????
        generateRequestDataFile(batchNo, appId, bizContent);
        //????????????
        String signContent = BaseSignature.getSignContent(params);

        String signType = params.get("signType");
        String sign = params.get("sign");
        boolean rsaCheckContent = BaseSignature.rsaCheck(signContent, sign, app.getSappPublicKey(), charset, signType);
        if (!rsaCheckContent) {
            if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(signContent) || !signContent.contains("\\/")) {
                throw new ServiceException("INVALID-SIGN");
            }
            String srouceData = signContent.replace("\\/", "/");
            boolean jsonCheck = BaseSignature.rsaCheck(srouceData, sign, app.getSappPublicKey(), charset, signType);
            if (!jsonCheck) {
                throw new ServiceException("INVALID-SIGN");
            }
        }

        //logger.debug("??????????????????????????????{}??????????????????{}, ?????????????????????{}", app, interfaceInfo, bizContent);
        logger.info("????????????????????????");
        returnMap.put("bizContent", bizContent);
        returnMap.put("appManage", app);
        returnMap.put("interfaceInfo", interfaceInfo);
        return returnMap;
    }


    /***
     * ????????????????????????
     * @param batchNo ????????????
     * @param appId APPId
     * @param bizContent ????????????
     */
    private void generateRequestDataFile(final String batchNo, final String appId, final String bizContent) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("??????????????????????????????????????????????????????{}", batchNo);
                String filePath = EvnUtils.getValue("request.data.path");//??????????????????
                String folder = DateUtils.getCurrentDTimeNumber();
                String filePathType = SysParaUtil.getValue("recognition_image_file_path_type");//?????????????????????
                String serverPath = "";
                if (filePathType.equals("windows")) {
                    serverPath = filePath + "\\" + appId + "\\" + folder;
                } else {
                    serverPath = filePath + "/" + appId + "/" + folder;
                }
                File tempFile = new File(serverPath);
                if (!tempFile.exists()) {
                    tempFile.mkdirs();
                }
                String fileName = batchNo + "_request_data.txt";
                File file = null;
                if (filePathType.equals("")) {
                    file = new File(serverPath + "\\" + fileName);
                } else {
                    file = new File(serverPath + "/" + fileName);
                }
                try {
                    if (TxtExport.createFile(file)) {
                        boolean flag = TxtExport.writeTxtFile(bizContent, file);
                        if (flag) {
                            logger.info("????????????????????????????????????????????????{}", batchNo);
                        } else {
                            logger.error("????????????????????????????????????????????????{}", batchNo);
                        }
                    }
                } catch (Exception e) {
                    logger.error("?????????????????????????????????{}", e);
                }
            }
        });
    }

    /**
     * ??????????????????????????????
     *
     * @param request ????????????
     * @param batchNo ????????????
     * @param appId   ??????ID
     * @param params  ??????????????????
     * @throws ServiceException
     * @throws Exception
     */
    private Map<String, Object> verifyBaseInfo(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        logger.info("?????????????????????????????????????????? ???????????????{}, ??????ID???{}", batchNo, appId);
        String charset = params.get("charset");
        String methodName = EncryptUtils.decryptStringUnZip(params.get("methodName"));
        //????????????APPId ????????????
        AppManage app = appManageService.verifyAppId(appId);
        //????????????????????????
        iCached.put(batchNo + "_app_info", app, 2 * 60 * 60);
        //??????????????????
        InterfaceInfo interfaceInfo = interfaceInfoService.verifyInterfaceAuth(methodName, app.getSuserId());
        //??????????????????????????????
        iCached.put(batchNo + "_interface_info", interfaceInfo, 2 * 60 * 60);
        //???????????????IP
        String clientIp = IPUtils.getIpFromRequest(request);
        //?????? ???????????? ?????? ????????? ??????
        interfaceAcceptService.syncAddInterfaceAcceptRecord(batchNo, params, app, interfaceInfo, "", clientIp);
        //????????????
        String bizContent = BaseEncrypt.decryptContent(params.get("bizContent"), params.get("encryptType"), app.getSappSecretKey(), charset);
        //?????????????????? ????????????
        generateRequestDataFile(batchNo, appId, bizContent);
        //????????????
        String signContent = BaseSignature.getSignContent(params);

        String signType = params.get("signType");
        String sign = params.get("sign");
        boolean rsaCheckContent = BaseSignature.rsaCheck(signContent, sign, app.getSappPublicKey(), charset, signType);
        if (!rsaCheckContent) {
            if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(signContent) || !signContent.contains("\\/")) {
                throw new ServiceException("INVALID-SIGN");
            }
            String srouceData = signContent.replace("\\/", "/");
            boolean jsonCheck = BaseSignature.rsaCheck(srouceData, sign, app.getSappPublicKey(), charset, signType);
            if (!jsonCheck) {
                throw new ServiceException("INVALID-SIGN");
            }
        }

        //logger.debug("??????????????????????????????{}??????????????????{}, ?????????????????????{}", app, interfaceInfo, bizContent);
        logger.info("????????????????????????");
        returnMap.put("bizContent", bizContent);
        returnMap.put("appManage", app);
        returnMap.put("interfaceInfo", interfaceInfo);
        return returnMap;
    }

    /**
     * ??????????????????
     *
     * @param modelCode   ????????????
     * @param imgPaths    ????????????????????????
     * @param batchNo     ????????????
     * @param app         ????????????
     * @param imageDetail ??????????????????
     * @param deviceId    ????????????
     * @return
     */
    private synchronized Map<String, Object> recognition(String modelCode, List<String> imgPaths, String batchNo, AppManage app, List<ImageDetail> imageDetail, String deviceId) throws ServiceException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sip = IPUtils.getLocalAddress();
        if (StringUtil.isBlank(sip)) {
            throw new ServiceException("RECOGNITION-SERVER-ERROR");
        }
        paramMap.put("sip", sip);
        paramMap.put("irunStatus", "30");//???????????????????????????????????????
        UserInfo userInfo = userInfoService.selectByPrimaryKey(app.getSuserId());
        if (StringUtil.isNotBlank(modelCode)) {
            paramMap.put("modelCode", modelCode);
        }
        if (StringUtil.isNotBlank(userInfo.getSmerchantCode())) {
            paramMap.put("merchantCode", userInfo.getSmerchantCode());
        } else {
            paramMap.put("appId", app.getSappId());
        }
        //???????????????????????????
        List<ServerList> serverList = serverListService.selectByMap(paramMap);
        if (null == serverList || serverList.size() <= 0) {
            throw new ServiceException("RECOGNITION-SERVER-ERROR");
        }
        ServerList server = null;
        Connection connection = null;
        Integer tempNum = 0;
        String tempStr = "";
        ServerList tempServer = null;
        Random random = new Random();
        int rnum = 0;
        Integer busyTimeOut = StringUtil.toNumber(GrpParaUtil.getValue(GrpParaUtil.SYSTEM_PARA_CODE, "GPU_SERVER_BUSY_TIME_OUT"), 60);
        do {
            if (tempNum > 0) {
                try {
                    if (tempNum >= busyTimeOut) {
                        throw new ServiceException("RECOGNITION-SERVER-ERROR");
                    }
                    logger.info("?????????????????????????????????????????????" + tempNum + "???");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("?????????????????????????????????{}", e);
                }
            }
            rnum = random.nextInt(serverList.size());
            tempServer = serverList.get(rnum);
            //for (ServerList tempServer : serverList) {
            try {
                tempStr = (String) iCached.get("busy_gpu_server_" + tempServer.getScode());
            } catch (Exception e) {
                tempStr = tempServer.getScode();
                logger.error("????????????????????????????????????{}", e);
            }
            if (StringUtil.isBlank(tempStr)) {
                connection = VisualUtils.getGoodsRecognition().getConnsMap().get(tempServer.getScode());
                if (null != connection) {
                    try {
                        iCached.put("busy_gpu_server_" + tempServer.getScode(), tempServer.getScode(), busyTimeOut);//????????????????????? 1??????
                        iCached.put("busy_gpu_server_batch_no_" + batchNo, tempServer.getScode(), busyTimeOut);
                    } catch (Exception e) {
                        logger.error("????????????????????????????????????????????????{}", e);
                    }
                    tempNum = 0;
                    server = tempServer;
                    break;
                }
            }
            //}
            tempNum++;
        } while (null == connection);
        /*if (null == connection || null == server || StringUtil.isBlank(server.getScode())) {
            throw new ServiceException("RECOGNITION-SERVER-ERROR");
        }*/
        //??????????????????
        ImageCellListBean listBean = new ImageCellListBean();
        listBean.setScode(batchNo);
        listBean.setServerCode(server.getScode());
        List<ImageCellBean> imgs = new ArrayList<ImageCellBean>();
        ImageCellBean cellBean = null;
        File fileTemp = null;
        int imgNum = 0;
        for (ImageDetail img : imageDetail) {
            cellBean = new ImageCellBean();
            cellBean.setCellid(img.getCellid());
            cellBean.setFilePath(imgPaths.get(imgNum));
            fileTemp = new File(img.getUploadUrl());
            if (null == fileTemp || !fileTemp.exists() || !fileTemp.isFile()) {//????????????????????????
                logger.error("??????????????????????????????????????????{}, ?????????????????????{}", batchNo, cellBean.getFilePath());
                throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
            }else{
                logger.info("????????????{}",cellBean.getFilePath());
            }
            cellBean.setDeviceId(StringUtil.isBlank(deviceId) ? "" : deviceId);
            cellBean.setSextends("");
            cellBean.setImageData("");
            imgs.add(cellBean);
            imgNum++;
        }
        listBean.setImgs(imgs);
        String recognitionParam = VisualUtils.getGson().toJson(listBean);
        String result = VisualUtils.getGoodsRecognition().filePathInventory(connection, recognitionParam);
        try {
            iCached.remove("busy_gpu_server_" + server.getScode());
            iCached.remove("busy_gpu_server_batch_no_" + batchNo);
        } catch (Exception e) {
            logger.error("????????????????????????????????????????????????{}", e);
        }
        logger.info("??????????????????????????????????????????{} ???????????????{}", batchNo, result);
        InventoryResultBean resultBean = VisualUtils.getGson().fromJson(result, new TypeToken<InventoryResultBean>() {}.getType());
        if (null == resultBean || !resultBean.getCode().equals("0")) {
            throw new ServiceException("RECOGNITION-SERVER-RESULT-ERROR");
        }
        List<ImgResultDetail> resultDetail = new ArrayList<ImgResultDetail>();
        ImgResultDetail imgResult = null;
        GoodDetail good = null;
        List<GoodDetail> goodDetail = null;
        Integer success = 0;
        Integer failed = 0;
        if (null != resultBean.getData()) {
            for (CellGoodsBean goodsBean : resultBean.getData()) {
                if (goodsBean.getStatus().equals("0")) {
                    success++;
                } else {
                    failed++;
                }
                imgResult = new ImgResultDetail();
                imgResult.setCellid(goodsBean.getCellid());
                imgResult.setStatus(goodsBean.getStatus());
                if (null != goodsBean.getGoods() && goodsBean.getGoods().size() > 0) {
                    goodDetail = new ArrayList<GoodDetail>();
                    for (GoodsBean goods : goodsBean.getGoods()) {
                        good = new GoodDetail();
                        good.setNumber(goods.getNumber());
                        good.setVrCode(goods.getSvrCode());
                        goodDetail.add(good);
                    }
                    imgResult.setGoodDetail(goodDetail);
                }
                resultDetail.add(imgResult);
            }

        } else {
            failed = -1;
        }
        if (failed == -1 || failed == resultBean.getData().size()) {
            throw new ServiceException("RECOGNITION-SERVER-RESULT-ERROR");
        }

        resultMap.put("imgResultDetail", resultDetail);
        resultMap.put("successNum", success);
        return resultMap;
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
            if (account.getFbalance().intValue() < tollNum) {
                throw new ServiceException("BALANCE-NOT-ENOUGH");
            }
            //????????????????????????
            account.setIfreezeNum(account.getIfreezeNum() + tollNum);
        } else if (account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
            //???????????????
            if (account.getIvalidityType().intValue() == BizTypeDefinitionEnum.ValidityType.FIXED_DATE.getCode()
                    && account.getTendTime().before(new Date())) {
                throw new ServiceException("BALANCE-NOT-ENOUGH");
            }
        } else if (account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.NUM_TOLL_TYPE.getCode()
                || account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()) {
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
     * @param interfaceCode ????????????
     * @param request ????????????
     * @param batchNo ????????????
     * @param imgRecognition ??????????????????
     * @return
     * @throws ServiceException
     */
    private List<String> verifyRecognitionParam(String interfaceCode, HttpServletRequest request, String batchNo, ImgRecognition imgRecognition) throws ServiceException {
        List<String> imgPaths = new ArrayList<String>();
        if (null == imgRecognition) {
            throw new ServiceException("INVALID-PARAMETER");
        }
        if (StringUtil.isBlank(imgRecognition.getOutBatchNo())) {
            throw new ServiceException("INVALID-PARAMETER-OUT-BATCH-NO");
        }
        InterfaceAccept accept = interfaceAcceptService.selectByOutBatchNo(interfaceCode, imgRecognition.getOutBatchNo());
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
                /*//ftp??????????????????????????????
                String serverPath = prefix + "/" + folder + "/" + batchNo + "/" + fileName;//??????????????????

                //??????????????????????????????
                String rootPath = request.getSession().getServletContext().getRealPath("/");
                String tmpPath = EvnUtils.getValue("upload.image.tmp");
                String tempPath  = rootPath + tmpPath + fileName;//??????????????????????????????
                File tempfile = new File(rootPath + tmpPath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                saveImage(imgType, img.getImgBase64(), "", tempPath, batchNo);

                File file = new File(tempPath);
                boolean flag = ImageUtils.upLoadPic(file, prefix + "/" + folder + "/" + batchNo + "/", fileName);
                if (flag) {
                    if (imgType == 1) {
                        img.setImgBase64(serverPath);//??????base64 ?????????????????????
                    }
                    imgPaths.add(serverPath.replace(prefix,sharePrefix));
                }*/
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
        /*try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        imgRecognition.setImageDetail(imgs);
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
}