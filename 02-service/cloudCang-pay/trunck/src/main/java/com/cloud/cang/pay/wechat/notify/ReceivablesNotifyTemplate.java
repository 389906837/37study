package com.cloud.cang.pay.wechat.notify;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.pay.wechat.common.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by YLF on 2019/8/5.
 */
public class ReceivablesNotifyTemplate {
    private static final Logger logger = LoggerFactory.getLogger(OpenNotifyTemplate.class);

    private WechatPointsNotifyData receivablesNotifyData;

    public ReceivablesNotifyTemplate(String paramsJson) {
        receivablesNotifyData = JSON.parseObject(paramsJson, WechatPointsNotifyData.class);
    }

    public String execute(ReceivablesNotifyCallBack receivablesNotifyCallBack) {
        WechatPointResponseData responseData = new WechatPointResponseData();
        logger.debug("receive data from wechat:" + receivablesNotifyData);
            if (receivablesNotifyCallBack != null) {
                try {
                    receivablesNotifyCallBack.onSuccess(receivablesNotifyData);
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
