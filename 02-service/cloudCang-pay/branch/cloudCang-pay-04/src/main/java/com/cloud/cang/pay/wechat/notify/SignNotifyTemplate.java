package com.cloud.cang.pay.wechat.notify;

import com.cloud.cang.pay.wechat.common.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 签约通知参数模板
 */
public class SignNotifyTemplate {

    private static final Logger logger = LoggerFactory.getLogger(SignNotifyTemplate.class);

    private SignNotifyData signNotifyData;

    public SignNotifyTemplate(String notifyXml) {
        signNotifyData = XMLParser.getObjectFromXML(notifyXml, SignNotifyData.class);
    }

    public String execute(SignSuccessCallBack successCallBack) {
        ResponseData responseData = new ResponseData();
        logger.debug("receive data from wechat:" + signNotifyData );
        if("SUCCESS".equals(signNotifyData.getReturn_code()) && "SUCCESS".equals(signNotifyData.getResult_code())) {
            if (successCallBack != null) {
                try {
                    successCallBack.onSuccess(signNotifyData);
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
        responseData.setReturn_msg(signNotifyData.getReturn_msg());
        return XMLParser.toXML(responseData);
    }


}
