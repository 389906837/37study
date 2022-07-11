package com.cloud.cang.open.api.op.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.op.InterfaceAcceptDefine;
import com.cloud.cang.open.api.common.APIConstant;
import com.cloud.cang.open.api.common.SubCodeEnum;
import com.cloud.cang.open.api.op.dao.InterfaceAcceptDao;
import com.cloud.cang.open.api.op.service.InterfaceAcceptService;
import com.cloud.cang.open.sdk.model.request.ImgRecognition;
import com.cloud.cang.open.sdk.model.request.ImgRecognitionDto;
import com.cloud.cang.open.sdk.model.request.QueryModel;
import com.cloud.cang.open.sdk.util.BaseSignature;
import com.cloud.cang.open.sdk.utils.EncryptUtils;
import com.cloud.cang.openapi.CommonParam;
import com.cloud.cang.openapi.RecongitionVo;
import com.cloud.cang.server.JobDto;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InterfaceAcceptServiceImpl extends GenericServiceImpl<InterfaceAccept, String> implements
        InterfaceAcceptService {

    private final static Logger logger = LoggerFactory.getLogger(InterfaceAcceptServiceImpl.class);
    @Autowired
    private ICached iCached;
    @Autowired
    InterfaceAcceptDao interfaceAcceptDao;

    @Override
    public GenericDao<InterfaceAccept, String> getDao() {
        return interfaceAcceptDao;
    }

    /**
     * 新增业务受理记录
     *
     * @param vo
     */
    @Override
    public void addInterfaceAcceptRecord(final RecongitionVo vo) {
        ExecutorManager.getInstance().execute(() -> {
            String batchNo = vo.getBatchNo();
            CommonParam commonParam = vo.getCommonParam();
            InterfaceInfo interfaceInfo = vo.getInterfaceInfo();
            AppManage appManage = vo.getAppManage();
            String outBatchNo = "";
            if (vo.getBizContent() instanceof ImgRecognitionDto) {
                outBatchNo = ((ImgRecognitionDto) vo.getBizContent()).getOutBatchNo();
            } else if (vo.getBizContent() instanceof QueryModel) {
                outBatchNo = ((QueryModel) vo.getBizContent()).getBatchNo();
            } else if (vo.getBizContent() instanceof ImgRecognition) {
                outBatchNo = ((ImgRecognition) vo.getBizContent()).getOutBatchNo();
            }

            InterfaceAccept accept = new InterfaceAccept();
            accept.setScode(batchNo);
            accept.setStpSerialNumber(outBatchNo);
            accept.setSinterfaceCode(interfaceInfo.getScode());
            accept.setSappId(appManage.getSappId());
            accept.setSappCode(appManage.getScode());
            accept.setSuserCode(appManage.getSuserCode());
            accept.setSuserId(appManage.getSuserId());
            accept.setScallbackAddress(commonParam.getNotifyUrl());

            // ImageDetail的imgBase64是加密字段，所以此时不作保存
            if (commonParam.getMethodName().equals(APIConstant.Recognition.RECOGNITION) ||
                    commonParam.getMethodName().equals(APIConstant.Recognition.RECOGNITION_SYNCHRONIZE) ||
                    commonParam.getMethodName().equals(APIConstant.Recognition.RECOGNITION_CALLBACK) ||
                    commonParam.getMethodName().equals(APIConstant.Recognition.RECOGNITION_YOLOV4_CALLBACK)) {
            } else {
                accept.setSrequestData(JSONUtil.toJsonStr(vo.getBizContent()));
            }
            accept.setSoperIp(vo.getClientIp());

            if (StringUtil.isNotBlank(commonParam.getTimestamp())) {
                DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                try {
                    accept.setSrequestTime(df.parse(commonParam.getTimestamp()));
                } catch (ParseException e) {
                    logger.error("解析请求时间戳异常，异常信息：{}", e);
                }
            } else {
                accept.setSrequestTime(new Date());
            }
            accept.setIisDealwith(0);
            accept.setIisNeedCallback(0);
            if (StringUtil.isNotBlank(commonParam.getNotifyUrl()) || interfaceInfo.getItype().intValue()
                    == BizTypeDefinitionEnum.InterfacType.ASYNCHRONOUS.getCode()) {
                accept.setIisNeedCallback(1);
            }
            accept.setIisCallback(0);
            accept.setIcallbackNum(0);
            accept.setIisCallbackSuccess(0);
            accept.setIisRequestSuccess(0);
            accept.setIdelFlag(0);
            accept.setAddTime(new Date());
            accept.setUpdateTime(new Date());
            interfaceAcceptDao.insert(accept);

            try {
                iCached.put(batchNo + appManage.getSappId() + APIConstant.RedisKey.INTERFACE_ACCEPT,
                        accept,
                        2 * APIConstant.SEC_OF_HOUR);
            } catch (Exception e) {
                logger.error("接口受理业务信息存放错误，错误信息：{}", e);
            }
        });
    }

    /**
     * 接口处理错误 更新业务受理记录
     *
     * @param batchNo       业务编号
     * @param app           应用信息
     * @param interfaceInfo
     * @param subCodeEnum   错误类型
     * @param params        请求参数
     */
    @Override
    public void updateInterfaceAcceptByFailed(String batchNo, AppManage app,
                                              InterfaceInfo interfaceInfo, SubCodeEnum subCodeEnum,
                                              CommonParam params) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("接口处理错误更新业务受理记录，业务编号：{}", batchNo);
                try {
                    //获取业务记录信息
                    InterfaceAccept accept = getInterfaceAccept(batchNo, app.getSappId());
                    if (null != accept) {
                        InterfaceAccept updateAccept = new InterfaceAccept();
                        updateAccept.setId(accept.getId());
                        updateAccept.setTrequestFinishTime(new Date());
                        updateAccept.setIisDealwith(1);
                        updateAccept.setUpdateTime(new Date());
                        //回调参数
                        Map<String, Object> tempMap = new HashMap<String, Object>();
                        tempMap.put("code", "200");
                        tempMap.put("msg", "success");
                        tempMap.put("subCode", subCodeEnum.getCode());
                        tempMap.put("subMsg", subCodeEnum.getReturnMsg());

                        //1、参数签名字段
                        String signContent = JSONObject.toJSONString(tempMap);
                        /*//1、参数签名字段
                        String signContent = BaseSignature.getSignContent(signContentTemp);*/

                        Map<String, Object> backMap = new HashMap<String, Object>();
                        tempMap.put("error_response", signContent);
                        //2、对参数进行签名
                        String sign = BaseSignature.rsaSign(signContent, app.getSplatformKey(), params.getCharset(), params.getSignType());
                        tempMap.put("sign", sign);

                        backMap.put("body", tempMap);
                        String backStr = JSONObject.toJSONString(backMap);
                        //3、整体加密
                        String encryptBackStr = EncryptUtils.encryptStringUnZip(backStr);

                        updateAccept.setSresponseData(backStr);
                        updateAccept.setSresponseEncryData(encryptBackStr);

                        if (null != interfaceInfo.getItype() && interfaceInfo.getItype().intValue() != BizTypeDefinitionEnum.InterfacType.ASYNCHRONOUS.getCode()) {
                            tempMap = new HashMap<String, Object>();
                            tempMap.put("code", "200");
                            tempMap.put("msg", "success");
                            tempMap.put("subCode", subCodeEnum.getCode());
                            tempMap.put("subMsg", subCodeEnum.getReturnMsg());
                            tempMap.put("appId", app.getSappId());
                            tempMap.put("appSecretKey", app.getSappSecretKey());
                            tempMap.put("methodName", params.getMethodName());
                            //1、参数签名字段
                            signContent = JSONObject.toJSONString(tempMap);
                            //2、对参数进行签名
                            sign = BaseSignature.rsaSign(signContent, app.getSplatformKey(), params.getCharset(), params.getSignType());
                            tempMap.put("sign", sign);
                            backStr = JSONObject.toJSONString(tempMap);
                            //3、整体加密
                            encryptBackStr = EncryptUtils.encryptStringUnZip(backStr);

                            updateAccept.setScallbackData(backStr);
                            updateAccept.setScallbackEncryData(encryptBackStr);
                        }
                        interfaceAcceptDao.updateByIdSelective(updateAccept);
                    }
                } catch (Exception e) {
                    logger.error("接口处理错误更新业务受理记录错误，错误信息：{}", e);
                }
            }
        });
    }

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
    @Override
    public void updateInterfaceAcceptBySuccess(String outBatchNo, String requestData, Integer imgSize,
                                               Integer successNum, String batchNo,
                                               String backBody, String encryptBackBody,
                                               String callbackBody, String encryptCallbackBody,
                                               AppManage app, CommonParam params) {
        ExecutorManager.getInstance().execute(() -> {
            logger.info("接口处理成功更新业务受理记录，业务编号：{}", batchNo);
            try {
                //获取业务记录信息
                InterfaceAccept accept = getInterfaceAccept(batchNo, app.getSappId());
                if (null != accept) {
                    InterfaceAccept updateAccept = new InterfaceAccept();
                    updateAccept.setId(accept.getId());
                    updateAccept.setSrequestData(requestData);
                    updateAccept.setStpSerialNumber(outBatchNo);
                    updateAccept.setTrequestFinishTime(new Date());
                    updateAccept.setIisDealwith(1);
                    updateAccept.setScallbackAddress(params.getNotifyUrl());
                    updateAccept.setUpdateTime(new Date());
                    updateAccept.setIisRequestSuccess(1);
                    updateAccept.setSresponseData(backBody);
                    updateAccept.setSresponseEncryData(encryptBackBody);
                    updateAccept.setScallbackData(callbackBody);
                    updateAccept.setScallbackEncryData(encryptCallbackBody);
                    updateAccept.setItollNum(imgSize);
                    updateAccept.setIactualTollNum(successNum);
                    interfaceAcceptDao.updateByIdSelective(updateAccept);
                }
            } catch (Exception e) {
                logger.error("接口处理错误更新业务受理记录错误，错误信息：{}", e);
            }
        });
    }

    /**
     * 判断第三方业务编号是否存在
     *
     * @param interfaceCode 接口编号
     * @param outBatchNo    第三方业务编号
     * @return
     */
    @Override
    public InterfaceAccept selectByOutBatchNo(String interfaceCode, String outBatchNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("interfaceCode", interfaceCode);
        paramMap.put("outBatchNo", outBatchNo);
        paramMap.put("bussessType", "10");
        return interfaceAcceptDao.selectByMap(paramMap);
    }

    /***
     * 获取商户业务订单信息
     * @param paramMap 查询参数
     * @return
     */
    @Override
    public InterfaceAccept selectByMap(Map<String, Object> paramMap) {
        return interfaceAcceptDao.selectByParamMap(paramMap);
    }

    /**
     * 获取接口受理信息
     *
     * @param batchNo 业务编号
     * @param sappId  应用ID
     * @return
     * @throws Exception
     */
    @Override
    public InterfaceAccept getInterfaceAccept(String batchNo, String sappId) throws Exception {
        InterfaceAccept accept = (InterfaceAccept) iCached.get(batchNo + sappId + APIConstant.RedisKey.INTERFACE_ACCEPT);
        if (null == accept) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("batchNo", batchNo);
            paramMap.put("appId", sappId);
            accept = interfaceAcceptDao.selectByMap(paramMap);
        }
        return accept;
    }

    /**
     * 接口回调成功 更新业务受理记录
     *
     * @param batchNo
     * @param appid
     */
    @Override
    public void updateInterfaceAcceptByCallbackSuccess(String batchNo, String appid) {
        //获取业务记录信息
        InterfaceAccept accept = null;
        try {
            accept = getInterfaceAccept(batchNo, appid);
            InterfaceAccept temp = new InterfaceAccept();
            temp.setId(accept.getId());
            temp.setIisCallback(1);
            temp.setIisCallbackSuccess(1);
            temp.setTcallbackSuccessTime(DateUtils.getCurrentDateTime());
            temp.setIcallbackNum(accept.getIcallbackNum() + 1);
            this.updateBySelective(temp);
        } catch (Exception e) {
            logger.error("回调成功后更新平台接口业务受理信息异常：{}", accept.getId());
        }
    }

    /**
     * 接口回调失败 更新业务受理记录
     *
     * @param batchNo
     * @param appid
     */
    @Override
    public void updateInterfaceAcceptByCallbackFaild(String batchNo, String appid) {
        //获取业务记录信息
        InterfaceAccept accept = null;
        try {
            accept = getInterfaceAccept(batchNo, appid);
            InterfaceAccept temp = new InterfaceAccept();
            temp.setId(accept.getId());
            temp.setIisCallback(1);
            temp.setIisCallbackSuccess(0);
            temp.setIcallbackNum(accept.getIcallbackNum() + 1);
            this.updateBySelective(temp);
            String isSendMessage = BizParaUtil.get("interface_accept_callback_maxnum");
            if (accept.getIisCallbackSuccess() == 1 || accept.getIcallbackNum() >= Integer.valueOf(isSendMessage)) {
                //回调次数大于限制次数 不再回调
                logger.info("失败,回调次数大于限制次数,不再回调:{}", accept.getId());
                return;
            }
            //计算回调时间
            Date time = ComputationTimes(temp.getIcallbackNum());
            //定时任务再次回调
            //定时更新
            JobDto jobDto = new JobDto();
            jobDto.setJobName(accept.getId());
            jobDto.setTime(time);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InterfaceAcceptDefine.INTERFACE_ACCEPT_CALLBACK);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            invoke.setRequestObj(jobDto); // post 参数
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
        } catch (Exception e) {
            logger.error("回调失败后更新平台接口业务受理信息异常：{}", accept.getId());
        }
    }

    @Override
    public void updateInterfaceAcceptByFailedToWeb(String batchNo, AppManage app, InterfaceInfo interfaceInfo, SubCodeEnum subCodeEnum, Map<String, String> params) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("接口处理错误更新业务受理记录，业务编号：{}", batchNo);
                try {
                    //获取业务记录信息
                    InterfaceAccept accept = getInterfaceAccept(batchNo, app.getSappId());
                    if (null != accept) {
                        InterfaceAccept updateAccept = new InterfaceAccept();
                        updateAccept.setId(accept.getId());
                        updateAccept.setTrequestFinishTime(new Date());
                        updateAccept.setIisDealwith(1);
                        updateAccept.setUpdateTime(new Date());
                        //回调参数
                        Map<String, Object> tempMap = new HashMap<String, Object>();
                        tempMap.put("code", "200");
                        tempMap.put("msg", "success");
                        tempMap.put("subCode", subCodeEnum.getCode());
                        tempMap.put("subMsg", subCodeEnum.getReturnMsg());

                        //1、参数签名字段
                        String signContent = JSONObject.toJSONString(tempMap);
                        /*//1、参数签名字段
                        String signContent = BaseSignature.getSignContent(signContentTemp);*/

                        Map<String, Object> backMap = new HashMap<String, Object>();
                        tempMap.put("error_response", signContent);
                        //2、对参数进行签名
                        String sign = BaseSignature.rsaSign(signContent, app.getSplatformKey(), params.get("charset"), params.get("signType"));
                        tempMap.put("sign", sign);

                        backMap.put("body", tempMap);
                        String backStr = JSONObject.toJSONString(backMap);
                        //3、整体加密
                        String encryptBackStr = EncryptUtils.encryptStringUnZip(backStr);

                        updateAccept.setSresponseData(backStr);
                        updateAccept.setSresponseEncryData(encryptBackStr);

                        if (null != interfaceInfo.getItype() && interfaceInfo.getItype().intValue() != BizTypeDefinitionEnum.InterfacType.ASYNCHRONOUS.getCode()) {
                            tempMap = new HashMap<String, Object>();
                            tempMap.put("code", "200");
                            tempMap.put("msg", "success");
                            tempMap.put("subCode", subCodeEnum.getCode());
                            tempMap.put("subMsg", subCodeEnum.getReturnMsg());
                            tempMap.put("appId", app.getSappId());
                            tempMap.put("appSecretKey", app.getSappSecretKey());
                            tempMap.put("methodName", params.get("methodName"));
                            //1、参数签名字段
                            signContent = JSONObject.toJSONString(tempMap);
                            //2、对参数进行签名
                            sign = BaseSignature.rsaSign(signContent, app.getSplatformKey(), params.get("charset"), params.get("signType"));
                            tempMap.put("sign", sign);
                            backStr = JSONObject.toJSONString(tempMap);
                            //3、整体加密
                            encryptBackStr = EncryptUtils.encryptStringUnZip(backStr);

                            updateAccept.setScallbackData(backStr);
                            updateAccept.setScallbackEncryData(encryptBackStr);
                        }
                        interfaceAcceptDao.updateByIdSelective(updateAccept);
                    }
                } catch (Exception e) {
                    logger.error("接口处理错误更新业务受理记录错误，错误信息：{}", e);
                }
            }
        });
    }

    /**
     * 计算发送时间
     *
     * @param num
     * @return
     */
    private Date ComputationTimes(int num) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        Date time = null;
        String temp = GrpParaUtil.getValue("SP000164", "callback_" + num);
        switch (num) {
            case 1:
                nowTime.add(Calendar.MINUTE, Integer.valueOf(temp));
                time = nowTime.getTime();
                break;
            case 2:
                nowTime.add(Calendar.MINUTE, Integer.valueOf(temp));
                time = nowTime.getTime();
                break;
            case 3:
                nowTime.add(Calendar.MINUTE, Integer.valueOf(temp));
                time = nowTime.getTime();
                break;
            default:
                break;
        }
        return time;
    }

}