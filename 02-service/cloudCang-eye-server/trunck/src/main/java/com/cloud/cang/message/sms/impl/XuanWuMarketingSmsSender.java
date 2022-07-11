package com.cloud.cang.message.sms.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.message.exception.SMSException;
import com.cloud.cang.message.sms.ISmsSender;
import com.cloud.cang.message.xx.service.SupplierInfoService;
import com.cloud.cang.model.xx.SupplierInfo;
import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.AccountInfo;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 玄武营销短信
 * Created by Changtanchang on 2017/12/21.
 */
@Service
public class XuanWuMarketingSmsSender implements ISmsSender {

    // 根据Code获取供应商
    @Autowired
    private SupplierInfoService supplierInfoService;

    // 本类日志
    private static Logger LOGGER = LoggerFactory.getLogger(XuanWuMarketingSmsSender.class);

    // 手机批量发送锁
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

    // 多发发送
    @SuppressWarnings("unused")
    @Override
    public String batchSendSameContent(List<String> mobiles, String content) throws SMSException {
        if (mobiles == null || mobiles.size() == 0) {
            throw new SMSException("手机号不能为空");
        }
        // 返回信息
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
                if (currentCount == 100 || (currentCount < 100 && totalCount == lockMobiles.size())) {
                    sbStr.append(lockMobiles.get(totalCount - 1) + ";");
                    if (sbStr != null && sbStr.toString().endsWith(";")) {
                        String mobileStr = sbStr.substring(0, sbStr.length() - 1);
                        String rtnValue = sendMessage(content, mobileStr, currentCount);
                        returnStr.append(rtnValue + "|");

                    }
                    currentCount = 0;
                    sbStr.setLength(0);
                    sbStr = new StringBuilder(1500);
                } else {
                    sbStr.append(lockMobiles.get(totalCount - 1) + ";");
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
                return returnStr.substring(0, returnStr.length() - 1);
            }

            return returnStr.toString();
        }
        return "";

    }

    /**
     * 单发送短信
     *
     * @param content
     * @param mobileStr
     * @return
     * @throws Exception
     */
    private String sendMessage(String content, String mobileStr, Integer currentCount) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("xuanwumarketing sms send message before mobile:{},content:{},mobileCount:{}", new Object[] { mobileStr, content, currentCount });
        }
        SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_XUANWU_MARKING_CODE);
        if (supplierInfo == null) {
            LOGGER.error("=============> supplier is disabled ");
            return "-99";

        }
        //content = content + Constrants.MESSAGE_SIGNATURE;

        MTPack pack = new MTPack();
        //pack.setBatchID(UUID.randomUUID());
        //pack.setBatchName("短信测试批次");
        pack.setMsgType(MTPack.MsgType.SMS);
        pack.setBizType(0);
        pack.setDistinctFlag(false);
        pack.setSendType(MTPack.SendType.MASS);
        ArrayList<MessageData> msgs = new ArrayList<MessageData>();
        if(mobileStr.indexOf(";") != -1) {
            String[] arr = mobileStr.split(";");
            for (String str : arr) {
                msgs.add(MessageData.getInstance(str, content ,null ,currentCount));
            }
        } else {
            /** 单发，一号码一内容 */
            msgs.add(new MessageData(mobileStr, content));
        }
        pack.setMsgs(msgs);
        Account ac = new Account(supplierInfo.getSaccName(), supplierInfo.getSaccPassword());
        PostMsg pm = new PostMsg();

        String extInfo = supplierInfo.getSextInfo();
        @SuppressWarnings("unchecked")
        Map<String, String> extInfoMap = JSON.parseObject(extInfo, Map.class);
        pm.getCmHost().setHost(extInfoMap.get("cmIp") + "", Integer.parseInt(extInfoMap.get("cmPort")));// 设置网关的IP和port，用于发送信息
        pm.getWsHost().setHost(extInfoMap.get("wsIp") + "", Integer.parseInt(extInfoMap.get("wsPort")));// 设置网关的
        GsmsResponse resp = pm.post(ac, pack);
        System.out.println(resp.getResult());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("xuanwumarketing sms send message end mobile:{},return value:{}", new Object[] { mobileStr, resp.getMessage() });
        }
        return String.valueOf(resp.getMessage());

    }

    @Override
    public String queryRemainMessageCount() {
        SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_XUANWU_MARKING_CODE);
        Account ac = new Account(supplierInfo.getSaccName(), supplierInfo.getSaccPassword());
        PostMsg pm = new PostMsg();
        String extInfo = supplierInfo.getSextInfo();
        @SuppressWarnings("unchecked")
        Map<String, String> extInfoMap = JSON.parseObject(extInfo, Map.class);
        pm.getCmHost().setHost(extInfoMap.get("cmIp") + "", Integer.parseInt(extInfoMap.get("cmPort")));// 设置网关的IP和port，用于发送信息
        pm.getWsHost().setHost(extInfoMap.get("wsIp") + "", Integer.parseInt(extInfoMap.get("wsPort")));// 设置网关的
        AccountInfo accountInfo = null;
        try {
            accountInfo = pm.getAccountInfo(ac);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        //余额
        if (null != accountInfo) {
            return accountInfo.getBalance()+"";

        }
        return "-1";

    }

    public static void main(String[] args) {
        String mobile = "15601990466";
        String content = "亲！年终大清仓（活动名称）活动开始了，即日起满100立减50还返10元券~另有各种国内外优质零食火热上新中！速速前往你身边的37号仓无人小店采购吧！回t退订";
        if (StringUtils.isBlank(mobile)) {
            throw new SMSException("手机号不能为空");
        }
        if (!ValidateUtils.isMobile(mobile)) {
            throw new SMSException("手机号格式不正确");
        }
        try {
            send(content,mobile,1) ;
        } catch (Exception e) {
            LOGGER.error("发送失败", e);
            throw new SMSException(e);
        }


    }
    private static String send(String content, String mobile, Integer num) throws Exception{
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("XuanwuMarketing sms send message before mobile:{},content:{},mobileCount:{}", new Object[] { mobile, content, num });
        }
        MTPack pack = new MTPack();
        pack.setMsgType(MTPack.MsgType.SMS);
        pack.setBizType(0);
        pack.setDistinctFlag(false);
        pack.setSendType(MTPack.SendType.MASS);
        ArrayList<MessageData> msgs = new ArrayList<MessageData>();
        if(mobile.indexOf(";") != -1) {
            String[] arr = mobile.split(";");
            for (String str : arr) {
                msgs.add(MessageData.getInstance(str, content ,null ,num));
            }
        } else {
            /** 单发，一号码一内容 */
            msgs.add(new MessageData(mobile, content));
        }
        pack.setMsgs(msgs);
        Account ac = new Account("yx@37hc", "vu5iNRb9");
        PostMsg pm = new PostMsg();

        String extInfo = "{cmIp:\"211.147.239.62\",cmPort:\"9080\",wsIp:\"211.147.239.62\",wsPort:\"9070\"}";
        @SuppressWarnings("unchecked")
        Map<String, String> extInfoMap = JSON.parseObject(extInfo, Map.class);
        pm.getCmHost().setHost(extInfoMap.get("cmIp") + "", Integer.parseInt(extInfoMap.get("cmPort")));// 设置网关的IP和port，用于发送信息
        pm.getWsHost().setHost(extInfoMap.get("wsIp") + "", Integer.parseInt(extInfoMap.get("wsPort")));// 设置网关的
        GsmsResponse resp = pm.post(ac, pack);
        System.out.println(resp.getResult());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("XuanwuMarketing sms send message end mobile:{},return value:{}", new Object[] { mobile, resp.getMessage() });
        }
        return String.valueOf(resp.getMessage());
    }
}
