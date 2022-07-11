package com.cloud.cang.open.sdk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.open.sdk.mapping.StringUtils;
import com.cloud.cang.open.sdk.util.BaseSignature;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 验证异步回调数据是否正确
 * Created by yan on 2018/11/22.
 */
public class RecSignature {

    public static Map<String,String> rsaCheck(HttpServletRequest request, String charset, String platformPublicKey, String signType) throws Exception {
        String str = (String) request.getParameter("backData");
        //解密为Json的Map
        String temp = EncryptUtils.decryptStringUnZip(str);
        //转为Map
        Map<String, String> map = JSON.parseObject(temp, Map.class);
        //验证签名
        //String signContent = BaseSignature.getSignContent(map);
        String sign = map.get("sign");
        map.remove("sign");
        String signContent = JSONObject.toJSONString(map);
        boolean rsaCheckContent = BaseSignature.rsaCheck(signContent, sign, platformPublicKey, charset, signType);
        if (!rsaCheckContent) {
            if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(signContent) || !signContent.contains("\\/")) {
                throw new ServiceException("INVALID-SIGN");
            }
            String srouceData = signContent.replace("\\/", "/");
            boolean jsonCheck = BaseSignature.rsaCheck(srouceData, sign, platformPublicKey, charset, signType);
            if (!jsonCheck) {
                throw new ServiceException("INVALID-SIGN");
            }
        }
        return map;
    }
}
