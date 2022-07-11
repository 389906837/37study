package com.cloud.cang.pay.hy.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.GiveActivityDto;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.hy.FreeOperLog;
import com.cloud.cang.model.hy.WechatFreeData;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sq.PayApply;
import com.cloud.cang.pay.ac.service.ActivityConfService;
import com.cloud.cang.pay.hy.service.FreeDataService;
import com.cloud.cang.pay.hy.service.FreeOperLogService;
import com.cloud.cang.pay.hy.service.WechatFreeDataService;
import com.cloud.cang.pay.om.service.OrderRecordService;
import com.cloud.cang.pay.sq.service.PayApplyService;
import com.cloud.cang.pay.wechat.notify.*;
import com.cloud.cang.pay.wechat.util.httpclient.util.AesUtil;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.pay.hy.dao.MemberInfoDao;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.pay.hy.service.MemberInfoService;

@Service
public class MemberInfoServiceImpl extends GenericServiceImpl<MemberInfo, String> implements
        MemberInfoService {

    @Autowired
    private MemberInfoDao memberInfoDao;
    @Autowired
    private FreeDataService freeDataService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private PayApplyService payApplyService;
    @Autowired
    private FreeOperLogService freeOperLogService;
    @Autowired
    private WechatFreeDataService wechatFreeDataService;
    @Autowired
    private ActivityConfService activityConfService;
    @Autowired
    private ICached iCached;
    private static final Logger logger = LoggerFactory.getLogger(MemberInfoServiceImpl.class);

    @Override
    public GenericDao<MemberInfo, String> getDao() {
        return memberInfoDao;
    }

    /**
     * 处理支付宝签约成功方法
     *
     * @param merchantCode 商户编号
     * @param map          返回参数 详见文档
     * @param conf         商户配置信息
     * @return
     * @throws Exception
     */
    @Override
    public boolean dealwithAlipaySign(String merchantCode, Map<String, String> map, MerchantConf conf) throws Exception {
        if (!conf.getSappId().equals(map.get("app_id"))) {
            logger.error("处理签约数据异常，商户数据异常");
            return false;
        }
        String externalAgreementNo = map.get("external_agreement_no");//系统商户签约唯一标识
        String agreementNo = map.get("agreement_no");//支付宝签约唯一标识
        FreeData freeData = freeDataService.selectByExternalAgreementNo(externalAgreementNo);
        if (freeData == null) {
            logger.error("支付宝签约数据异常");
            return false;
        }
        //更新数据
        FreeData updateData = new FreeData();
        updateData.setId(freeData.getId());
        if (StringUtil.isNotBlank(freeData.getSagreementNo()) && agreementNo.equals(freeData.getSagreementNo())) {
            logger.info("支付宝签约数据重复处理");
            updateData = setFreeDataValue(map, updateData);
            freeDataService.saveOrUpdate(updateData);
            return true;
        }

        String validTime = map.get("valid_time");//生效时间
        String invalidTime = map.get("invalid_time");//失效时间
        String status = map.get("status");//协议状态
        String signScene = map.get("sign_scene");//签约场景
        String alipayLogonId = map.get("alipay_logon_id");//支付宝脱敏账号
        String signTime = map.get("sign_time");//实际签约时间
        String externalLogonId = map.get("external_logon_id");//平台签约账户


        updateData.setSagreementNo(agreementNo);
        updateData.setTvalidTime(DateUtils.parseDateByFormat(validTime, "yyyy-MM-dd HH:mm:ss"));
        updateData.setTinvalidTime(DateUtils.parseDateByFormat(invalidTime, "yyyy-MM-dd HH:mm:ss"));
        updateData.setSsignScene(signScene);
        updateData.setSstatus(status);
        updateData.setSalipayLogonId(alipayLogonId);
        updateData.setTsignTime(DateUtils.parseDateByFormat(signTime, "yyyy-MM-dd HH:mm:ss"));
        updateData.setSexternalLogonId(externalLogonId);
        updateData = setFreeDataValue(map, updateData);
        freeDataService.saveOrUpdate(updateData);

        //新增免密操作记录
        FreeOperLog freeOperLog = new FreeOperLog();

        freeOperLog.setSmemberId(freeData.getSmemberId());
        freeOperLog.setSmemberName(freeData.getSmemberName());
        freeOperLog.setSmemberNo(freeData.getSmemberNo());
        freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
        freeOperLog.setSmerchantId(freeData.getSmerchantId());

        freeOperLog.setIaction(10);
        freeOperLog.setItype(20);
        freeOperLog.setSopenid(alipayLogonId);
        freeOperLog.setToperTime(DateUtils.parseDateByFormat(signTime, "yyyy-MM-dd HH:mm:ss"));
        freeOperLog.setScontractCode(agreementNo);
        freeOperLog.setScontractId(externalAgreementNo);
        freeOperLog.setSoperIp(map.get("sip"));
        freeOperLogService.insert(freeOperLog);

        if (updateData.getSstatus().equals("NORMAL")) {
            //修改用户状态
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIaipayOpen(1);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
        }
        //删除缓存
        iCached.remove("is_remind_open_free_data_" + freeData.getSmemberId());
        //开通免密 调用活动
        openFreeGive(freeData);
        return true;
    }

    @Override
    public boolean dealwithWechatSign(SignNotifyData signNotifyData, MerchantConf conf, String sip) throws Exception {

		/*if (!conf.getSplanId().equals(signNotifyData.getPlan_id())) {
            logger.error("处理签约数据异常，商户数据异常");
			return false;
		}*/

        String contractCode = signNotifyData.getContract_code();//系统商户签约唯一标识
        String contractId = signNotifyData.getContract_id();//微信委托代扣协议id
        WechatFreeData freeData = wechatFreeDataService.selectByContractCode(contractCode);
        if (freeData == null) {
            logger.error("微信签约数据异常");
            return false;
        }
        //更新数据
        WechatFreeData updateData = new WechatFreeData();
        updateData.setId(freeData.getId());
        if (StringUtil.isNotBlank(signNotifyData.getRequest_serial())) {
            updateData.setIrequestSerial(Integer.parseInt(signNotifyData.getRequest_serial()));
        }
        updateData.setScontractId(contractId);
        updateData.setSmchId(signNotifyData.getMch_id());
        updateData.setSopenid(signNotifyData.getOpenid());
        updateData.setSplanId(signNotifyData.getPlan_id());
        updateData.setSstatus(signNotifyData.getChange_type());
        if (StringUtil.isNotBlank(signNotifyData.getOperate_time())) {
            updateData.setTsignTime(DateUtils.parseDateByFormat(signNotifyData.getOperate_time(), "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtil.isNotBlank(signNotifyData.getOperate_time())) {
            updateData.setTcontractExpiredTime(DateUtils.parseDateByFormat(signNotifyData.getContract_expired_time(), "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtil.isNotBlank(freeData.getScontractId()) && contractId.equals(freeData.getScontractId())) {
            logger.info("微信签约数据重复处理");
            wechatFreeDataService.saveOrUpdate(updateData);
            return true;
        }
        wechatFreeDataService.saveOrUpdate(updateData);

        //新增免密操作记录
        FreeOperLog freeOperLog = new FreeOperLog();

        freeOperLog.setSmemberId(freeData.getSmemberId());
        freeOperLog.setSmemberName(freeData.getSmemberName());
        freeOperLog.setSmemberNo(freeData.getSmemberNo());
        freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
        freeOperLog.setSmerchantId(freeData.getSmerchantId());

        freeOperLog.setIaction(10);
        freeOperLog.setItype(10);
        freeOperLog.setSopenid(signNotifyData.getOpenid());
        freeOperLog.setToperTime(updateData.getTsignTime());
        freeOperLog.setScontractCode(contractCode);
        freeOperLog.setScontractId(contractId);
        freeOperLog.setSoperIp(sip);
        freeOperLogService.insert(freeOperLog);

        //删除缓存
        iCached.remove("is_remind_open_free_data_" + freeData.getSmemberId());
        if (updateData.getSstatus().equals("ADD")) {
            //修改用户状态
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIwechatOpen(1);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
            //开通免密 调用活动
            openFreeGive(freeData);
        }
        return true;
    }

    /***
     * 支付宝免密解约处理
     * @param merchantCode 商户编号
     * @param map 返回参数 详见文档
     * @param conf 商户配置信息
     * @return
     * @throws Exception
     */
    @Override
    public boolean dealwithAlipayUnsign(String merchantCode, Map<String, String> map, MerchantConf conf) throws Exception {
        if (!conf.getSappId().equals(map.get("app_id"))) {
            logger.error("处理解约数据异常，商户数据异常");
            return false;
        }
        String externalAgreementNo = map.get("external_agreement_no");//系统商户签约唯一标识
        FreeData freeData = freeDataService.selectByExternalAgreementNo(externalAgreementNo);
        if (freeData == null) {
            logger.error("支付宝解约数据异常");
            return false;
        }
        if ("UNSIGN".equals(freeData.getSstatus())) {
            logger.info("支付宝解约回调重复处理");
            return true;
        }
        //更新数据
        FreeData updateData = new FreeData();
        updateData.setId(freeData.getId());
        updateData.setSstatus(map.get("status"));
        updateData.setTunsignTime(DateUtils.parseDateByFormat(map.get("unsign_time"), "yyyy-MM-dd HH:mm:ss"));
        freeDataService.saveOrUpdate(updateData);


        //新增免密操作记录
        FreeOperLog freeOperLog = new FreeOperLog();

        freeOperLog.setSmemberId(freeData.getSmemberId());
        freeOperLog.setSmemberName(freeData.getSmemberName());
        freeOperLog.setSmemberNo(freeData.getSmemberNo());
        freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
        freeOperLog.setSmerchantId(freeData.getSmerchantId());

        freeOperLog.setIaction(20);
        freeOperLog.setItype(20);
        freeOperLog.setSopenid(freeData.getSalipayLogonId());
        freeOperLog.setToperTime(DateUtils.parseDateByFormat(map.get("unsign_time"), "yyyy-MM-dd HH:mm:ss"));
        freeOperLog.setScontractCode(externalAgreementNo);
        freeOperLog.setScontractId(freeData.getSagreementNo());
        freeOperLog.setSoperIp(map.get("sip"));
        freeOperLogService.insert(freeOperLog);

        //修改用户状态
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setId(freeData.getSmemberId());
        memberInfo.setIaipayOpen(0);
        memberInfo.setUpdateTime(new Date());
        this.updateBySelective(memberInfo);


        return true;
    }

    /**
     * 支付宝交易订单关闭处理
     *
     * @param merchantCode 商户编号
     * @param map          返回参数 详见文档
     * @param conf         商户配置信息
     * @throws Exception
     */
    @Override
    public boolean dealwithAlipayClosePayment(String merchantCode, Map<String, String> map, MerchantConf conf) throws Exception {
        if (!conf.getSappId().equals(map.get("app_id"))) {
            logger.error("处理关闭交易订单数据异常，商户数据异常");
            return false;
        }
        String outTradeNo = map.get("out_trade_no");//系统商户订单编号
        String tradeNo = map.get("trade_no");//系统商户订单编号
        //获取操作订单信息
        OrderRecord orderRecord = orderRecordService.selectByOrderCode(outTradeNo);
        if (null == orderRecord) {
            logger.error("处理关闭交易订单数据异常，订单不存在");
            return false;
        }
        PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord.getSpayApplyId());
        if (null == payApply) {
            logger.error("处理关闭交易订单数据异常，支付申请不存在");
            return false;
        }
        if (null != payApply.getIcloseStatus() && payApply.getIcloseStatus().intValue() == 1) {
            logger.info("关闭交易订单回调重复处理");
            return true;
        }
        //更新数据
        PayApply updateApply = new PayApply();
        updateApply.setId(orderRecord.getSpayApplyId());
        updateApply.setIcloseStatus(1);
        updateApply.setScloseTradeNo(tradeNo);
        updateApply.setTcloseTime(DateUtils.parseDateByFormat(map.get("gmt_close"), "yyyy-MM-dd HH:mm:ss"));
        payApplyService.updateBySelective(updateApply);
        return true;
    }

    /***
     * 微信免密协议解约处理
     * @param sip IP
     * @param resMap 返回参数
     * @param conf 商户配置信息
     * @throws Exception
     */
    @Override
    public boolean dealwithWechatUnsign(String sip, Map<String, Object> resMap, MerchantConf conf) throws Exception {
        if (!conf.getSappId().equals(String.valueOf(resMap.get("appid")))) {
            logger.error("处理微信免密解约数据异常，商户数据异常");
            return false;
        }
        String contractCode = (String) resMap.get("contract_code");//系统商户签约唯一标识
        return wechatUnsign(contractCode, "3", sip, conf);
    }

    /***
     * 微信免密协议解约处理
     * @param sip IP
     * @param unsignNotifyData 返回参数
     * @param conf 商户配置信息
     * @throws Exception
     */
    @Override
    public boolean dealwithWechatUnsign(String sip, UnsignNotifyData unsignNotifyData, MerchantConf conf) throws Exception {
        /*if (!conf.getSpid().equals(String.valueOf(unsignNotifyData.getMch_id()))) {
            logger.error("处理微信免密解约数据异常，商户数据异常");
			return false;
		}*/
        String contractCode = unsignNotifyData.getContract_code();//系统商户签约唯一标识
        return wechatUnsign(contractCode, unsignNotifyData.getContract_termination_mode(), sip, conf);
    }

    /**
     * 微信 解约 处理
     *
     * @param contractCode 系统免密数据编号
     * @param way          解约方式0-未解约
     *                     1-有效期过自动解约
     *                     2-用户主动解约
     *                     3-商户API解约
     *                     4-商户平台解约
     *                     5-注销
     * @param sip          操作IP
     * @param conf         商户配置数据
     */
    private boolean wechatUnsign(String contractCode, String way, String sip, MerchantConf conf) {
        WechatFreeData freeData = wechatFreeDataService.selectByContractCode(contractCode);
        if (freeData == null) {
            logger.error("处理微信免密解约微信免密数据异常");
            return false;
        }
        if ("DELETE".equals(freeData.getSstatus())) {
            logger.info("微信免密协议解约重复处理");
            return true;
        }
        //更新数据
        WechatFreeData updateData = new WechatFreeData();
        updateData.setId(freeData.getId());
        updateData.setSstatus("DELETE");
        updateData.setTunsignTime(DateUtils.getCurrentDateTime());
        updateData.setSunsignWay(way);
        wechatFreeDataService.saveOrUpdate(updateData);


        //新增免密操作记录
        FreeOperLog freeOperLog = new FreeOperLog();

        freeOperLog.setSmemberId(freeData.getSmemberId());
        freeOperLog.setSmemberName(freeData.getSmemberName());
        freeOperLog.setSmemberNo(freeData.getSmemberNo());
        freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
        freeOperLog.setSmerchantId(freeData.getSmerchantId());

        freeOperLog.setIaction(20);
        freeOperLog.setItype(10);
        freeOperLog.setSopenid(freeData.getSopenid());
        freeOperLog.setToperTime(updateData.getTunsignTime());
        freeOperLog.setScontractCode(freeData.getScontractCode());
        freeOperLog.setScontractId(freeData.getScontractId());
        freeOperLog.setSoperIp(sip);
        freeOperLog.setSunsignWay(way);
        freeOperLogService.insert(freeOperLog);

        //修改用户状态
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setId(freeData.getSmemberId());
        memberInfo.setIwechatOpen(0);
        memberInfo.setUpdateTime(new Date());
        this.updateBySelective(memberInfo);

        return true;
    }

    /**
     * 设置支付宝免密其他值
     *
     * @param map        回调参数
     * @param updateData
     * @return
     */
    private FreeData setFreeDataValue(Map<String, String> map, FreeData updateData) {
        String zmOpenId = map.get("zm_open_id");//用户的芝麻信用openId
        String alipayUserId = map.get("alipay_user_id");//用户签约的支付宝账号
        String forexEligible = map.get("forex_eligible");//是否海外购汇身份
        String notifyType = map.get("notify_type");//用户签约成功通知类型
        String zmScore = map.get("zm_score");//用户支付宝芝麻分
        String loginToken = map.get("login_token");//用户登录token
        String deviceId = map.get("device_id");//设备Id

        updateData.setSzmOpenId(zmOpenId);
        updateData.setSzmScore(zmScore);
        updateData.setSalipayUserId(alipayUserId);
        updateData.setSforexEligible(forexEligible);
        updateData.setSnotifyType(notifyType);
        updateData.setSloginToken(loginToken);
        updateData.setSdeviceId(deviceId);
        return updateData;
    }

    /**
     * 调用开通免密活动 微信
     *
     * @param freeData
     */
    public void openFreeGive(WechatFreeData freeData) {
        try {
            logger.debug("会员开通免密调用活动：{}", JSON.toJSONString(freeData));
            /**
             * 查询商户的会员开通免密活动编号
             */
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itype", 10);
            Integer sourceType = BizTypeDefinitionEnum.SourceBizStatus.FIRST_OPEN_WECHAT.getCode();
            map.put("iscenesType", sourceType);
            map.put("istatus", 1);
            map.put("smerchantId", freeData.getSmerchantId());
            ActivityConf activityConf = activityConfService.selectByMap(map);
            if (null != activityConf) {
                GiveActivityDto giveActivityDto = new GiveActivityDto();

                giveActivityDto.setSmerchantId(freeData.getSmerchantId());
                giveActivityDto.setSmerchantCode(freeData.getSmerchantCode());
                giveActivityDto.setMemberId(freeData.getSmemberId());
                giveActivityDto.setMemberCode(freeData.getSmemberNo());
                giveActivityDto.setMemberRealName(freeData.getSmemberName());

                giveActivityDto.setSsourceType(sourceType);
                giveActivityDto.setSsourceId(freeData.getSmemberId());
                giveActivityDto.setSsourceCode(freeData.getSmemberNo());
                giveActivityDto.setActiveConfCode(activityConf.getSconfCode());

                logger.info("会员开通免密调用活动参数：{}", giveActivityDto);
                //创建Rest服务客户端
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
                        .newInvoker(ActivityServicesDefine.ACTIVITY_GIVE_SERVICE);
                invoke.setRequestObj(giveActivityDto);
                //返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() {
                });
                ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
                logger.info("会员开通免密调用活动返回信息：{}", JSON.toJSONString(responseVo));
            }
        } catch (Exception e) {
            logger.error("会员开通免密调用活动异常：{}", e);
        }
    }

    /**
     * 调用开通免密活动 支付宝
     *
     * @param freeData
     */
    public void openFreeGive(FreeData freeData) {
        try {
            logger.debug("会员开通免密调用活动：{}", JSON.toJSONString(freeData));
            /**
             * 查询商户的会员开通免密活动编号
             */
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itype", 10);
            Integer sourceType = BizTypeDefinitionEnum.SourceBizStatus.FIRST_OPEN_ALIPAY.getCode();
            map.put("iscenesType", sourceType);
            map.put("istatus", 1);
            map.put("smerchantId", freeData.getSmerchantId());
            ActivityConf activityConf = activityConfService.selectByMap(map);
            if (null != activityConf) {
                GiveActivityDto giveActivityDto = new GiveActivityDto();

                giveActivityDto.setSmerchantId(freeData.getSmerchantId());
                giveActivityDto.setSmerchantCode(freeData.getSmerchantCode());
                giveActivityDto.setMemberId(freeData.getSmemberId());
                giveActivityDto.setMemberCode(freeData.getSmemberNo());
                giveActivityDto.setMemberRealName(freeData.getSmemberName());

                giveActivityDto.setSsourceType(sourceType);
                giveActivityDto.setSsourceId(freeData.getSmemberId());
                giveActivityDto.setSsourceCode(freeData.getSmemberNo());
                giveActivityDto.setActiveConfCode(activityConf.getSconfCode());

                logger.info("会员开通免密调用活动参数：{}", giveActivityDto);
                //创建Rest服务客户端
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
                        .newInvoker(ActivityServicesDefine.ACTIVITY_GIVE_SERVICE);
                invoke.setRequestObj(giveActivityDto);
                //返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() {
                });
                ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
                logger.info("会员开通免密调用活动返回信息：{}", JSON.toJSONString(responseVo));
            }
        } catch (Exception e) {
            logger.error("会员开通免密调用活动异常：{}", e);
        }
    }

    /***
     * 微信支付分开启服务处理
     * @param sip IP
     * @param openNotifyData 返回参数
     * @param conf 商户配置信息
     * @throws Exception
     */
    @Override
    public boolean dealwithWechatOpenService(String sip, WechatPointsNotifyData openNotifyData, MerchantConf conf) throws Exception {
        //String contractCode = openNotifyData.getContract_code();//系统商户签约唯一标识
        //return wechatUnsign(contractCode,unsignNotifyData.getContract_termination_mode(), sip, conf);
        String sprivateKey = conf.getSwechatApiv3Key();
        if (StringUtils.isBlank(sprivateKey)) {
            throw new ServiceException("商户APIV3私钥为空：{}", conf.getSmerchantCode());
        }
        AesUtil aesUtil = new AesUtil(sprivateKey.getBytes());
        WechatPointsResourceData openNotifyResourceData = JSON.toJavaObject(openNotifyData.getResource(), WechatPointsResourceData.class);
        String decryptData = aesUtil.decryptToString(openNotifyResourceData.getAssociated_data().getBytes(), openNotifyResourceData.getNonce().getBytes(), openNotifyResourceData.getCiphertext());
        if (StringUtil.isBlank(decryptData)) {
            throw new ServiceException("回调解密后数据为空：{}", JSON.toJSONString(openNotifyResourceData));
        }
        OpenNotifyUserData openNotifyUserData = JSON.parseObject(decryptData, OpenNotifyUserData.class);
        //更新数据
        if (openNotifyData.getEvent_type().equals("PAYSCORE.USER_OPEN_SERVICE")) {
            //开启服务
            logger.info("开启微信支付分服务：{}", openNotifyUserData.getOpenid());
            String outReuestNo = openNotifyUserData.getOut_request_no();//商户请求唯一标识
            WechatFreeData freeData = wechatFreeDataService.selectByOutReuestNo(outReuestNo);
            if (freeData == null) {
                logger.error("微信支付分开启/关闭服务数据异常");
                return false;
            }
            WechatFreeData updateData = new WechatFreeData();
            updateData.setId(freeData.getId());
            updateData.setSmchId(openNotifyUserData.getMchid());
            updateData.setSopenid(openNotifyUserData.getOpenid());
            updateData.setSstatus("ADD");
            if (StringUtil.isNotBlank(openNotifyUserData.getOpenorclose_time())) {
                updateData.setTsignTime(DateUtils.parseDateByFormat(openNotifyUserData.getOpenorclose_time(), "yyyyMMddHHmmss"));
            }
      /*  if (StringUtil.isNotBlank(freeData.getScontractId()) && contractId.equals(freeData.getScontractId())) {
            logger.info("微信签约数据重复处理");
            wechatFreeDataService.saveOrUpdate(updateData);
            return true;
        }*/
            wechatFreeDataService.saveOrUpdate(updateData);
            //新增免密操作记录
            FreeOperLog freeOperLog = new FreeOperLog();
            freeOperLog.setSmemberId(freeData.getSmemberId());
            freeOperLog.setSmemberName(freeData.getSmemberName());
            freeOperLog.setSmemberNo(freeData.getSmemberNo());
            freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
            freeOperLog.setSmerchantId(freeData.getSmerchantId());
            freeOperLog.setIaction(10);
            freeOperLog.setItype(10);
            freeOperLog.setSopenid(openNotifyUserData.getOpenid());
            freeOperLog.setToperTime(updateData.getTsignTime());
            freeOperLog.setSoperIp(sip);
            freeOperLogService.insert(freeOperLog);
            //删除缓存
            iCached.remove("is_remind_open_free_data_" + freeData.getSmemberId());
            //修改用户状态
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIwechatPointOpen(1);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
            //开通免密 调用活动
            openFreeGive(freeData);
        } else if (openNotifyData.getEvent_type().equals("PAYSCORE.USER_CLOSE_SERVICE")) {
            logger.info("关闭微信支付分服务：{}", openNotifyUserData.getOpenid());
            String openId = openNotifyUserData.getOpenid();//微信用户在商户对应appid下的唯一标识
            String mchId = openNotifyUserData.getMchid();//调用开启服务接口提交的商户号
            WechatFreeData freeData = wechatFreeDataService.selectByOpenIdAndMchId(openId, mchId);
            if (freeData == null) {
                logger.error("微信签约数据异常");
                return false;
            }
            //关闭服务
            WechatFreeData updateData = new WechatFreeData();
            updateData.setId(freeData.getId());
            updateData.setSstatus("DELETE");
            if (StringUtil.isNotBlank(openNotifyUserData.getOpenorclose_time())) {
                updateData.setTunsignTime(DateUtils.parseDateByFormat(openNotifyUserData.getOpenorclose_time(), "yyyyMMddHHmmss"));
            }
      /*  if (StringUtil.isNotBlank(freeData.getScontractId()) && contractId.equals(freeData.getScontractId())) {
            logger.info("微信签约数据重复处理");
            wechatFreeDataService.saveOrUpdate(updateData);
            return true;
        }*/
            wechatFreeDataService.saveOrUpdate(updateData);
            //新增免密操作记录
            FreeOperLog freeOperLog = new FreeOperLog();
            freeOperLog.setSmemberId(freeData.getSmemberId());
            freeOperLog.setSmemberName(freeData.getSmemberName());
            freeOperLog.setSmemberNo(freeData.getSmemberNo());
            freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
            freeOperLog.setSmerchantId(freeData.getSmerchantId());
            freeOperLog.setIaction(20);
            freeOperLog.setItype(10);
            freeOperLog.setSopenid(openNotifyUserData.getOpenid());
            freeOperLog.setToperTime(updateData.getTsignTime());
            freeOperLog.setSoperIp(sip);
            freeOperLogService.insert(freeOperLog);
            //修改用户状态
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIwechatPointOpen(0);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
        }
        return true;
    }

}