package com.cloud.cang.open.sdk.client;

import com.cloud.cang.open.sdk.common.CloudCangLogger;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.mapping.*;
import com.cloud.cang.open.sdk.mapping.json.ExceptionErrorListener;
import com.cloud.cang.open.sdk.mapping.json.JSONReader;
import com.cloud.cang.open.sdk.mapping.json.JSONWriter;
import com.cloud.cang.open.sdk.request.BaseRequest;
import com.cloud.cang.open.sdk.response.BaseResponse;
import com.cloud.cang.open.sdk.util.*;
import com.cloud.cang.open.sdk.utils.EncryptUtils;
import redis.clients.jedis.params.Params;

import java.io.IOException;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version 1.0
 * @ClassName: DefaultRequestClient
 * @Description: 默认客户端
 * @Author: zhouhong
 * @Date: 2018/9/3 15:22
 */
public class DefaultRequestClient implements BaseClient {


    private String serverUrl;
    private String appId;
    private String privateKey;
    private String format;
    private String sign_type;
    private String encryptType;
    private String encryptKey;
    private String platformPublicKey;//平台公钥
    private String charset;
    private int connectTimeout;
    private int readTimeout;
    private BaseRequest<? extends BaseResponse> request;
    private BaseParser<? extends BaseResponse> parser;

    public DefaultRequestClient(String serverUrl, String appId, String appSecretKey, String privateKey, String platformPublicKey) {
        this.format = "json";
        this.sign_type = "RSA2";
        this.encryptType = "AES";
        this.connectTimeout = 3000;
        this.readTimeout = 10000;
        this.charset = "utf-8";
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;
        this.encryptKey = appSecretKey;
        this.platformPublicKey = platformPublicKey;
    }

    public DefaultRequestClient(String serverUrl, String appId, String appSecretKey, String privateKey, String platformPublicKey, int readTimeout) {
        this.format = "json";
        this.sign_type = "RSA2";
        this.encryptType = "AES";
        this.connectTimeout = 3000;
        this.readTimeout = readTimeout;
        this.charset = "utf-8";
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;
        this.encryptKey = appSecretKey;
        this.platformPublicKey = platformPublicKey;
    }
    /*public DefaultRequestClient(String serverUrl, String appId, String appSecretKey, String privateKey, String format, String platformPublicKey) {
        this(serverUrl, appId, appSecretKey, privateKey, platformPublicKey);
        this.format = format;
    }

    public DefaultRequestClient(String serverUrl, String appId, String appSecretKey, String privateKey, String platformPublicKey, String format, String charset) {
        this(serverUrl, appId, appSecretKey, privateKey, platformPublicKey);
        this.format = format;
        this.charset = charset;
    }*/

    @Override
    public <T extends BaseResponse> T execute(BaseRequest<T> request) throws CloudCangException {
        BaseParser<T> parser = null;
        if("xml".equals(this.format)) {
            parser = new ObjectXmlParser(request.getResponseClass());
        } else {
            parser = new ObjectJsonParser(request.getResponseClass());
        }
        return (T) this._execute(request, (BaseParser) parser);
    }

    @Override
    public Map<String, Object> executeByMap(BaseRequest request) throws CloudCangException {
        BaseParser parser = null;
        if("xml".equals(this.format)) {
            parser = new ObjectXmlParser(request.getResponseClass());
        } else {
            parser = new ObjectJsonParser(request.getResponseClass());
        }
        return executeByMap(request, parser);
    }

    private Map<String, Object> executeByMap(BaseRequest request, BaseParser parser) throws CloudCangException {
        this.request = request;
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> rt = this.doPost(request);
        if(rt == null) {
            return null;
        } else {
            try {
                ResponseEncryptItem responseItem = this.encryptResponse(request, rt);
                //returnMap.put("body", responseItem.getRealContent());
                this.checkResponseSign(request, responseItem.getRealContent(), parser,true);
                JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
                Object rootObj = reader.read(responseItem.getRealContent());
                if(rootObj instanceof Map) {
                    Map<?, ?> rootJson = (Map)rootObj;
                    Collection<?> values = rootJson.values();
                    Iterator var7 = values.iterator();
                    while(var7.hasNext()) {
                        Object rspObj = var7.next();
                        if(rspObj instanceof Map) {
                            returnMap = (Map)rspObj;
                        }
                    }
                }
                String rootNode = request.getApiMethodName().replace('.', '_') + "_response";
                returnMap.remove(rootNode);
                returnMap.remove("sign");
            } catch (RuntimeException var8) {
                CloudCangLogger.logBizError((String)rt.get("rsp"));
                throw var8;
            } catch (CloudCangException var9) {
                CloudCangLogger.logBizError((String)rt.get("rsp"));
                throw new CloudCangException(var9);
            }
            //returnMap.put("params", rt.get("textParams"));

            return returnMap;
        }
    }

    private <T extends BaseResponse> T _execute(BaseRequest<T> request, BaseParser<T> parser) throws CloudCangException {
        this.request = request;
        this.parser = parser;
        Map<String, Object> rt = this.doPost(request);
        if(rt == null) {
            return null;
        } else {
            BaseResponse tRsp = null;
            try {
                ResponseEncryptItem responseItem = this.encryptResponse(request, rt, parser);
                tRsp = parser.parse(responseItem.getRealContent());
                tRsp.setBody(responseItem.getRealContent());
                this.checkResponseSign(request, parser, responseItem.getRealContent(), tRsp.isSuccess());
            } catch (RuntimeException var8) {
                CloudCangLogger.logBizError((String)rt.get("rsp"));
                throw var8;
            } catch (CloudCangException var9) {
                CloudCangLogger.logBizError((String)rt.get("rsp"));
                throw new CloudCangException(var9);
            }

            tRsp.setParams((BaseHashMap)rt.get("textParams"));
            if(!tRsp.isSuccess()) {
                CloudCangLogger.logErrorScene(rt, tRsp, "");
            }

            return (T) tRsp;
        }
    }


    private <T extends BaseResponse> Map<String, Object> doPost(BaseRequest<T> request) throws CloudCangException {
        Map<String, Object> result = new HashMap();
        RequestParametersHolder requestHolder = this.getRequestHolderWithSign(request);
        String url = this.getRequestUrl(requestHolder);
        if(CloudCangLogger.isBizDebugEnabled().booleanValue()) {
            CloudCangLogger.logBizDebug(this.getRedirectUrl(requestHolder));
        }
        String rsp = null;
        try {
            rsp = WebUtils.doPost(url, requestHolder.getApplicationParams(), this.charset, this.connectTimeout, this.readTimeout);
        } catch (IOException var10) {
            throw new CloudCangException(var10);
        }
        result.put("rsp", rsp);
        if(request.isNeedEncrypt()) {
            requestHolder.getApplicationParams().put("bizContent", BaseEncrypt.decryptContent(requestHolder.getApplicationParams().get("bizContent"),this.encryptType, this.encryptKey, this.charset));
            result.put("textParams", requestHolder.getApplicationParams());
        } else {
            result.put("textParams", requestHolder.getApplicationParams());
        }
        result.put("protocalMustParams", requestHolder.getProtocalMustParams());
        result.put("protocalOptParams", requestHolder.getProtocalOptParams());
        result.put("url", url);
        return result;
    }


    private <T extends BaseResponse> RequestParametersHolder getRequestHolderWithSign(BaseRequest<?> request) throws CloudCangException {
        RequestParametersHolder requestHolder = new RequestParametersHolder();
        BaseHashMap appParams = new BaseHashMap(request.getTextParams());

        try {
            if(request.getClass().getMethod("getBizContent", new Class[0]) != null && StringUtils.isEmpty((String)appParams.get("bizContent")) && request.getBizModel() != null) {
                appParams.put("bizContent", (new JSONWriter()).write(request.getBizModel(), true));
            }
        } catch (NoSuchMethodException var11) {
            CloudCangLogger.logBizError(var11.getMessage());
        } catch (SecurityException var12) {
            CloudCangLogger.logBizError(var12.getMessage());
        }

        if(request.isNeedEncrypt()) {
            if(StringUtils.isEmpty(appParams.get("bizContent"))) {
                throw new CloudCangException("当前API不支持加密请求");
            }
            if(!StringUtils.areNotEmpty(new String[]{this.encryptKey, this.encryptType})) {
                throw new CloudCangException("API请求要求加密，则必须设置密钥和密钥类型：encryptKey=" + this.encryptKey + ",encryptType=" + this.encryptType);
            }

            String encryptContent = BaseEncrypt.encryptContent((String)appParams.get("bizContent"), this.encryptType, this.encryptKey, this.charset);
            appParams.put("bizContent", encryptContent);
        }

        requestHolder.setApplicationParams(appParams);
        if(StringUtils.isEmpty(this.charset)) {
            this.charset = "UTF-8";
        }

        BaseHashMap protocalMustParams = new BaseHashMap();
        protocalMustParams.put("methodName", EncryptUtils.encryptStringUnZip(request.getApiMethodName()));
        protocalMustParams.put("version", request.getApiVersion());
        protocalMustParams.put("appId", EncryptUtils.encryptStringUnZip(this.appId));
        protocalMustParams.put("signType", this.sign_type);
        protocalMustParams.put("notifyUrl", request.getNotifyUrl());
        protocalMustParams.put("returnUrl", request.getReturnUrl());
        protocalMustParams.put("charset", this.charset);
        if(request.isNeedEncrypt()) {
            protocalMustParams.put("encryptType", this.encryptType);
        }

        Long timestamp = Long.valueOf(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        protocalMustParams.put("timestamp", df.format(new Date(timestamp.longValue())));
        requestHolder.setProtocalMustParams(protocalMustParams);
        BaseHashMap protocalOptParams = new BaseHashMap();
        protocalOptParams.put("format", this.format);
        requestHolder.setProtocalOptParams(protocalOptParams);
        if(!StringUtils.isEmpty(this.sign_type)) {
            String signContent = BaseSignature.getSignatureContent(requestHolder);
            protocalMustParams.put("sign", BaseSignature.rsaSign(signContent, this.privateKey, this.charset, this.sign_type));
        } else {
            protocalMustParams.put("sign", "");
        }

        return requestHolder;
    }


    private String getRequestUrl(RequestParametersHolder requestHolder) throws CloudCangException {
        StringBuffer urlSb = new StringBuffer(this.serverUrl);

        try {
            String sysMustQuery = WebUtils.buildQuery(requestHolder.getProtocalMustParams(), this.charset);
            String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), this.charset);
            urlSb.append("?");
            urlSb.append(sysMustQuery);
           /* urlSb.append("sysData=");
            urlSb.append(EncryptUtils.encryptStringUnZip(sysMustQuery));*/
            if(sysOptQuery != null & sysOptQuery.length() > 0) {
                urlSb.append("&");
                urlSb.append(sysOptQuery);
                /*urlSb.append("&sysOptData=");
                urlSb.append(EncryptUtils.encryptStringUnZip(sysOptQuery));*/
            }
        } catch (IOException var5) {
            throw new CloudCangException(var5);
        }

        return urlSb.toString();
    }


    private String getRedirectUrl(RequestParametersHolder requestHolder) throws CloudCangException {
        StringBuffer urlSb = new StringBuffer(this.serverUrl);

        try {
            Map<String, String> sortedMap = BaseSignature.getSortedMap(requestHolder);
            String sortedQuery = WebUtils.buildQuery(sortedMap, this.charset);
            urlSb.append("?");
            urlSb.append(sortedQuery);
        } catch (IOException var5) {
            throw new CloudCangException(var5);
        }

        return urlSb.toString();
    }



    private <T extends BaseResponse> ResponseEncryptItem encryptResponse(BaseRequest<T> request, Map<String, Object> rt, BaseParser<T> parser) throws CloudCangException {
        String responseBody = (String)rt.get("rsp");
        String realBody = null;
        if(request.isNeedEncrypt()) {
            //realBody = parser.encryptSourceData(request, responseBody, this.format, this.encryptType, this.encryptKey, this.charset);
            try {
                realBody = EncryptUtils.decryptStringUnZip(responseBody);
            } catch (Exception e) {
               throw new CloudCangException(e.getMessage());
            }
        } else {
            realBody = (String)rt.get("rsp");
        }

        return new ResponseEncryptItem(responseBody, realBody);
    }
    private ResponseEncryptItem encryptResponse(BaseRequest request, Map<String, Object> rt) throws CloudCangException {
        String responseBody = (String)rt.get("rsp");
        String realBody = null;
        if(request.isNeedEncrypt()) {
            try {
                realBody = EncryptUtils.decryptStringUnZip(responseBody);
            } catch (Exception e) {
                throw new CloudCangException(e.getMessage());
            }
        } else {
            realBody = (String)rt.get("rsp");
        }
        return new ResponseEncryptItem(responseBody, realBody);
    }

    static {
        Security.setProperty("jdk.certpath.disabledAlgorithms", "");
    }



    private <T extends BaseResponse> void checkResponseSign(BaseRequest<T> request, BaseParser<T> parser, String responseBody, boolean responseIsSucess) throws CloudCangException {
        if(!StringUtils.isEmpty(this.platformPublicKey)) {
            SignItem signItem = parser.getSignItem(request, responseBody);
            if(signItem == null) {
                throw new CloudCangException("sign check fail: Body is Empty!");
            }

            if(responseIsSucess || !responseIsSucess && !StringUtils.isEmpty(signItem.getSign())) {
                boolean rsaCheckContent = BaseSignature.rsaCheck(signItem.getSignSourceDate(), signItem.getSign(), this.platformPublicKey, this.charset, this.sign_type);
                if(!rsaCheckContent) {
                    if(StringUtils.isEmpty(signItem.getSignSourceDate()) || !signItem.getSignSourceDate().contains("\\/")) {
                        throw new CloudCangException("sign check fail: check Sign and Data Fail!");
                    }

                    String srouceData = signItem.getSignSourceDate().replace("\\/", "/");
                    boolean jsonCheck = BaseSignature.rsaCheck(srouceData, signItem.getSign(), this.platformPublicKey, this.charset, this.sign_type);
                    if(!jsonCheck) {
                        throw new CloudCangException("sign check fail: check Sign and Data Fail！JSON error！");
                    }
                }
            }
        }
    }


    private void checkResponseSign(BaseRequest request, String responseBody, BaseParser parser, boolean responseIsSucess) throws CloudCangException {
        if(!StringUtils.isEmpty(this.platformPublicKey)) {
            SignItem signItem = parser.getSignItem(request, responseBody);
            if(signItem == null) {
                throw new CloudCangException("sign check fail: Body is Empty!");
            }
            if(responseIsSucess || !responseIsSucess && !StringUtils.isEmpty(signItem.getSign())) {
                boolean rsaCheckContent = BaseSignature.rsaCheck(signItem.getSignSourceDate(), signItem.getSign(), this.platformPublicKey, this.charset, this.sign_type);
                if(!rsaCheckContent) {
                    if(StringUtils.isEmpty(signItem.getSignSourceDate()) || !signItem.getSignSourceDate().contains("\\/")) {
                        throw new CloudCangException("sign check fail: check Sign and Data Fail!");
                    }

                    String srouceData = signItem.getSignSourceDate().replace("\\/", "/");
                    boolean jsonCheck = BaseSignature.rsaCheck(srouceData, signItem.getSign(), this.platformPublicKey, this.charset, this.sign_type);
                    if(!jsonCheck) {
                        throw new CloudCangException("sign check fail: check Sign and Data Fail！JSON error！");
                    }
                }
            }
        }
    }

}
