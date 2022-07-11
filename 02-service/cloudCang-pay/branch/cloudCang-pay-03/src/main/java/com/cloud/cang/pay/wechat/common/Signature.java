package com.cloud.cang.pay.wechat.common;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.wechat.util.httpclient.auth.PrivateKeySigner;
import com.cloud.cang.pay.wechat.util.httpclient.util.PemUtil;
import org.slf4j.LoggerFactory;
import okhttp3.HttpUrl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.xml.sax.SAXException;

/**
 * 数据签名加密
 */
public class Signature {

    private static final Logger logger = LoggerFactory.getLogger(Signature.class);

    /**
     * 签名算法
     *
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    @SuppressWarnings("rawtypes")
    public static String getSign(Object o, MerchantConf conf) throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                list.add(f.getName() + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + conf.getSpayWechatKey();
        logger.debug("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        logger.debug("Sign Result:" + result);
        return result;
    }

    public static String getSign(Map<String, Object> map, MerchantConf conf) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + conf.getSpayWechatKey();
        logger.debug("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        logger.debug("Sign Result:" + result);
        return result;
    }

    public static String getOpen(Map<String, Object> map, MerchantConf conf) {
     /*   ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getValue() + "\n");
            }
        }
       int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result = sign(result.getBytes(), conf.getSprivateKey());
        return result;*/
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }

        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + conf.getSpayWechatKey();
        logger.debug("Open Before HMACSHA256:" + result);
        result = HMACSHA256.sha256_HMAC(result, conf.getSpayWechatKey()).toUpperCase();
        logger.debug("Open Result:" + result);
        return result;
    }

    private static String sign(byte[] message, String mechantPrivateKey) {
        try {
            java.security.Signature sign = java.security.Signature.getInstance("SHA256withRSA");
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(mechantPrivateKey.getBytes());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            sign.initSign(privateKey);
            sign.update(message);
   /*     PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                new ByteArrayInputStream(mechantPrivateKey.getBytes("utf-8")));*/
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名计算失败", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的私钥", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("生成私钥失败", e);
        }
    }

    /**
     * 从API返回的XML数据里面重新计算一次签名
     *
     * @param responseString API返回的XML数据
     * @return 新鲜出炉的签名
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static String getSignFromResponseString(String responseString, MerchantConf conf) throws IOException, SAXException, ParserConfigurationException {
        Map<String, Object> map = XMLParser.getMapFromXML(responseString);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return Signature.getSign(map, conf);
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param responseString API返回的XML数据字符串
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String responseString, MerchantConf conf) {

        try {
            Map<String, Object> map = XMLParser.getMapFromXML(responseString);
            logger.debug(map.toString());

            String signFromAPIResponse = map.get("sign").toString();
            if (signFromAPIResponse == "" || signFromAPIResponse == null) {
                logger.error("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
                return false;
            }
            logger.debug("服务器回包里面的签名是:" + signFromAPIResponse);
            //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
            map.put("sign", "");
            //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
            String signForAPIResponse = Signature.getSign(map, conf);

            if (!signForAPIResponse.equals(signFromAPIResponse)) {
                //签名验不过，表示这个API返回的数据有可能已经被篡改了
                logger.error("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
                return false;
            }
            logger.debug("恭喜，API返回的数据签名验证通过!!!");
            return true;
        } catch (Exception e) {
            logger.error("验证签名异常：{}", e);
            return false;
        }
    }

 /*   String getToken(String mchid, String method, HttpUrl url, String body) throws UnsupportedEncodingException, SignatureException, NoSuchAlgorithmException {
        String nonceStr = "your nonce string";
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = sign(message.getBytes("utf-8"));

        return "mchid=\"" + mchid + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + yourCertificateSerialNo + "\","
                + "signature=\"" + signature + "\"";
    }

    String sign(byte[] message) throws NoSuchAlgorithmException, SignatureException {
        java.security.Signature sign = java.security.Signature.getInstance("SHA256withRSA");
        sign.initSign(yourPrivateKey);
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }
*/
    String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }

        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

}
