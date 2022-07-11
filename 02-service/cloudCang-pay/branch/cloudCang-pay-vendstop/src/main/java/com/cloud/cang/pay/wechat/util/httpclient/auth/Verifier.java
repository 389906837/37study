package com.cloud.cang.pay.wechat.util.httpclient.auth;

public interface Verifier {
  boolean verify(String serialNumber, byte[] message, String signature);
}
