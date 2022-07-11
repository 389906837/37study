package com.cloud.cang.pay.wechat.service;

import com.cloud.cang.core.common.WechatConfigure;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.wechat.common.HttpService;
import com.cloud.cang.pay.wechat.common.XMLParser;
import com.cloud.cang.pay.wechat.notify.QueryFreePayNotifyData;
import com.cloud.cang.pay.wechat.notify.RefundNotifyData;
import com.cloud.cang.pay.wechat.protocol.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import com.cloud.cang.pay.wechat.common.JsonUtil;
import com.thoughtworks.xstream.persistence.XmlMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

/**
 * 微信支付创建预付款API
 */
@Service
public class WxPayApi {

	private static final Logger logger = LoggerFactory.getLogger(WxPayApi.class);

    public Map<String, Object> unifiedOrder(MerchantConf conf, UnifiedOrderReqData reqData) throws IOException, SAXException, ParserConfigurationException {
        HttpService httpService = new HttpService(conf);
        String res = httpService.doPost(WechatConfigure.UNIFIED_ORDER_API, reqData);
        logger.debug("UnifiedOrder get response:" + res);
        return XMLParser.getMapFromXML(res);
    }

    public Map<String, Object> unifiedFreePay(MerchantConf conf, UnifiedFreePayReqData reqData) throws IOException, SAXException, ParserConfigurationException {
        HttpService httpService = new HttpService(conf);
        String res = httpService.doPost(WechatConfigure.FREE_PAY_URL, reqData);
        logger.debug("UnifiedOrder get response:" + res);
        return XMLParser.getMapFromXML(res);
    }

    public RefundNotifyData unifiedRefundAmount(MerchantConf conf, RefundReqData reqData) throws IOException, SAXException, ParserConfigurationException {
        HttpService httpService = new HttpService(conf);
        String res = httpService.doPost(WechatConfigure.REFUND_API, reqData);
        logger.debug("UnifiedOrder get response:" + res);
        return XMLParser.getObjectFromXML(res, RefundNotifyData.class);
    }

    public Map<String, Object> unifiedRepairProcess(MerchantConf conf, BaseReqData reqData, String url) throws IOException, SAXException, ParserConfigurationException {
        HttpService httpService = new HttpService(conf);
        String res = httpService.doPost(url, reqData);
        logger.debug("UnifiedOrder get response:" + res);
        return XMLParser.getMapFromXML(res);
    }

    public QueryFreePayNotifyData unifiedQueryFreePay(MerchantConf conf, BaseReqData reqData) throws IOException, SAXException, ParserConfigurationException {
        HttpService httpService = new HttpService(conf);
        String res = httpService.doPost(WechatConfigure.QUERY_FREE_PAY_URL, reqData);
        logger.debug("UnifiedOrder get response:" + res);
        return XMLParser.getObjectFromXML(res, QueryFreePayNotifyData.class);
    }
    public Map<String,Object> unifiedQuerySign(MerchantConf conf, UnifiedQuerySignReqData reqData) throws IOException, SAXException, ParserConfigurationException {
        HttpService httpService = new HttpService(conf);
        String res = httpService.doPost(WechatConfigure.FREE_QUERY_SIGN_URL, reqData);
        logger.debug("unifiedQuerySign get response:" + res);
        return XMLParser.getMapFromXML(res);
    }

    public static Map<String,Object> unifiedUnSign(MerchantConf conf, UnifiedUnsignReqData reqData) throws IOException, SAXException, ParserConfigurationException  {
        HttpService httpService = new HttpService(conf);
        String res = httpService.doPost(WechatConfigure.FREE_UNSIGN_URL, reqData);
        logger.debug("unifiedUnSign get response:" + res);
        return XMLParser.getMapFromXML(res);
    }
    public Map<String, Object> unifiedDownloadBill(MerchantConf conf, DownloadbillReqData reqData) throws IOException, SAXException, ParserConfigurationException {
        HttpService httpService = new HttpService(conf);
        String res = httpService.doPost(WechatConfigure.DOWNLOAD_BILL_API, reqData);
        logger.debug("unifiedDownloadBill get response:" + res);
        if (res.indexOf("<xml><return_code>") != -1) {
            return XMLParser.getMapFromXML(res);
        } else {
            res = res.replace("?","");
            return XMLParser.getMapFromString(res);
        }
    }
	/*public String getOpenid(String appid, String appSecret, String code) throws Exception {
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + appid
                + "&secret="
                + appSecret
                + "&code=" + code + "&grant_type=authorization_code";
        String res = HttpService.doGet(requestUrl);
        System.out.println(res);
        Map<String, Object> resultMap = JsonUtil.fromJson(res, HashMap.class);
        if (resultMap.get("openid") == null) {
            return null;
        }

        return resultMap.get("openid").toString();
    }*/



   /* public void main(String[] args) throws Exception {
        UnifiedOrderReqData reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder("appid", "mch_id",
                "body", "out_trade_no", 1, "spbill_create_ip", "notify_url", "JSAPI").setOpenid("openid").build();
        System.out.println(UnifiedOrder(reqData));
    }*/
}
