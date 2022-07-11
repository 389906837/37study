package com.cloud.cang.pay.wechat.notify;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.pay.wechat.common.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by YLF on 2019/8/6.
 */
public class ConfirmOrderNotifyTemplate {
    private static final Logger logger = LoggerFactory.getLogger(OpenNotifyTemplate.class);

    private WechatPointsNotifyData wechatPointsNotifyData;

    public ConfirmOrderNotifyTemplate(String paramsJson) {
        wechatPointsNotifyData = JSON.parseObject(paramsJson, WechatPointsNotifyData.class);
    }

    public String execute(ConfirmOrderNotifyCallBack confirmOrderNotifyCallBack) {
        WechatPointResponseData responseData = new WechatPointResponseData();
        logger.debug("receive data from wechat:" + wechatPointsNotifyData);
            if (confirmOrderNotifyCallBack != null) {
                try {
                    confirmOrderNotifyCallBack.onSuccess(wechatPointsNotifyData);
                    responseData.setCode("SUCCESS");
                    responseData.setMessage("OK");
                    return JSON.toJSONString(responseData);
                } catch (Exception e) {
                    responseData.setCode("FAIL");
                    responseData.setMessage(e.getMessage());
                }
            }
        responseData.setCode("FAIL");
        //  responseData.setReturn_msg(payNotifyData.getReturn_msg());
        return JSON.toJSONString(responseData);

    }
}
