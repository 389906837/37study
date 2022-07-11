package com.cloud.cang.pay.wechat.notify;

import com.cloud.cang.pay.wechat.common.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2015/7/29.
 */
public class FreePayNotifyTemplate {

    private static final Logger logger = LoggerFactory.getLogger(FreePayNotifyTemplate.class);

    private FreePayNotifyData freePayNotifyData;

    public FreePayNotifyTemplate(String notifyXml) {
        freePayNotifyData = XMLParser.getObjectFromXML(notifyXml, FreePayNotifyData.class);
    }

    public String execute(FreePaySuccessCallBack successCallBack) {
        ResponseData responseData = new ResponseData();
        logger.debug("receive data from wechat:" + freePayNotifyData );
        if("SUCCESS".equals(freePayNotifyData.getReturn_code())) {

            if (successCallBack != null) {
                try {
                    successCallBack.onSuccess(freePayNotifyData);
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
        responseData.setReturn_msg(freePayNotifyData.getReturn_msg());
        return XMLParser.toXML(responseData);
    }


}
