package com.cloud.cang.act.ws.util;

/**
 * Created by YLF on 2020/8/6.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


public class RSAUtil {
    /**
     * 私钥签名（A用私钥签名）
     *
     * @param source
     * @param privateKey2
     * @return
     */
    public static String doSignBySHA256withRSA(String source, PrivateKey privateKey2) {
        byte[] sourceData = source.getBytes();
     //   String result = null;
        String string = null;
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey2);
            sign.update(sourceData);

            byte[] resultData = sign.sign();
            string = bytesToHex(resultData);
       /*     System.out.println(string);
            result = Base64Utils.encodeToString(resultData);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
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

}