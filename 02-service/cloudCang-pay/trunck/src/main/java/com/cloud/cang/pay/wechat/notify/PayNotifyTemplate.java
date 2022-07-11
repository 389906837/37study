package com.cloud.cang.pay.wechat.notify;

import com.cloud.cang.pay.wechat.common.XMLParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2015/7/29.
 */
public class PayNotifyTemplate {

    private static final Logger logger = LoggerFactory.getLogger(PayNotifyTemplate.class);
    
    private PayNotifyData payNotifyData;

    public PayNotifyTemplate(String notifyXml) {
//        boolean isValid = Signature.checkIsSignValidFromResponseString(notifyXml);
//        if (isValid) {
            payNotifyData = XMLParser.getObjectFromXML(notifyXml, PayNotifyData.class);
//        }

    }

    public String execute(PaySuccessCallBack successCallBack) {
        ResponseData responseData = new ResponseData();
//        if (payNotifyData == null) {
//            responseData.setReturn_code("FAIL");
//            responseData.setReturn_msg("签名错误");
//
//            return XMLParser.toXML(responseData);
//        }
        logger.debug("receive data from wechat:" + payNotifyData );
        if("SUCCESS".equals(payNotifyData.getReturn_code())) {

            if (successCallBack != null) {
                try {
                    successCallBack.onSuccess(payNotifyData);
                    responseData.setReturn_code("SUCCESS");
                    responseData.setReturn_msg("OK");
                    return XMLParser.toXML(responseData);
                } catch (Exception e) {
                    responseData.setReturn_code("FAIL");
                    responseData.setReturn_msg(e.getMessage());
                }
            }
        }
        responseData.setReturn_code("FAIL");
        responseData.setReturn_msg(payNotifyData.getReturn_msg());
        return XMLParser.toXML(responseData);

    }


}
