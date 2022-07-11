package com.cloud.cang.pay.hy.service.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import com.cloud.cang.pay.wechat.protocol.QueryUserAvaData;
import com.cloud.cang.pay.wechat.protocol.UnifiedOrderReqData;
import com.cloud.cang.pay.wechat.protocol.UnifiedQuerySignReqData;
import com.cloud.cang.pay.wechat.protocol.UnifiedUnsignReqData;
import com.cloud.cang.pay.wechat.service.WxPayApi;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.MD5;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
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
     * 支付宝免密签约
     *
     * @param signDto 签约参数
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<String> wechatSign(SignDto signDto) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //获取用户的支付宝免密数据
        WechatFreeData freeData = this.selectByMemberIdAndWithholdType(signDto.getSmemberId(), signDto.getSmemberMerchantCode(), 10);
        boolean flag = false;
        if (freeData == null) {
            freeData = new WechatFreeData();
            freeData.setScontractCode(IdGenerator.randomUUID(32));
            freeData.setIwechatWithholdType(10);
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
        //获取商户配置信息
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(signDto.getSmerchantCode(), 2);
        if (conf == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
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

        if (flag) {//新增免密数据
            this.saveOrUpdate(freeData);
        }
        String url = sb.toString();
        if (StringUtil.isNotBlank(url)) {
            responseVo.setData(url);
            logger.info("微信免密签约请求参数：{}", responseVo.getData());
            return responseVo;
        }

        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("创建微信免密签约参数异常");
        return responseVo;
    }

    /**
     * 微信支付分开通服务
     *
     * @param signDto 签约参数
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<String> wechatOpenService(SignDto signDto) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //获取用户的支付宝免密数据
        WechatFreeData freeData = this.selectByMemberIdAndWithholdType(signDto.getSmemberId(), signDto.getSmemberMerchantCode(), 20);
        boolean flag = false;
       /* if (freeData == null) {
            freeData = new WechatFreeData();
            // freeData.setScontractCode(IdGenerator.randomUUID(32));
            freeData.setIwechatWithholdType(20);
            freeData.setSmemberId(signDto.getSmemberId());
            freeData.setSmemberName(signDto.getSmemberName());
            freeData.setSmemberNo(signDto.getSmemberCode());
            freeData.setSmerchantCode(signDto.getSmemberMerchantCode());
            freeData.setIrequestSerial(Integer.parseInt(CoreUtils.newCode("hy_wechat_free_data")));
            flag = true;
        } else {
            //freeData.setScontractCode(IdGenerator.randomUUID(32));
            //flag = true;
        }*/
        //获取商户配置信息
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(signDto.getSmerchantCode(), 2);
        if (conf == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sb = new StringBuffer();
        sb.append("mch_id=" + URLEncoder.encode(conf.getSpid(), "UTF-8"));
        map.put("mch_id", conf.getSpid());
        sb.append("&service_id=" + URLEncoder.encode(conf.getSserviceId(), "UTF-8"));
        map.put("service_id", conf.getSserviceId());
        String out_request_no_temp = MD5.encode(conf.getSmerchantCode() + signDto.getSmemberCode() + DateUtils.getCurrentSTimeNumber());
        sb.append("&out_request_no=" + URLEncoder.encode(out_request_no_temp, "UTF-8"));
        map.put("out_request_no", out_request_no_temp);
        long timestamp = System.currentTimeMillis() / 1000;
        sb.append("&timestamp=" + URLEncoder.encode(String.valueOf(timestamp), "UTF-8"));
        map.put("timestamp", timestamp);
        String uuid = IdGenerator.randomUUID(32);
        sb.append("&nonce_str=" + URLEncoder.encode(uuid, "UTF-8"));
        map.put("nonce_str", uuid);
        sb.append("&sign_type=" + URLEncoder.encode("HMAC-SHA256", "UTF-8"));
        map.put("sign_type", "HMAC-SHA256");
        sb.append("&sign=" + URLEncoder.encode(Signature.getOpen(map, conf), "UTF-8"));

        if (freeData == null) {
            freeData = new WechatFreeData();
            // freeData.setScontractCode(IdGenerator.randomUUID(32));
            freeData.setIwechatWithholdType(20);
            freeData.setSmemberId(signDto.getSmemberId());
            freeData.setSmemberName(signDto.getSmemberName());
            freeData.setSmemberNo(signDto.getSmemberCode());
            freeData.setSmerchantCode(signDto.getSmemberMerchantCode());
            freeData.setIrequestSerial(Integer.parseInt(CoreUtils.newCode("hy_wechat_free_data")));
            flag = true;
        } else {
            flag = true;
        }
        if (flag) {//新增免密数据
            freeData.setSoutRequestNo(out_request_no_temp);
            this.saveOrUpdate(freeData);
        }
        String url = sb.toString();
        if (StringUtil.isNotBlank(url)) {
            responseVo.setData(url);
            logger.info("微信支付分开通服务请求参数：{}", responseVo.getData());
            return responseVo;
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("创建微信支付分开通服务参数异常");
        return responseVo;
    }

    /**
     * 获取微信免密数据
     *
     * @param smemberId     会员信息
     * @param smerchantCode 商户编号
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
     * 保存免密数据
     *
     * @param freeData 免密参数
     */
    @Override
    public WechatFreeData saveOrUpdate(WechatFreeData freeData) {
        if (StringUtil.isBlank(freeData.getId())) {//保存
            MerchantInfo merchantInfo = merchantInfoService.selectByCode(freeData.getSmerchantCode());
            freeData.setSmerchantId(merchantInfo.getId());
            freeData.setTaddTime(new Date());
            freeData.setTupdateTime(new Date());
            wechatFreeDataDao.insert(freeData);
        } else {//修改
            freeData.setTupdateTime(new Date());
            wechatFreeDataDao.updateByIdSelective(freeData);
        }
        return freeData;
    }

    /**
     * 查询免密签约数据
     *
     * @param contractCode  系统签约唯一标识
     * @param smerchantCode 商户编号
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
     * 查询微信免密支付
     *
     * @param querySignDto 查询参数
     * @return
     */
    @Override
    public ResponseVo<WechatSignResult> wechatQuerySign(QuerySignDto querySignDto) throws Exception {
        ResponseVo<WechatSignResult> responseVo = ResponseVo.getSuccessResponse();
        //获取用户的支付宝免密数据
      //  WechatFreeData freeData = this.selectByMemberId(querySignDto.getSmemberId());
        WechatFreeData freeData = this.selectByMemberIdAndWithholdType(querySignDto.getSmemberId(), querySignDto.getSmerchantCode(), 10);

        if (null == freeData) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户签约异常");
        }
        //获取商户配置信息
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(querySignDto.getSmerchantCode(), 2);
        if (null == conf) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
        }

        UnifiedQuerySignReqData querySignReqData = new UnifiedQuerySignReqData.UnifiedQuerySignReqDataBuilder(conf, freeData.getScontractCode(), "").build();
        Map<String, Object> resMap = wxPayApi.unifiedQuerySign(conf, querySignReqData);
        if (null != resMap) {
            logger.info("微信免密协议查询返回参数：{}", JSON.toJSONString(resMap));
            if ("SUCCESS".equals(resMap.get("return_code"))) {
                if ("SUCCESS".equals(resMap.get("result_code"))) {
                    //查询成功
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
                    logger.error("微信免密协议查询异常：{}", resMap.get("err_code") + "------------" + resMap.get("err_code_des"));
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
        responseVo.setMsg("未查到微信免密数据");
        return responseVo;
    }

    /**
     * 微信免密解决
     *
     * @param unsignDto 解约参数
     * @throws Exception
     */
    @Override
    public ResponseVo<String> wechatUnsign(UnsignDto unsignDto) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //获取用户的支付宝免密数据
        WechatFreeData freeData = this.selectByMemberIdAndWithholdType(unsignDto.getSmemberId(), unsignDto.getSmemberMerchantCode(), 10);
        if (null == freeData) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户解约异常");
        }
        //获取商户配置信息
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(unsignDto.getSmerchantCode(), 2);
        if (null == conf) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
        }
        if (StringUtil.isBlank(unsignDto.getRemark())) {
            unsignDto.setRemark("用户主动在商户下操作解约");
        }
        UnifiedUnsignReqData unsignReqData = new UnifiedUnsignReqData.UnifiedUnsignReqDataBuilder(conf, freeData.getScontractCode(), freeData.getScontractId())
                .setRemark(unsignDto.getRemark()).build();
        Map<String, Object> resMap = wxPayApi.unifiedUnSign(conf, unsignReqData);
        if (null != resMap) {
            logger.info("微信免密解约返回参数：{}", JSON.toJSONString(resMap));
            if ("SUCCESS".equals(resMap.get("return_code"))) {
                if ("SUCCESS".equals(resMap.get("result_code"))) {
                    //处理解约业务
                    memberInfoService.dealwithWechatUnsign(unsignDto.getSip(), resMap, conf);
                    responseVo.setData("SUCCESS");
                    //修改用户是否签约免密状态
                    MemberInfo memberInfo = new MemberInfo();
                    memberInfo.setId(unsignDto.getSmemberId());
                    memberInfo.setIwechatOpen(0);
                    memberInfoService.updateBySelective(memberInfo);
                    return responseVo;
                } else {
                    logger.error("微信免密协议解约异常：{}", resMap.get("err_code") + "------------" + resMap.get("err_code_des"));
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(String.valueOf(resMap.get("err_code_des")));
                }
            } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(String.valueOf(resMap.get("return_msg")));
            }
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信免密解约异常");
        return responseVo;
    }

    /**
     * 查询免密签约数据
     *
     * @param contractCode 系统签约唯一标识
     * @return
     */
    @Override
    public WechatFreeData selectByContractCode(String contractCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contractCode", contractCode);
        return wechatFreeDataDao.selectByContractCode(map);
    }

    /**
     * 查询免密签约数据
     *
     * @param outReuestNo 调用开启服务接口提交的商户请求唯一标识
     * @return
     */
    @Override
    public WechatFreeData selectByOutReuestNo(String outReuestNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("outReuestNo", outReuestNo);
        return wechatFreeDataDao.selectByOutReuestNo(map);
    }

    /**
     * 获取微信免密数据
     *
     * @param smemberId 会员信息
     * @return
     */
    @Override
    public WechatFreeData selectByMemberId(String smemberId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("smemberId", smemberId);
        return wechatFreeDataDao.selectByMemberId(map);
    }

    /**
     * 获取微信免密数据
     *
     * @param openId
     * @param mchId
     * @return
     */
    @Override
    public WechatFreeData selectByOpenIdAndMchId(String openId, String mchId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openId", openId);
        map.put("mchId", mchId);
        return wechatFreeDataDao.selectByOpenIdAndMchId(map);
    }

    /**
     * 获取微信免密数据
     *
     * @param smemberId 会员信息
     * @return
     */
    @Override
    public WechatFreeData selectByMemberIdAndWithholdType(String smemberId, String smerchantCode, Integer withholdType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("smemberId", smemberId);
        map.put("smerchantCode", smerchantCode);
        map.put("withholdType", withholdType);
        return wechatFreeDataDao.selectByMemberIdAndWithholdType(map);
    }


    /**
     * 微信支付分查询用户是否可使用
     *
     * @param queryUserAvaiDto 签约参数
     * @return
     */
    @Override
    public ResponseVo<QueryUserAvaiResult> queryUserAvailability(QueryUserAvaiDto queryUserAvaiDto) throws Exception {
        ResponseVo<QueryUserAvaiResult> responseVo = ResponseVo.getSuccessResponse();
        //获取用户的微信支付分免密数据
        WechatFreeData freeData = this.selectByMemberIdAndWithholdType(queryUserAvaiDto.getSmemberId(),queryUserAvaiDto.getSmerchantCode(),20);
        if (null == freeData) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户签约异常");
        }
        //获取商户配置信息
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(queryUserAvaiDto.getSmerchantCode(), 2);
        if (null == conf) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
        }
        QueryUserAvaData queryUserAvaData = new QueryUserAvaData.QueryUserAvaDataBuilder(conf, freeData.getSopenid()).build();
        CloseableHttpResponse response = wxPayApi.queryUserAvailability(conf, queryUserAvaData);
        Integer statusCode = response.getStatusLine().getStatusCode();
        String err_code_des = "微信支付分查询用户是否可使用异常";
        if (null != response) {
            HttpEntity entity2 = response.getEntity();
            String str = EntityUtils.toString(entity2, "utf-8");
            if (null != statusCode && statusCode == 200) {
                //更新订单信息
                EntityUtils.consume(entity2);
                QueryUserAvaiResult queryUserAvaiResult = JSON.parseObject(str, QueryUserAvaiResult.class);
                responseVo.setData(queryUserAvaiResult);
                return responseVo;
            } else {
                Map<String, String> map = JSON.parseObject(str, Map.class);
                err_code_des = String.valueOf(map.get("message"));
            }
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg(err_code_des);
        return responseVo;
    }
}
