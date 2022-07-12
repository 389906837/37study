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
     * 根据手机号查找用户
     *
     * @param mobile
     * @return
     */
    public MemberInfo selectByMobile(String mobile, String merchantCode) {
        return memberInfoDao.selectByMobile(mobile, merchantCode);
    }

    /**
     * 根据查询条件获取导出用户信息
     *
     * @param memberInfoVo
     * @return
     */
    @Override
    public List<Map<String, Object>> selectMemberInfoByExport(MemberInfoVo memberInfoVo) {
        return memberInfoDao.selectMemberInfoByExport(memberInfoVo);
    }

    /**
     * 短信发送模板编号
     */
    public final static String SEND_MSG_CODE_TMPE = "TPE0058";


    @Override
    public GenericDao<MemberInfo, String> getDao() {
        return memberInfoDao;
    }


    /**
     * 会员用户表列表数据
     */
    @Override
    public Page<MemberInfoDomain> selectAllMemberInfo(Page<MemberInfoDomain> page, MemberInfoVo memberInfoVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<MemberInfoDomain>) memberInfoDao.selectAllMemberInfo(memberInfoVo);
    }

    /**
     * 批量发券全部用户
     */
    @Override
    public List<MemberInfo> selectByWhere(MemberInfoVo memberInfoVo) {
        return memberInfoDao.selectByWhere(memberInfoVo);
    }


    /**
     * 根据ID数据删除会员用户表
     */
    @Override
    public void delete(String[] checkboxId) {
        for (String id : checkboxId) {
            MemberInfo memberInfo = memberInfoDao.selectByPrimaryKey(id);
            if (memberInfo != null) {
                // 删除
                memberInfoDao.deleteByPrimaryKey(memberInfo.getId());
            }
        }
    }

    /**
     * 重置密码
     *
     * @param sid
     */
    @Override
    public void resetPassword(String sid) {
        MemberInfo memeberInfo = memberInfoDao.selectByPrimaryKey(sid);
        if (memeberInfo == null) {
            throw new ServiceException("没有找到用户，重置密码失败！");
        }
        String password = "a" + IdFactory.randomNum(6);

        MemberInfo tempInfo = new MemberInfo();
        tempInfo.setId(sid);
        tempInfo.setSloginPwd(DigestUtils.sha1Hex(password));
        memberInfoDao.updateByIdSelective(tempInfo);

//        // 操作日志
//        String logText = "重置登录用户密码，用户编号：" + memeberInfo.getScode() + " 用户姓名：" + memeberInfo.getSmemberName();
//
//        MessageDto messageDto = new MessageDto();
//        messageDto.setTemplateCode(SEND_MSG_CODE_TMPE);
//        messageDto.setMobile(memeberInfo.getSmobile());
//        if (StringUtils.isNoneBlank(memeberInfo.getSemail())) {
//            messageDto.setEmail(memeberInfo.getSemail());
//        }
//
//        // 称谓
//        String name = "会员";
//        // if(memeberInfo.getIsCertification()==1){
//        name = memeberInfo.getSmemberName().substring(0, 1);
//        // }
//        // 内容
//        Map<String, Object> contentParam = new HashMap<String, Object>();
//        //contentParam.put("callName", name);
//        contentParam.put("password", password);
//        messageDto.setContentParam(contentParam);
//        try {
//            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);// 服务名称
//            invoke.setRequestObj(messageDto); // post 参数
//            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
//            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
//            });
//            ResponseVo<String> responseVo1 = (ResponseVo<String>) invoke.invoke();
//            if (!responseVo1.isSuccess()) {
//                throw new ServiceException("重置密码，发送短信失败，" + responseVo1.getMsg());
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            throw new ServiceException(e.getMessage());
//        }
    }

    /**
     * 用户支付宝免密支付补处理
     *
     * @param
     * @return ResponseVo
     */
    @Override
    public ResponseVo rechargeAgainUpData(SignResult signResult, HttpServletRequest request, String merchantCode) throws Exception {
        logger.debug("支付宝免密支付补处理,获得的参数=" + signResult);
        //获取商户配置信息
       /* MerchantConf conf = rechargeAgainService.getDefaultAlipayMerchantConf();*/
        if (this.dealwithAlipaySign(/*conf,*/ signResult, request, merchantCode)) {
            return ResponseVo.getSuccessResponse();
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户支付宝免密支付补处理失败！");

    }

    /**
     * 用户微信免密支付补处理
     *
     * @param
     * @return ResponseVo
     */
    @Override
    public ResponseVo WechantRechargeAgainUpData(WechatSignResult wechatSignResult, HttpServletRequest request, String merchantCode) throws Exception {
        logger.info("微信免密支付补处理,参数：{}", wechatSignResult);
       /* MerchantConf conf = rechargeAgainService.getDefaultWechatMerchantConf();*/
        //获取用户的支付宝免密数据
        if (this.wechantDealwithAlipaySign(wechatSignResult, request, merchantCode)) {
            return ResponseVo.getSuccessResponse();
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户支付宝免密支付补处理失败！");
    }

    /**
     * 用户微信免密支付补处理更新数据
     *
     * @param
     * @return boolean
     */
    private Boolean wechantDealwithAlipaySign(WechatSignResult wechatSignResult, HttpServletRequest request, String merchantCode) throws Exception {
     /*   if (!conf.getSplanId().equals(wechatSignResult.getPlan_id())) {
            logger.error("处理签约数据异常，商户数据异常");
            return false;
        }*/

        String contractCode = wechatSignResult.getContract_code();//系统商户签约唯一标识
        String contractId = wechatSignResult.getContract_id();//微信委托代扣协议id

        WechatFreeData freeData = wechatFreeDataService.selectByContractCode(contractCode, merchantCode);
        if (freeData == null) {
            logger.error("微信签约数据异常");
            return false;
        }
        if (wechatSignResult.getContract_state().equals("0")) {//签约
            //更新数据
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

            //新增免密操作记录
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
        } else if (wechatSignResult.getContract_state().equals("1")) {//解约

            if ("DELETE".equals(freeData.getSstatus())) {
                logger.info("微信免密协议解约重复处理");
                return true;
            }
            //更新数据
            WechatFreeData updateData = new WechatFreeData();
            updateData.setId(freeData.getId());
            updateData.setSstatus("DELETE");
            updateData.setTunsignTime(DateUtils.parseDateByFormat(wechatSignResult.getContract_terminated_time(), "yyyy-MM-dd HH:mm:ss"));
            updateData.setSunsignWay(wechatSignResult.getContract_termination_mode());
            updateData.setSunsignRemark(wechatSignResult.getContract_termination_remark());
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
            freeOperLog.setSoperIp(SessionUserUtils.getIpAddr(request));
            freeOperLog.setSunsignWay(wechatSignResult.getContract_termination_mode());
            freeOperLog.setSunsignRemark(wechatSignResult.getContract_termination_remark());
            freeOperLogService.insert(freeOperLog);

            //修改用户状态
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIwechatOpen(0);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
        }
        return true;
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
     * 用户支付宝免密支付补处理更新数据
     *
     * @param
     * @return boolean
     */
    private Boolean dealwithAlipaySign(/*MerchantConf conf,*/ SignResult signResult, HttpServletRequest request, String merchantCode) throws Exception {
        String externalAgreementNo = signResult.getExternalAgreementNo();//系统商户签约唯一标识
        String agreementNo = signResult.getAgreementNo();//支付宝签约唯一标识
        FreeData freeData = freeDataService.selectByExternalAgreementNo(externalAgreementNo, merchantCode);
        if (freeData == null) {
            logger.error("支付宝签约数据异常");
            return false;
        }
        if ("NORMAL".equals(signResult.getStatus())) {
            //更新数据
            FreeData updateData = new FreeData();
            updateData.setId(freeData.getId());

            String validTime = signResult.getValidTime();//生效时间
            String invalidTime = signResult.getInvalidTime();//失效时间
            String status = signResult.getStatus();//协议状态
            String signScene = signResult.getSignScene();//签约场景
            String alipayLogonId = signResult.getAlipayLogonId();//支付宝脱敏账号
            String signTime = signResult.getSignTime();//实际签约时间
            String externalLogonId = signResult.getExternalLogonId();//平台签约账户


            updateData.setSagreementNo(agreementNo);
            updateData.setTvalidTime(DateUtils.parseDateByFormat(validTime, "yyyy-MM-dd HH:mm:ss"));
            updateData.setTinvalidTime(DateUtils.parseDateByFormat(invalidTime, "yyyy-MM-dd HH:mm:ss"));
            updateData.setSsignScene(signScene);
            updateData.setSstatus(status);
            updateData.setSalipayLogonId(alipayLogonId);
            updateData.setTsignTime(DateUtils.parseDateByFormat(signTime, "yyyy-MM-dd HH:mm:ss"));
            updateData.setSexternalLogonId(externalLogonId);

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
            freeOperLog.setSoperIp(SessionUserUtils.getIpAddr(request));
            freeOperLogService.insert(freeOperLog);

            //修改用户状态
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIaipayOpen(1);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
            //删除缓存
            iCached.remove("is_remind_open_free_data_" + freeData.getSmemberId());
            //开通免密 调用活动
            openFreeGive(freeData);
        } else if ("TEMP".equals(signResult.getStatus()) || "STOP".equals(signResult.getStatus())) {

            if ("UNSIGN".equals(freeData.getSstatus())) {
                logger.info("支付宝免密协议解约重复处理");
                return true;
            }
            //更新数据
            FreeData updateData = new FreeData();
            updateData.setId(freeData.getId());
            updateData.setSstatus("UNSIGN");
            updateData.setTunsignTime(DateUtils.parseDateByFormat(signResult.getInvalidTime(), "yyyy-MM-dd HH:mm:ss"));
            updateData.setTupdateTime(DateUtils.getCurrentDateTime());
            freeDataService.saveOrUpdate(updateData);


            //新增免密操作记录
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

            //修改用户状态
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(freeData.getSmemberId());
            memberInfo.setIaipayOpen(0);
            memberInfo.setUpdateTime(new Date());
            this.updateBySelective(memberInfo);
        }
        return true;
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
     * 获取默认商户微信配置数据
     *
     * @return 商户微信配置数据
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
     * 根据条件查询
     *
     * @param memberInfoVo
     * @return
     */
    @Override
    public String selectMemberInfoByParam(MemberInfoVo memberInfoVo) {
        return memberInfoDao.selectMemberInfoByParam(memberInfoVo);
    }


    /**
     * 支付宝手动解约
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
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_UNSIGN);// 服务名称
        // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
        });
        invoke.setRequestObj(unsignDto); // post 参数
        ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
        if (null == resVO || !resVO.isSuccess()) {
            logger.error("用户支付宝免密解约异常：{}", resVO.getMsg());
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("会员手动解约失败！");
        }
        return ResponseVo.getSuccessResponse();
    }
}