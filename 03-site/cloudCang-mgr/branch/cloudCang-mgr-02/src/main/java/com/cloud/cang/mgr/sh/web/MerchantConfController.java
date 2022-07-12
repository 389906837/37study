package com.cloud.cang.mgr.sh.web;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.domain.MerchantConfDomain;
import com.cloud.cang.mgr.sh.service.*;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.model.sh.MerchantAttach;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;


/**
 * @author yanlingfeng
 * @version 1.0
 * @description 商户微信，支付宝配置
 * @time 2018-3-1
 * @fileName MerchantConfController.java
 */
@Controller
@MultipartConfig
@RequestMapping("/merchantConf")
public class MerchantConfController {

    private static final Logger log = LoggerFactory.getLogger(MerchantConfController.class);


    @Autowired
    MerchantInfoService merchantInfoService;

    @Autowired
    private MerchantConfService merchantConfService;
    @Autowired
    private ICached iCached;

    /**
     * 商户 添加/修改 支付宝/微信 配置
     *
     * @param
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo save(MerchantConfDomain merchantConfDomain) {
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantConfDomain.getSmerchantId());
            if (null == merchantInfo) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请先保存商户基础信息！");
            }
            //执行新增
            if (StringUtils.isBlank(merchantConfDomain.getId())) {
                merchantConfDomain.setTaddTime(DateUtils.getCurrentDateTime());
                merchantConfDomain.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                merchantConfDomain.setTupdateTime(DateUtils.getCurrentDateTime());
                merchantConfDomain.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                merchantConfDomain.setSmerchantCode(merchantInfo.getScode());
                merchantConfDomain.setItype(10);
                merchantConfDomain.setIISWC("1");
                merchantConfDomain.setIdelFlag(0);
                merchantConfService.insertMerchantDomain(merchantConfDomain);
                String WCId = merchantConfDomain.getId();
                merchantConfDomain.setItype(20);
                merchantConfDomain.setIISWC("0");
                merchantConfDomain.setIdelFlag(0);
                merchantConfService.insertMerchantDomain(merchantConfDomain);
                //添加微信配置更新到redis缓存
                String catcheKey = "";
                MerchantConf WcMecchantConf = merchantConfService.selectByPrimaryKey(WCId);
                catcheKey = RedisConst.SH_WECHAT_CONFIG + merchantInfo.getScode();
                iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(WcMecchantConf));
                log.debug("----更新微信配置Redis缓存从KEY:" + RedisConst.SH_WECHAT_CONFIG + "---" + catcheKey + "---" + JSON.toJSONString(WcMecchantConf));
                //添加支付宝配置更新到redis缓存
                MerchantConf aPMecchantConf = merchantConfService.selectByPrimaryKey(merchantConfDomain.getId());
                catcheKey = RedisConst.SH_ALIPAY_CONFIG + merchantInfo.getScode();
                iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(aPMecchantConf));
                log.debug("----更新支付宝配置Redis缓存从KEY:" + RedisConst.SH_ALIPAY_CONFIG + "---" + catcheKey + "---" + JSON.toJSONString(aPMecchantConf));
                //操作日志
                String logText = MessageFormat.format("商户添加支付宝/微信 配置，商户Id{0}", merchantConfDomain.getSmerchantId());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                return ResponseVo.getSuccessResponse();
            } else {
                //执行修改
                merchantConfDomain.setTupdateTime(DateUtils.getCurrentDateTime());
                merchantConfDomain.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                merchantConfDomain.setIISWC("0");
                merchantConfService.updateByDomain(merchantConfDomain);
                String aPId = merchantConfDomain.getId();
                merchantConfDomain.setIISWC("1");
                merchantConfDomain.setId(merchantConfDomain.getWCId());
                merchantConfService.updateByDomain(merchantConfDomain);
                //修改微信配置更新到redis缓存
                String catcheKey = "";
                MerchantConf WcMecchantConf = merchantConfService.selectByPrimaryKey(merchantConfDomain.getWCId());
                catcheKey = RedisConst.SH_WECHAT_CONFIG + merchantInfo.getScode();
                iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(WcMecchantConf));
                log.debug("----更新微信配置Redis缓存从KEY:" + RedisConst.SH_WECHAT_CONFIG + "---" + catcheKey + "---" + JSON.toJSONString(WcMecchantConf));
                //修改支付宝配置更新到redis缓存
                MerchantConf aPMecchantConf = merchantConfService.selectByPrimaryKey(aPId);
                catcheKey = RedisConst.SH_ALIPAY_CONFIG + merchantInfo.getScode();
                iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(aPMecchantConf));
                log.debug("----更新支付宝配置Redis缓存从KEY:" + RedisConst.SH_ALIPAY_CONFIG + "---" + catcheKey + "---" + JSON.toJSONString(aPMecchantConf));
                //操作日志
                String logText = MessageFormat.format("商户修改支付宝/微信 配置，商户Id{0}", merchantConfDomain.getSmerchantId());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return ResponseVo.getSuccessResponse();
            }
        } catch (Exception e) {
            log.error("商户编辑支付宝/微信 配置异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存商户微信/支付宝配置失败");
        }

    }
}