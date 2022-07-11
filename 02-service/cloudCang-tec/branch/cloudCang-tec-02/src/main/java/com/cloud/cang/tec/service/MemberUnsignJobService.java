package com.cloud.cang.tec.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.hy.FreeOperLog;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.*;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.hy.service.FreeDataService;
import com.cloud.cang.tec.hy.service.FreeOperLogService;
import com.cloud.cang.tec.hy.service.MemberInfoService;
import com.cloud.cang.tec.om.service.OrderRecordService;
import com.cloud.cang.tec.om.vo.OrderVo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 会员解密定时任务
 * @author zhouhong
 * @version 1.0
 */
@Service(value = "memberUnsignJobService")
public class MemberUnsignJobService {
    private static final Logger logger = LoggerFactory.getLogger(MemberUnsignJobService.class);

    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private ICached iCached;
    @Autowired
    private FreeOperLogService freeOperLogService;
    @Autowired
    private FreeDataService freeDataService;
    /**
     * 会员代扣协议解签定时查询
     */
    public void unsignAlipay() {
        logger.info("会员代扣协议解签定时查询任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return unsignAlipayQuery();
                } catch (Exception e) {
                    logger.error("会员代扣协议解签定时查询任务失败:{}", e);
                    return "会员代扣协议解签定时查询任务失败";
                }
            }
        }, TecConstants.MEMBER_UNSIGN_ALIPAY_QUERY, true);
    }

    /**
     * 会员代扣协议解签定时查询
     * @return
     * @throws Exception
     */
    private String unsignAlipayQuery() {
        String msg = "会员代扣协议解签定时查询任务执行成功";
        try {
            //查询所有有效用户数据
            List<MemberInfo> memberInfos = memberInfoService.selectByAlipaySign();
            if(null != memberInfos && memberInfos.size() > 0) {
                Integer tempI = null;
                MerchantClientConf clientConf = null;
                String smerchantCode = "";
                InetAddress addr = InetAddress.getLocalHost();
                String sip = addr.getHostAddress().toString(); //获取本机ip
                for (MemberInfo tempM : memberInfos) {
                    smerchantCode = tempM.getSmerchantCode();
                    tempI = (Integer) iCached.get("user_operate_device_key_"+tempM.getId());
                    if (null != tempI && tempI.intValue() == 1) {
                        continue;
                    }
                    clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + smerchantCode);
                    if (null == clientConf || null == clientConf.getIisConfAlipay() || clientConf.getIisConfAlipay().intValue() == 0) {//没有配置获取默认商户编号
                        smerchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
                    }
                    updateMemberAlipaySign(sip,tempM.getId(), smerchantCode);
                }
            }
        } catch (Exception e) {
            logger.error("会员代扣协议解签定时查询任务失败:{}", e);
            msg = "会员代扣协议解签定时查询任务失败";
        }
        return msg;
    }
    /**
     * 更新会员免密数据
     * @param merchantCode 商户编号
     * @param memberId 会员信息
     */
    public void updateMemberAlipaySign(final String clientIp, final String memberId, final String merchantCode) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
                    FreeData freeData = freeDataService.selectByMemberId(memberId, memberInfo.getSmerchantCode());
                    if (StringUtil.isNotBlank(freeData.getSproductCode()) &&
                        freeData.getSproductCode().equals("ONE_TIME_AUTH_P")) {//单次代扣

                        UnsignDto unsignDto = new UnsignDto();
                        unsignDto.setSmemberId(memberId);
                        unsignDto.setSmerchantCode(merchantCode);
                        unsignDto.setSmemberMerchantCode(memberInfo.getSmerchantCode());
                        unsignDto.setSip(clientIp);
                        // 创建Rest服务客户端
                        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_UNSIGN);
                        invoke.setRequestObj(unsignDto);
                        // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                        });
                        ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                        logger.info("免密解约结果：{}", JSONObject.toJSONString(responseVo));
                    }
                } catch (Exception e) {
                    logger.error("支付宝免密解约结果异常：{}", e);
                }
            }
        });
    }
}
