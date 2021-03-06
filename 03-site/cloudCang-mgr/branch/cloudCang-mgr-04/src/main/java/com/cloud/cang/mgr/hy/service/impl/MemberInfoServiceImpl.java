package com.cloud.cang.mgr.hy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.GiveActivityDto;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.common.utils.IdFactory;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.RechargeAgain.service.RechargeAgainService;
import com.cloud.cang.mgr.ac.service.ActivityConfService;
import com.cloud.cang.mgr.hy.dao.MemberInfoDao;
import com.cloud.cang.mgr.hy.domain.MemberInfoDomain;
import com.cloud.cang.mgr.hy.service.FreeDataService;
import com.cloud.cang.mgr.hy.service.FreeOperLogService;
import com.cloud.cang.mgr.hy.service.MemberInfoService;
import com.cloud.cang.mgr.hy.service.WechatFreeDataService;
import com.cloud.cang.mgr.hy.vo.MemberInfoVo;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.hy.FreeOperLog;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.hy.WechatFreeData;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.*;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberInfoServiceImpl extends GenericServiceImpl<MemberInfo, String> implements
        MemberInfoService {
    private static final Logger logger = LoggerFactory.getLogger(MemberInfoServiceImpl.class);
    @Autowired
    MemberInfoDao memberInfoDao;
    @Autowired
    FreeDataService freeDataService;
    @Autowired
    RechargeAgainService rechargeAgainService;
    @Autowired
    private ICached iCached;
    @Autowired
    private ActivityConfService activityConfService;
    @Autowired
    private WechatFreeDataService wechatFreeDataService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private FreeOperLogService freeOperLogService;

    /**
     * ???????????????????????????
     *
     * @param mobile
     * @return
     */
    public MemberInfo selectByMobile(String mobile, String merchantCode) {
        return memberInfoDao.selectByMobile(mobile, merchantCode);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param memberInfoVo
     * @return
     */
    @Override
    public List<Map<String, Object>> selectMemberInfoByExport(MemberInfoVo memberInfoVo) {
        return memberInfoDao.selectMemberInfoByExport(memberInfoVo);
    }

    /**
     * ????????????????????????
     */
    public final static String SEND_MSG_CODE_TMPE = "TPE0058";


    @Override
    public GenericDao<MemberInfo, String> getDao() {
        return memberInfoDao;
    }


    /**
     * ???????????????????????????
     */
    @Override
    public Page<MemberInfoDomain> selectAllMemberInfo(Page<MemberInfoDomain> page, MemberInfoVo memberInfoVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<MemberInfoDomain>) memberInfoDao.selectAllMemberInfo(memberInfoVo);
    }

    /**
     * ????????????????????????
     */
    @Override
    public List<MemberInfo> selectByWhere(MemberInfoVo memberInfoVo) {
        return memberInfoDao.selectByWhere(memberInfoVo);
    }


    /**
     * ??????ID???????????????????????????
     */
    @Override
    public void delete(String[] checkboxId) {
        for (String id : checkboxId) {
            MemberInfo memberInfo = memberInfoDao.selectByPrimaryKey(id);
            if (memberInfo != null) {
                // ??????
                memberInfoDao.deleteByPrimaryKey(memberInfo.getId());
            }
        }
    }

    /**
     * ????????????
     *
     * @param sid
     */
    @Override
    public void resetPassword(String sid) {
        MemberInfo memeberInfo = memberInfoDao.selectByPrimaryKey(sid);
        if (memeberInfo == null) {
            throw new ServiceException("??????????????????????????????????????????");
        }
        String password = "a" + IdFactory.randomNum(6);

        MemberInfo tempInfo = new MemberInfo();
        tempInfo.setId(sid);
        tempInfo.setSloginPwd(DigestUtils.sha1Hex(password));
        memberInfoDao.updateByIdSelective(tempInfo);

//        // ????????????
//        String logText = "??????????????????????????????????????????" + memeberInfo.getScode() + " ???????????????" + memeberInfo.getSmemberName();
//
//        MessageDto messageDto = new MessageDto();
//        messageDto.setTemplateCode(SEND_MSG_CODE_TMPE);
//        messageDto.setMobile(memeberInfo.getSmobile());
//        if (StringUtils.isNoneBlank(memeberInfo.getSemail())) {
//            messageDto.setEmail(memeberInfo.getSemail());
//        }
//
//        // ??????
//        String name = "??????";
//        // if(memeberInfo.getIsCertification()==1){
//        name = memeberInfo.getSmemberName().substring(0, 1);
//        // }
//        // ??????
//        Map<String, Object> contentParam = new HashMap<String, Object>();
//        //contentParam.put("callName", name);
//        contentParam.put("password", password);
//        messageDto.setContentParam(contentParam);
//        try {
//            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);// ????????????
//            invoke.setRequestObj(messageDto); // post ??????
//            // ??????????????????????????????????????????????????????????????????????????????
//            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
//            });
//            ResponseVo<String> responseVo1 = (ResponseVo<String>) invoke.invoke();
//            if (!responseVo1.isSuccess()) {
//                throw new ServiceException("????????????????????????????????????" + responseVo1.getMsg());
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            throw new ServiceException(e.getMessage());
//        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param
     * @return ResponseVo
     */
    @Override
    public ResponseVo rechargeAgainUpData(SignResult signResult, HttpServletRequest request, String merchantCode) throws Exception {
        logger.debug("??????????????????????????????,???????????????=" + signResult);
        //????????????????????????
       /* MerchantConf conf = rechargeAgainService.getDefaultAlipayMerchantConf();*/
        if (this.dealwithAlipaySign(/*conf,*/ signResult, request, merchantCode)) {
            return ResponseVo.getSuccessResponse();
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("?????????????????????????????????????????????");

    }

    /**
     * ?????????????????????????????????
     *
     * @param
     * @return ResponseVo
     */
    @Override
    public ResponseVo WechantRechargeAgainUpData(WechatSignResult wechatSignResult, HttpServletRequest request, String merchantCode) throws Exception {
        logger.info("???????????????????????????,?????????{}", wechatSignResult);
       /* MerchantConf conf = rechargeAgainService.getDefaultWechatMerchantConf();*/
        //????????????????????????????????????
        if (this.wechantDealwithAlipaySign(wechatSignResult, request, merchantCode)) {
            return ResponseVo.getSuccessResponse();
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("?????????????????????????????????????????????");
    }

    /**
     * ?????????????????????????????????
     *
     * @param
     * @return ResponseVo
     */
    @Override
    public ResponseVo wechatPointRechargeAgainUpData(QueryUserAvaiResult queryUserAvaiResult, HttpServletRequest request, String merchantCode) throws Exception {
        logger.info("????????????????????????,?????????{}", JSON.toJSONString(queryUserAvaiResult));
       /* MerchantConf conf = rechargeAgainService.getDefaultWechatMerchantConf();*/
        if (this.wechantPointDealwithAlipaySign(queryUserAvaiResult, request, merchantCode)) {
            return ResponseVo.getSuccessResponse();
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????????????????????????????");
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param
     * @return boolean
     */
    private Boolean wechantDealwithAlipaySign(WechatSignResult wechatSignResult, HttpServletRequest request, String merchantCode) throws Exception {
     /*   if (!conf.getSplanId().equals(wechatSignResult.getPlan_id())) {
            logger.error("?????????????????????????????????????????????");
            return false;
        }*/

        String contractCode = wechatSignResult.getContract_code();//??????????????????????????????
        String contractId = wechatSignResult.getContract_id();//????????????????????????id

        WechatFreeData freeData = wechatFreeDataService.selectByContractCode(contractCode, merchantCode);
        if (freeData == null) {
            logger.error("????????????????????????");
            return false;
        }
        if (wechatSignResult.getContract_state().equals("0")) {//??????
            //????????????
            WechatFreeData updateData = new WechatFreeData();
            updateData.setId(freeData.getId());
            if (StringUtil.isNotBlank(wechatSignResult.getRequest_serial())) {
                updateData.setIrequestSerial(Integer.parseInt(wechatSignResult.getRequest_serial()));
            }
            updateData.setScontractId(contractId);
            updateData.setSmchId(wechatSignResult.getMch_id());
            updateData.setSopenid(wechatSignResult.getOpenid());
            updateData.setSplanId(wechatSignResult.getPlan_id());
            updateData.setSstatus("ADD");

            if (StringUtil.isNotBlank(wechatSignResult.getContract_signed_time())) {
                updateData.setTsignTime(DateUtils.parseDateByFormat(wechatSignResult.getContract_signed_time(), "yyyy-MM-dd HH:mm:ss"));
            }
            if (StringUtil.isNotBlank(wechatSignResult.getContract_expired_time())) {
                updateData.setTcontractExpiredTime(DateUtils.parseDateByFormat(wechatSignResult.getContract_expired_time(), "yyyy-MM-dd HH:mm:ss"));
            }
            wechatFreeDataService.saveOrUpdate(updateData);

            //????????????????????????
            FreeOperLog freeOperLog = new FreeOperLog();

            freeOperLog.setSmemberId(freeData.getSmemberId());
            freeOperLog.setSmemberName(freeData.getSmemberName());
            freeOperLog.setSmemberNo(freeData.getSmemberNo());
            freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
            freeOperLog.setSmerchantId(freeData.getSmerchantId());

            freeOperLog.setIaction(10);
            freeOperLog.setItype(10);
            freeOperLog.setSopenid(wechatSignResult.getOpenid());
            freeOperLog.setToperTime(updateData.getTsignTime());
            freeOperLog.setScontractCode(contractCode);
            freeOperLog.setScontractId(contractId);
            freeOperLog.setSoperIp(SessionUserUtils.getIpAddr(request));
            freeOperLogService.insert(freeOperLog);
            //????????????
            iCached.remove("is_remind_open_free_data_" + freeData.getSmemberId());
            if (updateData.getSstatus().equals("ADD")) {
                //??????????????????
                MemberInfo memberInfo = new MemberInfo();
                memberInfo.setId(freeData.getSmemberId());
                memberInfo.setIwechatOpen(1);
                memberInfo.setUpdateTime(new Date());
                this.updateBySelective(memberInfo);
                //???????????? ????????????
                openFreeGive(freeData);
            }
        } else if (wechatSignResult.getContract_state().equals("1")) {//??????

            if ("DELETE".equals(freeData.getSstatus())) {
                logger.info("????????????????????????????????????");
                return true;
            }
            //????????????
            WechatFreeData updateData = new WechatFreeData();
            updateData.setId(freeData.getId());
            updateData.setSstatus("DELETE");
            updateData.setTunsignTime(DateUtils.parseDateByFormat(wechatSignResult.getContract_terminated_time(), "yyyy-MM-dd HH:mm:ss"));
            updateData.setSunsignWay(wechatSignResult.getContract_termination_mode());
            updateData.setSunsignRemark(wechatSignResult.getContract_termination_remark());
            wechatFreeDataService.saveOrUpdate(updateData);


            //????????????????????????
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
            freeOperLog.setSoperIp(SessionUserUtils.getIpAddr(request));
            freeOperLog.setSunsignWay(wechatSignResult.getContract_termination_mode());
            freeOperLog.setSunsignRemark(wechatSignResult.getContract_termination_remark());
            freeOperLogService.insert(freeOperLog);

            //??????????????????
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIwechatOpen(0);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
        }
        return true;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param
     * @return boolean
     */
    private Boolean wechantPointDealwithAlipaySign(QueryUserAvaiResult queryUserAvaiResult, HttpServletRequest request, String merchantCode) throws Exception {
        String openid = queryUserAvaiResult.getOpenid();//??????????????????????????????
        WechatFreeData freeData = wechatFreeDataService.selectByOpenId(openid, merchantCode);
        if (freeData == null) {
            logger.error("????????????????????????");
            return false;
        }
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_ALIPAY_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
      /*  ?????????????????????????????????
        UNAVAILABLE - ????????????????????????
        AVAILABLE - ?????????????????????*/
        if (queryUserAvaiResult.getUse_service_state().equals("AVAILABLE")) {//??????
            //????????????
            WechatFreeData updateData = new WechatFreeData();
            updateData.setId(freeData.getId());

            updateData.setSmchId(conf.getSpid());
            updateData.setSopenid(queryUserAvaiResult.getOpenid());
            updateData.setSstatus("ADD");
            updateData.setTsignTime(DateUtils.getCurrentDateTime());
            wechatFreeDataService.saveOrUpdate(updateData);
            //????????????????????????
            FreeOperLog freeOperLog = new FreeOperLog();
            freeOperLog.setSmemberId(freeData.getSmemberId());
            freeOperLog.setSmemberName(freeData.getSmemberName());
            freeOperLog.setSmemberNo(freeData.getSmemberNo());
            freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
            freeOperLog.setSmerchantId(freeData.getSmerchantId());
            freeOperLog.setIaction(10);
            freeOperLog.setItype(10);
            freeOperLog.setSopenid(queryUserAvaiResult.getOpenid());
            freeOperLog.setToperTime(updateData.getTsignTime());
            freeOperLog.setSoperIp(SessionUserUtils.getIpAddr(request));
            freeOperLogService.insert(freeOperLog);
            //????????????
            iCached.remove("is_remind_open_free_data_" + freeData.getSmemberId());
            if (updateData.getSstatus().equals("ADD")) {
                //??????????????????
                MemberInfo memberInfo = new MemberInfo();
                memberInfo.setId(freeData.getSmemberId());
                memberInfo.setIwechatPointOpen(1);
                memberInfo.setUpdateTime(new Date());
                this.updateBySelective(memberInfo);
                //???????????? ????????????
                openFreeGive(freeData);
            }
        } else if (queryUserAvaiResult.getUse_service_state().equals("UNAVAILABLE")) {//??????
            if ("DELETE".equals(freeData.getSstatus())) {
                logger.info("????????????????????????????????????");
                return true;
            }
            //????????????
            WechatFreeData updateData = new WechatFreeData();
            updateData.setId(freeData.getId());
            updateData.setSstatus("DELETE");
            updateData.setTunsignTime(DateUtils.getCurrentDateTime());
            //updateData.setSunsignWay(wechatSignResult.getContract_termination_mode());
           // updateData.setSunsignRemark(wechatSignResult.getContract_termination_remark());
            wechatFreeDataService.saveOrUpdate(updateData);
            //????????????????????????
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
            freeOperLog.setSoperIp(SessionUserUtils.getIpAddr(request));
            //freeOperLog.setSunsignWay(wechatSignResult.getContract_termination_mode());
            //freeOperLog.setSunsignRemark(wechatSignResult.getContract_termination_remark());
            freeOperLogService.insert(freeOperLog);
            //??????????????????
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIwechatPointOpen(0);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
        }
        return true;
    }

    /**
     * ???????????????????????? ??????
     *
     * @param freeData
     */
    public void openFreeGive(WechatFreeData freeData) {
        try {
            logger.debug("?????????????????????????????????{}", JSON.toJSONString(freeData));
            /**
             * ?????????????????????????????????????????????
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

                logger.info("???????????????????????????????????????{}", giveActivityDto);
                //??????Rest???????????????
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
                        .newInvoker(ActivityServicesDefine.ACTIVITY_GIVE_SERVICE);
                invoke.setRequestObj(giveActivityDto);
                //??????????????????????????????????????????????????????????????????????????????
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() {
                });
                ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
                logger.info("?????????????????????????????????????????????{}", JSON.toJSONString(responseVo));
            }
        } catch (Exception e) {
            logger.error("???????????????????????????????????????{}", e);
        }
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param
     * @return boolean
     */
    private Boolean dealwithAlipaySign(/*MerchantConf conf,*/ SignResult signResult, HttpServletRequest request, String merchantCode) throws Exception {
        String externalAgreementNo = signResult.getExternalAgreementNo();//??????????????????????????????
        String agreementNo = signResult.getAgreementNo();//???????????????????????????
        FreeData freeData = freeDataService.selectByExternalAgreementNo(externalAgreementNo, merchantCode);
        if (freeData == null) {
            logger.error("???????????????????????????");
            return false;
        }
        if ("NORMAL".equals(signResult.getStatus())) {
            //????????????
            FreeData updateData = new FreeData();
            updateData.setId(freeData.getId());

            String validTime = signResult.getValidTime();//????????????
            String invalidTime = signResult.getInvalidTime();//????????????
            String status = signResult.getStatus();//????????????
            String signScene = signResult.getSignScene();//????????????
            String alipayLogonId = signResult.getAlipayLogonId();//?????????????????????
            String signTime = signResult.getSignTime();//??????????????????
            String externalLogonId = signResult.getExternalLogonId();//??????????????????


            updateData.setSagreementNo(agreementNo);
            updateData.setTvalidTime(DateUtils.parseDateByFormat(validTime, "yyyy-MM-dd HH:mm:ss"));
            updateData.setTinvalidTime(DateUtils.parseDateByFormat(invalidTime, "yyyy-MM-dd HH:mm:ss"));
            updateData.setSsignScene(signScene);
            updateData.setSstatus(status);
            updateData.setSalipayLogonId(alipayLogonId);
            updateData.setTsignTime(DateUtils.parseDateByFormat(signTime, "yyyy-MM-dd HH:mm:ss"));
            updateData.setSexternalLogonId(externalLogonId);

            freeDataService.saveOrUpdate(updateData);
            //????????????????????????
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
            freeOperLog.setSoperIp(SessionUserUtils.getIpAddr(request));
            freeOperLogService.insert(freeOperLog);

            //??????????????????
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIaipayOpen(1);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
            //????????????
            iCached.remove("is_remind_open_free_data_" + freeData.getSmemberId());
            //???????????? ????????????
            openFreeGive(freeData);
        } else if ("TEMP".equals(signResult.getStatus()) || "STOP".equals(signResult.getStatus())) {

            if ("UNSIGN".equals(freeData.getSstatus())) {
                logger.info("???????????????????????????????????????");
                return true;
            }
            //????????????
            FreeData updateData = new FreeData();
            updateData.setId(freeData.getId());
            updateData.setSstatus("UNSIGN");
            updateData.setTunsignTime(DateUtils.parseDateByFormat(signResult.getInvalidTime(), "yyyy-MM-dd HH:mm:ss"));
            updateData.setTupdateTime(DateUtils.getCurrentDateTime());
            freeDataService.saveOrUpdate(updateData);


            //????????????????????????
            FreeOperLog freeOperLog = new FreeOperLog();

            freeOperLog.setSmemberId(freeData.getSmemberId());
            freeOperLog.setSmemberName(freeData.getSmemberName());
            freeOperLog.setSmemberNo(freeData.getSmemberNo());
            freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
            freeOperLog.setSmerchantId(freeData.getSmerchantId());

            freeOperLog.setIaction(20);
            freeOperLog.setItype(10);
            freeOperLog.setToperTime(updateData.getTunsignTime());
            freeOperLog.setScontractCode(freeData.getSexternalAgreementNo());
            freeOperLog.setSoperIp(SessionUserUtils.getIpAddr(request));
            freeOperLogService.insert(freeOperLog);

            //??????????????????
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIaipayOpen(0);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
        }
        return true;
    }

    /**
     * ???????????????????????? ?????????
     *
     * @param freeData
     */
    public void openFreeGive(FreeData freeData) {
        try {
            logger.debug("?????????????????????????????????{}", JSON.toJSONString(freeData));
            /**
             * ?????????????????????????????????????????????
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
                giveActivityDto.setActiveConfCode(activityConf.getSconfCode());

                logger.info("???????????????????????????????????????{}", giveActivityDto);
                //??????Rest???????????????
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
                        .newInvoker(ActivityServicesDefine.ACTIVITY_GIVE_SERVICE);
                invoke.setRequestObj(giveActivityDto);
                //??????????????????????????????????????????????????????????????????????????????
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() {
                });
                ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
                logger.info("?????????????????????????????????????????????{}", JSON.toJSONString(responseVo));
            }
        } catch (Exception e) {
            logger.error("???????????????????????????????????????{}", e);
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @return ????????????????????????
     * @throws Exception
     */
    @Override
    public MerchantConf getDefaultWechatMerchantConf() throws Exception {
        String merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSONObject.parseObject(json, MerchantConf.class);
    }

    /**
     * ??????????????????
     *
     * @param memberInfoVo
     * @return
     */
    @Override
    public String selectMemberInfoByParam(MemberInfoVo memberInfoVo) {
        return memberInfoDao.selectMemberInfoByParam(memberInfoVo);
    }


    /**
     * ?????????????????????
     *
     * @param memberInfo
     * @return
     */
    @Override
    public ResponseVo manualTermination(MemberInfo memberInfo, HttpServletRequest request) throws Exception {
        UnsignDto unsignDto = new UnsignDto();
        unsignDto.setSmerchantCode(memberInfo.getSmerchantCode());
        unsignDto.setSmemberId(memberInfo.getId());
        unsignDto.setSmemberMerchantCode(memberInfo.getSmerchantCode());
        unsignDto.setSip(RequestUtils.getIpAddr(request));
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_UNSIGN);// ????????????
        // ??????????????????????????????????????????????????????????????????????????????
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
        });
        invoke.setRequestObj(unsignDto); // post ??????
        ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
        if (null == resVO || !resVO.isSuccess()) {
            logger.error("????????????????????????????????????{}", resVO.getMsg());
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
        }
        return ResponseVo.getSuccessResponse();
    }
}