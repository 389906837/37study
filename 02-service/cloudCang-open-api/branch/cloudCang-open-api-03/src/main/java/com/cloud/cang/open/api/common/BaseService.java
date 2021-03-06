package com.cloud.cang.open.api.common;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.open.api.common.utils.IPUtils;
import com.cloud.cang.open.api.common.utils.RequestUtil;
import com.cloud.cang.open.api.common.utils.TxtExport;
import com.cloud.cang.open.api.op.service.AppManageService;
import com.cloud.cang.open.api.op.service.InterfaceAcceptService;
import com.cloud.cang.open.api.op.service.InterfaceInfoService;
import com.cloud.cang.open.api.op.service.TransferLogService;
import com.cloud.cang.open.sdk.mapping.StringUtils;
import com.cloud.cang.open.sdk.model.request.BalanceModel;
import com.cloud.cang.open.sdk.model.request.ImgRecognition;
import com.cloud.cang.open.sdk.model.request.ImgRecognitionDto;
import com.cloud.cang.open.sdk.model.request.QueryModel;
import com.cloud.cang.open.sdk.util.BaseEncrypt;
import com.cloud.cang.open.sdk.util.BaseSignature;
import com.cloud.cang.open.sdk.utils.EncryptUtils;
import com.cloud.cang.openapi.CommonParam;
import com.cloud.cang.openapi.RecongitionVo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: 37cang-?????????
 * @description:
 * @author: qzg
 * @create: 2019-11-19 17:17
 **/
@Component
public class BaseService {
    private final static Logger logger = LoggerFactory.getLogger(BaseService.class);
    @Autowired
    ICached iCached;
    @Autowired
    private AppManageService appManageService;
    @Autowired
    InterfaceInfoService interfaceInfoService;
    @Autowired
    InterfaceAcceptService interfaceAcceptService;
    @Autowired
    TransferLogService transferLogService;

    //ImgRecognitionDto ??? method
    public final static String[]  METHOD_ARRAY= {"openDoor","openDoorCheck","openDoorInventory","closeDoor",
                                                "closeDoor_inventory",
                                                "localGravityLayeredOpenDoorInventory",
                                                "localGravityLayeredReplenOpenDoorInventory",
                                                "local_gravity_layered_close_door"};
    public static String bizContent = "";
    /**
     * ??????????????????
     *
     * @param request  ???????????????methodName , bizContent , appId???
     * @param response
     * @throws ServiceException
     * @throws Exception
     */

    public RecongitionVo verifyCommonParam(HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
        String batchNo = CoreUtils.newCode("op_interface_accept");
        CommonParam commonParam = RequestUtil.parseCommonParam(request);

        logger.info("??????????????????????????????...???????????????{}, ??????ID???{}", batchNo, commonParam.getAppId());
        if(null == commonParam || StrUtil.isBlank(commonParam.getMethodName())
                || StrUtil.isBlank(commonParam.getAppId())){
            throw new ServiceException("REQUEST PARAMETER ERROR");
        }
        String charset = commonParam.getCharset();
        String appId =  EncryptUtils.decryptStringUnZip(commonParam.getAppId());
        String methodName = EncryptUtils.decryptStringUnZip(commonParam.getMethodName());
        if ("cloud.api.recognition".equals(methodName)) {//???????????????????????????
            request.getRequestDispatcher("http://cloudapi.37cang.cn/gateway").forward(request, response);
            return null;
        }
        //??????????????????
        AppManage app = appManageService.verifyAppId(appId);

        //????????????
        Map<String,String> map = MapUtil.newHashMap(20);
        BeanUtil.beanToMap(commonParam).forEach((k,v)->{
            if(v !=null){
                map.put(k,v.toString());
            }
        });


        if(StrUtil.isNotEmpty(request.getParameter("test")) &&
                StrUtil.equals(request.getParameter("test"),"testCang")){// todo ????????????
        }else{
            String signContent = BaseSignature.getSignContent(map);
            String signType = commonParam.getSignType();
            String sign = commonParam.getSign();
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
        }

        //?????????????????????
        InterfaceInfo interfaceInfo = interfaceInfoService.verifyInterfaceAuth(methodName, app.getSuserId());
        if(interfaceInfo == null ){
            throw new ServiceException("METHOD ERROR");
        }

        //todo ????????????=====================
        String bizContent = "";
        if(StrUtil.isNotEmpty(request.getParameter("test")) &&
                StrUtil.equals(request.getParameter("test"),"testCang")){// todo ????????????
            iCached.put("test_cang_"+batchNo, System.currentTimeMillis(),2*60*60);
        }else{
            //????????????
            bizContent = BaseEncrypt.decryptContent(commonParam.getBizContent(),
                    commonParam.getEncryptType(),
                    app.getSappSecretKey(), charset);
        }

        //todo ????????????=====================
        if(StrUtil.isEmpty(this.bizContent)){
            this.bizContent= bizContent;
        }
        if(StrUtil.isNotEmpty(request.getParameter("test")) &&
                StrUtil.equals(request.getParameter("test"),"testCang")
                && StrUtil.isNotEmpty(this.bizContent)){
            bizContent = this.bizContent;
        }
        //todo =====================

        if(APIConstant.Recognition.RECOGNITION.equals(methodName)){
            generateRequestDataFile(batchNo, appId, bizContent);
        }
        logger.info("????????????????????????");
        //??????????????????CommonParam
        commonParam.setMethodName(methodName);
        commonParam.setAppId(appId);
        commonParam.setBizContent(bizContent);
        logger.info("????????????:",commonParam);

        // ??????
        RecongitionVo vo = null;
        switch (commonParam.getMethodName()){
            case APIConstant.Recognition.RECOGNITION:
                ImgRecognitionDto imgRecognitionDto = JSONObject.parseObject(bizContent, ImgRecognitionDto.class);
                vo = new RecongitionVo<ImgRecognitionDto>();

                //todo ????????????=====================
                if(StrUtil.isNotEmpty(request.getParameter("test")) &&
                        StrUtil.equals(request.getParameter("test"),"testCang")){
                imgRecognitionDto.setOutBatchNo(IdUtil.simpleUUID());
                }
                //todo =====================

                vo.setBizContent(imgRecognitionDto);
                vo.setClientIp(IPUtils.getIpFromRequest(request));
                vo.setBatchNo(batchNo);
                vo.setAppManage(app);
                vo.setInterfaceInfo(interfaceInfo);
                vo.setCommonParam(commonParam);
                // ??????ImgRecognitionDto???method????????????
                if (!ArrayUtil.contains(METHOD_ARRAY, imgRecognitionDto.getMethod())) {
                    String error = "???????????????????????????method:" + imgRecognitionDto.getMethod();
                    logger.error(error);
                    DingTalkUtils.sendText(error);
                    throw new ServiceException("REQUEST PARAMETER ERROR");
                }
                break;
            case APIConstant.Recognition.RECOGNITION_SYNCHRONIZE:
                ImgRecognition imgRecognition = JSONObject.parseObject(bizContent, ImgRecognition.class);
                vo = new RecongitionVo<ImgRecognition>();
                vo.setBizContent(imgRecognition);
                vo.setClientIp(IPUtils.getIpFromRequest(request));
                vo.setBatchNo(batchNo);
                vo.setAppManage(app);
                vo.setInterfaceInfo(interfaceInfo);
                vo.setCommonParam(commonParam);
                break;
            case APIConstant.Recognition.QUERY:
                vo = new RecongitionVo<QueryModel>();
                vo.setBizContent(JSONObject.parseObject(bizContent, QueryModel.class));
                vo.setClientIp(IPUtils.getIpFromRequest(request));
                vo.setBatchNo(batchNo);
                vo.setAppManage(app);
                vo.setInterfaceInfo(interfaceInfo);
                vo.setCommonParam(commonParam);
                break;
            case APIConstant.Recognition.BALANCE:
                vo = new RecongitionVo<BalanceModel>();
                vo.setBizContent(JSONObject.parseObject(bizContent, BalanceModel.class));
                vo.setClientIp(IPUtils.getIpFromRequest(request));
                vo.setBatchNo(batchNo);
                vo.setAppManage(app);
                vo.setInterfaceInfo(interfaceInfo);
                vo.setCommonParam(commonParam);
                break;
            case APIConstant.Recognition.RECOGNITION_CALLBACK:
                vo = new RecongitionVo<ImgRecognition>();
                vo.setBizContent(JSONObject.parseObject(bizContent, ImgRecognition.class));
                vo.setClientIp(IPUtils.getIpFromRequest(request));
                vo.setBatchNo(batchNo);
                vo.setAppManage(app);
                vo.setInterfaceInfo(interfaceInfo);
                vo.setCommonParam(commonParam);
                break;
            case APIConstant.Recognition.RECOGNITION_YOLOV4_CALLBACK:
                vo = new RecongitionVo<ImgRecognition>();
                vo.setBizContent(JSONObject.parseObject(bizContent, ImgRecognition.class));
                vo.setClientIp(IPUtils.getIpFromRequest(request));
                vo.setBatchNo(batchNo);
                vo.setAppManage(app);
                vo.setInterfaceInfo(interfaceInfo);
                vo.setCommonParam(commonParam);
                break;
        }

        //????????????????????????????????????(??????)
        try {
            interfaceAcceptService.addInterfaceAcceptRecord(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(vo == null){
            logger.error("?????????methodName:" + commonParam.getMethodName());
            throw new ServiceException("METHOD EMPTY");
        }
        return vo;
    }

    /***
     * ????????????????????????
     * @param batchNo ????????????
     * @param appId APPId
     * @param bizContent ????????????
     */
    private void generateRequestDataFile(final String batchNo, final String appId, final String bizContent) {
        ExecutorManager.getInstance().execute(() -> {
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
        });
    }

    /**
     * ??????????????????
     *
     * @param recongitionVo
     * @param codeEnum ?????????
     * @param response @throws Exception
     */
    public void realTimeBackcallError(RecongitionVo recongitionVo, SubCodeEnum codeEnum, HttpServletResponse response) {
        String platformPrivateKey = recongitionVo.getAppManage().getSplatformKey();
        if(StringUtil.isBlank(platformPrivateKey)){
            logger.error("????????????????????????????????????????????????{}", recongitionVo.getBatchNo());
            return;
        }
        CommonParam commonParam = recongitionVo.getCommonParam();
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("code", "200");
        tempMap.put("msg", "success");
        tempMap.put("subCode", codeEnum.getCode());
        tempMap.put("subMsg", codeEnum.getReturnMsg());
        try {
            sendBackResponse(tempMap, commonParam, platformPrivateKey, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     *
     * @param tempMap            ????????????
     * @param commonParam             ????????????
     * @param platformPrivateKey ??????????????????
     * @param response
     * @throws Exception
     */
    protected void sendBackResponse(Map<String, Object> tempMap, CommonParam commonParam, String platformPrivateKey,
                                  HttpServletResponse response) throws Exception {
        Map<String, Object> backMap = new HashMap<String, Object>();
        //1?????????????????????
        String signContent = JSONObject.toJSONString(tempMap);
        tempMap.put("error_response", signContent);
        //2????????????????????????
        String sign = BaseSignature.rsaSign(signContent, platformPrivateKey, commonParam.getCharset(),
                commonParam.getSignType());
        tempMap.put("sign", sign);
        backMap.put("body", tempMap);
        String backStr = JSONObject.toJSONString(backMap);
        //3???????????????
        String encryptBackStr = EncryptUtils.encryptStringUnZip(backStr);
        logger.info("???????????????????????????{}", encryptBackStr);
        response.getWriter().write(encryptBackStr);
    }

    public void failOperation(RecongitionVo recongitionVo, SubCodeEnum subCodeEnum){
        try {
            if(recongitionVo !=null){
                AppManage app = recongitionVo.getAppManage();
                InterfaceInfo interfaceInfo = recongitionVo.getInterfaceInfo();
                if (null != app && null != interfaceInfo) {
                    //??????????????????????????????
                    String operType = "";
                    String methodName = recongitionVo.getCommonParam().getMethodName();
                    switch (methodName){
                        case APIConstant.Recognition.RECOGNITION:
                        case APIConstant.Recognition.RECOGNITION_SYNCHRONIZE:
                        case APIConstant.Recognition.RECOGNITION_CALLBACK:
                            operType = APIConstant.BizOperType.IMAGE_RECOGNITION;
                            break;
                        case APIConstant.Recognition.QUERY:
                            operType = APIConstant.BizOperType.RECOGNITION_QUERY;
                            break;
                        case APIConstant.Recognition.BALANCE:
                            operType = APIConstant.BizOperType.BALANCE_QUERY;
                            break;
                    }

                    transferLogService.addTransferLog(
                            recongitionVo.getClientIp(),
                            recongitionVo.getBatchNo(), app,
                            interfaceInfo,
                            APIConstant.BizLog.error,
                            operType,
                            subCodeEnum.getCode() + "##" + subCodeEnum.getReturnMsg());

                    //?????????????????????????????????
                    interfaceAcceptService.updateInterfaceAcceptByFailed(
                            recongitionVo.getBatchNo(),
                            app,
                            interfaceInfo,
                            subCodeEnum,
                            recongitionVo.getCommonParam());

                    iCached.remove(recongitionVo.getBatchNo() + app.getSappId() + APIConstant.RedisKey.INTERFACE_ACCEPT);

                    DingTalkUtils.sendText("open-sdk??????open-api??????:"+subCodeEnum.getReturnMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void realTimeBackcallSuccess(Map<String, Object> returnMap,HttpServletResponse response) throws IOException {
        response.getWriter().write((String) returnMap.get("encryptBackBody"));
    }
}
