package com.cloud.cang.pay.wechat.notify;

import com.cloud.cang.pay.wechat.common.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 微信端解约  通知参数模板
 */
public class UnsignNotifyTemplate {

    private static final Logger logger = LoggerFactory.getLogger(UnsignNotifyTemplate.class);

    private UnsignNotifyData unsignNotifyData;

    public UnsignNotifyTemplate(String notifyXml) {
        unsignNotifyData = XMLParser.getObjectFromXML(notifyXml, UnsignNotifyData.class);
    }

    public String execute(UnsignSuccessCallBack successCallBack) {
        ResponseData responseData = new ResponseData();
        logger.debug("receive data from wechat:" + unsignNotifyData );
        if("SUCCESS".equals(unsignNotifyData.getReturn_code()) && "SUCCESS".equals(unsignNotifyData.getResult_code())) {
            if (successCallBack != null) {
                try {
                    successCallBack.onSuccess(unsignNotifyData);
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
        responseData.setReturn_msg(unsignNotifyData.getReturn_msg());
        return XMLParser.toXML(responseData);
    }


}
