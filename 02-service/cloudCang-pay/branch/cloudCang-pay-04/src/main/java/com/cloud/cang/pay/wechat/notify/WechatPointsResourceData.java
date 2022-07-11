package com.cloud.cang.pay.wechat.notify;

/**
 * Created by YLF on 2019/8/6.
 */
public class WechatPointsResourceData {
    private String algorithm;//resource.algorithm 对开启结果数据进行加密的加密算法，目前只支持AEAD_AES_256_GCM
    private String ciphertext;//resource.ciphertext Base64编码后的开启结果数据密文
    private String associated_data;//resource.associated_data 附加数据
    private String nonce;// resource.nonce 加密使用的随机串
    private String original_type;//

    public String getOriginal_type() {
        return original_type;
    }

    public void setOriginal_type(String original_type) {
        this.original_type = original_type;
    }

    @Override
    public String toString() {
        return "OpenNotifyResourceData{" +
                "algorithm='" + algorithm + '\'' +
                ", ciphertext='" + ciphertext + '\'' +
                ", associated_data='" + associated_data + '\'' +
                ", nonce='" + nonce + '\'' +
                '}';
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getAssociated_data() {
        return associated_data;
    }

    public void setAssociated_data(String associated_data) {
        this.associated_data = associated_data;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
