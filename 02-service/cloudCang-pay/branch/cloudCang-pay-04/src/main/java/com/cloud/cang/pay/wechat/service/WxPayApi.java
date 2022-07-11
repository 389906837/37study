package com.cloud.cang.pay.wechat.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.core.common.WechatConfigure;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.CreatSmartretailOrderResult;
import com.cloud.cang.pay.EndSmartretailOrderResult;
import com.cloud.cang.pay.QuerySmartretailOrderResult;
import com.cloud.cang.pay.QueryUserAvaiResult;
import com.cloud.cang.pay.wechat.common.HttpService;
import com.cloud.cang.pay.wechat.common.XMLParser;
import com.cloud.cang.pay.wechat.notify.QueryFreePayNotifyData;
import com.cloud.cang.pay.wechat.notify.RefundNotifyData;
import com.cloud.cang.pay.wechat.protocol.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;


import com.cloud.cang.pay.wechat.util.httpclient.WechatPayHttpClientBuilder;
import okhttp3.HttpUrl;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Map<String, Object> unifiedQuerySign(MerchantConf conf, UnifiedQuerySignReqData reqData) throws IOException, SAXException, ParserConfigurationException {
        HttpService httpService = new HttpService(conf);
        String res = httpService.doPost(WechatConfigure.FREE_QUERY_SIGN_URL, reqData);
        logger.debug("unifiedQuerySign get response:" + res);
        return XMLParser.getMapFromXML(res);
    }

    public static Map<String, Object> unifiedUnSign(MerchantConf conf, UnifiedUnsignReqData reqData) throws IOException, SAXException, ParserConfigurationException {
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
            res = res.replace("?", "");
            return XMLParser.getMapFromString(res);
        }
    }

    public CloseableHttpResponse queryUserAvailability(MerchantConf conf, QueryUserAvaData reqData) throws Exception {
 /*       HttpService httpService = new HttpService(conf);
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        // HTTP请求
        String temp = WechatConfigure.QUERY_USER_AVAILABILITY + "?service_id=" + reqData.getService_id() + "&appid=" + reqData.getAppid() + "&openid=" + reqData.getOpenid();
        HttpUrl url = HttpUrl.parse(temp);
        jiami jiam2i = new jiami();
        String token = jiam2i.getToken(privateKey, url, "GET", conf.getSpid(), conf.getSwechatApiv3CertNumber(), "");
        String res = httpService.doGetV3(temp, token);
        QueryUserAvaiResult queryUserAvaiResult = JSON.parseObject(res, QueryUserAvaiResult.class);
        return queryUserAvaiResult;*/


        // HTTP请求
       /* X509Certificate wechatpayCertificate = getCertificate(conf.getSwechatCertUrl());
        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
        listCertificates.add(wechatpayCertificate);*/
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(conf.getSpid(), conf.getSwechatApiv3CertNumber(), privateKey)
             /*   .withWechatpay(listCertificates)*/
                .build();
        URIBuilder uriBuilder = new URIBuilder(WechatConfigure.QUERY_USER_AVAILABILITY);
        uriBuilder.setParameter("service_id", reqData.getService_id());
        uriBuilder.setParameter("openid", reqData.getOpenid());
        uriBuilder.setParameter("appid", reqData.getAppid());
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        return response;

    }

    public CloseableHttpResponse creatSmartretailOrder(MerchantConf conf, CreatSmartretailOrderData reqData) throws Exception {
       /* HttpService httpService = new HttpService(conf);
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        // HTTP请求
        HttpUrl url = HttpUrl.parse(WechatConfigure.CREAT_SMARTRETAIL_ORDER);
        jiami jiam2i = new jiami();
        String str = JSON.toJSONString(reqData);
        String token = jiam2i.getToken(privateKey, url, "POST", conf.getSpid(), conf.getSwechatApiv3CertNumber(), str);
        String res = httpService.doPostV3(WechatConfigure.CREAT_SMARTRETAIL_ORDER, str, token);
        CreatSmartretailOrderResult creatSmartretailOrderResult = JSON.parseObject(res, CreatSmartretailOrderResult.class);
        return creatSmartretailOrderResult;*/
        // HTTP请求
/*        X509Certificate wechatpayCertificate = getCertificate(conf.getSwechatCertUrl());
        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
        listCertificates.add(wechatpayCertificate);*/
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(conf.getSpid(), conf.getSwechatApiv3CertNumber(), privateKey)
               /* .withWechatpay(listCertificates)*/
                .build();
        HttpPost httpPost = new HttpPost(WechatConfigure.CREAT_SMARTRETAIL_ORDER);
        String str = JSON.toJSONString(reqData);
        StringEntity reqEntity = new StringEntity(
                str, ContentType.create("application/json", "utf-8"));
        httpPost.setEntity(reqEntity);
        httpPost.addHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    public CloseableHttpResponse cancelSmartretailOrder(MerchantConf conf, CancelSmartretailOrderData reqData, String out_order_no) throws Exception {
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(conf.getSpid(), conf.getSwechatApiv3CertNumber(), privateKey)
               /* .withWechatpay(listCertificates)*/
                .build();
        String temp = "https://api.mch.weixin.qq.com/v3/payscore/smartretail-orders/" + out_order_no + "/cancel";
        HttpPost httpPost = new HttpPost(temp);
        String str = JSON.toJSONString(reqData);
        StringEntity reqEntity = new StringEntity(
                str, ContentType.create("application/json", "utf-8"));
        httpPost.setEntity(reqEntity);
        httpPost.addHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    public CloseableHttpResponse syncSmartretailOrder(MerchantConf conf, SyncOrderData reqData, String out_order_no) throws Exception {
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(conf.getSpid(), conf.getSwechatApiv3CertNumber(), privateKey)
               /* .withWechatpay(listCertificates)*/
                .build();
        String temp ="https://api.mch.weixin.qq.com/v3/payscore/payafter-orders/" + out_order_no + "/sync";
        HttpPost httpPost = new HttpPost(temp);
        String str = JSON.toJSONString(reqData);
        StringEntity reqEntity = new StringEntity(
                str, ContentType.create("application/json", "utf-8"));
        httpPost.setEntity(reqEntity);
        httpPost.addHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    public CloseableHttpResponse endSmartretailOrder(MerchantConf conf, EndSmartretailOrderData reqData, String
            out_order_no) throws IOException, SAXException, ParserConfigurationException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
      /*  HttpService httpService = new HttpService(conf);
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        // HTTP请求
        String temp = "https://api.mch.weixin.qq.com/v3/payscore/smartretail-orders/" + out_order_no + "/complete";
        HttpUrl url = HttpUrl.parse(temp);
        jiami jiam2i = new jiami();
        String str = JSON.toJSONString(reqData);
        String token = jiam2i.getToken(privateKey, url, "POST", conf.getSpid(), conf.getSwechatApiv3CertNumber(), str);
        String res = httpService.doPostV3(temp, str, token);
        EndSmartretailOrderResult endSmartretailOrderResult = JSON.parseObject(res, EndSmartretailOrderResult.class);
        return endSmartretailOrderResult;*/
        // HTTP请求
/*        X509Certificate wechatpayCertificate = getCertificate(conf.getSwechatCertUrl());
        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
        listCertificates.add(wechatpayCertificate);*/
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(conf.getSpid(), conf.getSwechatApiv3CertNumber(), privateKey)
                /*.withWechatpay(listCertificates)*/
                .build();
        String temp = "https://api.mch.weixin.qq.com/v3/payscore/smartretail-orders/" + out_order_no + "/complete";
        HttpPost httpPost = new HttpPost(temp);
        String str = JSON.toJSONString(reqData);
        StringEntity reqEntity = new StringEntity(
                str, ContentType.create("application/json", "utf-8"));
        httpPost.setEntity(reqEntity);
        httpPost.addHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;


    }

    public CloseableHttpResponse querySmartretailOrder(MerchantConf conf, QuerySmartretailOrderData reqData) throws Exception {
      /*  HttpService httpService = new HttpService(conf);
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        // HTTP请求
        String temp = WechatConfigure.QUERY_SMARTRETAIL_ORDER + "?service_id=" + reqData.getService_id() + "&=" + reqData.getOut_order_no() + "&appid=" + reqData.getAppid();
        HttpUrl url = HttpUrl.parse(temp);
        jiami jiam2i = new jiami();
        String token = jiam2i.getToken(privateKey, url, "GET", conf.getSpid(), conf.getSwechatApiv3CertNumber(), "");
        String res = httpService.doGetV3(temp, token);
        QuerySmartretailOrderResult querySmartretailOrderResult = JSON.parseObject(res, QuerySmartretailOrderResult.class);
        return querySmartretailOrderResult;*/
        // HTTP请求
     /*   X509Certificate wechatpayCertificate = getCertificate(conf.getSwechatCertUrl());
        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
        listCertificates.add(wechatpayCertificate);*/
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(conf.getSpid(), conf.getSwechatApiv3CertNumber(), privateKey)
           /*     .withWechatpay(listCertificates)*/
                .build();
        URIBuilder uriBuilder = new URIBuilder(WechatConfigure.QUERY_SMARTRETAIL_ORDER);
        uriBuilder.setParameter("service_id", reqData.getService_id());
        uriBuilder.setParameter("out_order_no", reqData.getOut_order_no());
        uriBuilder.setParameter("appid", reqData.getAppid());
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        return response;
    }

    protected final String getResponseBody(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();

        return (entity != null && entity.isRepeatable()) ? EntityUtils.toString(entity) : "";
    }

    public void closeSmartretailOrder(MerchantConf conf, String outout_order_no) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("appid", conf.getSwechatPointAppid());
        obj.put("service_id", conf.getSserviceId());
        obj.put("reason", "撤销服务");
        String query = obj.toString();
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        // HTTP请求
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(conf.getSpid(), conf.getSwechatApiv3CertNumber(), privateKey)
                /*.withWechatpay(listCertificates)*/
                .build();
        String temp = "https://api.mch.weixin.qq.com/v3/payscore/smartretail-orders/" + outout_order_no + "/cancel";
        HttpPost httpPost = new HttpPost(temp);
        StringEntity reqEntity = new StringEntity(
                query, ContentType.create("application/json", "utf-8"));
        httpPost.setEntity(reqEntity);
        httpPost.addHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response);
    }

    public void queryCert(MerchantConf conf) throws Exception {

        HttpService httpService = new HttpService(conf);
        PrivateKey privateKey = getPrivateKey(conf.getSwechatPrivateKeyUrl());
        // HTTP请求
        HttpUrl url = HttpUrl.parse("https://api.mch.weixin.qq.com/v3/certificates");
        jiami jiam2i = new jiami();
        String token = jiam2i.getToken(privateKey, url, "GET", conf.getSpid(), conf.getSwechatApiv3CertNumber(), "");
        String res = httpService.doGetV3("https://api.mch.weixin.qq.com/v3/certificates", token);
        System.out.println(res);
    }


    /**
     * 获取私钥。
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */

    public static PrivateKey getPrivateKey(String filename) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

    /**
     * 获取证书。
     *
     * @param filename 证书文件路径  (required)
     * @return X509证书
     */
    public static X509Certificate getCertificate(String filename) throws IOException {
        InputStream fis = new FileInputStream(filename);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(bis);
            cert.checkValidity();
            return cert;
        } catch (CertificateExpiredException e) {
            throw new RuntimeException("证书已过期", e);
        } catch (CertificateNotYetValidException e) {
            throw new RuntimeException("证书尚未生效", e);
        } catch (CertificateException e) {
            throw new RuntimeException("无效的证书文件", e);
        } finally {
            bis.close();
        }
    }
}
