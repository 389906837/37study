package com.cloud.cang.open.sdk.util;


import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.mapping.StringUtils;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @version 1.0
 * @Description: 签名工具类
 * @Author: zhouhong
 * @Date: 2018/9/4 11:00
 */
public class BaseSignature {

    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    public BaseSignature() {
    }

    public static String getSignatureContent(RequestParametersHolder requestHolder) {
        return getSignContent(getSortedMap(requestHolder));
    }

    public static Map<String, String> getSortedMap(RequestParametersHolder requestHolder) {
        Map<String, String> sortedParams = new TreeMap();
        BaseHashMap appParams = requestHolder.getApplicationParams();
        if(appParams != null && appParams.size() > 0) {
            sortedParams.putAll(appParams);
        }

        BaseHashMap protocalMustParams = requestHolder.getProtocalMustParams();
        if(protocalMustParams != null && protocalMustParams.size() > 0) {
            sortedParams.putAll(protocalMustParams);
        }

        BaseHashMap protocalOptParams = requestHolder.getProtocalOptParams();
        if(protocalOptParams != null && protocalOptParams.size() > 0) {
            sortedParams.putAll(protocalOptParams);
        }

        return sortedParams;
    }

    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for(int i = 0; i < keys.size(); ++i) {
            String key = (String)keys.get(i);
            String value = (String)sortedParams.get(key);
            if (key.equals("sign")) {
                continue;
            }
            if(StringUtils.areNotEmpty(new String[]{key, value})) {
                content.append((index == 0?"":"&") + key + "=" + value);
                ++index;
            }
        }

        return content.toString();
    }

    public static String rsaSign(String content, String privateKey, String charset, String signType) throws CloudCangException {
        if("RSA".equals(signType)) {
            return rsaSign(content, privateKey, charset);
        } else if("RSA2".equals(signType)) {
            return rsa256Sign(content, privateKey, charset);
        } else {
            throw new CloudCangException("Sign Type is Not Support : signType=" + signType);
        }
    }

    public static String rsa256Sign(String content, String privateKey, String charset) throws CloudCangException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(priKey);
            if(StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception var6) {
            throw new CloudCangException("RSAcontent = " + content + "; charset = " + charset, var6);
        }
    }

    public static String rsaSign(String content, String privateKey, String charset) throws CloudCangException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            if(StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException var6) {
            throw new CloudCangException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", var6);
        } catch (Exception var7) {
            throw new CloudCangException("RSAcontent = " + content + "; charset = " + charset, var7);
        }
    }

    public static String rsaSign(Map<String, String> params, String privateKey, String charset) throws CloudCangException {
        String signContent = getSignContent(params);
        return rsaSign(signContent, privateKey, charset);
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if(ins != null && !StringUtils.isEmpty(algorithm)) {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = StreamUtil.readText(ins).getBytes();
            encodedKey = Base64.decodeBase64(encodedKey);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }

    public static String getSignCheckContentV1(Map<String, String> params) {
        if(params == null) {
            return null;
        } else {
            params.remove("sign");
            params.remove("sign_type");
            StringBuffer content = new StringBuffer();
            List<String> keys = new ArrayList(params.keySet());
            Collections.sort(keys);

            for(int i = 0; i < keys.size(); ++i) {
                String key = (String)keys.get(i);
                String value = (String)params.get(key);
                content.append((i == 0?"":"&") + key + "=" + value);
            }

            return content.toString();
        }
    }

    public static String getSignCheckContentV2(Map<String, String> params) {
        if(params == null) {
            return null;
        } else {
            params.remove("sign");
            StringBuffer content = new StringBuffer();
            List<String> keys = new ArrayList(params.keySet());
            Collections.sort(keys);

            for(int i = 0; i < keys.size(); ++i) {
                String key = (String)keys.get(i);
                String value = (String)params.get(key);
                content.append((i == 0?"":"&") + key + "=" + value);
            }

            return content.toString();
        }
    }

    public static boolean rsaCheckV1(Map<String, String> params, String publicKey, String charset) throws CloudCangException {
        String sign = (String)params.get("sign");
        String content = getSignCheckContentV1(params);
        return rsaCheckContent(content, sign, publicKey, charset);
    }

    public static boolean rsaCheckV1(Map<String, String> params, String publicKey, String charset, String signType) throws CloudCangException {
        String sign = (String)params.get("sign");
        String content = getSignCheckContentV1(params);
        return rsaCheck(content, sign, publicKey, charset, signType);
    }

    public static boolean rsaCheckV2(Map<String, String> params, String publicKey, String charset) throws CloudCangException {
        String sign = (String)params.get("sign");
        String content = getSignCheckContentV2(params);
        return rsaCheckContent(content, sign, publicKey, charset);
    }

    public static boolean rsaCheckV2(Map<String, String> params, String publicKey, String charset, String signType) throws CloudCangException {
        String sign = (String)params.get("sign");
        String content = getSignCheckContentV2(params);
        return rsaCheck(content, sign, publicKey, charset, signType);
    }

    public static boolean rsaCheck(String content, String sign, String publicKey, String charset, String signType) throws CloudCangException {
        if("RSA".equals(signType)) {
            return rsaCheckContent(content, sign, publicKey, charset);
        } else if("RSA2".equals(signType)) {
            return rsa256CheckContent(content, sign, publicKey, charset);
        } else {
            throw new CloudCangException("Sign Type is Not Support : signType=" + signType);
        }
    }

    public static boolean rsa256CheckContent(String content, String sign, String publicKey, String charset) throws CloudCangException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(pubKey);
            if(StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception var6) {
            throw new CloudCangException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, var6);
        }
    }

    public static boolean rsaCheckContent(String content, String sign, String publicKey, String charset) throws CloudCangException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            if(StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception var6) {
            throw new CloudCangException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, var6);
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static String checkSignAndDecrypt(Map<String, String> params, String alipayPublicKey, String cusPrivateKey, boolean isCheckSign, boolean isDecrypt) throws CloudCangException {
        String charset = (String)params.get("charset");
        String bizContent = (String)params.get("biz_content");
        if(isCheckSign && !rsaCheckV2(params, alipayPublicKey, charset)) {
            throw new CloudCangException("rsaCheck failure:rsaParams=" + params);
        } else {
            return isDecrypt?rsaDecrypt(bizContent, cusPrivateKey, charset):bizContent;
        }
    }

    public static String checkSignAndDecrypt(Map<String, String> params, String alipayPublicKey, String cusPrivateKey, boolean isCheckSign, boolean isDecrypt, String signType) throws CloudCangException {
        String charset = (String)params.get("charset");
        String bizContent = (String)params.get("biz_content");
        if(isCheckSign && !rsaCheckV2(params, alipayPublicKey, charset, signType)) {
            throw new CloudCangException("rsaCheck failure:rsaParams=" + params);
        } else {
            return isDecrypt?rsaDecrypt(bizContent, cusPrivateKey, charset):bizContent;
        }
    }

    public static String encryptAndSign(String bizContent, String alipayPublicKey, String cusPrivateKey, String charset, boolean isEncrypt, boolean isSign) throws CloudCangException {
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isEmpty(charset)) {
            charset = "GBK";
        }

        sb.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");
        String encrypted;
        if(isEncrypt) {
            sb.append("<alipay>");
            encrypted = rsaEncrypt(bizContent, alipayPublicKey, charset);
            sb.append("<response>" + encrypted + "</response>");
            sb.append("<encryption_type>RSA</encryption_type>");
            if(isSign) {
                String sign = rsaSign(encrypted, cusPrivateKey, charset);
                sb.append("<sign>" + sign + "</sign>");
                sb.append("<sign_type>RSA</sign_type>");
            }

            sb.append("</alipay>");
        } else if(isSign) {
            sb.append("<alipay>");
            sb.append("<response>" + bizContent + "</response>");
            encrypted = rsaSign(bizContent, cusPrivateKey, charset);
            sb.append("<sign>" + encrypted + "</sign>");
            sb.append("<sign_type>RSA</sign_type>");
            sb.append("</alipay>");
        } else {
            sb.append(bizContent);
        }

        return sb.toString();
    }

    public static String encryptAndSign(String bizContent, String alipayPublicKey, String cusPrivateKey, String charset, boolean isEncrypt, boolean isSign, String signType) throws CloudCangException {
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isEmpty(charset)) {
            charset = "GBK";
        }

        sb.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");
        String encrypted;
        if(isEncrypt) {
            sb.append("<alipay>");
            encrypted = rsaEncrypt(bizContent, alipayPublicKey, charset);
            sb.append("<response>" + encrypted + "</response>");
            sb.append("<encryption_type>RSA</encryption_type>");
            if(isSign) {
                String sign = rsaSign(encrypted, cusPrivateKey, charset, signType);
                sb.append("<sign>" + sign + "</sign>");
                sb.append("<sign_type>");
                sb.append(signType);
                sb.append("</sign_type>");
            }

            sb.append("</alipay>");
        } else if(isSign) {
            sb.append("<alipay>");
            sb.append("<response>" + bizContent + "</response>");
            encrypted = rsaSign(bizContent, cusPrivateKey, charset, signType);
            sb.append("<sign>" + encrypted + "</sign>");
            sb.append("<sign_type>");
            sb.append(signType);
            sb.append("</sign_type>");
            sb.append("</alipay>");
        } else {
            sb.append(bizContent);
        }

        return sb.toString();
    }

    public static String rsaEncrypt(String content, String publicKey, String charset) throws CloudCangException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, pubKey);
            byte[] data = StringUtils.isEmpty(charset)?content.getBytes():content.getBytes(charset);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * 117) {
                byte[] cache;
                if(inputLen - offSet > 117) {
                    cache = cipher.doFinal(data, offSet, 117);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
            out.close();
            return StringUtils.isEmpty(charset)?new String(encryptedData):new String(encryptedData, charset);
        } catch (Exception var12) {
            throw new CloudCangException("EncryptContent = " + content + ",charset = " + charset, var12);
        }
    }

    public static String rsaDecrypt(String content, String privateKey, String charset) throws CloudCangException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, priKey);
            byte[] encryptedData = StringUtils.isEmpty(charset)? Base64.decodeBase64(content.getBytes()):Base64.decodeBase64(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
                byte[] cache;
                if(inputLen - offSet > 128) {
                    cache = cipher.doFinal(encryptedData, offSet, 128);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] decryptedData = out.toByteArray();
            out.close();
            return StringUtils.isEmpty(charset)?new String(decryptedData):new String(decryptedData, charset);
        } catch (Exception var12) {
            throw new CloudCangException("EncodeContent = " + content + ",charset = " + charset, var12);
        }
    }
}
