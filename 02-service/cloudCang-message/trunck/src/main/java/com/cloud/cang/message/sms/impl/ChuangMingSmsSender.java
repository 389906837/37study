package com.cloud.cang.message.sms.impl;

import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.message.exception.SMSException;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.message.sms.ISmsSender;
import com.cloud.cang.message.xx.service.SupplierInfoService;
import com.shcm.bean.BalanceResultBean;
import com.shcm.bean.SendResultBean;
import com.shcm.send.DataApi;
import com.shcm.send.OpenApi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 创明营销短信
 *
 * @author zhouhong
 * @version 1.0
 */
@Service
public class ChuangMingSmsSender implements ISmsSender {

    @Autowired
    private SupplierInfoService supplierInfoService;

    private final String sOpenUrl = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
    private final String sDataUrl = "http://smsapi.c123.cn/DataPlatform/DataApi";
    // 默认使用的签名编号(未指定签名编号时传此值到服务器)
    private static final int csid = 0;
    // 通道组编号
    private int cgid = 1765;
    private static Logger LOGGER = LoggerFactory
            .getLogger(ChuangMingSmsSender.class);
    //手机批量发送锁
    private Lock lock = new ReentrantLock();

    @Override
    public String send(String mobile, String content) throws SMSException {
        if (StringUtils.isBlank(mobile)) {
            throw new SMSException("手机号不能为空");
        }
        if (!ValidateUtils.isMobile(mobile)) {
            throw new SMSException("手机号格式不正确");
        }
        try {
            return sendMessage(content, mobile, 1);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new SMSException(e);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public String batchSendSameContent(List<String> mobiles, String content)
            throws SMSException {


        if (mobiles == null || mobiles.size() == 0) {
            throw new SMSException("手机号不能为空");
        }
        //返回信息
        StringBuffer returnStr = new StringBuffer();
        try {
            lock.lock();
            List<String> lockMobiles = mobiles.subList(0, mobiles.size());
            // 当前记录数
            int currentCount = 0;
            StringBuilder sbStr = new StringBuilder(1500);
            // 总记录数
            int totalCount = 0;
            for (int i = 0; i < lockMobiles.size(); i++) {
                currentCount++;
                totalCount++;
                if (currentCount == 100
                        || (currentCount < 100 && totalCount == lockMobiles.size())) {
                    sbStr.append(lockMobiles.get(totalCount - 1) + ",");
                    if (sbStr != null && sbStr.toString().endsWith(",")) {
                        String mobileStr = sbStr.substring(0,
                                sbStr.length() - 1);
                        String rtnValue = sendMessage(content, mobileStr, currentCount);
                        returnStr.append(rtnValue + "|");
                    }
                    currentCount = 0;
                    sbStr.setLength(0);
                    sbStr = new StringBuilder(1500);
                } else {
                    sbStr.append(lockMobiles.get(totalCount - 1) + ",");
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new SMSException(e);
        } finally {
            lock.unlock();
        }

        if (returnStr != null) {
            if (returnStr.toString().endsWith("|")) {
                return returnStr.substring(0,
                        returnStr.length() - 1);
            }
            return returnStr.toString();
        }
        return "";
    }

    /**
     * 发送短信
     *
     * @param content
     * @param mobileStr
     * @return
     * @throws Exception
     */
    private String sendMessage(String content,
                               String mobileStr, Integer currentCount) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("chuangming sms send message before mobile:{},content:{},mobileCount:{}", new Object[]{mobileStr, content, currentCount});
        }

        SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_CHUANGMING_CODE);
        if (supplierInfo == null) {
            LOGGER.error("=============> supplier is disabled ");
            return "-99";

        }
        // 接口帐号
        String account = supplierInfo.getSaccName();
        // 接口密钥
        String authkey = supplierInfo.getSaccPassword();

        // 发送参数
        OpenApi.initialzeAccount(sOpenUrl, account, authkey, cgid, csid);
        // 状态及回复参数
        DataApi.initialzeAccount(sDataUrl, account, authkey);
        List<SendResultBean> items = OpenApi.sendOnce(mobileStr, content, 0, 0, null);
        StringBuilder returnMsg = new StringBuilder();
        String issuccess = "";
        for (SendResultBean t : items) {
            if (t.getResult() < 1) {
                issuccess = t.getResult() + "";
                LOGGER.error("send chuangming message error:{}", t.getErrMsg());
            } else {
                returnMsg.append("发送成功: 消息编号<" + t.getMsgId() + "> 总数<"
                        + t.getTotal() + "> 余额<" + t.getRemain() + ">");
                issuccess = t.getMsgId() + "";
            }
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("chuangming sms send message end mobile:{},return value:{}", new Object[]{mobileStr, returnMsg});
        }
        return issuccess;

    }

    @Override
    public String queryRemainMessageCount() {
        SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_CHUANGMING_CODE);
        // 接口帐号
        String account = supplierInfo.getSaccName();
        // 接口密钥
        String authkey = supplierInfo.getSaccPassword();
        // 发送参数
        OpenApi.initialzeAccount(sOpenUrl, account, authkey, cgid, csid);
        // 状态及回复参数
        DataApi.initialzeAccount(sDataUrl, account, authkey);
        BalanceResultBean resultBean = OpenApi.getBalance();
        return String.valueOf(resultBean.getRemain());
    }

}
