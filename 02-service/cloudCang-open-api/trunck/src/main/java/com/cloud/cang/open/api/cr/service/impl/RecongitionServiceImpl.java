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
     * 图片识别接口 同步
     *
     * @param request
     * @param batchNo 处理业务编号
     * @param appId   商户应用appId
     * @param params  请求参数
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> recongitionByImg(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("图片视觉识别同步方法接口开始执行，请求业务编号： {}，请求商户APPID：{}", batchNo, appId);
        //logger.debug("图片视觉识别同步方法接口开始执行,请求参数：{}", params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String charset = params.get("charset");
        String signType = params.get("signType");
        //获取客户端IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //验证接口请求参数
        ImgRecognition imgRecognition = JSONObject.parseObject(bizContent, ImgRecognition.class);
        long stime = System.nanoTime();
        List<String> imgPaths = verifyRecognitionParam(interfaceInfo.getScode(), request, batchNo, imgRecognition);//验证参数 保存图片
        long etime = System.nanoTime();
        logger.info("处理批次：{}, 保存图片时间：" + (etime - stime) / 1000 / 1000 + "毫秒", batchNo);
        //本次收费次数
        //判断是否验证接口余额
        boolean updateAccount = false;
        InterfaceAccount account = null;
        int tollNum = 1;
        if (interfaceInfo.getIpayWay().intValue() == BizTypeDefinitionEnum.InterfaceTollWay.TOLL.getCode()) {
            //验证接口余额
            if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()
                    || interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode()) {
                tollNum = imgRecognition.getImageDetail().size();
            } else if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
                tollNum = 0;
            }
            account = verifyAccountBalance(app.getSuserId(), interfaceInfo.getScode(), tollNum);
            updateAccount = true;
        }
        String modelCode = params.get("modelCode");//识别模型编号
        stime = System.nanoTime();
        //调用识别接口
        Map<String, Object> recognitionResult = recognition(modelCode, imgPaths, batchNo, app, imgRecognition.getImageDetail(), imgRecognition.getDeviceId());
        etime = System.nanoTime();
        logger.info("处理批次：{}, 识别时间：" + (etime - stime) / 1000 / 1000 + "毫秒", batchNo);
        List<ImgResultDetail> resultDetail = (List<ImgResultDetail>) recognitionResult.get("imgResultDetail");

        //创建返回参数
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("code", "200");
        tempMap.put("msg", "success");
        tempMap.put("subCode", SubCodeEnum.SUCCESS.getCode());
        tempMap.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
        tempMap.put("batchNo", batchNo);
        tempMap.put("outBatchNo", imgRecognition.getOutBatchNo());
        tempMap.put("imgResultDetail", resultDetail);
        tempMap.put("deviceId", imgRecognition.getDeviceId());
        //1、参数签名字段
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_response", signContentTemp);
        //2、对参数进行签名
        String signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap.put("sign", signTemp);

        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("bizContent", imgRecognition);
        tempMap.put("params", JSONObject.toJSONString(temp));

        tempMap = SortUtils.sortMapByKey(tempMap);//Map排序

        Map<String, Object> tempObj = new HashMap<String, Object>();
        tempObj.put("body", tempMap);

        String backBody = JSONObject.toJSONString(tempObj);//返回参数
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//返回加密参数
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);

        //创建回调参数
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

        //1、参数签名字段
        signContentTemp = JSONObject.toJSONString(tempMap1);
        //2、对参数进行签名
        signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap1.put("sign", signTemp);

        String callbackBody = JSONObject.toJSONString(tempMap1);//返回参数
        String encryptCallbackBody = EncryptUtils.encryptStringUnZip(callbackBody);//返回加密参数

        returnMap.put("callbackBody", callbackBody);
        returnMap.put("encryptCallbackBody", encryptCallbackBody);


        Integer successNum = (Integer) recognitionResult.get("successNum");//调用次数
        Integer deduction = 0;
        //1、如果图像识别成功次数 > 0  并且是收费接口
        //if (successNum.intValue() > 0 && updateAccount) {
        //2、如果图像识别成功次数 = 请求识别图片数量  并且是收费接口
        if (successNum.intValue() == imgRecognition.getImageDetail().size()) {
            deduction = successNum;
        }
        if (updateAccount) {
            //更新接口账户信息
            InterfaceAccount accountUpdate = new InterfaceAccount();
            accountUpdate.setId(account.getId());
            accountUpdate.setIdeductionNum((account.getIdeductionNum() != null ? account.getIdeductionNum() + deduction : deduction));
            accountUpdate.setItransferNum((account.getItransferNum() != null ? account.getItransferNum() + successNum : successNum));
            accountUpdate.setFbalance(account.getFbalance() - deduction);
            accountUpdate.setUpdateTime(new Date());
            interfaceAccountService.updateBySelective(accountUpdate);
        }
        //更新业务处理结果
        interfaceAcceptService.updateInterfaceAcceptBySuccess(imgRecognition.getOutBatchNo(), JSONObject.toJSONString(imgRecognition), imgRecognition.getImageDetail().size(), deduction, batchNo, backBody, encryptBackBody, callbackBody, encryptCallbackBody, app, params);
        transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition", "图片视觉识别同步接口调用成功，业务编号：" + batchNo + "，成功识别图片张数：" + successNum);

        iCached.remove(batchNo + app.getSappId() + "_interface_accept");
        iCached.remove(batchNo + "_app_info");
        iCached.remove(batchNo + "_interface_info");
        return returnMap;
    }

    /**
     * 图片识别接口 异步
     *
     * @param request
     * @param batchNo 处理业务编号
     * @param appId   商户应用appId
     * @param params  请求参数
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    @Override
    public Map<String, Object> recongitionAsyncByImg(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("图片视觉识别异步方法接口开始执行，请求业务编号： {}，请求商户APPID：{}", batchNo, appId);
        logger.debug("图片视觉识别异步方法接口开始执行,请求参数：{}", params);
        String charset = params.get("charset");
        String signType = params.get("signType");
        //获取客户端IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //验证接口请求参数
        ImgRecognition imgRecognition = JSONObject.parseObject(bizContent, ImgRecognition.class);
        List<String> imgPaths = verifyRecognitionParam(interfaceInfo.getScode(), request, batchNo, imgRecognition);//验证参数 保存图片
        //本次收费次数
        //判断是否验证接口余额
        boolean updateAccount = false;
        InterfaceAccount account = null;
        int tollNum = 1;
        if (interfaceInfo.getIpayWay().intValue() == BizTypeDefinitionEnum.InterfaceTollWay.TOLL.getCode()) {
            //验证接口余额
            if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()
                    || interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode()) {
                tollNum = imgRecognition.getImageDetail().size();
            } else if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
                tollNum = 0;
            }
            account = verifyAccountBalance(app.getSuserId(), interfaceInfo.getScode(), tollNum);
            updateAccount = true;
        }
        String modelCode = params.get("modelCode");//识别模型编号
        String methodName = EncryptUtils.decryptStringUnZip(params.get("methodName"));
        return AsyncRecognition(modelCode, imgPaths, batchNo, app, imgRecognition.getImageDetail(), imgRecognition.getDeviceId(), charset, signType, imgRecognition, account, updateAccount, clientIp, interfaceInfo, methodName, params);
    }

    /**
     * 图片识别接口   异步
     *
     * @param modelCode   模型编号
     * @param imgPaths    对应识别图片地址
     * @param batchNo     业务编号
     * @param app         应用信息
     * @param imageDetail 识别图片集合
     * @param deviceId    设备信息
     * @return
     */
    private synchronized Map<String, Object> AsyncRecognition(String modelCode, List<String> imgPaths, String batchNo, AppManage app, List<ImageDetail> imageDetail, String deviceId, String charset, String signType, ImgRecognition imgRecognition, InterfaceAccount account, boolean updateAccount, String clientIp, InterfaceInfo interfaceInfo, String methodName, Map<String, String> params) throws ServiceException, CloudCangException {
        //回调返回信息
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        //创建返回参数
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("code", "200");
        tempMap.put("msg", "success");
        tempMap.put("subCode", SubCodeEnum.SUCCESS.getCode());
        tempMap.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
        //1、参数签名字段
        final String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_async_response", signContentTemp);
        //2、对参数进行签名
        String signTemp = null;
        signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap.put("sign", signTemp);
        tempMap = SortUtils.sortMapByKey(tempMap);//Map排序
        Map<String, Object> tempObj = new HashMap<String, Object>();
        tempObj.put("body", tempMap);
        String backBody = JSONObject.toJSONString(tempObj);//返回参数
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//返回加密参数
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
                    paramMap.put("irunStatus", "30");//初始化成功的服务器可以识别
                    UserInfo userInfo = userInfoService.selectByPrimaryKey(app.getSuserId());
                    if (StringUtil.isNotBlank(modelCode)) {
                        paramMap.put("modelCode", modelCode);
                    }
                    if (StringUtil.isNotBlank(userInfo.getSmerchantCode())) {
                        paramMap.put("merchantCode", userInfo.getSmerchantCode());
                    } else {
                        paramMap.put("appId", app.getSappId());
                    }
                    //获取识别服务器数据
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
                                logger.info("正在等待释放识别服务器。已等待" + tempNum + "秒");
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                logger.error("获取服务休眠等待异常：{}", e);
                            }
                        }
                        rnum = random.nextInt(serverList.size());
                        tempServer = serverList.get(rnum);
                        try {
                            tempStr = (String) iCached.get("busy_gpu_server_" + tempServer.getScode());
                        } catch (Exception e) {
                            tempStr = tempServer.getScode();
                            logger.error("获取正在识别服务器异常：{}", e);
                        }
                        if (StringUtil.isBlank(tempStr)) {
                            connection = VisualUtils.getGoodsRecognition().getConnsMap().get(tempServer.getScode());
                            if (null != connection) {
                                try {
                                    iCached.put("busy_gpu_server_" + tempServer.getScode(), tempServer.getScode(), busyTimeOut);//服务器存放时间 1分钟
                                    iCached.put("busy_gpu_server_batch_no_" + batchNo, tempServer.getScode(), busyTimeOut);
                                } catch (Exception e) {
                                    logger.error("存储正在识别服务器繁忙信息异常：{}", e);
                                }
                                tempNum = 0;
                                server = tempServer;
                                break;
                            }
                        }
                        tempNum++;
                    } while (null == connection);

                    //组装识别参数
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
                        if (null == fileTemp || !fileTemp.exists() || !fileTemp.isFile()) {//判断文件是否存在
                            logger.error("获取识别图片为空，处理批次：{}, 文件地址信息：{}", batchNo, cellBean.getFilePath());
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
                        logger.error("移除正在识别服务器繁忙信息异常：{}", e);
                    }
                    logger.info("图片识别返回结果，业务编号：{} 返回结果：{}", batchNo, result);
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


                    //创建回调参数
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

                    //1、参数签名字段
                    String signContentTemp = JSONObject.toJSONString(tempMap1);
                    //2、对参数进行签名
                    String signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);

                    tempMap1.put("sign", signTemp);

                    String callbackBody = JSONObject.toJSONString(tempMap1);//返回参数
                    String encryptCallbackBody = EncryptUtils.encryptStringUnZip(callbackBody);//返回加密参数

                    returnMap.put("callbackBody", callbackBody);
                    returnMap.put("encryptCallbackBody", encryptCallbackBody);


                    Integer successNum = success;//调用次数
                    Integer deduction = 0;
                    //1、如果图像识别成功次数 > 0  并且是收费接口
                    //if (successNum.intValue() > 0 && updateAccount) {
                    //2、如果图像识别成功次数 = 请求识别图片数量  并且是收费接口
                    if (successNum.intValue() == imgRecognition.getImageDetail().size()) {
                        deduction = successNum;
                    }
                    if (updateAccount) {
                        //更新接口账户信息
                        InterfaceAccount accountUpdate = new InterfaceAccount();
                        accountUpdate.setId(account.getId());
                        accountUpdate.setIdeductionNum((account.getIdeductionNum() != null ? account.getIdeductionNum() + deduction : deduction));
                        accountUpdate.setItransferNum((account.getItransferNum() != null ? account.getItransferNum() + successNum : successNum));
                        accountUpdate.setFbalance(account.getFbalance() - deduction);
                        accountUpdate.setUpdateTime(new Date());
                        interfaceAccountService.updateBySelective(accountUpdate);
                    }
                    //更新业务处理结果
                    interfaceAcceptService.updateInterfaceAcceptBySuccess(imgRecognition.getOutBatchNo(), JSONObject.toJSONString(imgRecognition), imgRecognition.getImageDetail().size(), deduction, batchNo, backBody, encryptBackBody, callbackBody, encryptCallbackBody, app, params);
                    transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition", "图片视觉识别同步接口调用成功，业务编号：" + batchNo + "，成功识别图片张数：" + successNum);
                    iCached.remove(batchNo + app.getSappId() + "_interface_accept");
                    iCached.remove(batchNo + "_app_info");
                    iCached.remove(batchNo + "_interface_info");

                    //回调地址
                    String notifyUrl = params.get("notifyUrl");
                    //返回加密后的数据
                    Map<String, String> map = new HashMap();
                    map.put("backData", encryptCallbackBody);
                    String status = WebUtils.doGet(notifyUrl, map);
                    //回调成功
                    if (status.equals("SUCCESS")) {
                        interfaceAcceptService.updateInterfaceAcceptByCallbackSuccess(batchNo, app.getSappId());
                    } else {
                        //回调失败
                        interfaceAcceptService.updateInterfaceAcceptByCallbackFaild(batchNo, app.getSappId());
                    }
                    //判断回调返回的数据
                } catch (Exception e) {
                    logger.error("异步调用图片识别接口异常:{}", e);
                }
            }
        });


        return returnMap;
    }


    /**
     * 视觉识别账户信息查询
     *
     * @param request 请求信息
     * @param batchNo 业务编号
     * @param appId   应用ID
     * @param params  查询参数
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> recongitionAccountQuery(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("图片视觉识别账户信息查询接口开始执行，请求商户APPID：{}，请求参数：{}", appId, params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String charset = params.get("charset");
        String signType = params.get("signType");
        //获取客户端IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //验证接口请求参数
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
        //创建返回参数
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
        //1、参数签名字段
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_getBalance_response", signContentTemp);
        //2、对参数进行签名
        String signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap.put("sign", signTemp);

        /*Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("bizContent", balanceModel);
        tempMap.put("params", JSONObject.toJSONString(temp));*/

        tempMap = SortUtils.sortMapByKey(tempMap);//Map排序

        Map<String, Object> tempObj = new HashMap<String, Object>();
        tempObj.put("body", tempMap);

        String backBody = JSONObject.toJSONString(tempObj);//返回参数
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//返回加密参数
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);

        interfaceAcceptService.updateInterfaceAcceptBySuccess("", JSONObject.toJSONString(balanceModel), 0, 0, batchNo, backBody, encryptBackBody, "", "", app, params);
        transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition_account_query", "图片视觉识别账户查询接口调用成功，业务编号：" + batchNo);

        iCached.remove(batchNo + app.getSappId() + "_interface_accept");
        iCached.remove(batchNo + "_app_info");
        iCached.remove(batchNo + "_interface_info");
        return returnMap;
    }

    /**
     * 查询视觉识别订单查询接口
     *
     * @param request 请求信息
     * @param batchNo 业务编号
     * @param appId   应用ID
     * @param params  查询参数
     */
    @Override
    public Map<String, Object> recongitionOrderQuery(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("图片视觉识别订单查询接口开始执行，请求商户APPID：{}，请求参数：{}", appId, params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String methodName = EncryptUtils.decryptStringUnZip(params.get("methodName"));
        //获取客户端IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //验证接口请求参数
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
        logger.debug("业务受理信息：{}", interfaceAccept);
        if (null == interfaceAccept || StringUtil.isBlank(interfaceAccept.getSresponseEncryData())) {
            throw new ServiceException("NO-DATA");
        }
        String rootNode = methodName.replace('.', '_') + "_response";
        String backBody = interfaceAccept.getSresponseData().replace("cloud_api_recognition_response", rootNode).replace("cloud_api_recognition_async_response", rootNode);//返回参数
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//返回加密参数
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);

        interfaceAcceptService.updateInterfaceAcceptBySuccess("", JSONObject.toJSONString(query), 0, 0, batchNo, backBody, encryptBackBody, "", "", app, params);
        transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition_order_query", "图片视觉识别订单查询接口调用成功，业务编号：" + batchNo);


        iCached.remove(batchNo + app.getSappId() + "_interface_accept");
        iCached.remove(batchNo + "_app_info");
        iCached.remove(batchNo + "_interface_info");
        return returnMap;
    }

    /***
     * http请求版云端识别接口
     * @param request 请求参数
     * @param batchNo 业务编号
     * @param appId APPID
     * @param params 请求参数
     * @return
     */
    @Override
    public Map<String, Object> recongitionByImgToWeb(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        logger.info("图片视觉识别同步方法接口开始执行，请求业务编号： {}，请求商户APPID：{}", batchNo, appId);
        //logger.debug("图片视觉识别同步方法接口开始执行,请求参数：{}", params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String charset = params.get("charset");
        String signType = params.get("signType");
        String methodName = params.get("methodName");
        //获取客户端IP
        String clientIp = IPUtils.getIpFromRequest(request);
        Map<String, Object> tempReturn = verifyBaseInfo(request, methodName, batchNo, appId, params);
        AppManage app = (AppManage) tempReturn.get("appManage");
        InterfaceInfo interfaceInfo = (InterfaceInfo) tempReturn.get("interfaceInfo");
        String bizContent = (String) tempReturn.get("bizContent");

        //验证接口请求参数
        ImgRecognition imgRecognition = JSONObject.parseObject(bizContent, ImgRecognition.class);
        long stime = System.nanoTime();
        List<String> imgPaths = verifyRecognitionParam(interfaceInfo.getScode(), request, batchNo, imgRecognition);//验证参数 保存图片
        long etime = System.nanoTime();
        logger.info("处理批次：{}, 保存图片时间：" + (etime - stime) / 1000 / 1000 + "毫秒", batchNo);
        //本次收费次数
        //判断是否验证接口余额
        boolean updateAccount = false;
        InterfaceAccount account = null;
        int tollNum = 1;
        if (interfaceInfo.getIpayWay().intValue() == BizTypeDefinitionEnum.InterfaceTollWay.TOLL.getCode()) {
            //验证接口余额
            if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()
                    || interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.ALL_TOLL_TYPE.getCode()) {
                tollNum = imgRecognition.getImageDetail().size();
            } else if (interfaceInfo.getIpayType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
                tollNum = 0;
            }
            account = verifyAccountBalance(app.getSuserId(), interfaceInfo.getScode(), tollNum);
            updateAccount = true;
        }
        String modelCode = params.get("modelCode");//识别模型编号
        stime = System.nanoTime();
        //调用识别接口
        Map<String, Object> recognitionResult = recognition(modelCode, imgPaths, batchNo, app, imgRecognition.getImageDetail(), imgRecognition.getDeviceId());
        etime = System.nanoTime();
        logger.info("处理批次：{}, 识别时间：" + (etime - stime) / 1000 / 1000 + "毫秒", batchNo);
        List<ImgResultDetail> resultDetail = (List<ImgResultDetail>) recognitionResult.get("imgResultDetail");

        //创建返回参数
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("code", "200");
        tempMap.put("msg", "success");
        tempMap.put("subCode", SubCodeEnum.SUCCESS.getCode());
        tempMap.put("subMsg", SubCodeEnum.SUCCESS.getReturnMsg());
        tempMap.put("batchNo", batchNo);
        tempMap.put("outBatchNo", imgRecognition.getOutBatchNo());
        tempMap.put("imgResultDetail", resultDetail);
        tempMap.put("deviceId", imgRecognition.getDeviceId());
        //1、参数签名字段
        String signContentTemp = JSONObject.toJSONString(tempMap);
        tempMap.put("cloud_api_recognition_response", signContentTemp);
        //2、对参数进行签名
        String signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap.put("sign", signTemp);

        /*Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("bizContent", imgRecognition);
        tempMap.put("params", JSONObject.toJSONString(temp));*/

        tempMap = SortUtils.sortMapByKey(tempMap);//Map排序

        Map<String, Object> tempObj = new HashMap<String, Object>();
        tempObj.put("body", tempMap);

        String backBody = JSONObject.toJSONString(tempObj);//返回参数
        String encryptBackBody = EncryptUtils.encryptStringUnZip(backBody);//返回加密参数
        returnMap.put("backBody", backBody);
        returnMap.put("encryptBackBody", encryptBackBody);

        //创建回调参数
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

        //1、参数签名字段
        signContentTemp = JSONObject.toJSONString(tempMap1);
        //2、对参数进行签名
        signTemp = BaseSignature.rsaSign(signContentTemp, app.getSplatformKey(), charset, signType);
        tempMap1.put("sign", signTemp);

        String callbackBody = JSONObject.toJSONString(tempMap1);//返回参数
        String encryptCallbackBody = EncryptUtils.encryptStringUnZip(callbackBody);//返回加密参数

        returnMap.put("callbackBody", callbackBody);
        returnMap.put("encryptCallbackBody", encryptCallbackBody);


        Integer successNum = (Integer) recognitionResult.get("successNum");//调用次数
        Integer deduction = 0;
        //1、如果图像识别成功次数 > 0  并且是收费接口
        //if (successNum.intValue() > 0 && updateAccount) {
        //2、如果图像识别成功次数 = 请求识别图片数量  并且是收费接口
        if (successNum.intValue() == imgRecognition.getImageDetail().size()) {
            deduction = successNum;
        }
        if (updateAccount) {
            //更新接口账户信息
            InterfaceAccount accountUpdate = new InterfaceAccount();
            accountUpdate.setId(account.getId());
            accountUpdate.setIdeductionNum((account.getIdeductionNum() != null ? account.getIdeductionNum() + deduction : deduction));
            accountUpdate.setItransferNum((account.getItransferNum() != null ? account.getItransferNum() + successNum : successNum));
            accountUpdate.setFbalance(account.getFbalance() - deduction);
            accountUpdate.setUpdateTime(new Date());
            interfaceAccountService.updateBySelective(accountUpdate);
        }
        //更新业务处理结果
        interfaceAcceptService.updateInterfaceAcceptBySuccess(imgRecognition.getOutBatchNo(), JSONObject.toJSONString(imgRecognition), imgRecognition.getImageDetail().size(), deduction, batchNo, backBody, encryptBackBody, callbackBody, encryptCallbackBody, app, params);
        transferLogService.addTransferLog(clientIp, batchNo, app, interfaceInfo, 10, "image_recognition", "图片视觉识别同步接口调用成功，业务编号：" + batchNo + "，成功识别图片张数：" + successNum);

        iCached.remove(batchNo + app.getSappId() + "_interface_accept");
        iCached.remove(batchNo + "_app_info");
        iCached.remove(batchNo + "_interface_info");
        return returnMap;
    }

    private Map<String,Object> verifyBaseInfo(HttpServletRequest request, String methodName, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        logger.info("验证基础参数方法开始。。。。 业务编号：{}, 应用ID：{}", batchNo, appId);
        String charset = params.get("charset");
        //验证商户APPId 是否正确
        AppManage app = appManageService.verifyAppId(appId);
        //记录商户平台信息
        iCached.put(batchNo + "_app_info", app, 2 * 60 * 60);
        //验证接口权限
        InterfaceInfo interfaceInfo = interfaceInfoService.verifyInterfaceAuth(methodName, app.getSuserId());
        //记录调用业务接口信息
        iCached.put(batchNo + "_interface_info", interfaceInfo, 2 * 60 * 60);
        //获取客户端IP
        String clientIp = IPUtils.getIpFromRequest(request);
        //新增 接口请求 受理 记录表 异步
        interfaceAcceptService.syncAddInterfaceAcceptRecord(batchNo, params, app, interfaceInfo, "", clientIp);
        //业务参数
        //String bizContent = BaseEncrypt.decryptContent(params.get("bizContent"), params.get("encryptType"), app.getSappSecretKey(), charset);
        String bizContent = params.get("bizContent");
        //保存请求文件 文件名称
        generateRequestDataFile(batchNo, appId, bizContent);
        //验证签名
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

        //logger.debug("返回参数，应用信息：{}，接口信息：{}, 返回业务参数：{}", app, interfaceInfo, bizContent);
        logger.info("基础参数校验成功");
        returnMap.put("bizContent", bizContent);
        returnMap.put("appManage", app);
        returnMap.put("interfaceInfo", interfaceInfo);
        return returnMap;
    }


    /***
     * 生成请求数据文件
     * @param batchNo 业务编号
     * @param appId APPId
     * @param bizContent 保存数据
     */
    private void generateRequestDataFile(final String batchNo, final String appId, final String bizContent) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("新增生成请求数据文件信息，业务编号：{}", batchNo);
                String filePath = EvnUtils.getValue("request.data.path");//文件保存位置
                String folder = DateUtils.getCurrentDTimeNumber();
                String filePathType = SysParaUtil.getValue("recognition_image_file_path_type");//文件服务器类型
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
                            logger.info("请求数据文件生成成功，业务编号：{}", batchNo);
                        } else {
                            logger.error("请求数据文件生成失败，业务编号：{}", batchNo);
                        }
                    }
                } catch (Exception e) {
                    logger.error("生成请求数据文件异常：{}", e);
                }
            }
        });
    }

    /**
     * 验证识别接口基础信息
     *
     * @param request 请求信息
     * @param batchNo 业务编号
     * @param appId   应用ID
     * @param params  接口参数集合
     * @throws ServiceException
     * @throws Exception
     */
    private Map<String, Object> verifyBaseInfo(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        logger.info("验证基础参数方法开始。。。。 业务编号：{}, 应用ID：{}", batchNo, appId);
        String charset = params.get("charset");
        String methodName = EncryptUtils.decryptStringUnZip(params.get("methodName"));
        //验证商户APPId 是否正确
        AppManage app = appManageService.verifyAppId(appId);
        //记录商户平台信息
        iCached.put(batchNo + "_app_info", app, 2 * 60 * 60);
        //验证接口权限
        InterfaceInfo interfaceInfo = interfaceInfoService.verifyInterfaceAuth(methodName, app.getSuserId());
        //记录调用业务接口信息
        iCached.put(batchNo + "_interface_info", interfaceInfo, 2 * 60 * 60);
        //获取客户端IP
        String clientIp = IPUtils.getIpFromRequest(request);
        //新增 接口请求 受理 记录表 异步
        interfaceAcceptService.syncAddInterfaceAcceptRecord(batchNo, params, app, interfaceInfo, "", clientIp);
        //业务参数
        String bizContent = BaseEncrypt.decryptContent(params.get("bizContent"), params.get("encryptType"), app.getSappSecretKey(), charset);
        //保存请求文件 文件名称
        generateRequestDataFile(batchNo, appId, bizContent);
        //验证签名
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

        //logger.debug("返回参数，应用信息：{}，接口信息：{}, 返回业务参数：{}", app, interfaceInfo, bizContent);
        logger.info("基础参数校验成功");
        returnMap.put("bizContent", bizContent);
        returnMap.put("appManage", app);
        returnMap.put("interfaceInfo", interfaceInfo);
        return returnMap;
    }

    /**
     * 图片识别接口
     *
     * @param modelCode   模型编号
     * @param imgPaths    对应识别图片地址
     * @param batchNo     业务编号
     * @param app         应用信息
     * @param imageDetail 识别图片集合
     * @param deviceId    设备信息
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
        paramMap.put("irunStatus", "30");//初始化成功的服务器可以识别
        UserInfo userInfo = userInfoService.selectByPrimaryKey(app.getSuserId());
        if (StringUtil.isNotBlank(modelCode)) {
            paramMap.put("modelCode", modelCode);
        }
        if (StringUtil.isNotBlank(userInfo.getSmerchantCode())) {
            paramMap.put("merchantCode", userInfo.getSmerchantCode());
        } else {
            paramMap.put("appId", app.getSappId());
        }
        //获取识别服务器数据
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
                    logger.info("正在等待释放识别服务器。已等待" + tempNum + "秒");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("获取服务休眠等待异常：{}", e);
                }
            }
            rnum = random.nextInt(serverList.size());
            tempServer = serverList.get(rnum);
            //for (ServerList tempServer : serverList) {
            try {
                tempStr = (String) iCached.get("busy_gpu_server_" + tempServer.getScode());
            } catch (Exception e) {
                tempStr = tempServer.getScode();
                logger.error("获取正在识别服务器异常：{}", e);
            }
            if (StringUtil.isBlank(tempStr)) {
                connection = VisualUtils.getGoodsRecognition().getConnsMap().get(tempServer.getScode());
                if (null != connection) {
                    try {
                        iCached.put("busy_gpu_server_" + tempServer.getScode(), tempServer.getScode(), busyTimeOut);//服务器存放时间 1分钟
                        iCached.put("busy_gpu_server_batch_no_" + batchNo, tempServer.getScode(), busyTimeOut);
                    } catch (Exception e) {
                        logger.error("存储正在识别服务器繁忙信息异常：{}", e);
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
        //组装识别参数
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
            if (null == fileTemp || !fileTemp.exists() || !fileTemp.isFile()) {//判断文件是否存在
                logger.error("获取识别图片为空，处理批次：{}, 文件地址信息：{}", batchNo, cellBean.getFilePath());
                throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
            }else{
                logger.info("图片存在{}",cellBean.getFilePath());
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
            logger.error("移除正在识别服务器繁忙信息异常：{}", e);
        }
        logger.info("图片识别返回结果，业务编号：{} 返回结果：{}", batchNo, result);
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
     * 验证接口余额
     *
     * @param userId        用户ID
     * @param interfaceCode 接口编号
     * @param tollNum       收费次数
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
        //锁定 操作接口账户信息
        account = interfaceAccountService.selectByPrimaryKeySync(account.getId());
        if (null == account.getIfreezeNum()) {
            account.setIfreezeNum(0);
        }
        if (account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.NUM_TOLL_TYPE.getCode()
                || account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()) {
            //按次收费
            if (account.getFbalance().intValue() < tollNum) {
                throw new ServiceException("BALANCE-NOT-ENOUGH");
            }
            //设置账户冻结次数
            account.setIfreezeNum(account.getIfreezeNum() + tollNum);
        } else if (account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.TIME_TOLL_TYPE.getCode()) {
            //按时间计费
            if (account.getIvalidityType().intValue() == BizTypeDefinitionEnum.ValidityType.FIXED_DATE.getCode()
                    && account.getTendTime().before(new Date())) {
                throw new ServiceException("BALANCE-NOT-ENOUGH");
            }
        } else if (account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.NUM_TOLL_TYPE.getCode()
                || account.getIaccountType().intValue() == BizTypeDefinitionEnum.InterfaceTollType.IMG_NUM_TOLL_TYPE.getCode()) {
            //按时间计费
            if (account.getIvalidityType().intValue() == BizTypeDefinitionEnum.ValidityType.FIXED_DATE.getCode()
                    && account.getTendTime().before(new Date())) {
                //按次收费
                if (account.getItransferNum().intValue() < tollNum) {
                    throw new ServiceException("BALANCE-NOT-ENOUGH");
                }
                //设置账户冻结次数
                account.setIfreezeNum(account.getIfreezeNum() + tollNum);
            }
        }
        return account;
    }

    /***
     * 验证识别图片有效性
     * @param interfaceCode 接口编号
     * @param request 请求数据
     * @param batchNo 业务编号
     * @param imgRecognition 识别图片数据
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
        //获取保存图片服务器位置 服务器路径前缀+日期yyyyMMdd+业务编号.
        String filePathType = SysParaUtil.getValue("recognition_image_file_path_type");//文件服务器类型
        String prefix = EvnUtils.getValue("upload.image.path.prefix");//服务器路径前缀
        String sharePrefix = EvnUtils.getValue("image.path.share.prefix");//共享文件服务器前缀
        String fileName = "";//保存图片文件名
        String tempFileName = "";//临时保存图片文件名
        String extName = "";//保存图片文件格式
        int imgType = -1;//1 base64 2 远程图片
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
                    logger.error("获取存储图片文件名错误");
                    throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
                }
                if (!extName.equalsIgnoreCase("jpg") && !extName.equalsIgnoreCase("jpeg")) {
                    logger.error("获取存储图片文件格式错误，处理批次：{}", batchNo);
                    throw new ServiceException("INVALID-PARAMETER-IMAGE-FORMAT-ERROR");
                }
            } catch (Exception e) {
                logger.error("获取存储图片文件名错误，处理批次：{}, 异常信息：{}", batchNo, e);
                throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
            }
            //保存、上传图片到服务器
            String folder = DateUtils.getCurrentDTimeNumber();
            if (filePathType.equals("windows")) {
                /*//ftp上传到静态资源服务器
                String serverPath = prefix + "/" + folder + "/" + batchNo + "/" + fileName;//上传文件路径

                //上传图片本地临时目录
                String rootPath = request.getSession().getServletContext().getRealPath("/");
                String tmpPath = EvnUtils.getValue("upload.image.tmp");
                String tempPath  = rootPath + tmpPath + fileName;//上传文件本地保存路径
                File tempfile = new File(rootPath + tmpPath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                saveImage(imgType, img.getImgBase64(), "", tempPath, batchNo);

                File file = new File(tempPath);
                boolean flag = ImageUtils.upLoadPic(file, prefix + "/" + folder + "/" + batchNo + "/", fileName);
                if (flag) {
                    if (imgType == 1) {
                        img.setImgBase64(serverPath);//替换base64 文件服务器位置
                    }
                    imgPaths.add(serverPath.replace(prefix,sharePrefix));
                }*/
                String uploadFilePath = prefix + "\\" + folder + "\\" + batchNo;
                File tempfile = new File(uploadFilePath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                String uploadPath = prefix + "\\" + folder + "\\" + batchNo + "\\" + fileName;//上传文件路径
                String serverPath = sharePrefix + "/" + folder + "/" + batchNo + "/" + fileName;//上传文件路径
                saveImage(imgType, img.getImgBase64(), img.getImgUrl(), uploadPath, batchNo);
                imgPaths.add(serverPath);
                if (imgType == 1) {
                    img.setImgBase64(serverPath);//替换base64 文件服务器位置
                }
                img.setUploadUrl(uploadPath);
            } else {//linux 直接保存到nfs文件共享目录
                String uploadFilePath = prefix + "/" + folder + "/" + batchNo;
                File tempfile = new File(uploadFilePath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                String serverPath = prefix + "/" + folder + "/" + batchNo + "/" + fileName;//上传文件路径
                saveImage(imgType, img.getImgBase64(), img.getImgUrl(), serverPath, batchNo);

                imgPaths.add(serverPath.replace(prefix, sharePrefix));
                if (imgType == 1) {
                    img.setImgBase64(serverPath);//替换base64 文件服务器位置
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
     * 保存图片
     *
     * @param imgType    图片类型 1 base64
     * @param fileBase64 图片base64数据
     * @param imgUrl     图片路径
     * @param serverPath 上传文件服务器位置
     * @param batchNo    业务编号
     * @throws ServiceException
     */
    private void saveImage(int imgType, String fileBase64, String imgUrl, String serverPath, String batchNo) throws ServiceException {
        if (imgType == 1) {
            boolean flag = ImageUtils.string2Image(fileBase64, serverPath);
            if (!flag) {
                logger.error("base64转换成图片错误，处理批次：{}, 保存地址：{}", batchNo, serverPath);
                throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
            }
        } else if (imgType == 2) {
            ImageUtils.downloadImage(imgUrl, serverPath);
        }
    }
}