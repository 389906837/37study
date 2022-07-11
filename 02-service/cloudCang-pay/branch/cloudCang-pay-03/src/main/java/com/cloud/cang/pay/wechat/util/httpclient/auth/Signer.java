package com.cloud.cang.pay.wechat.util.httpclient.auth;

public interface Signer {
  SignatureResult sign(byte[] message);

  class SignatureResult {
    String sign;
    String certificateSerialNumber;

    public SignatureResult(String sign, String serialNumber) {
      this.sign = sign;
      this.certificateSerialNumber = serialNumber;
    }
  }
}
