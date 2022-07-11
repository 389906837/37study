package com.cloud.cang.open.sdk.client;

import com.cloud.cang.open.sdk.common.CloudCangLogger;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.mapping.*;
import com.cloud.cang.open.sdk.mapping.json.ExceptionErrorListener;
import com.cloud.cang.open.sdk.mapping.json.JSONReader;
import com.cloud.cang.open.sdk.mapping.json.JSONWriter;
import com.cloud.cang.open.sdk.request.BaseRequest;
import com.cloud.cang.open.sdk.request.ImgAsyncRecognitionRequest;
import com.cloud.cang.open.sdk.response.BaseResponse;
import com.cloud.cang.open.sdk.util.*;
import com.cloud.cang.open.sdk.utils.EncryptUtils;

import javax.servlet.http.HttpServletRequest;
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
public class ResponseClient {


    private String format;
    private String sign_type;
    private String platformPublicKey;//平台公钥
    private String charset;
    private Integer type;
    private HttpServletRequest request;

    public ResponseClient(HttpServletRequest request, String platformPublicKey) {
        this.format = "json";
        this.sign_type = "RSA2";
        this.charset = "utf-8";
        this.platformPublicKey = platformPublicKey;
        this.request = request;
        this.type = 10;
    }

    public ResponseClient(HttpServletRequest request, String platformPublicKey, Integer type) {
        this.format = "json";
        this.sign_type = "RSA2";
        this.charset = "utf-8";
        this.platformPublicKey = platformPublicKey;
        this.request = request;
        this.type = type;
    }

    public Map<String, Object> parsingByMap() throws CloudCangException {
        BaseRequest baseRequest = null;
        if (type.intValue() == 10) {
            baseRequest = new ImgAsyncRecognitionRequest();
        }
        BaseParser parser = null;
        if("xml".equals(this.format)) {
            parser = new ObjectXmlParser(baseRequest.getResponseClass());
        } else {
            parser = new ObjectJsonParser(baseRequest.getResponseClass());
        }
        return executeByMap(baseRequest, parser);
    }

    private Map<String, Object> executeByMap(BaseRequest baseRequest, BaseParser parser) throws CloudCangException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String responseBody = request.getParameter("encryptCallbackBody");
        try {
            ResponseEncryptItem responseItem = this.encryptResponse(baseRequest, responseBody);
            this.checkResponseSign(baseRequest, responseItem.getRealContent(), parser,true);
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
            String rootNode = baseRequest.getApiMethodName().replace('.', '_') + "_response";
            returnMap.remove(rootNode);
            returnMap.remove("sign");
        } catch (RuntimeException var8) {
            CloudCangLogger.logBizError(responseBody);
            throw var8;
        } catch (CloudCangException var9) {
            CloudCangLogger.logBizError(responseBody);
            throw new CloudCangException(var9);
        }
        return returnMap;
    }

    private ResponseEncryptItem encryptResponse(BaseRequest request, String responseBody) throws CloudCangException {
        String realBody = responseBody;
        if(request.isNeedEncrypt()) {
            try {
                realBody = EncryptUtils.decryptStringUnZip(responseBody);
            } catch (Exception e) {
                throw new CloudCangException(e.getMessage());
            }
        }
        return new ResponseEncryptItem(responseBody, realBody);
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
