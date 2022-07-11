package com.cloud.cang.pay.wechat.notify;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.pay.wechat.common.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by YLF on 2019/7/22.
 */
public class OpenNotifyTemplate {
    private static final Logger logger = LoggerFactory.getLogger(OpenNotifyTemplate.class);

    private WechatPointsNotifyData openNotifyData;

    public OpenNotifyTemplate(String paramsJson) {
        openNotifyData = JSON.parseObject(paramsJson, WechatPointsNotifyData.class);
    }

    public String execute(OpenNotifyCallBack openNotifyCallBack) {
        WechatPointResponseData responseData = new WechatPointResponseData();
        logger.debug("open/close service from wechat:" + openNotifyData);
     /*   if ("PAYSCORE.USER_OPEN_SERVICE".equals(openNotifyData.getEvent_type())) {*/
        if (openNotifyCallBack != null) {
            try {
                openNotifyCallBack.onSuccess(openNotifyData);
                responseData.setCode("SUCCESS");
                responseData.setMessage("OK");
                return JSON.toJSONString(responseData);
            } catch (Exception e) {
                responseData.setCode("FAIL");
                responseData.setMessage(e.getMessage());
            }
        }
    /*    }*/
        responseData.setCode("FAIL");
        //  responseData.setReturn_msg(payNotifyData.getReturn_msg());
        return JSON.toJSONString(responseData);
    }
}
