package com.cloud.cang.pay.hy.service.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayUserAgreementQueryRequest;
import com.alipay.api.request.AlipayUserAgreementUnsignRequest;
import com.alipay.api.response.AlipayUserAgreementQueryResponse;
import com.alipay.api.response.AlipayUserAgreementUnsignResponse;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.WechatConfigure;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.*;
import com.cloud.cang.pay.common.utils.IdGenerator;
import com.cloud.cang.pay.hy.service.MemberInfoService;
import com.cloud.cang.pay.hy.vo.QuerySignVo;
import com.cloud.cang.pay.hy.vo.UnsignVo;
import com.cloud.cang.pay.sh.service.MerchantInfoService;
import com.cloud.cang.pay.wechat.common.Signature;
import com.cloud.cang.pay.wechat.protocol.UnifiedOrderReqData;
import com.cloud.cang.pay.wechat.protocol.UnifiedQuerySignReqData;
import com.cloud.cang.pay.wechat.protocol.UnifiedUnsignReqData;
import com.cloud.cang.pay.wechat.service.WxPayApi;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.pay.hy.dao.WechatFreeDataDao;
import com.cloud.cang.model.hy.WechatFreeData;
import com.cloud.cang.pay.hy.service.WechatFreeDataService;

@Service
public class WechatFreeDataServiceImpl extends GenericServiceImpl<WechatFreeData, String> implements
        WechatFreeDataService {

    @Autowired
    WechatFreeDataDao wechatFreeDataDao;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private WxPayApi wxPayApi;
    private static final Logger logger = LoggerFactory.getLogger(WechatFreeDataServiceImpl.class);

    @Override
    public GenericDao<WechatFreeData, String> getDao() {
        return wechatFreeDataDao;
    }

    /**
     * ?????????????????????
     *
     * @param signDto ????????????
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<String> wechatSign(SignDto signDto) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //????????????????????????????????????
        WechatFreeData freeData = this.selectByMemberId(signDto.getSmemberId(), signDto.getSmemberMerchantCode());
        boolean flag = false;
        if (freeData == null) {
            freeData = new WechatFreeData();
            freeData.setScontractCode(IdGenerator.randomUUID(32));
            freeData.setSmemberId(signDto.getSmemberId());
            freeData.setSmemberName(signDto.getSmemberName());
            freeData.setSmemberNo(signDto.getSmemberCode());
            freeData.setSmerchantCode(signDto.getSmemberMerchantCode());
            freeData.setIrequestSerial(Integer.parseInt(CoreUtils.newCode("hy_wechat_free_data")));
            flag = true;
        } else {
            //freeData.setScontractCode(IdGenerator.randomUUID(32));
            //flag = true;
        }
        //????????????????????????
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(signDto.getSmerchantCode(), 2);
        if (conf == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sb = new StringBuffer(WechatConfigure.FREE_SIGN_URL + "?");
        sb.append("appid=" + conf.getSappId());
        map.put("appid", conf.getSappId());
        sb.append("&mch_id=" + conf.getSpid());
        map.put("mch_id", conf.getSpid());
        sb.append("&plan_id=" + conf.getSplanId());
        map.put("plan_id", conf.getSplanId());
        sb.append("&contract_code=" + freeData.getScontractCode());
        map.put("contract_code", freeData.getScontractCode());
        sb.append("&request_serial=" + freeData.getIrequestSerial());
        map.put("request_serial", freeData.getIrequestSerial());
        sb.append("&contract_display_account=" + signDto.getSmemberName());
        map.put("contract_display_account", signDto.getSmemberName());
        String notify_url = GrpParaUtil.getDetailForName(CoreConstant.WECHAT_FREE_CONFIG, "sign_notify_url").getSvalue();
        sb.append("&notify_url=" + URLEncoder.encode(notify_url, "UTF-8"));
        map.put("notify_url", notify_url);
        sb.append("&version=1.0");
        map.put("version", "1.0");
        long timestamp = System.currentTimeMillis() / 1000;
        sb.append("&timestamp=" + timestamp);
        map.put("timestamp", timestamp);
        if (StringUtil.isNotBlank(signDto.getSip())) {
            sb.append("&clientip=" + signDto.getSip());
            map.put("clientip", signDto.getSip());
        }
        sb.append("&outerid=" + signDto.getSmemberCode());
        map.put("outerid", signDto.getSmemberCode());
        /*sb.append("&return_web=1");
        map.put("return_web", 1);*/
        sb.append("&sign=" + Signature.getSign(map, conf));

        if (flag) {//??????????????????
            this.saveOrUpdate(freeData);
        }
        String url = sb.toString();
        if (StringUtil.isNotBlank(url)) {
            responseVo.setData(url);
            logger.info("?????????????????????????????????{}", responseVo.getData());
            return responseVo;
        }

        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("????????????????????????????????????");
        return responseVo;
    }

    /**
     * ????????????????????????
     *
     * @param smemberId     ????????????
     * @param smerchantCode ????????????
     * @return
     */
    @Override
    public WechatFreeData selectByMemberId(String smemberId, String smerchantCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("smemberId", smemberId);
        map.put("smerchantCode", smerchantCode);
        return wechatFreeDataDao.selectByMemberId(map);
    }

    /**
     * ??????????????????
     *
     * @param freeData ????????????
     */
    @Override
    public WechatFreeData saveOrUpdate(WechatFreeData freeData) {
        if (StringUtil.isBlank(freeData.getId())) {//??????
            MerchantInfo merchantInfo = merchantInfoService.selectByCode(freeData.getSmerchantCode());
            freeData.setSmerchantId(merchantInfo.getId());
            freeData.setTaddTime(new Date());
            freeData.setTupdateTime(new Date());
            wechatFreeDataDao.insert(freeData);
        } else {//??????
            freeData.setTupdateTime(new Date());
            wechatFreeDataDao.updateByIdSelective(freeData);
        }
        return freeData;
    }

    /**
     * ????????????????????????
     *
     * @param contractCode  ????????????????????????
     * @param smerchantCode ????????????
     * @return
     */
    @Override
    public WechatFreeData selectByContractCode(String contractCode, String smerchantCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contractCode", contractCode);
        map.put("smerchantCode", smerchantCode);
        return wechatFreeDataDao.selectByContractCode(map);
    }

    /**
     * ????????????????????????
     *
     * @param querySignDto ????????????
     * @return
     */
    @Override
    public ResponseVo<WechatSignResult> wechatQuerySign(QuerySignDto querySignDto) throws Exception {
        ResponseVo<WechatSignResult> responseVo = ResponseVo.getSuccessResponse();
        //????????????????????????????????????
        WechatFreeData freeData = this.selectByMemberId(querySignDto.getSmemberId());
        if (null == freeData) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????");
        }
        //????????????????????????
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(querySignDto.getSmerchantCode(), 2);
        if (null == conf) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????");
        }

        UnifiedQuerySignReqData querySignReqData = new UnifiedQuerySignReqData.UnifiedQuerySignReqDataBuilder(conf, freeData.getScontractCode(), "").build();
        Map<String, Object> resMap = wxPayApi.unifiedQuerySign(conf, querySignReqData);
        if (null != resMap) {
            logger.info("???????????????????????????????????????{}", JSON.toJSONString(resMap));
            if ("SUCCESS".equals(resMap.get("return_code"))) {
                if ("SUCCESS".equals(resMap.get("result_code"))) {
                    //????????????
                    WechatSignResult signResult = new WechatSignResult();
                    signResult.setMch_id(String.valueOf(resMap.get("mch_id")));
                    signResult.setAppid(String.valueOf(resMap.get("appid")));
                    signResult.setContract_id(String.valueOf(resMap.get("contract_id")));
                    signResult.setPlan_id(String.valueOf(resMap.get("plan_id")));
                    signResult.setRequest_serial(String.valueOf(resMap.get("request_serial")));
                    signResult.setContract_code(String.valueOf(resMap.get("contract_code")));
                    if (null != resMap.get("contract_display_account")) {
                        signResult.setContract_display_account(String.valueOf(resMap.get("contract_display_account")));
                    }
                    if (null != resMap.get("contract_state")) {
                        signResult.setContract_state(String.valueOf(resMap.get("contract_state")));
                    }
                    if (null != resMap.get("contract_signed_time")) {
                        signResult.setContract_signed_time(String.valueOf(resMap.get("contract_signed_time")));
                    }
                    if (null != resMap.get("contract_expired_time")) {
                        signResult.setContract_expired_time(String.valueOf(resMap.get("contract_expired_time")));
                    }
                    if (null != resMap.get("contract_terminated_time")) {
                        signResult.setContract_terminated_time(String.valueOf(resMap.get("contract_terminated_time")));
                    }
                    if (null != resMap.get("contract_termination_mode")) {
                        signResult.setContract_termination_mode(String.valueOf(resMap.get("contract_termination_mode")));
                    }
                    if (null != resMap.get("contract_termination_remark")) {
                        signResult.setContract_termination_remark(String.valueOf(resMap.get("contract_termination_remark")));
                    }
                    if (null != resMap.get("openid")) {
                        signResult.setOpenid(String.valueOf(resMap.get("openid")));
                    }
                    responseVo.setData(signResult);
                    return responseVo;
                } else {
                    logger.error("?????????????????????????????????{}", resMap.get("err_code") + "------------" + resMap.get("err_code_des"));
                    responseVo.setSuccess(false);
                    responseVo.setErrorCode(-1000);
                    responseVo.setMsg(String.valueOf(resMap.get("err_code_des")));
                    return responseVo;
                }
            } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(String.valueOf(resMap.get("return_msg")));
            }
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("???????????????????????????");
        return responseVo;
    }

    /**
     * ??????????????????
     *
     * @param unsignDto ????????????
     * @throws Exception
     */
    @Override
    public ResponseVo<String> wechatUnsign(UnsignDto unsignDto) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //????????????????????????????????????
        WechatFreeData freeData = this.selectByMemberId(unsignDto.getSmemberId(), unsignDto.getSmemberMerchantCode());
        if (null == freeData) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????");
        }
        //????????????????????????
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(unsignDto.getSmerchantCode(), 2);
        if (null == conf) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????");
        }
        if (StringUtil.isBlank(unsignDto.getRemark())) {
            unsignDto.setRemark("????????????????????????????????????");
        }
        UnifiedUnsignReqData unsignReqData = new UnifiedUnsignReqData.UnifiedUnsignReqDataBuilder(conf, freeData.getScontractCode(), freeData.getScontractId())
                .setRemark(unsignDto.getRemark()).build();
        Map<String, Object> resMap = wxPayApi.unifiedUnSign(conf, unsignReqData);
        if (null != resMap) {
            logger.info("?????????????????????????????????{}", JSON.toJSONString(resMap));
            if ("SUCCESS".equals(resMap.get("return_code"))) {
                if ("SUCCESS".equals(resMap.get("result_code"))) {
                    //??????????????????
                    memberInfoService.dealwithWechatUnsign(unsignDto.getSip(), resMap, conf);
                    responseVo.setData("SUCCESS");
                    //????????????????????????????????????
                    MemberInfo memberInfo = new MemberInfo();
                    memberInfo.setId(unsignDto.getSmemberId());
                    memberInfo.setIwechatOpen(0);
                    memberInfoService.updateBySelective(memberInfo);
                    return responseVo;
                } else {
                    logger.error("?????????????????????????????????{}", resMap.get("err_code") + "------------" + resMap.get("err_code_des"));
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(String.valueOf(resMap.get("err_code_des")));
                }
            } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(String.valueOf(resMap.get("return_msg")));
            }
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("????????????????????????");
        return responseVo;
    }

    /**
     * ????????????????????????
     *
     * @param contractCode ????????????????????????
     * @return
     */
    @Override
    public WechatFreeData selectByContractCode(String contractCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contractCode", contractCode);
        return wechatFreeDataDao.selectByContractCode(map);
    }

    /**
     * ????????????????????????
     *
     * @param smemberId ????????????
     * @return
     */
    @Override
    public WechatFreeData selectByMemberId(String smemberId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("smemberId", smemberId);
        return wechatFreeDataDao.selectByMemberId(map);
    }
}
